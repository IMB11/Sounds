/*? if >=1.21 && fabric {*/
package dev.imb11.sounds.api.datagen;

import dev.imb11.sounds.api.SoundDefinition;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public abstract class SoundDefinitionProvider<T> extends FabricCodecDataProvider<SoundDefinition<T>> {
    private final Registry<T> registry;

    protected SoundDefinitionProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registriesFuture, String outputFolder, Registry<T> registry) {
        super(dataOutput, registriesFuture, PackOutput.Target.RESOURCE_PACK, "sounds/" + outputFolder, SoundDefinition.getCodec(registry.key()));
        this.registry = registry;
    }


    public SoundDefinition.Builder<T> create(SoundEvent event) {
        return new SoundDefinition.Builder<T>(event, this.registry);
    }

    public SoundDefinition.Builder<T> create(Holder<SoundEvent> event) {
        //? if <1.21.2 {
        /*var val = BuiltInRegistries.SOUND_EVENT.get(event.unwrapKey().get());
        *///?} else {
        var val = BuiltInRegistries.SOUND_EVENT.getValue(event.unwrapKey().get());
        //?}
        return new SoundDefinition.Builder<T>(val, this.registry);
    }

    @Override
    protected void configure(BiConsumer<ResourceLocation, SoundDefinition<T>> provider, HolderLookup.Provider lookup) {
        accept((s, tSoundDefinition) -> provider.accept(ResourceLocation.parse(s), tSoundDefinition.build()));
    }

    public abstract void accept(BiConsumer<String, SoundDefinition.Builder<T>> provider);

    @Override
    public String getName() {
        return "SoundDefinition[" + this.registry.key().location() + "]Provider";
    }
}
/*?}*/