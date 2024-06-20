package dev.imb11.sounds.config;

import dev.imb11.sounds.api.config.ConfiguredSound;
import dev.imb11.sounds.api.config.DynamicConfiguredSound;
import dev.imb11.sounds.config.utils.ConfigGroup;
import dev.imb11.sounds.config.utils.ConfigUtil;
import dev.imb11.sounds.api.config.TagPair;
import dev.imb11.sounds.sound.CustomSounds;
import dev.imb11.sounds.sound.context.RepeaterSoundContext;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

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
//    @SerialEntry
//    public ConfiguredSound swordSwoopSoundEffect = new ConfiguredSound("swordSwoop", CustomSounds.BLOCK_SWORD_SWOOP, true, 1.0F, 1.0F);
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

    /// == BLOCKS == ///
    @SerialEntry public final TagPair ACACIA_LEAVES = registerTagPair("acacia_leaves", 1.0F, 1.0F, CustomSounds.BLOCK_ACACIA_LEAVES_BREAK, CustomSounds.BLOCK_ACACIA_LEAVES_STEP, CustomSounds.BLOCK_ACACIA_LEAVES_PLACE, CustomSounds.BLOCK_ACACIA_LEAVES_HIT, CustomSounds.BLOCK_ACACIA_LEAVES_FALL);
    @SerialEntry public final TagPair ACACIA_LOG = registerTagPair("acacia_log", 1.0F, 1.0F, CustomSounds.BLOCK_ACACIA_LOG_BREAK, CustomSounds.BLOCK_ACACIA_LOG_STEP, CustomSounds.BLOCK_ACACIA_LOG_PLACE, CustomSounds.BLOCK_ACACIA_LOG_HIT, CustomSounds.BLOCK_ACACIA_LOG_FALL);
    @SerialEntry public final TagPair ACACIA_OBJECT = registerTagPair("acacia_object", 1.0F, 1.0F, CustomSounds.BLOCK_ACACIA_PLANKS_BREAK, CustomSounds.BLOCK_ACACIA_PLANKS_STEP, CustomSounds.BLOCK_ACACIA_PLANKS_PLACE, CustomSounds.BLOCK_ACACIA_PLANKS_HIT, CustomSounds.BLOCK_ACACIA_PLANKS_FALL);
    @SerialEntry public final TagPair ACACIA_PLANKS = registerTagPair("acacia_planks", 1.0F, 1.0F, CustomSounds.BLOCK_ACACIA_PLANKS_BREAK, CustomSounds.BLOCK_ACACIA_PLANKS_STEP, CustomSounds.BLOCK_ACACIA_PLANKS_PLACE, CustomSounds.BLOCK_ACACIA_PLANKS_HIT, CustomSounds.BLOCK_ACACIA_PLANKS_FALL);
    @SerialEntry public final TagPair BARREL = registerTagPair("barrel", 1.0F, 1.0F, SoundEvents.BLOCK_WOOD_BREAK, CustomSounds.BLOCK_BARREL_STEP, CustomSounds.BLOCK_BARREL_PLACE, CustomSounds.BLOCK_BARREL_HIT, SoundEvents.BLOCK_WOOD_FALL);
    @SerialEntry public final TagPair BEEHIVE = registerTagPair("beehive", 1.5F, 1.0F, CustomSounds.BLOCK_BEEHIVE_BREAK, CustomSounds.BLOCK_BOOKSHELF_STEP, CustomSounds.BLOCK_BEEHIVE_PLACE, CustomSounds.BLOCK_BEEHIVE_HIT, SoundEvents.BLOCK_WOOD_FALL);
    @SerialEntry public final TagPair BIRCH_LEAVES = registerTagPair("birch_leaves", 1.0F, 1.0F, CustomSounds.BLOCK_BIRCH_LEAVES_BREAK, CustomSounds.BLOCK_BIRCH_LEAVES_STEP, CustomSounds.BLOCK_BIRCH_LEAVES_PLACE, CustomSounds.BLOCK_BIRCH_LEAVES_HIT, CustomSounds.BLOCK_BIRCH_LEAVES_FALL);
    @SerialEntry public final TagPair BIRCH_LOG = registerTagPair("birch_log", 1.0F, 1.0F, CustomSounds.BLOCK_BIRCH_LOG_BREAK, CustomSounds.BLOCK_BIRCH_LOG_STEP, CustomSounds.BLOCK_BIRCH_LOG_PLACE, CustomSounds.BLOCK_BIRCH_LOG_HIT, CustomSounds.BLOCK_BIRCH_LOG_FALL);
    @SerialEntry public final TagPair BIRCH_OBJECT = registerTagPair("birch_object", 1.0F, 1.0F, CustomSounds.BLOCK_BIRCH_OBJECT_BREAK, CustomSounds.BLOCK_BIRCH_OBJECT_STEP, CustomSounds.BLOCK_BIRCH_OBJECT_PLACE, CustomSounds.BLOCK_BIRCH_PLANKS_HIT, CustomSounds.BLOCK_BIRCH_OBJECT_FALL);
    @SerialEntry public final TagPair BIRCH_PLANKS = registerTagPair("birch_planks", 1.0F, 1.0F, CustomSounds.BLOCK_BIRCH_PLANKS_BREAK, CustomSounds.BLOCK_BIRCH_PLANKS_STEP, CustomSounds.BLOCK_BIRCH_PLANKS_PLACE, CustomSounds.BLOCK_BIRCH_PLANKS_HIT, CustomSounds.BLOCK_BIRCH_PLANKS_FALL);
    @SerialEntry public final TagPair BOOKSHELF = registerTagPair("bookshelf", 1.5F, 1.0F, CustomSounds.BLOCK_BOOKSHELF_BREAK, CustomSounds.BLOCK_BOOKSHELF_STEP, CustomSounds.BLOCK_BOOKSHELF_PLACE, CustomSounds.BLOCK_BOOKSHELF_HIT, SoundEvents.BLOCK_WOOD_FALL);
    @SerialEntry public final TagPair CHEST = registerTagPair("chest", 1.5F, 1.0F, SoundEvents.BLOCK_WOOD_BREAK, CustomSounds.BLOCK_CHEST_STEP, CustomSounds.BLOCK_BARREL_PLACE, CustomSounds.BLOCK_CHEST_HIT, SoundEvents.BLOCK_WOOD_FALL);
    @SerialEntry public final TagPair CLAY = registerTagPair("clay", 1.0F, 1.0F, CustomSounds.BLOCK_CLAY_BREAK, CustomSounds.BLOCK_CLAY_STEP, CustomSounds.BLOCK_CLAY_PLACE, CustomSounds.BLOCK_CLAY_HIT, SoundEvents.BLOCK_MUD_FALL);
    @SerialEntry public final TagPair CLAY_BRICKS = registerTagPair("clay_bricks", 1.0F, 1.3F, SoundEvents.BLOCK_NETHER_BRICKS_BREAK, SoundEvents.BLOCK_NETHER_BRICKS_STEP, SoundEvents.BLOCK_NETHER_BRICKS_PLACE, SoundEvents.BLOCK_NETHER_BRICKS_HIT, SoundEvents.BLOCK_NETHER_BRICKS_FALL);
    @SerialEntry public final TagPair COBBLESTONE = registerTagPair("cobblestone", 1.0F, 1.0F, CustomSounds.BLOCK_COBBLESTONE_BREAK, CustomSounds.BLOCK_COBBLESTONE_STEP, CustomSounds.BLOCK_COBBLESTONE_PLACE, CustomSounds.BLOCK_COBBLESTONE_HIT, CustomSounds.BLOCK_COBBLESTONE_FALL);
    @SerialEntry public final TagPair COPPER_ORE = registerTagPair("copper_ore", 1.0F, 1.0F, CustomSounds.BLOCK_COPPER_ORE_BREAK, CustomSounds.BLOCK_COPPER_ORE_STEP, CustomSounds.BLOCK_COPPER_ORE_PLACE, CustomSounds.BLOCK_COPPER_ORE_HIT, CustomSounds.BLOCK_COPPER_ORE_FALL);
    @SerialEntry public final TagPair DARK_PRISMARINE = registerTagPair("dark_prismarine", 1.0F, 1.0F, SoundEvents.BLOCK_DEEPSLATE_TILES_BREAK, SoundEvents.BLOCK_DEEPSLATE_TILES_STEP, SoundEvents.BLOCK_DEEPSLATE_TILES_PLACE, SoundEvents.BLOCK_DEEPSLATE_TILES_HIT, SoundEvents.BLOCK_DEEPSLATE_TILES_FALL);
    @SerialEntry public final TagPair DEEPSLATE_COPPER_ORE = registerTagPair("deepslate_copper_ore", 1.0F, 1.0F, CustomSounds.BLOCK_COPPER_ORE_BREAK, CustomSounds.BLOCK_DEEPSLATE_COPPER_ORE_STEP, CustomSounds.BLOCK_DEEPSLATE_COPPER_ORE_PLACE, CustomSounds.BLOCK_DEEPSLATE_COPPER_ORE_HIT, CustomSounds.BLOCK_DEEPSLATE_COPPER_ORE_FALL);
    @SerialEntry public final TagPair DEEPSLATE_GOLD_ORE = registerTagPair("deepslate_gold_ore", 1.0F, 1.0F, CustomSounds.BLOCK_DEEPSLATE_GOLD_ORE_BREAK, CustomSounds.BLOCK_DEEPSLATE_GOLD_ORE_STEP, CustomSounds.BLOCK_DEEPSLATE_GOLD_ORE_PLACE, CustomSounds.BLOCK_DEEPSLATE_GOLD_ORE_HIT, CustomSounds.BLOCK_DEEPSLATE_GOLD_ORE_FALL);
    @SerialEntry public final TagPair DEEPSLATE_IRON_ORE = registerTagPair("deepslate_iron_ore", 1.0F, 1.0F, CustomSounds.BLOCK_DEEPSLATE_IRON_ORE_BREAK, CustomSounds.BLOCK_DEEPSLATE_IRON_ORE_STEP, CustomSounds.BLOCK_DEEPSLATE_IRON_ORE_PLACE, CustomSounds.BLOCK_DEEPSLATE_IRON_ORE_HIT, CustomSounds.BLOCK_DEEPSLATE_IRON_ORE_FALL);
    @SerialEntry public final TagPair EMERALD_BLOCK = registerTagPair("emerald_block", 1.0F, 1.2F, SoundEvents.BLOCK_BONE_BLOCK_BREAK, SoundEvents.BLOCK_BONE_BLOCK_STEP, CustomSounds.BLOCK_QUARTZ_PLACE, SoundEvents.BLOCK_BONE_BLOCK_HIT, SoundEvents.BLOCK_BONE_BLOCK_FALL);
    @SerialEntry public final TagPair END_STONE = registerTagPair("end_stone", 0.9F, 0.8F, CustomSounds.BLOCK_QUARTZ_BREAK, CustomSounds.BLOCK_QUARTZ_STEP, CustomSounds.BLOCK_QUARTZ_PLACE, SoundEvents.BLOCK_STONE_HIT, SoundEvents.BLOCK_STONE_FALL);
    @SerialEntry public final TagPair END_STONE_BRICKS = registerTagPair("end_stone_bricks", 1.0F, 1.0F, SoundEvents.BLOCK_DEEPSLATE_BRICKS_BREAK, CustomSounds.BLOCK_QUARTZ_STEP, SoundEvents.BLOCK_DEEPSLATE_BRICKS_PLACE, SoundEvents.BLOCK_DEEPSLATE_BRICKS_HIT, SoundEvents.BLOCK_DEEPSLATE_BRICKS_FALL);
    @SerialEntry public final TagPair GLASS = registerTagPair("glass", 1.0F, 1.0F, CustomSounds.BLOCK_GLASS_BREAK, CustomSounds.BLOCK_GLASS_STEP, CustomSounds.BLOCK_GLASS_PLACE, SoundEvents.BLOCK_GLASS_HIT, SoundEvents.BLOCK_GLASS_FALL);
    @SerialEntry public final TagPair GOLD = registerTagPair("gold", 1.0F, 1.6F, SoundEvents.BLOCK_NETHERITE_BLOCK_BREAK, CustomSounds.BLOCK_GOLD_BLOCK_STEP, SoundEvents.BLOCK_NETHERITE_BLOCK_PLACE, SoundEvents.BLOCK_NETHERITE_BLOCK_HIT, SoundEvents.BLOCK_NETHERITE_BLOCK_FALL);
    @SerialEntry public final TagPair GOLD_ORE = registerTagPair("gold_ore", 1.0F, 1.0F, CustomSounds.BLOCK_GOLD_ORE_BREAK, CustomSounds.BLOCK_GOLD_ORE_STEP, CustomSounds.BLOCK_GOLD_ORE_PLACE, CustomSounds.BLOCK_GOLD_ORE_HIT, CustomSounds.BLOCK_GOLD_ORE_FALL);
    @SerialEntry public final TagPair GRAVEL = registerTagPair("gravel", 1.0F, 1.2F, CustomSounds.BLOCK_GRAVEL_BREAK, CustomSounds.BLOCK_GRAVEL_STEP, CustomSounds.BLOCK_GRAVEL_PLACE, CustomSounds.BLOCK_GRAVEL_HIT, SoundEvents.BLOCK_GRAVEL_FALL);
    @SerialEntry public final TagPair HAY_BLOCK = registerTagPair("hay_block", 1.0F, 1.0F, CustomSounds.BLOCK_HAY_BLOCK_BREAK, CustomSounds.BLOCK_HAY_BLOCK_STEP, CustomSounds.BLOCK_HAY_BLOCK_PLACE, CustomSounds.BLOCK_HAY_BLOCK_HIT, SoundEvents.BLOCK_TUFF_FALL);
    @SerialEntry public final TagPair ICE = registerTagPair("ice", 1.0F, 1.0F, CustomSounds.BLOCK_ICE_BREAK, CustomSounds.BLOCK_ICE_STEP, CustomSounds.BLOCK_ICE_PLACE, CustomSounds.BLOCK_ICE_HIT, CustomSounds.BLOCK_ICE_FALL);
    @SerialEntry public final TagPair IRON = registerTagPair("iron", 1.0F, 1.2F, SoundEvents.BLOCK_NETHERITE_BLOCK_BREAK, CustomSounds.BLOCK_IRON_BLOCK_STEP, SoundEvents.BLOCK_NETHERITE_BLOCK_PLACE, SoundEvents.BLOCK_NETHERITE_BLOCK_HIT, SoundEvents.BLOCK_NETHERITE_BLOCK_FALL);
    @SerialEntry public final TagPair IRON_ORE = registerTagPair("iron_ore", 1.0F, 1.0F, CustomSounds.BLOCK_DEEPSLATE_IRON_ORE_BREAK, CustomSounds.BLOCK_IRON_ORE_STEP, CustomSounds.BLOCK_IRON_ORE_PLACE, CustomSounds.BLOCK_IRON_ORE_HIT, CustomSounds.BLOCK_IRON_ORE_FALL);
    @SerialEntry public final TagPair JUNGLE_LEAVES = registerTagPair("jungle_leaves", 1.0F, 1.0F, CustomSounds.BLOCK_JUNGLE_LEAVES_BREAK, CustomSounds.BLOCK_JUNGLE_LEAVES_STEP, CustomSounds.BLOCK_JUNGLE_LEAVES_PLACE, CustomSounds.BLOCK_JUNGLE_LEAVES_HIT, CustomSounds.BLOCK_JUNGLE_LEAVES_FALL);
    @SerialEntry public final TagPair JUNGLE_OBJECT = registerTagPair("jungle_object", 1.0F, 1.0F, CustomSounds.BLOCK_JUNGLE_PLANKS_BREAK, CustomSounds.BLOCK_JUNGLE_PLANKS_STEP, CustomSounds.BLOCK_JUNGLE_PLANKS_PLACE, CustomSounds.BLOCK_SPRUCE_PLANKS_HIT, CustomSounds.BLOCK_JUNGLE_PLANKS_FALL);
    @SerialEntry public final TagPair JUNGLE_PLANKS = registerTagPair("jungle_planks", 1.0F, 1.0F, CustomSounds.BLOCK_JUNGLE_PLANKS_BREAK, CustomSounds.BLOCK_JUNGLE_PLANKS_STEP, CustomSounds.BLOCK_JUNGLE_PLANKS_PLACE, CustomSounds.BLOCK_SPRUCE_PLANKS_HIT, CustomSounds.BLOCK_JUNGLE_PLANKS_FALL);
    @SerialEntry public final TagPair LAPIS_BLOCK = registerTagPair("lapis_block", 1.0F, 1.2F, SoundEvents.BLOCK_BONE_BLOCK_BREAK, SoundEvents.BLOCK_BONE_BLOCK_STEP, SoundEvents.BLOCK_BONE_BLOCK_PLACE, SoundEvents.BLOCK_BONE_BLOCK_HIT, SoundEvents.BLOCK_BONE_BLOCK_FALL);
    @SerialEntry public final TagPair LOOM = registerTagPair("loom", 1.0F, 1.0F, CustomSounds.BLOCK_LOOM_BREAK, CustomSounds.BLOCK_LOOM_STEP, CustomSounds.BLOCK_LOOM_PLACE, CustomSounds.BLOCK_LOOM_HIT, SoundEvents.BLOCK_WOOD_FALL);
    @SerialEntry public final TagPair MAGMA_BLOCK = registerTagPair("magma_block", 1.0F, 1.0F, CustomSounds.BLOCK_MAGMA_BLOCK_BREAK, CustomSounds.BLOCK_MAGMA_BLOCK_STEP, CustomSounds.BLOCK_MAGMA_BLOCK_PLACE, CustomSounds.BLOCK_MAGMA_BLOCK_HIT, CustomSounds.BLOCK_MAGMA_BLOCK_FALL);
    @SerialEntry public final TagPair MANGROVE_LEAVES = registerTagPair("mangrove_leaves", 1.0F, 1.0F, CustomSounds.BLOCK_MANGROVE_LEAVES_BREAK, CustomSounds.BLOCK_MANGROVE_LEAVES_STEP, CustomSounds.BLOCK_MANGROVE_LEAVES_PLACE, CustomSounds.BLOCK_MANGROVE_LEAVES_HIT, CustomSounds.BLOCK_MANGROVE_LEAVES_FALL);
    @SerialEntry public final TagPair MANGROVE_LOG = registerTagPair("mangrove_log", 1.0F, 1.0F, CustomSounds.BLOCK_MANGROVE_LOG_BREAK, CustomSounds.BLOCK_MANGROVE_LOG_STEP, CustomSounds.BLOCK_MANGROVE_LOG_PLACE, CustomSounds.BLOCK_MANGROVE_LOG_HIT, CustomSounds.BLOCK_MANGROVE_LOG_FALL);
    @SerialEntry public final TagPair MANGROVE_OBJECT = registerTagPair("mangrove_object", 1.0F, 1.0F, CustomSounds.BLOCK_MANGROVE_PLANKS_BREAK, CustomSounds.BLOCK_MANGROVE_PLANKS_STEP, CustomSounds.BLOCK_MANGROVE_PLANKS_PLACE, CustomSounds.BLOCK_MANGROVE_PLANKS_HIT, CustomSounds.BLOCK_MANGROVE_PLANKS_FALL);
    @SerialEntry public final TagPair MANGROVE_PLANKS = registerTagPair("mangrove_planks", 1.0F, 1.0F, CustomSounds.BLOCK_MANGROVE_PLANKS_BREAK, CustomSounds.BLOCK_MANGROVE_PLANKS_STEP, CustomSounds.BLOCK_MANGROVE_PLANKS_PLACE, CustomSounds.BLOCK_MANGROVE_PLANKS_HIT, CustomSounds.BLOCK_MANGROVE_PLANKS_FALL);
    @SerialEntry public final TagPair MOSSY_COBBLESTONE = registerTagPair("mossy_cobblestone", 1.0F, 1.0F, CustomSounds.BLOCK_MOSSY_COBBLESTONE_BREAK, CustomSounds.BLOCK_MOSSY_COBBLESTONE_STEP, CustomSounds.BLOCK_COBBLESTONE_PLACE, CustomSounds.BLOCK_MOSSY_COBBLESTONE_HIT, CustomSounds.BLOCK_COBBLESTONE_FALL);
    @SerialEntry public final TagPair MOSSY_STONE_BRICKS = registerTagPair("mossy_stone_bricks", 1.0F, 1.0F, CustomSounds.BLOCK_MOSSY_COBBLESTONE_BREAK, CustomSounds.BLOCK_MOSSY_STONE_BRICKS_STEP, SoundEvents.BLOCK_DEEPSLATE_BRICKS_PLACE, CustomSounds.BLOCK_MOSSY_STONE_BRICKS_HIT, CustomSounds.BLOCK_MOSSY_STONE_BRICKS_FALL);
    @SerialEntry public final TagPair OAK_LOG = registerTagPair("oak_log", 1.0F, 1.0F, CustomSounds.BLOCK_OAK_LOG_BREAK, CustomSounds.BLOCK_OAK_LOG_STEP, CustomSounds.BLOCK_OAK_LOG_PLACE, CustomSounds.BLOCK_OAK_LOG_HIT, CustomSounds.BLOCK_OAK_LOG_FALL);
    @SerialEntry public final TagPair OBSIDIAN = registerTagPair("obsidian", 1.0F, 0.7F, SoundEvents.BLOCK_DEEPSLATE_BREAK, SoundEvents.BLOCK_DEEPSLATE_STEP, SoundEvents.BLOCK_DEEPSLATE_PLACE, SoundEvents.BLOCK_DEEPSLATE_HIT, SoundEvents.BLOCK_DEEPSLATE_FALL);
    @SerialEntry public final TagPair PACKED_ICE = registerTagPair("packed_ice", 1.0F, 1.0F, CustomSounds.BLOCK_PACKED_ICE_BREAK, CustomSounds.BLOCK_PACKED_ICE_STEP, CustomSounds.BLOCK_PACKED_ICE_PLACE, CustomSounds.BLOCK_PACKED_ICE_HIT, CustomSounds.BLOCK_PACKED_ICE_FALL);
    @SerialEntry public final TagPair PRISMARINE = registerTagPair("prismarine", 1.0F, 1.0F, SoundEvents.BLOCK_DEEPSLATE_BREAK, SoundEvents.BLOCK_DEEPSLATE_STEP, SoundEvents.BLOCK_DEEPSLATE_PLACE, SoundEvents.BLOCK_DEEPSLATE_HIT, SoundEvents.BLOCK_DEEPSLATE_FALL);
    @SerialEntry public final TagPair QUARTZ = registerTagPair("quartz", 1.0F, 1.0F, CustomSounds.BLOCK_QUARTZ_BREAK, CustomSounds.BLOCK_QUARTZ_STEP, SoundEvents.BLOCK_DEEPSLATE_PLACE, SoundEvents.BLOCK_STONE_HIT, SoundEvents.BLOCK_DEEPSLATE_FALL);
    @SerialEntry public final TagPair RAW_GOLD_BLOCK = registerTagPair("raw_gold_block", 1.0F, 1.0F, CustomSounds.BLOCK_RAW_GOLD_BLOCK_BREAK, CustomSounds.BLOCK_RAW_GOLD_BLOCK_STEP, CustomSounds.BLOCK_RAW_GOLD_BLOCK_PLACE, CustomSounds.BLOCK_RAW_GOLD_BLOCK_HIT, CustomSounds.BLOCK_RAW_GOLD_BLOCK_FALL);
    @SerialEntry public final TagPair SANDSTONE = registerTagPair("sandstone", 1.0F, 1.0F, SoundEvents.BLOCK_TUFF_BREAK, CustomSounds.BLOCK_SANDSTONE_STEP, SoundEvents.BLOCK_TUFF_PLACE, SoundEvents.BLOCK_TUFF_HIT, SoundEvents.BLOCK_TUFF_FALL);
    @SerialEntry public final TagPair SHEET_METAL = registerTagPair("sheet_metal", 1.0F, 1.2F, CustomSounds.BLOCK_SHEET_METAL_BREAK, CustomSounds.BLOCK_SHEET_METAL_STEP, SoundEvents.BLOCK_NETHERITE_BLOCK_PLACE, SoundEvents.BLOCK_NETHERITE_BLOCK_HIT, SoundEvents.BLOCK_NETHERITE_BLOCK_FALL);
    @SerialEntry public final TagPair SPRUCE_LEAVES = registerTagPair("spruce_leaves", 1.0F, 1.0F, CustomSounds.BLOCK_SPRUCE_LEAVES_BREAK, CustomSounds.BLOCK_SPRUCE_LEAVES_STEP, CustomSounds.BLOCK_SPRUCE_LEAVES_PLACE, CustomSounds.BLOCK_SPRUCE_LEAVES_HIT, CustomSounds.BLOCK_SPRUCE_LEAVES_FALL);
    @SerialEntry public final TagPair SPRUCE_LOG = registerTagPair("spruce_log", 1.0F, 1.0F, CustomSounds.BLOCK_SPRUCE_LOG_BREAK, CustomSounds.BLOCK_SPRUCE_LOG_STEP, CustomSounds.BLOCK_SPRUCE_LOG_PLACE, CustomSounds.BLOCK_SPRUCE_LOG_HIT, CustomSounds.BLOCK_SPRUCE_LOG_FALL);
    @SerialEntry public final TagPair SPRUCE_OBJECT = registerTagPair("spruce_object", 1.0F, 1.0F, CustomSounds.BLOCK_SPRUCE_OBJECT_BREAK, CustomSounds.BLOCK_SPRUCE_PLANKS_STEP, CustomSounds.BLOCK_SPRUCE_OBJECT_PLACE, CustomSounds.BLOCK_SPRUCE_PLANKS_HIT, CustomSounds.BLOCK_SPRUCE_PLANKS_FALL);
    @SerialEntry public final TagPair SPRUCE_PLANKS = registerTagPair("spruce_planks", 1.0F, 1.0F, CustomSounds.BLOCK_SPRUCE_PLANKS_BREAK, CustomSounds.BLOCK_SPRUCE_PLANKS_STEP, CustomSounds.BLOCK_SPRUCE_PLANKS_PLACE, CustomSounds.BLOCK_SPRUCE_PLANKS_HIT, CustomSounds.BLOCK_SPRUCE_PLANKS_FALL);
    @SerialEntry public final TagPair STONE_BRICKS = registerTagPair("stone_bricks", 1.0F, 1.0F, CustomSounds.BLOCK_COBBLESTONE_BREAK, CustomSounds.BLOCK_STONE_BRICKS_STEP, SoundEvents.BLOCK_DEEPSLATE_BRICKS_PLACE, CustomSounds.BLOCK_STONE_BRICKS_HIT, CustomSounds.BLOCK_STONE_BRICKS_FALL);
    @SerialEntry public final TagPair TERRACOTTA = registerTagPair("terracotta", 1.0F, 0.6F, SoundEvents.BLOCK_CALCITE_BREAK, SoundEvents.BLOCK_CALCITE_STEP, SoundEvents.BLOCK_CALCITE_PLACE, SoundEvents.BLOCK_CALCITE_HIT, SoundEvents.BLOCK_CALCITE_FALL);

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

    public List<Supplier<TagPair>> getAllTagPairs() {
        return List.<Supplier<TagPair>>of(
                () -> ACACIA_LEAVES,
                () -> ACACIA_LOG,
                () -> ACACIA_OBJECT,
                () -> ACACIA_PLANKS,
                () -> BARREL,
                () -> BEEHIVE,
                () -> BIRCH_LEAVES,
                () -> BIRCH_LOG,
                () -> BIRCH_OBJECT,
                () -> BIRCH_PLANKS,
                () -> BOOKSHELF,
                () -> CHEST,
                () -> CLAY,
                () -> CLAY_BRICKS,
                () -> COBBLESTONE,
                () -> COPPER_ORE,
                () -> DARK_PRISMARINE,
                () -> DEEPSLATE_COPPER_ORE,
                () -> DEEPSLATE_GOLD_ORE,
                () -> DEEPSLATE_IRON_ORE,
                () -> EMERALD_BLOCK,
                () -> END_STONE,
                () -> END_STONE_BRICKS,
                () -> GLASS,
                () -> GOLD,
                () -> GOLD_ORE,
                () -> GRAVEL,
                () -> HAY_BLOCK,
                () -> ICE,
                () -> IRON,
                () -> IRON_ORE,
                () -> JUNGLE_LEAVES,
                () -> JUNGLE_OBJECT,
                () -> JUNGLE_PLANKS,
                () -> LAPIS_BLOCK,
                () -> LOOM,
                () -> MAGMA_BLOCK,
                () -> MANGROVE_LEAVES,
                () -> MANGROVE_LOG,
                () -> MANGROVE_OBJECT,
                () -> MANGROVE_PLANKS,
                () -> MOSSY_COBBLESTONE,
                () -> MOSSY_STONE_BRICKS,
                () -> OAK_LOG,
                () -> OBSIDIAN,
                () -> PACKED_ICE,
                () -> PRISMARINE,
                () -> QUARTZ,
                () -> RAW_GOLD_BLOCK,
                () -> SANDSTONE,
                () -> SHEET_METAL,
                () -> SPRUCE_LEAVES,
                () -> SPRUCE_LOG,
                () -> SPRUCE_OBJECT,
                () -> SPRUCE_PLANKS,
                () -> STONE_BRICKS,
                () -> TERRACOTTA);
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

        List<OptionGroup> tagOptionGroups = new ArrayList<>();

        Supplier<TagPair>[] defaultSuppliers = defaults.getAllTagPairs().toArray(Supplier[]::new);
        Supplier<TagPair>[] nonDefaultSuppliers = this.getAllTagPairs().toArray(Supplier[]::new);

        for (int i = 0; i < nonDefaultSuppliers.length; i++) {
            Supplier<TagPair> nonDefaultSupplier = nonDefaultSuppliers[i];
            Supplier<TagPair> defaultSupplier = defaultSuppliers[i];

            TagPair pair = nonDefaultSupplier.get();
            TagPair defaultPair = defaultSupplier.get();

            tagOptionGroups.add(pair.createYACL(defaultPair));
        }

        ConfigCategory.Builder blocksCategory = ConfigCategory.createBuilder()
                .name(Text.translatable("sounds.config.world.blocks"))
                .option(LabelOption.create(Text.translatable("sounds.config.world.blocks.description")))
                .option(ButtonOption.createBuilder()
                        .name(Text.of("Open GitHub Repository"))
                        .description(OptionDescription.EMPTY)
                        .action((screen, option) -> Util.getOperatingSystem().open("https://github.com/IMB11/Sounds/tree/main/src/main/resources/data/sounds/tags/blocks")).build())
                .groups(tagOptionGroups);

        builder.category(blocksCategory.build());

        builder.category(ConfigCategory.createBuilder()
                .name(Text.translatable("sounds.config.world.actions"))
                .option(ConfigUtil.create(defaults.enableEnderpearlVariety, v -> config.enableEnderpearlVariety = v, () -> config.enableEnderpearlVariety, "sounds.config.enableEnderpearlVariety"))
//                .group(config.swordSwoopSoundEffect.getOptionGroup(defaults.swordSwoopSoundEffect))
                .group(config.frostWalkerSoundEffect.getOptionGroup(defaults.frostWalkerSoundEffect))
                .group(config.leadSnappingSoundEffect.getOptionGroup(defaults.leadSnappingSoundEffect))
                .group(config.bowPullSoundEffect.getOptionGroup(defaults.bowPullSoundEffect))
                .group(config.plantPotFillSoundEffect.getOptionGroup(defaults.plantPotFillSoundEffect))
                .group(config.cakeEatSoundEffect.getOptionGroup(defaults.cakeEatSoundEffect))
                .build());

        return builder;
    }

    public static TagPair registerTagPair(String id, float pitch, float volume, SoundEvent breakSound, SoundEvent stepSound, SoundEvent placeSound, SoundEvent hitSound, SoundEvent fallSound) {
        return new TagPair(TagKey.of(RegistryKeys.BLOCK, new Identifier("sounds", id)), new BlockSoundGroup(pitch, volume, breakSound, stepSound, placeSound, hitSound, fallSound));
    }
}
