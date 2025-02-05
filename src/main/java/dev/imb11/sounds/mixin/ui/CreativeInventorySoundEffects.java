package dev.imb11.sounds.mixin.ui;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.imb11.sounds.SoundsClient;
import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.UISoundsConfig;
import dev.imb11.sounds.dynamic.DynamicSoundHelper;
import dev.imb11.sounds.sound.context.ItemStackSoundContext;
import dev.imb11.sounds.util.MixinStatics;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(CreativeInventoryScreen.class)
//? if <1.21.2 {
public abstract class CreativeInventorySoundEffects extends net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen<CreativeInventoryScreen.CreativeScreenHandler> {
//?} else {
/*public abstract class CreativeInventorySoundEffects extends net.minecraft.client.gui.screen.ingame.HandledScreen<CreativeInventoryScreen.CreativeScreenHandler> {
*///?}
    @Shadow
    @Nullable
    private Slot deleteItemSlot;

    @Shadow
    @Nullable
    private List<Slot> slots;
    @Unique
    private double prevDeleteAllTime = 0L;

    protected CreativeInventorySoundEffects(CreativeInventoryScreen.CreativeScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Inject(method = "search", at = @At("HEAD"), cancellable = false)
    public void $inventory_typing_sound_effect(CallbackInfo ci) {
        SoundsConfig.get(UISoundsConfig.class).inventoryTypingSoundEffect.playSound();
    }

    @Inject(method = "onMouseClick", at = @At("HEAD"), cancellable = false)
    public void $pre_item_delete_sound_effect(Slot slot, int slotId, int button, SlotActionType actionType, CallbackInfo ci) {
        MixinStatics.CURRENT_SLOT = slot;
        MixinStatics.DELETE_ITEM_SLOT = deleteItemSlot;
    }

    @Definition(id = "actionType", local = @Local(type = SlotActionType.class, argsOnly = true))
    @Definition(id = "CLONE", field = "Lnet/minecraft/screen/slot/SlotActionType;CLONE:Lnet/minecraft/screen/slot/SlotActionType;")
    @Expression("actionType == CLONE")
    @ModifyExpressionValue(method = "onMouseClick", at = @At("MIXINEXTRAS:EXPRESSION"))
    public boolean $creative_tab_item_clone(boolean original) {
        if (original) {
            SoundsConfig.get(UISoundsConfig.class).itemCopySoundEffect.playDynamicSound(MixinStatics.CURRENT_SLOT.getStack(), ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.PLACE));
        }
        return original;
    }

    @Definition(id = "actionType", local = @Local(type = SlotActionType.class, argsOnly = true))
    @Definition(id = "SWAP", field = "Lnet/minecraft/screen/slot/SlotActionType;SWAP:Lnet/minecraft/screen/slot/SlotActionType;")
    @Expression("actionType == SWAP")
    @ModifyExpressionValue(method = "onMouseClick", at = @At("MIXINEXTRAS:EXPRESSION"))
    public boolean $creative_tab_item_swap(boolean original) {
        if (original) {
            SoundsConfig.get(UISoundsConfig.class).itemClickSoundEffect.playDynamicSound(MixinStatics.CURRENT_SLOT.getStack(), ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.PLACE));
        }
        return original;
    }

    @Inject(method = "onMouseClick", at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;clickCreativeStack(Lnet/minecraft/item/ItemStack;I)V"), cancellable = false)
    public void $mass_item_delete_sound_effect(Slot slot, int slotId, int button, SlotActionType actionType, CallbackInfo ci) {
        double currentTime = GLFW.glfwGetTime();
        double timeElapsed = currentTime - prevDeleteAllTime;
        if (this.slots != null && slots.stream().anyMatch(Slot::hasStack) && timeElapsed >= 0.1)
            SoundsConfig.get(UISoundsConfig.class).itemDeleteSoundEffect.playSound();
        prevDeleteAllTime = currentTime;
    }

    @Inject(method = "onMouseClick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/CreativeInventoryScreen$CreativeScreenHandler;setCursorStack(Lnet/minecraft/item/ItemStack;)V", shift = At.Shift.AFTER, ordinal = 4))
    public void $choose_item_sound_effect(Slot slot, int slotId, int button, SlotActionType actionType, CallbackInfo ci) {
        ItemStack stack = this.handler.getCursorStack();
        SoundsConfig.get(UISoundsConfig.class).itemClickSoundEffect.playDynamicSound(stack, ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.PLACE));
    }
}
