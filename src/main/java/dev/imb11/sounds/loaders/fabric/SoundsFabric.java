//? if fabric {
package dev.imb11.sounds.loaders.fabric;

import dev.imb11.sounds.SoundsClient;
import dev.imb11.sounds.dynamic.SoundsReloadListener;
import dev.imb11.sounds.dynamic.TagPairHelper;
import dev.imb11.sounds.sound.events.PotionEventHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.reloader.ResourceReloaderKeys;
import net.minecraft.server.packs.PackType;

public class SoundsFabric implements ClientModInitializer {
    private final PotionEventHelper potionEventHelper = new PotionEventHelper();

    @Override
    public void onInitializeClient() {
        ResourceLoader.get(PackType.CLIENT_RESOURCES).registerReloader(SoundsClient.id("reload_listener"), new SoundsReloadListener());
        ResourceLoader.get(PackType.CLIENT_RESOURCES).addReloaderOrdering(ResourceReloaderKeys.AFTER_VANILLA, SoundsClient.id("reload_listener"));
        SoundsClient.init();

        ClientTickEvents.START_WORLD_TICK.register(potionEventHelper::listenForEffectChanges);
        ClientWorldEvents.AFTER_CLIENT_WORLD_CHANGE.register((minecraft, level) -> TagPairHelper.buildCache());
    }
}
//?}
