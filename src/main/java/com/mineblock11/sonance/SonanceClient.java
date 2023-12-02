package com.mineblock11.sonance;

import com.mineblock11.sonance.config.SonanceConfig;
import com.mineblock11.sonance.dynamic.DynamicSoundHelper;
import com.mineblock11.sonance.dynamic.SonanceReloadListener;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;

public class SonanceClient implements ClientModInitializer {
    public static ResourceLocation id(String id) {
        return new ResourceLocation("sonance", id);
    }

    @Override
    public void onInitializeClient() {
        SonanceConfig.load();
        DynamicSoundHelper.initialize();

        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new SonanceReloadListener());
    }
}
