package com.mineblock11.sonance.sound.context;

import com.mineblock11.sonance.SonanceClient;
import com.mineblock11.sonance.api.SoundDefinition;
import com.mineblock11.sonance.config.UISoundConfig;
import com.mineblock11.sonance.dynamic.DynamicSoundHelper;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import org.jetbrains.annotations.Nullable;

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
                if (definition.keys.isValid(type)) {
                    soundEvent = definition.soundEvent;
                    break;
                }
            }
        } catch (Exception ignored) {
            SonanceClient.LOGGER.warn("Screen of type {} has no declared ScreenHandlerType - ignoring.", context.getClass().getName());
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
