package dev.imb11.sounds.mixin.world.actions;

import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.WorldSoundsConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderPearlEntity.class)
public abstract class EnderpearlVarietyMixin extends ThrownItemEntity {
    public EnderpearlVarietyMixin(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    /*? if <1.21 {*/
    /*@Inject(method = "onCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;requestTeleport(DDD)V"), cancellable = false)
    public void $enderpearl_variety_effect(net.minecraft.util.hit.HitResult hitResult, CallbackInfo ci) {
    *//*?} else {*/
    @Inject(method = "playTeleportSound", at = @At(value = "HEAD"), cancellable = true)
    public void $enderpearl_variety_effect(World world, Vec3d pos, CallbackInfo ci) {
    /*?}*/
        if(SoundsConfig.get(WorldSoundsConfig.class).enableEnderpearlVariety) {
            if(this.getOwner() instanceof PlayerEntity && this.getWorld().isClient) {
                this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 0.8F, 1.0F, false);
                /*? if >=1.21 {*/
                ci.cancel();
                /*?}*/
            }
        }
    }
}
