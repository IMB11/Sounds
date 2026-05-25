package dev.imb11.sounds;

import dev.imb11.mru.LoaderUtils;
import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.dynamic.DynamicSoundHelper;
import dev.imb11.sounds.sound.CustomSounds;
import dev.imb11.sounds.util.ConfigSetters;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.RandomSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

import net.minecraft.resources.Identifier;

public class SoundsClient implements ClientModInitializer, ModInitializer {
    public static final RandomSource RANDOM = RandomSource.create();
    public static final Path DEFAULT_PACK_PATH = LoaderUtils.getConfigFolder("sounds").resolve("dynamic_sounds");
    public static final Logger LOGGER = LoggerFactory.getLogger("Sounds");

    public static Identifier id(String id) {
        return Identifier.fromNamespaceAndPath("sounds", id);
    }

    @Override
    public void onInitialize() {
        CustomSounds.initialize();
    }

    @Override
    public void onInitializeClient() {
        DynamicSoundHelper.initialize();
        SoundsConfig.loadAll();
        ConfigSetters.init();
    }
}
