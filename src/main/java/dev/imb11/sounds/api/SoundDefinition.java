package dev.imb11.sounds.api;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class SoundDefinition<T> {
    public static <T> Codec<SoundDefinition<T>> getCodec(RegistryKey<? extends Registry<T>> registryKey) {
        return RecordCodecBuilder.create(builder -> builder.group(
                    Identifier.CODEC.xmap(SoundEvent::of, SoundEvent::getId).fieldOf("soundEvent").forGetter(SoundDefinition<T>::getSoundEvent),
                    TagList.getCodec(registryKey).fieldOf("keys").forGetter(SoundDefinition<T>::getKeys)
                ).apply(builder, SoundDefinition<T>::new));
    }


    public SoundEvent getSoundEvent() {
        return soundEvent;
    }

    public TagList<T> getKeys() {
        return keys;
    }

    public final SoundEvent soundEvent;

    public final TagList<T> keys;

    public SoundDefinition(SoundEvent soundEvent, TagList<T> keys) {
        this.soundEvent = soundEvent;
        this.keys = keys;
    }

    public static class Builder<T> {
        private final SoundEvent soundEvent;
        private final TagList<T> keys = new TagList<>(new ArrayList<>());
        private final Registry<T> registry;

        public Builder(SoundEvent soundEvent, Registry<T> registry) {
            this.soundEvent = soundEvent;
            this.registry = registry;
        }

        public Builder<T> addKey(T key) {
            keys.add(Either.left(registry.getKey(key).orElseThrow(() -> new RuntimeException("SoundDefinition.Builder: Could not find RegistryKey for " + key.toString()))));
            return this;
        }

        @SafeVarargs
        public final Builder<T> addMultipleKeys(T... keys) {
            for(T key : keys) {
                addKey(key);
            }
            return this;
        }

        @SafeVarargs
        public final Builder<T> addMultipleKeys(TagKey<T>... keys) {
            for(TagKey<T> key : keys) {
                addKey(key);
            }
            return this;
        }

        public Builder<T> addKey(TagKey<T> key) {
            keys.add(Either.right(key));
            return this;
        }

        public SoundDefinition<T> build() {
            return new SoundDefinition<>(soundEvent, keys);
        }
    }
}
