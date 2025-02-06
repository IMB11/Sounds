package dev.imb11.sounds.api.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.imb11.sounds.api.context.DynamicSoundContext;
import dev.isxander.yacl3.api.ButtonOption;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class DynamicConfiguredSound<T, F extends DynamicSoundContext<T>> extends ConfiguredSound {
    public static final Codec<DynamicConfiguredSound> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("id").forGetter(ConfiguredSound::getId),
                    ResourceLocation.CODEC.fieldOf("soundEvent").forGetter(sound -> sound.soundEvent.key().location()),
                    Codec.BOOL.fieldOf("shouldPlay").forGetter(ConfiguredSound::shouldPlay),
                    Codec.FLOAT.fieldOf("pitch").forGetter(ConfiguredSound::getPitch),
                    Codec.FLOAT.fieldOf("volume").forGetter(ConfiguredSound::getVolume),
                    Codec.BOOL.fieldOf("enabledDynamic").forGetter(DynamicConfiguredSound::canUseDynamicSounds)
            ).apply(instance, DynamicConfiguredSound::new));
    public boolean enableDynamicSounds;

    public DynamicConfiguredSound(String id, ResourceLocation soundEvent, boolean enabled, float pitch, float volume, boolean enableDynamicSounds) {
        super(id, soundEvent, enabled, pitch, volume);
        this.enableDynamicSounds = enableDynamicSounds;
    }

    public DynamicConfiguredSound(String id, SoundEvent soundEvent, boolean enabled, float pitch, float volume, boolean enableDynamicSounds) {
        super(id, soundEvent, enabled, pitch, volume);
        this.enableDynamicSounds = enableDynamicSounds;
    }

    public DynamicConfiguredSound(String id, Holder.Reference<SoundEvent> soundEvent, boolean enabled, float pitch, float volume, boolean enableDynamicSounds) {
        super(id, soundEvent, enabled, pitch, volume);
        this.enableDynamicSounds = enableDynamicSounds;
    }

    public boolean canUseDynamicSounds() {
        return enableDynamicSounds;
    }

    public void playDynamicSound(SoundEvent event) {
        playSound(SimpleSoundInstance.forUI(event, this.pitch, this.volume));
    }

    public void playDynamicSound(T context, F contextHandler) {
        SoundInstance event = contextHandler.handleContext(context, getSoundEvent(), this.pitch, this.volume);

        if (event == null || !enableDynamicSounds) {
            this.playSound();
            return;
        }

        this.playSound(event);
    }

    @Override
    public <E extends ConfiguredSound> ArrayList<Option<?>> addExtraOptions(E defaults) {
        DynamicConfiguredSound<?, ?> dynamicDefaults = (DynamicConfiguredSound<?, ?>) defaults;
        ArrayList<Option<?>> options = super.addExtraOptions(defaults);

        var shouldDynamic = Option.<Boolean>createBuilder()
                .name(Component.translatable("sounds.config.dynamic.option"))
                .description(OptionDescription.createBuilder()
                                .text(Component.translatable("sounds.config.dynamic.option.description")).build())
                .binding(dynamicDefaults.enableDynamicSounds, () -> this.enableDynamicSounds, (val) -> this.enableDynamicSounds = val)
                .controller(opt -> BooleanControllerBuilder.create(opt).coloured(true).onOffFormatter())
                .build();

        options.add(shouldDynamic);

        return options;
    }
}
