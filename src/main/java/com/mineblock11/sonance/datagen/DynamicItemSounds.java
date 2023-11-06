package com.mineblock11.sonance.datagen;

import com.mineblock11.sonance.SonanceClient;
import com.mineblock11.sonance.api.SoundDefinition;
import com.mineblock11.sonance.api.datagen.SoundDefinitionProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class DynamicItemSounds extends SoundDefinitionProvider<Item> {
    protected DynamicItemSounds(FabricDataOutput dataOutput) {
        super(dataOutput, "items", Registries.ITEM);
    }

    @Override
    public void accept(BiConsumer<String, SoundDefinition.Builder<Item>> provider) {
        provider.accept("wooden_equipment", create(SoundEvents.ITEM_AXE_STRIP)
                .addKey(Items.WOODEN_AXE)
                .addKey(Items.WOODEN_HOE)
                .addKey(Items.WOODEN_PICKAXE)
                .addKey(Items.WOODEN_SHOVEL)
                .addKey(Items.WOODEN_SWORD)
                .addKey(Items.CARROT_ON_A_STICK)
                .addKey(Items.STICK)
                .addKey(Items.WARPED_FUNGUS_ON_A_STICK)
                .addKey(Items.DEBUG_STICK));

        provider.accept("stone_equipment", create(SoundEvents.ITEM_ARMOR_EQUIP_GENERIC)
                .addKey(Items.STONE_AXE)
                .addKey(Items.STONE_HOE)
                .addKey(Items.STONE_PICKAXE)
                .addKey(Items.STONE_SHOVEL)
                .addKey(Items.STONE_SWORD));

        provider.accept("chainmail_equipment", create(SoundEvents.ITEM_ARMOR_EQUIP_CHAIN)
                .addKey(Items.CHAINMAIL_HELMET)
                .addKey(Items.CHAINMAIL_CHESTPLATE)
                .addKey(Items.CHAINMAIL_LEGGINGS)
                .addKey(Items.CHAINMAIL_BOOTS));

        provider.accept("iron_equipment", create(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                .addKey(Items.SHEARS)
                .addKey(Items.SHIELD)
                .addKey(Items.IRON_AXE)
                .addKey(Items.IRON_HOE)
                .addKey(Items.IRON_PICKAXE)
                .addKey(Items.IRON_SHOVEL)
                .addKey(Items.IRON_SWORD)
                .addKey(Items.IRON_HORSE_ARMOR)
                .addKey(Items.IRON_HELMET)
                .addKey(Items.IRON_CHESTPLATE)
                .addKey(Items.IRON_LEGGINGS)
                .addKey(Items.IRON_BOOTS));

        provider.accept("golden_equipment", create(SoundEvents.ITEM_ARMOR_EQUIP_GOLD)
                .addKey(Items.GOLDEN_AXE)
                .addKey(Items.GOLDEN_HOE)
                .addKey(Items.GOLDEN_PICKAXE)
                .addKey(Items.GOLDEN_SHOVEL)
                .addKey(Items.GOLDEN_SWORD)
                .addKey(Items.CLOCK)
                .addKey(Items.GOLDEN_HORSE_ARMOR)
                .addKey(Items.GOLDEN_HELMET)
                .addKey(Items.GOLDEN_CHESTPLATE)
                .addKey(Items.GOLDEN_LEGGINGS)
                .addKey(Items.GOLDEN_BOOTS));

        provider.accept("diamond_equipment", create(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND)
                .addKey(Items.DIAMOND_AXE)
                .addKey(Items.DIAMOND_HOE)
                .addKey(Items.DIAMOND_PICKAXE)
                .addKey(Items.DIAMOND_SHOVEL)
                .addKey(Items.DIAMOND_SWORD)
                .addKey(Items.DIAMOND_HORSE_ARMOR)
                .addKey(Items.DIAMOND_HELMET)
                .addKey(Items.DIAMOND_CHESTPLATE)
                .addKey(Items.DIAMOND_LEGGINGS)
                .addKey(Items.DIAMOND_BOOTS));

        provider.accept("netherite_equipment", create(SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE)
                .addKey(Items.NETHERITE_AXE)
                .addKey(Items.NETHERITE_HOE)
                .addKey(Items.NETHERITE_PICKAXE)
                .addKey(Items.NETHERITE_SHOVEL)
                .addKey(Items.NETHERITE_SWORD)
                .addKey(Items.NETHERITE_HELMET)
                .addKey(Items.NETHERITE_CHESTPLATE)
                .addKey(Items.NETHERITE_LEGGINGS)
                .addKey(Items.NETHERITE_BOOTS));

        provider.accept("leather_equipment", create(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER)
                .addKey(Items.LEATHER_HORSE_ARMOR)
                .addKey(Items.LEATHER_HELMET)
                .addKey(Items.LEATHER_CHESTPLATE)
                .addKey(Items.LEATHER_LEGGINGS)
                .addKey(Items.LEATHER_BOOTS));

        provider.accept("elytra", create(SoundEvents.ITEM_ARMOR_EQUIP_ELYTRA)
                .addKey(Items.ELYTRA));

        provider.accept("trident", create(SoundEvents.ITEM_TRIDENT_HIT)
                .addKey(Items.TRIDENT));

        provider.accept("bows", create(SoundEvents.ENTITY_ARROW_SHOOT)
                .addKey(Items.BOW)
                .addKey(Items.CROSSBOW));

        provider.accept("fishing_rods", create(SoundEvents.ENTITY_FISHING_BOBBER_SPLASH)
                .addKey(Items.FISHING_ROD));

        provider.accept("flint_and_steel", create(SoundEvents.ITEM_FLINTANDSTEEL_USE)
                .addKey(Items.FLINT_AND_STEEL));

        provider.accept("dyes", create(SoundEvents.ITEM_DYE_USE)
                .addKey(ConventionalItemTags.DYES));

        provider.accept("fireworks", create(SoundEvents.ITEM_FIRECHARGE_USE)
                .addKey(Items.FIREWORK_ROCKET));

        provider.accept("ingot_metals", create(SoundEvents.BLOCK_METAL_BREAK)
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

        provider.accept("shiny_metals", create(SoundEvents.BLOCK_AMETHYST_CLUSTER_HIT)
                .addKey(Items.AMETHYST_SHARD)
                .addKey(Items.QUARTZ)
                .addKey(Items.EMERALD)
                .addKey(Items.LAPIS_LAZULI)
                .addKey(Items.DIAMOND));

        provider.accept("dirty_metals", create(SoundEvents.BLOCK_GRAVEL_HIT)
                .addKey(Items.COAL)
                .addKey(Items.CHARCOAL)
                .addKey(Items.FLINT)
                .addKey(Items.CLAY_BALL));
    }
}
