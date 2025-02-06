package dev.imb11.sounds.mixin.ui;

import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.UISoundsConfig;
import dev.imb11.sounds.dynamic.DynamicSoundHelper;
import dev.imb11.sounds.sound.context.ItemStackSoundContext;
import dev.imb11.sounds.util.MixinStatics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerMenu.class)
public abstract class ItemTransferSoundEffects {
    @Shadow
    public abstract Slot getSlot(int index);

    @Shadow public abstract ItemStack getCarried();

    @Inject(method = "doClick", at = @At("HEAD"))
    void $item_transfer_sound_effects(int slotIndex, int button, ClickType actionType, Player player, CallbackInfo ci) {
        if (!(player instanceof LocalPlayer)) return;
        if (slotIndex < 0) return;

        ItemStack itemStack = getSlot(slotIndex).getItem();
        if (actionType == ClickType.PICKUP) {
            if (itemStack.isEmpty()) {
                ItemStack cursorStack = this.getCarried();
                SoundsConfig.get(UISoundsConfig.class).itemClickSoundEffect.playDynamicSound(cursorStack, ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.PLACE));
            } else {
                SoundsConfig.get(UISoundsConfig.class).itemClickSoundEffect.playDynamicSound(itemStack, ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.PLACE));
            }
        } else if (actionType == ClickType.PICKUP_ALL) {
            ItemStack cursorStack = this.getCarried();
            SoundsConfig.get(UISoundsConfig.class).itemClickSoundEffect.playDynamicSound(cursorStack, ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.PLACE));
        } else if (actionType == ClickType.SWAP) {
            if (itemStack.isEmpty()) {
                ItemStack buttonStack = player.getInventory().getItem(button);
                SoundsConfig.get(UISoundsConfig.class).itemClickSoundEffect.playDynamicSound(buttonStack, ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.PLACE));
            } else {
                SoundsConfig.get(UISoundsConfig.class).itemClickSoundEffect.playDynamicSound(itemStack, ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.PLACE));
            }
        } else if (actionType == ClickType.QUICK_MOVE) {
            if (itemStack.isEmpty()) {
                ItemStack cursorStack = this.getCarried();
                SoundsConfig.get(UISoundsConfig.class).itemClickSoundEffect.playDynamicSound(cursorStack, ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.PLACE));
            } else {
                SoundsConfig.get(UISoundsConfig.class).itemClickSoundEffect.playDynamicSound(itemStack, ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.PLACE));
            }
        } else if (actionType == ClickType.CLONE) {
            SoundsConfig.get(UISoundsConfig.class).itemCopySoundEffect.playDynamicSound(itemStack, ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.PLACE));
        }

        MixinStatics.previousAction = actionType;
    }
}
