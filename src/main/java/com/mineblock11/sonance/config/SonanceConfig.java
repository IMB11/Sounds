package com.mineblock11.sonance.config;

import com.google.gson.GsonBuilder;
import com.mineblock11.mru.config.YACLHelper;
import com.mineblock11.sonance.sound.ConfiguredSound;
import com.mineblock11.sonance.sound.DynamicConfiguredSound;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.config.ConfigEntry;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class SonanceConfig {
    private static final YACLHelper.NamespacedHelper HELPER = new YACLHelper.NamespacedHelper("sonance");
    private static final ConfigClassHandler<SonanceConfig> GSON = HELPER.createHandler(SonanceConfig.class, new ArrayList<UnaryOperator<GsonBuilder>>(List.of(
            builder -> builder.registerTypeAdapter(ConfiguredSound.class, new ConfiguredSoundTypeAdapter()),
            builder -> builder.registerTypeAdapter(DynamicConfiguredSound.class, new DynamicConfiguredSoundTypeAdapter())
    )));
    @ConfigEntry
    public final ConfiguredSound typingSoundEffect = new ConfiguredSound(true, "typing", SoundEvents.BLOCK_NOTE_BLOCK_HAT, 1.6f, 0.4f);
    @ConfigEntry
    public final ConfiguredSound messageSoundEffect = new ConfiguredSound(true, "message", SoundEvents.BLOCK_NOTE_BLOCK_HAT, 2.0f, 0.8f);
    @ConfigEntry
    public final ConfiguredSound mentionSoundEffect = new ConfiguredSound(true, "mention", SoundEvents.BLOCK_NOTE_BLOCK_CHIME, 1.8f, 0.9f);
    @ConfigEntry
    public final DynamicConfiguredSound inventoryOpenSoundEffect = new DynamicConfiguredSound(true, true, "inventoryOpen", getSoundEventReference(SoundEvents.UI_TOAST_IN), 2f, 0.2f);
    @ConfigEntry
    public final DynamicConfiguredSound inventoryCloseSoundEffect = new DynamicConfiguredSound(true, false, "inventoryClose", getSoundEventReference(SoundEvents.UI_TOAST_OUT), 2f, 0.2f);
    @ConfigEntry
    public final ConfiguredSound inventoryScrollSoundEffect = new ConfiguredSound(true, "inventoryScroll", SoundEvents.BLOCK_NOTE_BLOCK_HAT, 1.8f, 0.2f);

    @ConfigEntry
    public final DynamicConfiguredSound hotbarScrollSoundEffect = new DynamicConfiguredSound(true, true, "hotbarScroll", SoundEvents.BLOCK_NOTE_BLOCK_HAT, 1.8f, 0.2f);

    @ConfigEntry
    public final DynamicConfiguredSound hotbarPickSoundEffect = new DynamicConfiguredSound(true, true, "hotbarPick", SoundEvents.BLOCK_NOTE_BLOCK_HAT, 1.8f, 0.2f);
    @ConfigEntry
    public final DynamicConfiguredSound itemDropSoundEffect = new DynamicConfiguredSound(true, true, "itemDrop", SoundEvents.BLOCK_NOTE_BLOCK_HAT, 1.4f, 0.2f);

//    @ConfigEntry
//    public final DynamicConfiguredSound itemPickupSoundEffect = new DynamicConfiguredSound(true, true, "itemPickup", getSoundEventReference(SoundEvents.ENTITY_ITEM_PICKUP), 1.4f, 0.2f);

    @ConfigEntry
    public final DynamicConfiguredSound itemCopySoundEffect = new DynamicConfiguredSound(true, true, "itemCopy", SoundEvents.BLOCK_NOTE_BLOCK_HAT, 1.4f, 0.2f);
    @ConfigEntry
    public final DynamicConfiguredSound itemDeleteSoundEffect = new DynamicConfiguredSound(true, true, "itemDelete", SoundEvents.BLOCK_NOTE_BLOCK_HAT, 1.4f, 0.1f);
    @ConfigEntry
    public final DynamicConfiguredSound itemDragSoundEffect = new DynamicConfiguredSound(true, true, "itemDrag", SoundEvents.BLOCK_NOTE_BLOCK_HAT, 1.4f, 0.2f);
    @ConfigEntry
    public final DynamicConfiguredSound itemClickSoundEffect = new DynamicConfiguredSound(true, true, "itemPick", SoundEvents.BLOCK_NOTE_BLOCK_HAT, 1.4f, 0.2f);

    public static SonanceConfig get() {
        return GSON.instance();
    }

    public static void load() {
        GSON.load();
    }

    public static RegistryEntry.Reference<SoundEvent> getSoundEventReference(SoundEvent soundEvent) {
        return Registries.SOUND_EVENT.getEntry(RegistryKey.of(Registries.SOUND_EVENT.getKey(), soundEvent.getId())).get();
    }

    public static YetAnotherConfigLib getInstance() {
        return YetAnotherConfigLib.create(GSON,
                ((defaults, config, builder) -> builder
                        .title(Text.empty())
                        .category(
                                ConfigCategory.createBuilder().groups(List.of(
                                        config.typingSoundEffect.getOptionGroup(defaults.typingSoundEffect, true),
                                        config.messageSoundEffect.getOptionGroup(defaults.messageSoundEffect, true),
                                        config.mentionSoundEffect.getOptionGroup(defaults.mentionSoundEffect),
                                        config.hotbarScrollSoundEffect.getOptionGroup(defaults.hotbarScrollSoundEffect, true),
                                        config.hotbarPickSoundEffect.getOptionGroup(defaults.hotbarPickSoundEffect, true),
//                                        config.itemPickupSoundEffect.getOptionGroup(defaults.itemPickupSoundEffect),
                                        config.itemDropSoundEffect.getOptionGroup(defaults.itemDropSoundEffect, true),
                                        config.itemCopySoundEffect.getOptionGroup(defaults.itemCopySoundEffect, true),
                                        config.itemDeleteSoundEffect.getOptionGroup(defaults.itemDeleteSoundEffect, true),
                                        config.itemDragSoundEffect.getOptionGroup(defaults.itemDragSoundEffect, true),
                                        config.inventoryOpenSoundEffect.getOptionGroup(defaults.inventoryOpenSoundEffect, true),
                                        config.inventoryCloseSoundEffect.getOptionGroup(defaults.inventoryCloseSoundEffect, true),
                                        config.inventoryScrollSoundEffect.getOptionGroup(defaults.inventoryScrollSoundEffect, true)
                                )).name(Text.translatable("sonance.config.static")).build())));
    }
}
