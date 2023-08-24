package com.mineblock11.sonance.config;

import com.google.gson.GsonBuilder;
import com.mineblock11.sonance.sound.ConfiguredSound;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
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
    public final ConfiguredSound hotbarScrollSoundEffect = new ConfiguredSound(true, "hotbarScroll", SoundEvents.BLOCK_NOTE_BLOCK_HAT, 1.8f, 0.2f);

    @ConfigEntry
    public boolean useDynamicHotbar = true;

    public static SonanceConfig get() {
        return GSON.getConfig();
    }

    public static void load() {
        GSON.load();
    }

    public static YetAnotherConfigLib getInstance() {
        return YetAnotherConfigLib.create(GSON,
                ((defaults, config, builder) -> {
                    var hotbarScrollGroup = config.hotbarScrollSoundEffect.getOptionGroup(defaults.hotbarScrollSoundEffect);
                    return builder
                            .title(Text.empty())
                            .category(
                                    ConfigCategory.createBuilder().groups(List.of(
                                            config.typingSoundEffect.getOptionGroup(defaults.typingSoundEffect),
                                            config.messageSoundEffect.getOptionGroup(defaults.messageSoundEffect),
                                            config.mentionSoundEffect.getOptionGroup(defaults.mentionSoundEffect),
                                            hotbarScrollGroup
                                    )).name(Text.translatable("sonance.config.static")).build())
                            .category(
                                    ConfigCategory.createBuilder().options(List.of(
                                            Option.<Boolean>createBuilder()
                                                    .name(Text.translatable("sonance.config.dynamic.hotbar.name"))
                                                    .description(OptionDescription.createBuilder()
                                                            .text(Text.translatable("sonance.config.dynamic.hotbar.desc")).build())
                                                    .listener((opt, val) -> {
                                                        for (Option<?> option : hotbarScrollGroup.options()) {
                                                            if(option.pendingValue() instanceof Boolean) {
                                                                option.setAvailable(!val);
                                                            }
                                                        }
                                                    })
                                                    .binding(defaults.useDynamicHotbar, () -> config.useDynamicHotbar, val -> config.useDynamicHotbar = val)
                                                    .controller(opt -> BooleanControllerBuilder.create(opt).coloured(true).yesNoFormatter()).build()
                                    )).name(Text.translatable("sonance.config.dynamic")).build());
                }));
    }
}
