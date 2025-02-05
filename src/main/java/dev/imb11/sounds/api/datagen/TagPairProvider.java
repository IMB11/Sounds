/*? if >=1.21 && fabric {*/
/*package dev.imb11.sounds.api.datagen;

import dev.imb11.sounds.api.SoundDefinition;
import dev.imb11.sounds.api.config.TagPair;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.data.DataOutput;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public abstract class TagPairProvider extends FabricCodecDataProvider<TagPair> {

    protected TagPairProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(dataOutput, registriesFuture, DataOutput.OutputType.RESOURCE_PACK, "sounds/blocks", TagPair.CODEC);
    }

    @Override
    protected void configure(BiConsumer<Identifier, TagPair> provider, RegistryWrapper.WrapperLookup lookup) {
        accept((s, tTagPair) -> provider.accept(Identifier.of(s), tTagPair.build()));
    }

    public abstract void accept(BiConsumer<String, TagPair.Builder> provider);

    @Override
    public String getName() {
        return "TagPairProvider";
    }
}
*//*?}*/