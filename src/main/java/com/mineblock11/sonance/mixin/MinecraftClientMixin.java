package com.mineblock11.sonance.mixin;

import com.mineblock11.sonance.config.SonanceConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow
    @Nullable
    public Screen currentScreen;

    @Inject(method = "setScreen", at = @At("HEAD"), cancellable = false)
    public void $open_close_inventory_sound_effect(Screen screen, CallbackInfo ci) {
        if (screen == null && currentScreen instanceof HandledScreen<?>)
            SonanceConfig.get().inventoryCloseSoundEffect.playSound();
        else if (currentScreen != screen && screen instanceof HandledScreen<?>)
            SonanceConfig.get().inventoryOpenSoundEffect.playSound();
    }
}
