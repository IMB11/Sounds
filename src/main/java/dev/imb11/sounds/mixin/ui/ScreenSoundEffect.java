package dev.imb11.sounds.mixin.ui;

import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.UISoundsConfig;
import dev.imb11.sounds.sound.context.ScreenHandlerSoundContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.sounds.SoundEvents;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class ScreenSoundEffect {
    @Shadow
    @Nullable
    public Screen currentScreen;

    @Inject(method = "setScreen", at = @At("HEAD"), cancellable = false)
    public void $open_close_inventory_sound_effect(Screen screen, CallbackInfo ci) {
        if (screen == null && (currentScreen instanceof BookViewScreen || currentScreen instanceof BookEditScreen)) {
            SoundsConfig.get(UISoundsConfig.class).inventoryCloseSoundEffect.playDynamicSound(SoundEvents.VILLAGER_WORK_LIBRARIAN);
        } else if (currentScreen != screen && (screen instanceof BookViewScreen || screen instanceof BookEditScreen)) {
            SoundsConfig.get(UISoundsConfig.class).inventoryOpenSoundEffect.playDynamicSound(SoundEvents.VILLAGER_WORK_LIBRARIAN);
        } else if (screen == null && currentScreen instanceof AbstractContainerScreen<?> handledScreen)
            SoundsConfig.get(UISoundsConfig.class).inventoryCloseSoundEffect.playDynamicSound(handledScreen.getMenu(), ScreenHandlerSoundContext.of(false));
        else if (currentScreen != screen && screen instanceof AbstractContainerScreen<?> handledScreen)
            SoundsConfig.get(UISoundsConfig.class).inventoryOpenSoundEffect.playDynamicSound(handledScreen.getMenu(), ScreenHandlerSoundContext.of(true));
    }
}
