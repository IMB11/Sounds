package dev.imb11.sounds.config.adapters;

import com.google.gson.*;
import com.mojang.serialization.JsonOps;
import dev.imb11.sounds.sound.DynamicConfiguredSound;

import java.lang.reflect.Type;

public class DynamicConfiguredSoundTypeAdapter implements JsonSerializer<DynamicConfiguredSound>, JsonDeserializer<DynamicConfiguredSound> {
    @Override
    public DynamicConfiguredSound deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return DynamicConfiguredSound.CODEC.decode(JsonOps.INSTANCE, jsonElement)
                .result()
                .orElseThrow()
                .getFirst();
    }

    @Override
    public JsonElement serialize(DynamicConfiguredSound configuredSound, Type type, JsonSerializationContext jsonSerializationContext) {
        return DynamicConfiguredSound.CODEC.encodeStart(JsonOps.INSTANCE, configuredSound)
                .result()
                .orElseThrow();
    }
}
