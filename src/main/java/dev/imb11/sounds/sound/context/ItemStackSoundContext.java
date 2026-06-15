package dev.imb11.sounds.sound.context;

import dev.imb11.sounds.api.SoundDefinition;
import dev.imb11.sounds.api.config.ConfiguredSimpleSoundInstance;
import dev.imb11.sounds.api.context.DynamicSoundContext;
import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.UISoundsConfig;
import dev.imb11.sounds.dynamic.DynamicSoundHelper;
import dev.imb11.sounds.mixin.accessors.BlockAccessor;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import static dev.imb11.sounds.dynamic.TagPairHelper.ITEM_CACHE;
import static dev.imb11.sounds.dynamic.TagPairHelper.ITEM_TAG_CACHE;

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
    public ConfiguredSimpleSoundInstance handleContext(ItemStack context, Identifier fallback, float pitch, float volume) {
        if (SoundsConfig.get(UISoundsConfig.class).enableDynamicItemSounds) {
			Item item = context.getItem();
            if (item instanceof BlockItem blockItem) {
				Block block = blockItem.getBlock();
                fallback = this.blockSoundType.getTransformer().apply(((BlockAccessor)block).invokeGetSoundType(block.defaultBlockState()));
            }

            ResourceKey<Item> key = item.builtInRegistryHolder().key();
            if (ITEM_CACHE.containsKey(key)) {
                SoundDefinition<Item> definition = ITEM_CACHE.get(key);
                fallback = definition.getSoundEvent();

                if (definition.getPitch().isPresent()) {
                    pitch = definition.getPitch().get();
                }

                if (definition.getVolume().isPresent()) {
                    volume = definition.getVolume().get();
                }
            }
            else {
				for (TagKey<Item> itemTagKey : context.tags().toList()) {
                    if (ITEM_TAG_CACHE.containsKey(itemTagKey)) {
                        var definition =  ITEM_TAG_CACHE.get(itemTagKey);
                        ITEM_CACHE.put(key, definition);
                        if (definition.getPitch().isPresent()) {
                            pitch = definition.getPitch().get();
                        }

                        if (definition.getVolume().isPresent()) {
                            volume = definition.getVolume().get();
                        }
                        break;
                    }
                }
			}
        }
        return createSoundInstance(fallback, pitch, volume, false);
    }
}
