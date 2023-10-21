package com.mineblock11.sonance.sound;

import com.mineblock11.sonance.dynamic.DynamicSoundHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class DynamicConfiguredSound extends ConfiguredSound {
    public static final Codec<DynamicConfiguredSound> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("shouldPlay").forGetter(ConfiguredSound::isShouldPlay),
                    Codec.BOOL.fieldOf("enabledDynamic").forGetter(DynamicConfiguredSound::isEnabledDynamic),
                    Identifier.CODEC.fieldOf("soundEvent").forGetter(sound -> sound.soundEvent.registryKey().getValue()),
                    Codec.FLOAT.fieldOf("pitch").forGetter(ConfiguredSound::getPitch),
                    Codec.FLOAT.fieldOf("volume").forGetter(ConfiguredSound::getVolume),
                    Codec.STRING.fieldOf("id").forGetter(ConfiguredSound::getId)
            ).apply(instance, DynamicConfiguredSound::new));
    public boolean enabledDynamic;

    public DynamicConfiguredSound(boolean shouldPlay, boolean enabledDynamic, Identifier soundEvent, float pitch, float volume, String id) {
        super(shouldPlay, soundEvent, pitch, volume, id);
        this.enabledDynamic = enabledDynamic;
    }

    public DynamicConfiguredSound(boolean shouldPlay, boolean enabledDynamic, String id, RegistryEntry.Reference<SoundEvent> soundEvent, float pitch, float volume) {
        super(shouldPlay, id, soundEvent, pitch, volume);
        this.enabledDynamic = enabledDynamic;
    }

    public boolean isEnabledDynamic() {
        return enabledDynamic;
    }

    public void playDynamicSound(SoundEvent event) {
        if (enabledDynamic ) {
            this.forceSound(event, null, null);
        } else {
            this.playSound();
        }
    }

    public void playDynamicSound(ItemStack stack, DynamicSoundHelper.BlockSoundType soundType) {
        if (enabledDynamic && stack != null) {
            this.forceSound(DynamicSoundHelper.getItemSound(stack, this.fetchSoundEvent(), soundType), null, null);
        } else {
            this.playSound();
        }
    }

    public void playDynamicSound(ScreenHandler screen) {
        if (enabledDynamic && screen != null) {
            this.forceSound(DynamicSoundHelper.getScreenSound(screen), null, null);
        } else {
            this.playSound();
        }
    }

    public OptionGroup getOptionGroup(DynamicConfiguredSound defaults) {
        return this.getOptionGroup(defaults, false);
    }

    public OptionGroup getOptionGroup(DynamicConfiguredSound defaults, boolean enableImage) {
        if(enableImage) {
            var volumeOpt = Option.<Float>createBuilder()
                    .name(Text.translatable("sonance.config.volume.name"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("sonance.config.volume.description"))
                            .webpImage(new Identifier("sonance", "textures/config/" + id.toLowerCase() + ".webp"))
                            .build())
                    .binding(defaults.volume, () -> this.volume, (val) -> this.volume = val)
                    .controller(opt -> FloatSliderControllerBuilder.create(opt).step(0.1f).range(0f, 2f))
                    .build();

            var pitchOpt = Option.<Float>createBuilder()
                    .name(Text.translatable("sonance.config.pitch.name"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("sonance.config.pitch.description"))
                            .webpImage(new Identifier("sonance", "textures/config/" + id.toLowerCase() + ".webp"))
                            .build())
                    .binding(defaults.pitch, () -> this.pitch, (val) -> this.pitch = val)
                    .controller(opt -> FloatSliderControllerBuilder.create(opt).step(0.1f).range(0f, 2f))
                    .build();

            var shouldPlay = Option.<Boolean>createBuilder()
                    .name(Text.translatable("sonance.config.shouldPlay.name"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("sonance.config.shouldPlay.description"))
                            .webpImage(new Identifier("sonance", "textures/config/" + id.toLowerCase() + ".webp"))
                            .build())
                    .binding(defaults.shouldPlay, () -> this.shouldPlay, (val) -> this.shouldPlay = val)
                    .listener((opt, val) -> {
                        pitchOpt.setAvailable(val);
                        volumeOpt.setAvailable(val);
                    })
                    .controller(opt -> BooleanControllerBuilder.create(opt).coloured(true).yesNoFormatter())
                    .build();

            var shouldDynamic = Option.<Boolean>createBuilder()
                    .name(Text.translatable("sonance.config.dynamic.name"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("sonance.config.dynamic.description"))
                            .webpImage(new Identifier("sonance", "textures/config/" + id.toLowerCase() + ".webp"))
                            .build())
                    .binding(defaults.enabledDynamic, () -> this.enabledDynamic, (val) -> this.enabledDynamic = val)
                    .controller(opt -> BooleanControllerBuilder.create(opt).coloured(true).onOffFormatter())
                    .build();

            return OptionGroup
                    .createBuilder()
                    .name(Text.translatable("sonance.config." + id + ".name"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("sonance.config." + id + ".description"))
                            .webpImage(new Identifier("sonance", "textures/config/" + id.toLowerCase() + ".webp"))
                            .build())
                    .options(List.of(shouldPlay, shouldDynamic, volumeOpt, pitchOpt))
                    .collapsed(true)
                    .build();
        }

        var volumeOpt = Option.<Float>createBuilder()
                .name(Text.translatable("sonance.config.volume.name"))
                .description(OptionDescription.createBuilder()
                        .text(Text.translatable("sonance.config.volume.description"))
//                        .webpImage(new Identifier("sonance", "textures/config/" + id + ".webp"))
                        .build())
                .binding(defaults.volume, () -> this.volume, (val) -> this.volume = val)
                .controller(opt -> FloatSliderControllerBuilder.create(opt).step(0.1f).range(0f, 2f))
                .build();

        var pitchOpt = Option.<Float>createBuilder()
                .name(Text.translatable("sonance.config.pitch.name"))
                .description(OptionDescription.createBuilder()
                        .text(Text.translatable("sonance.config.pitch.description"))
//                        .webpImage(new Identifier("sonance", "images/" + id + ".webp"))
                        .build())
                .binding(defaults.pitch, () -> this.pitch, (val) -> this.pitch = val)
                .controller(opt -> FloatSliderControllerBuilder.create(opt).step(0.1f).range(0f, 2f))
                .build();

        var shouldPlay = Option.<Boolean>createBuilder()
                .name(Text.translatable("sonance.config.shouldPlay.name"))
                .description(OptionDescription.createBuilder()
                        .text(Text.translatable("sonance.config.shouldPlay.description"))
//                        .webpImage(new Identifier("sonance", "images/" + id + ".webp"))
                        .build())
                .binding(defaults.shouldPlay, () -> this.shouldPlay, (val) -> this.shouldPlay = val)
                .listener((opt, val) -> {
                    pitchOpt.setAvailable(val);
                    volumeOpt.setAvailable(val);
                })
                .controller(opt -> BooleanControllerBuilder.create(opt).coloured(true).yesNoFormatter())
                .build();

        var shouldDynamic = Option.<Boolean>createBuilder()
                .name(Text.translatable("sonance.config.dynamic.name"))
                .description(OptionDescription.createBuilder()
                        .text(Text.translatable("sonance.config.dynamic.description"))
//                        .webpImage(new Identifier("sonance", "images/" + id + ".webp"))
                        .build())
                .binding(defaults.enabledDynamic, () -> this.enabledDynamic, (val) -> this.enabledDynamic = val)
                .controller(opt -> BooleanControllerBuilder.create(opt).coloured(true).onOffFormatter())
                .build();

        return OptionGroup
                .createBuilder()
                .name(Text.translatable("sonance.config." + id + ".name"))
                .description(OptionDescription.createBuilder()
                        .text(Text.translatable("sonance.config." + id + ".description"))
//                        .webpImage(new Identifier("sonance", "images/" + id + ".webp"))
                        .build())
                .options(List.of(shouldPlay, shouldDynamic, volumeOpt, pitchOpt))
                .collapsed(true)
                .build();
    }
}
