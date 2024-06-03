package dev.imb11.sounds.api.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.imb11.sounds.api.context.DynamicSoundContext;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class DynamicConfiguredSound<T, F extends DynamicSoundContext<T>> extends ConfiguredSound {
    public static final Codec<DynamicConfiguredSound> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(

                    Codec.STRING.fieldOf("id").forGetter(ConfiguredSound::getId),
                    Identifier.CODEC.fieldOf("soundEvent").forGetter(sound -> sound.soundEvent.registryKey().getValue()),
                    Codec.BOOL.fieldOf("shouldPlay").forGetter(ConfiguredSound::shouldPlay),
                    Codec.FLOAT.fieldOf("pitch").forGetter(ConfiguredSound::getPitch),
                    Codec.FLOAT.fieldOf("volume").forGetter(ConfiguredSound::getVolume),
                    Codec.BOOL.fieldOf("enabledDynamic").forGetter(DynamicConfiguredSound::canUseDynamicSounds)
            ).apply(instance, DynamicConfiguredSound::new));
    public boolean enableDynamicSounds;

    public DynamicConfiguredSound(String id, Identifier soundEvent, boolean enabled, float pitch, float volume, boolean enableDynamicSounds) {
        super(id, soundEvent, enabled, pitch, volume);
        this.enableDynamicSounds = enableDynamicSounds;
    }

    public DynamicConfiguredSound(String id, SoundEvent soundEvent, boolean enabled, float pitch, float volume, boolean enableDynamicSounds) {
        super(id, soundEvent, enabled, pitch, volume);
        this.enableDynamicSounds = enableDynamicSounds;
    }

    public DynamicConfiguredSound(String id, RegistryEntry.Reference<SoundEvent> soundEvent, boolean enabled, float pitch, float volume, boolean enableDynamicSounds) {
        super(id, soundEvent, enabled, pitch, volume);
        this.enableDynamicSounds = enableDynamicSounds;
    }

    public boolean canUseDynamicSounds() {
        return enableDynamicSounds;
    }

    public void playDynamicSound(SoundEvent event) {
        playSound(PositionedSoundInstance.master(event, this.pitch, this.volume));
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
                .name(Text.translatable("sounds.config.dynamic.name"))
                .description(OptionDescription.createBuilder()
                                .text(Text.translatable("sounds.config.dynamic.description")).build())
                .binding(dynamicDefaults.enableDynamicSounds, () -> this.enableDynamicSounds, (val) -> this.enableDynamicSounds = val)
                .controller(opt -> BooleanControllerBuilder.create(opt).coloured(true).onOffFormatter())
                .build();

        options.add(shouldDynamic);

        return options;
    }
}
