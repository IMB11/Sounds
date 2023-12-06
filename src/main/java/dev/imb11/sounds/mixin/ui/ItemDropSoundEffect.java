package dev.imb11.sounds.mixin.ui;

import dev.imb11.sounds.SoundsClient;
import dev.imb11.sounds.config.UISoundConfig;
import dev.imb11.sounds.dynamic.DynamicSoundHelper;
import dev.imb11.sounds.sound.context.ItemStackSoundContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ConcurrentModificationException;

@Mixin(PlayerEntity.class)
public class ItemDropSoundEffect {
    @Unique
    private double prevTime = 0D;

    @Inject(method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;", at = @At("HEAD"))
    public void $drop_item_sound_effect(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
        double currentTime = GLFW.glfwGetTime();
        double timeElapsed = currentTime - prevTime;

        if (timeElapsed >= 0.09D) {
            try {
                UISoundConfig.get().itemDropSoundEffect.playDynamicSound(stack, ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.FALL));
            } catch (ConcurrentModificationException ignored) {
                SoundsClient.LOGGER.warn("Captured ConcurrentModificationException in ItemDropSoundEffect mixin.");
            }

            prevTime = currentTime;
        }
    }
}
