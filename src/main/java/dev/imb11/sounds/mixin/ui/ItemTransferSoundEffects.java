package dev.imb11.sounds.mixin.ui;

import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.UISoundsConfig;
import dev.imb11.sounds.dynamic.DynamicSoundHelper;
import dev.imb11.sounds.sound.context.ItemStackSoundContext;
import dev.imb11.sounds.util.MixinStatics;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenHandler.class)
public abstract class ItemTransferSoundEffects {
    @Shadow
    public abstract ItemStack getCursorStack();

    @Shadow
    public abstract Slot getSlot(int index);

    @Unique
    private SlotActionType previousAction = null;

    @Inject(method = "internalOnSlotClick", at = @At("HEAD"))
    void $item_transfer_sound_effects(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {
        if (!(player instanceof ClientPlayerEntity)) return;
        if (slotIndex < 0) return;

        ItemStack itemStack = getSlot(slotIndex).getStack();
        if (actionType == SlotActionType.PICKUP) {
            if (itemStack.isEmpty()) {
                ItemStack cursorStack = this.getCursorStack();
                SoundsConfig.get(UISoundsConfig.class).itemClickSoundEffect.playDynamicSound(cursorStack, ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.PLACE));
            } else {
                SoundsConfig.get(UISoundsConfig.class).itemClickSoundEffect.playDynamicSound(itemStack, ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.PLACE));
            }
        } else if (actionType == SlotActionType.PICKUP_ALL) {
            ItemStack cursorStack = this.getCursorStack();
            SoundsConfig.get(UISoundsConfig.class).itemClickSoundEffect.playDynamicSound(cursorStack, ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.PLACE));
        } else if (actionType == SlotActionType.SWAP) {
            if (itemStack.isEmpty()) {
                ItemStack buttonStack = player.getInventory().getStack(button);
                SoundsConfig.get(UISoundsConfig.class).itemClickSoundEffect.playDynamicSound(buttonStack, ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.PLACE));
            } else {
                SoundsConfig.get(UISoundsConfig.class).itemClickSoundEffect.playDynamicSound(itemStack, ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.PLACE));
            }
        } else if (actionType == SlotActionType.QUICK_MOVE) {
            if (itemStack.isEmpty()) {
                ItemStack cursorStack = this.getCursorStack();
                SoundsConfig.get(UISoundsConfig.class).itemClickSoundEffect.playDynamicSound(cursorStack, ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.PLACE));
            } else {
                SoundsConfig.get(UISoundsConfig.class).itemClickSoundEffect.playDynamicSound(itemStack, ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.PLACE));
            }
        } else if (actionType == SlotActionType.CLONE) {
            SoundsConfig.get(UISoundsConfig.class).itemCopySoundEffect.playDynamicSound(itemStack, ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.PLACE));
        }

        MixinStatics.previousAction = actionType;
    }
}
