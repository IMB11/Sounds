//? if neoforge {
package dev.imb11.sounds.loaders.neoforge;

import dev.imb11.sounds.SoundsClient;
import dev.imb11.sounds.dynamic.SoundsReloadListener;
import dev.imb11.sounds.gui.SoundsConfigScreen;
import dev.imb11.sounds.sound.CustomSounds;
import dev.imb11.sounds.sound.events.PotionEventHelper;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod("sounds")
public class SoundsNeoForge {
    public final PotionEventHelper potionEventHelper = new PotionEventHelper();

    public SoundsNeoForge(IEventBus bus) {
        SoundsClient.init();

        CustomSounds.REGISTRY.register(bus);
        ModLoadingContext.get().getActiveContainer().registerExtensionPoint(IConfigScreenFactory.class, (client, parent) -> new SoundsConfigScreen(parent));

        bus.addListener(SoundsNeoForge::registerResourceReloadListeners);
    }

    private static void registerResourceReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(new SoundsReloadListener());
    }

    @SubscribeEvent
    private void clientTickEvent(ClientTickEvent event) {
        potionEventHelper.listenForEffectChanges(Minecraft.getInstance().level);
    }
}
//?}
