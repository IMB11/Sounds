package dev.imb11.sounds.mixin.world.mobs;

import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.WorldSoundsConfig;
import dev.imb11.sounds.sound.ConfiguredSound;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(MobEntity.class)
public abstract class BabyChickenChirpSoundMixin extends LivingEntity {
    protected BabyChickenChirpSoundMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyArgs(method = "playAmbientSound", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/MobEntity;playSound(Lnet/minecraft/sound/SoundEvent;FF)V"))
    public void $baby_chicken_chirp_sound_effect(Args args) {
        ConfiguredSound sound = SoundsConfig.get(WorldSoundsConfig.class).babyChickenChirpSoundEffect;
        if (((Object) this) instanceof ChickenEntity chicken && chicken.isBaby() && sound.enabled) {
            args.set(0, sound.soundEvent);
            args.set(1, sound.volume);
            args.set(2, sound.pitch);
        }
    }
}
