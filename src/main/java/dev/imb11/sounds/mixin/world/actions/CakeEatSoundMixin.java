package dev.imb11.sounds.mixin.world.actions;

import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.WorldSoundsConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CakeBlock.class)
public class CakeEatSoundMixin {
    @Unique
    private static long lastPlayed = 0;
    @Unique
    private static final long COOLDOWN = 150;
    @Inject(method = "eat", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;awardStat(Lnet/minecraft/resources/ResourceLocation;)V"))
    private static void eatCake(LevelAccessor world, BlockPos pos, BlockState state, Player player, CallbackInfoReturnable<InteractionResult> cir) {
        // nom nom nom
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastPlayed > COOLDOWN) {
            SoundsConfig.get(WorldSoundsConfig.class).cakeEatSoundEffect.playSound();
            lastPlayed = currentTime;
        }
    }
}
