//? if neoforge {
/*package dev.imb11.sounds.loaders.neoforge;

import dev.imb11.sounds.SoundsClient;
import dev.imb11.sounds.gui.SoundsConfigScreen;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod("sounds")
public class SoundsNeoForge {
    public SoundsNeoForge(IEventBus bus) {
        SoundsClient.init();
        ModLoadingContext.get().getActiveContainer().registerExtensionPoint(IConfigScreenFactory.class, (client, parent) -> new SoundsConfigScreen(parent));
    }
}
*///?}
