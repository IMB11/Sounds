package dev.imb11.sounds.mixin.ui;

import dev.imb11.sounds.config.old.UISoundConfig;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilScreen.class)
public class AnvilTypingEffect {
    @Inject(method = "keyPressed", at = @At("RETURN"))
    public void $anvil_typing_sound_effect(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
       if(cir.getReturnValue()) {
           UISoundConfig.get().inventoryTypingSoundEffect.playSound();
       }
    }
}
