package dev.imb11.sounds.dynamic;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.JsonOps;
import dev.imb11.sounds.SoundsClient;
import dev.imb11.sounds.api.SoundDefinition;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStreamReader;
import java.util.ArrayList;

public class SoundsReloadListener implements SimpleSynchronousResourceReloadListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(SoundsReloadListener.class);
    private static final Gson GSON = new Gson();

    @Override
    public Identifier getFabricId() {
        return SoundsClient.id("sounds_reload_listener");
    }

    @Override
    public void reload(ResourceManager manager) {
        DynamicSoundHelper.clearDefinitions();

        DynamicSoundHelper.loadDirectories.forEach((directory, codec) -> {
            ArrayList<SoundDefinition<?>> resultList = (ArrayList<SoundDefinition<?>>) DynamicSoundHelper.loadedDefinitions.get(directory);

            for (Identifier id : manager.findResources("sounds/" + directory, path -> path.getPath().endsWith(".json")).keySet()) {
                try {
                    var resource = manager.getResource(id).orElseThrow();
                    var inputStream = resource.getInputStream();
                    var reader = new JsonReader(new InputStreamReader(inputStream));

                    /*? if <1.20.5 {*//*
                    SoundDefinition<?> result = (SoundDefinition<?>) codec.parse(JsonOps.INSTANCE, GSON.fromJson(reader, JsonObject.class)).getOrThrow(false, s -> {
                        throw new RuntimeException(s);
                    });
                    *//*? } else {*/
                    SoundDefinition<?> result = (SoundDefinition<?>) codec.parse(JsonOps.INSTANCE, GSON.fromJson(reader, JsonObject.class)).result().orElseThrow();
                    /*? }*/

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
                    var entries = Registries.ITEM.getOrCreateEntryList(tagKey);

                    for (RegistryEntry<Item> key : entries) {
                        var entry = Registries.ITEM.get(key.getKey().get());
                        itemsWithLoadedDefinitions.add(entry);
                    }
                }
            }
        });
    }
}
