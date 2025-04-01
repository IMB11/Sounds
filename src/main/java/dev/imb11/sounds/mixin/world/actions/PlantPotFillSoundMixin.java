package dev.imb11.sounds.mixin.world.actions;

import com.llamalad7.mixinextras.sugar.Local;
import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.WorldSoundsConfig;
import dev.imb11.sounds.sound.context.ItemStackSoundContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlowerPotBlock.class)
public class PlantPotFillSoundMixin {
    @Shadow @Final private Block potted;

    //? if <1.21.2 {
    /*@Inject(method = "useWithoutItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/InteractionResult;sidedSuccess(Z)Lnet/minecraft/world/InteractionResult;"))
    *///?} else {
    @Inject(method = "useWithoutItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;gameEvent(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/core/Holder;Lnet/minecraft/core/BlockPos;)V"))
    //?}
    public void playPlantPotFillSound(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
        if(world.isClientSide) {
            Block blockOrAirIfNull = this.potted == null ? Blocks.AIR : this.potted;
            SoundsConfig.get(WorldSoundsConfig.class).plantPotFillSoundEffect.playDynamicSound(new ItemStack(blockOrAirIfNull), new ItemStackSoundContext());
        }
    }

    //? if >=1.21.2 {
    @Inject(method = "useItemOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;consume(ILnet/minecraft/world/entity/LivingEntity;)V"))
    public void playPlantPotFillSound(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir) {
        if(level.isClientSide) {
            SoundsConfig.get(WorldSoundsConfig.class).plantPotFillSoundEffect.playDynamicSound(stack, new ItemStackSoundContext());
        }
    }
    //?}
}
