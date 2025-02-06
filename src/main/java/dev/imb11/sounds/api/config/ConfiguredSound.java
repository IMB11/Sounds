package dev.imb11.sounds.api.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.isxander.yacl3.api.ButtonOption;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.DropdownStringControllerBuilder;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class ConfiguredSound {
    public static final Codec<ConfiguredSound> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("id").forGetter(ConfiguredSound::getId),
                    ResourceLocation.CODEC.fieldOf("soundEvent").forGetter(sound -> sound.soundEvent.key().location()),
                    Codec.BOOL.fieldOf("shouldPlay").forGetter(ConfiguredSound::shouldPlay),
                    Codec.FLOAT.fieldOf("pitch").forGetter(ConfiguredSound::getPitch),
                    Codec.FLOAT.fieldOf("volume").forGetter(ConfiguredSound::getVolume)
            ).apply(instance, ConfiguredSound::new));
    public final String id;
    public final Minecraft client;
    public boolean enabled;
    public Holder.Reference<SoundEvent> soundEvent;
    public float pitch = 1f;
    public float volume = 1f;
    private float _pendingPitch = 1f;
    private float _pendingVolume = 1f;
    private Holder.Reference<SoundEvent> _pendingSoundEvent;

    public ConfiguredSound(String id, ResourceLocation soundEvent, boolean enabled, float pitch, float volume) {
        this.enabled = enabled;
        //? if <1.21.2 {
        //this.soundEvent = RegistryEntry.Reference.standAlone(Registries.SOUND_EVENT.getEntryOwner(), RegistryKey.of(Registries.SOUND_EVENT.getKey(), soundEvent));
        //?} else {
        this.soundEvent = Holder.Reference.createStandAlone(BuiltInRegistries.SOUND_EVENT, ResourceKey.create(BuiltInRegistries.SOUND_EVENT.key(), soundEvent));
        //?}
        this.pitch = pitch;
        this.volume = volume;

        _pendingPitch = pitch;
        _pendingVolume = volume;
        _pendingSoundEvent = this.soundEvent;

        this.id = id;
        this.client = Minecraft.getInstance();
    }

    public ConfiguredSound(String id, Holder.Reference<SoundEvent> soundEvent, boolean enabled, float pitch, float volume) {
        this(id, soundEvent.key().location(), enabled, pitch, volume);
    }

    public ConfiguredSound(String id, SoundEvent soundEvent, boolean enabled, float pitch, float volume) {
        //? if <1.21.2 {
        //this(id, soundEvent.getId(), enabled, pitch, volume);
        //?} else {
        this(id, soundEvent.location(), enabled, pitch, volume);
        //?}
    }

    public ConfiguredSound(String id, Holder<SoundEvent> soundEvent, boolean enabled, float pitch, float volume) {
        this(id, soundEvent.value(), enabled, pitch, volume);
    }

    private <T extends ConfiguredSound> ArrayList<Option<?>> createDefaultOptions(T defaults) {
        var volumeOpt = Option.<Float>createBuilder()
                .name(Component.translatable("sounds.config.volume.name"))
                .description(OptionDescription.createBuilder()
                                .text(Component.translatable("sounds.config.volume.description")).build())
                .binding(defaults.volume, () -> this.volume, (val) -> this.volume = val)
                .listener((opt, val) -> {
                    this._pendingVolume = val;
                })
                .controller(opt -> FloatSliderControllerBuilder.create(opt).step(0.1f).range(0f, 2f))
                .build();

        var pitchOpt = Option.<Float>createBuilder()
                .name(Component.translatable("sounds.config.pitch.name"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("sounds.config.pitch.description")).build())
                .binding(defaults.pitch, () -> this.pitch, (val) -> this.pitch = val)
                .listener((opt, val) -> {
                    this._pendingPitch = val;
                })
                .controller(opt -> FloatSliderControllerBuilder.create(opt).step(0.1f).range(0f, 2f))
                .build();

        var soundEventOpt = Option.<String>createBuilder()
                .name(Component.translatable("sounds.config.event.name"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("sounds.config.event.description")).build())
                .binding(defaults.soundEvent.key().location().toString(), () -> this.soundEvent.key().location().toString(), (val) ->
                        this.soundEvent = Holder.Reference.createStandAlone(
                                //? if <1.21.2 {
                                //Registries.SOUND_EVENT.getEntryOwner(),
                                //?} else {
                                BuiltInRegistries.SOUND_EVENT,
                                //?}
                                ResourceKey.create(BuiltInRegistries.SOUND_EVENT.key(), ResourceLocation.tryParse(val))))
                .listener((opt, val) -> this._pendingSoundEvent = Holder.Reference.createStandAlone(
                        //? if <1.21.2 {
                        //Registries.SOUND_EVENT.getEntryOwner(),
                        //?} else {
                        BuiltInRegistries.SOUND_EVENT,
                        //?}
                        ResourceKey.create(BuiltInRegistries.SOUND_EVENT.key(), ResourceLocation.tryParse(val))))
                .controller(opt -> DropdownStringControllerBuilder.create(opt)
                        .allowAnyValue(false)
                        .allowEmptyValue(false)
                        .values(BuiltInRegistries.SOUND_EVENT.registryKeySet().stream().map(ResourceKey::location).map(ResourceLocation::toString).toList()))
                .build();

        return new ArrayList<>(List.of(volumeOpt, pitchOpt, soundEventOpt));
    }

    public <T extends ConfiguredSound> ArrayList<Option<?>> addExtraOptions(T defaults) {
        return new ArrayList<>();
    }

    public ButtonOption getPreviewButton() {
        return ButtonOption.createBuilder()
                .name(Component.translatable("sounds.config.preview.name", ""))
                .description(OptionDescription.of(Component.translatable("sounds.config.preview.option.description")))
                .action((a, b) -> playPreviewSound())
                .build();
    }

    public OptionGroup getOptionGroup(ConfiguredSound defaults) {
        ArrayList<Option<?>> defaultOptions = createDefaultOptions(defaults);
        ArrayList<Option<?>> extraOptions = addExtraOptions(defaults);
        ArrayList<Option<?>> allOptions = new ArrayList<>(defaultOptions);
        allOptions.addAll(extraOptions);

        var shouldPlay = Option.<Boolean>createBuilder()
                .name(Component.translatable("sounds.config.shouldPlay.option"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("sounds.config.shouldPlay.option.description")).build())
                .binding(defaults.enabled, () -> this.enabled, (val) -> this.enabled = val)
                .listener((opt, val) -> {
                    // Disable/Enable all options when toggled.
                    allOptions.forEach(option -> option.setAvailable(val));
                })
                .controller(opt -> BooleanControllerBuilder.create(opt).coloured(true).yesNoFormatter())
                .build();

        return OptionGroup
                .createBuilder()
                .name(Component.translatable("sounds.config." + id + ".option").withStyle(ChatFormatting.UNDERLINE))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("sounds.config." + id + ".option.description")).build())
                .option(getPreviewButton())
                .option(shouldPlay)
                .options(allOptions)
                .collapsed(true)
                .build();
    }

    public final SoundEvent fetchSoundEvent() {
        return BuiltInRegistries.SOUND_EVENT.getValue(this.soundEvent.key());
    }

    protected static long lastShownToast = -1L;
    public void playSound() {
        if (this.enabled) {
            try {
                final SoundEvent event = BuiltInRegistries.SOUND_EVENT.getValue(this.soundEvent.key());
                this.playSound(event, this.pitch, this.volume);
            } catch (Exception ignored) {
                // Prevent toast spam:
                if (System.currentTimeMillis() > lastShownToast + 5000) {
                    lastShownToast = System.currentTimeMillis();
                    Minecraft client = Minecraft.getInstance();
                    client.getToastManager().addToast(SystemToast.multiline(client,
                            SystemToast.SystemToastId.WORLD_ACCESS_FAILURE,
                            Component.translatable("sounds.config.play.error.title"),
                            Component.translatable("sounds.config.play.error.description", this.getId())));
                }
            }
        }
    }

    private void playPreviewSound() {
        try {
            final SoundEvent event = BuiltInRegistries.SOUND_EVENT.getValue(this._pendingSoundEvent.key());
            this.playSound(event, _pendingPitch, _pendingVolume);
        } catch (Exception ignored) {
            if (System.currentTimeMillis() > lastShownToast + 5000) {
                lastShownToast = System.currentTimeMillis();
                Minecraft client = Minecraft.getInstance();
                client.getToastManager().addToast(SystemToast.multiline(client,
                        SystemToast.SystemToastId.WORLD_ACCESS_FAILURE,
                        Component.translatable("sounds.config.preview.error.title"),
                        Component.translatable("sounds.config.preview.error.description")));
            }
        }
    }

    private void playSound(SoundEvent event, float pitch, float volume) {
        this.playSound(SimpleSoundInstance.forUI(event, pitch, volume));
    }

    public @Nullable SimpleSoundInstance getSoundInstance() {
        if (this.enabled) {
            try {
                final SoundEvent event = BuiltInRegistries.SOUND_EVENT.getValue(this.soundEvent.key());
                return SimpleSoundInstance.forUI(event, pitch, volume);
            } catch (Exception ignored) {
                return null;
            }
        }
        return null;
    }

    public void playSound(SoundInstance soundInstance) {
        if (this.enabled) {
            client.getSoundManager().play(soundInstance);
        }
    }

    public void stopSound(SoundInstance soundInstance) {
        if (this.enabled) {
            client.getSoundManager().stop(soundInstance);
        }
    }

    public boolean shouldPlay() {
        return enabled;
    }

    public SoundEvent getSoundEvent() {
        return BuiltInRegistries.SOUND_EVENT.getValue(this.soundEvent.key());
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
