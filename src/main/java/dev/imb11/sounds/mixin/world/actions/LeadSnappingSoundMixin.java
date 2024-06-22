package dev.imb11.sounds.mixin.world.actions;

import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.WorldSoundsConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/*? if >=1.21 {*/
@Mixin(net.minecraft.entity.Leashable.class)
public interface LeadSnappingSoundMixin {
/*?} else {*/
/*@Mixin(net.minecraft.entity.mob.MobEntity.class)
public class LeadSnappingSoundMixin {
*//*?}*/
    /*? if <=1.20.6 {*/
    /*@Inject(method = "detachLeash", at = @At("TAIL"), cancellable = false)
    public void $lead_snapping_sound_effect(boolean sendPacket, boolean dropItem, CallbackInfo ci) {
        if(dropItem) {
    *//*?} else {*/
    @Inject(method = "detachLeash(Lnet/minecraft/entity/Entity;ZZ)V", at = @At("TAIL"), cancellable = false)
    private static <E extends net.minecraft.entity.Entity & net.minecraft.entity.Leashable> void $lead_snapping_sound_effect(E entity, boolean sendPacket, boolean dropItem, CallbackInfo ci) {
        if (dropItem && entity.getWorld().isClient) {
    /*?}*/
            SoundsConfig.get(WorldSoundsConfig.class).leadSnappingSoundEffect.playSound();
        }
    }
}
