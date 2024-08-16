//? if forge {
/*package dev.imb11.sounds.loaders.forge;

import dev.architectury.platform.forge.EventBuses;
import dev.imb11.sounds.SoundsClient;
import dev.imb11.sounds.gui.SoundsConfigScreen;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("sounds")
public class SoundsForge {
    public SoundsForge() {
        EventBuses.registerModEventBus("sounds", FMLJavaModLoadingContext.get().getModEventBus());

        SoundsClient.init();

        ModLoadingContext.get().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(
                        (minecraftClient, parent) -> new SoundsConfigScreen(parent)
                )
        );
    }
}
*///?}
