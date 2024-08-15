/*? if >=1.21 && fabric {*/
package dev.imb11.sounds.api.datagen;

import dev.imb11.sounds.api.SoundDefinition;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.data.DataOutput;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public abstract class SoundDefinitionProvider<T> extends FabricCodecDataProvider<SoundDefinition<T>> {
    private final Registry<T> registry;

    protected SoundDefinitionProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture, String outputFolder, Registry<T> registry) {
        super(dataOutput, registriesFuture, DataOutput.OutputType.RESOURCE_PACK, "sounds/" + outputFolder, SoundDefinition.getCodec(registry.getKey()));
        this.registry = registry;
    }


    public SoundDefinition.Builder<T> create(SoundEvent event) {
        return new SoundDefinition.Builder<T>(event, this.registry);
    }

    public SoundDefinition.Builder<T> create(RegistryEntry<SoundEvent> event) {
        return new SoundDefinition.Builder<T>(Registries.SOUND_EVENT.get(event.getKey().get()), this.registry);
    }

    @Override
    protected void configure(BiConsumer<Identifier, SoundDefinition<T>> provider, RegistryWrapper.WrapperLookup lookup) {
        accept((s, tSoundDefinition) -> provider.accept(Identifier.of(s), tSoundDefinition.build()));
    }

    public abstract void accept(BiConsumer<String, SoundDefinition.Builder<T>> provider);

    @Override
    public String getName() {
        return "SoundDefinition[" + this.registry.getKey().getValue() + "]Provider";
    }
}
/*?}*/