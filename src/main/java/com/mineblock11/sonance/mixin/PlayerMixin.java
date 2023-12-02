package com.mineblock11.sonance.mixin;

import com.mineblock11.sonance.config.SonanceConfig;
import com.mineblock11.sonance.dynamic.DynamicSoundHelper;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {
    @Unique
    private double prevTime = 0D;

    @Inject(method = "drop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity;", at = @At("HEAD"))
    public void $drop_item_sound_effect(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
        double currentTime = GLFW.glfwGetTime();
        double timeElapsed = currentTime - prevTime;

        if (timeElapsed >= 0.085D) {
            SonanceConfig.get().itemDropSoundEffect.playDynamicSound(stack, DynamicSoundHelper.BlockSoundType.FALL);
            prevTime = currentTime;
        }
    }
}
