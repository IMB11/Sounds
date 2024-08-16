package dev.imb11.sounds.mixin.ui;

import dev.imb11.sounds.util.MixinStatics;
import dev.imb11.sounds.config.ChatSoundsConfig;
import dev.imb11.sounds.config.SoundsConfig;
import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatScreen.class)
public class TypingSoundEffect {
    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = false)
    public void $typing_sound_effect(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (MixinStatics.isNotSpecialKey(keyCode)) {
            SoundsConfig.get(ChatSoundsConfig.class).typingSoundEffect.playSound();
        }
    }
}
