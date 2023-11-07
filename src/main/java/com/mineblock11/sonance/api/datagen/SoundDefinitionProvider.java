package com.mineblock11.sonance.api.datagen;

import com.mineblock11.sonance.api.SoundDefinition;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.data.DataOutput;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

public abstract class SoundDefinitionProvider<T> extends FabricCodecDataProvider<SoundDefinition<T>> {
    private final Registry<T> registry;

    protected SoundDefinitionProvider(FabricDataOutput dataOutput, String outputFolder, Registry<T> registry) {
        super(dataOutput, DataOutput.OutputType.RESOURCE_PACK, "sonance/" + outputFolder, SoundDefinition.getCodec(registry.getKey()));
        this.registry = registry;
    }

    public SoundDefinition.Builder<T> create(SoundEvent event) {
        return new SoundDefinition.Builder<T>(event, this.registry);
    }

    @Override
    protected void configure(BiConsumer<Identifier, SoundDefinition<T>> provider) {
        accept((s, tSoundDefinition) -> provider.accept(new Identifier(s), tSoundDefinition.build()));
    }

    public abstract void accept(BiConsumer<String, SoundDefinition.Builder<T>> provider);

    @Override
    public String getName() {
        return "SoundDefinition[" + this.registry.getKey().getValue() + "]Provider";
    }
}
