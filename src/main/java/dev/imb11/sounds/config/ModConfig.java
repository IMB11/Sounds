package dev.imb11.sounds.config;

import dev.imb11.sounds.config.utils.ConfigGroup;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ModConfig extends ConfigGroup<ModConfig> implements YetAnotherConfigLib.ConfigBackedBuilder<ModConfig> {
    @SerialEntry
    public boolean hideSoundsButtonInSoundMenu = false;

    public ModConfig() {
        super(ModConfig.class);
    }

    @Override
    public YetAnotherConfigLib getYACL() {
        return YetAnotherConfigLib.create(getHandler(), this);
    }

    @Override
    public ResourceLocation getImage() {
        return ResourceLocation.fromNamespaceAndPath("sounds", "textures/gui/chat_sounds.webp");
    }

    @Override
    public Component getName() {
        return Component.translatable("sounds.config.chat");
    }

    @Override
    public String getID() {
        return "mod_utils";
    }

    @Override
    public YetAnotherConfigLib.Builder build(ModConfig defaults, ModConfig config, YetAnotherConfigLib.Builder builder) {
        return builder;
    }
}
