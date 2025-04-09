package dev.imb11.sounds;

import dev.imb11.mru.API;
import dev.imb11.mru.LoaderUtils;
import dev.imb11.sounds.config.ChatSoundsConfig;
import dev.imb11.sounds.config.ModConfig;
import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.dynamic.DynamicSoundHelper;
import dev.imb11.sounds.sound.CustomSounds;
import dev.imb11.sounds.util.ConfigSetters;
import net.minecraft.util.RandomSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public class SoundsClient {
    public static final RandomSource RANDOM = RandomSource.create();
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

        DynamicSoundHelper.initialize();

        CustomSounds.initialize();

        CompletableFuture.runAsync(() -> {
            try {
                API apiClient = new API();
                SUPPORTERS = apiClient.getKofiSupporters();
            } catch (Exception ignored) {}
        }, Util.nonCriticalIoPool());

        //? if !(neoforge && >=1.21.5) {
        SoundsConfig.loadAll();
        ConfigSetters.init();
        //?}
    }
}
