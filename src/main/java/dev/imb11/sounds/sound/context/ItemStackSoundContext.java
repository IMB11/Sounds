package dev.imb11.sounds.sound.context;

import dev.imb11.sounds.api.SoundDefinition;
import dev.imb11.sounds.api.context.DynamicSoundContext;
import dev.imb11.sounds.dynamic.DynamicSoundHelper;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.random.Random;

import java.lang.reflect.Field;

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
    public SoundInstance handleContext(ItemStack context, SoundEvent fallback, float pitch, float volume) {
        var item = context.getItem();

        if (item instanceof BlockItem blockItem) {
            var block = blockItem.getBlock();
            fallback = this.blockSoundType.getTransformer().apply(block.getSoundGroup(block.getDefaultState()));
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

    @Override
    public SoundInstance getExample(SoundEvent fallback, float pitch, float volume) {
        return handleContext(Registries.ITEM.getRandom(Random.create()).get().value().getDefaultStack(), fallback, pitch, volume);
    }
}
