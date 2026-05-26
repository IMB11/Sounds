//? fabric {
package dev.imb11.sounds.loaders.fabric.datagen;

import dev.imb11.sounds.api.SoundDefinition;
import dev.imb11.sounds.api.datagen.SoundDefinitionProvider;
import dev.imb11.sounds.sound.CustomSounds;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class DynamicItemSounds extends SoundDefinitionProvider<Item> {
    protected DynamicItemSounds(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(dataOutput, registriesFuture, "items", BuiltInRegistries.ITEM);
    }

    @Override
    public void accept(BiConsumer<String, SoundDefinition.Builder<Item>> provider) {
        provider.accept("anvils", create(CustomSounds.ANVIL_FALL)
                .addKey(Items.ANVIL)
                .addKey(Items.CHIPPED_ANVIL)
                .addKey(Items.DAMAGED_ANVIL));

        provider.accept("trial_keys", create(CustomSounds.TRIAL_KEY)
                .addKey(Items.TRIAL_KEY)
                .addKey(Items.OMINOUS_TRIAL_KEY)
                .setPitch(1f)
                .setVolume(0.4f));

        provider.accept("wind_charge", create(CustomSounds.WIND_CHARGE)
                .addKey(Items.WIND_CHARGE)
                .setPitch(2f)
                .setVolume(0.1f));

        provider.accept("wooden_equipment", create(CustomSounds.WOODEN_EQUIPMENT)
                .addKey(Items.WOODEN_AXE)
                .addKey(Items.WOODEN_HOE)
                .addKey(Items.WOODEN_PICKAXE)
                .addKey(Items.WOODEN_SHOVEL)
                .addKey(Items.WOODEN_SWORD)
                .addKey(Items.WOODEN_SPEAR)
                .addKey(Items.CARROT_ON_A_STICK)
                .addKey(Items.STICK)
                .addKey(Items.WARPED_FUNGUS_ON_A_STICK)
                .addKey(Items.DEBUG_STICK));

        provider.accept("stone_equipment", create(CustomSounds.EQUIP_GENERIC)
                .addKey(Items.STONE_AXE)
                .addKey(Items.STONE_HOE)
                .addKey(Items.STONE_PICKAXE)
                .addKey(Items.STONE_SHOVEL)
                .addKey(Items.STONE_SPEAR)
                .addKey(Items.STONE_SWORD));


        provider.accept("copper_equipment", create(CustomSounds.EQUIP_COPPER)
                .addKey(Items.COPPER_AXE)
                .addKey(Items.COPPER_HOE)
                .addKey(Items.COPPER_PICKAXE)
                .addKey(Items.COPPER_SHOVEL)
                .addKey(Items.COPPER_SPEAR)
                .addKey(Items.COPPER_SWORD)
                .addKey(Items.COPPER_HORSE_ARMOR)
                .addKey(Items.COPPER_NAUTILUS_ARMOR)
                .addKey(Items.COPPER_HELMET)
                .addKey(Items.COPPER_CHESTPLATE)
                .addKey(Items.COPPER_LEGGINGS)
                .addKey(Items.COPPER_BOOTS));

        provider.accept("chainmail_equipment", create(CustomSounds.CHAIN)
                .addKey(Items.CHAINMAIL_HELMET)
                .addKey(Items.CHAINMAIL_CHESTPLATE)
                .addKey(Items.CHAINMAIL_LEGGINGS)
                .addKey(Items.CHAINMAIL_BOOTS));

        provider.accept("iron_equipment", create(CustomSounds.EQUIP_IRON)
                .addKey(Items.SHEARS)
                .addKey(Items.SHIELD)
                .addKey(Items.IRON_AXE)
                .addKey(Items.IRON_HOE)
                .addKey(Items.IRON_PICKAXE)
                .addKey(Items.IRON_SHOVEL)
                .addKey(Items.IRON_SPEAR)
                .addKey(Items.IRON_SWORD)
                .addKey(Items.IRON_HORSE_ARMOR)
                .addKey(Items.IRON_NAUTILUS_ARMOR)
                .addKey(Items.IRON_HELMET)
                .addKey(Items.IRON_CHESTPLATE)
                .addKey(Items.IRON_LEGGINGS)
                .addKey(Items.IRON_BOOTS));

        provider.accept("golden_equipment", create(CustomSounds.EQUIP_GOLD)
                .addKey(Items.GOLDEN_AXE)
                .addKey(Items.GOLDEN_HOE)
                .addKey(Items.GOLDEN_PICKAXE)
                .addKey(Items.GOLDEN_SHOVEL)
                .addKey(Items.GOLDEN_SPEAR)
                .addKey(Items.GOLDEN_SWORD)
                .addKey(Items.CLOCK)
                .addKey(Items.GOLDEN_HORSE_ARMOR)
                .addKey(Items.GOLDEN_NAUTILUS_ARMOR)
                .addKey(Items.GOLDEN_HELMET)
                .addKey(Items.GOLDEN_CHESTPLATE)
                .addKey(Items.GOLDEN_LEGGINGS)
                .addKey(Items.GOLDEN_BOOTS));

        provider.accept("diamond_equipment", create(CustomSounds.EQUIP_DIAMOND)
                .addKey(Items.DIAMOND_AXE)
                .addKey(Items.DIAMOND_HOE)
                .addKey(Items.DIAMOND_PICKAXE)
                .addKey(Items.DIAMOND_SHOVEL)
                .addKey(Items.DIAMOND_SPEAR)
                .addKey(Items.DIAMOND_SWORD)
                .addKey(Items.DIAMOND_HORSE_ARMOR)
                .addKey(Items.DIAMOND_NAUTILUS_ARMOR)
                .addKey(Items.DIAMOND_HELMET)
                .addKey(Items.DIAMOND_CHESTPLATE)
                .addKey(Items.DIAMOND_LEGGINGS)
                .addKey(Items.DIAMOND_BOOTS));

        provider.accept("netherite_equipment", create(CustomSounds.EQUIP_NETHERITE)
                .addKey(Items.NETHERITE_AXE)
                .addKey(Items.NETHERITE_HOE)
                .addKey(Items.NETHERITE_PICKAXE)
                .addKey(Items.NETHERITE_SHOVEL)
                .addKey(Items.NETHERITE_SPEAR)
                .addKey(Items.NETHERITE_SWORD)
                .addKey(Items.NETHERITE_HORSE_ARMOR)
                .addKey(Items.NETHERITE_NAUTILUS_ARMOR)
                .addKey(Items.MACE)
                .addKey(Items.NETHERITE_HELMET)
                .addKey(Items.NETHERITE_CHESTPLATE)
                .addKey(Items.NETHERITE_LEGGINGS)
                .addKey(Items.NETHERITE_BOOTS));

        provider.accept("leather_equipment", create(CustomSounds.EQUIP_LEATHER)
                .addKey(Items.LEATHER_HORSE_ARMOR)
                .addKey(Items.LEATHER_HELMET)
                .addKey(Items.LEATHER_CHESTPLATE)
                .addKey(Items.LEATHER_LEGGINGS)
                .addKey(Items.LEATHER_BOOTS)
                .addKey(Items.LEATHER)
                .addKey(Items.SADDLE)
                .addKey(Items.BRUSH)
                .addKey(Items.WOLF_ARMOR)
                .addKey(Items.RABBIT_HIDE));

        provider.accept("exoskeletal", create(CustomSounds.EQUIP_TURTLE)
                .addKey(Items.ARMADILLO_SCUTE)
                .addKey(Items.TURTLE_SCUTE)
                .addKey(Items.TURTLE_EGG)
                .setPitch(1.8f)
                .setVolume(0.5f));

        provider.accept("enchanting_books", create(CustomSounds.USE_ENCHANTMENT_TABLE)
                .addKey(Items.ENCHANTED_BOOK)
                .addKey(Items.EXPERIENCE_BOTTLE)
                .setVolume(0.2f)
                .setPitch(0.01f));

        provider.accept("crop_food", create(CustomSounds.CROP_FOODS)
                .addKey(ConventionalItemTags.VEGETABLE_FOODS)
                .addKey(ConventionalItemTags.FRUIT_FOODS)
                .addKey(ConventionalItemTags.BERRY_FOODS)
                .addKey(ConventionalItemTags.BREAD_FOODS)
                .setVolume(1.0f)
                .setPitch(1.76f));

        provider.accept("bowl_food", create(CustomSounds.SOUP_FOODS)
                .addKey(ConventionalItemTags.SOUP_FOODS)
                .setVolume(0.5f)
                .setPitch(1.58f));

        provider.accept("meat_and_fish", create(CustomSounds.MEAT_AND_FISH_FOOD)
                 .addKey(ConventionalItemTags.RAW_FISH_FOODS)
                .addKey(ConventionalItemTags.RAW_MEAT_FOODS)
                .addKey(ConventionalItemTags.COOKED_FISH_FOODS)
                .addKey(ConventionalItemTags.COOKED_MEAT_FOODS)
                .setPitch(1.8f)
                .setVolume(0.20f));

        provider.accept("elytra", create(CustomSounds.EQUIP_ELYTRA)
                .addKey(Items.ELYTRA));

        provider.accept("trident", create(CustomSounds.TRIDENT)
                .addKey(Items.TRIDENT));

        provider.accept("bows", create(CustomSounds.BOW)
                .addKey(Items.BOW)
                .addKey(Items.CROSSBOW));

        provider.accept("fishing_rods", create(SoundEvents.POINTED_DRIPSTONE_DRIP_WATER_INTO_CAULDRON)
                .addKey(Items.FISHING_ROD));

        provider.accept("flint_and_steel", create(CustomSounds.FLINTANDSTEEL_USE)
                .addKey(Items.FLINT_AND_STEEL));

        provider.accept("dyes", create(SoundEvents.DYE_USE)
                .addKey(ConventionalItemTags.DYES));

        provider.accept("papers", create(SoundEvents.BOOK_PAGE_TURN)
                .addKey(Items.PAPER)
                .addKey(Items.FILLED_MAP)
                .addKey(Items.MAP));

        provider.accept("fireworks", create(SoundEvents.BAMBOO_SAPLING_HIT)
                .addKey(Items.FIREWORK_ROCKET));

        provider.accept("ingot_metals", create(SoundEvents.METAL_BREAK)
                .addKey(Items.IRON_INGOT)
                .addKey(Items.GOLD_INGOT)
                .addKey(Items.NETHERITE_INGOT)
                .addKey(Items.COPPER_INGOT)
                .addKey(Items.IRON_NUGGET)
                .addKey(Items.GOLD_NUGGET)
                .addKey(Items.NETHERITE_SCRAP)
                .addKey(Items.RAW_GOLD)
                .addKey(Items.RAW_IRON)
                .addKey(Items.RAW_COPPER));

        provider.accept("shiny_metals", create(SoundEvents.AMETHYST_CLUSTER_HIT)
                .addKey(Items.AMETHYST_SHARD)
                .addKey(Items.QUARTZ)
                .addKey(Items.EMERALD)
                .addKey(Items.LAPIS_LAZULI)
                .addKey(Items.DIAMOND));

        provider.accept("bowl", create(CustomSounds.BOWL)
                .addKey(Items.BOWL)
                .setPitch(1.85f)
                .setVolume(0.25f));

        provider.accept("oak_boat", create(CustomSounds.WOOD)
                .addKey(Items.OAK_BOAT)
                .addKey(Items.OAK_CHEST_BOAT)
                .setPitch(1.75f)
                .setVolume(0.75f));

        provider.accept("birch_boat", create(CustomSounds.BLOCK_BIRCH_PLANKS_HIT)
                .addKey(Items.BIRCH_BOAT)
                .addKey(Items.BIRCH_CHEST_BOAT)
                .setPitch(1.75f)
                .setVolume(0.75f));

        provider.accept("spruce_boat", create(CustomSounds.BLOCK_SPRUCE_PLANKS_HIT)
                .addKey(Items.SPRUCE_BOAT)
                .addKey(Items.SPRUCE_CHEST_BOAT)
                .setPitch(1.75f)
                .setVolume(0.75f));

        provider.accept("jungle_boat", create(CustomSounds.BLOCK_JUNGLE_PLANKS_HIT)
                .addKey(Items.JUNGLE_BOAT)
                .addKey(Items.JUNGLE_CHEST_BOAT)
                .setPitch(1.75f)
                .setVolume(0.75f));

        provider.accept("acacia_boat", create(CustomSounds.BLOCK_ACACIA_PLANKS_HIT)
                .addKey(Items.ACACIA_BOAT)
                .addKey(Items.ACACIA_CHEST_BOAT)
                .setPitch(1.75f)
                .setVolume(0.75f));

        provider.accept("dark_oak_boat", create(CustomSounds.WOOD)
                .addKey(Items.DARK_OAK_BOAT)
                .addKey(Items.DARK_OAK_CHEST_BOAT)
                .setPitch(1.75f)
                .setVolume(0.75f));

        provider.accept("pale_oak_boat", create(CustomSounds.WOOD)
                .addKey(Items.PALE_OAK_BOAT)
                .addKey(Items.PALE_OAK_CHEST_BOAT)
                .setPitch(1.75f)
                .setVolume(0.75f));

        provider.accept("cherry_boat", create(CustomSounds.WOOD)
                .addKey(Items.CHERRY_BOAT)
                .addKey(Items.CHERRY_CHEST_BOAT)
                .setPitch(1.75f)
                .setVolume(0.75f));

        provider.accept("mangrove_boat", create(CustomSounds.BLOCK_MANGROVE_PLANKS_HIT)
                .addKey(Items.MANGROVE_BOAT)
                .addKey(Items.MANGROVE_CHEST_BOAT)
                .setPitch(1.75f)
                .setVolume(0.75f));

        provider.accept("bamboo_raft", create(CustomSounds.BAMBOO_WOOD)
                .addKey(Items.BAMBOO_RAFT)
                .addKey(Items.BAMBOO_CHEST_RAFT)
                .setPitch(1.75f)
                .setVolume(0.75f));

        provider.accept("minecarts", create(SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON)
                .addKey(Items.MINECART)
                .addKey(Items.CHEST_MINECART)
                .addKey(Items.FURNACE_MINECART)
                .addKey(Items.TNT_MINECART)
                .addKey(Items.HOPPER_MINECART)
                .addKey(Items.COMMAND_BLOCK_MINECART)
                .setPitch(1.75f)
                .setVolume(0.75f));

        provider.accept("dirty_metals", create(SoundEvents.GRAVEL_HIT)
                .addKey(Items.COAL)
                .addKey(Items.CHARCOAL)
                .addKey(Items.FLINT)
                .addKey(Items.CLAY_BALL)
                .addKey(Items.REDSTONE)
                .addKey(Items.GLOWSTONE)
                .addKey(Items.GUNPOWDER));

        provider.accept("shards", create(SoundEvents.DECORATED_POT_STEP)
                .addKey(Items.DISC_FRAGMENT_5)
                .addKey(ItemTags.DECORATED_POT_SHERDS));

        provider.accept("smithing_templates", create(CustomSounds.USE_SMITHING_TABLE)
                .addKey(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE)
                .addKey(TagKey.create(Registries.ITEM, Identifier.parse("trim_templates"))));

        provider.accept("filled_buckets", create(SoundEvents.BUCKET_FILL)
                .addKey(ConventionalItemTags.WATER_BUCKETS)
                .addKey(ConventionalItemTags.ENTITY_WATER_BUCKETS));

        provider.accept("banner_templates", create(CustomSounds.WOOL)
                .addKey(Items.CREEPER_BANNER_PATTERN)
                .addKey(Items.FLOWER_BANNER_PATTERN)
                .addKey(Items.GLOBE_BANNER_PATTERN)
                .addKey(Items.MOJANG_BANNER_PATTERN)
                .addKey(Items.SKULL_BANNER_PATTERN)
                .addKey(Items.PIGLIN_BANNER_PATTERN));

        provider.accept("brewing_items", create(CustomSounds.BOTTLE_FILL)
                .addKey(Items.GLASS_BOTTLE)
                .addKey(Items.POTION)
                .addKey(Items.DRAGON_BREATH)
                .addKey(Items.SPLASH_POTION)
                .addKey(Items.OMINOUS_BOTTLE)
                .addKey(Items.LINGERING_POTION));

        provider.accept("wet_mob_drops", create(SoundEvents.SLIME_BLOCK_HIT)
                .addKey(Items.SLIME_BALL)
                .addKey(Items.HONEYCOMB)
                .addKey(Items.HONEY_BOTTLE)
                .addKey(Items.FERMENTED_SPIDER_EYE)
                .addKey(Items.BLAZE_POWDER)
                .addKey(Items.RABBIT_FOOT)
                .addKey(Items.SPIDER_EYE)
                .addKey(Items.ROTTEN_FLESH)
                .addKey(Items.GLISTERING_MELON_SLICE)
                .addKey(Items.MAGMA_CREAM)
                .addKey(Items.GHAST_TEAR));

        provider.accept("sculk", create(SoundEvents.SCULK_VEIN_FALL)
                .addKey(Items.ECHO_SHARD));

        provider.accept("bones", create(SoundEvents.BONE_BLOCK_HIT)
                .addKey(Items.BONE)
                .addKey(Items.BONE_MEAL));

        // Spawn Eggs

        List<Item> spawnEggs = BuiltInRegistries.ITEM.stream().filter(item -> item instanceof SpawnEggItem).toList();
        provider.accept("spawn_eggs", create(SoundEvents.SNIFFER_EGG_PLOP)
                .addKey(Items.EGG)
                .addMultipleKeys(spawnEggs));
    }

//    private Identifier getAmbientSoundForEntity(EntityType<?> entityType) {
//        return Identifier.of("entity." + entityType.getUntranslatedName() + ".ambient");
//    }
}
//?}