package dev.imb11.sounds.config;

import dev.imb11.sounds.sound.ConfiguredSound;
import dev.imb11.sounds.sound.DynamicConfiguredSound;
import dev.imb11.sounds.sound.context.RepeaterSoundContext;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.impl.controller.BooleanControllerBuilderImpl;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class WorldSoundsConfig extends ConfigGroup implements YetAnotherConfigLib.ConfigBackedBuilder<WorldSoundsConfig> {
    /// == MECHANICS == ///
    public DynamicConfiguredSound<Integer, RepeaterSoundContext> repeaterUseSoundEffect = new DynamicConfiguredSound<>("repeaterUse", SoundEvents.BLOCK_NOTE_BLOCK_HAT, true, 1.0F, 0.45F, true);
    @SerialEntry
    public ConfiguredSound jukeboxUseSoundEffect = new ConfiguredSound("jukeboxUse", SoundEvents.BLOCK_NOTE_BLOCK_BASEDRUM, true, 0.8F, 0.75F);
    @SerialEntry
    public ConfiguredSound daylightDetectorUseSoundEffect = new ConfiguredSound("daylightDetectorUse", SoundEvents.BLOCK_NOTE_BLOCK_HAT, true, 0.8F, 0.45F);
    @SerialEntry
    public ConfiguredSound furnaceMinecartFuelSoundEffect = new ConfiguredSound("furnaceMinecartFuel", SoundEvents.ENTITY_CREEPER_HURT, true, 1.9F, 0.2F);

    /// == ACTIONS == ///
    @SerialEntry
    public boolean enableDynamicEatingSounds = true;
    @SerialEntry
    public ConfiguredSound frostWalkerSoundEffect = new ConfiguredSound("frostWalker", SoundEvents.BLOCK_GLASS_HIT, true, 1.0F, 0.5F);
    @SerialEntry
    public ConfiguredSound leadSnappingSoundEffect = new ConfiguredSound("leadSnapping", SoundEvents.ENTITY_LEASH_KNOT_BREAK, true, 1.0F, 0.5F);
    @SerialEntry
    public ConfiguredSound bowPullSoundEffect = new ConfiguredSound("bowPull", SoundEvents.ITEM_CROSSBOW_LOADING_MIDDLE, true, 1.0F, 0.25F);

    /// == MOBS == ///
    @SerialEntry
    public ConfiguredSound babyChickenChirpSoundEffect = new ConfiguredSound("babyChickenChirp", SoundEvents.ENTITY_PARROT_AMBIENT, true, 2.0F, 0.5F);
    @SerialEntry
    public ConfiguredSound wolfHowlingSoundEffect = new ConfiguredSound("wolfHowling", SoundEvents.ENTITY_WOLF_HOWL, true, 1.0F, 0.2F);

    public WorldSoundsConfig() {
        super(WorldSoundsConfig.class);
    }

    @Override
    public WorldSoundsConfig get() {
        return super.get();
    }

    @Override
    public YetAnotherConfigLib getYACL() {
        return YetAnotherConfigLib.create(getHandler(), this::build);
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
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.translatable("sounds.config.enableDynamicEatingSounds.name"))
                                .description(OptionDescription.of(Text.translatable("sounds.config.enableDynamicEatingSounds.description")))
                                .binding(defaults.enableDynamicEatingSounds, () -> config.enableDynamicEatingSounds, (v) -> config.enableDynamicEatingSounds = v)
                                .controller((opt) -> BooleanControllerBuilder.create(opt).coloured(true).yesNoFormatter())
                                .build())
                        .group(config.frostWalkerSoundEffect.getOptionGroup(defaults.frostWalkerSoundEffect))
                        .group(config.leadSnappingSoundEffect.getOptionGroup(defaults.leadSnappingSoundEffect))
                        .group(config.bowPullSoundEffect.getOptionGroup(defaults.bowPullSoundEffect))
                .build());

        builder.category(ConfigCategory.createBuilder()
                        .name(Text.translatable("sounds.config.world.mobs"))
                        .group(config.babyChickenChirpSoundEffect.getOptionGroup(defaults.babyChickenChirpSoundEffect))
                        .group(config.wolfHowlingSoundEffect.getOptionGroup(defaults.wolfHowlingSoundEffect))
                .build());

        return builder;
    }
}
