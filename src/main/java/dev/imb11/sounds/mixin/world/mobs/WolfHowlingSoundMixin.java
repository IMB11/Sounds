package dev.imb11.sounds.mixin.world.mobs;

import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.WorldSoundsConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WolfEntity.class)
public abstract class WolfHowlingSoundMixin extends TameableEntity {
    @Unique
    private boolean hasHowledDuringPhase = false;

    protected WolfHowlingSoundMixin(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = false)
    public void $wolf_howling_sound_effect(CallbackInfo ci) {
        int moonPhase = this.getWorld().getMoonPhase();

        // 0 = full, 4 = new
        if ((moonPhase == 0 || moonPhase == 4) && this.random.nextInt(32) == 0 && !this.hasHowledDuringPhase) {
            SoundsConfig.get(WorldSoundsConfig.class).wolfHowlingSoundEffect.playSound();
            this.hasHowledDuringPhase = true;
        }

        if (moonPhase != 0) {
            this.hasHowledDuringPhase = false;
        }
    }
}
