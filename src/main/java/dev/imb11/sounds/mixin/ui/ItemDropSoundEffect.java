package dev.imb11.sounds.mixin.ui;

import dev.imb11.sounds.config.WorldSoundsConfig;
import dev.imb11.sounds.util.MixinStatics;
import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.UISoundsConfig;
import dev.imb11.sounds.dynamic.DynamicSoundHelper;
import dev.imb11.sounds.sound.context.ItemStackSoundContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;", at = @At("HEAD"))
    protected void $drop_item_sound_effect(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
    }
}

@Mixin(ClientPlayerEntity.class)
public abstract class ItemDropSoundEffect extends PlayerEntityMixin {
    @Shadow @Final protected MinecraftClient client;

    @Shadow public abstract void playSound(SoundEvent sound, float volume, float pitch);

    protected ItemDropSoundEffect(EntityType<? extends LivingEntity> entityType, World world) {
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

        if (MixinStatics.previousAction == SlotActionType.QUICK_MOVE) {
            MixinStatics.previousAction = null;
            return;
        }

        SoundsConfig.get(UISoundsConfig.class).itemDropSoundEffect.playDynamicSound(stack, ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.FALL));
    }

    @Inject(method = "dropSelectedItem(Z)Z", at = @At("HEAD"))
    private void $drop_selected_item_sound_effect(boolean entireStack, CallbackInfoReturnable<Boolean> cir) {
        ItemStack stack = this.getMainHandStack();
        playSound(stack);
    }

    @Override
    protected void $drop_item_sound_effect(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
        if (!this.getWorld().isClient) return;
        playSound(stack);
    }
}
