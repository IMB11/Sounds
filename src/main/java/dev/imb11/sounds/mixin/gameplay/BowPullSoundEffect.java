package dev.imb11.sounds.mixin.gameplay;

import dev.imb11.sounds.config.GameplaySoundConfig;
import net.minecraft.client.MinecraftClient;
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
        this.currentBowPullSound = GameplaySoundConfig.get().bowPullSoundEffect.getSoundInstance();
        if (this.currentBowPullSound != null) {
            MinecraftClient.getInstance().getSoundManager().play(this.currentBowPullSound);
        }
    }

    @Inject(method = "onStoppedUsing", at = @At(value = "HEAD"))
    public void $stop_bow_pull_sound(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfo ci) {
        if(this.currentBowPullSound != null) {
            MinecraftClient.getInstance().getSoundManager().stop(this.currentBowPullSound);
            this.currentBowPullSound = null;
        }
    }
}
