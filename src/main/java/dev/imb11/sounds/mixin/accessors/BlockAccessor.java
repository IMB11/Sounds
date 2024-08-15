package dev.imb11.sounds.mixin.accessors;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.sound.BlockSoundGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/*? if >=1.21 {*/
@Mixin(AbstractBlock.class)
/*?} else {*/
/*@Mixin(Block.class)
 *//*?}*/
public interface BlockAccessor {
    @Invoker("getSoundGroup")
    BlockSoundGroup invokeGetSoundGroup(BlockState state);
}
