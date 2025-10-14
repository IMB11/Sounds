package dev.imb11.sounds.mixin.world.mechanics;

import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.WorldSoundsConfig;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class SwordSwingSoundEffect {
    @Unique
    public SoundInstance sounds$currentSwordSwooshSound;

    @Inject(method = "swing(Lnet/minecraft/world/InteractionHand;Z)V", at = @At(value = "HEAD"))
    public void $start_sword_swoosh_sound(InteractionHand interactionHand, boolean bl, CallbackInfo ci) {
        var entity =  (LivingEntity) (Object) this;
        if (!entity.level().isClientSide()) return;
        if (entity.getItemInHand(interactionHand).is(ItemTags.SWORDS)) {
            this.sounds$currentSwordSwooshSound = SoundsConfig.get(WorldSoundsConfig.class).swordSwooshSoundEffect.getSoundInstance();
            if (this.sounds$currentSwordSwooshSound != null) {
                SoundsConfig.get(WorldSoundsConfig.class).swordSwooshSoundEffect.playSound(this.sounds$currentSwordSwooshSound);
            }
        }
    }

}
