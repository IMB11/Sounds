package dev.imb11.sounds.sound.context;

import dev.imb11.sounds.SoundsClient;
import dev.imb11.sounds.api.SoundDefinition;
import dev.imb11.sounds.api.context.DynamicSoundContext;
import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.UISoundsConfig;
import dev.imb11.sounds.dynamic.DynamicSoundHelper;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class ScreenHandlerSoundContext implements DynamicSoundContext<AbstractContainerMenu> {

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
    public SoundInstance handleContext(AbstractContainerMenu context, SoundEvent fallback, float pitch, float volume) {
        SoundEvent soundEvent = null;
        try {
            var type = context.getType();
            for (SoundDefinition<MenuType<?>> definition : DynamicSoundHelper.<MenuType<?>>getDefinitions("screens")) {
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
            SoundsClient.LOGGER.debug("Screen of type {} has no declared ScreenHandlerType - ignoring.", context.getClass().getName());
        }

        if (isOpening) {
            fallback = SoundsConfig.get(UISoundsConfig.class).inventoryOpenSoundEffect.getSoundEvent();
        } else {
            fallback = SoundsConfig.get(UISoundsConfig.class).inventoryCloseSoundEffect.getSoundEvent();
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
