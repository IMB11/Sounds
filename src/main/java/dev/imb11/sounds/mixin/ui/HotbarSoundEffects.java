package dev.imb11.sounds.mixin.ui;

import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.UISoundsConfig;
import dev.imb11.sounds.dynamic.DynamicSoundHelper;
import dev.imb11.sounds.sound.context.ItemStackSoundContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public class HotbarSoundEffects {
    @Shadow
    @Final
    public PlayerEntity player;

    //? if <1.21.2 {
    /*@Inject(method = "scrollInHotbar", at = @At("RETURN"), cancellable = false)
    public void $hotbar_scroll_sound_effect(double scrollAmount, CallbackInfo ci) {
        SoundsConfig.get(UISoundsConfig.class).hotbarScrollSoundEffect.playDynamicSound(this.player.getMainHandStack(), ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.PLACE));
    }*/
    //?} else {
    @Inject(method = "setSelectedSlot", at = @At("RETURN"), cancellable = false)
    public void $hotbar_scroll_sound_effect(int slot, CallbackInfo ci) {
        SoundsConfig.get(UISoundsConfig.class).hotbarScrollSoundEffect.playDynamicSound(this.player.getMainHandStack(), ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.PLACE));
    }
    //?}

//    @Inject(method = "dropSelectedItem", at = @At("HEAD"))
//    public void $item_drop_sound_effect(boolean entireStack, CallbackInfoReturnable<ItemStack> cir) {
//        SoundsConfig.get(UISoundsConfig.class).itemDropSoundEffect.playDynamicSound(this.player.getMainHandStack(), DynamicSoundHelper.BlockSoundType.FALL);
//    }

    @Inject(method = "addPickBlock", at = @At("RETURN"), cancellable = false)
    public void $hotbar_pick_sound_effect(ItemStack stack, CallbackInfo ci) {
        SoundsConfig.get(UISoundsConfig.class).hotbarPickSoundEffect.playDynamicSound(stack, ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.STEP));
    }
}
