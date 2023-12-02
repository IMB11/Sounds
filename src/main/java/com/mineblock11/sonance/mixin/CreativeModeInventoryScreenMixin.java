package com.mineblock11.sonance.mixin;

import com.mineblock11.sonance.MixinStatics;
import com.mineblock11.sonance.config.SonanceConfig;
import com.mineblock11.sonance.dynamic.DynamicSoundHelper;
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
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

@Mixin(CreativeModeInventoryScreen.class)
public class CreativeModeInventoryScreenMixin {
    @Shadow
    @Nullable
    private Slot destroyItemSlot;

    @Shadow @Nullable private List<Slot> originalSlots;

    @Inject(method = "slotClicked", at = @At("HEAD"), cancellable = false)
    public void $pre_item_delete_sound_effect(Slot slot, int slotId, int button, ClickType actionType, CallbackInfo ci) {
        MixinStatics.CURRENT_SLOT = slot;
        MixinStatics.DELETE_ITEM_SLOT = destroyItemSlot;
    }

    @Unique private double prevDeleteAllTime = 0L;

    @Inject( method = "slotClicked", at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;handleCreativeModeItemAdd(Lnet/minecraft/world/item/ItemStack;I)V"), cancellable = false)
    void $mass_item_delete_sound_effect(Slot slot, int slotId, int button, ClickType actionType, CallbackInfo ci)
    {
        double currentTime = GLFW.glfwGetTime();
        double timeElapsed = currentTime - prevDeleteAllTime;
        if (this.originalSlots != null && originalSlots.stream().anyMatch(Slot::hasItem) && timeElapsed >= 0.1)
            SonanceConfig.get().itemDeleteSoundEffect.playSound();
        prevDeleteAllTime = currentTime;
    }

    @Mixin(CreativeModeInventoryScreen.ItemPickerMenu.class)
    public static abstract class CreativeScreenHandlerMixin {
        @Shadow public abstract ItemStack getCarried();

        @Inject(method = "setCarried", at = @At("HEAD"))
        public void $item_delete_sound_effect(ItemStack stack, CallbackInfo ci) {
            if (MixinStatics.CURRENT_SLOT == MixinStatics.DELETE_ITEM_SLOT && !getCarried().isEmpty())
                SonanceConfig.get().itemDeleteSoundEffect.playDynamicSound(getCarried(), DynamicSoundHelper.BlockSoundType.HIT);
        }

        @Unique private double prevTime = 0L;
        @Unique private float prevValue = -69420f;

        @Inject(method = "scrollTo", at = @At("TAIL"))
        public void $inventory_scroll_sound_effect(float position, CallbackInfo ci) {
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
