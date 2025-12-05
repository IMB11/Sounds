package dev.imb11.sounds.mixin.compat;

import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.WorldSoundsConfig;
import net.mehvahdjukaar.amendments.common.block.DirectionalCakeBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(DirectionalCakeBlock.class)
public class AmendmentsDirectionalCakeMixin {
    @Unique
    private static long lastPlayed = 0;
    @Unique
    private static final long COOLDOWN = 150;
    @Inject(method = "eatSliceD", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;awardStat(Lnet/minecraft/resources/ResourceLocation;)V"))
    private static void eatCake(LevelAccessor level, BlockPos pos, BlockState state, Player player, Direction dir, CallbackInfoReturnable<InteractionResult> cir) {
        // nom nom nom
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastPlayed > COOLDOWN) {
            SoundsConfig.get(WorldSoundsConfig.class).cakeEatSoundEffect.playSound();
            lastPlayed = currentTime;
        }
    }
}
