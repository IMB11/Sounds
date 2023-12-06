package dev.imb11.sounds.config.adapters;

import com.google.gson.*;
import dev.imb11.sounds.sound.ConfiguredSound;
import com.mojang.serialization.JsonOps;

import java.lang.reflect.Type;

public class ConfiguredSoundTypeAdapter implements JsonSerializer<ConfiguredSound>, JsonDeserializer<ConfiguredSound> {
    @Override
    public ConfiguredSound deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return ConfiguredSound.CODEC.decode(JsonOps.INSTANCE, jsonElement)
                .result()
                .orElseThrow()
                .getFirst();
    }

    @Override
    public JsonElement serialize(ConfiguredSound configuredSound, Type type, JsonSerializationContext jsonSerializationContext) {
        return ConfiguredSound.CODEC.encodeStart(JsonOps.INSTANCE, configuredSound)
                .result()
                .orElseThrow();
    }
}
