package com.mineblock11.sonance;

import com.mineblock11.sonance.config.SonanceConfig;
import com.mineblock11.sonance.dynamic.DynamicSoundHelper;
import com.mineblock11.sonance.dynamic.SonanceReloadListener;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SonanceClient implements ClientModInitializer {
    public static Identifier id(String id) {
        return new Identifier("sonance", id);
    }

    public static final Logger LOGGER = LoggerFactory.getLogger("Sonance");

    @Override
    public void onInitializeClient() {
        SonanceConfig.loadAll();
        DynamicSoundHelper.initialize();

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SonanceReloadListener());
    }
}
