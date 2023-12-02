package com.mineblock11.sonance.dynamic;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.mineblock11.sonance.SonanceClient;
import com.mineblock11.sonance.api.SoundDefinition;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SonanceReloadListener implements SimpleSynchronousResourceReloadListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(SonanceReloadListener.class);
    private static final Gson GSON = new Gson();

    @Override
    public ResourceLocation getFabricId() {
        return SonanceClient.id("sonance_reload_listener");
    }

    @Override
    public void onResourceManagerReload(ResourceManager manager) {
        DynamicSoundHelper.clearDefinitions();

        DynamicSoundHelper.loadDirectories.forEach((directory, codec) -> {
            ArrayList<SoundDefinition<?>> resultList = (ArrayList<SoundDefinition<?>>) DynamicSoundHelper.loadedDefinitions.get(directory);

            for(ResourceLocation id : manager.listResources("sonance/" + directory, path -> path.getPath().endsWith(".json")).keySet()) {
                try {
                    var resource = manager.getResource(id).orElseThrow();
                    var inputStream = resource.open();
                    var reader = new JsonReader(new InputStreamReader(inputStream));

                    SoundDefinition<?> result = (SoundDefinition<?>) codec.parse(JsonOps.INSTANCE, GSON.fromJson(reader, JsonObject.class)).getOrThrow(false, s -> {
                        throw new RuntimeException(s);
                    });

                    resultList.add(result);

                    inputStream.close();
                } catch(Exception e) {
                    LOGGER.error("Error occurred while loading resource json: " + id.toString(), e);
                }
            }

            DynamicSoundHelper.loadedDefinitions.put(directory, resultList);
        });
    }
}
