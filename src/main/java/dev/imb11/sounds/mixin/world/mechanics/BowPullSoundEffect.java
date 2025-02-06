package dev.imb11.sounds.mixin.world.mechanics;

import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.WorldSoundsConfig;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BowItem.class)
public class BowPullSoundEffect {
    @Unique
    public SoundInstance sounds$currentBowPullSound;

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;startUsingItem(Lnet/minecraft/world/InteractionHand;)V", shift = At.Shift.AFTER))
    //? if <1.21.2 {
    //public void $start_bow_pull_sound(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<net.minecraft.util.TypedActionResult<ItemStack>> cir) {
    //?} else {
    public void $start_bow_pull_sound(Level world, Player user, InteractionHand hand, CallbackInfoReturnable<net.minecraft.world.InteractionResult> cir) {
    //?}
        if (!world.isClientSide) return;
        this.sounds$currentBowPullSound = SoundsConfig.get(WorldSoundsConfig.class).bowPullSoundEffect.getSoundInstance();
        if (this.sounds$currentBowPullSound != null) {
            SoundsConfig.get(WorldSoundsConfig.class).bowPullSoundEffect.playSound(this.sounds$currentBowPullSound);
        }
    }

    @Inject(method = "releaseUsing", at = @At(value = "HEAD"))
    //? if <1.21.2 {
    //public void $stop_bow_pull_sound(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfo ci) {
    //?} else {
    public void $stop_bow_pull_sound(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks, CallbackInfoReturnable<Boolean> cir) {
    //?}
        if (!world.isClientSide) return;
        if (this.sounds$currentBowPullSound != null) {
            SoundsConfig.get(WorldSoundsConfig.class).bowPullSoundEffect.stopSound(this.sounds$currentBowPullSound);
            this.sounds$currentBowPullSound = null;
        }
    }
}
