package com.mineblock11.sonance;

import com.mineblock11.sonance.config.SonanceConfig;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.Identifier;

public class SonanceClient implements ClientModInitializer {
    public static Identifier id(String id) {
        return new Identifier("sonance", id);
    }

    @Override
    public void onInitializeClient() {
        SonanceConfig.load();
    }
}
