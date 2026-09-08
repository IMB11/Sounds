package dev.imb11.sounds.mixin.ui;

import dev.imb11.sounds.util.MixinStatics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Prediction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.UISoundsConfig;
import dev.imb11.sounds.dynamic.DynamicSoundHelper;
import dev.imb11.sounds.sound.context.ItemStackSoundContext;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(LivingEntity.class)
public abstract class ItemDropSoundEffect extends Entity {
    protected ItemDropSoundEffect(EntityType<? extends Entity> entityType, Level world) {
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

        if (MixinStatics.previousAction == ContainerInput.QUICK_MOVE) {
            MixinStatics.previousAction = null;
            return;
        }

        SoundsConfig.get(UISoundsConfig.class).itemDropSoundEffect.playDynamicSound(stack, ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.FALL));
    }

    @Inject(method = "drop", at = @At("HEAD"))
    private void $drop_selected_item_sound_effect(ItemStack itemStack, boolean thrownFromHand, Prediction prediction, CallbackInfoReturnable<ItemEntity> cir) {
        if (!this.level().isClientSide()) return;
        if (((Object) this instanceof LocalPlayer))
            sounds$playSound(itemStack);
    }
}
