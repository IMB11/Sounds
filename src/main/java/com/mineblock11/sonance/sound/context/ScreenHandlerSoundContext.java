package com.mineblock11.sonance.sound.context;

import com.mineblock11.sonance.SonanceClient;
import com.mineblock11.sonance.api.SoundDefinition;
import com.mineblock11.sonance.config.UISoundConfig;
import com.mineblock11.sonance.dynamic.DynamicSoundHelper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.SoundEvent;
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
    public @Nullable SoundEvent handleContext(ScreenHandler context) {
        try {
            var type = context.getType();
            for (SoundDefinition<ScreenHandlerType<?>> definition : DynamicSoundHelper.<ScreenHandlerType<?>>getDefinitions("screens")) {
                if (definition.keys.isValid(type)) {
                    return definition.soundEvent;
                }
            }
        } catch (Exception ignored) {
            SonanceClient.LOGGER.warn("Screen of type {} has no declared ScreenHandlerType - ignoring.", context.getClass().getName());
        }

        return isOpening ? UISoundConfig.get().inventoryOpenSoundEffect.fetchSoundEvent() : UISoundConfig.get().inventoryCloseSoundEffect.fetchSoundEvent();
    }
}
