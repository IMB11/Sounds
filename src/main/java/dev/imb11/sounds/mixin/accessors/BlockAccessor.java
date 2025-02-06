package dev.imb11.sounds.mixin.accessors;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BlockBehaviour.class)
public interface BlockAccessor {
    @Invoker("getSoundType")
    SoundType invokeGetSoundType(BlockState state);
}
