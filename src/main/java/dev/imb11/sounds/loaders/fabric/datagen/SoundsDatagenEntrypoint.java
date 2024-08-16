//? if fabric {
package dev.imb11.sounds.loaders.fabric.datagen;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class SoundsDatagenEntrypoint implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        final FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        String glob = "**/*.json";

        /*? if >=1.21 {*/
        pack.addProvider(DynamicItemSounds::new);
        dev.imb11.mru.event.fabric.DatagenFinishedCallback.EVENT.register((outdir) -> dev.imb11.mru.packing.Packer.pack(outdir, glob));
        pack.addProvider(DynamicScreenSounds::new);
        pack.addProvider(DynamicTagPairs::new);
        /*?}*/
    }
}
//?}
