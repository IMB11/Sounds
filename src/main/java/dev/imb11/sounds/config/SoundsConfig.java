package dev.imb11.sounds.config;

import dev.imb11.sounds.SoundsClient;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class SoundsConfig {
    public static void loadAll() {
        // Migrate old config if it exists.
        Path oldConfigPath = FabricLoader.getInstance().getConfigDir().resolve("sounds.config.json");
        if(oldConfigPath.toFile().exists()) {
            try {
                Files.copy(oldConfigPath, FabricLoader.getInstance().getConfigDir().resolve("sounds.ui.json"), StandardCopyOption.REPLACE_EXISTING);
                Files.delete(oldConfigPath);
            } catch (IOException e) {
                SoundsClient.LOGGER.error("Failed to migrate config from old location to new location.", e);
            }
        }

        UISoundConfig.load();
        GameplaySoundConfig.load();
        CompatConfig.load();
    }

    public static RegistryEntry.Reference<SoundEvent> getSoundEventReference(SoundEvent soundEvent) {
        return Registries.SOUND_EVENT.getEntry(RegistryKey.of(Registries.SOUND_EVENT.getKey(), soundEvent.getId())).get();
    }
}
