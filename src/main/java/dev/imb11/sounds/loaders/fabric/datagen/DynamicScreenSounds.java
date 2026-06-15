/*? if >=1.21 && fabric {*/
package dev.imb11.sounds.loaders.fabric.datagen;

import dev.imb11.sounds.api.SoundDefinition;
import dev.imb11.sounds.api.datagen.SoundDefinitionProvider;
import dev.imb11.sounds.sound.CustomSounds;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class DynamicScreenSounds extends SoundDefinitionProvider<MenuType<?>> {

    protected DynamicScreenSounds(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(dataOutput, registriesFuture, "screens", BuiltInRegistries.MENU);
    }

    @Override
    public void accept(BiConsumer<String, SoundDefinition.Builder<MenuType<?>>> provider) {
        provider.accept("crafting", create(CustomSounds.WOOD)
                .addKey(MenuType.CRAFTING));

        provider.accept("smithing", create(CustomSounds.USE_SMITHING_TABLE)
                .addKey(MenuType.SMITHING));

        provider.accept("anvil", create(CustomSounds.USE_ANVIL)
                .addKey(MenuType.ANVIL));

        provider.accept("smoker", create(CustomSounds.USE_SMOKER)
                .addKey(MenuType.SMOKER));

        provider.accept("furnace", create(CustomSounds.USE_FURNACE)
                .addKey(MenuType.FURNACE)
                .addKey(MenuType.BLAST_FURNACE));

        provider.accept("lectern", create(CustomSounds.USE_LECTERN)
                .addKey(MenuType.LECTERN));

        provider.accept("stonecutter", create(CustomSounds.USE_STONECUTTER)
                .addKey(MenuType.STONECUTTER));

        provider.accept("grindstone", create(CustomSounds.STONE_PLACE)
                .addKey(MenuType.GRINDSTONE));

        provider.accept("beacon", create(CustomSounds.AMETHYST_BLOCK_RESONATE)
                .addKey(MenuType.BEACON));

        provider.accept("brewing_stand", create(CustomSounds.USE_BREWING_STAND)
                .addKey(MenuType.BREWING_STAND));

        provider.accept("loom", create(CustomSounds.USE_LOOM)
                .addKey(MenuType.LOOM));

        provider.accept("cartography_table", create(CustomSounds.USE_CARTOGRAPHY_TABLE)
                .addKey(MenuType.CARTOGRAPHY_TABLE));

        provider.accept("enchantment_table", create(CustomSounds.USE_ENCHANTMENT_TABLE)
                .addKey(MenuType.ENCHANTMENT));

        provider.accept("redstone_item_movement", create(CustomSounds.ITEM_PICK)
                .addKey(MenuType.GENERIC_3x3)
                .addKey(MenuType.HOPPER));
    }
}
/*?}*/