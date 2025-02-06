/*? if >=1.21 && fabric {*/
package dev.imb11.sounds.loaders.fabric.datagen;

import dev.imb11.sounds.api.config.TagPair;
import dev.imb11.sounds.api.config.TagPair.Builder;
import dev.imb11.sounds.api.datagen.TagPairProvider;
import dev.imb11.sounds.sound.CustomSounds;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class DynamicTagPairs extends TagPairProvider {
    protected DynamicTagPairs(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(dataOutput, registriesFuture);
    }

    @Override
    public void accept(BiConsumer<String, TagPair.Builder> provider) {
        provider.accept("acacia_leaves", TagPair.Builder.create()
                .addKey(Blocks.ACACIA_LEAVES)
                .addKey(Blocks.ACACIA_SAPLING)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_ACACIA_LEAVES_BREAK.get(), CustomSounds.BLOCK_ACACIA_LEAVES_STEP.get(), CustomSounds.BLOCK_ACACIA_LEAVES_PLACE.get(), CustomSounds.BLOCK_ACACIA_LEAVES_HIT.get(), CustomSounds.BLOCK_ACACIA_LEAVES_FALL.get()));

        provider.accept("acacia_log", TagPair.Builder.create()
                .addKey(Blocks.ACACIA_LOG)
                .addKey(Blocks.ACACIA_WOOD)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_ACACIA_LOG_BREAK.get(), CustomSounds.BLOCK_ACACIA_LOG_STEP.get(), CustomSounds.BLOCK_ACACIA_LOG_PLACE.get(), CustomSounds.BLOCK_ACACIA_LOG_HIT.get(), CustomSounds.BLOCK_ACACIA_LOG_FALL.get()));

        provider.accept("acacia_object", TagPair.Builder.create()
                .addKey(Blocks.ACACIA_PRESSURE_PLATE)
                .addKey(Blocks.ACACIA_FENCE_GATE)
                .addKey(Blocks.ACACIA_FENCE)
                .addKey(Blocks.ACACIA_SIGN)
                .addKey(Blocks.ACACIA_BUTTON)
                .addKey(Blocks.ACACIA_DOOR)
                .addKey(Blocks.ACACIA_TRAPDOOR)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_ACACIA_PLANKS_BREAK.get(), CustomSounds.BLOCK_ACACIA_PLANKS_STEP.get(), CustomSounds.BLOCK_ACACIA_PLANKS_PLACE.get(), CustomSounds.BLOCK_ACACIA_PLANKS_HIT.get(), CustomSounds.BLOCK_ACACIA_PLANKS_FALL.get()));

        provider.accept("acacia_planks", TagPair.Builder.create()
                .addKey(Blocks.ACACIA_PLANKS)
                .addKey(Blocks.ACACIA_SLAB)
                .addKey(Blocks.ACACIA_STAIRS)
                .addKey(Blocks.STRIPPED_ACACIA_LOG)
                .addKey(Blocks.STRIPPED_ACACIA_WOOD)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_ACACIA_PLANKS_BREAK.get(), CustomSounds.BLOCK_ACACIA_PLANKS_STEP.get(), CustomSounds.BLOCK_ACACIA_PLANKS_PLACE.get(), CustomSounds.BLOCK_ACACIA_PLANKS_HIT.get(), CustomSounds.BLOCK_ACACIA_PLANKS_FALL.get()));

        provider.accept("barrel", TagPair.Builder.create()
                .addKey(Blocks.BARREL)
                .group(1.0F, 1.0F, SoundEvents.WOOD_BREAK, CustomSounds.BLOCK_BARREL_STEP.get(), CustomSounds.BLOCK_BARREL_PLACE.get(), CustomSounds.BLOCK_BARREL_HIT.get(), SoundEvents.WOOD_FALL));

        provider.accept("beehive", TagPair.Builder.create()
                .addKey(Blocks.BEEHIVE)
                .group(1.5F, 1.0F, CustomSounds.BLOCK_BEEHIVE_BREAK.get(), CustomSounds.BLOCK_BOOKSHELF_STEP.get(), CustomSounds.BLOCK_BEEHIVE_PLACE.get(), CustomSounds.BLOCK_BEEHIVE_HIT.get(), SoundEvents.WOOD_FALL));

        provider.accept("birch_leaves", TagPair.Builder.create()
                .addKey(Blocks.BIRCH_LEAVES)
                .addKey(Blocks.BIRCH_SAPLING)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_BIRCH_LEAVES_BREAK.get(), CustomSounds.BLOCK_BIRCH_LEAVES_STEP.get(), CustomSounds.BLOCK_BIRCH_LEAVES_PLACE.get(), CustomSounds.BLOCK_BIRCH_LEAVES_HIT.get(), CustomSounds.BLOCK_BIRCH_LEAVES_FALL.get()));

        provider.accept("birch_log", TagPair.Builder.create()
                .addKey(Blocks.BIRCH_LOG)
                .addKey(Blocks.BIRCH_WOOD)
                .addKey(Blocks.BIRCH_DOOR)
                .addKey(Blocks.BIRCH_TRAPDOOR)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_BIRCH_LOG_BREAK.get(), CustomSounds.BLOCK_BIRCH_LOG_STEP.get(), CustomSounds.BLOCK_BIRCH_LOG_PLACE.get(), CustomSounds.BLOCK_BIRCH_LOG_HIT.get(), CustomSounds.BLOCK_BIRCH_LOG_FALL.get()));

        provider.accept("birch_object", TagPair.Builder.create()
                .addKey(Blocks.BIRCH_PRESSURE_PLATE)
                .addKey(Blocks.BIRCH_FENCE_GATE)
                .addKey(Blocks.BIRCH_FENCE)
                .addKey(Blocks.BIRCH_SIGN)
                .addKey(Blocks.BIRCH_BUTTON)
                .addKey(Blocks.BIRCH_SLAB)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_BIRCH_OBJECT_BREAK.get(), CustomSounds.BLOCK_BIRCH_OBJECT_STEP.get(), CustomSounds.BLOCK_BIRCH_OBJECT_PLACE.get(), CustomSounds.BLOCK_BIRCH_PLANKS_HIT.get(), CustomSounds.BLOCK_BIRCH_OBJECT_FALL.get()));

        provider.accept("birch_planks", TagPair.Builder.create()
                .addKey(Blocks.BIRCH_PLANKS)
                .addKey(Blocks.BIRCH_STAIRS)
                .addKey(Blocks.STRIPPED_BIRCH_LOG)
                .addKey(Blocks.STRIPPED_BIRCH_WOOD)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_BIRCH_PLANKS_BREAK.get(), CustomSounds.BLOCK_BIRCH_PLANKS_STEP.get(), CustomSounds.BLOCK_BIRCH_PLANKS_PLACE.get(), CustomSounds.BLOCK_BIRCH_PLANKS_HIT.get(), CustomSounds.BLOCK_BIRCH_PLANKS_FALL.get()));

        provider.accept("bookshelf", TagPair.Builder.create()
                .addKey(Blocks.BOOKSHELF)
                .group(1.5F, 1.0F, CustomSounds.BLOCK_BOOKSHELF_BREAK.get(), CustomSounds.BLOCK_BOOKSHELF_STEP.get(), CustomSounds.BLOCK_BOOKSHELF_PLACE.get(), CustomSounds.BLOCK_BOOKSHELF_HIT.get(), SoundEvents.WOOD_FALL));

        provider.accept("chest", TagPair.Builder.create()
                .addKey(Blocks.CHEST)
                .group(1.5F, 1.0F, SoundEvents.WOOD_BREAK, CustomSounds.BLOCK_CHEST_STEP.get(), CustomSounds.BLOCK_BARREL_PLACE.get(), CustomSounds.BLOCK_CHEST_HIT.get(), SoundEvents.WOOD_FALL));

        provider.accept("clay", TagPair.Builder.create()
                .addKey(Blocks.CLAY)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_CLAY_BREAK.get(), CustomSounds.BLOCK_CLAY_STEP.get(), CustomSounds.BLOCK_CLAY_PLACE.get(), CustomSounds.BLOCK_CLAY_HIT.get(), SoundEvents.MUD_FALL));

        provider.accept("clay_bricks", TagPair.Builder.create()
                .addKey(Blocks.BRICKS)
                .addKey(Blocks.BRICK_SLAB)
                .addKey(Blocks.BRICK_STAIRS)
                .addKey(Blocks.BRICK_WALL)
                .group(1.0F, 1.3F, SoundEvents.NETHER_BRICKS_BREAK, SoundEvents.NETHER_BRICKS_STEP, SoundEvents.NETHER_BRICKS_PLACE, SoundEvents.NETHER_BRICKS_HIT, SoundEvents.NETHER_BRICKS_FALL));

        provider.accept("cobblestone", TagPair.Builder.create()
                .addKey(Blocks.COBBLESTONE)
                .addKey(Blocks.COBBLESTONE_SLAB)
                .addKey(Blocks.COBBLESTONE_STAIRS)
                .addKey(Blocks.COBBLESTONE_WALL)
                .addKey(Blocks.INFESTED_COBBLESTONE)
                .addKey(Blocks.MOSSY_COBBLESTONE)
                .addKey(Blocks.MOSSY_COBBLESTONE_SLAB)
                .addKey(Blocks.MOSSY_COBBLESTONE_STAIRS)
                .addKey(Blocks.MOSSY_COBBLESTONE_WALL)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_COBBLESTONE_BREAK.get(), CustomSounds.BLOCK_COBBLESTONE_STEP.get(), CustomSounds.BLOCK_COBBLESTONE_PLACE.get(), CustomSounds.BLOCK_COBBLESTONE_HIT.get(), CustomSounds.BLOCK_COBBLESTONE_FALL.get()));

        provider.accept("copper_ore", TagPair.Builder.create()
                .addKey(Blocks.COPPER_ORE)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_COPPER_ORE_BREAK.get(), CustomSounds.BLOCK_COPPER_ORE_STEP.get(), CustomSounds.BLOCK_COPPER_ORE_PLACE.get(), CustomSounds.BLOCK_COPPER_ORE_HIT.get(), CustomSounds.BLOCK_COPPER_ORE_FALL.get()));

        provider.accept("dark_prismarine", TagPair.Builder.create()
                .addKey(Blocks.DARK_PRISMARINE)
                .addKey(Blocks.DARK_PRISMARINE_STAIRS)
                .addKey(Blocks.DARK_PRISMARINE_SLAB)
                .group(1.0F, 1.0F, SoundEvents.DEEPSLATE_TILES_BREAK, SoundEvents.DEEPSLATE_TILES_STEP, SoundEvents.DEEPSLATE_TILES_PLACE, SoundEvents.DEEPSLATE_TILES_HIT, SoundEvents.DEEPSLATE_TILES_FALL));

        provider.accept("deepslate_copper_ore", TagPair.Builder.create()
                .addKey(Blocks.DEEPSLATE_COPPER_ORE)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_COPPER_ORE_BREAK.get(), CustomSounds.BLOCK_DEEPSLATE_COPPER_ORE_STEP.get(), CustomSounds.BLOCK_DEEPSLATE_COPPER_ORE_PLACE.get(), CustomSounds.BLOCK_DEEPSLATE_COPPER_ORE_HIT.get(), CustomSounds.BLOCK_DEEPSLATE_COPPER_ORE_FALL.get()));

        provider.accept("deepslate_gold_ore", TagPair.Builder.create()
                .addKey(Blocks.DEEPSLATE_GOLD_ORE)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_DEEPSLATE_GOLD_ORE_BREAK.get(), CustomSounds.BLOCK_DEEPSLATE_GOLD_ORE_STEP.get(), CustomSounds.BLOCK_DEEPSLATE_GOLD_ORE_PLACE.get(), CustomSounds.BLOCK_DEEPSLATE_GOLD_ORE_HIT.get(), CustomSounds.BLOCK_DEEPSLATE_GOLD_ORE_FALL.get()));

        provider.accept("deepslate_iron_ore", TagPair.Builder.create()
                .addKey(Blocks.DEEPSLATE_IRON_ORE)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_DEEPSLATE_IRON_ORE_BREAK.get(), CustomSounds.BLOCK_DEEPSLATE_IRON_ORE_STEP.get(), CustomSounds.BLOCK_DEEPSLATE_IRON_ORE_PLACE.get(), CustomSounds.BLOCK_DEEPSLATE_IRON_ORE_HIT.get(), CustomSounds.BLOCK_DEEPSLATE_IRON_ORE_FALL.get()));

        provider.accept("emerald_block", TagPair.Builder.create()
                .addKey(Blocks.EMERALD_BLOCK)
                .group(1.0F, 1.2F, SoundEvents.BONE_BLOCK_BREAK, SoundEvents.BONE_BLOCK_STEP, CustomSounds.BLOCK_QUARTZ_PLACE.get(), SoundEvents.BONE_BLOCK_HIT, SoundEvents.BONE_BLOCK_FALL));

        provider.accept("end_stone", TagPair.Builder.create()
                .addKey(Blocks.END_STONE)
                .group(0.9F, 0.8F, CustomSounds.BLOCK_QUARTZ_BREAK.get(), CustomSounds.BLOCK_QUARTZ_STEP.get(), CustomSounds.BLOCK_QUARTZ_PLACE.get(), SoundEvents.STONE_HIT, SoundEvents.STONE_FALL));

        provider.accept("end_stone_bricks", TagPair.Builder.create()
                .addKey(Blocks.END_STONE_BRICKS)
                .addKey(Blocks.END_STONE_BRICK_STAIRS)
                .addKey(Blocks.END_STONE_BRICK_SLAB)
                .addKey(Blocks.END_STONE_BRICK_WALL)
                .group(1.0F, 1.0F, SoundEvents.DEEPSLATE_BRICKS_BREAK, CustomSounds.BLOCK_QUARTZ_STEP.get(), SoundEvents.DEEPSLATE_BRICKS_PLACE, SoundEvents.DEEPSLATE_BRICKS_HIT, SoundEvents.DEEPSLATE_BRICKS_FALL));

        var glass = TagPair.Builder.create()
                .addKey(Blocks.GLOWSTONE)
                .addKey(Blocks.BEACON)
                .addKey(Blocks.REDSTONE_LAMP)
                .addKey(Blocks.SEA_LANTERN)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_GLASS_BREAK.get(), CustomSounds.BLOCK_GLASS_STEP.get(), CustomSounds.BLOCK_GLASS_PLACE.get(), SoundEvents.GLASS_HIT, SoundEvents.GLASS_FALL);

        for (ResourceKey<Block> block : BuiltInRegistries.BLOCK.registryKeySet().stream().toList()) {
            if (block.location().getPath().contains("glass")) {
                glass.addKey(BuiltInRegistries.BLOCK.getValue(block));
            }
        }

        provider.accept("glass", glass);

        provider.accept("gold", TagPair.Builder.create()
                .addKey(Blocks.GOLD_BLOCK)
                .addKey(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE)
                .group(1.0F, 1.6F, SoundEvents.NETHERITE_BLOCK_BREAK, CustomSounds.BLOCK_GOLD_BLOCK_STEP.get(), SoundEvents.NETHERITE_BLOCK_PLACE, SoundEvents.NETHERITE_BLOCK_HIT, SoundEvents.NETHERITE_BLOCK_FALL));

        provider.accept("gold_ore", TagPair.Builder.create()
                .addKey(Blocks.GOLD_ORE)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_GOLD_ORE_BREAK.get(), CustomSounds.BLOCK_GOLD_ORE_STEP.get(), CustomSounds.BLOCK_GOLD_ORE_PLACE.get(), CustomSounds.BLOCK_GOLD_ORE_HIT.get(), CustomSounds.BLOCK_GOLD_ORE_FALL.get()));

        provider.accept("gravel", TagPair.Builder.create()
                .addKey(Blocks.GRAVEL)
                .group(1.0F, 1.2F, CustomSounds.BLOCK_GRAVEL_BREAK.get(), CustomSounds.BLOCK_GRAVEL_STEP.get(), CustomSounds.BLOCK_GRAVEL_PLACE.get(), CustomSounds.BLOCK_GRAVEL_HIT.get(), SoundEvents.GRAVEL_FALL));

        provider.accept("hay_block", TagPair.Builder.create()
                .addKey(Blocks.HAY_BLOCK)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_HAY_BLOCK_BREAK.get(), CustomSounds.BLOCK_HAY_BLOCK_STEP.get(), CustomSounds.BLOCK_HAY_BLOCK_PLACE.get(), CustomSounds.BLOCK_HAY_BLOCK_HIT.get(), SoundEvents.TUFF_FALL));

        provider.accept("ice", TagPair.Builder.create()
                .addKey(Blocks.ICE)
                .addKey(Blocks.FROSTED_ICE)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_ICE_BREAK.get(), CustomSounds.BLOCK_ICE_STEP.get(), CustomSounds.BLOCK_ICE_PLACE.get(), CustomSounds.BLOCK_ICE_HIT.get(), CustomSounds.BLOCK_ICE_FALL.get()));

        provider.accept("iron", TagPair.Builder.create()
                .addKey(Blocks.IRON_DOOR)
                .addKey(Blocks.IRON_BLOCK)
                .addKey(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE)
                .group(1.0F, 1.2F, SoundEvents.NETHERITE_BLOCK_BREAK, CustomSounds.BLOCK_IRON_BLOCK_STEP.get(), SoundEvents.NETHERITE_BLOCK_PLACE, SoundEvents.NETHERITE_BLOCK_HIT, SoundEvents.NETHERITE_BLOCK_FALL));

        provider.accept("iron_ore", TagPair.Builder.create()
                .addKey(Blocks.IRON_ORE)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_DEEPSLATE_IRON_ORE_BREAK.get(), CustomSounds.BLOCK_IRON_ORE_STEP.get(), CustomSounds.BLOCK_IRON_ORE_PLACE.get(), CustomSounds.BLOCK_IRON_ORE_HIT.get(), CustomSounds.BLOCK_IRON_ORE_FALL.get()));

        provider.accept("jungle_leaves", TagPair.Builder.create()
                .addKey(Blocks.JUNGLE_LEAVES)
                .addKey(Blocks.JUNGLE_SAPLING)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_JUNGLE_LEAVES_BREAK.get(), CustomSounds.BLOCK_JUNGLE_LEAVES_STEP.get(), CustomSounds.BLOCK_JUNGLE_LEAVES_PLACE.get(), CustomSounds.BLOCK_JUNGLE_LEAVES_HIT.get(), CustomSounds.BLOCK_JUNGLE_LEAVES_FALL.get()));

        provider.accept("jungle_object", TagPair.Builder.create()
                .addKey(Blocks.JUNGLE_PRESSURE_PLATE)
                .addKey(Blocks.JUNGLE_FENCE_GATE)
                .addKey(Blocks.JUNGLE_FENCE)
                .addKey(Blocks.JUNGLE_SIGN)
                .addKey(Blocks.JUNGLE_BUTTON)
                .addKey(Blocks.JUNGLE_DOOR)
                .addKey(Blocks.JUNGLE_TRAPDOOR)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_JUNGLE_PLANKS_BREAK.get(), CustomSounds.BLOCK_JUNGLE_PLANKS_STEP.get(), CustomSounds.BLOCK_JUNGLE_PLANKS_PLACE.get(), CustomSounds.BLOCK_SPRUCE_PLANKS_HIT.get(), CustomSounds.BLOCK_JUNGLE_PLANKS_FALL.get()));

        provider.accept("jungle_planks", TagPair.Builder.create()
                .addKey(Blocks.JUNGLE_PLANKS)
                .addKey(Blocks.JUNGLE_STAIRS)
                .addKey(Blocks.JUNGLE_SLAB)
                .addKey(Blocks.STRIPPED_JUNGLE_LOG)
                .addKey(Blocks.STRIPPED_JUNGLE_WOOD)
                .addKey(Blocks.JUNGLE_WOOD)
                .addKey(Blocks.JUNGLE_LOG)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_JUNGLE_PLANKS_BREAK.get(), CustomSounds.BLOCK_JUNGLE_PLANKS_STEP.get(), CustomSounds.BLOCK_JUNGLE_PLANKS_PLACE.get(), CustomSounds.BLOCK_SPRUCE_PLANKS_HIT.get(), CustomSounds.BLOCK_JUNGLE_PLANKS_FALL.get()));

        provider.accept("lapis_block", TagPair.Builder.create()
                .addKey(Blocks.LAPIS_BLOCK)
                .addKey(Blocks.BLACK_GLAZED_TERRACOTTA)
                .addKey(Blocks.BLUE_GLAZED_TERRACOTTA)
                .addKey(Blocks.BROWN_GLAZED_TERRACOTTA)
                .addKey(Blocks.CYAN_GLAZED_TERRACOTTA)
                .addKey(Blocks.GRAY_GLAZED_TERRACOTTA)
                .addKey(Blocks.GREEN_GLAZED_TERRACOTTA)
                .addKey(Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA)
                .addKey(Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA)
                .addKey(Blocks.LIME_GLAZED_TERRACOTTA)
                .addKey(Blocks.MAGENTA_GLAZED_TERRACOTTA)
                .addKey(Blocks.ORANGE_GLAZED_TERRACOTTA)
                .addKey(Blocks.PINK_GLAZED_TERRACOTTA)
                .addKey(Blocks.PURPLE_GLAZED_TERRACOTTA)
                .addKey(Blocks.RED_GLAZED_TERRACOTTA)
                .addKey(Blocks.WHITE_GLAZED_TERRACOTTA)
                .addKey(Blocks.YELLOW_GLAZED_TERRACOTTA)
                .group(1.0F, 1.2F, SoundEvents.BONE_BLOCK_BREAK, SoundEvents.BONE_BLOCK_STEP, SoundEvents.BONE_BLOCK_PLACE, SoundEvents.BONE_BLOCK_HIT, SoundEvents.BONE_BLOCK_FALL));

        provider.accept("loom", TagPair.Builder.create()
                .addKey(Blocks.LOOM)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_LOOM_BREAK.get(), CustomSounds.BLOCK_LOOM_STEP.get(), CustomSounds.BLOCK_LOOM_PLACE.get(), CustomSounds.BLOCK_LOOM_HIT.get(), SoundEvents.WOOD_FALL));

        provider.accept("magma_block", TagPair.Builder.create()
                .addKey(Blocks.MAGMA_BLOCK)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_MAGMA_BLOCK_BREAK.get(), CustomSounds.BLOCK_MAGMA_BLOCK_STEP.get(), CustomSounds.BLOCK_MAGMA_BLOCK_PLACE.get(), CustomSounds.BLOCK_MAGMA_BLOCK_HIT.get(), CustomSounds.BLOCK_MAGMA_BLOCK_FALL.get()));

        provider.accept("mangrove_leaves", TagPair.Builder.create()
                .addKey(Blocks.MANGROVE_LEAVES)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_MANGROVE_LEAVES_BREAK.get(), CustomSounds.BLOCK_MANGROVE_LEAVES_STEP.get(), CustomSounds.BLOCK_MANGROVE_LEAVES_PLACE.get(), CustomSounds.BLOCK_MANGROVE_LEAVES_HIT.get(), CustomSounds.BLOCK_MANGROVE_LEAVES_FALL.get()));

        provider.accept("mangrove_log", TagPair.Builder.create()
                .addKey(Blocks.MANGROVE_LOG)
                .addKey(Blocks.MANGROVE_WOOD)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_MANGROVE_LOG_BREAK.get(), CustomSounds.BLOCK_MANGROVE_LOG_STEP.get(), CustomSounds.BLOCK_MANGROVE_LOG_PLACE.get(), CustomSounds.BLOCK_MANGROVE_LOG_HIT.get(), CustomSounds.BLOCK_MANGROVE_LOG_FALL.get()));

        provider.accept("mangrove_object", TagPair.Builder.create()
                .addKey(Blocks.MANGROVE_PRESSURE_PLATE)
                .addKey(Blocks.MANGROVE_FENCE_GATE)
                .addKey(Blocks.MANGROVE_FENCE)
                .addKey(Blocks.MANGROVE_SIGN)
                .addKey(Blocks.MANGROVE_BUTTON)
                .addKey(Blocks.MANGROVE_DOOR)
                .addKey(Blocks.MANGROVE_TRAPDOOR)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_MANGROVE_PLANKS_BREAK.get(), CustomSounds.BLOCK_MANGROVE_PLANKS_STEP.get(), CustomSounds.BLOCK_MANGROVE_PLANKS_PLACE.get(), CustomSounds.BLOCK_MANGROVE_PLANKS_HIT.get(), CustomSounds.BLOCK_MANGROVE_PLANKS_FALL.get()));

        provider.accept("mangrove_planks", TagPair.Builder.create()
                .addKey(Blocks.MANGROVE_PLANKS)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_MANGROVE_PLANKS_BREAK.get(), CustomSounds.BLOCK_MANGROVE_PLANKS_STEP.get(), CustomSounds.BLOCK_MANGROVE_PLANKS_PLACE.get(), CustomSounds.BLOCK_MANGROVE_PLANKS_HIT.get(), CustomSounds.BLOCK_MANGROVE_PLANKS_FALL.get()));

        provider.accept("mossy_cobblestone", TagPair.Builder.create()
                .addKey(Blocks.MOSSY_COBBLESTONE)
                .addKey(Blocks.MOSSY_COBBLESTONE_SLAB)
                .addKey(Blocks.MOSSY_COBBLESTONE_STAIRS)
                .addKey(Blocks.MOSSY_COBBLESTONE_WALL)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_MOSSY_COBBLESTONE_BREAK.get(), CustomSounds.BLOCK_MOSSY_COBBLESTONE_STEP.get(), CustomSounds.BLOCK_COBBLESTONE_PLACE.get(), CustomSounds.BLOCK_MOSSY_COBBLESTONE_HIT.get(), CustomSounds.BLOCK_COBBLESTONE_FALL.get()));

        provider.accept("mossy_stone_bricks", TagPair.Builder.create()
                .addKey(Blocks.MOSSY_STONE_BRICKS)
                .addKey(Blocks.INFESTED_MOSSY_STONE_BRICKS)
                .addKey(Blocks.MOSSY_STONE_BRICK_SLAB)
                .addKey(Blocks.MOSSY_STONE_BRICK_STAIRS)
                .addKey(Blocks.MOSSY_STONE_BRICK_WALL)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_MOSSY_COBBLESTONE_BREAK.get(), CustomSounds.BLOCK_MOSSY_STONE_BRICKS_STEP.get(), SoundEvents.DEEPSLATE_BRICKS_PLACE, CustomSounds.BLOCK_MOSSY_STONE_BRICKS_HIT.get(), CustomSounds.BLOCK_MOSSY_STONE_BRICKS_FALL.get()));

        provider.accept("oak_log", TagPair.Builder.create()
                .addKey(Blocks.OAK_LOG)
                .addKey(Blocks.OAK_WOOD)
                .addKey(Blocks.DARK_OAK_LOG)
                .addKey(Blocks.DARK_OAK_WOOD)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_OAK_LOG_BREAK.get(), CustomSounds.BLOCK_OAK_LOG_STEP.get(), CustomSounds.BLOCK_OAK_LOG_PLACE.get(), CustomSounds.BLOCK_OAK_LOG_HIT.get(), CustomSounds.BLOCK_OAK_LOG_FALL.get()));

        provider.accept("obsidian", TagPair.Builder.create()
                .addKey(Blocks.RESPAWN_ANCHOR)
                .addKey(Blocks.ENCHANTING_TABLE)
                .addKey(Blocks.ENDER_CHEST)
                .group(1.0F, 0.7F, SoundEvents.DEEPSLATE_BREAK, SoundEvents.DEEPSLATE_STEP, SoundEvents.DEEPSLATE_PLACE, SoundEvents.DEEPSLATE_HIT, SoundEvents.DEEPSLATE_FALL));

        provider.accept("packed_ice", TagPair.Builder.create()
                .addKey(Blocks.FROSTED_ICE)
                .addKey(Blocks.PACKED_ICE)
                .addKey(Blocks.BLUE_ICE)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_PACKED_ICE_BREAK.get(), CustomSounds.BLOCK_PACKED_ICE_STEP.get(), CustomSounds.BLOCK_PACKED_ICE_PLACE.get(), CustomSounds.BLOCK_PACKED_ICE_HIT.get(), CustomSounds.BLOCK_PACKED_ICE_FALL.get()));

        provider.accept("prismarine", TagPair.Builder.create()
                .addKey(Blocks.PRISMARINE)
                .addKey(Blocks.PRISMARINE_STAIRS)
                .addKey(Blocks.PRISMARINE_SLAB)
                .addKey(Blocks.PRISMARINE_WALL)
                .addKey(Blocks.END_STONE)
                .group(1.0F, 1.0F, SoundEvents.DEEPSLATE_BREAK, SoundEvents.DEEPSLATE_STEP, SoundEvents.DEEPSLATE_PLACE, SoundEvents.DEEPSLATE_HIT, SoundEvents.DEEPSLATE_FALL));

        provider.accept("quartz", TagPair.Builder.create()
                .addKey(Blocks.QUARTZ_BLOCK)
                .addKey(Blocks.QUARTZ_STAIRS)
                .addKey(Blocks.QUARTZ_SLAB)
                .addKey(Blocks.CHISELED_QUARTZ_BLOCK)
                .addKey(Blocks.QUARTZ_PILLAR)
                .addKey(Blocks.SMOOTH_QUARTZ)
                .addKey(Blocks.SMOOTH_QUARTZ_STAIRS)
                .addKey(Blocks.SMOOTH_QUARTZ_SLAB)
                .addKey(Blocks.QUARTZ_BRICKS)
                .addKey(Blocks.REDSTONE_BLOCK)
                .addKey(Blocks.DIAMOND_BLOCK)
                .addKey(Blocks.OBSIDIAN)
                .addKey(Blocks.CRYING_OBSIDIAN)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_QUARTZ_BREAK.get(), CustomSounds.BLOCK_QUARTZ_STEP.get(), SoundEvents.DEEPSLATE_PLACE, SoundEvents.STONE_HIT, SoundEvents.DEEPSLATE_FALL));

        provider.accept("raw_gold_block", TagPair.Builder.create()
                .addKey(Blocks.RAW_GOLD_BLOCK)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_RAW_GOLD_BLOCK_BREAK.get(), CustomSounds.BLOCK_RAW_GOLD_BLOCK_STEP.get(), CustomSounds.BLOCK_RAW_GOLD_BLOCK_PLACE.get(), CustomSounds.BLOCK_RAW_GOLD_BLOCK_HIT.get(), CustomSounds.BLOCK_RAW_GOLD_BLOCK_FALL.get()));

        provider.accept("sandstone", TagPair.Builder.create()
                .addKey(Blocks.SANDSTONE)
                .addKey(Blocks.CHISELED_SANDSTONE)
                .addKey(Blocks.CUT_SANDSTONE)
                .addKey(Blocks.SMOOTH_SANDSTONE)
                .addKey(Blocks.RED_SANDSTONE)
                .addKey(Blocks.CHISELED_RED_SANDSTONE)
                .addKey(Blocks.CUT_RED_SANDSTONE)
                .addKey(Blocks.SMOOTH_RED_SANDSTONE)
                .addKey(Blocks.SANDSTONE_SLAB)
                .addKey(Blocks.RED_SANDSTONE_SLAB)
                .addKey(Blocks.CUT_SANDSTONE_SLAB)
                .addKey(Blocks.CUT_RED_SANDSTONE_SLAB)
                .addKey(Blocks.SANDSTONE_STAIRS)
                .addKey(Blocks.SANDSTONE_WALL)
                .addKey(Blocks.RED_SANDSTONE_WALL)
                .addKey(Blocks.RED_SANDSTONE_STAIRS)
                .addKey(Blocks.SMOOTH_SANDSTONE_STAIRS)
                .addKey(Blocks.SMOOTH_SANDSTONE_SLAB)
                .addKey(Blocks.SMOOTH_RED_SANDSTONE_STAIRS)
                .addKey(Blocks.SMOOTH_RED_SANDSTONE_SLAB)
                .group(1.0F, 1.0F, SoundEvents.TUFF_BREAK, CustomSounds.BLOCK_SANDSTONE_STEP.get(), SoundEvents.TUFF_PLACE, SoundEvents.TUFF_HIT, SoundEvents.TUFF_FALL));

        provider.accept("sheet_metal", TagPair.Builder.create()
                .addKey(Blocks.IRON_BARS)
                .addKey(Blocks.IRON_TRAPDOOR)
                .addKey(Blocks.HOPPER)
                .addKey(BlockTags.CAULDRONS)
                .group(1.0F, 1.2F, CustomSounds.BLOCK_SHEET_METAL_BREAK.get(), CustomSounds.BLOCK_SHEET_METAL_STEP.get(), SoundEvents.NETHERITE_BLOCK_PLACE, SoundEvents.NETHERITE_BLOCK_HIT, SoundEvents.NETHERITE_BLOCK_FALL));

        provider.accept("spruce_leaves", TagPair.Builder.create()
                .addKey(Blocks.SPRUCE_LEAVES)
                .addKey(Blocks.SPRUCE_SAPLING)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_SPRUCE_LEAVES_BREAK.get(), CustomSounds.BLOCK_SPRUCE_LEAVES_STEP.get(), CustomSounds.BLOCK_SPRUCE_LEAVES_PLACE.get(), CustomSounds.BLOCK_SPRUCE_LEAVES_HIT.get(), CustomSounds.BLOCK_SPRUCE_LEAVES_FALL.get()));

        provider.accept("spruce_log", TagPair.Builder.create()
                .addKey(Blocks.SPRUCE_LOG)
                .addKey(Blocks.SPRUCE_WOOD)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_SPRUCE_LOG_BREAK.get(), CustomSounds.BLOCK_SPRUCE_LOG_STEP.get(), CustomSounds.BLOCK_SPRUCE_LOG_PLACE.get(), CustomSounds.BLOCK_SPRUCE_LOG_HIT.get(), CustomSounds.BLOCK_SPRUCE_LOG_FALL.get()));

        provider.accept("spruce_object", TagPair.Builder.create()
                .addKey(Blocks.SPRUCE_PRESSURE_PLATE)
                .addKey(Blocks.SPRUCE_FENCE_GATE)
                .addKey(Blocks.SPRUCE_FENCE)
                .addKey(Blocks.SPRUCE_SIGN)
                .addKey(Blocks.SPRUCE_BUTTON)
                .addKey(Blocks.SPRUCE_DOOR)
                .addKey(Blocks.SPRUCE_TRAPDOOR)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_SPRUCE_OBJECT_BREAK.get(), CustomSounds.BLOCK_SPRUCE_PLANKS_STEP.get(), CustomSounds.BLOCK_SPRUCE_OBJECT_PLACE.get(), CustomSounds.BLOCK_SPRUCE_PLANKS_HIT.get(), CustomSounds.BLOCK_SPRUCE_PLANKS_FALL.get()));

        provider.accept("spruce_planks", TagPair.Builder.create()
                .addKey(Blocks.SPRUCE_PLANKS)
                .addKey(Blocks.SPRUCE_STAIRS)
                .addKey(Blocks.SPRUCE_SLAB)
                .addKey(Blocks.STRIPPED_SPRUCE_LOG)
                .addKey(Blocks.STRIPPED_SPRUCE_WOOD)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_SPRUCE_PLANKS_BREAK.get(), CustomSounds.BLOCK_SPRUCE_PLANKS_STEP.get(), CustomSounds.BLOCK_SPRUCE_PLANKS_PLACE.get(), CustomSounds.BLOCK_SPRUCE_PLANKS_HIT.get(), CustomSounds.BLOCK_SPRUCE_PLANKS_FALL.get()));

        provider.accept("stone_bricks", TagPair.Builder.create()
                .addKey(Blocks.STONE_BRICKS)
                .addKey(Blocks.CRACKED_STONE_BRICKS)
                .addKey(Blocks.CHISELED_STONE_BRICKS)
                .addKey(Blocks.INFESTED_STONE_BRICKS)
                .addKey(Blocks.INFESTED_CRACKED_STONE_BRICKS)
                .addKey(Blocks.INFESTED_CHISELED_STONE_BRICKS)
                .addKey(Blocks.STONE_BRICK_SLAB)
                .addKey(Blocks.STONE_BRICK_STAIRS)
                .addKey(Blocks.STONE_BRICK_WALL)
                .group(1.0F, 1.0F, CustomSounds.BLOCK_COBBLESTONE_BREAK.get(), CustomSounds.BLOCK_STONE_BRICKS_STEP.get(), SoundEvents.DEEPSLATE_BRICKS_PLACE, CustomSounds.BLOCK_STONE_BRICKS_HIT.get(), CustomSounds.BLOCK_STONE_BRICKS_FALL.get()));

        provider.accept("terracotta", TagPair.Builder.create()
                .addKey(Blocks.WHITE_TERRACOTTA)
                .addKey(Blocks.ORANGE_TERRACOTTA)
                .addKey(Blocks.MAGENTA_TERRACOTTA)
                .addKey(Blocks.LIGHT_BLUE_TERRACOTTA)
                .addKey(Blocks.YELLOW_TERRACOTTA)
                .addKey(Blocks.LIME_TERRACOTTA)
                .addKey(Blocks.PINK_TERRACOTTA)
                .addKey(Blocks.GRAY_TERRACOTTA)
                .addKey(Blocks.LIGHT_GRAY_TERRACOTTA)
                .addKey(Blocks.CYAN_TERRACOTTA)
                .addKey(Blocks.PURPLE_TERRACOTTA)
                .addKey(Blocks.BLUE_TERRACOTTA)
                .addKey(Blocks.BROWN_TERRACOTTA)
                .addKey(Blocks.GREEN_TERRACOTTA)
                .addKey(Blocks.RED_TERRACOTTA)
                .addKey(Blocks.BLACK_TERRACOTTA)
                .addKey(Blocks.TERRACOTTA)
                .addKey(Blocks.BEDROCK)
                .group(1.0F, 0.6F, SoundEvents.CALCITE_BREAK, SoundEvents.CALCITE_STEP, SoundEvents.CALCITE_PLACE, SoundEvents.CALCITE_HIT, SoundEvents.CALCITE_FALL));
    }
}
/*?}*/