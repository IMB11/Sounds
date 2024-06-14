//package dev.imb11.sounds.mixin.world.actions;
//
//import com.mojang.authlib.GameProfile;
//import dev.imb11.sounds.config.SoundsConfig;
//import dev.imb11.sounds.config.WorldSoundsConfig;
//import net.minecraft.client.MinecraftClient;
//import net.minecraft.client.network.AbstractClientPlayerEntity;
//import net.minecraft.client.network.ClientPlayerEntity;
//import net.minecraft.client.world.ClientWorld;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.SwordItem;
//import net.minecraft.util.Hand;
//import org.jetbrains.annotations.Nullable;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.Unique;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//@Mixin(MinecraftClient.class)
//public class SwingSoundMixin {
//    @Shadow @Nullable public ClientPlayerEntity player;
//
//    @Unique
//    public void $swing_item_sound() {
//        ItemStack stackInHand = this.player.getStackInHand(Hand.MAIN_HAND);
//        Item item = stackInHand.getItem();
//
//        if(item instanceof SwordItem swordItem) {
//            SoundsConfig.get(WorldSoundsConfig.class).swordSwoopSoundEffect.playSound();
//        }
//    }
//
//    @Inject(method = "doAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;resetLastAttackedTicks()V"))
//    public void $swing_item_sound_miss(CallbackInfoReturnable<Boolean> cir) {
//        $swing_item_sound();
//    }
//
//    @Inject(method = "doAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;attackBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z"))
//    public void $swing_item_sound_block(CallbackInfoReturnable<Boolean> cir) {
//        $swing_item_sound();
//    }
//}
