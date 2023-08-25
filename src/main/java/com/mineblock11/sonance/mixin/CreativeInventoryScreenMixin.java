package com.mineblock11.sonance.mixin;

import com.mineblock11.sonance.MixinStatics;
import com.mineblock11.sonance.config.SonanceConfig;
import com.mineblock11.sonance.dynamic.DynamicSoundHelper;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(CreativeInventoryScreen.class)
public class CreativeInventoryScreenMixin {
    @Shadow
    @Nullable
    private Slot deleteItemSlot;

    @Shadow @Nullable private List<Slot> slots;

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
            SonanceConfig.get().itemDeleteSoundEffect.playSound();
        prevDeleteAllTime = currentTime;
    }

    @Mixin(CreativeInventoryScreen.CreativeScreenHandler.class)
    public static abstract class CreativeScreenHandlerMixin {
        @Shadow public abstract ItemStack getCursorStack();

        @Inject(method = "setCursorStack", at = @At("HEAD"), cancellable = false)
        public void $item_delete_sound_effect(ItemStack stack, CallbackInfo ci) {
            if (MixinStatics.CURRENT_SLOT == MixinStatics.DELETE_ITEM_SLOT && !getCursorStack().isEmpty())
                SonanceConfig.get().itemDeleteSoundEffect.playDynamicSound(getCursorStack(), DynamicSoundHelper.BlockSoundType.HIT);
        }

        @Unique private double prevTime = 0L;
        @Unique private float prevValue = -69420f;

        @Inject(method = "scrollItems", at = @At("TAIL"))
        public void scrollItems(float position, CallbackInfo ci) {
            double currentTime = GLFW.glfwGetTime();
            double timeElapsed = currentTime - prevTime;

            if (timeElapsed >= 0.05 && prevValue != position) {
                SonanceConfig.get().inventoryScrollSoundEffect.playSound();
                prevTime = currentTime;
                prevValue = position;
            }
        }
    }
}
