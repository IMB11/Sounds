package com.mineblock11.sonance.mixin;

import com.mineblock11.sonance.config.SonanceConfig;
import com.mineblock11.sonance.dynamic.DynamicSoundHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.ClickType;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ScreenHandler.class)
public abstract class ScreenHandlerMixin {
    @Shadow
    @Final
    public DefaultedList<Slot> slots;

    @Shadow
    public abstract ItemStack getCursorStack();

    @Shadow public abstract Slot getSlot(int index);

    @Inject( method = "method_34249", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/screen/ScreenHandler;setCursorStack(Lnet/minecraft/item/ItemStack;)V"))
    void $item_click_0_sound_effect(Slot slot, PlayerEntity playerEntity, ItemStack stack, CallbackInfo ci)
    {
        if (!stack.isEmpty())
            SonanceConfig.get().itemClickSoundEffect.playDynamicSound(stack, DynamicSoundHelper.BlockSoundType.PLACE);
    }

    @Inject(method = "internalOnSlotClick", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/screen/ScreenHandler;setCursorStack(Lnet/minecraft/item/ItemStack;)V"))
    void $item_click_1_sound_effect(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci)
    {
        if (slotIndex >= 0)
            SonanceConfig.get().itemClickSoundEffect.playDynamicSound(getSlot(slotIndex).getStack(), DynamicSoundHelper.BlockSoundType.PLACE);
    }

    @Inject(method = "internalOnSlotClick", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/item/ItemStack;increment(I)V"))
    void $item_transfer_0_sound_effects(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci)
    {
        if (slotIndex >= 0)
            SonanceConfig.get().itemClickSoundEffect.playDynamicSound(getSlot(slotIndex).getStack(), DynamicSoundHelper.BlockSoundType.PLACE);
    }
}
