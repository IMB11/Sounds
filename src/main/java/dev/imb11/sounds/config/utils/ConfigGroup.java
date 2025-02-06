package dev.imb11.sounds.config.utils;

import com.google.gson.GsonBuilder;
import dev.imb11.mru.LoaderUtils;
import dev.imb11.sounds.api.config.TagPair;
import dev.imb11.sounds.api.config.adapters.ConfiguredSoundTypeAdapter;
import dev.imb11.sounds.api.config.adapters.DynamicConfiguredSoundTypeAdapter;
import dev.imb11.sounds.api.config.adapters.TagPairTypeAdapter;
import dev.imb11.sounds.config.adapters.HotbarConfiguredSoundTypeAdapter;
import dev.imb11.sounds.config.adapters.InventoryConfiguredSoundTypeAdapter;
import dev.imb11.sounds.api.config.ConfiguredSound;
import dev.imb11.sounds.api.config.DynamicConfiguredSound;
import dev.imb11.sounds.sound.HotbarDynamicConfiguredSound;
import dev.imb11.sounds.sound.InventoryDynamicConfiguredSound;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public abstract class ConfigGroup<T extends ConfigGroup> {
    private ConfigClassHandler<?> handler;
    private final Class<T> clazz;

    protected ConfigGroup(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void save() {
        handler.save();
    }

    public void load() {
        if(handler == null) {
            handler = ConfigClassHandler
                    .createBuilder(clazz)
                    .id(ResourceLocation.fromNamespaceAndPath("sounds", getID()))
                    .serializer(config -> {
                        var builder = GsonConfigSerializerBuilder
                                .create(config)
                                .setPath(LoaderUtils.getGameDir().resolve("config/").resolve("sounds/" + getID() + ".json"))
                                .appendGsonBuilder(GsonBuilder::setPrettyPrinting);

                        builder = builder.appendGsonBuilder(gson -> {
                            gson.registerTypeAdapter(ConfiguredSound.class, new ConfiguredSoundTypeAdapter());
                            gson.registerTypeAdapter(DynamicConfiguredSound.class, new DynamicConfiguredSoundTypeAdapter());
                            gson.registerTypeAdapter(HotbarDynamicConfiguredSound.class, new HotbarConfiguredSoundTypeAdapter());
                            gson.registerTypeAdapter(InventoryDynamicConfiguredSound.class, new InventoryConfiguredSoundTypeAdapter());
                            gson.registerTypeAdapter(TagPair.class, new TagPairTypeAdapter());
                            return gson;
                        });

                        return builder.build();
                    })
                    .build();
        }

        handler.load();
    }

    public <T extends ConfigGroup> T get() {
        return (T) handler.instance();
    }

    public <T extends ConfigGroup> ConfigClassHandler<T> getHandler() {
        return (ConfigClassHandler<T>) handler;
    }

    public abstract YetAnotherConfigLib getYACL();

    public abstract ResourceLocation getImage();

    public abstract Component getName();

    public abstract String getID();
}
