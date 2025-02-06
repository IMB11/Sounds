package dev.imb11.sounds.mixin.ui;

import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.UISoundsConfig;
import dev.imb11.sounds.dynamic.DynamicSoundHelper;
import dev.imb11.sounds.sound.context.ItemStackSoundContext;
import dev.imb11.sounds.util.MixinStatics;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeModeInventoryScreen.ItemPickerMenu.class)
public abstract class CreativeScreenHandlerMixin {

    @Unique
    private boolean sounds$tempSkip = false;
    @Unique
    private int sounds$tempSkipCounter = 0;

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/CreativeModeInventoryScreen$ItemPickerMenu;scrollTo(F)V"), cancellable = false)
    public void $constructor_sounds_capture(Player player, CallbackInfo ci) {
        sounds$tempSkip = true;
    }

    @Unique
    private double sounds$prevTime = 0L;
    @Unique
    private float sounds$prevValue = -69420f;

    @Shadow
    public abstract ItemStack getCarried();

    @Inject(method = "setCarried", at = @At("HEAD"), cancellable = false)
    public void $item_delete_sound_effect(ItemStack stack, CallbackInfo ci) {
        if (MixinStatics.CURRENT_SLOT == MixinStatics.DELETE_ITEM_SLOT && !getCarried().isEmpty())
            SoundsConfig.get(UISoundsConfig.class).itemDeleteSoundEffect.playDynamicSound(getCarried(), ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.HIT));
    }

    @Inject(method = "scrollTo", at = @At("TAIL"))
    public void $inventory_scroll_sound_effect(float position, CallbackInfo ci) {
        // Don't do anything for 0.1s after screen opened.
        if (sounds$tempSkip) {
            sounds$tempSkipCounter++;
            if (sounds$tempSkipCounter == 2) {
                sounds$tempSkipCounter = 0;
                sounds$tempSkip = false;
            }
            return;
        }

        double currentTime = GLFW.glfwGetTime();
        double timeElapsed = currentTime - sounds$prevTime;

        if (timeElapsed >= 0.05 && sounds$prevValue != position) {
            SoundsConfig.get(UISoundsConfig.class).inventoryScrollSoundEffect.playSound();
            sounds$prevTime = currentTime;
            sounds$prevValue = position;
        }
    }
}
