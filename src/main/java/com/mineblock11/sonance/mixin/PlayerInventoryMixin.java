package com.mineblock11.sonance.mixin;

import com.mineblock11.sonance.config.SonanceConfig;
import com.mineblock11.sonance.dynamic.DynamicSoundHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {
    @Shadow @Final public PlayerEntity player;

    @Inject(method = "scrollInHotbar", at = @At("RETURN"), cancellable = false)
    public void $hotbar_scroll_sound_effect(double scrollAmount, CallbackInfo ci) {
        if(SonanceConfig.get().useDynamicHotbar) {
            SonanceConfig.get().hotbarScrollSoundEffect.forceSound(DynamicSoundHelper.getItemSound(this.player.getMainHandStack(), SonanceConfig.get().hotbarScrollSoundEffect.fetchSoundEvent()));
        } else {
            SonanceConfig.get().hotbarScrollSoundEffect.playSound();
        }

    }
}
