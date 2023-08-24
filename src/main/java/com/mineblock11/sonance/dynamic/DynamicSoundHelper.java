package com.mineblock11.sonance.dynamic;

import net.minecraft.item.*;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class DynamicSoundHelper {
    public SoundEvent getItemSound(ItemStack itemStack) {
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
            return block.getSoundGroup(block.getDefaultState()).getPlaceSound();
        }

        return SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
    }
}
