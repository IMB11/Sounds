package dev.imb11.sounds.sound.context;

import dev.imb11.sounds.SoundsClient;
import dev.imb11.sounds.api.SoundDefinition;
import dev.imb11.sounds.api.context.DynamicSoundContext;
import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.UISoundsConfig;
import dev.imb11.sounds.dynamic.DynamicSoundHelper;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.random.Random;

public class ScreenHandlerSoundContext implements DynamicSoundContext<ScreenHandler> {

    private final boolean isOpening;

    public ScreenHandlerSoundContext() {
        this(true);
    }

    public ScreenHandlerSoundContext(boolean isOpening) {
        this.isOpening = isOpening;
    }

    public static ScreenHandlerSoundContext of(boolean isOpening) {
        return new ScreenHandlerSoundContext(isOpening);
    }

    @Override
    public SoundInstance handleContext(ScreenHandler context, SoundEvent fallback, float pitch, float volume) {
        SoundEvent soundEvent = null;
        try {
            var type = context.getType();
            for (SoundDefinition<ScreenHandlerType<?>> definition : DynamicSoundHelper.<ScreenHandlerType<?>>getDefinitions("screens")) {
                if (definition.getKeys().isValid(type)) {
                    soundEvent = definition.getSoundEvent();

                    if (definition.getPitch().isPresent()) {
                        pitch = definition.getPitch().get();
                    }

                    if (definition.getVolume().isPresent()) {
                        volume = definition.getVolume().get();
                    }

                    break;
                }
            }
        } catch (Exception ignored) {
            SoundsClient.LOGGER.warn("Screen of type {} has no declared ScreenHandlerType - ignoring.", context.getClass().getName());
        }

        if (isOpening) {
            fallback = SoundsConfig.get(UISoundsConfig.class).inventoryOpenSoundEffect.fetchSoundEvent();
        } else {
            fallback = SoundsConfig.get(UISoundsConfig.class).inventoryCloseSoundEffect.fetchSoundEvent();
        }

        if (soundEvent == null) {
            soundEvent = fallback;
        }

        return createSoundInstance(soundEvent, pitch, volume);
    }

    @Override
    public SoundInstance getExample(SoundEvent fallback, float pitch, float volume) {
        return createSoundInstance(fallback, pitch, volume);
    }
}
