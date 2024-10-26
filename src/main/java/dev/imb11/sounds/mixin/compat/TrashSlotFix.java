package dev.imb11.sounds.mixin.compat;

import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.UISoundsConfig;
import dev.imb11.sounds.dynamic.DynamicSoundHelper;
import dev.imb11.sounds.sound.context.ItemStackSoundContext;
import net.blay09.mods.trashslot.TrashHelper;
import net.blay09.mods.trashslot.client.TrashSlotSlot;
import net.blay09.mods.trashslot.client.deletion.DefaultDeletionProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(value = DefaultDeletionProvider.class, remap = false)
public class TrashSlotFix {
    @Inject(method = "deleteMouseItem", at = @At("HEAD"))
    private void itemDeleteTrashSlot(PlayerEntity player, ItemStack mouseItem, TrashSlotSlot trashSlot, boolean isRightClick, CallbackInfo ci) {
        SoundsConfig.get(UISoundsConfig.class).itemDeleteSoundEffect.playSound();
    }
}
