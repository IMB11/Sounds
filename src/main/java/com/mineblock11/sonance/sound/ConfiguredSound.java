package com.mineblock11.sonance.sound;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class ConfiguredSound {
    public static final Codec<ConfiguredSound> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("shouldPlay").forGetter(ConfiguredSound::isShouldPlay),
                    ResourceLocation.CODEC.fieldOf("soundEvent").forGetter(sound -> sound.soundEvent.key().location()),
                    Codec.FLOAT.fieldOf("pitch").forGetter(ConfiguredSound::getPitch),
                    Codec.FLOAT.fieldOf("volume").forGetter(ConfiguredSound::getVolume),
                    Codec.STRING.fieldOf("id").forGetter(ConfiguredSound::getId)
            ).apply(instance, ConfiguredSound::new));
    public final String id;
    public boolean shouldPlay;
    public Holder.Reference<SoundEvent> soundEvent;
    public float pitch = 1f;
    public float volume = 1f;
    private float defaultPitch;
    private float defaultVolume;

    public ConfiguredSound(boolean shouldPlay, ResourceLocation soundEvent, float pitch, float volume, String id) {
        this.defaultPitch = defaultPitch;
        this.defaultVolume = defaultVolume;
        this.shouldPlay = shouldPlay;
        this.soundEvent = Holder.Reference.createStandAlone(BuiltInRegistries.SOUND_EVENT.holderOwner(), ResourceKey.create(BuiltInRegistries.SOUND_EVENT.key(), soundEvent));
        this.pitch = pitch;
        this.volume = volume;
        this.id = id;
    }

    public ConfiguredSound(boolean shouldPlay, String id, Holder.Reference<SoundEvent> soundEvent, float pitch, float volume) {
        this.shouldPlay = shouldPlay;
        this.id = id;
        this.soundEvent = soundEvent;
        this.pitch = pitch;
        this.volume = volume;
    }

    public ConfiguredSound(boolean shouldPlay, String id, SoundEvent soundEvent, float pitch, float volume) {
        this.shouldPlay = shouldPlay;
        this.id = id;
        this.pitch = pitch;
        this.volume = volume;

        var key = BuiltInRegistries.SOUND_EVENT.getResourceKey(soundEvent).orElseThrow();
        this.soundEvent = BuiltInRegistries.SOUND_EVENT.getHolder(key).orElseThrow();
    }

    public final SoundEvent fetchSoundEvent() {
        return BuiltInRegistries.SOUND_EVENT.get(this.soundEvent.key());
    }

    public void playSound() {
        if (this.shouldPlay) {
            Minecraft client = Minecraft.getInstance();
            final SoundEvent event = BuiltInRegistries.SOUND_EVENT.get(this.soundEvent.key());
            client.getSoundManager().play(SimpleSoundInstance.forUI(event, pitch, volume));
        }
    }

    public void forceSound(@Nullable SoundEvent event, @Nullable Float _pitch, @Nullable Float _volume) {
        Minecraft client = Minecraft.getInstance();
        client.getSoundManager().play(SimpleSoundInstance.forUI(event != null ? event : this.fetchSoundEvent(), _pitch != null ? _pitch : pitch, _volume != null ? _volume : volume));
    }

    public OptionGroup getOptionGroup(ConfiguredSound defaults) {
        return this.getOptionGroup(defaults, false);
    }

    public OptionGroup getOptionGroup(ConfiguredSound defaults, boolean hasImage) {
        if(hasImage) {
            var volumeOpt = Option.<Float>createBuilder()
                    .name(Component.translatable("sonance.config.volume.name"))
                    .description(OptionDescription.createBuilder()
                            .webpImage(new ResourceLocation("sonance", "textures/config/" + id.toLowerCase() + ".webp"))
                            .text(Component.translatable("sonance.config.volume.description"))
                            .build())
                    .binding(defaults.volume, () -> this.volume, (val) -> this.volume = val)
                    .controller(opt -> FloatSliderControllerBuilder.create(opt).step(0.1f).range(0f, 2f))
                    .build();

            var pitchOpt = Option.<Float>createBuilder()
                    .name(Component.translatable("sonance.config.pitch.name"))
                    .description(OptionDescription.createBuilder()
                            .webpImage(new ResourceLocation("sonance", "textures/config/" + id.toLowerCase() + ".webp"))
                            .text(Component.translatable("sonance.config.pitch.description"))
                            .build())
                    .binding(defaults.pitch, () -> this.pitch, (val) -> this.pitch = val)
                    .controller(opt -> FloatSliderControllerBuilder.create(opt).step(0.1f).range(0f, 2f))
                    .build();

            var shouldPlay = Option.<Boolean>createBuilder()
                    .name(Component.translatable("sonance.config.shouldPlay.name"))
                    .description(OptionDescription.createBuilder()
                            .webpImage(new ResourceLocation("sonance", "textures/config/" + id.toLowerCase() + ".webp"))
                            .text(Component.translatable("sonance.config.shouldPlay.description"))
                            .build())
                    .binding(defaults.shouldPlay, () -> this.shouldPlay, (val) -> this.shouldPlay = val)
                    .listener((opt, val) -> {
                        pitchOpt.setAvailable(val);
                        volumeOpt.setAvailable(val);
                    })
                    .controller(opt -> BooleanControllerBuilder.create(opt).coloured(true).yesNoFormatter())
                    .build();

            return OptionGroup
                    .createBuilder()
                    .name(Component.translatable("sonance.config." + id + ".name"))
                    .description(OptionDescription.createBuilder()
                            .webpImage(new ResourceLocation("sonance", "textures/config/" + id.toLowerCase() + ".webp"))
                            .text(Component.translatable("sonance.config." + id + ".description"))
                            .build())
                    .options(List.of(shouldPlay, volumeOpt, pitchOpt))
                    .collapsed(true)
                    .build();
        }

        var volumeOpt = Option.<Float>createBuilder()
                .name(Component.translatable("sonance.config.volume.name"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("sonance.config.volume.description"))
                        .build())
                .binding(defaults.volume, () -> this.volume, (val) -> this.volume = val)
                .controller(opt -> FloatSliderControllerBuilder.create(opt).step(0.1f).range(0f, 2f))
                .build();

        var pitchOpt = Option.<Float>createBuilder()
                .name(Component.translatable("sonance.config.pitch.name"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("sonance.config.pitch.description"))
                        .build())
                .binding(defaults.pitch, () -> this.pitch, (val) -> this.pitch = val)
                .controller(opt -> FloatSliderControllerBuilder.create(opt).step(0.1f).range(0f, 2f))
                .build();

        var shouldPlay = Option.<Boolean>createBuilder()
                .name(Component.translatable("sonance.config.shouldPlay.name"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("sonance.config.shouldPlay.description"))
                        .build())
                .binding(defaults.shouldPlay, () -> this.shouldPlay, (val) -> this.shouldPlay = val)
                .listener((opt, val) -> {
                    pitchOpt.setAvailable(val);
                    volumeOpt.setAvailable(val);
                })
                .controller(opt -> BooleanControllerBuilder.create(opt).coloured(true).yesNoFormatter())
                .build();

        return OptionGroup
                .createBuilder()
                .name(Component.translatable("sonance.config." + id + ".name"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("sonance.config." + id + ".description"))
                        .build())
                .options(List.of(shouldPlay, volumeOpt, pitchOpt))
                .collapsed(true)
                .build();
    }

    public float getDefaultPitch() {
        return defaultPitch;
    }

    public void setDefaultPitch(float defaultPitch) {
        this.defaultPitch = defaultPitch;
    }

    public float getDefaultVolume() {
        return defaultVolume;
    }

    public void setDefaultVolume(float defaultVolume) {
        this.defaultVolume = defaultVolume;
    }

    public boolean isShouldPlay() {
        return shouldPlay;
    }

    public void setShouldPlay(boolean shouldPlay) {
        this.shouldPlay = shouldPlay;
    }

    public Holder.Reference<SoundEvent> getSoundEvent() {
        return soundEvent;
    }

    public void setSoundEvent(Holder.Reference<SoundEvent> soundEvent) {
        this.soundEvent = soundEvent;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public String getId() {
        return id;
    }
}
