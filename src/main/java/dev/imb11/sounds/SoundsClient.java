package dev.imb11.sounds;

import dev.architectury.registry.ReloadListenerRegistry;
import dev.imb11.mru.API;
import dev.imb11.mru.LoaderUtils;
import dev.imb11.mru.packing.Unpacker;
import dev.imb11.mru.packing.resource.UnpackedResourcePack;
import dev.imb11.sounds.config.ChatSoundsConfig;
import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.dynamic.DynamicSoundHelper;
import dev.imb11.sounds.dynamic.SoundsReloadListener;
import dev.imb11.sounds.sound.CustomSounds;
import dev.imb11.sounds.sound.events.PotionEventHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class SoundsClient {
    public static final Path DEFAULT_PACK_PATH = LoaderUtils.getConfigFolder("sounds").resolve("dynamic_sounds");
    public static final Logger LOGGER = LoggerFactory.getLogger("Sounds");
    public static String[] SUPPORTERS = new String[] {
            "You have no internet.",
            "Unable to gather supporters."
    };

    public static Identifier id(String id) {
        return Identifier.of("sounds", id);
    }

    public static void init() {
        SoundsConfig.loadAll();
        DynamicSoundHelper.initialize();

        if(System.getProperty("fabric-api.datagen") == null) {
            Unpacker.register(SoundsClient.class, new UnpackedResourcePack("sounds", DEFAULT_PACK_PATH, "sounds", "This folder contains various dynamic sound definitions, you should consult the documentation site for more information: https://docs.imb11.dev/sounds"));
        }

        ReloadListenerRegistry.register(ResourceType.CLIENT_RESOURCES, new SoundsReloadListener());

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
            API apiClient = new API();
            SUPPORTERS = apiClient.getKofiSupporters();
        } catch (Exception ignored) {}
    }
}
