package dev.imb11.sounds.api.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.WorldSoundsConfig;
import dev.isxander.yacl3.api.ButtonOption;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.DropdownStringControllerBuilder;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import net.fabricmc.fabric.api.tag.client.v1.ClientTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
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
                    Identifier.CODEC.fieldOf("blockTag").forGetter(TagPair::getBlockTagID),
                    BLOCK_GROUP_CODEC.fieldOf("group").forGetter(TagPair::getGroup),
                    Codec.BOOL.fieldOf("enabled").forGetter(TagPair::isEnabled)
            ).apply(instance, TagPair::new));
    private final TagKey<Block> blockTag;
    private BlockSoundGroup group;
    private BlockSoundGroup pendingGroup;
    private boolean enabled;

    /**
     * Outside the mixin for debugging reasons.
     */
    @ApiStatus.Internal
    public static void handleTagPair(BlockState state, CallbackInfoReturnable<BlockSoundGroup> cir) {
        var entry = state.getRegistryEntry();
        AtomicReference<Supplier<TagPair>> result = new AtomicReference<>(null);

        Stream<Supplier<TagPair>> tagPairStream = SoundsConfig.get(WorldSoundsConfig.class)
                .getAllTagPairs().stream();

        tagPairStream.parallel().forEach(allTagPair -> {
            if(result.get() != null) {
                return;
            }

            if (ClientTags.isInWithLocalFallback(allTagPair.get().getBlockTag(), entry)) {
                result.set(allTagPair);
            }
        });

        if (result.get() != null) {
            TagPair pair = result.get().get();
            System.out.println("Using custom sound for " + pair.getBlockTagID().toString());
            System.out.println("Enabled: " + pair.isEnabled());
            if(pair.isEnabled()) {
                cir.setReturnValue(pair.getGroup());
            }
        }
    }

    public Identifier getBlockTagID() {
        return blockTag.id();
    }

    public TagKey<Block> getBlockTag() {
        return blockTag;
    }

    public BlockSoundGroup getGroup() {
        return group;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public TagPair(TagKey<Block> blockTag, BlockSoundGroup group) {
        this.blockTag = blockTag;
        this.group = group;
        this.pendingGroup = group;
        this.enabled = true;
    }

    public TagPair(Identifier blockTag, BlockSoundGroup group, boolean enabled) {
        this(TagKey.of(RegistryKeys.BLOCK, blockTag), group);
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

    public Option<String> createSoundOpt(TagPair defaults, String type) {
        return Option.<String>createBuilder()
                .name(Text.translatable("sounds.config.event.name.brackets", Text.translatable("sounds.config.preview." + type).getString()))
                .description(OptionDescription.createBuilder()
                        .text(Text.translatable("sounds.config.event.description")).build())
                .binding((switch (type) {
                    case "break":
                        yield defaults.group.getBreakSound().getId().toString();
                    case "step":
                        yield defaults.group.getStepSound().getId().toString();
                    case "place":
                        yield defaults.group.getPlaceSound().getId().toString();
                    case "hit":
                        yield defaults.group.getHitSound().getId().toString();
                    case "fall":
                        yield defaults.group.getFallSound().getId().toString();
                    default:
                        yield "";
                }), () -> switch (type) {
                    case "break" -> this.group.getBreakSound().getId().toString();
                    case "step" -> this.group.getStepSound().getId().toString();
                    case "place" -> this.group.getPlaceSound().getId().toString();
                    case "hit" -> this.group.getHitSound().getId().toString();
                    case "fall" -> this.group.getFallSound().getId().toString();
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
                .binding(defaults.group.getVolume(), () -> this.group.getVolume(), (val) -> this.group = new BlockSoundGroup(this.group.pitch, val, this.group.getBreakSound(), this.group.getStepSound(), this.group.getPlaceSound(), this.group.getHitSound(), this.group.getFallSound()))
                .listener((opt, val) -> {
                    this.pendingGroup = new BlockSoundGroup(this.pendingGroup.pitch, val, this.pendingGroup.getBreakSound(), this.pendingGroup.getStepSound(), this.pendingGroup.getPlaceSound(), this.pendingGroup.getHitSound(), this.pendingGroup.getFallSound());
                })
                .controller(opt -> FloatSliderControllerBuilder.create(opt).step(0.1f).range(0f, 2f))
                .build();

        var pitchOpt = Option.<Float>createBuilder()
                .name(Text.translatable("sounds.config.pitch.name"))
                .description(OptionDescription.createBuilder()
                        .text(Text.translatable("sounds.config.pitch.description")).build())
                .binding(defaults.group.getPitch(), () -> this.group.getPitch(), (val) -> this.group = new BlockSoundGroup(val, this.group.volume, this.group.getBreakSound(), this.group.getStepSound(), this.group.getPlaceSound(), this.group.getHitSound(), this.group.getFallSound()))
                .listener((opt, val) -> {
                    this.pendingGroup = new BlockSoundGroup(val, this.pendingGroup.volume, this.pendingGroup.getBreakSound(), this.pendingGroup.getStepSound(), this.pendingGroup.getPlaceSound(), this.pendingGroup.getHitSound(), this.pendingGroup.getFallSound());
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
    }
}
