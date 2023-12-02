package com.mineblock11.sonance.mixin.ui;

import com.mineblock11.sonance.config.SonanceConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.BookEditScreen;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.sound.SoundEvents;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class ScreenSoundEffect {
    @Shadow
    @Nullable
    public Screen currentScreen;

    @Inject(method = "setScreen", at = @At("HEAD"), cancellable = false)
    public void $open_close_inventory_sound_effect(Screen screen, CallbackInfo ci) {
        if (screen == null && (currentScreen instanceof BookScreen || currentScreen instanceof BookEditScreen)) {
            SonanceConfig.get().inventoryCloseSoundEffect.playDynamicSound(SoundEvents.ENTITY_VILLAGER_WORK_LIBRARIAN);
        } else if (currentScreen != screen && (screen instanceof BookScreen || screen instanceof BookEditScreen)) {
            SonanceConfig.get().inventoryOpenSoundEffect.playDynamicSound(SoundEvents.ENTITY_VILLAGER_WORK_LIBRARIAN);
        } else if (screen == null && currentScreen instanceof HandledScreen<?> handledScreen)
            SonanceConfig.get().inventoryCloseSoundEffect.playDynamicSound(handledScreen.getScreenHandler(), false);
        else if (currentScreen != screen && screen instanceof HandledScreen<?> handledScreen)
            SonanceConfig.get().inventoryOpenSoundEffect.playDynamicSound(handledScreen.getScreenHandler(), true);
    }
}
