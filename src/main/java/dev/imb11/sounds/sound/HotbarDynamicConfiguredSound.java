package dev.imb11.sounds.sound;

import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.UISoundsConfig;
import dev.imb11.sounds.sound.context.ItemStackSoundContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class HotbarDynamicConfiguredSound extends DynamicConfiguredSound<ItemStack, ItemStackSoundContext> {
    public HotbarDynamicConfiguredSound(String id, Identifier soundEvent, boolean enabled, float pitch, float volume, boolean enableDynamicSounds) {
        super(id, soundEvent, enabled, pitch, volume, enableDynamicSounds);
    }

    public HotbarDynamicConfiguredSound(String id, SoundEvent soundEvent, boolean enabled, float pitch, float volume, boolean enableDynamicSounds) {
        super(id, soundEvent, enabled, pitch, volume, enableDynamicSounds);
    }

    public HotbarDynamicConfiguredSound(String id, RegistryEntry.Reference<SoundEvent> soundEvent, boolean enabled, float pitch, float volume, boolean enableDynamicSounds) {
        super(id, soundEvent, enabled, pitch, volume, enableDynamicSounds);
    }

    @Override
    public void playDynamicSound(ItemStack context, ItemStackSoundContext contextHandler) {
        // If item stack is empty and UISounds.ignoreEmptyInventorySlots is true, don't play any sound, else, normal behavior
        if (context.isEmpty() && SoundsConfig.get(UISoundsConfig.class).ignoreEmptyHotbarSlots) return;

        super.playDynamicSound(context, contextHandler);
    }
}
