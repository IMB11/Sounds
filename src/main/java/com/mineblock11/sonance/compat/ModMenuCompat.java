package com.mineblock11.sonance.compat;

import com.mineblock11.sonance.config.SonanceConfig;
import com.mineblock11.sonance.gui.SonanceConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.text.Text;

public class ModMenuCompat implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return SonanceConfigScreen::new;
    }
}