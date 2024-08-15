package dev.imb11.sounds.mixin;

import dev.imb11.sounds.SoundsClient;
import dev.imb11.sounds.api.config.TagPair;
import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.WorldSoundsConfig;
import dev.imb11.sounds.dynamic.TagPairHelper;
import dev.imb11.sounds.util.BlockAccessor;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.registry.Registries;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/*? if >=1.21 {*/
@Mixin(AbstractBlock.class)
/*?} else {*/
/*@Mixin(Block.class)
*//*?}*/
public abstract class BlockSoundMixin implements BlockAccessor {
    @Unique
    private @Nullable TagPair associatedTagPair = null;
    @Unique
    private boolean hasFetched = false;

    @Override
    public void sounds$prepareTagPair(Identifier value) {
        try {
            if(MinecraftClient.getInstance() == null) return;
            if(((Object) this) instanceof FluidBlock) {
                hasFetched = true;
                return;
            }

            if(SoundsConfig.get(WorldSoundsConfig.class).disableBlocksEntirely)
                return;

            @Nullable TagPair pair = TagPairHelper.get(value);
            associatedTagPair = pair;
            hasFetched = true;
        } catch (Exception ignored) {
            SoundsClient.LOGGER.warn("Early-load attempt at getting custom sound block group failed. Ignoring for now.");
        }
    }

    @Inject(method = "getSoundGroup", at = @At("HEAD"), cancellable = true)
    public void $manageCustomSounds(BlockState state, CallbackInfoReturnable<BlockSoundGroup> cir) {
//        if(MinecraftClient.getInstance().world == null) return;
        try {
            if(!hasFetched) {
                sounds$prepareTagPair(Registries.BLOCK.getId(state.getBlock()));
            }

            if(SoundsConfig.get(WorldSoundsConfig.class).disableBlocksEntirely)
                return;
        } catch (Exception ignored) {
            SoundsClient.LOGGER.warn("Failed initial block fetch.");
        }

        if(associatedTagPair != null) {
            cir.setReturnValue(associatedTagPair.getGroup());
        }
    }
}
