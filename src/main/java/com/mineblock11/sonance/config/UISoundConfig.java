package com.mineblock11.sonance.config;

import com.google.gson.GsonBuilder;
import com.mineblock11.mru.config.YACLHelper;
import com.mineblock11.sonance.config.adapters.ConfiguredSoundTypeAdapter;
import com.mineblock11.sonance.config.adapters.DynamicConfiguredSoundTypeAdapter;
import com.mineblock11.sonance.sound.ConfiguredSound;
import com.mineblock11.sonance.sound.DynamicConfiguredSound;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import static com.mineblock11.sonance.config.SonanceConfig.getSoundEventReference;

public class UISoundConfig {
    private static final YACLHelper.NamespacedHelper HELPER = new YACLHelper.NamespacedHelper("sonance.ui");
    private static final ConfigClassHandler<UISoundConfig> GSON = HELPER.createHandler(UISoundConfig.class, new ArrayList<UnaryOperator<GsonBuilder>>(List.of(
            gsonBuilder -> gsonBuilder.registerTypeAdapter(ConfiguredSound.class, new ConfiguredSoundTypeAdapter()),
            builder -> builder.registerTypeAdapter(DynamicConfiguredSound.class, new DynamicConfiguredSoundTypeAdapter())
    )));

    // Chat Sounds
    @SerialEntry
    public final ConfiguredSound typingSoundEffect = new ConfiguredSound(true, "typing", SoundEvents.BLOCK_NOTE_BLOCK_HAT, 1.6f, 0.4f);
    @SerialEntry
    public final ConfiguredSound messageSoundEffect = new ConfiguredSound(true, "message", SoundEvents.BLOCK_NOTE_BLOCK_HAT, 2.0f, 0.8f);
    @SerialEntry
    public final ConfiguredSound mentionSoundEffect = new ConfiguredSound(true, "mention", SoundEvents.BLOCK_NOTE_BLOCK_CHIME, 1.8f, 0.9f);

    // Screen Sounds
    @SerialEntry
    public final DynamicConfiguredSound inventoryOpenSoundEffect = new DynamicConfiguredSound(true, true, "inventoryOpen", getSoundEventReference(SoundEvents.UI_TOAST_IN), 2f, 0.2f);
    @SerialEntry
    public final DynamicConfiguredSound inventoryCloseSoundEffect = new DynamicConfiguredSound(true, false, "inventoryClose", getSoundEventReference(SoundEvents.UI_TOAST_OUT), 2f, 0.2f);
    @SerialEntry
    public final ConfiguredSound inventoryScrollSoundEffect = new ConfiguredSound(true, "inventoryScroll", SoundEvents.BLOCK_NOTE_BLOCK_HAT, 1.8f, 0.2f);

    @SerialEntry
    public final ConfiguredSound inventoryTypingSoundEffect = new ConfiguredSound(true, "inventoryTyping", SoundEvents.BLOCK_NOTE_BLOCK_HAT, 1.8f, 0.2f);

    // Hotbar Sounds
    @SerialEntry
    public final DynamicConfiguredSound hotbarScrollSoundEffect = new DynamicConfiguredSound(true, true, "hotbarScroll", SoundEvents.BLOCK_NOTE_BLOCK_HAT, 1.8f, 0.2f);
    @SerialEntry
    public final DynamicConfiguredSound hotbarPickSoundEffect = new DynamicConfiguredSound(true, true, "hotbarPick", SoundEvents.BLOCK_NOTE_BLOCK_HAT, 1.8f, 0.2f);

    // Item Sounds
    @SerialEntry
    public final DynamicConfiguredSound itemDropSoundEffect = new DynamicConfiguredSound(true, true, "itemDrop", SoundEvents.BLOCK_NOTE_BLOCK_HAT, 1.4f, 0.2f);
    @SerialEntry
    public final DynamicConfiguredSound itemCopySoundEffect = new DynamicConfiguredSound(true, true, "itemCopy", SoundEvents.BLOCK_NOTE_BLOCK_HAT, 1.4f, 0.2f);
    @SerialEntry
    public final DynamicConfiguredSound itemDeleteSoundEffect = new DynamicConfiguredSound(true, true, "itemDelete", SoundEvents.BLOCK_NOTE_BLOCK_HAT, 1.4f, 0.1f);
    @SerialEntry
    public final DynamicConfiguredSound itemDragSoundEffect = new DynamicConfiguredSound(true, true, "itemDrag", SoundEvents.BLOCK_NOTE_BLOCK_HAT, 1.4f, 0.2f);
    @SerialEntry
    public final DynamicConfiguredSound itemClickSoundEffect = new DynamicConfiguredSound(true, true, "itemPick", SoundEvents.BLOCK_NOTE_BLOCK_HAT, 1.4f, 0.2f);

    public static UISoundConfig get() {
        return GSON.instance();
    }

    public static void load() {
        GSON.load();
    }

    public static YetAnotherConfigLib getInstance() {
        return YetAnotherConfigLib.create(GSON,
                (defaults, config, builder) -> builder
                        .title(Text.empty())
                        .category(
                                ConfigCategory.createBuilder()
                                        .name(Text.translatable("sonance.config.ui.chat"))
                                        .groups(List.of(
                                                config.typingSoundEffect.getOptionGroup(defaults.typingSoundEffect, true),
                                                config.messageSoundEffect.getOptionGroup(defaults.messageSoundEffect, true),
                                                config.mentionSoundEffect.getOptionGroup(defaults.mentionSoundEffect)
                                        ))
                                        .build()
                        ).category(
                                ConfigCategory.createBuilder()
                                        .name(Text.translatable("sonance.config.ui.hotbar"))
                                        .groups(List.of(
                                                config.hotbarScrollSoundEffect.getOptionGroup(defaults.hotbarScrollSoundEffect, true),
                                                config.hotbarPickSoundEffect.getOptionGroup(defaults.hotbarPickSoundEffect, true)
                                        ))
                                        .build()
                        ).category(
                                ConfigCategory.createBuilder()
                                        .name(Text.translatable("sonance.config.ui.screen"))
                                        .groups(List.of(
                                                config.inventoryOpenSoundEffect.getOptionGroup(defaults.inventoryOpenSoundEffect, true),
                                                config.inventoryCloseSoundEffect.getOptionGroup(defaults.inventoryCloseSoundEffect, true),
                                                config.inventoryScrollSoundEffect.getOptionGroup(defaults.inventoryScrollSoundEffect, true),
                                                config.inventoryTypingSoundEffect.getOptionGroup(defaults.inventoryTypingSoundEffect, true)
                                        ))
                                        .build()
                        ).category(
                                ConfigCategory.createBuilder()
                                        .name(Text.translatable("sonance.config.ui.item"))
                                        .groups(List.of(
                                                config.itemDropSoundEffect.getOptionGroup(defaults.itemDropSoundEffect, true),
                                                config.itemCopySoundEffect.getOptionGroup(defaults.itemCopySoundEffect, true),
                                                config.itemDeleteSoundEffect.getOptionGroup(defaults.itemDeleteSoundEffect, true),
                                                config.itemDragSoundEffect.getOptionGroup(defaults.itemDragSoundEffect, true),
                                                config.itemClickSoundEffect.getOptionGroup(defaults.itemClickSoundEffect, false)
                                        ))
                                        .build()
                        )
        );
    }
}
