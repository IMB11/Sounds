package dev.imb11.sounds;

import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.dynamic.DynamicSoundHelper;
import dev.imb11.sounds.dynamic.SoundsReloadListener;
import dev.imb11.sounds.gui.images.YACLImageReloadListenerFabric;
import dev.imb11.sounds.sound.events.PotionEventHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoundsClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Sounds");

    public static Identifier id(String id) {
        return new Identifier("sounds", id);
    }

    @Override
    public void onInitializeClient() {
        SoundsConfig.loadAll();
        DynamicSoundHelper.initialize();

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SoundsReloadListener());
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new YACLImageReloadListenerFabric());

        PotionEventHelper.initialize();
    }
}
