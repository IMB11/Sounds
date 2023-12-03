package com.mineblock11.sonance.mixin.ui;

import com.mineblock11.sonance.SonanceClient;
import com.mineblock11.sonance.config.SonanceConfig;
import com.mineblock11.sonance.config.UISoundConfig;
import com.mineblock11.sonance.dynamic.DynamicSoundHelper;
import com.mineblock11.sonance.sound.context.ItemStackSoundContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ConcurrentModificationException;

@Mixin(ScreenHandler.class)
public abstract class ItemTransferSoundEffects {
    @Shadow public abstract Slot getSlot(int index);
    @Unique
    private double prevTime = 0D;

    @Inject( method = "method_34249", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/screen/ScreenHandler;setCursorStack(Lnet/minecraft/item/ItemStack;)V"))
    void $item_click_0_sound_effect(Slot slot, PlayerEntity playerEntity, ItemStack stack, CallbackInfo ci)
    {
        double currentTime = GLFW.glfwGetTime();
        double timeElapsed = currentTime - prevTime;

        if (timeElapsed >= 0.09D) {
            if (!stack.isEmpty()) {
                try {
                    UISoundConfig.get().itemClickSoundEffect.playDynamicSound(stack, ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.PLACE));
                } catch (ConcurrentModificationException ignored) {
                    SonanceClient.LOGGER.warn("Captured ConcurrentModificationException in ItemTransferSoundEffects mixin.");
                }
            }

            prevTime = currentTime;
        }
    }

    @Inject(method = "internalOnSlotClick", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/screen/ScreenHandler;setCursorStack(Lnet/minecraft/item/ItemStack;)V"))
    void $item_click_1_sound_effect(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci)
    {
        if (slotIndex >= 0)
            UISoundConfig.get().itemClickSoundEffect.playDynamicSound(getSlot(slotIndex).getStack(), ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.PLACE));
    }

    @Inject(method = "internalOnSlotClick", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/item/ItemStack;increment(I)V"))
    void $item_transfer_0_sound_effects(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci)
    {
        double currentTime = GLFW.glfwGetTime();
        double timeElapsed = currentTime - prevTime;

        if (timeElapsed >= 0.09D) {
            if (slotIndex >= 0) {
                try {
                    UISoundConfig.get().itemClickSoundEffect.playDynamicSound(getSlot(slotIndex).getStack(), ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.PLACE));
                } catch (ConcurrentModificationException ignored) {
                    SonanceClient.LOGGER.warn("Captured ConcurrentModificationException in ItemTransferSoundEffects mixin.");
                }
            }

            prevTime = currentTime;
        }
    }
}
