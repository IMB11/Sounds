package com.mineblock11.sonance.config;

import com.google.gson.GsonBuilder;
import com.mineblock11.sonance.sound.ConfiguredSound;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.config.ConfigEntry;
import dev.isxander.yacl3.config.GsonConfigInstance;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

import java.nio.file.Path;
import java.util.List;

public class SonanceConfig {
    private static final Path CONFIG_FILE_PATH = FabricLoader.getInstance().getConfigDir().resolve("sonance.config.json");
    private static final GsonConfigInstance<SonanceConfig> GSON = GsonConfigInstance.createBuilder(SonanceConfig.class)
            .overrideGsonBuilder(new GsonBuilder()
                    .disableHtmlEscaping()
                    .setPrettyPrinting()
                    .registerTypeAdapter(ConfiguredSound.class, new ConfiguredSoundTypeAdapter())
                    .create())
            .setPath(CONFIG_FILE_PATH)
            .build();
    @ConfigEntry
    public final ConfiguredSound typingSoundEffect = new ConfiguredSound("typing", SoundEvents.BLOCK_NOTE_BLOCK_HAT, 1.6f, 0.4f);
    @ConfigEntry
    public final ConfiguredSound messageSoundEffect = new ConfiguredSound("message", SoundEvents.BLOCK_NOTE_BLOCK_HAT, 2.0f, 0.8f);
    @ConfigEntry
    public final ConfiguredSound mentionSoundEffect = new ConfiguredSound("mention", SoundEvents.BLOCK_NOTE_BLOCK_CHIME, 1.8f, 0.9f);

    public static SonanceConfig get() {
        return GSON.getConfig();
    }

    public static void load() {
        GSON.load();
    }

    public static YetAnotherConfigLib getInstance() {
        return YetAnotherConfigLib.create(GSON,
                ((defaults, config, builder) -> builder.title(Text.empty()).category(ConfigCategory.createBuilder()
                        .groups(List.of(
                                config.typingSoundEffect.getOptionGroup(defaults.typingSoundEffect)
                        ))
                        .name(Text.translatable("sonance.config")).build())));
    }
}
