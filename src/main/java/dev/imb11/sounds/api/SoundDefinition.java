package dev.imb11.sounds.api;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;

import java.util.ArrayList;
import java.util.Optional;

public class SoundDefinition<T> {
    private final SoundEvent soundEvent;
    private final TagList<T> keys;
    private final Optional<Float> volume;
    private final Optional<Float> pitch;

    protected SoundDefinition(SoundEvent soundEvent, TagList<T> keys, Optional<Float> volume, Optional<Float> pitch) {
        this.soundEvent = soundEvent;
        this.keys = keys;
        this.volume = volume;
        this.pitch = pitch;
    }

    public static <T> Codec<SoundDefinition<T>> getCodec(RegistryKey<? extends Registry<T>> registryKey) {
        return RecordCodecBuilder.create(builder -> builder.group(
                //? if <1.21.2 {
                Identifier.CODEC.xmap(SoundEvent::of, SoundEvent::getId).fieldOf("soundEvent").forGetter(SoundDefinition<T>::getSoundEvent),
                //?} else {
                /*Identifier.CODEC.xmap(SoundEvent::of, SoundEvent::id).fieldOf("soundEvent").forGetter(SoundDefinition<T>::getSoundEvent),
                *///?}
                TagList.getCodec(registryKey).fieldOf("keys").forGetter(SoundDefinition<T>::getKeys),
                Codecs.POSITIVE_FLOAT.optionalFieldOf("volume").forGetter(SoundDefinition<T>::getVolume),
                Codecs.POSITIVE_FLOAT.optionalFieldOf("pitch").forGetter(SoundDefinition<T>::getPitch)
        ).apply(builder, SoundDefinition<T>::new));
    }

    public SoundEvent getSoundEvent() {
        return soundEvent;
    }

    public TagList<T> getKeys() {
        return keys;
    }

    public Optional<Float> getPitch() {
        return pitch;
    }

    public Optional<Float> getVolume() {
        return volume;
    }

    public static class Builder<T> {
        private final SoundEvent soundEvent;
        private final TagList<T> keys = new TagList<>(new ArrayList<>());
        private final Registry<T> registry;
        private Optional<Float> volume = Optional.empty();
        private Optional<Float> pitch = Optional.empty();

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
            for (T key : keys) {
                addKey(key);
            }
            return this;
        }

        @SafeVarargs
        public final Builder<T> addMultipleKeys(TagKey<T>... keys) {
            for (TagKey<T> key : keys) {
                addKey(key);
            }
            return this;
        }

        public Builder<T> addKey(TagKey<T> key) {
            keys.add(Either.right(key));
            return this;
        }

        public Builder<T> setVolume(float volume) {
            this.volume = Optional.of(volume);
            return this;
        }

        public Builder<T> setPitch(float pitch) {
            this.pitch = Optional.of(pitch);
            return this;
        }

        public SoundDefinition<T> build() {
            return new SoundDefinition<>(soundEvent, keys, volume, pitch);
        }
    }
}
