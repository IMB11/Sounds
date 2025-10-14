package dev.imb11.sounds.mixin.ui;

import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.UISoundsConfig;
import dev.imb11.sounds.dynamic.DynamicSoundHelper;
import dev.imb11.sounds.sound.context.ItemStackSoundContext;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Inventory.class)
public abstract class HotbarSoundEffects {
    @Shadow
    @Final
    public Player player;

    @Shadow @Final
    private NonNullList<ItemStack> items;

    @Inject(
            method = "setSelectedSlot",
            at = @At("RETURN"),
            cancellable = false)
    public void $hotbar_scroll_sound_effect(int slot, CallbackInfo ci) {
        if (!this.player.level().isClientSide()) return;
        if (!Inventory.isHotbarSlot(slot)) return;

        SoundsConfig.get(UISoundsConfig.class).hotbarScrollSoundEffect.playDynamicSound(this.player.getMainHandItem(), ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.PLACE));
    }

    @Inject(method = "pickSlot", at = @At("RETURN"))
    public void $hotbar_pick_sound_effect(int slot, CallbackInfo ci) {
        if (player.isLocalPlayer())
            SoundsConfig.get(UISoundsConfig.class).hotbarPickSoundEffect.playDynamicSound(this.items.get(slot), ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.STEP));
    }
}
