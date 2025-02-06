package dev.imb11.sounds.mixin.world.actions;

import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.WorldSoundsConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/*? if >=1.21 {*/
@Mixin(net.minecraft.world.level.block.FrostedIceBlock.class)
/*?} else {*/
/*@Mixin(net.minecraft.enchantment.FrostWalkerEnchantment.class)
*//*?}*/
public class FrostWalkerSoundsMixin {
    /*? if >=1.21 {*/
    @Inject(method = "onBlockAdded", at = @At("TAIL"))
    private void $frost_walker_freeze_sound_effect(net.minecraft.world.level.block.state.BlockState state, Level world, BlockPos pos, net.minecraft.world.level.block.state.BlockState oldState, boolean notify, CallbackInfo ci) {
    /*?} else {*/
    /*@Inject(method = "freezeWater", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z", shift = At.Shift.AFTER))
    private static void $frost_walker_freeze_sound_effect(net.minecraft.entity.LivingEntity entity, World world, BlockPos blockPos, int level, CallbackInfo ci) {
    *//*?}*/
    if (world.random.nextInt(4) == 0) {
            SoundsConfig.get(WorldSoundsConfig.class).frostWalkerSoundEffect.playSound();
        }
    }
}
