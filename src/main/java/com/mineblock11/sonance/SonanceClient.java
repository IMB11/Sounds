package com.mineblock11.sonance;

import com.mineblock11.sonance.config.SonanceConfig;
import com.mineblock11.sonance.dynamic.DynamicSoundHelper;
import com.mineblock11.sonance.dynamic.SonanceReloadListener;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

public class SonanceClient implements ClientModInitializer {
    public static Identifier id(String id) {
        return new Identifier("sonance", id);
    }

    @Override
    public void onInitializeClient() {
        SonanceConfig.load();
        DynamicSoundHelper.initialize();

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SonanceReloadListener());
    }
}
