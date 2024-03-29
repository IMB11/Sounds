package dev.imb11.sounds.config;

import dev.imb11.sounds.config.utils.ConfigGroup;
import dev.imb11.sounds.sound.ConfiguredSound;
import dev.imb11.sounds.sound.DynamicConfiguredSound;
import dev.imb11.sounds.sound.context.ItemStackSoundContext;
import dev.imb11.sounds.sound.context.ScreenHandlerSoundContext;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class UISoundsConfig extends ConfigGroup<UISoundsConfig> implements YetAnotherConfigLib.ConfigBackedBuilder<UISoundsConfig> {
    @SerialEntry
    public final DynamicConfiguredSound<ItemStack, ItemStackSoundContext> hotbarScrollSoundEffect = new DynamicConfiguredSound<>("hotbarScroll", SoundEvents.BLOCK_NOTE_BLOCK_HAT, true, 1.8f, 0.2f, true);
    @SerialEntry
    public final DynamicConfiguredSound<ItemStack, ItemStackSoundContext> hotbarPickSoundEffect = new DynamicConfiguredSound<>("hotbarPick", SoundEvents.BLOCK_NOTE_BLOCK_HAT, true, 1.8f, 0.2f, true);
    @SerialEntry
    public final DynamicConfiguredSound<ScreenHandler, ScreenHandlerSoundContext> inventoryOpenSoundEffect = new DynamicConfiguredSound<>("inventoryOpen", SoundsConfig.getSoundEventReference(SoundEvents.UI_TOAST_IN), true, 2f, 0.2f, true);
    @SerialEntry
    public final DynamicConfiguredSound<ScreenHandler, ScreenHandlerSoundContext> inventoryCloseSoundEffect = new DynamicConfiguredSound<>("inventoryClose", SoundsConfig.getSoundEventReference(SoundEvents.UI_TOAST_OUT), true, 2f, 0.2f, false);
    @SerialEntry
    public final ConfiguredSound inventoryScrollSoundEffect = new ConfiguredSound("inventoryScroll", SoundEvents.BLOCK_NOTE_BLOCK_HAT, true, 1.8f, 0.2f);
    @SerialEntry
    public final ConfiguredSound inventoryTypingSoundEffect = new ConfiguredSound("inventoryTyping", SoundEvents.BLOCK_NOTE_BLOCK_HAT, true, 1.8f, 0.2f);
    /// == ITEM MANAGEMENT == ///
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
    /// == INTERFACE (GENERAL) == ///
    @SerialEntry
    public boolean ignoreEmptyHotbarSlots = false;

    public UISoundsConfig() {
        super(UISoundsConfig.class);
    }

    @Override
    public YetAnotherConfigLib getYACL() {
        return YetAnotherConfigLib.create(getHandler(), this);
    }

    @Override
    public Identifier getImage() {
        return new Identifier("sounds", "textures/gui/ui_sounds.webp");
    }

    @Override
    public Text getName() {
        return Text.translatable("sounds.config.ui");
    }

    @Override
    public String getID() {
        return "ui";
    }

    @Override
    public YetAnotherConfigLib.Builder build(UISoundsConfig defaults, UISoundsConfig config, YetAnotherConfigLib.Builder builder) {
        builder.title(Text.of("UI Sounds"));
        builder.category(ConfigCategory.createBuilder()
                .name(Text.translatable("sounds.config.ui.interface"))
                .option(Option.<Boolean>createBuilder()
                        .name(Text.translatable("sounds.config.ignoreEmptyHotbarSlots.name"))
                        .description(OptionDescription.of(Text.translatable("sounds.config.ignoreEmptyHotbarSlots.description")))
                        .binding(defaults.ignoreEmptyHotbarSlots, () -> config.ignoreEmptyHotbarSlots, (v) -> config.ignoreEmptyHotbarSlots = v)
                        .controller((opt) -> BooleanControllerBuilder.create(opt).coloured(true).yesNoFormatter())
                        .build())
                .group(config.hotbarScrollSoundEffect.getOptionGroup(defaults.hotbarScrollSoundEffect))
                .group(config.hotbarPickSoundEffect.getOptionGroup(defaults.hotbarPickSoundEffect))
                .group(config.inventoryOpenSoundEffect.getOptionGroup(defaults.inventoryOpenSoundEffect))
                .group(config.inventoryCloseSoundEffect.getOptionGroup(defaults.inventoryCloseSoundEffect))
                .group(config.inventoryScrollSoundEffect.getOptionGroup(defaults.inventoryScrollSoundEffect))
                .group(config.inventoryTypingSoundEffect.getOptionGroup(defaults.inventoryTypingSoundEffect))
                .build());
        builder.category(ConfigCategory.createBuilder()
                .name(Text.translatable("sounds.config.ui.item_management"))
                .group(config.itemDropSoundEffect.getOptionGroup(defaults.itemDropSoundEffect))
                .group(config.itemCopySoundEffect.getOptionGroup(defaults.itemCopySoundEffect))
                .group(config.itemDeleteSoundEffect.getOptionGroup(defaults.itemDeleteSoundEffect))
                .group(config.itemDragSoundEffect.getOptionGroup(defaults.itemDragSoundEffect))
                .group(config.itemClickSoundEffect.getOptionGroup(defaults.itemClickSoundEffect))
                .build());

        return builder;
    }
}
