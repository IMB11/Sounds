package dev.imb11.sounds.config;

import dev.imb11.mru.yacl.EntryType;
import dev.imb11.sounds.SoundsClient;
import dev.imb11.sounds.api.config.ConfiguredSound;
import dev.imb11.sounds.api.config.DynamicConfiguredSound;
import dev.imb11.sounds.config.utils.ConfigGroup;
import dev.imb11.sounds.sound.context.RepeaterSoundContext;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.DropdownStringControllerBuilder;
import dev.isxander.yacl3.api.controller.ItemControllerBuilder;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;

import static dev.imb11.sounds.config.SoundsConfig.HELPER;

public class WorldSoundsConfig extends ConfigGroup<WorldSoundsConfig> implements YetAnotherConfigLib.ConfigBackedBuilder<WorldSoundsConfig> {
    /// == MECHANICS == ///
    @SerialEntry
    public DynamicConfiguredSound<Integer, RepeaterSoundContext> repeaterUseSoundEffect = new DynamicConfiguredSound<>("repeaterUse", SoundEvents.STONE_BUTTON_CLICK_ON, true, 0.95F, 0.3F, true);
    @SerialEntry
    public ConfiguredSound jukeboxUseSoundEffect = new ConfiguredSound("jukeboxUse", SoundEvents.NOTE_BLOCK_BASEDRUM, true, 0.8F, 0.75F);
    @SerialEntry
    public ConfiguredSound daylightDetectorUseSoundEffect = new ConfiguredSound("daylightDetectorUse", SoundEvents.NOTE_BLOCK_HAT, true, 0.8F, 0.45F);
    @SerialEntry
    public ConfiguredSound furnaceMinecartFuelSoundEffect = new ConfiguredSound("furnaceMinecartFuel", SoundEvents.CREEPER_HURT, true, 1.9F, 0.2F);

    /// == ACTIONS == ///
//    @SerialEntry
//    public ConfiguredSound swordSwoopSoundEffect = new ConfiguredSound("swordSwoop", CustomSounds.BLOCK_SWORD_SWOOP, true, 1.0F, 1.0F);
    @SerialEntry
    public ConfiguredSound frostWalkerSoundEffect = new ConfiguredSound("frostWalker", SoundEvents.POWDER_SNOW_FALL, true, 2.0F, 0.5F);
    @SerialEntry
    public ConfiguredSound leadSnappingSoundEffect = new ConfiguredSound("leadSnapping", SoundEvents.LEASH_KNOT_BREAK, true, 1.0F, 0.5F);
    @SerialEntry
    public ConfiguredSound bowPullSoundEffect = new ConfiguredSound("bowPull", SoundEvents.CROSSBOW_LOADING_MIDDLE, true, 1.0F, 0.25F);
    @SerialEntry
    public ConfiguredSound plantPotFillSoundEffect = new ConfiguredSound("plantPotFill", SoundEvents.GRASS_PLACE, true, 0.5F, 0.4F);
    @SerialEntry
    public ConfiguredSound cakeEatSoundEffect = new ConfiguredSound("cakeEat", SoundEvents.GENERIC_EAT, true, 1.2F, 0.7F);
    @SerialEntry
    public boolean enableEnderpearlVariety = true;
    @SerialEntry
    public boolean disableBlocksEntirely = false;
    @SerialEntry
    public List<String> ignoredBlocks = new ArrayList<>();

    public WorldSoundsConfig() {
        super(WorldSoundsConfig.class);
    }

    @Override
    public YetAnotherConfigLib getYACL() {
        return YetAnotherConfigLib.create(getHandler(), this);
    }

    @Override
    public ResourceLocation getIcon() {
        return ResourceLocation.fromNamespaceAndPath("sounds", "textures/gui/world_sounds.png");
    }

    @Override
    public Component getName() {
        return Component.translatable("sounds.config.world");
    }

    @Override
    public String getID() {
        return "world";
    }

    @Override
    public YetAnotherConfigLib.Builder build(WorldSoundsConfig defaults, WorldSoundsConfig config, YetAnotherConfigLib.Builder builder) {
        builder.title(Component.nullToEmpty("World Sounds"));
        builder.category(ConfigCategory.createBuilder()
                .name(Component.translatable("sounds.config.world.mechanics"))
                .group(config.repeaterUseSoundEffect.getOptionGroup(defaults.repeaterUseSoundEffect))
                .group(config.jukeboxUseSoundEffect.getOptionGroup(defaults.jukeboxUseSoundEffect))
                .group(config.daylightDetectorUseSoundEffect.getOptionGroup(defaults.daylightDetectorUseSoundEffect))
                .group(config.furnaceMinecartFuelSoundEffect.getOptionGroup(defaults.furnaceMinecartFuelSoundEffect))
                .build());
        builder.category(ConfigCategory.createBuilder()
                .name(Component.translatable("sounds.config.world.blocks"))
                .option(HELPER.get("disableBlocksEntirely", defaults.disableBlocksEntirely, () -> config.disableBlocksEntirely, v1 -> config.disableBlocksEntirely = v1))
                .option(LabelOption.create(Component.translatable("sounds.config.world.blocks.description")))
                .option(ButtonOption.createBuilder()
                        .name(Component.nullToEmpty("Open Wiki"))
                        .description(OptionDescription.EMPTY)
                        .action((screen, option) -> Util.getPlatform().openUri("https://docs.imb11.dev/sounds/data/custom-block-sounds"))
                        .build())
                .option(LabelOption.create(Component.empty()))
                .option(ListOption.<String>createBuilder()
                        .name(HELPER.getText(EntryType.OPTION_NAME, "ignoredBlocks"))
                        .description(OptionDescription.of(HELPER.getText(EntryType.OPTION_DESCRIPTION, "ignoredBlocks")))
                        .binding(defaults.ignoredBlocks, () -> config.ignoredBlocks, (val) -> config.ignoredBlocks = val)
                        .controller(opt -> DropdownStringControllerBuilder.create(opt)
                                .allowEmptyValue(false)
                                .values(BuiltInRegistries.BLOCK.registryKeySet().stream()
                                        .map(ResourceKey::location)
                                        .map(ResourceLocation::toString).toList()))
                        .initial("minecraft:grass_block")
                        .build()
                )
                .build());
        builder.category(ConfigCategory.createBuilder()
                .name(Component.translatable("sounds.config.world.actions"))
                .option(HELPER.get("enableEnderpearlVariety", defaults.enableEnderpearlVariety, () -> config.enableEnderpearlVariety, v -> config.enableEnderpearlVariety = v))
//                .group(config.swordSwoopSoundEffect.getOptionGroup(defaults.swordSwoopSoundEffect))
                .group(config.frostWalkerSoundEffect.getOptionGroup(defaults.frostWalkerSoundEffect))
                .group(config.leadSnappingSoundEffect.getOptionGroup(defaults.leadSnappingSoundEffect))
                .group(config.bowPullSoundEffect.getOptionGroup(defaults.bowPullSoundEffect))
                .group(config.plantPotFillSoundEffect.getOptionGroup(defaults.plantPotFillSoundEffect))
                .group(config.cakeEatSoundEffect.getOptionGroup(defaults.cakeEatSoundEffect))
                .build());

        return builder;
    }
}
