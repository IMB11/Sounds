package dev.imb11.sounds;

import cc.cassian.mru.client.events.ClientRegisterEvent;
import cc.cassian.mru.events.CommonRegisterEvent;
import dev.imb11.mru.LoaderUtils;
import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.dynamic.DynamicSoundHelper;
import dev.imb11.sounds.sound.CustomSounds;
import dev.imb11.sounds.util.ConfigSetters;
import net.minecraft.util.RandomSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

import net.minecraft.resources.Identifier;

public class SoundsClient implements ClientRegisterEvent, CommonRegisterEvent {
    public static final RandomSource RANDOM = RandomSource.create();
    public static final Path DEFAULT_PACK_PATH = LoaderUtils.getConfigFolder("sounds").resolve("dynamic_sounds");
    public static final Logger LOGGER = LoggerFactory.getLogger("Sounds");
    public static String username;

    public static Identifier id(String id) {
        return Identifier.fromNamespaceAndPath("sounds", id);
    }

    @Override
    public void onInitialize() {
        CustomSounds.initialize();
    }

    public static String username() {
        return username;
    }

    @Override
    public void onInitializeClient() {
        DynamicSoundHelper.initialize();
        SoundsConfig.loadAll();
        ConfigSetters.init();
    }
}
