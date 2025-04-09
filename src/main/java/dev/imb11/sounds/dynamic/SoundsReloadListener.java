package dev.imb11.sounds.dynamic;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.JsonOps;
import dev.imb11.sounds.api.SoundDefinition;
import dev.imb11.sounds.api.config.TagPair;
import dev.imb11.sounds.config.ChatSoundsConfig;
import dev.imb11.sounds.config.SoundsConfig;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet.Named;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.tags.TagKey;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;

public class SoundsReloadListener extends SimplePreparableReloadListener<Void> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SoundsReloadListener.class);
    private static final Gson GSON = new Gson();

    public void reload(ResourceManager manager) {
        handleDynamicSounds(manager);

        // Load tag pairs
        TagPairHelper.LOADED_TAG_PAIRS.clear();

        for (ResourceLocation id : manager.listResources("sounds/blocks", path -> path.getPath().endsWith(".json")).keySet()) {
            try {
                var resource = manager.getResource(id).orElseThrow();
                var inputStream = resource.open();
                var reader = new JsonReader(new InputStreamReader(inputStream));

                TagPair.CODEC.decode(JsonOps.INSTANCE, GSON.fromJson(reader, JsonObject.class))
                        .result()
                        .ifPresent(tagPair -> TagPairHelper.LOADED_TAG_PAIRS.put(id, tagPair.getFirst()));

                inputStream.close();
            } catch (Exception e) {
                LOGGER.error("Error occurred while loading resource json: " + id.toString(), e);
            }
        }

        TagPairHelper.buildCache();
    }

    private static void handleDynamicSounds(ResourceManager manager) {
        DynamicSoundHelper.clearDefinitions();
        DynamicSoundHelper.loadDirectories.forEach((directory, codec) -> {
            ArrayList<SoundDefinition<?>> resultList = (ArrayList<SoundDefinition<?>>) DynamicSoundHelper.loadedDefinitions.get(directory);

            for (ResourceLocation id : manager.listResources("sounds/" + directory, path -> path.getPath().endsWith(".json")).keySet()) {
                try {
                    var resource = manager.getResource(id).orElseThrow();
                    var inputStream = resource.open();
                    var reader = new JsonReader(new InputStreamReader(inputStream));

                    /*? if =1.20.1 {*/
                    /*SoundDefinition<?> result = (SoundDefinition<?>) codec.parse(JsonOps.INSTANCE, GSON.fromJson(reader, JsonObject.class)).getOrThrow(false, s -> {
                        throw new RuntimeException(s);
                    });
                    *//*?} else {*/
                    SoundDefinition<?> result = (SoundDefinition<?>) codec.parse(JsonOps.INSTANCE, GSON.fromJson(reader, JsonObject.class)).result().orElseThrow();
                    /*?}*/

                    resultList.add(result);

                    inputStream.close();
                } catch (Exception e) {
                    LOGGER.error("Error occurred while loading resource json: " + id.toString(), e);
                }
            }

            DynamicSoundHelper.loadedDefinitions.put(directory, resultList);
        });

        // List all items that do not have a dynamic sound event that isn't a BlockItem
        ArrayList<Item> items = new ArrayList<>();
        BuiltInRegistries.ITEM.forEach(item -> {
            items.add(item);
        });

        ArrayList<Item> itemsWithLoadedDefinitions = new ArrayList<>();

        DynamicSoundHelper.loadedDefinitions.get("items").forEach(definition -> {
            SoundDefinition<Item> definitionCast = (SoundDefinition<Item>) definition;

            for (Either<ResourceKey<Item>, TagKey<Item>> registryKeyTagKeyEither : definitionCast.getKeys().getInternalList()) {
                if (registryKeyTagKeyEither.left().isPresent()) {
                    var key = registryKeyTagKeyEither.left().get();
                    //? if <1.21.2 {
                    /*var entry = BuiltInRegistries.ITEM.get(key.location());
                    *///?} else {
                    var entry = BuiltInRegistries.ITEM.getValue(key.location());
                    //?}

                    itemsWithLoadedDefinitions.add(entry);
                } else if (registryKeyTagKeyEither.right().isPresent()) {
                    var tagKey = registryKeyTagKeyEither.right().get();

                    //? if <1.21.2 {
                    /*var entries = BuiltInRegistries.ITEM.getOrCreateTag(tagKey);
                    *///?} else {
                    var entriesOpt = BuiltInRegistries.ITEM.get(tagKey);
                    if(entriesOpt.isEmpty()) continue;
                    var entries = entriesOpt.get();
                    //?}

                    for (Holder<Item> key : entries) {
                        var entry = BuiltInRegistries.ITEM.get(key.unwrapKey().get());
                        //? if <1.21.2 {
                        /*itemsWithLoadedDefinitions.add(entry);
                        *///?} else {
                        itemsWithLoadedDefinitions.add(entry.get().value());
                        //?}
                    }
                }
            }
        });
    }

    @Override
    protected Void prepare(ResourceManager manager, ProfilerFiller profiler) {
        profiler.push("SoundsReloadListener");
        this.reload(manager);
        profiler.pop();
        return null;
    }

    @Override
    protected void apply(Void prepared, ResourceManager manager, ProfilerFiller profiler) {
        // NO-OP
    }
}
