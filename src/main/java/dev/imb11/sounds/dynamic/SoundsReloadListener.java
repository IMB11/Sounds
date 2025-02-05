package dev.imb11.sounds.dynamic;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.JsonOps;
import dev.imb11.sounds.api.SoundDefinition;
import dev.imb11.sounds.api.config.TagPair;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SinglePreparationResourceReloader;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStreamReader;
import java.util.ArrayList;

public class SoundsReloadListener extends SinglePreparationResourceReloader<Void> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SoundsReloadListener.class);
    private static final Gson GSON = new Gson();

    public void reload(ResourceManager manager) {
        handleDynamicSounds(manager);

        // Load tag pairs
        TagPairHelper.LOADED_TAG_PAIRS.clear();

        for (Identifier id : manager.findResources("sounds/blocks", path -> path.getPath().endsWith(".json")).keySet()) {
            try {
                var resource = manager.getResource(id).orElseThrow();
                var inputStream = resource.getInputStream();
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

            for (Identifier id : manager.findResources("sounds/" + directory, path -> path.getPath().endsWith(".json")).keySet()) {
                try {
                    var resource = manager.getResource(id).orElseThrow();
                    var inputStream = resource.getInputStream();
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
        Registries.ITEM.streamEntries().forEach(item -> {
            items.add(item.value());
        });

        ArrayList<Item> itemsWithLoadedDefinitions = new ArrayList<>();

        DynamicSoundHelper.loadedDefinitions.get("items").forEach(definition -> {
            SoundDefinition<Item> definitionCast = (SoundDefinition<Item>) definition;

            for (Either<RegistryKey<Item>, TagKey<Item>> registryKeyTagKeyEither : definitionCast.getKeys().getInternalList()) {
                if (registryKeyTagKeyEither.left().isPresent()) {
                    var key = registryKeyTagKeyEither.left().get();
                    var entry = Registries.ITEM.get(key.getValue());
                    itemsWithLoadedDefinitions.add(entry);
                } else if (registryKeyTagKeyEither.right().isPresent()) {
                    var tagKey = registryKeyTagKeyEither.right().get();

                    //? if <1.21.2 {
                    var entries = Registries.ITEM.getOrCreateEntryList(tagKey);
                    //?} else {
                    /*var entriesOpt = Registries.ITEM.getOptional(tagKey);
                    if(entriesOpt.isEmpty()) continue;
                    var entries = entriesOpt.get();
                    *///?}

                    for (RegistryEntry<Item> key : entries) {
                        var entry = Registries.ITEM.get(key.getKey().get());
                        itemsWithLoadedDefinitions.add(entry);
                    }
                }
            }
        });
    }

//    @Override
//    public CompletableFuture<Void> reload(Synchronizer synchronizer, ResourceManager manager, Profiler prepareProfiler, Profiler applyProfiler, Executor prepareExecutor, Executor applyExecutor) {
//        return CompletableFuture.supplyAsync(() -> {
//            this.reload(manager);
//            synchronizer.whenPrepared(null);
//            return null;
//        }, applyExecutor);
//    }

    @Override
    protected Void prepare(ResourceManager manager, Profiler profiler) {
        profiler.push("SoundsReloadListener");
        this.reload(manager);
        profiler.pop();
        return null;
    }

    @Override
    protected void apply(Void prepared, ResourceManager manager, Profiler profiler) {
        // NO-OP
    }
}
