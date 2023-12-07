package dev.imb11.sounds.sound.context;

import dev.imb11.sounds.SoundsClient;
import dev.imb11.sounds.api.SoundDefinition;
import dev.imb11.sounds.config.UISoundConfig;
import dev.imb11.sounds.dynamic.DynamicSoundHelper;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.SoundEvent;

public class ScreenHandlerSoundContext implements DynamicSoundContext<ScreenHandler> {

    private final boolean isOpening;

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

                    if(definition.getPitch().isPresent()) {
                        pitch = definition.getPitch().get();
                    }

                    if(definition.getVolume().isPresent()) {
                        volume = definition.getVolume().get();
                    }

                    break;
                }
            }
        } catch (Exception ignored) {
            SoundsClient.LOGGER.warn("Screen of type {} has no declared ScreenHandlerType - ignoring.", context.getClass().getName());
        }

        if(isOpening) {
            fallback = UISoundConfig.get().inventoryOpenSoundEffect.fetchSoundEvent();
        } else {
            fallback = UISoundConfig.get().inventoryCloseSoundEffect.fetchSoundEvent();
        }

        if(soundEvent == null) {
            soundEvent = fallback;
        }

        return createSoundInstance(soundEvent, pitch, volume);
    }
}
