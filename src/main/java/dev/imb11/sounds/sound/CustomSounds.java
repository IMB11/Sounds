package dev.imb11.sounds.sound;

import java.util.function.Supplier;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

@SuppressWarnings("unused")
public class CustomSounds {
    //? if neoforge {
    /*public static final net.neoforged.neoforge.registries.DeferredRegister<SoundEvent> REGISTRY = net.neoforged.neoforge.registries.DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, "sounds");
    *///?}

    public static final Supplier<SoundEvent> ITEM_SWORD_SWOOSH = register("item.sword.swoosh");
    public static final Supplier<SoundEvent> ITEM_HARD_METAL_HOLD = register("item.hard_metal.hold");

    public static final Supplier<SoundEvent> ITEM_SHINY_METAL_HOLD = register("item.shiny_metal.hold");

    public static final Supplier<SoundEvent> BLOCK_ACACIA_LEAVES_BREAK = register("block.acacia_leaves.break");

    public static final Supplier<SoundEvent> BLOCK_ACACIA_LEAVES_FALL = register("block.acacia_leaves.fall");

    public static final Supplier<SoundEvent> BLOCK_ACACIA_LEAVES_HIT = register("block.acacia_leaves.hit");

    public static final Supplier<SoundEvent> BLOCK_ACACIA_LEAVES_PLACE = register("block.acacia_leaves.place");

    public static final Supplier<SoundEvent> BLOCK_ACACIA_LEAVES_STEP = register("block.acacia_leaves.step");

    public static final Supplier<SoundEvent> BLOCK_ACACIA_LOG_BREAK = register("block.acacia_log.break");

    public static final Supplier<SoundEvent> BLOCK_ACACIA_LOG_FALL = register("block.acacia_log.fall");

    public static final Supplier<SoundEvent> BLOCK_ACACIA_LOG_HIT = register("block.acacia_log.hit");

    public static final Supplier<SoundEvent> BLOCK_ACACIA_LOG_PLACE = register("block.acacia_log.place");

    public static final Supplier<SoundEvent> BLOCK_ACACIA_LOG_STEP = register("block.acacia_log.step");

    public static final Supplier<SoundEvent> BLOCK_ACACIA_PLANKS_BREAK = register("block.acacia_planks.break");

    public static final Supplier<SoundEvent> BLOCK_ACACIA_PLANKS_FALL = register("block.acacia_planks.fall");

    public static final Supplier<SoundEvent> BLOCK_ACACIA_PLANKS_HIT = register("block.acacia_planks.hit");

    public static final Supplier<SoundEvent> BLOCK_ACACIA_PLANKS_PLACE = register("block.acacia_planks.place");

    public static final Supplier<SoundEvent> BLOCK_ACACIA_PLANKS_STEP = register("block.acacia_planks.step");

    public static final Supplier<SoundEvent> BLOCK_BARREL_HIT = register("block.barrel.hit");

    public static final Supplier<SoundEvent> BLOCK_BARREL_PLACE = register("block.barrel.place");

    public static final Supplier<SoundEvent> BLOCK_BARREL_STEP = register("block.barrel.step");

    public static final Supplier<SoundEvent> BLOCK_BEEHIVE_BREAK = register("block.beehive.break");

    public static final Supplier<SoundEvent> BLOCK_BEEHIVE_HIT = register("block.beehive.hit");

    public static final Supplier<SoundEvent> BLOCK_BEEHIVE_PLACE = register("block.beehive.place");

    public static final Supplier<SoundEvent> BLOCK_BIRCH_LEAVES_BREAK = register("block.birch_leaves.break");

    public static final Supplier<SoundEvent> BLOCK_BIRCH_LEAVES_FALL = register("block.birch_leaves.fall");

    public static final Supplier<SoundEvent> BLOCK_BIRCH_LEAVES_HIT = register("block.birch_leaves.hit");

    public static final Supplier<SoundEvent> BLOCK_BIRCH_LEAVES_PLACE = register("block.birch_leaves.place");

    public static final Supplier<SoundEvent> BLOCK_BIRCH_LEAVES_STEP = register("block.birch_leaves.step");

    public static final Supplier<SoundEvent> BLOCK_BIRCH_LOG_BREAK = register("block.birch_log.break");

    public static final Supplier<SoundEvent> BLOCK_BIRCH_LOG_FALL = register("block.birch_log.fall");

    public static final Supplier<SoundEvent> BLOCK_BIRCH_LOG_HIT = register("block.birch_log.hit");

    public static final Supplier<SoundEvent> BLOCK_BIRCH_LOG_PLACE = register("block.birch_log.place");

    public static final Supplier<SoundEvent> BLOCK_BIRCH_LOG_STEP = register("block.birch_log.step");

    public static final Supplier<SoundEvent> BLOCK_BIRCH_OBJECT_BREAK = register("block.birch_object.break");

    public static final Supplier<SoundEvent> BLOCK_BIRCH_OBJECT_FALL = register("block.birch_object.fall");

    public static final Supplier<SoundEvent> BLOCK_BIRCH_OBJECT_HIT = register("block.birch_object.hit");

    public static final Supplier<SoundEvent> BLOCK_BIRCH_OBJECT_PLACE = register("block.birch_object.place");

    public static final Supplier<SoundEvent> BLOCK_BIRCH_OBJECT_STEP = register("block.birch_object.step");

    public static final Supplier<SoundEvent> BLOCK_BIRCH_PLANKS_BREAK = register("block.birch_planks.break");

    public static final Supplier<SoundEvent> BLOCK_BIRCH_PLANKS_FALL = register("block.birch_planks.fall");

    public static final Supplier<SoundEvent> BLOCK_BIRCH_PLANKS_HIT = register("block.birch_planks.hit");

    public static final Supplier<SoundEvent> BLOCK_BIRCH_PLANKS_PLACE = register("block.birch_planks.place");

    public static final Supplier<SoundEvent> BLOCK_BIRCH_PLANKS_STEP = register("block.birch_planks.step");

    public static final Supplier<SoundEvent> BLOCK_BOOKSHELF_BREAK = register("block.bookshelf.break");

    public static final Supplier<SoundEvent> BLOCK_BOOKSHELF_HIT = register("block.bookshelf.hit");

    public static final Supplier<SoundEvent> BLOCK_BOOKSHELF_PLACE = register("block.bookshelf.place");

    public static final Supplier<SoundEvent> BLOCK_BOOKSHELF_STEP = register("block.bookshelf.step");

    public static final Supplier<SoundEvent> BLOCK_CHEST_HIT = register("block.chest.hit");

    public static final Supplier<SoundEvent> BLOCK_CHEST_PLACE = register("block.chest.place");

    public static final Supplier<SoundEvent> BLOCK_CHEST_STEP = register("block.chest.step");

    public static final Supplier<SoundEvent> BLOCK_CLAY_BREAK = register("block.clay.break");

    public static final Supplier<SoundEvent> BLOCK_CLAY_HIT = register("block.clay.hit");

    public static final Supplier<SoundEvent> BLOCK_CLAY_PLACE = register("block.clay.place");

    public static final Supplier<SoundEvent> BLOCK_CLAY_STEP = register("block.clay.step");

    public static final Supplier<SoundEvent> BLOCK_COBBLESTONE_BREAK = register("block.cobblestone.break");

    public static final Supplier<SoundEvent> BLOCK_COBBLESTONE_FALL = register("block.cobblestone.fall");

    public static final Supplier<SoundEvent> BLOCK_COBBLESTONE_HIT = register("block.cobblestone.hit");

    public static final Supplier<SoundEvent> BLOCK_COBBLESTONE_PLACE = register("block.cobblestone.place");

    public static final Supplier<SoundEvent> BLOCK_COBBLESTONE_STEP = register("block.cobblestone.step");

    public static final Supplier<SoundEvent> BLOCK_COPPER_ORE_BREAK = register("block.copper_ore.break");

    public static final Supplier<SoundEvent> BLOCK_COPPER_ORE_FALL = register("block.copper_ore.fall");

    public static final Supplier<SoundEvent> BLOCK_COPPER_ORE_HIT = register("block.copper_ore.hit");

    public static final Supplier<SoundEvent> BLOCK_COPPER_ORE_PLACE = register("block.copper_ore.place");

    public static final Supplier<SoundEvent> BLOCK_COPPER_ORE_STEP = register("block.copper_ore.step");

    public static final Supplier<SoundEvent> BLOCK_DEEPSLATE_COPPER_ORE_BREAK = register("block.deepslate_copper_ore.break");

    public static final Supplier<SoundEvent> BLOCK_DEEPSLATE_COPPER_ORE_FALL = register("block.deepslate_copper_ore.fall");

    public static final Supplier<SoundEvent> BLOCK_DEEPSLATE_COPPER_ORE_HIT = register("block.deepslate_copper_ore.hit");

    public static final Supplier<SoundEvent> BLOCK_DEEPSLATE_COPPER_ORE_PLACE = register("block.deepslate_copper_ore.place");

    public static final Supplier<SoundEvent> BLOCK_DEEPSLATE_COPPER_ORE_STEP = register("block.deepslate_copper_ore.step");

    public static final Supplier<SoundEvent> BLOCK_DEEPSLATE_GOLD_ORE_BREAK = register("block.deepslate_gold_ore.break");

    public static final Supplier<SoundEvent> BLOCK_DEEPSLATE_GOLD_ORE_FALL = register("block.deepslate_gold_ore.fall");

    public static final Supplier<SoundEvent> BLOCK_DEEPSLATE_GOLD_ORE_HIT = register("block.deepslate_gold_ore.hit");

    public static final Supplier<SoundEvent> BLOCK_DEEPSLATE_GOLD_ORE_PLACE = register("block.deepslate_gold_ore.place");

    public static final Supplier<SoundEvent> BLOCK_DEEPSLATE_GOLD_ORE_STEP = register("block.deepslate_gold_ore.step");

    public static final Supplier<SoundEvent> BLOCK_DEEPSLATE_IRON_ORE_BREAK = register("block.deepslate_iron_ore.break");

    public static final Supplier<SoundEvent> BLOCK_DEEPSLATE_IRON_ORE_FALL = register("block.deepslate_iron_ore.fall");

    public static final Supplier<SoundEvent> BLOCK_DEEPSLATE_IRON_ORE_HIT = register("block.deepslate_iron_ore.hit");

    public static final Supplier<SoundEvent> BLOCK_DEEPSLATE_IRON_ORE_PLACE = register("block.deepslate_iron_ore.place");

    public static final Supplier<SoundEvent> BLOCK_DEEPSLATE_IRON_ORE_STEP = register("block.deepslate_iron_ore.step");

    public static final Supplier<SoundEvent> BLOCK_GLASS_BREAK = register("block.glass.break");

    public static final Supplier<SoundEvent> BLOCK_GLASS_PLACE = register("block.glass.place");

    public static final Supplier<SoundEvent> BLOCK_GLASS_STEP = register("block.glass.step");

    public static final Supplier<SoundEvent> BLOCK_GOLD_BLOCK_STEP = register("block.gold_block.step");

    public static final Supplier<SoundEvent> BLOCK_GOLD_ORE_BREAK = register("block.gold_ore.break");

    public static final Supplier<SoundEvent> BLOCK_GOLD_ORE_FALL = register("block.gold_ore.fall");

    public static final Supplier<SoundEvent> BLOCK_GOLD_ORE_HIT = register("block.gold_ore.hit");

    public static final Supplier<SoundEvent> BLOCK_GOLD_ORE_PLACE = register("block.gold_ore.place");

    public static final Supplier<SoundEvent> BLOCK_GOLD_ORE_STEP = register("block.gold_ore.step");

    public static final Supplier<SoundEvent> BLOCK_GRAVEL_BREAK = register("block.gravel.break");

    public static final Supplier<SoundEvent> BLOCK_GRAVEL_FALL = register("block.gravel.fall");

    public static final Supplier<SoundEvent> BLOCK_GRAVEL_HIT = register("block.gravel.hit");

    public static final Supplier<SoundEvent> BLOCK_GRAVEL_PLACE = register("block.gravel.place");

    public static final Supplier<SoundEvent> BLOCK_GRAVEL_STEP = register("block.gravel.step");

    public static final Supplier<SoundEvent> BLOCK_HAY_BLOCK_BREAK = register("block.hay_block.break");

    public static final Supplier<SoundEvent> BLOCK_HAY_BLOCK_HIT = register("block.hay_block.hit");

    public static final Supplier<SoundEvent> BLOCK_HAY_BLOCK_PLACE = register("block.hay_block.place");

    public static final Supplier<SoundEvent> BLOCK_HAY_BLOCK_STEP = register("block.hay_block.step");

    public static final Supplier<SoundEvent> BLOCK_ICE_BREAK = register("block.ice.break");

    public static final Supplier<SoundEvent> BLOCK_ICE_FALL = register("block.ice.fall");

    public static final Supplier<SoundEvent> BLOCK_ICE_HIT = register("block.ice.hit");

    public static final Supplier<SoundEvent> BLOCK_ICE_PLACE = register("block.ice.place");

    public static final Supplier<SoundEvent> BLOCK_ICE_STEP = register("block.ice.step");

    public static final Supplier<SoundEvent> BLOCK_IRON_BLOCK_STEP = register("block.iron_block.step");

    public static final Supplier<SoundEvent> BLOCK_IRON_ORE_BREAK = register("block.iron_ore.break");

    public static final Supplier<SoundEvent> BLOCK_IRON_ORE_FALL = register("block.iron_ore.fall");

    public static final Supplier<SoundEvent> BLOCK_IRON_ORE_HIT = register("block.iron_ore.hit");

    public static final Supplier<SoundEvent> BLOCK_IRON_ORE_PLACE = register("block.iron_ore.place");

    public static final Supplier<SoundEvent> BLOCK_IRON_ORE_STEP = register("block.iron_ore.step");

    public static final Supplier<SoundEvent> BLOCK_JUNGLE_LEAVES_BREAK = register("block.jungle_leaves.break");

    public static final Supplier<SoundEvent> BLOCK_JUNGLE_LEAVES_FALL = register("block.jungle_leaves.fall");

    public static final Supplier<SoundEvent> BLOCK_JUNGLE_LEAVES_HIT = register("block.jungle_leaves.hit");

    public static final Supplier<SoundEvent> BLOCK_JUNGLE_LEAVES_PLACE = register("block.jungle_leaves.place");

    public static final Supplier<SoundEvent> BLOCK_JUNGLE_LEAVES_STEP = register("block.jungle_leaves.step");

    public static final Supplier<SoundEvent> BLOCK_JUNGLE_OBJECT_BREAK = register("block.jungle_object.break");

    public static final Supplier<SoundEvent> BLOCK_JUNGLE_OBJECT_FALL = register("block.jungle_object.fall");

    public static final Supplier<SoundEvent> BLOCK_JUNGLE_OBJECT_HIT = register("block.jungle_object.hit");

    public static final Supplier<SoundEvent> BLOCK_JUNGLE_OBJECT_PLACE = register("block.jungle_object.place");

    public static final Supplier<SoundEvent> BLOCK_JUNGLE_OBJECT_STEP = register("block.jungle_object.step");

    public static final Supplier<SoundEvent> BLOCK_JUNGLE_PLANKS_BREAK = register("block.jungle_planks.break");

    public static final Supplier<SoundEvent> BLOCK_JUNGLE_PLANKS_FALL = register("block.jungle_planks.fall");

    public static final Supplier<SoundEvent> BLOCK_JUNGLE_PLANKS_HIT = register("block.jungle_planks.hit");

    public static final Supplier<SoundEvent> BLOCK_JUNGLE_PLANKS_PLACE = register("block.jungle_planks.place");

    public static final Supplier<SoundEvent> BLOCK_JUNGLE_PLANKS_STEP = register("block.jungle_planks.step");

    public static final Supplier<SoundEvent> BLOCK_LOOM_BREAK = register("block.loom.break");

    public static final Supplier<SoundEvent> BLOCK_LOOM_HIT = register("block.loom.hit");

    public static final Supplier<SoundEvent> BLOCK_LOOM_PLACE = register("block.loom.place");

    public static final Supplier<SoundEvent> BLOCK_LOOM_STEP = register("block.loom.step");

    public static final Supplier<SoundEvent> BLOCK_MAGMA_BLOCK_BREAK = register("block.magma_block.break");

    public static final Supplier<SoundEvent> BLOCK_MAGMA_BLOCK_FALL = register("block.magma_block.fall");

    public static final Supplier<SoundEvent> BLOCK_MAGMA_BLOCK_HIT = register("block.magma_block.hit");

    public static final Supplier<SoundEvent> BLOCK_MAGMA_BLOCK_PLACE = register("block.magma_block.place");

    public static final Supplier<SoundEvent> BLOCK_MAGMA_BLOCK_STEP = register("block.magma_block.step");

    public static final Supplier<SoundEvent> BLOCK_MANGROVE_LEAVES_BREAK = register("block.mangrove_leaves.break");

    public static final Supplier<SoundEvent> BLOCK_MANGROVE_LEAVES_FALL = register("block.mangrove_leaves.fall");

    public static final Supplier<SoundEvent> BLOCK_MANGROVE_LEAVES_HIT = register("block.mangrove_leaves.hit");

    public static final Supplier<SoundEvent> BLOCK_MANGROVE_LEAVES_PLACE = register("block.mangrove_leaves.place");

    public static final Supplier<SoundEvent> BLOCK_MANGROVE_LEAVES_STEP = register("block.mangrove_leaves.step");

    public static final Supplier<SoundEvent> BLOCK_MANGROVE_LOG_BREAK = register("block.mangrove_log.break");

    public static final Supplier<SoundEvent> BLOCK_MANGROVE_LOG_FALL = register("block.mangrove_log.fall");

    public static final Supplier<SoundEvent> BLOCK_MANGROVE_LOG_HIT = register("block.mangrove_log.hit");

    public static final Supplier<SoundEvent> BLOCK_MANGROVE_LOG_PLACE = register("block.mangrove_log.place");

    public static final Supplier<SoundEvent> BLOCK_MANGROVE_LOG_STEP = register("block.mangrove_log.step");

    public static final Supplier<SoundEvent> BLOCK_MANGROVE_OBJECT_BREAK = register("block.mangrove_object.break");

    public static final Supplier<SoundEvent> BLOCK_MANGROVE_OBJECT_FALL = register("block.mangrove_object.fall");

    public static final Supplier<SoundEvent> BLOCK_MANGROVE_OBJECT_HIT = register("block.mangrove_object.hit");

    public static final Supplier<SoundEvent> BLOCK_MANGROVE_OBJECT_PLACE = register("block.mangrove_object.place");

    public static final Supplier<SoundEvent> BLOCK_MANGROVE_OBJECT_STEP = register("block.mangrove_object.step");

    public static final Supplier<SoundEvent> BLOCK_MANGROVE_PLANKS_BREAK = register("block.mangrove_planks.break");

    public static final Supplier<SoundEvent> BLOCK_MANGROVE_PLANKS_FALL = register("block.mangrove_planks.fall");

    public static final Supplier<SoundEvent> BLOCK_MANGROVE_PLANKS_HIT = register("block.mangrove_planks.hit");

    public static final Supplier<SoundEvent> BLOCK_MANGROVE_PLANKS_PLACE = register("block.mangrove_planks.place");

    public static final Supplier<SoundEvent> BLOCK_MANGROVE_PLANKS_STEP = register("block.mangrove_planks.step");

    public static final Supplier<SoundEvent> BLOCK_MOSSY_COBBLESTONE_BREAK = register("block.mossy_cobblestone.break");

    public static final Supplier<SoundEvent> BLOCK_MOSSY_COBBLESTONE_HIT = register("block.mossy_cobblestone.hit");

    public static final Supplier<SoundEvent> BLOCK_MOSSY_COBBLESTONE_STEP = register("block.mossy_cobblestone.step");

    public static final Supplier<SoundEvent> BLOCK_MOSSY_STONE_BRICKS_BREAK = register("block.mossy_stone_bricks.break");

    public static final Supplier<SoundEvent> BLOCK_MOSSY_STONE_BRICKS_FALL = register("block.mossy_stone_bricks.fall");

    public static final Supplier<SoundEvent> BLOCK_MOSSY_STONE_BRICKS_HIT = register("block.mossy_stone_bricks.hit");

    public static final Supplier<SoundEvent> BLOCK_MOSSY_STONE_BRICKS_STEP = register("block.mossy_stone_bricks.step");

    public static final Supplier<SoundEvent> BLOCK_OAK_LOG_BREAK = register("block.oak_log.break");

    public static final Supplier<SoundEvent> BLOCK_OAK_LOG_FALL = register("block.oak_log.fall");

    public static final Supplier<SoundEvent> BLOCK_OAK_LOG_HIT = register("block.oak_log.hit");

    public static final Supplier<SoundEvent> BLOCK_OAK_LOG_PLACE = register("block.oak_log.place");

    public static final Supplier<SoundEvent> BLOCK_OAK_LOG_STEP = register("block.oak_log.step");

    public static final Supplier<SoundEvent> BLOCK_PACKED_ICE_BREAK = register("block.packed_ice.break");

    public static final Supplier<SoundEvent> BLOCK_PACKED_ICE_FALL = register("block.packed_ice.fall");

    public static final Supplier<SoundEvent> BLOCK_PACKED_ICE_HIT = register("block.packed_ice.hit");

    public static final Supplier<SoundEvent> BLOCK_PACKED_ICE_PLACE = register("block.packed_ice.place");

    public static final Supplier<SoundEvent> BLOCK_PACKED_ICE_STEP = register("block.packed_ice.step");

    public static final Supplier<SoundEvent> BLOCK_QUARTZ_BREAK = register("block.quartz.break");

    public static final Supplier<SoundEvent> BLOCK_QUARTZ_HIT = register("block.quartz.hit");

    public static final Supplier<SoundEvent> BLOCK_QUARTZ_PLACE = register("block.quartz.place");

    public static final Supplier<SoundEvent> BLOCK_QUARTZ_STEP = register("block.quartz.step");

    public static final Supplier<SoundEvent> BLOCK_RAW_GOLD_BLOCK_BREAK = register("block.raw_gold_block.break");

    public static final Supplier<SoundEvent> BLOCK_RAW_GOLD_BLOCK_FALL = register("block.raw_gold_block.fall");

    public static final Supplier<SoundEvent> BLOCK_RAW_GOLD_BLOCK_HIT = register("block.raw_gold_block.hit");

    public static final Supplier<SoundEvent> BLOCK_RAW_GOLD_BLOCK_PLACE = register("block.raw_gold_block.place");

    public static final Supplier<SoundEvent> BLOCK_RAW_GOLD_BLOCK_STEP = register("block.raw_gold_block.step");

    public static final Supplier<SoundEvent> BLOCK_SANDSTONE_STEP = register("block.sandstone.step");

    public static final Supplier<SoundEvent> BLOCK_SHEET_METAL_BREAK = register("block.sheet_metal.break");

    public static final Supplier<SoundEvent> BLOCK_SHEET_METAL_STEP = register("block.sheet_metal.step");

    public static final Supplier<SoundEvent> BLOCK_SPRUCE_LEAVES_BREAK = register("block.spruce_leaves.break");

    public static final Supplier<SoundEvent> BLOCK_SPRUCE_LEAVES_FALL = register("block.spruce_leaves.fall");

    public static final Supplier<SoundEvent> BLOCK_SPRUCE_LEAVES_HIT = register("block.spruce_leaves.hit");

    public static final Supplier<SoundEvent> BLOCK_SPRUCE_LEAVES_PLACE = register("block.spruce_leaves.place");

    public static final Supplier<SoundEvent> BLOCK_SPRUCE_LEAVES_STEP = register("block.spruce_leaves.step");

    public static final Supplier<SoundEvent> BLOCK_SPRUCE_LOG_BREAK = register("block.spruce_log.break");

    public static final Supplier<SoundEvent> BLOCK_SPRUCE_LOG_FALL = register("block.spruce_log.fall");

    public static final Supplier<SoundEvent> BLOCK_SPRUCE_LOG_HIT = register("block.spruce_log.hit");

    public static final Supplier<SoundEvent> BLOCK_SPRUCE_LOG_PLACE = register("block.spruce_log.place");

    public static final Supplier<SoundEvent> BLOCK_SPRUCE_LOG_STEP = register("block.spruce_log.step");

    public static final Supplier<SoundEvent> BLOCK_SPRUCE_OBJECT_BREAK = register("block.spruce_object.break");

    public static final Supplier<SoundEvent> BLOCK_SPRUCE_OBJECT_FALL = register("block.spruce_object.fall");

    public static final Supplier<SoundEvent> BLOCK_SPRUCE_OBJECT_HIT = register("block.spruce_object.hit");

    public static final Supplier<SoundEvent> BLOCK_SPRUCE_OBJECT_PLACE = register("block.spruce_object.place");

    public static final Supplier<SoundEvent> BLOCK_SPRUCE_OBJECT_STEP = register("block.spruce_object.step");

    public static final Supplier<SoundEvent> BLOCK_SPRUCE_PLANKS_BREAK = register("block.spruce_planks.break");

    public static final Supplier<SoundEvent> BLOCK_SPRUCE_PLANKS_FALL = register("block.spruce_planks.fall");

    public static final Supplier<SoundEvent> BLOCK_SPRUCE_PLANKS_HIT = register("block.spruce_planks.hit");

    public static final Supplier<SoundEvent> BLOCK_SPRUCE_PLANKS_PLACE = register("block.spruce_planks.place");

    public static final Supplier<SoundEvent> BLOCK_SPRUCE_PLANKS_STEP = register("block.spruce_planks.step");

    public static final Supplier<SoundEvent> BLOCK_STONE_BRICKS_BREAK = register("block.stone_bricks.break");

    public static final Supplier<SoundEvent> BLOCK_STONE_BRICKS_FALL = register("block.stone_bricks.fall");

    public static final Supplier<SoundEvent> BLOCK_STONE_BRICKS_HIT = register("block.stone_bricks.hit");

    public static final Supplier<SoundEvent> BLOCK_STONE_BRICKS_PLACE = register("block.stone_bricks.place");

    public static final Supplier<SoundEvent> BLOCK_STONE_BRICKS_STEP = register("block.stone_bricks.step");

    private static Supplier<SoundEvent> register(String id) {
        ResourceLocation _id = ResourceLocation.fromNamespaceAndPath("sounds", id);
        //? if fabric {
        var val = Registry.register(BuiltInRegistries.SOUND_EVENT, _id, SoundEvent.createVariableRangeEvent(_id));
        return () -> val;
        //?} else {
        /*return REGISTRY.register(id, () -> SoundEvent.createVariableRangeEvent(_id));
        *///?}
    }

    public static void initialize() {
    }
}