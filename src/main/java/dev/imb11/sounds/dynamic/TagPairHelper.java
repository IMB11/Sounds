package dev.imb11.sounds.dynamic;

import com.mojang.datafixers.util.Either;
import dev.imb11.sounds.SoundsClient;
import dev.imb11.sounds.api.config.TagPair;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.HashMap;

public class TagPairHelper {
    protected static HashMap<Identifier, TagPair> LOADED_TAG_PAIRS = new HashMap<>();
    protected static HashMap<Identifier, Identifier> BLOCK_CACHE = new HashMap<>();

    public static Collection<TagPair> getAllTagPairs() {
        return LOADED_TAG_PAIRS.values();
    }

    public static void buildCache() {
        BLOCK_CACHE.clear();
        for (var entry : LOADED_TAG_PAIRS.entrySet()) {
            var id = entry.getKey();
            var val = entry.getValue();
            for (Either<RegistryKey<Block>, TagKey<Block>> registryKeyTagKeyEither : val.getKeys().getInternalList()) {
                if(registryKeyTagKeyEither.left().isPresent()) {
                    BLOCK_CACHE.put(registryKeyTagKeyEither.left().get().getValue(), id);
                } else if(registryKeyTagKeyEither.right().isPresent()) {
                    var vals = Registries.BLOCK.getEntryList(registryKeyTagKeyEither.right().get());
                    if(vals.isPresent()) {
                        for (RegistryEntry<Block> block : vals.get()) {
                            BLOCK_CACHE.put(block.getKey().get().getValue(), id);
                        }
                    } else {
                        SoundsClient.LOGGER.warn("Failed to find block entries for tag key: " + registryKeyTagKeyEither.right().get().id());
                    }
                }
            }
        }
    }

    public static TagPair get(Identifier id) {
        var cacheVal = BLOCK_CACHE.get(id);
        if(cacheVal == null) return null;
        return LOADED_TAG_PAIRS.get(cacheVal);
    }
}
