package dev.imb11.sounds.mixin.world.mechanics;

import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.WorldSoundsConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class SwordSwingSoundEffect {
    @Shadow
    public int swingTime;
    @Unique
    public SoundInstance sounds$currentSwordSwooshSound;
    @Unique
    private static long lastPlayed = 0;
    @Unique
    private static final long COOLDOWN = 250;

    @Inject(method = "swing(Lnet/minecraft/world/InteractionHand;Z)V", at = @At(value = "HEAD"))
    public void $start_sword_swoosh_sound(InteractionHand interactionHand, boolean bl, CallbackInfo ci) {
        long currentTime = System.currentTimeMillis();
        var entity =  (LivingEntity) (Object) this;
        if (!entity.level().isClientSide()) return;
        if (entity instanceof LocalPlayer localPlayer && Minecraft.getInstance().player != null && localPlayer.getUUID().equals(Minecraft.getInstance().player.getUUID())) {
            if (entity.getItemInHand(interactionHand).is(ItemTags.SWORDS) && (currentTime - lastPlayed > COOLDOWN)) {
                this.sounds$currentSwordSwooshSound = SoundsConfig.get(WorldSoundsConfig.class).swordSwooshSoundEffect.getSoundInstance();
                if (this.sounds$currentSwordSwooshSound != null) {
                    SoundsConfig.get(WorldSoundsConfig.class).swordSwooshSoundEffect.playSound(this.sounds$currentSwordSwooshSound);
                    lastPlayed = currentTime;
                }
            }
        }
    }

}
