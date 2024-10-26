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
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryOwner;
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
    private float _pendingPitch = 1f;
    private float _pendingVolume = 1f;
    private RegistryEntry.Reference<SoundEvent> _pendingSoundEvent;

    public ConfiguredSound(String id, Identifier soundEvent, boolean enabled, float pitch, float volume) {
        this.enabled = enabled;
        //? if <1.21.2 {
        //this.soundEvent = RegistryEntry.Reference.standAlone(Registries.SOUND_EVENT.getEntryOwner(), RegistryKey.of(Registries.SOUND_EVENT.getKey(), soundEvent));
        //?} else {
        this.soundEvent = RegistryEntry.Reference.standAlone(Registries.SOUND_EVENT, RegistryKey.of(Registries.SOUND_EVENT.getKey(), soundEvent));
        //?}
        this.pitch = pitch;
        this.volume = volume;

        _pendingPitch = pitch;
        _pendingVolume = volume;
        _pendingSoundEvent = this.soundEvent;

        this.id = id;
        this.client = MinecraftClient.getInstance();
    }

    public ConfiguredSound(String id, RegistryEntry.Reference<SoundEvent> soundEvent, boolean enabled, float pitch, float volume) {
        this(id, soundEvent.registryKey().getValue(), enabled, pitch, volume);
    }

    public ConfiguredSound(String id, SoundEvent soundEvent, boolean enabled, float pitch, float volume) {
        //? if <1.21.2 {
        //this(id, soundEvent.getId(), enabled, pitch, volume);
        //?} else {
        this(id, soundEvent.id(), enabled, pitch, volume);
        //?}
    }

    public ConfiguredSound(String id, RegistryEntry<SoundEvent> soundEvent, boolean enabled, float pitch, float volume) {
        this(id, soundEvent.value(), enabled, pitch, volume);
    }

    private <T extends ConfiguredSound> ArrayList<Option<?>> createDefaultOptions(T defaults) {
        var volumeOpt = Option.<Float>createBuilder()
                .name(Text.translatable("sounds.config.volume.name"))
                .description(OptionDescription.createBuilder()
                                .text(Text.translatable("sounds.config.volume.description")).build())
                .binding(defaults.volume, () -> this.volume, (val) -> this.volume = val)
                .listener((opt, val) -> {
                    this._pendingVolume = val;
                })
                .controller(opt -> FloatSliderControllerBuilder.create(opt).step(0.1f).range(0f, 2f))
                .build();

        var pitchOpt = Option.<Float>createBuilder()
                .name(Text.translatable("sounds.config.pitch.name"))
                .description(OptionDescription.createBuilder()
                        .text(Text.translatable("sounds.config.pitch.description")).build())
                .binding(defaults.pitch, () -> this.pitch, (val) -> this.pitch = val)
                .listener((opt, val) -> {
                    this._pendingPitch = val;
                })
                .controller(opt -> FloatSliderControllerBuilder.create(opt).step(0.1f).range(0f, 2f))
                .build();

        var soundEventOpt = Option.<String>createBuilder()
                .name(Text.translatable("sounds.config.event.name"))
                .description(OptionDescription.createBuilder()
                        .text(Text.translatable("sounds.config.event.description")).build())
                .binding(defaults.soundEvent.registryKey().getValue().toString(), () -> this.soundEvent.registryKey().getValue().toString(), (val) ->
                        this.soundEvent = RegistryEntry.Reference.standAlone(
                                //? if <1.21.2 {
                                //Registries.SOUND_EVENT.getEntryOwner(),
                                //?} else {
                                Registries.SOUND_EVENT,
                                //?}
                                RegistryKey.of(Registries.SOUND_EVENT.getKey(), Identifier.tryParse(val))))
                .listener((opt, val) -> this._pendingSoundEvent = RegistryEntry.Reference.standAlone(
                        //? if <1.21.2 {
                        //Registries.SOUND_EVENT.getEntryOwner(),
                        //?} else {
                        Registries.SOUND_EVENT,
                        //?}
                        RegistryKey.of(Registries.SOUND_EVENT.getKey(), Identifier.tryParse(val))))
                .controller(opt -> DropdownStringControllerBuilder.create(opt)
                        .allowAnyValue(false)
                        .allowEmptyValue(false)
                        .values(Registries.SOUND_EVENT.getKeys().stream().map(RegistryKey::getValue).map(Identifier::toString).toList()))
                .build();

        return new ArrayList<>(List.of(volumeOpt, pitchOpt, soundEventOpt));
    }

    public <T extends ConfiguredSound> ArrayList<Option<?>> addExtraOptions(T defaults) {
        return new ArrayList<>();
    }

    public ButtonOption getPreviewButton() {
        return ButtonOption.createBuilder()
                .name(Text.translatable("sounds.config.preview.name", ""))
                .description(OptionDescription.of(Text.translatable("sounds.config.preview.option.description")))
                .action((a, b) -> playPreviewSound())
                .build();
    }

    public OptionGroup getOptionGroup(ConfiguredSound defaults) {
        ArrayList<Option<?>> defaultOptions = createDefaultOptions(defaults);
        ArrayList<Option<?>> extraOptions = addExtraOptions(defaults);
        ArrayList<Option<?>> allOptions = new ArrayList<>(defaultOptions);
        allOptions.addAll(extraOptions);

        var shouldPlay = Option.<Boolean>createBuilder()
                .name(Text.translatable("sounds.config.shouldPlay.option"))
                .description(OptionDescription.createBuilder()
                        .text(Text.translatable("sounds.config.shouldPlay.option.description")).build())
                .binding(defaults.enabled, () -> this.enabled, (val) -> this.enabled = val)
                .listener((opt, val) -> {
                    // Disable/Enable all options when toggled.
                    allOptions.forEach(option -> option.setAvailable(val));
                })
                .controller(opt -> BooleanControllerBuilder.create(opt).coloured(true).yesNoFormatter())
                .build();

        return OptionGroup
                .createBuilder()
                .name(Text.translatable("sounds.config." + id + ".option").formatted(Formatting.UNDERLINE))
                .description(OptionDescription.createBuilder()
                        .text(Text.translatable("sounds.config." + id + ".option.description")).build())
                .option(getPreviewButton())
                .option(shouldPlay)
                .options(allOptions)
                .collapsed(true)
                .build();
    }

    public final SoundEvent fetchSoundEvent() {
        return Registries.SOUND_EVENT.get(this.soundEvent.registryKey());
    }

    protected static long lastShownToast = -1L;
    public void playSound() {
        if (this.enabled) {
            try {
                final SoundEvent event = Registries.SOUND_EVENT.get(this.soundEvent.registryKey());
                this.playSound(event, this.pitch, this.volume);
            } catch (Exception ignored) {
                // Prevent toast spam:
                if (System.currentTimeMillis() > lastShownToast + 5000) {
                    lastShownToast = System.currentTimeMillis();
                    MinecraftClient client = MinecraftClient.getInstance();
                    client.getToastManager().add(SystemToast.create(client,
                            SystemToast.Type.WORLD_ACCESS_FAILURE,
                            Text.translatable("sounds.config.play.error.title"),
                            Text.translatable("sounds.config.play.error.description", this.getId())));
                }
            }
        }
    }

    private void playPreviewSound() {
        try {
            final SoundEvent event = Registries.SOUND_EVENT.get(this._pendingSoundEvent.registryKey());
            this.playSound(event, _pendingPitch, _pendingVolume);
        } catch (Exception ignored) {
            if (System.currentTimeMillis() > lastShownToast + 5000) {
                lastShownToast = System.currentTimeMillis();
                MinecraftClient client = MinecraftClient.getInstance();
                client.getToastManager().add(SystemToast.create(client,
                        SystemToast.Type.WORLD_ACCESS_FAILURE,
                        Text.translatable("sounds.config.preview.error.title"),
                        Text.translatable("sounds.config.preview.error.description")));
            }
        }
    }

    private void playSound(SoundEvent event, float pitch, float volume) {
        this.playSound(PositionedSoundInstance.master(event, pitch, volume));
    }

    public @Nullable PositionedSoundInstance getSoundInstance() {
        if (this.enabled) {
            try {
                final SoundEvent event = Registries.SOUND_EVENT.get(this.soundEvent.registryKey());
                return PositionedSoundInstance.master(event, pitch, volume);
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
        return Registries.SOUND_EVENT.get(this.soundEvent.registryKey());
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
