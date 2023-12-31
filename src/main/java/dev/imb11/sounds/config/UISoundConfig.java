package dev.imb11.sounds.config;

import com.google.gson.GsonBuilder;
import com.mineblock11.mru.config.YACLHelper;
import dev.imb11.sounds.config.adapters.ConfiguredSoundTypeAdapter;
import dev.imb11.sounds.config.adapters.DynamicConfiguredSoundTypeAdapter;
import dev.imb11.sounds.sound.ConfiguredSound;
import dev.imb11.sounds.sound.DynamicConfiguredSound;
import dev.imb11.sounds.sound.context.ItemStackSoundContext;
import dev.imb11.sounds.sound.context.ScreenHandlerSoundContext;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class UISoundConfig {
    private static final YACLHelper.NamespacedHelper HELPER = new YACLHelper.NamespacedHelper("sounds.ui");
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
    public final DynamicConfiguredSound<ScreenHandler, ScreenHandlerSoundContext> inventoryOpenSoundEffect = new DynamicConfiguredSound<>("inventoryOpen", SoundsConfig.getSoundEventReference(SoundEvents.UI_TOAST_IN), true, 2f, 0.2f, true);
    @SerialEntry
    public final DynamicConfiguredSound<ScreenHandler, ScreenHandlerSoundContext> inventoryCloseSoundEffect = new DynamicConfiguredSound<>("inventoryClose", SoundsConfig.getSoundEventReference(SoundEvents.UI_TOAST_OUT), true, 2f, 0.2f, false);
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

    @SerialEntry
    public boolean useAtForChatMentions = true;

    @SerialEntry
    public boolean ignoreSystemChats = false;

    @SerialEntry
    public boolean ignoreEmptyHotbarSlots = false;

    public static UISoundConfig get() {
        return GSON.instance();
    }

    public static void load() {
        GSON.load();
    }

    public static YetAnotherConfigLib getInstance() {
        return YetAnotherConfigLib.create(GSON,
                (defaults, config, builder) -> {
                    Option<Boolean> useAtForChatMentionsOption = Option.<Boolean>createBuilder()
                            .name(Text.translatable("sounds.config.chat_mention_at.name"))
                            .description(OptionDescription.createBuilder()
                                    .text(Text.translatable("sounds.config.chat_mention_at.description")).build())
                            .binding(defaults.useAtForChatMentions, () -> config.useAtForChatMentions, (value) -> config.useAtForChatMentions = value)
                            .controller(opt -> BooleanControllerBuilder.create(opt).coloured(true).yesNoFormatter())
                            .build();

                    Option<Boolean> ignoreSystemChatsOption = Option.<Boolean>createBuilder()
                            .name(Text.translatable("sounds.config.ignore_system_chats.name"))
                            .description(OptionDescription.createBuilder()
                                    .text(Text.translatable("sounds.config.ignore_system_chats.description")).build())
                            .binding(defaults.ignoreSystemChats, () -> config.ignoreSystemChats, (value) -> config.ignoreSystemChats = value)
                            .controller(opt -> BooleanControllerBuilder.create(opt).coloured(true).yesNoFormatter())
                            .build();

                    Option<Boolean> ignoreEmptyHotbarSlotsOption = Option.<Boolean>createBuilder()
                            .name(Text.translatable("sounds.config.ignore_empty_hotbar_slots.name"))
                            .description(OptionDescription.createBuilder()
                                    .text(Text.translatable("sounds.config.ignore_empty_hotbar_slots.description")).build())
                            .binding(defaults.ignoreEmptyHotbarSlots, () -> config.ignoreEmptyHotbarSlots, (value) -> config.ignoreEmptyHotbarSlots = value)
                            .controller(opt -> BooleanControllerBuilder.create(opt).coloured(true).yesNoFormatter())
                            .build();

                    return builder
                            .title(Text.empty())
                            .category(
                                    ConfigCategory.createBuilder()
                                            .name(Text.translatable("sounds.config.ui.chat"))
                                            .groups(List.of(
                                                    config.typingSoundEffect.getOptionGroup(defaults.typingSoundEffect, true),
                                                    config.messageSoundEffect.getOptionGroup(defaults.messageSoundEffect, true),
                                                    config.mentionSoundEffect.getOptionGroup(defaults.mentionSoundEffect)
                                                    ))
                                            .option(useAtForChatMentionsOption)
                                            .option(ignoreSystemChatsOption)
                                            .build()
                            ).category(
                                    ConfigCategory.createBuilder()
                                            .name(Text.translatable("sounds.config.ui.hotbar"))
                                            .groups(List.of(
                                                    config.hotbarScrollSoundEffect.getOptionGroup(defaults.hotbarScrollSoundEffect, true),
                                                    config.hotbarPickSoundEffect.getOptionGroup(defaults.hotbarPickSoundEffect, true)
                                            ))
                                            .option(ignoreEmptyHotbarSlotsOption)
                                            .build()
                            ).category(
                                    ConfigCategory.createBuilder()
                                            .name(Text.translatable("sounds.config.ui.screen"))
                                            .groups(List.of(
                                                    config.inventoryOpenSoundEffect.getOptionGroup(defaults.inventoryOpenSoundEffect, true),
                                                    config.inventoryCloseSoundEffect.getOptionGroup(defaults.inventoryCloseSoundEffect, true),
                                                    config.inventoryScrollSoundEffect.getOptionGroup(defaults.inventoryScrollSoundEffect, true),
                                                    config.inventoryTypingSoundEffect.getOptionGroup(defaults.inventoryTypingSoundEffect, true)
                                            ))
                                            .build()
                            ).category(
                                    ConfigCategory.createBuilder()
                                            .name(Text.translatable("sounds.config.ui.item"))
                                            .groups(List.of(
                                                    config.itemDropSoundEffect.getOptionGroup(defaults.itemDropSoundEffect, true),
                                                    config.itemCopySoundEffect.getOptionGroup(defaults.itemCopySoundEffect, true),
                                                    config.itemDeleteSoundEffect.getOptionGroup(defaults.itemDeleteSoundEffect, true),
                                                    config.itemDragSoundEffect.getOptionGroup(defaults.itemDragSoundEffect, true),
                                                    config.itemClickSoundEffect.getOptionGroup(defaults.itemClickSoundEffect, false)
                                            ))
                                            .build()
                            );
                }
        );
    }
}
