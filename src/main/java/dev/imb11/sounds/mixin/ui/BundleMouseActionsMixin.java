package dev.imb11.sounds.mixin.ui;

import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.UISoundsConfig;
import dev.imb11.sounds.dynamic.DynamicSoundHelper;
import dev.imb11.sounds.sound.context.ItemStackSoundContext;
import net.minecraft.client.gui.BundleMouseActions;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BundleMouseActions.class)
public abstract class BundleMouseActionsMixin {

    @Inject(method = "onMouseScrolled", at = @At("RETURN"))
    public void $hotbar_pick_sound_effect(double scrollX, double scrollY, int slotIndex, ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
        SoundsConfig.get(UISoundsConfig.class).hotbarScrollSoundEffect.playDynamicSound(itemStack, ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.PLACE));
    }
}
