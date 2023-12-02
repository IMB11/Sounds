package com.mineblock11.sonance.config;

import com.google.gson.GsonBuilder;
import com.mineblock11.mru.config.YACLHelper;
import com.mineblock11.sonance.sound.ConfiguredSound;
import com.mineblock11.sonance.sound.DynamicConfiguredSound;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.config.ConfigEntry;
import dev.isxander.yacl3.config.GsonConfigInstance;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class SonanceConfig {
    private static final YACLHelper.NamespacedHelper HELPER = new YACLHelper.NamespacedHelper("sonance");
    private static final ConfigClassHandler<SonanceConfig> GSON = HELPER.createHandler(SonanceConfig.class, new ArrayList<UnaryOperator<GsonBuilder>>(List.of(
            gsonBuilder -> gsonBuilder.registerTypeAdapter(ConfiguredSound.class, new ConfiguredSoundTypeAdapter()),
            builder -> builder.registerTypeAdapter(DynamicConfiguredSound.class, new DynamicConfiguredSoundTypeAdapter())
    )));

    @SerialEntry
    public final ConfiguredSound typingSoundEffect = new ConfiguredSound(true, "typing", SoundEvents.NOTE_BLOCK_HAT, 1.6f, 0.4f);
    @SerialEntry
    public final ConfiguredSound messageSoundEffect = new ConfiguredSound(true, "message", SoundEvents.NOTE_BLOCK_HAT, 2.0f, 0.8f);
    @SerialEntry
    public final ConfiguredSound mentionSoundEffect = new ConfiguredSound(true, "mention", SoundEvents.NOTE_BLOCK_CHIME, 1.8f, 0.9f);
    @SerialEntry
    public final DynamicConfiguredSound inventoryOpenSoundEffect = new DynamicConfiguredSound(true, true, "inventoryOpen", getSoundEventReference(SoundEvents.UI_TOAST_IN), 2f, 0.2f);
    @SerialEntry
    public final DynamicConfiguredSound inventoryCloseSoundEffect = new DynamicConfiguredSound(true, false, "inventoryClose", getSoundEventReference(SoundEvents.UI_TOAST_OUT), 2f, 0.2f);
    @SerialEntry
    public final ConfiguredSound inventoryScrollSoundEffect = new ConfiguredSound(true, "inventoryScroll", SoundEvents.NOTE_BLOCK_HAT, 1.8f, 0.2f);

    @SerialEntry
    public final DynamicConfiguredSound hotbarScrollSoundEffect = new DynamicConfiguredSound(true, true, "hotbarScroll", SoundEvents.NOTE_BLOCK_HAT, 1.8f, 0.2f);

    @SerialEntry
    public final DynamicConfiguredSound hotbarPickSoundEffect = new DynamicConfiguredSound(true, true, "hotbarPick", SoundEvents.NOTE_BLOCK_HAT, 1.8f, 0.2f);
    @SerialEntry
    public final DynamicConfiguredSound itemDropSoundEffect = new DynamicConfiguredSound(true, true, "itemDrop", SoundEvents.NOTE_BLOCK_HAT, 1.4f, 0.2f);

//    @SerialEntry
//    public final DynamicConfiguredSound itemPickupSoundEffect = new DynamicConfiguredSound(true, true, "itemPickup", getSoundEventReference(SoundEvents.ENTITY_ITEM_PICKUP), 1.4f, 0.2f);

    @SerialEntry
    public final DynamicConfiguredSound itemCopySoundEffect = new DynamicConfiguredSound(true, true, "itemCopy", SoundEvents.NOTE_BLOCK_HAT, 1.4f, 0.2f);
    @SerialEntry
    public final DynamicConfiguredSound itemDeleteSoundEffect = new DynamicConfiguredSound(true, true, "itemDelete", SoundEvents.NOTE_BLOCK_HAT, 1.4f, 0.1f);
    @SerialEntry
    public final DynamicConfiguredSound itemDragSoundEffect = new DynamicConfiguredSound(true, true, "itemDrag", SoundEvents.NOTE_BLOCK_HAT, 1.4f, 0.2f);
    @SerialEntry
    public final DynamicConfiguredSound itemClickSoundEffect = new DynamicConfiguredSound(true, true, "itemPick", SoundEvents.NOTE_BLOCK_HAT, 1.4f, 0.2f);

    public static SonanceConfig get() {
        return GSON.instance();
    }

    public static void load() {
        GSON.load();
    }

    public static Holder.Reference<SoundEvent> getSoundEventReference(SoundEvent soundEvent) {
        return BuiltInRegistries.SOUND_EVENT.getHolder(ResourceKey.create(BuiltInRegistries.SOUND_EVENT.key(), soundEvent.getLocation())).get();
    }

    public static YetAnotherConfigLib getInstance() {
        return YetAnotherConfigLib.create(GSON,
                ((defaults, config, builder) -> builder
                        .title(Component.empty())
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
                                )).name(Component.translatable("sonance.config.static")).build())));
    }
}
