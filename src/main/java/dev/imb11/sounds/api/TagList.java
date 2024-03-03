package dev.imb11.sounds.api;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;

import java.util.List;

public class TagList<T> {

    private final List<Either<RegistryKey<T>, TagKey<T>>> _list;

    public TagList(List<Either<RegistryKey<T>, TagKey<T>>> list) {
        _list = list;
    }

    public static <T> Codec<TagList<T>> getCodec(RegistryKey<? extends Registry<T>> registryKey) {
        return Codec.list(
                Codec.either(
                        RegistryKey.createCodec(registryKey),
                        TagKey.codec(registryKey)
                )
        ).xmap(TagList::new, TagList::getInternalList);
    }

    public List<Either<RegistryKey<T>, TagKey<T>>> getInternalList() {
        return _list;
    }

    public void add(Either<RegistryKey<T>, TagKey<T>> either) {
        _list.add(either);
    }

    public boolean isValid(T value) {
        for (Either<RegistryKey<T>, TagKey<T>> either : _list) {
            if (either.left().isPresent()) {
                var key = either.left().get();
                Registry<T> registry = (Registry<T>) Registries.REGISTRIES.get(key.getRegistry());
                assert registry != null;
                var entry = registry.getId(value);
                if (either.left().get().getValue().equals(entry)) {
                    return true;
                }
            } else if (either.right().isPresent()) {
                TagKey<T> tagKey = either.right().get();
                Registry<T> registry = (Registry<T>) Registries.REGISTRIES.get(tagKey.registry().getValue());
                assert registry != null;
                var entry = registry.getEntry(value);
                if (entry.isIn(tagKey)) {
                    return true;
                }
            }
        }
        return false;
    }
}
