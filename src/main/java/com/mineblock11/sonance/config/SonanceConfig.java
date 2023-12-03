package com.mineblock11.sonance.config;

import com.google.gson.GsonBuilder;
import com.mineblock11.mru.config.YACLHelper;
import com.mineblock11.sonance.SonanceClient;
import com.mineblock11.sonance.config.adapters.ConfiguredSoundTypeAdapter;
import com.mineblock11.sonance.config.adapters.DynamicConfiguredSoundTypeAdapter;
import com.mineblock11.sonance.sound.ConfiguredSound;
import com.mineblock11.sonance.sound.DynamicConfiguredSound;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class SonanceConfig {
    public static void loadAll() {
        // Migrate old config if it exists.
        Path oldConfigPath = FabricLoader.getInstance().getConfigDir().resolve("sonance.config.json");
        if(oldConfigPath.toFile().exists()) {
            try {
                Files.copy(oldConfigPath, FabricLoader.getInstance().getConfigDir().resolve("sonance.ui.json"), StandardCopyOption.REPLACE_EXISTING);
                Files.delete(oldConfigPath);
            } catch (IOException e) {
                SonanceClient.LOGGER.error("Failed to migrate config from old location to new location.", e);
            }
        }

        UISoundConfig.load();
        GameplaySoundConfig.load();
    }

    public static RegistryEntry.Reference<SoundEvent> getSoundEventReference(SoundEvent soundEvent) {
        return Registries.SOUND_EVENT.getEntry(RegistryKey.of(Registries.SOUND_EVENT.getKey(), soundEvent.getId())).get();
    }
}
