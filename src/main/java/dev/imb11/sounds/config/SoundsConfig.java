package dev.imb11.sounds.config;

import dev.imb11.sounds.SoundsClient;
import dev.imb11.sounds.config.utils.ConfigGroup;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

public class SoundsConfig {
    private static final HashMap<Class<?>, ConfigGroup> CONFIG_GROUPS = new HashMap<>();

    static {
        addGroup(new WorldSoundsConfig());
        addGroup(new ChatSoundsConfig());
        addGroup(new EventSoundsConfig());
        addGroup(new UISoundsConfig());
    }

    private static void addGroup(ConfigGroup group) {
        CONFIG_GROUPS.put(group.getClass(), group);
    }

    public static void loadAll() {
        CONFIG_GROUPS.values().forEach(ConfigGroup::load);
    }

    public static ConfigGroup[] getAll() {
        return CONFIG_GROUPS.values().toArray(new ConfigGroup[0]);
    }

    public static <T extends ConfigGroup> T getRaw(Class<T> clazz) {
        if (CONFIG_GROUPS.containsKey(clazz)) {
            return (T) CONFIG_GROUPS.get(clazz);
        }

        throw new IllegalArgumentException("No config group found for class " + clazz.getName());
    }

    public static <T extends ConfigGroup> T get(Class<T> clazz) {
        if (CONFIG_GROUPS.containsKey(clazz)) {
            return (T) CONFIG_GROUPS.get(clazz).getHandler().instance();
        }

        throw new IllegalArgumentException("No config group found for class " + clazz.getName());
    }

    public static RegistryEntry.Reference<SoundEvent> getSoundEventReference(SoundEvent soundEvent) {
        return Registries.SOUND_EVENT.getEntry(RegistryKey.of(Registries.SOUND_EVENT.getKey(), soundEvent.getId())).get();
    }
}
