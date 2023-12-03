package com.mineblock11.sonance.config;

import com.google.gson.GsonBuilder;
import com.mineblock11.mru.config.YACLHelper;
import com.mineblock11.sonance.config.adapters.ConfiguredSoundTypeAdapter;
import com.mineblock11.sonance.config.adapters.DynamicConfiguredSoundTypeAdapter;
import com.mineblock11.sonance.sound.ConfiguredSound;
import com.mineblock11.sonance.sound.DynamicConfiguredSound;
import com.mineblock11.sonance.sound.context.ItemStackSoundContext;
import com.mineblock11.sonance.sound.context.ScreenHandlerSoundContext;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

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
    public final ConfiguredSound typingSoundEffect = new ConfiguredSound("typing", SoundEvents.BLOCK_NOTE_BLOCK_HAT, true, 1.6f, 0.4f);
    @SerialEntry
    public final ConfiguredSound messageSoundEffect = new ConfiguredSound("message", SoundEvents.BLOCK_NOTE_BLOCK_HAT, true, 2.0f, 0.8f);
    @SerialEntry
    public final ConfiguredSound mentionSoundEffect = new ConfiguredSound("mention", SoundEvents.BLOCK_NOTE_BLOCK_CHIME, true, 1.8f, 0.9f);

    // Screen Sounds
    @SerialEntry
    public final DynamicConfiguredSound<ScreenHandler, ScreenHandlerSoundContext> inventoryOpenSoundEffect = new DynamicConfiguredSound<>("inventoryOpen", getSoundEventReference(SoundEvents.UI_TOAST_IN), true, 2f, 0.2f, true);
    @SerialEntry
    public final DynamicConfiguredSound<ScreenHandler, ScreenHandlerSoundContext> inventoryCloseSoundEffect = new DynamicConfiguredSound<>("inventoryClose", getSoundEventReference(SoundEvents.UI_TOAST_OUT), true, 2f, 0.2f, false);
    @SerialEntry
    public final ConfiguredSound inventoryScrollSoundEffect = new ConfiguredSound("inventoryScroll", SoundEvents.BLOCK_NOTE_BLOCK_HAT, true, 1.8f, 0.2f);

    @SerialEntry
    public final ConfiguredSound inventoryTypingSoundEffect = new ConfiguredSound("inventoryTyping", SoundEvents.BLOCK_NOTE_BLOCK_HAT, true, 1.8f, 0.2f);

    // Hotbar Sounds
    @SerialEntry
    public final DynamicConfiguredSound<ItemStack, ItemStackSoundContext> hotbarScrollSoundEffect = new DynamicConfiguredSound<>("hotbarScroll", SoundEvents.BLOCK_NOTE_BLOCK_HAT, true, 1.8f, 0.2f, true);
    @SerialEntry
    public final DynamicConfiguredSound<ItemStack, ItemStackSoundContext> hotbarPickSoundEffect = new DynamicConfiguredSound<>("hotbarPick", SoundEvents.BLOCK_NOTE_BLOCK_HAT, true, 1.8f, 0.2f, true);

    // Item Sounds
    @SerialEntry
    public final DynamicConfiguredSound<ItemStack, ItemStackSoundContext> itemDropSoundEffect = new DynamicConfiguredSound<>("itemDrop", SoundEvents.BLOCK_NOTE_BLOCK_HAT, true, 1.4f, 0.2f, true);
    @SerialEntry
    public final DynamicConfiguredSound<ItemStack, ItemStackSoundContext> itemCopySoundEffect = new DynamicConfiguredSound<>("itemCopy", SoundEvents.BLOCK_NOTE_BLOCK_HAT, true, 1.4f, 0.2f, true);
    @SerialEntry
    public final DynamicConfiguredSound<ItemStack, ItemStackSoundContext> itemDeleteSoundEffect = new DynamicConfiguredSound<>("itemDelete", SoundEvents.BLOCK_NOTE_BLOCK_HAT, true, 1.4f, 0.1f, true);
    @SerialEntry
    public final DynamicConfiguredSound<ItemStack, ItemStackSoundContext> itemDragSoundEffect = new DynamicConfiguredSound<>("itemDrag", SoundEvents.BLOCK_NOTE_BLOCK_HAT, true, 1.4f, 0.2f, true);
    @SerialEntry
    public final DynamicConfiguredSound<ItemStack, ItemStackSoundContext> itemClickSoundEffect = new DynamicConfiguredSound<>("itemPick", SoundEvents.BLOCK_NOTE_BLOCK_HAT, true, 1.4f, 0.2f, true);

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
