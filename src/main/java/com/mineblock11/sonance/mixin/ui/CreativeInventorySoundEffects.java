package com.mineblock11.sonance.mixin.ui;

import com.mineblock11.sonance.MixinStatics;
import com.mineblock11.sonance.config.SonanceConfig;
import com.mineblock11.sonance.config.UISoundConfig;
import com.mineblock11.sonance.dynamic.DynamicSoundHelper;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventorySoundEffects extends AbstractInventoryScreen<CreativeInventoryScreen.CreativeScreenHandler> {
    @Shadow
    @Nullable
    private Slot deleteItemSlot;

    @Shadow @Nullable private List<Slot> slots;

    protected CreativeInventorySoundEffects(CreativeInventoryScreen.CreativeScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Inject(method = "search", at = @At("HEAD"), cancellable = false)
    public void $inventory_typing_sound_effect(CallbackInfo ci) {
        UISoundConfig.get().inventoryTypingSoundEffect.playSound();
    }

    @Inject(method = "onMouseClick", at = @At("HEAD"), cancellable = false)
    public void $pre_item_delete_sound_effect(Slot slot, int slotId, int button, SlotActionType actionType, CallbackInfo ci) {
        MixinStatics.CURRENT_SLOT = slot;
        MixinStatics.DELETE_ITEM_SLOT = deleteItemSlot;
    }

    @Unique private double prevDeleteAllTime = 0L;

    @Inject( method = "onMouseClick", at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;clickCreativeStack(Lnet/minecraft/item/ItemStack;I)V"), cancellable = false)
    void $mass_item_delete_sound_effect(Slot slot, int slotId, int button, SlotActionType actionType, CallbackInfo ci)
    {
        double currentTime = GLFW.glfwGetTime();
        double timeElapsed = currentTime - prevDeleteAllTime;
        if (this.slots != null && slots.stream().anyMatch(Slot::hasStack) && timeElapsed >= 0.1)
            UISoundConfig.get().itemDeleteSoundEffect.playSound();
        prevDeleteAllTime = currentTime;
    }

    @Inject(method = "onMouseClick", at = @At(value = "INVOKE", target="Lnet/minecraft/client/gui/screen/ingame/CreativeInventoryScreen$CreativeScreenHandler;setCursorStack(Lnet/minecraft/item/ItemStack;)V", shift = At.Shift.AFTER, ordinal = 4))
    public void $choose_item_sound_effect(Slot slot, int slotId, int button, SlotActionType actionType, CallbackInfo ci) {
        ItemStack stack = this.handler.getCursorStack();
        UISoundConfig.get().itemClickSoundEffect.playDynamicSound(stack, DynamicSoundHelper.BlockSoundType.PLACE);
    }

    @Mixin(CreativeInventoryScreen.CreativeScreenHandler.class)
    public static abstract class CreativeScreenHandlerMixin {
        @Shadow public abstract ItemStack getCursorStack();

        @Inject(method = "setCursorStack", at = @At("HEAD"), cancellable = false)
        public void $item_delete_sound_effect(ItemStack stack, CallbackInfo ci) {
            if (MixinStatics.CURRENT_SLOT == MixinStatics.DELETE_ITEM_SLOT && !getCursorStack().isEmpty())
                UISoundConfig.get().itemDeleteSoundEffect.playDynamicSound(getCursorStack(), DynamicSoundHelper.BlockSoundType.HIT);
        }

        @Unique private double prevTime = 0L;
        @Unique private float prevValue = -69420f;

        @Inject(method = "scrollItems", at = @At("TAIL"))
        public void $inventory_scroll_sound_effect(float position, CallbackInfo ci) {
            double currentTime = GLFW.glfwGetTime();
            double timeElapsed = currentTime - prevTime;

            if (timeElapsed >= 0.05 && prevValue != position) {
                UISoundConfig.get().inventoryScrollSoundEffect.playSound();
                prevTime = currentTime;
                prevValue = position;
            }
        }
    }
}
