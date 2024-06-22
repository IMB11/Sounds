package dev.imb11.sounds.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class SoundsDatagenEntrypoint implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        final FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        /*? if >1.20.6 {*/
        pack.addProvider(DynamicItemSounds::new);
        pack.addProvider(DynamicScreenSounds::new);
        /*?}*/
    }
}
