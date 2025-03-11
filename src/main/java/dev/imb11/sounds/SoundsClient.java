package dev.imb11.sounds;

import dev.imb11.mru.API;
import dev.imb11.mru.LoaderUtils;
import dev.imb11.sounds.config.ChatSoundsConfig;
import dev.imb11.sounds.config.ModConfig;
import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.dynamic.DynamicSoundHelper;
import dev.imb11.sounds.dynamic.SoundsReloadListener;
import dev.imb11.sounds.sound.CustomSounds;
import dev.imb11.sounds.sound.events.PotionEventHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;

public class SoundsClient {
    public static final Path DEFAULT_PACK_PATH = LoaderUtils.getConfigFolder("sounds").resolve("dynamic_sounds");
    public static final Logger LOGGER = LoggerFactory.getLogger("Sounds");
    public static String[] SUPPORTERS = new String[] {
            "You have no internet.",
            "Unable to gather supporters."
    };

    public static ResourceLocation id(String id) {
        return ResourceLocation.fromNamespaceAndPath("sounds", id);
    }

    public static void init() {
        SoundsConfig.loadAll();
        DynamicSoundHelper.initialize();

        CustomSounds.initialize();

        ChatSoundsConfig chatSoundsConfig = SoundsConfig.getRaw(ChatSoundsConfig.class);
        ChatSoundsConfig instanceChatSoundsConfig = (ChatSoundsConfig) chatSoundsConfig.getHandler().instance();

        // Add username to mentionKeywords if it's not already there
        if (!instanceChatSoundsConfig.mentionKeywords.contains("@" + Minecraft.getInstance().getUser().getName())) {
            instanceChatSoundsConfig.mentionKeywords.add("@" + Minecraft.getInstance().getUser().getName());
        }

        chatSoundsConfig.save();

        CompletableFuture.runAsync(() -> {
            try {
                API apiClient = new API();
                SUPPORTERS = apiClient.getKofiSupporters();
            } catch (Exception ignored) {}
        }, Util.nonCriticalIoPool());

        if (LoaderUtils.isModInstalled("rsls")) {
            LOGGER.warn("Raise Sound Limits Simplified was detected, hideSoundsButtonInSoundMenu = true has been set and saved to prevent collision of buttons on the sound mixer menu.");
            var modConfig = SoundsConfig.get(ModConfig.class);
            modConfig.hideSoundsButtonInSoundMenu = true;
            modConfig.save();
        }
    }
}
