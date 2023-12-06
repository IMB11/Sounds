package dev.imb11.sounds.mixin;//package com.mineblock11.sounds.mixin;
//
//import com.mineblock11.sounds.config.SoundsConfig;
//import com.mineblock11.sounds.dynamic.DynamicSoundHelper;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.ItemEntity;
//import net.minecraft.entity.LivingEntity;
//import net.minecraft.entity.projectile.ArrowEntity;
//import net.minecraft.entity.projectile.ProjectileEntity;
//import net.minecraft.entity.projectile.SpectralArrowEntity;
//import net.minecraft.item.Items;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//@Mixin(LivingEntity.class)
//public class LivingEntityMixin {
//    @Inject(method="sendPickup", at = @At("HEAD"), cancellable = false)
//    public void $item_pickup_sound_effect(Entity item, int count, CallbackInfo ci) {
//        if(item instanceof ItemEntity itemEntity) {
//            SoundsConfig.get().itemPickupSoundEffect.playDynamicSound(itemEntity.getStack(), DynamicSoundHelper.BlockSoundType.HIT);
//        } else if (item instanceof ProjectileEntity projectileEntity) {
//            if(projectileEntity instanceof ArrowEntity || projectileEntity instanceof SpectralArrowEntity) {
//                SoundsConfig.get().itemPickupSoundEffect.playDynamicSound(Items.ARROW.getDefaultStack(), DynamicSoundHelper.BlockSoundType.HIT);
//            }
//        }
//    }
//}
