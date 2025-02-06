package dev.imb11.sounds.mixin.world.mechanics;

import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.WorldSoundsConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.MinecartFurnace;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecartFurnace.class)
public abstract class FurnaceMinecartEntityMixin extends AbstractMinecart {
    protected FurnaceMinecartEntityMixin(EntityType<?> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "interact", at = @At(value = "TAIL"))
    public void $furnace_minecart_fuel_sound_effect(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (this.level().isClientSide) {
            SoundsConfig.get(WorldSoundsConfig.class).furnaceMinecartFuelSoundEffect.playSound();
        }
    }
}
