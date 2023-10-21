//package com.mineblock11.sonance.mixin;
//
//import com.llamalad7.mixinextras.injector.WrapWithCondition;
//import com.mineblock11.sonance.config.SonanceConfig;
//import com.mineblock11.sonance.dynamic.DynamicSoundHelper;
//import net.minecraft.client.network.ClientPlayNetworkHandler;
//import net.minecraft.client.world.ClientWorld;
//import net.minecraft.network.packet.s2c.play.ItemPickupAnimationS2CPacket;
//import net.minecraft.sound.SoundCategory;
//import net.minecraft.sound.SoundEvent;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//@Mixin(ClientPlayNetworkHandler.class)
//public class ClientPlayNetworkHandlerMixin {
//    @WrapWithCondition(method = "onItemPickupAnimation", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;playSound(DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FFZ)V", ordinal = 1))
//    public boolean $item_pickup_sound_effect(ClientWorld targetClass, double x, double y, double z, SoundEvent sound, SoundCategory category, float volume, float pitch, boolean useDistance) {
//        return !SonanceConfig.get().itemPickupSoundEffect.isShouldPlay();
//    }
//}
