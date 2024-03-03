package dev.imb11.sounds.config;

import com.google.gson.GsonBuilder;
import com.mineblock11.mru.config.YACLHelper;
import dev.imb11.sounds.config.adapters.ConfiguredSoundTypeAdapter;
import dev.imb11.sounds.config.adapters.DynamicConfiguredSoundTypeAdapter;
import dev.imb11.sounds.sound.ConfiguredSound;
import dev.imb11.sounds.sound.DynamicConfiguredSound;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public abstract class ConfigGroup {
    private final ConfigClassHandler<? extends ConfigGroup> handler;
    private final YACLHelper.NamespacedHelper namespacedHelper;

    protected ConfigGroup(Class<? extends ConfigGroup> clazz) {
        this.namespacedHelper = new YACLHelper.NamespacedHelper("sounds." + getID());
        handler = namespacedHelper.createHandler(clazz, new ArrayList<UnaryOperator<GsonBuilder>>(List.of(
                gsonBuilder -> gsonBuilder.registerTypeAdapter(ConfiguredSound.class, new ConfiguredSoundTypeAdapter()),
                builder -> builder.registerTypeAdapter(DynamicConfiguredSound.class, new DynamicConfiguredSoundTypeAdapter())
        )));
    }

    public void load() {
        handler.load();
    }

    public <T extends ConfigGroup> T get() {
        return (T) handler.instance();
    }
    public <T extends ConfigGroup> ConfigClassHandler<T> getHandler() {
        return (ConfigClassHandler<T>) handler;
    }

    private YACLHelper.NamespacedHelper getNamespacedHelper() {
        return namespacedHelper;
    }

    public abstract YetAnotherConfigLib getYACL();
    public abstract Identifier getImage();
    public abstract Text getName();
    public abstract String getID();
}
