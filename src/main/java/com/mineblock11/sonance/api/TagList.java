package com.mineblock11.sonance.api;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

public class TagList<T> {

    public static <T> Codec<TagList<T>> getCodec(ResourceKey<? extends Registry<T>> registryKey) {
        return Codec.list(
                Codec.either(
                        ResourceKey.codec(registryKey),
                        TagKey.hashedCodec(registryKey)
                )
        ).xmap(TagList::new, TagList::getInternalList);
    }

    private List<Either<ResourceKey<T>, TagKey<T>>> getInternalList() {
        return _list;
    }

    public void add(Either<ResourceKey<T>, TagKey<T>> either) {
        _list.add(either);
    }

    private final List<Either<ResourceKey<T>, TagKey<T>>> _list;

    public TagList(List<Either<ResourceKey<T>, TagKey<T>>> list) {
        _list = list;
    }

    public boolean isValid(T value) {
        for(Either<ResourceKey<T>, TagKey<T>> either : _list) {
            if(either.left().isPresent()) {
                var key = either.left().get();
                Registry<T> registry = (Registry<T>) BuiltInRegistries.REGISTRY.get(key.registry());
                assert registry != null;
                var entry = registry.getKey(value);
                if(either.left().get().location().equals(entry)) {
                    return true;
                }
            } else if (either.right().isPresent()) {
                TagKey<T> tagKey = either.right().get();
                Registry<T> registry = (Registry<T>) BuiltInRegistries.REGISTRY.get(tagKey.registry().location());
                assert registry != null;
                var entry = registry.wrapAsHolder(value);
                if(entry.is(tagKey)) {
                    return true;
                }
            }
        }
        return false;
    }
}
