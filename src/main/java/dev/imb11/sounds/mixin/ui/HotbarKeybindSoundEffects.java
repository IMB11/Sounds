package dev.imb11.sounds.mixin.ui;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(MinecraftClient.class)
public class HotbarKeybindSoundEffects {
    @WrapOperation(method = "handleInputEvents", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerInventory;selectedSlot:I"))
    public void $hotbar_keybind_sound_effect(PlayerInventory instance, int value, Operation<Void> original) {
        instance.setSelectedSlot(value);
    }
}
