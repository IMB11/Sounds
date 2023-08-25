package com.mineblock11.sonance.dynamic;

import net.minecraft.item.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

import java.util.function.Function;

public class DynamicSoundHelper {
    public static SoundEvent getItemSound(ItemStack itemStack, SoundEvent defaultSoundEvent, BlockSoundType type) {
        var item = itemStack.getItem();
        if (item instanceof ToolItem toolItem) {
            var mat = toolItem.getMaterial();
            if (mat.equals(ToolMaterials.WOOD)) {
                return SoundEvents.ITEM_AXE_STRIP;
            } else if (mat.equals(ToolMaterials.NETHERITE)) {
                return SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE;
            } else if (mat.equals(ToolMaterials.IRON)) {
                return SoundEvents.ITEM_ARMOR_EQUIP_IRON;
            } else if (mat.equals(ToolMaterials.GOLD)) {
                return SoundEvents.ITEM_ARMOR_EQUIP_GOLD;
            } else if (mat.equals(ToolMaterials.DIAMOND)) {
                return SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
            } else {
                return SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
            }
        }

        if (item instanceof ArmorItem armorItem) {
            return armorItem.getEquipSound();
        }

        if (item instanceof ElytraItem) {
            return SoundEvents.ITEM_ARMOR_EQUIP_ELYTRA;
        }

        if (item instanceof DyeItem) {
            return SoundEvents.ITEM_DYE_USE;
        }

        if (item instanceof BlockItem blockItem) {
            var block = blockItem.getBlock();
            return type.getTransformer().apply(block.getSoundGroup(block.getDefaultState()));
        }

        return defaultSoundEvent;
    }

    public enum BlockSoundType {
        PLACE(BlockSoundGroup::getPlaceSound),
        HIT(BlockSoundGroup::getHitSound),
        BREAK(BlockSoundGroup::getBreakSound),
        FALL(BlockSoundGroup::getFallSound),
        STEP(BlockSoundGroup::getStepSound);

        private final Function<BlockSoundGroup, SoundEvent> transformer;

        BlockSoundType(Function<BlockSoundGroup, SoundEvent> transformer) {
            this.transformer = transformer;
        }

        public Function<BlockSoundGroup, SoundEvent> getTransformer() {
            return transformer;
        }
    }
}
