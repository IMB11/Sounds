package dev.imb11.sounds.dynamic;

import com.mojang.datafixers.util.Either;
import dev.imb11.sounds.SoundsClient;
import dev.imb11.sounds.api.SoundDefinition;
import dev.imb11.sounds.api.config.TagPair;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Optional;

import net.fabricmc.fabric.api.tag.client.v1.ClientTags;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class TagPairHelper {
    protected static HashMap<Identifier, TagPair> LOADED_TAG_PAIRS = new HashMap<>();
    protected static HashMap<Identifier, Identifier> BLOCK_CACHE = new HashMap<>();
    public static HashMap<ResourceKey<Item>, SoundDefinition<Item>> ITEM_CACHE = new HashMap<>();
    public static HashMap<TagKey<Item>, SoundDefinition<Item>> ITEM_TAG_CACHE = new HashMap<>();

    public static Collection<TagPair> getAllTagPairs() {
        return LOADED_TAG_PAIRS.values();
    }

    public static void buildCache() {
        SoundsClient.LOGGER.info("Building tag cache.");
        BLOCK_CACHE.clear();
        ITEM_TAG_CACHE.clear();
        ITEM_CACHE.clear();
        for (var entry : LOADED_TAG_PAIRS.entrySet()) {
            var id = entry.getKey();
            var val = entry.getValue();
            for (Either<ResourceKey<Block>, TagKey<Block>> registryKeyTagKeyEither : val.getKeys().getInternalList()) {
                if(registryKeyTagKeyEither.left().isPresent()) {
                    BLOCK_CACHE.put(registryKeyTagKeyEither.left().get().identifier(), id);
                } else if(registryKeyTagKeyEither.right().isPresent()) {
                    //? fabric {
                    ClientTags.getOrCreateLocalTag(registryKeyTagKeyEither.right().get()).forEach(identifier -> {
                        BLOCK_CACHE.put(identifier, id);
                    });
                    //?} else {
                    /*var vals = BuiltInRegistries.BLOCK.get(registryKeyTagKeyEither.right().get());
                    if(vals.isPresent()) {
                        for (Holder<Block> block : vals.get()) {
                            BLOCK_CACHE.put(block.unwrapKey().get().identifier(), id);
                        }
                    } else {
                        SoundsClient.LOGGER.warn("Failed to find block entries for tag key: " + registryKeyTagKeyEither.right().get().location());
                    }
                    *///?}
                }
            }
        }
        for (SoundDefinition<Item> definition : DynamicSoundHelper.<Item>getDefinitions("items")) {
            definition.getKeys().getInternalList().forEach(value -> {
                if (value.left().isPresent()) {
                    ITEM_CACHE.put(value.left().get(), definition);
                }
                else if (value.right().isPresent()) {
                    ITEM_TAG_CACHE.put(value.right().get(), definition);
                }
            });
        }
    }

    public static TagPair get(Identifier id) {
        var cacheVal = BLOCK_CACHE.get(id);
        if(cacheVal == null) return null;
        return LOADED_TAG_PAIRS.get(cacheVal);
    }
}
