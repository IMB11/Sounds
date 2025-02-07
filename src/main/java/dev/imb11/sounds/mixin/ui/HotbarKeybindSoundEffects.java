package dev.imb11.sounds.mixin.ui;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.UISoundsConfig;
import dev.imb11.sounds.dynamic.DynamicSoundHelper;
import dev.imb11.sounds.sound.context.ItemStackSoundContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Minecraft.class)
public class HotbarKeybindSoundEffects {
    @Shadow @Nullable
    public LocalPlayer player;

    @WrapOperation(method = "handleKeybinds", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Inventory;selected:I"))
    public void $hotbar_keybind_sound_effect(Inventory instance, int value, Operation<Void> original) {
        //? if <1.21.2 {
        /*SoundsConfig.get(UISoundsConfig.class).hotbarScrollSoundEffect.playDynamicSound(this.player.getMainHandItem(), ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.PLACE));
        original.call(instance, value);
        *///?} else {
        instance.setSelectedHotbarSlot(value);
        //?}
    }
}
