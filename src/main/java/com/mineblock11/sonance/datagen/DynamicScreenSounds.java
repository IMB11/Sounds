package com.mineblock11.sonance.datagen;

import com.mineblock11.sonance.api.SoundDefinition;
import com.mineblock11.sonance.api.datagen.SoundDefinitionProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.SoundEvents;

import java.util.function.BiConsumer;

public class DynamicScreenSounds extends SoundDefinitionProvider<ScreenHandlerType<?>> {
    protected DynamicScreenSounds(FabricDataOutput dataOutput) {
        super(dataOutput, "screens", Registries.SCREEN_HANDLER);
    }

    @Override
    public void accept(BiConsumer<String, SoundDefinition.Builder<ScreenHandlerType<?>>> provider) {
        provider.accept("crafting", create(SoundEvents.BLOCK_WOOD_HIT)
                .addKey(ScreenHandlerType.CRAFTING));

        provider.accept("smithing", create(SoundEvents.ENTITY_IRON_GOLEM_STEP)
                .addKey(ScreenHandlerType.SMITHING));

        provider.accept("anvil", create(SoundEvents.BLOCK_ANVIL_PLACE)
                .addKey(ScreenHandlerType.ANVIL));

        provider.accept("smoker", create(SoundEvents.BLOCK_CAMPFIRE_CRACKLE)
                .addKey(ScreenHandlerType.SMOKER));

        provider.accept("furnace", create(SoundEvents.BLOCK_FIRE_AMBIENT)
                .addKey(ScreenHandlerType.FURNACE)
                .addKey(ScreenHandlerType.BLAST_FURNACE));

        provider.accept("lectern", create(SoundEvents.ENTITY_VILLAGER_WORK_LIBRARIAN)
                .addKey(ScreenHandlerType.LECTERN));

        provider.accept("stonecutter", create(SoundEvents.ENTITY_VILLAGER_WORK_MASON)
                .addKey(ScreenHandlerType.STONECUTTER));

        provider.accept("grindstone", create(SoundEvents.BLOCK_STONE_PLACE)
                .addKey(ScreenHandlerType.GRINDSTONE));

        provider.accept("beacon", create(SoundEvents.BLOCK_AMETHYST_BLOCK_RESONATE)
                .addKey(ScreenHandlerType.BEACON));

        provider.accept("brewing_stand", create(SoundEvents.ITEM_BOTTLE_EMPTY)
                .addKey(ScreenHandlerType.BREWING_STAND));

        provider.accept("loom", create(SoundEvents.BLOCK_TRIPWIRE_DETACH)
                .addKey(ScreenHandlerType.LOOM));

        provider.accept("cartography_table", create(SoundEvents.ENTITY_VILLAGER_WORK_CARTOGRAPHER)
                .addKey(ScreenHandlerType.CARTOGRAPHY_TABLE));

        provider.accept("enchantment_table", create(SoundEvents.BLOCK_END_PORTAL_FRAME_FILL)
                .addKey(ScreenHandlerType.ENCHANTMENT));
    }
}
