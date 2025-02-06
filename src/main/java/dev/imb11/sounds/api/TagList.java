package dev.imb11.sounds.api;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import java.util.Comparator;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

public class TagList<T> {

    private final List<Either<ResourceKey<T>, TagKey<T>>> _list;

    public TagList(List<Either<ResourceKey<T>, TagKey<T>>> list) {
        _list = list;
    }

    public static <T> Codec<TagList<T>> getCodec(ResourceKey<? extends Registry<T>> registryKey) {
        return Codec.list(
                Codec.either(
                        ResourceKey.codec(registryKey),
                        TagKey.hashedCodec(registryKey)
                )
        ).xmap(TagList::new, TagList::getInternalList);
    }

    public List<Either<ResourceKey<T>, TagKey<T>>> getInternalList() {
        return _list;
    }

    public void add(Either<ResourceKey<T>, TagKey<T>> either) {
        _list.add(either);

        _list.sort((o1, o2) -> {
            // Sort alphabetically, tagkeys first then registrykeys
            if (o1.left().isPresent() && o2.right().isPresent()) {
                return -1;
            } else if (o1.right().isPresent() && o2.left().isPresent()) {
                return 1;
            } else if (o1.left().isPresent() && o2.left().isPresent()) {
                return o1.left().get().location().compareTo(o2.left().get().location());
            } else {
                return o1.right().get().location().compareTo(o2.right().get().location());
            }
        });
    }

    public boolean isValid(T value) {
        for (Either<ResourceKey<T>, TagKey<T>> either : _list) {
            if (either.left().isPresent()) {
                var key = either.left().get();
                //? if <1.21.2 {
                /*Registry<T> registry = (Registry<T>) BuiltInRegistries.REGISTRY.get(key.registry());
                 *///?} else {
                Registry<T> registry = (Registry<T>) BuiltInRegistries.REGISTRY.getValue(key.registry());
                //?}
                assert registry != null;
                var entry = registry.getKey(value);
                if (either.left().get().location().equals(entry)) {
                    return true;
                }
            } else if (either.right().isPresent()) {
                TagKey<T> tagKey = either.right().get();
                //? if <1.21.2 {
                /*Registry<T> registry = (Registry<T>) BuiltInRegistries.REGISTRY.get(tagKey.registry().location());
                *///?} else {
                Registry<T> registry = (Registry<T>) BuiltInRegistries.REGISTRY.getValue(tagKey.registry().location());
                //?}
                assert registry != null;
                var entry = registry.wrapAsHolder(value);
                if (entry.is(tagKey)) {
                    return true;
                }
            }
        }
        return false;
    }
}
