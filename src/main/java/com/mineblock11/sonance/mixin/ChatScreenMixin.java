package com.mineblock11.sonance.mixin;

import com.mineblock11.sonance.config.SonanceConfig;
import net.minecraft.client.gui.screen.ChatScreen;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {
    private static boolean isNotSpecialKey(int keycode) {
        switch (keycode) {
            case GLFW.GLFW_KEY_LEFT_SHIFT:
            case GLFW.GLFW_KEY_RIGHT_SHIFT:
            case GLFW.GLFW_KEY_LEFT_CONTROL:
            case GLFW.GLFW_KEY_RIGHT_CONTROL:
            case GLFW.GLFW_KEY_CAPS_LOCK:
            case GLFW.GLFW_KEY_TAB:
            case GLFW.GLFW_KEY_ENTER:
            case GLFW.GLFW_KEY_BACKSPACE:
            case GLFW.GLFW_KEY_INSERT:
            case GLFW.GLFW_KEY_DELETE:
            case GLFW.GLFW_KEY_END:
            case GLFW.GLFW_KEY_HOME:
            case GLFW.GLFW_KEY_PAGE_DOWN:
            case GLFW.GLFW_KEY_PAGE_UP:
            case GLFW.GLFW_KEY_PAUSE:
            case GLFW.GLFW_KEY_SCROLL_LOCK:
            case GLFW.GLFW_KEY_PRINT_SCREEN:
            case GLFW.GLFW_KEY_ESCAPE:
            case GLFW.GLFW_KEY_LEFT_ALT:
            case GLFW.GLFW_KEY_RIGHT_ALT:
            case GLFW.GLFW_KEY_NUM_LOCK:
            case GLFW.GLFW_KEY_KP_ENTER:
            case GLFW.GLFW_KEY_F1:
            case GLFW.GLFW_KEY_F2:
            case GLFW.GLFW_KEY_F3:
            case GLFW.GLFW_KEY_F4:
            case GLFW.GLFW_KEY_F5:
            case GLFW.GLFW_KEY_F6:
            case GLFW.GLFW_KEY_F7:
            case GLFW.GLFW_KEY_F8:
            case GLFW.GLFW_KEY_F9:
            case GLFW.GLFW_KEY_F10:
            case GLFW.GLFW_KEY_F11:
            case GLFW.GLFW_KEY_F12:
                return false;
            default:
                return true;
        }
    }

    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = false)
    public void $typing_sound_effect(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (isNotSpecialKey(keyCode)) {
            SonanceConfig.get().typingSoundEffect.playSound();
        }
    }
}
