package dev.imb11.sounds.mixin.world.mechanics;

import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.WorldSoundsConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.FurnaceMinecartEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FurnaceMinecartEntity.class)
public abstract class FurnaceMinecartEntityMixin extends AbstractMinecartEntity {
    protected FurnaceMinecartEntityMixin(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "interact", at = @At(value = "TAIL"))
    public void $furnace_minecart_fuel_sound_effect(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if(this.getWorld().isClient) {
            SoundsConfig.get(WorldSoundsConfig.class).furnaceMinecartFuelSoundEffect.playSound();
        }
    }
}
