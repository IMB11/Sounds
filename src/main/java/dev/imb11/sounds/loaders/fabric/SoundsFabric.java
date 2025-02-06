//? if fabric {
package dev.imb11.sounds.loaders.fabric;

import dev.imb11.sounds.SoundsClient;
import dev.imb11.sounds.dynamic.SoundsReloadListener;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class SoundsFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new SoundsReloadListenerFabric());
        SoundsClient.init();
    }

    public static class SoundsReloadListenerFabric extends SoundsReloadListener implements IdentifiableResourceReloadListener  {
        @Override
        public ResourceLocation getFabricId() {
            return SoundsClient.id("reload_listener");
        }
    }
}
//?}
