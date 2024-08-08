package dev.imb11.sounds.sound;

import dev.imb11.sounds.MixinStatics;
import dev.imb11.sounds.api.config.DynamicConfiguredSound;
import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.UISoundsConfig;
import dev.imb11.sounds.sound.context.ItemStackSoundContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class InventoryDynamicConfiguredSound extends DynamicConfiguredSound<ItemStack, ItemStackSoundContext> {
    public InventoryDynamicConfiguredSound(String id, Identifier soundEvent, boolean enabled, float pitch, float volume, boolean enableDynamicSounds) {
        super(id, soundEvent, enabled, pitch, volume, enableDynamicSounds);
    }

    public InventoryDynamicConfiguredSound(String id, SoundEvent soundEvent, boolean enabled, float pitch, float volume, boolean enableDynamicSounds) {
        super(id, soundEvent, enabled, pitch, volume, enableDynamicSounds);
    }

    public InventoryDynamicConfiguredSound(String id, RegistryEntry.Reference<SoundEvent> soundEvent, boolean enabled, float pitch, float volume, boolean enableDynamicSounds) {
        super(id, soundEvent, enabled, pitch, volume, enableDynamicSounds);
    }

    @Override
    public void playDynamicSound(ItemStack context, ItemStackSoundContext contextHandler) {
        // If item stack is empty and UISounds.ignoreEmptyInventorySlots is true, don't play any sound, else, normal behavior
        if ((context.isEmpty() && SoundsConfig.get(UISoundsConfig.class).ignoreEmptyInventorySlots)
                || MixinStatics.temporarilyDisableInventorySounds) return;

        super.playDynamicSound(context, contextHandler);
    }
}
