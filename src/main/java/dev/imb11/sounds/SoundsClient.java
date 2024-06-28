package dev.imb11.sounds;

import com.mineblock11.mru.Utilities;
import dev.imb11.sounds.config.ChatSoundsConfig;
import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.dynamic.DynamicSoundHelper;
import dev.imb11.sounds.dynamic.SoundsReloadListener;
import dev.imb11.sounds.sound.CustomSounds;
import dev.imb11.sounds.sound.events.PotionEventHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.impl.client.keybinding.KeyBindingRegistryImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoundsClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Sounds");
    public static String[] SUPPORTERS = new String[] {
            "You have no internet.",
            "Unable to gather supporters."
    };

    public static Identifier id(String id) {
        return Identifier.of("sounds", id);
    }

    @Override
    public void onInitializeClient() {
        SoundsConfig.loadAll();
        DynamicSoundHelper.initialize();

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SoundsReloadListener());

        CustomSounds.initialize();
        PotionEventHelper.initialize();

        ChatSoundsConfig chatSoundsConfig = SoundsConfig.getRaw(ChatSoundsConfig.class);
        ChatSoundsConfig instanceChatSoundsConfig = (ChatSoundsConfig) chatSoundsConfig.getHandler().instance();

        // Add username to mentionKeywords if it's not already there
        if (!instanceChatSoundsConfig.mentionKeywords.contains("@" + MinecraftClient.getInstance().getSession().getUsername())) {
            instanceChatSoundsConfig.mentionKeywords.add("@" + MinecraftClient.getInstance().getSession().getUsername());
        }

        System.out.println();
        chatSoundsConfig.save();

        try {
            SUPPORTERS = Utilities.getKofiSupporters();
        } catch (Exception ignored) {}
    }
}
