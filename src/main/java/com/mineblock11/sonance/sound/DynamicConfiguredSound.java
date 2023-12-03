package com.mineblock11.sonance.sound;

import com.mineblock11.sonance.sound.context.DynamicSoundContext;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

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

    public DynamicConfiguredSound(String id, RegistryEntry.Reference<SoundEvent> soundEvent, boolean enabled, float pitch, float volume, boolean enableDynamicSounds) {
        super(id, soundEvent, enabled, pitch, volume);
        this.enableDynamicSounds = enableDynamicSounds;
    }

    public void playSound(@Nullable SoundEvent event, @Nullable Float _pitch, @Nullable Float _volume) {
        this.client.getSoundManager().play(PositionedSoundInstance.master(event != null ? event : this.fetchSoundEvent(), _pitch != null ? _pitch : pitch, _volume != null ? _volume : volume));
    }

    public boolean canUseDynamicSounds() {
        return enableDynamicSounds;
    }

    public void playDynamicSound(SoundEvent event) {
        if (enableDynamicSounds) {
            this.playSound(event, null, null);
        } else {
            this.playSound();
        }
    }

    public void playDynamicSound(T context, F contextHandler) {
        SoundEvent event = contextHandler.handleContext(context);

        if (event == null || !enableDynamicSounds) {
            this.playSound();
        }

        this.playSound(event, null, null);
    }

    @Override
    public <E extends ConfiguredSound> ArrayList<Option<?>> addExtraOptions(E defaults, @Nullable Identifier groupImage) {
        DynamicConfiguredSound<?, ?> dynamicDefaults = (DynamicConfiguredSound<?, ?>) defaults;
        ArrayList<Option<?>> options = super.addExtraOptions(defaults, groupImage);

        var shouldDynamic = Option.<Boolean>createBuilder()
                .name(Text.translatable("sonance.config.dynamic.name"))
                .description(addImageIfPresent(OptionDescription.createBuilder()
                                .text(Text.translatable("sonance.config.dynamic.description"))
                        , groupImage).build())
                .binding(dynamicDefaults.enableDynamicSounds, () -> this.enableDynamicSounds, (val) -> this.enableDynamicSounds = val)
                .controller(opt -> BooleanControllerBuilder.create(opt).coloured(true).onOffFormatter())
                .build();

        options.add(shouldDynamic);

        return options;
    }
}
