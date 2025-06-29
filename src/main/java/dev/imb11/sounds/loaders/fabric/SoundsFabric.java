//? if fabric {
package dev.imb11.sounds.loaders.fabric;

import dev.imb11.sounds.SoundsClient;
import dev.imb11.sounds.dynamic.SoundsReloadListener;
import dev.imb11.sounds.sound.events.PotionEventHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientWorldEvents;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;

public class SoundsFabric implements ClientModInitializer {
    private final PotionEventHelper potionEventHelper = new PotionEventHelper();

    @Override
    public void onInitializeClient() {
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new SoundsReloadListenerFabric());
        SoundsClient.init();

        ClientTickEvents.START_WORLD_TICK.register(potionEventHelper::listenForEffectChanges);
    }

    public static class SoundsReloadListenerFabric extends SoundsReloadListener implements IdentifiableResourceReloadListener  {
        @Override
        public ResourceLocation getFabricId() {
            return SoundsClient.id("reload_listener");
        }

        @Override
        public void reload(ResourceManager manager) {
            super.reload(manager);
        }
    }
}
//?}
