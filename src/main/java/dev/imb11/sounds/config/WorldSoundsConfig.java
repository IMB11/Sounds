package dev.imb11.sounds.config;

import dev.imb11.sounds.config.utils.ConfigGroup;
import dev.imb11.sounds.sound.ConfiguredSound;
import dev.imb11.sounds.sound.DynamicConfiguredSound;
import dev.imb11.sounds.sound.context.RepeaterSoundContext;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class WorldSoundsConfig extends ConfigGroup<WorldSoundsConfig> implements YetAnotherConfigLib.ConfigBackedBuilder<WorldSoundsConfig> {
    /// == MECHANICS == ///
    @SerialEntry
    public DynamicConfiguredSound<Integer, RepeaterSoundContext> repeaterUseSoundEffect = new DynamicConfiguredSound<>("repeaterUse", SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, true, 0.95F, 0.3F, true);
    @SerialEntry
    public ConfiguredSound jukeboxUseSoundEffect = new ConfiguredSound("jukeboxUse", SoundEvents.BLOCK_NOTE_BLOCK_BASEDRUM, true, 0.8F, 0.75F);
    @SerialEntry
    public ConfiguredSound daylightDetectorUseSoundEffect = new ConfiguredSound("daylightDetectorUse", SoundEvents.BLOCK_NOTE_BLOCK_HAT, true, 0.8F, 0.45F);
    @SerialEntry
    public ConfiguredSound furnaceMinecartFuelSoundEffect = new ConfiguredSound("furnaceMinecartFuel", SoundEvents.ENTITY_CREEPER_HURT, true, 1.9F, 0.2F);

    /// == ACTIONS == ///
    @SerialEntry
    public ConfiguredSound frostWalkerSoundEffect = new ConfiguredSound("frostWalker", SoundEvents.BLOCK_POWDER_SNOW_FALL, true, 2.0F, 0.5F);
    @SerialEntry
    public ConfiguredSound leadSnappingSoundEffect = new ConfiguredSound("leadSnapping", SoundEvents.ENTITY_LEASH_KNOT_BREAK, true, 1.0F, 0.5F);
    @SerialEntry
    public ConfiguredSound bowPullSoundEffect = new ConfiguredSound("bowPull", SoundEvents.ITEM_CROSSBOW_LOADING_MIDDLE, true, 1.0F, 0.25F);
    @SerialEntry
    public ConfiguredSound plantPotFillSoundEffect = new ConfiguredSound("plantPotFill", SoundEvents.BLOCK_GRASS_PLACE, true, 0.5F, 0.4F);
    @SerialEntry
    public ConfiguredSound cakeEatSoundEffect = new ConfiguredSound("cakeEat", SoundEvents.ENTITY_GENERIC_EAT, true, 1.2F, 0.7F);
    @SerialEntry
    public boolean enableEnderpearlVariety = true;

    public WorldSoundsConfig() {
        super(WorldSoundsConfig.class);
    }

    @Override
    public YetAnotherConfigLib getYACL() {
        return YetAnotherConfigLib.create(getHandler(), this);
    }

    @Override
    public Identifier getImage() {
        return new Identifier("sounds", "textures/gui/world_sounds.webp");
    }

    @Override
    public Text getName() {
        return Text.translatable("sounds.config.world");
    }

    @Override
    public String getID() {
        return "world";
    }

    @Override
    public YetAnotherConfigLib.Builder build(WorldSoundsConfig defaults, WorldSoundsConfig config, YetAnotherConfigLib.Builder builder) {
        builder.title(Text.of("World Sounds"));
        builder.category(ConfigCategory.createBuilder()
                .name(Text.translatable("sounds.config.world.mechanics"))
                .group(config.repeaterUseSoundEffect.getOptionGroup(defaults.repeaterUseSoundEffect))
                .group(config.jukeboxUseSoundEffect.getOptionGroup(defaults.jukeboxUseSoundEffect))
                .group(config.daylightDetectorUseSoundEffect.getOptionGroup(defaults.daylightDetectorUseSoundEffect))
                .group(config.furnaceMinecartFuelSoundEffect.getOptionGroup(defaults.furnaceMinecartFuelSoundEffect))
                .build());

        builder.category(ConfigCategory.createBuilder()
                .name(Text.translatable("sounds.config.world.actions"))
                .option(ConfigUtil.create(defaults.enableEnderpearlVariety, v -> config.enableEnderpearlVariety = v, () -> config.enableEnderpearlVariety, "sounds.config.enableEnderpearlVariety"))
                .group(config.frostWalkerSoundEffect.getOptionGroup(defaults.frostWalkerSoundEffect))
                .group(config.leadSnappingSoundEffect.getOptionGroup(defaults.leadSnappingSoundEffect))
                .group(config.bowPullSoundEffect.getOptionGroup(defaults.bowPullSoundEffect))
                .group(config.plantPotFillSoundEffect.getOptionGroup(defaults.plantPotFillSoundEffect))
                .group(config.cakeEatSoundEffect.getOptionGroup(defaults.cakeEatSoundEffect))
                .build());

        return builder;
    }
}
