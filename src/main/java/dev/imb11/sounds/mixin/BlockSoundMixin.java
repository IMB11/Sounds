package dev.imb11.sounds.mixin;

import dev.imb11.sounds.SoundsClient;
import dev.imb11.sounds.api.config.TagPair;
import dev.imb11.sounds.api.event.SoundDefinitionReplacementEvent;
import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.WorldSoundsConfig;
import dev.imb11.sounds.dynamic.TagPairHelper;
import dev.imb11.sounds.util.BlockAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/*? if >=1.21 {*/
@Mixin(BlockBehaviour.class)
/*?} else {*/
/*@Mixin(Block.class)
@Debug(export = true)
*//*?}*/
public abstract class BlockSoundMixin implements BlockAccessor {
    @Unique
    private @Nullable TagPair sounds$associatedTagPair = null;
    @Unique
    private boolean sounds$hasFetched = false;

    @Override
    public void sounds$prepareTagPair(ResourceLocation value) {
        try {
            if(Minecraft.getInstance() == null) return;
            if(((Object) this) instanceof LiquidBlock) {
                sounds$hasFetched = true;
                return;
            }

            if(SoundsConfig.get(WorldSoundsConfig.class).disableBlocksEntirely || SoundsConfig.get(WorldSoundsConfig.class).ignoredBlocks.contains(value.toString()))
                return;

            @Nullable TagPair pair = TagPairHelper.get(value);
            sounds$associatedTagPair = pair;
            sounds$hasFetched = true;
        } catch (Exception ignored) {
            SoundsClient.LOGGER.warn("Early-load attempt at getting custom sound block group failed. Ignoring for now.");
        }
    }

    @Inject(method = "getSoundType", at = @At("HEAD"), cancellable = true)
    public void $manageCustomSounds(BlockState state, CallbackInfoReturnable<SoundType> cir) {
        try {
            var id = BuiltInRegistries.BLOCK.getKey(state.getBlock());
            if(!sounds$hasFetched) {
                sounds$prepareTagPair(id);
            }

            if(SoundsConfig.get(WorldSoundsConfig.class).disableBlocksEntirely || SoundsConfig.get(WorldSoundsConfig.class).ignoredBlocks.contains(id.toString()))
                return;
        } catch (Exception ignored) {
            SoundsClient.LOGGER.warn("Failed initial block fetch.");
        }

        SoundType group = null;
        if(sounds$associatedTagPair != null) {
            group = sounds$associatedTagPair.getGroup();
        }

        var eventResponse = SoundDefinitionReplacementEvent.fire(group);

        if (eventResponse.response() == SoundDefinitionReplacementEvent.ResponseType.OVERRIDE) {
            var replacement = eventResponse.override();

            if (replacement != null) {
                cir.setReturnValue(replacement);
            }
        }

        // CANCEL - Use vanillas, not ours.
        if (eventResponse.response() == SoundDefinitionReplacementEvent.ResponseType.CANCEL) return;

        // PASS - No override, continue with normal planned sound group.
        if (group != null) {
            cir.setReturnValue(group);
        }
    }
}
