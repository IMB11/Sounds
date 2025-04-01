package dev.imb11.sounds.api;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Optional;

import dev.imb11.mru.RegistryUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;

public class SoundDefinition<T> {
    private final ResourceLocation soundEvent;
    private final TagList<T> keys;
    private final Optional<Float> volume;
    private final Optional<Float> pitch;

    protected SoundDefinition(ResourceLocation soundEvent, TagList<T> keys, Optional<Float> volume, Optional<Float> pitch) {
        this.soundEvent = soundEvent;
        this.keys = keys;
        this.volume = volume;
        this.pitch = pitch;
    }

    public static <T> Codec<SoundDefinition<T>> getCodec(ResourceKey<? extends Registry<T>> registryKey) {
        return RecordCodecBuilder.create(builder -> builder.group(
                ResourceLocation.CODEC.fieldOf("soundEvent").forGetter(i -> i.soundEvent),
                TagList.getCodec(registryKey).fieldOf("keys").forGetter(SoundDefinition<T>::getKeys),
                ExtraCodecs.POSITIVE_FLOAT.optionalFieldOf("volume").forGetter(SoundDefinition<T>::getVolume),
                ExtraCodecs.POSITIVE_FLOAT.optionalFieldOf("pitch").forGetter(SoundDefinition<T>::getPitch)
        ).apply(builder, SoundDefinition<T>::new));
    }

    public ResourceLocation getSoundEvent() {
        return this.soundEvent;
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
        private final ResourceLocation soundEvent;
        private final TagList<T> keys = new TagList<>(new ArrayList<>());
        private final Registry<T> registry;
        private Optional<Float> volume = Optional.empty();
        private Optional<Float> pitch = Optional.empty();

        public Builder(ResourceLocation soundEvent, Registry<T> registry) {
            this.soundEvent = soundEvent;
            this.registry = registry;
        }

        public Builder<T> addKey(T key) {
            keys.add(Either.left(registry.getResourceKey(key).orElseThrow(() -> new RuntimeException("SoundDefinition.Builder: Could not find RegistryKey for " + key.toString()))));
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
