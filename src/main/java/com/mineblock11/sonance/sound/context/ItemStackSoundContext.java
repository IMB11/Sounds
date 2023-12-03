package com.mineblock11.sonance.sound.context;

import com.mineblock11.sonance.api.SoundDefinition;
import com.mineblock11.sonance.dynamic.DynamicSoundHelper;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import org.jetbrains.annotations.Nullable;

public class ItemStackSoundContext implements DynamicSoundContext<ItemStack> {
    private final DynamicSoundHelper.BlockSoundType blockSoundType;

    public ItemStackSoundContext(DynamicSoundHelper.BlockSoundType blockSoundType) {
        this.blockSoundType = blockSoundType;
    }

    public static ItemStackSoundContext of(DynamicSoundHelper.BlockSoundType blockSoundType) {
        return new ItemStackSoundContext(blockSoundType);
    }

    @Override
    public  SoundInstance handleContext(ItemStack context, SoundEvent fallback, float pitch, float volume) {
        var item = context.getItem();

        for (SoundDefinition<Item> definition : DynamicSoundHelper.<Item>getDefinitions("items")) {
            if (definition.keys.isValid(item)) {
                fallback = definition.soundEvent;
                break;
            }
        }

        if (item instanceof BlockItem blockItem) {
            var block = blockItem.getBlock();
            fallback = this.blockSoundType.getTransformer().apply(block.getSoundGroup(block.getDefaultState()));
        }

        return createSoundInstance(fallback, pitch, volume);
    }
}
