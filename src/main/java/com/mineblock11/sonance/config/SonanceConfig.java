package com.mineblock11.sonance.config;

import com.google.gson.GsonBuilder;
import com.mineblock11.sonance.sound.ConfiguredSound;
import com.mineblock11.sonance.sound.DynamicConfiguredSound;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.config.ConfigEntry;
import dev.isxander.yacl3.config.GsonConfigInstance;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
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
        return GSON.getConfig();
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
                                        config.typingSoundEffect.getOptionGroup(defaults.typingSoundEffect),
                                        config.messageSoundEffect.getOptionGroup(defaults.messageSoundEffect),
                                        config.mentionSoundEffect.getOptionGroup(defaults.mentionSoundEffect),
                                        config.hotbarScrollSoundEffect.getOptionGroup(defaults.hotbarScrollSoundEffect),
                                        config.hotbarPickSoundEffect.getOptionGroup(defaults.hotbarPickSoundEffect),
//                                        config.itemPickupSoundEffect.getOptionGroup(defaults.itemPickupSoundEffect),
                                        config.itemDropSoundEffect.getOptionGroup(defaults.itemDropSoundEffect),
                                        config.itemCopySoundEffect.getOptionGroup(defaults.itemCopySoundEffect),
                                        config.itemDeleteSoundEffect.getOptionGroup(defaults.itemDeleteSoundEffect),
                                        config.itemDragSoundEffect.getOptionGroup(defaults.itemDragSoundEffect),
                                        config.inventoryOpenSoundEffect.getOptionGroup(defaults.inventoryOpenSoundEffect),
                                        config.inventoryCloseSoundEffect.getOptionGroup(defaults.inventoryCloseSoundEffect),
                                        config.inventoryScrollSoundEffect.getOptionGroup(defaults.inventoryScrollSoundEffect)
                                )).name(Text.translatable("sonance.config.static")).build())));
    }
}
