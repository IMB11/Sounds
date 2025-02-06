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
    private SoundType group;
    private SoundType pendingGroup;
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
        this.pendingGroup = group;
        this.enabled = true;
    }

    public TagPair(TagList<Block> keys, SoundType group, boolean enabled) {
        this(keys, group);
        this.enabled = enabled;
    }

    private void playSound(SoundEvent event) {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(event, group.getPitch(), group.getVolume()));
    }

    public ButtonOption createAction(String type) {
        Consumer<Void> action = (e) -> {
            switch (type) {
                case "break":
                    playSound(group.getBreakSound());
                    break;
                case "step":
                    playSound(group.getStepSound());
                    break;
                case "place":
                    playSound(group.getPlaceSound());
                    break;
                case "hit":
                    playSound(group.getHitSound());
                    break;
                case "fall":
                    playSound(group.getFallSound());
                    break;
            }
        };

        return ButtonOption.createBuilder()
                .name(Component.translatable("sounds.config.preview.name.brackets", Component.translatable("sounds.config.preview." + type).getString()))
                .description(OptionDescription.of(Component.translatable("sounds.config.preview.description")))
                .action((a, b) -> action.accept(null))
                .build();
    }
    
    private ResourceLocation getSoundId(SoundEvent event) {
        //? if <1.21.2 {
        //return event.getId();
        //?} else {
        return event.location();
        //?}
    }

    public Option<String> createSoundOpt(TagPair defaults, String type) {
        return Option.<String>createBuilder()
                .name(Component.translatable("sounds.config.event.name.brackets", Component.translatable("sounds.config.preview." + type).getString()))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("sounds.config.event.description")).build())
                .binding((switch (type) {
                    case "break":
                        yield getSoundId(defaults.group.getBreakSound()).toString();
                    case "step":
                        yield getSoundId(defaults.group.getStepSound()).toString();
                    case "place":
                        yield getSoundId(defaults.group.getPlaceSound()).toString();
                    case "hit":
                        yield getSoundId(defaults.group.getHitSound()).toString();
                    case "fall":
                        yield getSoundId(defaults.group.getFallSound()).toString();
                    default:
                        yield "";
                }), () -> switch (type) {
                    case "break" -> getSoundId(this.group.getBreakSound()).toString();
                    case "step" -> getSoundId(this.group.getStepSound()).toString();
                    case "place" -> getSoundId(this.group.getPlaceSound()).toString();
                    case "hit" -> getSoundId(this.group.getHitSound()).toString();
                    case "fall" -> getSoundId(this.group.getFallSound()).toString();
                    default -> "";
                }, (val) -> {
                    var event = BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.tryParse(val));

                    switch (type) {
                        case "break" ->
                                this.group = new SoundType(this.group.getPitch(), this.group.getVolume(), event, this.group.getStepSound(), this.group.getPlaceSound(), this.group.getHitSound(), this.group.getFallSound());
                        case "step" ->
                                this.group = new SoundType(this.group.getPitch(), this.group.getVolume(), this.group.getBreakSound(), event, this.group.getPlaceSound(), this.group.getHitSound(), this.group.getFallSound());
                        case "place" ->
                                this.group = new SoundType(this.group.getPitch(), this.group.getVolume(), this.group.getBreakSound(), this.group.getStepSound(), event, this.group.getHitSound(), this.group.getFallSound());
                        case "hit" ->
                                this.group = new SoundType(this.group.getPitch(), this.group.getVolume(), this.group.getBreakSound(), this.group.getStepSound(), this.group.getPlaceSound(), event, this.group.getFallSound());
                        case "fall" ->
                                this.group = new SoundType(this.group.getPitch(), this.group.getVolume(), this.group.getBreakSound(), this.group.getStepSound(), this.group.getPlaceSound(), this.group.getHitSound(), event);
                    }
                })
                .listener((opt, val) -> {
                    var event = BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.tryParse(val));

                    switch (type) {
                        case "break" ->
                                this.pendingGroup = new SoundType(this.pendingGroup.getPitch(), this.pendingGroup.getVolume(), event, this.pendingGroup.getStepSound(), this.pendingGroup.getPlaceSound(), this.pendingGroup.getHitSound(), this.pendingGroup.getFallSound());
                        case "step" ->
                                this.pendingGroup = new SoundType(this.pendingGroup.getPitch(), this.pendingGroup.getVolume(), this.pendingGroup.getBreakSound(), event, this.pendingGroup.getPlaceSound(), this.pendingGroup.getHitSound(), this.pendingGroup.getFallSound());
                        case "place" ->
                                this.pendingGroup = new SoundType(this.pendingGroup.getPitch(), this.pendingGroup.getVolume(), this.pendingGroup.getBreakSound(), this.pendingGroup.getStepSound(), event, this.pendingGroup.getHitSound(), this.pendingGroup.getFallSound());
                        case "hit" ->
                                this.pendingGroup = new SoundType(this.pendingGroup.getPitch(), this.pendingGroup.getVolume(), this.pendingGroup.getBreakSound(), this.pendingGroup.getStepSound(), this.pendingGroup.getPlaceSound(), event, this.pendingGroup.getFallSound());
                        case "fall" ->
                                this.pendingGroup = new SoundType(this.pendingGroup.getPitch(), this.pendingGroup.getVolume(), this.pendingGroup.getBreakSound(), this.pendingGroup.getStepSound(), this.pendingGroup.getPlaceSound(), this.pendingGroup.getHitSound(), event);
                    }
                })
                .controller(opt -> DropdownStringControllerBuilder.create(opt)
                        .allowAnyValue(false)
                        .allowEmptyValue(false)
                        .values(BuiltInRegistries.SOUND_EVENT.registryKeySet().stream().map(ResourceKey::location).map(ResourceLocation::toString).toList()))
                .build();
    }

    /**
    public OptionGroup createYACL(TagPair defaults) {
        var breakOpt = createAction("break");
        var stepOpt = createAction("step");
        var placeOpt = createAction("place");
        var hitOpt = createAction("hit");
        var fallOpt = createAction("fall");

        var volumeOpt = Option.<Float>createBuilder()
                .name(Text.translatable("sounds.config.volume.name"))
                .description(OptionDescription.createBuilder()
                        .text(Text.translatable("sounds.config.volume.description")).build())
                .binding(defaults.group.getVolume(), () -> this.group.getVolume(), (val) -> this.group = new BlockSoundGroup(val, this.group.pitch, this.group.getBreakSound(), this.group.getStepSound(), this.group.getPlaceSound(), this.group.getHitSound(), this.group.getFallSound()))
                .listener((opt, val) -> {
                    this.pendingGroup = new BlockSoundGroup(val, this.pendingGroup.pitch, this.pendingGroup.getBreakSound(), this.pendingGroup.getStepSound(), this.pendingGroup.getPlaceSound(), this.pendingGroup.getHitSound(), this.pendingGroup.getFallSound());
                })
                .controller(opt -> FloatSliderControllerBuilder.create(opt).step(0.1f).range(0f, 2f))
                .build();

        var pitchOpt = Option.<Float>createBuilder()
                .name(Text.translatable("sounds.config.pitch.name"))
                .description(OptionDescription.createBuilder()
                        .text(Text.translatable("sounds.config.pitch.description")).build())
                .binding(defaults.group.getPitch(), () -> this.group.getPitch(), (val) -> this.group = new BlockSoundGroup(this.group.volume, val, this.group.getBreakSound(), this.group.getStepSound(), this.group.getPlaceSound(), this.group.getHitSound(), this.group.getFallSound()))
                .listener((opt, val) -> {
                    this.pendingGroup = new BlockSoundGroup(this.pendingGroup.volume, val, this.pendingGroup.getBreakSound(), this.pendingGroup.getStepSound(), this.pendingGroup.getPlaceSound(), this.pendingGroup.getHitSound(), this.pendingGroup.getFallSound());
                })
                .controller(opt -> FloatSliderControllerBuilder.create(opt).step(0.1f).range(0f, 2f))
                .build();

        var breakSound = createSoundOpt(defaults, "break");
        var stepSound = createSoundOpt(defaults, "step");
        var placeSound = createSoundOpt(defaults, "place");
        var hitSound = createSoundOpt(defaults, "hit");
        var fallSound = createSoundOpt(defaults, "fall");

        var shouldPlay = Option.<Boolean>createBuilder()
                .name(Text.translatable("sounds.config.shouldPlay.name"))
                .description(OptionDescription.createBuilder()
                        .text(Text.translatable("sounds.config.shouldPlay.description")).build())
                .binding(defaults.enabled, () -> this.enabled, (val) -> this.enabled = val)
                .listener((opt, val) -> {
                    breakSound.setAvailable(val);
                    stepSound.setAvailable(val);
                    placeSound.setAvailable(val);
                    hitSound.setAvailable(val);
                    pitchOpt.setAvailable(val);
                    volumeOpt.setAvailable(val);
                })
                .controller(opt -> BooleanControllerBuilder.create(opt).coloured(true).yesNoFormatter())
                .build();

        return OptionGroup.createBuilder()
                .name(Text.translatable(blockTag.id().toString()))
                .collapsed(true)
                .description(OptionDescription.of(Text.translatable("sounds.config.tag.description")))
                .options(List.of(
                        shouldPlay,
                        volumeOpt,
                        pitchOpt,
                        breakSound,
                        stepSound,
                        placeSound,
                        hitSound,
                        fallSound,
                        breakOpt,
                        stepOpt,
                        placeOpt,
                        hitOpt,
                        fallOpt
                ))
                .build();
    }*/

    /****/
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
