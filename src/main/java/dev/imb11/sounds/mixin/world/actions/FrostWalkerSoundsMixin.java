package dev.imb11.sounds.mixin.world.actions;

import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.WorldSoundsConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FrostedIceBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FrostedIceBlock.class)
public class FrostWalkerSoundsMixin {
    @Inject(method = "onPlace", at = @At("TAIL"))
    private void $frost_walker_freeze_sound_effect(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {
    if (world.random.nextInt(4) == 0) {
            SoundsConfig.get(WorldSoundsConfig.class).frostWalkerSoundEffect.playSound();
        }
    }
}
