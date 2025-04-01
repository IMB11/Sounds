package dev.imb11.sounds.mixin.ui;

import dev.imb11.sounds.util.MixinStatics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.UISoundsConfig;
import dev.imb11.sounds.dynamic.DynamicSoundHelper;
import dev.imb11.sounds.sound.context.ItemStackSoundContext;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    //? if <1.21.5 {
    /*@Inject(method = "drop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity;", at = @At("HEAD"))
    *///?} else {
    @Inject(method = "drop", at = @At("HEAD"))
    //?}
    protected void $drop_item_sound_effect(
            ItemStack stack,
            boolean throwRandomly,
            //? if <1.21.5 {
            /*boolean retainOwnership,
            *///?}
            CallbackInfoReturnable<ItemEntity> cir) {
    }
}

@Mixin(LocalPlayer.class)
public abstract class ItemDropSoundEffect extends PlayerEntityMixin {
    @Shadow @Final protected Minecraft minecraft;

    @Shadow public abstract void playSound(@NotNull SoundEvent sound, float volume, float pitch);

    protected ItemDropSoundEffect(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Unique
    private static long sounds$dropSoundCooldownTime = 0L;

    @Unique
    private void sounds$playSound(ItemStack stack) {
        if(MixinStatics.hasOpenedInventorioScreen) {
            sounds$dropSoundCooldownTime = System.currentTimeMillis() + ((long) SoundsConfig.get(UISoundsConfig.class).itemSoundCooldown);
            MixinStatics.hasOpenedInventorioScreen = false;
            return;
        }
        if(sounds$dropSoundCooldownTime > System.currentTimeMillis()) return;
        sounds$dropSoundCooldownTime = System.currentTimeMillis() + ((long) SoundsConfig.get(UISoundsConfig.class).itemSoundCooldown);

        if (MixinStatics.previousAction == ClickType.QUICK_MOVE) {
            MixinStatics.previousAction = null;
            return;
        }

        SoundsConfig.get(UISoundsConfig.class).itemDropSoundEffect.playDynamicSound(stack, ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.FALL));
    }

    @Inject(method = "drop", at = @At("HEAD"))
    private void $drop_selected_item_sound_effect(boolean entireStack, CallbackInfoReturnable<Boolean> cir) {
        ItemStack stack = this.getMainHandItem();
        sounds$playSound(stack);
    }

    @Override
    protected void $drop_item_sound_effect(
            ItemStack stack,
            boolean throwRandomly,
            //? if <1.21.5 {
            /*boolean retainOwnership,
            *///?}
            CallbackInfoReturnable<ItemEntity> cir) {
        if (!this.level().isClientSide) return;
        sounds$playSound(stack);
    }
}
