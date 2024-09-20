package dev.imb11.sounds.config;

import dev.imb11.sounds.config.utils.ConfigGroup;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

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
    public Identifier getImage() {
        return Identifier.of("sounds", "textures/gui/chat_sounds.webp");
    }

    @Override
    public Text getName() {
        return Text.translatable("sounds.config.chat");
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
