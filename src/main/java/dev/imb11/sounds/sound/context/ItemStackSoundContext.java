package dev.imb11.sounds.sound.context;

import dev.imb11.sounds.api.SoundDefinition;
import dev.imb11.sounds.api.context.DynamicSoundContext;
import dev.imb11.sounds.dynamic.DynamicSoundHelper;
import dev.imb11.sounds.mixin.accessors.BlockAccessor;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemStackSoundContext implements DynamicSoundContext<ItemStack> {
    private final DynamicSoundHelper.BlockSoundType blockSoundType;

    public ItemStackSoundContext() {
        this(DynamicSoundHelper.BlockSoundType.FALL);
    }

    public ItemStackSoundContext(DynamicSoundHelper.BlockSoundType blockSoundType) {
        this.blockSoundType = blockSoundType;
    }

    public static ItemStackSoundContext of(DynamicSoundHelper.BlockSoundType blockSoundType) {
        return new ItemStackSoundContext(blockSoundType);
    }

    @Override
    public SoundInstance handleContext(ItemStack context, ResourceLocation fallback, float pitch, float volume) {
        var item = context.getItem();

        if (item instanceof BlockItem blockItem) {
            var block = blockItem.getBlock();
            fallback = this.blockSoundType.getTransformer().apply(((BlockAccessor)block).invokeGetSoundType(block.defaultBlockState()));
        }

        for (SoundDefinition<Item> definition : DynamicSoundHelper.<Item>getDefinitions("items")) {
            if (definition.getKeys().isValid(item)) {
                fallback = definition.getSoundEvent();

                if (definition.getPitch().isPresent()) {
                    pitch = definition.getPitch().get();
                }

                if (definition.getVolume().isPresent()) {
                    volume = definition.getVolume().get();
                }

                break;
            }
        }

        return createSoundInstance(fallback, pitch, volume);
    }
}
