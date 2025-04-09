package dev.imb11.sounds.util;

import dev.imb11.mru.LoaderUtils;
import dev.imb11.sounds.SoundsClient;
import dev.imb11.sounds.config.ChatSoundsConfig;
import dev.imb11.sounds.config.ModConfig;
import dev.imb11.sounds.config.SoundsConfig;
import net.minecraft.client.Minecraft;

public class ConfigSetters {
    public static void init() {
        if (LoaderUtils.isModInstalled("rsls")) {
            SoundsClient.LOGGER.warn("Raise Sound Limits Simplified was detected, hideSoundsButtonInSoundMenu = true has been set and saved to prevent collision of buttons on the sound mixer menu.");
            var modConfig = SoundsConfig.getRaw(ModConfig.class);
            var instanceModConfig = (ModConfig) modConfig.getHandler().instance();
            instanceModConfig.hideSoundsButtonInSoundMenu = true;
            modConfig.save();
        }

        // Add username to mentionKeywords if it's not already there
        ChatSoundsConfig chatSoundsConfig = SoundsConfig.getRaw(ChatSoundsConfig.class);
        ChatSoundsConfig instanceChatSoundsConfig = (ChatSoundsConfig) chatSoundsConfig.getHandler().instance();
        if (!instanceChatSoundsConfig.mentionKeywords.contains("@" + Minecraft.getInstance().getUser().getName())) {
            instanceChatSoundsConfig.mentionKeywords.add("@" + Minecraft.getInstance().getUser().getName());
            chatSoundsConfig.save();
        }
    }
}
