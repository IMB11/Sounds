package com.mineblock11.sonance.sound;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ConfiguredSound {
    public static final Codec<ConfiguredSound> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("id").forGetter(ConfiguredSound::getId),
                    Identifier.CODEC.fieldOf("soundEvent").forGetter(sound -> sound.soundEvent.registryKey().getValue()),
                    Codec.BOOL.fieldOf("shouldPlay").forGetter(ConfiguredSound::shouldPlay),
                    Codec.FLOAT.fieldOf("pitch").forGetter(ConfiguredSound::getPitch),
                    Codec.FLOAT.fieldOf("volume").forGetter(ConfiguredSound::getVolume)
            ).apply(instance, ConfiguredSound::new));
    public final String id;
    public final MinecraftClient client;
    public boolean enabled;
    public RegistryEntry.Reference<SoundEvent> soundEvent;
    public float pitch = 1f;
    public float volume = 1f;

    public ConfiguredSound(String id, Identifier soundEvent, boolean enabled, float pitch, float volume) {
        this.enabled = enabled;
        this.soundEvent = RegistryEntry.Reference.standAlone(Registries.SOUND_EVENT.getEntryOwner(), RegistryKey.of(Registries.SOUND_EVENT.getKey(), soundEvent));
        this.pitch = pitch;
        this.volume = volume;
        this.id = id;

        this.client = MinecraftClient.getInstance();
    }

    public ConfiguredSound(String id, RegistryEntry.Reference<SoundEvent> soundEvent, boolean enabled, float pitch, float volume) {
        this(id, soundEvent.registryKey().getValue(), enabled, pitch, volume);
    }

    public ConfiguredSound(String id, SoundEvent soundEvent, boolean enabled, float pitch, float volume) {
        this(id, soundEvent.getId(), enabled, pitch, volume);
    }

    public OptionDescription.Builder addImageIfPresent(OptionDescription.Builder builder, @Nullable Identifier groupImage) {
        if (groupImage != null) {
            return builder.webpImage(groupImage);
        }
        return builder;
    }

    private <T extends ConfiguredSound> ArrayList<Option<?>> createDefaultOptions(T defaults, @Nullable Identifier groupImage) {
        var volumeOpt = Option.<Float>createBuilder()
                .name(Text.translatable("sonance.config.volume.name"))
                .description(addImageIfPresent(OptionDescription.createBuilder()
                                .text(Text.translatable("sonance.config.volume.description"))
                        , groupImage).build())
                .binding(defaults.volume, () -> this.volume, (val) -> this.volume = val)
                .controller(opt -> FloatSliderControllerBuilder.create(opt).step(0.1f).range(0f, 2f))
                .build();

        var pitchOpt = Option.<Float>createBuilder()
                .name(Text.translatable("sonance.config.pitch.name"))
                .description(addImageIfPresent(OptionDescription.createBuilder()
                        .text(Text.translatable("sonance.config.pitch.description")), groupImage
                ).build())
                .binding(defaults.pitch, () -> this.pitch, (val) -> this.pitch = val)
                .controller(opt -> FloatSliderControllerBuilder.create(opt).step(0.1f).range(0f, 2f))
                .build();

        var shouldPlay = Option.<Boolean>createBuilder()
                .name(Text.translatable("sonance.config.shouldPlay.name"))
                .description(addImageIfPresent(OptionDescription.createBuilder()
                                .text(Text.translatable("sonance.config.shouldPlay.description"))
                        , groupImage).build())
                .binding(defaults.enabled, () -> this.enabled, (val) -> this.enabled = val)
                .listener((opt, val) -> {
                    pitchOpt.setAvailable(val);
                    volumeOpt.setAvailable(val);
                })
                .controller(opt -> BooleanControllerBuilder.create(opt).coloured(true).yesNoFormatter())
                .build();

        return new ArrayList<>(List.of(shouldPlay, volumeOpt, pitchOpt));
    }

    public <T extends ConfiguredSound> ArrayList<Option<?>> addExtraOptions(T defaults, @Nullable Identifier groupImage) {
        return new ArrayList<>();
    }

    public OptionGroup getOptionGroup(ConfiguredSound defaults) {
        return this.getOptionGroup(defaults, false);
    }

    public OptionGroup getOptionGroup(ConfiguredSound defaults, boolean hasImage) {
        Identifier image = hasImage ? new Identifier("sonance", "textures/gui/" + id.toLowerCase() + ".webp") : null;

        ArrayList<Option<?>> defaultOptions = createDefaultOptions(defaults, image);
        ArrayList<Option<?>> extraOptions = addExtraOptions(defaults, image);

        ArrayList<Option<?>> allOptions = new ArrayList<>(defaultOptions);
        allOptions.addAll(extraOptions);

        return OptionGroup
                .createBuilder()
                .name(Text.translatable("sonance.config." + id + ".name").formatted(Formatting.UNDERLINE))
                .description(OptionDescription.createBuilder()
                        .text(Text.translatable("sonance.config." + id + ".description"))
                        .build())
                .options(allOptions)
                .collapsed(true)
                .build();
    }

    public final SoundEvent fetchSoundEvent() {
        return Registries.SOUND_EVENT.get(this.soundEvent.registryKey());
    }

    public void playSound() {
        if (this.enabled) {
            final SoundEvent event = Registries.SOUND_EVENT.get(this.soundEvent.registryKey());
            client.getSoundManager().play(PositionedSoundInstance.master(event, pitch, volume));
        }
    }

    public boolean shouldPlay() {
        return enabled;
    }

    public RegistryEntry.Reference<SoundEvent> getSoundEvent() {
        return soundEvent;
    }

    public float getPitch() {
        return pitch;
    }

    public float getVolume() {
        return volume;
    }

    public String getId() {
        return id;
    }
}
