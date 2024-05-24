package dev.imb11.sounds.config.adapters;

import com.google.gson.*;
import com.mojang.serialization.JsonOps;
import dev.imb11.sounds.sound.DynamicConfiguredSound;
import dev.imb11.sounds.sound.InventoryDynamicConfiguredSound;

import java.lang.reflect.Type;

public class InventoryConfiguredSoundTypeAdapter implements JsonSerializer<InventoryDynamicConfiguredSound>, JsonDeserializer<InventoryDynamicConfiguredSound> {
    @Override
    public InventoryDynamicConfiguredSound deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        var sound = DynamicConfiguredSound.CODEC.decode(JsonOps.INSTANCE, jsonElement)
                .result()
                .orElseThrow()
                .getFirst();
        
        return new InventoryDynamicConfiguredSound(sound.getId(), sound.getSoundEvent(), sound.enabled, sound.getPitch(), sound.getVolume(), sound.enableDynamicSounds);
    }

    @Override
    public JsonElement serialize(InventoryDynamicConfiguredSound configuredSound, Type type, JsonSerializationContext jsonSerializationContext) {
        return DynamicConfiguredSound.CODEC.encodeStart(JsonOps.INSTANCE, configuredSound)
                .result()
                .orElseThrow();
    }
}
