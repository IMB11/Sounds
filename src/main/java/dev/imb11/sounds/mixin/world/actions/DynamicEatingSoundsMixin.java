package dev.imb11.sounds.mixin.world.actions;

import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.WorldSoundsConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(LivingEntity.class)
public abstract class DynamicEatingSoundsMixin {
    @ModifyArgs(method = "spawnConsumptionEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;playSound(Lnet/minecraft/sound/SoundEvent;FF)V", ordinal = 1))
    public void $eat_sound_effect(Args args) {
        if (((Object)this) instanceof PlayerEntity playerEntity) {
            HungerManager manager = playerEntity.getHungerManager();
            int hungerLevel = manager.getFoodLevel();

            // A low hunger level = higher pitch, quicker eating sound
            // Max hunger level = lower pitch, slower eating sound
            // Range between 0.5 and 1.5
            float eatingPitch = 0.5F + (1.0F - (hungerLevel / 20.0F));

            if (SoundsConfig.get(WorldSoundsConfig.class).enableDynamicEatingSounds) {
                args.set(2, eatingPitch);
            }
        }
    }
}
