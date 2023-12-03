package com.mineblock11.sonance.config;

import com.google.gson.GsonBuilder;
import com.mineblock11.mru.config.YACLHelper;
import com.mineblock11.sonance.config.adapters.ConfiguredSoundTypeAdapter;
import com.mineblock11.sonance.config.adapters.DynamicConfiguredSoundTypeAdapter;
import com.mineblock11.sonance.sound.ConfiguredSound;
import com.mineblock11.sonance.sound.DynamicConfiguredSound;
import com.mineblock11.sonance.sound.context.RepeaterSoundContext;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class GameplaySoundConfig {
    private static final YACLHelper.NamespacedHelper HELPER = new YACLHelper.NamespacedHelper("sonance.gameplay");
    private static final ConfigClassHandler<GameplaySoundConfig> GSON = HELPER.createHandler(GameplaySoundConfig.class, new ArrayList<UnaryOperator<GsonBuilder>>(List.of(
            gsonBuilder -> gsonBuilder.registerTypeAdapter(ConfiguredSound.class, new ConfiguredSoundTypeAdapter()),
            builder -> builder.registerTypeAdapter(DynamicConfiguredSound.class, new DynamicConfiguredSoundTypeAdapter())
    )));

    @SerialEntry
    public DynamicConfiguredSound<Integer, RepeaterSoundContext> repeaterUseSoundEffect = new DynamicConfiguredSound<>("repeaterUse", SoundEvents.BLOCK_NOTE_BLOCK_HAT, true, 1.0F, 0.45F, true);
    @SerialEntry
    public ConfiguredSound jukeboxUseSoundEffect = new ConfiguredSound("jukeboxUse", SoundEvents.BLOCK_NOTE_BLOCK_BASEDRUM, true, 0.8F, 0.75F);

    public static void load() {
        GSON.load();
    }

    public static GameplaySoundConfig get() {
        return GSON.instance();
    }

    public static YetAnotherConfigLib getInstance() {
        return YetAnotherConfigLib.create(GSON,
                (defaults, config, builder) -> builder
                        .category(ConfigCategory.createBuilder()
                                .name(Text.translatable("sonance.config.gameplay.redstone"))
                                .groups(List.of(
                                        config.repeaterUseSoundEffect.getOptionGroup(defaults.repeaterUseSoundEffect, true),
                                        config.jukeboxUseSoundEffect.getOptionGroup(defaults.jukeboxUseSoundEffect)
                                ))
                                .build())
                        .title(Text.empty())
        );
    }
}
