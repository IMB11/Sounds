package dev.imb11.sounds.config;

import dev.imb11.sounds.config.utils.ConfigGroup;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import static dev.imb11.sounds.config.SoundsConfig.HELPER;

public class ModConfig extends ConfigGroup<ModConfig> implements YetAnotherConfigLib.ConfigBackedBuilder<ModConfig> {
    @SerialEntry
    public boolean hideSoundsButtonInSoundMenu = false;
    @SerialEntry
    public boolean enableVerboseSoundTypeLogging = false;

    public ModConfig() {
        super(ModConfig.class);
    }

    @Override
    public YetAnotherConfigLib getYACL() {
        return YetAnotherConfigLib.create(getHandler(), this);
    }

    @Override
    public ResourceLocation getIcon() {
        return ResourceLocation.fromNamespaceAndPath("sounds", "textures/gui/mod_options.png");
    }

    @Override
    public Component getName() {
        return Component.translatable("sounds.config.mod");
    }

    @Override
    public String getID() {
        return "mod_utils";
    }

    @Override
    public YetAnotherConfigLib.Builder build(ModConfig defaults, ModConfig config, YetAnotherConfigLib.Builder builder) {
        return builder.title(getName())
                .category(ConfigCategory.createBuilder()
                        .name(getName())
                        .option(HELPER.get("hideSoundsButtonInSoundMenu", defaults.hideSoundsButtonInSoundMenu, () -> hideSoundsButtonInSoundMenu, val -> hideSoundsButtonInSoundMenu = val, false)).build());
    }
}
