package dev.imb11.sounds.mixin.ui;

import dev.imb11.sounds.config.WorldSoundsConfig;
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

    @Inject(method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;", at = @At("HEAD"))
    protected void $drop_item_sound_effect(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
    }
}

@Mixin(LocalPlayer.class)
public abstract class ItemDropSoundEffect extends PlayerEntityMixin {
    @Shadow @Final protected Minecraft client;

    @Shadow public abstract void playSound(SoundEvent sound, float volume, float pitch);

    protected ItemDropSoundEffect(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Unique
    private static long dropSoundCooldownTime = 0L;

    @Unique
    private void playSound(ItemStack stack) {
        if(MixinStatics.hasOpenedInventorioScreen) {
            dropSoundCooldownTime = System.currentTimeMillis() + ((long) SoundsConfig.get(UISoundsConfig.class).itemSoundCooldown);
            MixinStatics.hasOpenedInventorioScreen = false;
            return;
        }
        if(dropSoundCooldownTime > System.currentTimeMillis()) return;
        dropSoundCooldownTime = System.currentTimeMillis() + ((long) SoundsConfig.get(UISoundsConfig.class).itemSoundCooldown);

        if (MixinStatics.previousAction == ClickType.QUICK_MOVE) {
            MixinStatics.previousAction = null;
            return;
        }

        SoundsConfig.get(UISoundsConfig.class).itemDropSoundEffect.playDynamicSound(stack, ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.FALL));
    }

    @Inject(method = "dropSelectedItem(Z)Z", at = @At("HEAD"))
    private void $drop_selected_item_sound_effect(boolean entireStack, CallbackInfoReturnable<Boolean> cir) {
        ItemStack stack = this.getMainHandItem();
        playSound(stack);
    }

    @Override
    protected void $drop_item_sound_effect(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
        if (!this.level().isClientSide) return;
        playSound(stack);
    }
}
