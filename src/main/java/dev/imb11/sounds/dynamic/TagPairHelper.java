package dev.imb11.sounds.dynamic;

import dev.imb11.sounds.api.config.TagPair;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.HashMap;

public class TagPairHelper {
    protected static HashMap<Identifier, TagPair> LOADED_TAG_PAIRS = new HashMap<>();

    public static Collection<TagPair> getAllTagPairs() {
        return LOADED_TAG_PAIRS.values();
    }
}
