package com.mineblock11.sonance.api;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;

public class SoundDefinition<T> {
    public static <T> Codec<SoundDefinition<T>> getCodec(ResourceKey<? extends Registry<T>> registryKey) {
        return RecordCodecBuilder.create(builder -> builder.group(
                    ResourceLocation.CODEC.xmap(SoundEvent::createVariableRangeEvent, SoundEvent::getLocation).fieldOf("soundEvent").forGetter(SoundDefinition<T>::getSoundEvent),
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
            keys.add(Either.left(registry.getResourceKey(key).orElseThrow(() -> new RuntimeException("SoundDefinition.Builder: Could not find RegistryKey for " + key.toString()))));
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
