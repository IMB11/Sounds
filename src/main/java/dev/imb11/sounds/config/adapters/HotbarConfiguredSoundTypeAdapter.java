package dev.imb11.sounds.config.adapters;

import com.google.gson.*;
import com.mojang.serialization.JsonOps;
import dev.imb11.sounds.sound.DynamicConfiguredSound;
import dev.imb11.sounds.sound.HotbarDynamicConfiguredSound;

import java.lang.reflect.Type;

public class HotbarConfiguredSoundTypeAdapter implements JsonSerializer<HotbarDynamicConfiguredSound>, JsonDeserializer<HotbarDynamicConfiguredSound> {
    @Override
    public HotbarDynamicConfiguredSound deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        var sound = DynamicConfiguredSound.CODEC.decode(JsonOps.INSTANCE, jsonElement)
                .result()
                .orElseThrow()
                .getFirst();

        return new HotbarDynamicConfiguredSound(sound.getId(), sound.getSoundEvent(), sound.enabled, sound.getPitch(), sound.getVolume(), sound.enableDynamicSounds);
    }

    @Override
    public JsonElement serialize(HotbarDynamicConfiguredSound configuredSound, Type type, JsonSerializationContext jsonSerializationContext) {
        return DynamicConfiguredSound.CODEC.encodeStart(JsonOps.INSTANCE, configuredSound)
                .result()
                .orElseThrow();
    }
}
