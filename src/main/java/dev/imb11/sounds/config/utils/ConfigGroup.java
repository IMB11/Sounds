package dev.imb11.sounds.config.utils;

import com.google.gson.GsonBuilder;
import com.mineblock11.mru.config.YACLHelper;
import dev.imb11.sounds.config.adapters.ConfiguredSoundTypeAdapter;
import dev.imb11.sounds.config.adapters.DynamicConfiguredSoundTypeAdapter;
import dev.imb11.sounds.config.adapters.HotbarConfiguredSoundTypeAdapter;
import dev.imb11.sounds.config.adapters.InventoryConfiguredSoundTypeAdapter;
import dev.imb11.sounds.sound.ConfiguredSound;
import dev.imb11.sounds.sound.DynamicConfiguredSound;
import dev.imb11.sounds.sound.HotbarDynamicConfiguredSound;
import dev.imb11.sounds.sound.InventoryDynamicConfiguredSound;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.ConfigSerializer;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public abstract class ConfigGroup<T extends ConfigGroup> {
    private ConfigClassHandler<?> handler;
    private final Class<T> clazz;

    protected ConfigGroup(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void load() {
        if(handler == null) {
            handler = ConfigClassHandler
                    .createBuilder(clazz)
                    .id(new Identifier("sounds", getID()))
                    .serializer(config -> {
                        var builder = GsonConfigSerializerBuilder
                                .create(config)
                                .setPath(FabricLoader.getInstance().getConfigDir().resolve("sounds/" + getID() + ".json"))
                                .appendGsonBuilder(GsonBuilder::setPrettyPrinting);

                        builder = builder.appendGsonBuilder(gson -> {
                            gson.registerTypeAdapter(ConfiguredSound.class, new ConfiguredSoundTypeAdapter());
                            gson.registerTypeAdapter(DynamicConfiguredSound.class, new DynamicConfiguredSoundTypeAdapter());
                            gson.registerTypeAdapter(HotbarDynamicConfiguredSound.class, new HotbarConfiguredSoundTypeAdapter());
                            gson.registerTypeAdapter(InventoryDynamicConfiguredSound.class, new InventoryConfiguredSoundTypeAdapter());
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

    public abstract Identifier getImage();

    public abstract Text getName();

    public abstract String getID();
}
