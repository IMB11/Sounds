package com.mineblock11.sonance.mixin;

import com.mineblock11.sonance.config.SonanceConfig;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {
    @Inject(method = "scrollInHotbar", at = @At("HEAD"), cancellable = false)
    public void $hotbar_scroll_sound_effect(double scrollAmount, CallbackInfo ci) {
        // if dynamic, play dynamic item sound.
        SonanceConfig.get().hotbarScrollSoundEffect.playSound();
    }
}
