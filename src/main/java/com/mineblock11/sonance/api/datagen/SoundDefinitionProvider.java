package com.mineblock11.sonance.api.datagen;

import com.mineblock11.sonance.api.SoundDefinition;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.core.Registry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import java.util.function.BiConsumer;

public abstract class SoundDefinitionProvider<T> extends FabricCodecDataProvider<SoundDefinition<T>> {
    private final Registry<T> registry;

    protected SoundDefinitionProvider(FabricDataOutput dataOutput, String outputFolder, Registry<T> registry) {
        super(dataOutput, PackOutput.Target.RESOURCE_PACK, "sonance/" + outputFolder, SoundDefinition.getCodec(registry.key()));
        this.registry = registry;
    }

    public SoundDefinition.Builder<T> create(SoundEvent event) {
        return new SoundDefinition.Builder<T>(event, this.registry);
    }

    @Override
    protected void configure(BiConsumer<ResourceLocation, SoundDefinition<T>> provider) {
        accept((s, tSoundDefinition) -> provider.accept(new ResourceLocation(s), tSoundDefinition.build()));
    }

    public abstract void accept(BiConsumer<String, SoundDefinition.Builder<T>> provider);

    @Override
    public String getName() {
        return "SoundDefinition[" + this.registry.key().location() + "]Provider";
    }
}
