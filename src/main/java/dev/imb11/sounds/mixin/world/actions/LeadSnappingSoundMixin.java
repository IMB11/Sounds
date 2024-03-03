package dev.imb11.sounds.mixin.world.actions;

import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.WorldSoundsConfig;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public class LeadSnappingSoundMixin {
    @Inject(method = "detachLeash", at = @At("TAIL"), cancellable = false)
    public void $lead_snapping_sound_effect(boolean sendPacket, boolean dropItem, CallbackInfo ci) {
        if (dropItem) {
            SoundsConfig.get(WorldSoundsConfig.class).leadSnappingSoundEffect.playSound();
        }
    }
}
