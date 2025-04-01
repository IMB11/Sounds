package dev.imb11.sounds.api.config;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.imb11.sounds.api.TagList;
import dev.imb11.sounds.dynamic.TagPairHelper;
import dev.isxander.yacl3.api.ButtonOption;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.controller.DropdownStringControllerBuilder;
import org.jetbrains.annotations.ApiStatus;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public class TagPair {
    private static final Codec<SoundType> BLOCK_GROUP_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.FLOAT.fieldOf("pitch").forGetter(SoundType::getPitch),
                    Codec.FLOAT.fieldOf("volume").forGetter(SoundType::getVolume),
                    SoundEvent.DIRECT_CODEC.fieldOf("break").forGetter(SoundType::getBreakSound),
                    SoundEvent.DIRECT_CODEC.fieldOf("step").forGetter(SoundType::getStepSound),
                    SoundEvent.DIRECT_CODEC.fieldOf("place").forGetter(SoundType::getPlaceSound),
                    SoundEvent.DIRECT_CODEC.fieldOf("hit").forGetter(SoundType::getHitSound),
                    SoundEvent.DIRECT_CODEC.fieldOf("fall").forGetter(SoundType::getFallSound)
            ).apply(instance, SoundType::new));
    public static final Codec<TagPair> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    TagList.getCodec(BuiltInRegistries.BLOCK.key()).fieldOf("keys").forGetter(TagPair::getKeys),
                    BLOCK_GROUP_CODEC.fieldOf("group").forGetter(TagPair::getGroup),
                    Codec.BOOL.fieldOf("enabled").forGetter(TagPair::isEnabled)
            ).apply(instance, TagPair::new));
    private final TagList<Block> keys;
    private final SoundType group;
    private boolean enabled;

    public TagList<Block> getKeys() {
        return keys;
    }

    public SoundType getGroup() {
        return group;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public TagPair(TagList<Block> keys, SoundType group) {
        this.keys = keys;
        this.group = group;
        this.enabled = true;
    }

    public TagPair(TagList<Block> keys, SoundType group, boolean enabled) {
        this(keys, group);
        this.enabled = enabled;
    }

    public static class Builder {
        private final TagList<Block> keys = new TagList<>(new ArrayList<>());;
        private SoundType group;
        private boolean enabled;

        private Builder() {
            this.group = SoundType.STONE;
            this.enabled = true;
        }

        public static Builder create() {
            return new Builder();
        }

        public Builder group(float pitch, float volume, SoundEvent breakSound, SoundEvent stepSound, SoundEvent placeSound, SoundEvent hitSound, SoundEvent fallSound) {
            this.group = new SoundType(pitch, volume, breakSound, stepSound, placeSound, hitSound, fallSound);
            return this;
        }

        @SafeVarargs
        public final Builder addMultipleKeys(Block... keys) {
            for (Block key : keys) {
                addKey(key);
            }
            return this;
        }

        public Builder addKey(Block key) {
            keys.add(Either.left(BuiltInRegistries.BLOCK.getResourceKey(key).orElseThrow(() -> new RuntimeException("TagPair.Builder: Could not find RegistryKey for " + key.toString()))));
            return this;
        }

        @SafeVarargs
        public final Builder addMultipleKeys(TagKey<Block>... keys) {
            for (TagKey<Block> key : keys) {
                addKey(key);
            }
            return this;
        }

        public Builder addKey(TagKey<Block> key) {
            keys.add(Either.right(key));
            return this;
        }

        public Builder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public TagPair build() {
            return new TagPair(keys, group, enabled);
        }
    }
}
