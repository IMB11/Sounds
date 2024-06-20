package dev.imb11.sounds.api.config.adapters;

import com.google.gson.*;
import com.mojang.serialization.JsonOps;
import dev.imb11.sounds.api.config.ConfiguredSound;
import dev.imb11.sounds.api.config.DynamicConfiguredSound;
import dev.imb11.sounds.api.config.TagPair;

import java.lang.reflect.Type;

public class TagPairTypeAdapter  implements JsonSerializer<TagPair>, JsonDeserializer<TagPair> {
    @Override
    public TagPair deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return TagPair.CODEC.decode(JsonOps.INSTANCE, json)
                .result()
                .orElseThrow()
                .getFirst();
    }

    @Override
    public JsonElement serialize(TagPair src, Type typeOfSrc, JsonSerializationContext context) {
        return TagPair.CODEC.encodeStart(JsonOps.INSTANCE, src)
                .result()
                .orElseThrow();
    }
}
