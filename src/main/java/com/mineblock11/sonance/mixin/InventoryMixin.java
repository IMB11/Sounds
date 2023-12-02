package com.mineblock11.sonance.mixin;

import com.mineblock11.sonance.config.SonanceConfig;
import com.mineblock11.sonance.dynamic.DynamicSoundHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Inventory.class)
public class InventoryMixin {
    @Shadow
    @Final
    public Player player;

    @Inject(method = "swapPaint", at = @At("RETURN"), cancellable = false)
    public void $hotbar_scroll_sound_effect(double scrollAmount, CallbackInfo ci) {
        SonanceConfig.get().hotbarScrollSoundEffect.playDynamicSound(this.player.getMainHandItem(), DynamicSoundHelper.BlockSoundType.PLACE);
    }

//    @Inject(method = "dropSelectedItem", at = @At("HEAD"))
//    public void $item_drop_sound_effect(boolean entireStack, CallbackInfoReturnable<ItemStack> cir) {
//        SonanceConfig.get().itemDropSoundEffect.playDynamicSound(this.player.getMainHandStack(), DynamicSoundHelper.BlockSoundType.FALL);
//    }

    @Inject(method = "setPickedItem", at = @At("RETURN"), cancellable = false)
    public void $hotbar_pick_sound_effect(ItemStack stack, CallbackInfo ci) {
        SonanceConfig.get().hotbarPickSoundEffect.playDynamicSound(stack, DynamicSoundHelper.BlockSoundType.STEP);
    }
}
