package com.mineblock11.sonance.compat;

import com.mineblock11.sonance.config.SonanceConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuCompat implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> SonanceConfig.getInstance().generateScreen(parent);
    }
}