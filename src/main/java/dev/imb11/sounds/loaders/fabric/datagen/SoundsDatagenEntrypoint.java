//? if fabric {
package dev.imb11.sounds.loaders.fabric.datagen;

import dev.imb11.mru.event.fabric.DatagenFinishedCallback;
import dev.imb11.mru.packing.Packer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class SoundsDatagenEntrypoint implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        DatagenFinishedCallback.EVENT.register((outdir) -> Packer.pack(outdir, "**/*.json"));

        final FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        /*? if >=1.21 {*/
        pack.addProvider(DynamicItemSounds::new);
        pack.addProvider(DynamicScreenSounds::new);
        pack.addProvider(DynamicTagPairs::new);
        /*?}*/
    }
}
//?}
