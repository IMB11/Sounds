package dev.imb11.sounds.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

/**
 * @deprecated Not ready for production use.
 */
@Deprecated(forRemoval = false, since = "0.9.0")
public class CustomSounds {
//    public static final SoundEvent ITEM_SWORD_SWOOP = register("item.sword.swoosh");
//    public static final SoundEvent ITEM_HARD_METAL_HOLD = register("item.hard_metal.hold");
//    public static final SoundEvent ITEM_SHINY_METAL_HOLD = register("item.shiny_metal.hold");

    private static SoundEvent register(String id) {
        Identifier _id = Identifier.of("sounds", id);
        return Registry.register(Registries.SOUND_EVENT, _id, SoundEvent.of(_id));
    }

    public static void initialize() {}
}
