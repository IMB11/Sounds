package com.mineblock11.sonance.config;

import com.google.gson.GsonBuilder;
import com.mineblock11.sonance.sound.ConfiguredSound;
import com.mineblock11.sonance.sound.DynamicConfiguredSound;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.config.ConfigEntry;
import dev.isxander.yacl3.config.GsonConfigInstance;
import net.fabricmc.loader.api.FabricLoader;
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
                    .registerTypeAdapter(DynamicConfiguredSound.class, new DynamicConfiguredSoundTypeAdapter())
                    .create())
            .setPath(CONFIG_FILE_PATH)
            .build();
    @ConfigEntry
    public final ConfiguredSound typingSoundEffect = new ConfiguredSound(true, "typing", SoundEvents.BLOCK_NOTE_BLOCK_HAT, 1.6f, 0.4f);
    @ConfigEntry
    public final ConfiguredSound messageSoundEffect = new ConfiguredSound(true, "message", SoundEvents.BLOCK_NOTE_BLOCK_HAT, 2.0f, 0.8f);
    @ConfigEntry
    public final ConfiguredSound mentionSoundEffect = new ConfiguredSound(true, "mention", SoundEvents.BLOCK_NOTE_BLOCK_CHIME, 1.8f, 0.9f);
    @ConfigEntry
    public final DynamicConfiguredSound hotbarScrollSoundEffect = new DynamicConfiguredSound(true, true, "hotbarScroll", SoundEvents.BLOCK_NOTE_BLOCK_HAT, 1.8f, 0.2f);

    @ConfigEntry
    public final DynamicConfiguredSound hotbarPickSoundEffect = new DynamicConfiguredSound(true, true, "hotbarPick", SoundEvents.BLOCK_NOTE_BLOCK_HAT, 1.8f, 0.2f);
    @ConfigEntry
    public final DynamicConfiguredSound itemDropSoundEffect = new DynamicConfiguredSound(true, true, "itemDrop", SoundEvents.BLOCK_NOTE_BLOCK_HAT, 1.4f, 0.2f);

    public static SonanceConfig get() {
        return GSON.getConfig();
    }

    public static void load() {
        GSON.load();
    }

    public static YetAnotherConfigLib getInstance() {
        return YetAnotherConfigLib.create(GSON,
                ((defaults, config, builder) -> {
                    return builder
                            .title(Text.empty())
                            .category(
                                    ConfigCategory.createBuilder().groups(List.of(
                                            config.typingSoundEffect.getOptionGroup(defaults.typingSoundEffect),
                                            config.messageSoundEffect.getOptionGroup(defaults.messageSoundEffect),
                                            config.mentionSoundEffect.getOptionGroup(defaults.mentionSoundEffect),
                                            config.hotbarScrollSoundEffect.getOptionGroup(defaults.hotbarScrollSoundEffect),
                                            config.hotbarPickSoundEffect.getOptionGroup(defaults.hotbarPickSoundEffect),
                                            config.itemDropSoundEffect.getOptionGroup(defaults.itemDropSoundEffect)
                                    )).name(Text.translatable("sonance.config.static")).build());
                }));
    }
}
