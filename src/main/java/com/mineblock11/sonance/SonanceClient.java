package com.mineblock11.sonance;

import com.mineblock11.sonance.config.SonanceConfig;
import net.fabricmc.api.ClientModInitializer;

public class SonanceClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SonanceConfig.load();
    }
}
