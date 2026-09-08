package dev.imb11.sounds.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;

import java.util.HashSet;

public class MixinStatics {
    public static Slot CURRENT_SLOT;
    public static Slot DELETE_ITEM_SLOT;
    public static boolean temporarilyDisableInventorySounds = false;
    public static boolean hasOpenedInventorioScreen = false;
    public static final HashSet<Identifier> FOUND_SOUND_EVENTS = new HashSet<>();
    public static ContainerInput previousAction;

    public static boolean isNotSpecialKey(int keycode) {
        return switch (keycode) {
            case InputConstants.KEY_LSHIFT, InputConstants.KEY_RSHIFT, InputConstants.KEY_LCONTROL, InputConstants.KEY_RCONTROL, InputConstants.KEY_CAPSLOCK, InputConstants.KEY_TAB, InputConstants.KEY_RETURN, InputConstants.KEY_INSERT, InputConstants.KEY_DELETE, InputConstants.KEY_END, InputConstants.KEY_HOME, InputConstants.KEY_PAGEDOWN, InputConstants.KEY_PAGEUP, InputConstants.KEY_PAUSE, InputConstants.KEY_SCROLLLOCK, InputConstants.KEY_PRINTSCREEN, InputConstants.KEY_ESCAPE, InputConstants.KEY_LALT, InputConstants.KEY_RALT, InputConstants.KEY_NUMLOCK, InputConstants.KEYCODE_NUMPADENTER, InputConstants.KEY_F1, InputConstants.KEY_F2, InputConstants.KEY_F3, InputConstants.KEY_F4, InputConstants.KEY_F5, InputConstants.KEY_F6, InputConstants.KEY_F7, InputConstants.KEY_F8, InputConstants.KEY_F9, InputConstants.KEY_F10, InputConstants.KEY_F11, InputConstants.KEY_F12 ->
                    false;
            default -> true;
        };
    }
}
