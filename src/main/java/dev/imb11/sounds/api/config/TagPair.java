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
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class TagPair {
    private static final Codec<BlockSoundGroup> BLOCK_GROUP_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.FLOAT.fieldOf("pitch").forGetter(BlockSoundGroup::getPitch),
                    Codec.FLOAT.fieldOf("volume").forGetter(BlockSoundGroup::getVolume),
                    SoundEvent.CODEC.fieldOf("break").forGetter(BlockSoundGroup::getBreakSound),
                    SoundEvent.CODEC.fieldOf("step").forGetter(BlockSoundGroup::getStepSound),
                    SoundEvent.CODEC.fieldOf("place").forGetter(BlockSoundGroup::getPlaceSound),
                    SoundEvent.CODEC.fieldOf("hit").forGetter(BlockSoundGroup::getHitSound),
                    SoundEvent.CODEC.fieldOf("fall").forGetter(BlockSoundGroup::getFallSound)
            ).apply(instance, BlockSoundGroup::new));
    public static final Codec<TagPair> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    TagList.getCodec(Registries.BLOCK.getKey()).fieldOf("keys").forGetter(TagPair::getKeys),
                    BLOCK_GROUP_CODEC.fieldOf("group").forGetter(TagPair::getGroup),
                    Codec.BOOL.fieldOf("enabled").forGetter(TagPair::isEnabled)
            ).apply(instance, TagPair::new));
    private final TagList<Block> keys;
    private BlockSoundGroup group;
    private BlockSoundGroup pendingGroup;
    private boolean enabled;

    public TagList<Block> getKeys() {
        return keys;
    }

    public BlockSoundGroup getGroup() {
        return group;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public TagPair(TagList<Block> keys, BlockSoundGroup group) {
        this.keys = keys;
        this.group = group;
        this.pendingGroup = group;
        this.enabled = true;
    }

    public TagPair(TagList<Block> keys, BlockSoundGroup group, boolean enabled) {
        this(keys, group);
        this.enabled = enabled;
    }

    private void playSound(SoundEvent event) {
        MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(event, group.getPitch(), group.getVolume()));
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
                .name(Text.translatable("sounds.config.preview.name.brackets", Text.translatable("sounds.config.preview." + type).getString()))
                .description(OptionDescription.of(Text.translatable("sounds.config.preview.description")))
                .action((a, b) -> action.accept(null))
                .build();
    }
    
    private Identifier getSoundId(SoundEvent event) {
        //? if <1.21.2 {
        //return event.getId();
        //?} else {
        return event.id();
        //?}
    }

    public Option<String> createSoundOpt(TagPair defaults, String type) {
        return Option.<String>createBuilder()
                .name(Text.translatable("sounds.config.event.name.brackets", Text.translatable("sounds.config.preview." + type).getString()))
                .description(OptionDescription.createBuilder()
                        .text(Text.translatable("sounds.config.event.description")).build())
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
                    var event = Registries.SOUND_EVENT.get(Identifier.tryParse(val));

                    switch (type) {
                        case "break" ->
                                this.group = new BlockSoundGroup(this.group.getPitch(), this.group.getVolume(), event, this.group.getStepSound(), this.group.getPlaceSound(), this.group.getHitSound(), this.group.getFallSound());
                        case "step" ->
                                this.group = new BlockSoundGroup(this.group.getPitch(), this.group.getVolume(), this.group.getBreakSound(), event, this.group.getPlaceSound(), this.group.getHitSound(), this.group.getFallSound());
                        case "place" ->
                                this.group = new BlockSoundGroup(this.group.getPitch(), this.group.getVolume(), this.group.getBreakSound(), this.group.getStepSound(), event, this.group.getHitSound(), this.group.getFallSound());
                        case "hit" ->
                                this.group = new BlockSoundGroup(this.group.getPitch(), this.group.getVolume(), this.group.getBreakSound(), this.group.getStepSound(), this.group.getPlaceSound(), event, this.group.getFallSound());
                        case "fall" ->
                                this.group = new BlockSoundGroup(this.group.getPitch(), this.group.getVolume(), this.group.getBreakSound(), this.group.getStepSound(), this.group.getPlaceSound(), this.group.getHitSound(), event);
                    }
                })
                .listener((opt, val) -> {
                    var event = Registries.SOUND_EVENT.get(Identifier.tryParse(val));

                    switch (type) {
                        case "break" ->
                                this.pendingGroup = new BlockSoundGroup(this.pendingGroup.getPitch(), this.pendingGroup.getVolume(), event, this.pendingGroup.getStepSound(), this.pendingGroup.getPlaceSound(), this.pendingGroup.getHitSound(), this.pendingGroup.getFallSound());
                        case "step" ->
                                this.pendingGroup = new BlockSoundGroup(this.pendingGroup.getPitch(), this.pendingGroup.getVolume(), this.pendingGroup.getBreakSound(), event, this.pendingGroup.getPlaceSound(), this.pendingGroup.getHitSound(), this.pendingGroup.getFallSound());
                        case "place" ->
                                this.pendingGroup = new BlockSoundGroup(this.pendingGroup.getPitch(), this.pendingGroup.getVolume(), this.pendingGroup.getBreakSound(), this.pendingGroup.getStepSound(), event, this.pendingGroup.getHitSound(), this.pendingGroup.getFallSound());
                        case "hit" ->
                                this.pendingGroup = new BlockSoundGroup(this.pendingGroup.getPitch(), this.pendingGroup.getVolume(), this.pendingGroup.getBreakSound(), this.pendingGroup.getStepSound(), this.pendingGroup.getPlaceSound(), event, this.pendingGroup.getFallSound());
                        case "fall" ->
                                this.pendingGroup = new BlockSoundGroup(this.pendingGroup.getPitch(), this.pendingGroup.getVolume(), this.pendingGroup.getBreakSound(), this.pendingGroup.getStepSound(), this.pendingGroup.getPlaceSound(), this.pendingGroup.getHitSound(), event);
                    }
                })
                .controller(opt -> DropdownStringControllerBuilder.create(opt)
                        .allowAnyValue(false)
                        .allowEmptyValue(false)
                        .values(Registries.SOUND_EVENT.getKeys().stream().map(RegistryKey::getValue).map(Identifier::toString).toList()))
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
        private BlockSoundGroup group;
        private boolean enabled;

        private Builder() {
            this.group = BlockSoundGroup.STONE;
            this.enabled = true;
        }

        public static Builder create() {
            return new Builder();
        }

        public Builder group(float pitch, float volume, SoundEvent breakSound, SoundEvent stepSound, SoundEvent placeSound, SoundEvent hitSound, SoundEvent fallSound) {
            this.group = new BlockSoundGroup(pitch, volume, breakSound, stepSound, placeSound, hitSound, fallSound);
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
            keys.add(Either.left(Registries.BLOCK.getKey(key).orElseThrow(() -> new RuntimeException("TagPair.Builder: Could not find RegistryKey for " + key.toString()))));
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
