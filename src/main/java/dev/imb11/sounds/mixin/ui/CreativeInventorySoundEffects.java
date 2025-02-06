package dev.imb11.sounds.mixin.ui;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.UISoundsConfig;
import dev.imb11.sounds.dynamic.DynamicSoundHelper;
import dev.imb11.sounds.sound.context.ItemStackSoundContext;
import dev.imb11.sounds.util.MixinStatics;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeInventorySoundEffects extends net.minecraft.client.gui.screens.inventory.AbstractContainerScreen<CreativeModeInventoryScreen.ItemPickerMenu> {
    @Shadow
    @Nullable
    private Slot destroyItemSlot;

    @Shadow
    @Nullable
    private List<Slot> originalSlots;

    @Unique
    private double sounds$prevDeleteAllTime = 0L;

    protected CreativeInventorySoundEffects(CreativeModeInventoryScreen.ItemPickerMenu screenHandler, Inventory playerInventory, Component text) {
        super(screenHandler, playerInventory, text);
    }

    @Inject(method = "refreshSearchResults", at = @At("HEAD"), cancellable = false)
    public void $inventory_typing_sound_effect(CallbackInfo ci) {
        SoundsConfig.get(UISoundsConfig.class).inventoryTypingSoundEffect.playSound();
    }

    @Inject(method = "slotClicked", at = @At("HEAD"), cancellable = false)
    public void $pre_item_delete_sound_effect(Slot slot, int slotId, int button, ClickType actionType, CallbackInfo ci) {
        MixinStatics.CURRENT_SLOT = slot;
        MixinStatics.DELETE_ITEM_SLOT = destroyItemSlot;
    }

    @Definition(id = "actionType", local = @Local(type = ClickType.class, argsOnly = true))
    @Definition(id = "CLONE", field = "Lnet/minecraft/world/inventory/ClickType;CLONE:Lnet/minecraft/world/inventory/ClickType;")
    @Expression("actionType == CLONE")
    @ModifyExpressionValue(method = "slotClicked", at = @At("MIXINEXTRAS:EXPRESSION"))
    public boolean $creative_tab_item_clone(boolean original) {
        if (original) {
            SoundsConfig.get(UISoundsConfig.class).itemCopySoundEffect.playDynamicSound(MixinStatics.CURRENT_SLOT.getItem(), ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.PLACE));
        }
        return original;
    }

    @Definition(id = "actionType", local = @Local(type = ClickType.class, argsOnly = true))
    @Definition(id = "SWAP", field = "Lnet/minecraft/world/inventory/ClickType;SWAP:Lnet/minecraft/world/inventory/ClickType;")
    @Expression("actionType == SWAP")
    @ModifyExpressionValue(method = "slotClicked", at = @At("MIXINEXTRAS:EXPRESSION"))
    public boolean $creative_tab_item_swap(boolean original) {
        if (original) {
            SoundsConfig.get(UISoundsConfig.class).itemClickSoundEffect.playDynamicSound(MixinStatics.CURRENT_SLOT.getItem(), ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.PLACE));
        }
        return original;
    }

    @Inject(method = "slotClicked", at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;handleCreativeModeItemAdd(Lnet/minecraft/world/item/ItemStack;I)V"), cancellable = false)
    public void $mass_item_delete_sound_effect(Slot slot, int slotId, int button, ClickType actionType, CallbackInfo ci) {
        double currentTime = GLFW.glfwGetTime();
        double timeElapsed = currentTime - sounds$prevDeleteAllTime;
        if (this.originalSlots != null && originalSlots.stream().anyMatch(Slot::hasItem) && timeElapsed >= 0.1)
            SoundsConfig.get(UISoundsConfig.class).itemDeleteSoundEffect.playSound();
        sounds$prevDeleteAllTime = currentTime;
    }

    @Inject(method = "slotClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/CreativeModeInventoryScreen$ItemPickerMenu;setCarried(Lnet/minecraft/world/item/ItemStack;)V", shift = At.Shift.AFTER, ordinal = 4))
    public void $choose_item_sound_effect(Slot slot, int slotId, int button, ClickType actionType, CallbackInfo ci) {
        ItemStack stack = this.menu.getCarried();
        SoundsConfig.get(UISoundsConfig.class).itemClickSoundEffect.playDynamicSound(stack, ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.PLACE));
    }
}
