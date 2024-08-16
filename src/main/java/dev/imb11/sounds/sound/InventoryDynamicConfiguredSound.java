package dev.imb11.sounds.sound;

import dev.imb11.sounds.util.MixinStatics;
import dev.imb11.sounds.api.config.DynamicConfiguredSound;
import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.UISoundsConfig;
import dev.imb11.sounds.sound.context.ItemStackSoundContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class InventoryDynamicConfiguredSound extends DynamicConfiguredSound<ItemStack, ItemStackSoundContext> {
    private static HashMap<String, Long> itemTypeCooldownMap = new HashMap<>();

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

        if(!context.isEmpty()) {
            String itemId = context.getTranslationKey();
            float cooldownDuration = SoundsConfig.get(UISoundsConfig.class).itemSoundCooldown * 1000f;
            long currentTime = System.currentTimeMillis();

            // If the item type is on cooldown, don't play the sound
            if (itemTypeCooldownMap.containsKey(itemId)) {
                long lastPlayedTime = itemTypeCooldownMap.get(itemId);
                if (currentTime - lastPlayedTime < cooldownDuration) return;
            }

            // Update the cooldown map with the current time plus the cooldown duration
            itemTypeCooldownMap.put(itemId, currentTime);
        }

        super.playDynamicSound(context, contextHandler);
    }
}
