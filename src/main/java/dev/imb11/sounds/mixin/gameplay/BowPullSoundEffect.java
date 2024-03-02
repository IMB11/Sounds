package dev.imb11.sounds.mixin.gameplay;

import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.WorldSoundsConfig;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BowItem.class)
public class BowPullSoundEffect {
    @Unique
    public SoundInstance currentBowPullSound;

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setCurrentHand(Lnet/minecraft/util/Hand;)V", shift = At.Shift.AFTER))
    public void $start_bow_pull_sound(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if (!world.isClient) return;
        this.currentBowPullSound = SoundsConfig.get(WorldSoundsConfig.class).bowPullSoundEffect.getSoundInstance();
        if (this.currentBowPullSound != null) {
            SoundsConfig.get(WorldSoundsConfig.class).bowPullSoundEffect.playSound(this.currentBowPullSound);
        }
    }

    @Inject(method = "onStoppedUsing", at = @At(value = "HEAD"))
    public void $stop_bow_pull_sound(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfo ci) {
        if (!world.isClient) return;
        if (this.currentBowPullSound != null) {
            SoundsConfig.get(WorldSoundsConfig.class).bowPullSoundEffect.stopSound(this.currentBowPullSound);
            this.currentBowPullSound = null;
        }
    }
}
