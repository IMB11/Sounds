package dev.imb11.sounds.mixin;

import dev.imb11.sounds.SoundsClient;
import dev.imb11.sounds.api.config.TagPair;
import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.WorldSoundsConfig;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.sound.BlockSoundGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/*? if >1.20.4 {*/
@Mixin(AbstractBlock.class)
/*?} else {*/
/*@Mixin(Block.class)
*//*?}*/
public abstract class BlockSoundMixin {
    @Inject(method = "getSoundGroup", at = @At("HEAD"), cancellable = true)
    public void $manageCustomSounds(BlockState state, CallbackInfoReturnable<BlockSoundGroup> cir) {
        if(state.getBlock() instanceof FluidBlock) return;

        if(SoundsConfig.get(WorldSoundsConfig.class).disableBlocksEntirely)
            return;

        try {
            TagPair.handleTagPair(state.getBlock(), cir);
        } catch (Exception ignored) {
            SoundsClient.LOGGER.warn("Early-load attempt at getting custom sound block group failed. Ignoring for now.");
        }
    }
}
