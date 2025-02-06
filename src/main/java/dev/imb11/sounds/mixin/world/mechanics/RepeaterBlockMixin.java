package dev.imb11.sounds.mixin.world.mechanics;

import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.WorldSoundsConfig;
import dev.imb11.sounds.sound.context.RepeaterSoundContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RepeaterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RepeaterBlock.class)
public class RepeaterBlockMixin {
    @Inject(method = "onUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z", shift = At.Shift.AFTER))
    /*? if =1.20.1 {*/
    /*public void $repeater_use_sound_effect(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
    *//*?} else {*/
    public void $repeater_use_sound_effect(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
    /*?}*/
        if (world.isClientSide) {
            int delayLevel = state.getValue(RepeaterBlock.DELAY);
            SoundsConfig.get(WorldSoundsConfig.class).repeaterUseSoundEffect.playDynamicSound(delayLevel, RepeaterSoundContext.of());
        }
    }
}
