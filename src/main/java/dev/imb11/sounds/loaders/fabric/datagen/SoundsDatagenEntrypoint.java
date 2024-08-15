package dev.imb11.sounds.loaders.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class SoundsDatagenEntrypoint implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        final FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        /*? if >=1.21 {*/
        pack.addProvider(DynamicItemSounds::new);
        pack.addProvider(DynamicScreenSounds::new);
        pack.addProvider(DynamicTagPairs::new);
        /*?}*/
    }
}
