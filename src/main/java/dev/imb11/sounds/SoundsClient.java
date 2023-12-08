package dev.imb11.sounds;

import dev.imb11.sounds.config.GameplaySoundConfig;
import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.dynamic.DynamicSoundHelper;
import dev.imb11.sounds.dynamic.SoundsReloadListener;
import dev.imb11.sounds.gui.images.YACLImageReloadListenerFabric;
import dev.imb11.sounds.sound.events.PotionEventHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class SoundsClient implements ClientModInitializer {
    public static Identifier id(String id) {
        return new Identifier("sounds", id);
    }

    public static final Logger LOGGER = LoggerFactory.getLogger("Sounds");

    @Override
    public void onInitializeClient() {
        SoundsConfig.loadAll();
        DynamicSoundHelper.initialize();

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SoundsReloadListener());
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new YACLImageReloadListenerFabric());

        PotionEventHelper.initialize();
    }
}
