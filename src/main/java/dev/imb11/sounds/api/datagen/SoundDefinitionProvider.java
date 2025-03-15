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
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public abstract class SoundDefinitionProvider<T> extends FabricCodecDataProvider<SoundDefinition<T>> {
    private final Registry<T> registry;

    protected SoundDefinitionProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registriesFuture, String outputFolder, Registry<T> registry) {
        super(dataOutput, registriesFuture, PackOutput.Target.RESOURCE_PACK, "sounds/" + outputFolder, SoundDefinition.getCodec(registry.key()));
        this.registry = registry;
    }

    public SoundDefinition.Builder<T> create(ResourceLocation event) {
        return new SoundDefinition.Builder<T>(event, this.registry);
    }

    public SoundDefinition.Builder<T> create(SoundEvent event) {
        //? if >=1.21.2 {
        return this.create(event.location());
        //?} else {
        /*return this.create(event.getLocation());
        *///?}
    }

    public SoundDefinition.Builder<T> create(Holder<SoundEvent> event) {
        return this.create(event.unwrapKey().get().location());
    }

    @Override
    protected void configure(BiConsumer<ResourceLocation, SoundDefinition<T>> provider, HolderLookup.Provider lookup) {
        accept((s, tSoundDefinition) -> provider.accept(ResourceLocation.parse(s), tSoundDefinition.build()));
    }

    public abstract void accept(BiConsumer<String, SoundDefinition.Builder<T>> provider);

    @Override
    public @NotNull String getName() {
        return "SoundDefinition[" + this.registry.key().location() + "]Provider";
    }
}
/*?}*/