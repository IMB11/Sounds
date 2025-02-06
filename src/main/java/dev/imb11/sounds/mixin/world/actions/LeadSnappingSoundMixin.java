package dev.imb11.sounds.mixin.world.actions;

import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.WorldSoundsConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Leashable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Leashable.class)
public interface LeadSnappingSoundMixin {
    @Inject(method = "dropLeash(Lnet/minecraft/world/entity/Entity;ZZ)V", at = @At("TAIL"), cancellable = false)
    private static <E extends Entity & Leashable> void $lead_snapping_sound_effect(E entity, boolean sendPacket, boolean dropItem, CallbackInfo ci) {
        if (dropItem && entity.level().isClientSide) {
            SoundsConfig.get(WorldSoundsConfig.class).leadSnappingSoundEffect.playSound();
        }
    }
}
