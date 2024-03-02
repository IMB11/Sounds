package dev.imb11.sounds.mixin.ui;

import dev.imb11.sounds.config.old.UISoundConfig;
import net.minecraft.client.gui.screen.ChatScreen;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatScreen.class)
public class TypingSoundEffect {
    @Unique
    private static boolean isNotSpecialKey(int keycode) {
        return switch (keycode) {
            case GLFW.GLFW_KEY_LEFT_SHIFT, GLFW.GLFW_KEY_RIGHT_SHIFT, GLFW.GLFW_KEY_LEFT_CONTROL, GLFW.GLFW_KEY_RIGHT_CONTROL, GLFW.GLFW_KEY_CAPS_LOCK, GLFW.GLFW_KEY_TAB, GLFW.GLFW_KEY_ENTER, GLFW.GLFW_KEY_INSERT, GLFW.GLFW_KEY_DELETE, GLFW.GLFW_KEY_END, GLFW.GLFW_KEY_HOME, GLFW.GLFW_KEY_PAGE_DOWN, GLFW.GLFW_KEY_PAGE_UP, GLFW.GLFW_KEY_PAUSE, GLFW.GLFW_KEY_SCROLL_LOCK, GLFW.GLFW_KEY_PRINT_SCREEN, GLFW.GLFW_KEY_ESCAPE, GLFW.GLFW_KEY_LEFT_ALT, GLFW.GLFW_KEY_RIGHT_ALT, GLFW.GLFW_KEY_NUM_LOCK, GLFW.GLFW_KEY_KP_ENTER, GLFW.GLFW_KEY_F1, GLFW.GLFW_KEY_F2, GLFW.GLFW_KEY_F3, GLFW.GLFW_KEY_F4, GLFW.GLFW_KEY_F5, GLFW.GLFW_KEY_F6, GLFW.GLFW_KEY_F7, GLFW.GLFW_KEY_F8, GLFW.GLFW_KEY_F9, GLFW.GLFW_KEY_F10, GLFW.GLFW_KEY_F11, GLFW.GLFW_KEY_F12 ->
                    false;
            default -> true;
        };
    }

    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = false)
    public void $typing_sound_effect(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (isNotSpecialKey(keyCode)) {
            UISoundConfig.get().typingSoundEffect.playSound();
        }
    }
}
