/*? if >=1.21 && fabric {*/
/*package dev.imb11.sounds.loaders.fabric.datagen;

import dev.imb11.sounds.api.SoundDefinition;
import dev.imb11.sounds.api.datagen.SoundDefinitionProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.inventory.MenuType;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class DynamicScreenSounds extends SoundDefinitionProvider<MenuType<?>> {

    protected DynamicScreenSounds(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(dataOutput, registriesFuture, "screens", BuiltInRegistries.MENU);
    }

    @Override
    public void accept(BiConsumer<String, SoundDefinition.Builder<MenuType<?>>> provider) {
        provider.accept("crafting", create(SoundEvents.WOOD_HIT)
                .addKey(MenuType.CRAFTING));

        provider.accept("smithing", create(SoundEvents.IRON_GOLEM_STEP)
                .addKey(MenuType.SMITHING));

        provider.accept("anvil", create(SoundEvents.ANVIL_PLACE)
                .addKey(MenuType.ANVIL));

        provider.accept("smoker", create(SoundEvents.CAMPFIRE_CRACKLE)
                .addKey(MenuType.SMOKER));

        provider.accept("furnace", create(SoundEvents.FIRE_AMBIENT)
                .addKey(MenuType.FURNACE)
                .addKey(MenuType.BLAST_FURNACE));

        provider.accept("lectern", create(SoundEvents.VILLAGER_WORK_LIBRARIAN)
                .addKey(MenuType.LECTERN));

        provider.accept("stonecutter", create(SoundEvents.VILLAGER_WORK_MASON)
                .addKey(MenuType.STONECUTTER));

        provider.accept("grindstone", create(SoundEvents.STONE_PLACE)
                .addKey(MenuType.GRINDSTONE));

        provider.accept("beacon", create(SoundEvents.AMETHYST_BLOCK_RESONATE)
                .addKey(MenuType.BEACON));

        provider.accept("brewing_stand", create(SoundEvents.BOTTLE_EMPTY)
                .addKey(MenuType.BREWING_STAND));

        provider.accept("loom", create(SoundEvents.TRIPWIRE_DETACH)
                .addKey(MenuType.LOOM));

        provider.accept("cartography_table", create(SoundEvents.VILLAGER_WORK_CARTOGRAPHER)
                .addKey(MenuType.CARTOGRAPHY_TABLE));

        provider.accept("enchantment_table", create(SoundEvents.END_PORTAL_FRAME_FILL)
                .addKey(MenuType.ENCHANTMENT));

        provider.accept("redstone_item_movement", create(SoundEvents.STONE_HIT)
                .addKey(MenuType.GENERIC_3x3)
                .addKey(MenuType.HOPPER));
    }
}
*//*?}*/