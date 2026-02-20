package dev.imb11.sounds.mixin.ui;

import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.UISoundsConfig;
import dev.imb11.sounds.util.MixinStatics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EditBox.class)
public abstract class EditBoxSoundEffects extends AbstractWidget {

    public EditBoxSoundEffects(int i, int j, int k, int l, Component component) {
        super(i, j, k, l, component);
    }

    @Inject(method = "keyPressed", at = @At("RETURN"))
    private void $editBoxSounds(KeyEvent keyEvent, CallbackInfoReturnable<Boolean> cir) {
        if (this.isActive() && this.isFocused() && MixinStatics.isNotSpecialKey(keyEvent.key())) {
            SoundsConfig.get(UISoundsConfig.class).typingSoundEffect.playSound();
        }
    }
}
