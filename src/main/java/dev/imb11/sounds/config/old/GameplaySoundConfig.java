//package dev.imb11.sounds.config.old;
//
//import com.google.gson.GsonBuilder;
//import com.mineblock11.mru.config.YACLHelper;
//import dev.imb11.sounds.config.adapters.ConfiguredSoundTypeAdapter;
//import dev.imb11.sounds.config.adapters.DynamicConfiguredSoundTypeAdapter;
//import dev.imb11.sounds.sound.ConfiguredSound;
//import dev.imb11.sounds.sound.DynamicConfiguredSound;
//import dev.imb11.sounds.sound.context.RepeaterSoundContext;
//import dev.isxander.yacl3.api.ConfigCategory;
//import dev.isxander.yacl3.api.YetAnotherConfigLib;
//import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
//import dev.isxander.yacl3.config.v2.api.SerialEntry;
//import net.minecraft.sound.SoundEvents;
//import net.minecraft.text.Text;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.function.UnaryOperator;
//
//public class GameplaySoundConfig {
//    private static final YACLHelper.NamespacedHelper HELPER = new YACLHelper.NamespacedHelper("sounds.gameplay");
//    private static final ConfigClassHandler<GameplaySoundConfig> GSON = HELPER.createHandler(GameplaySoundConfig.class, new ArrayList<UnaryOperator<GsonBuilder>>(List.of(
//            gsonBuilder -> gsonBuilder.registerTypeAdapter(ConfiguredSound.class, new ConfiguredSoundTypeAdapter()),
//            builder -> builder.registerTypeAdapter(DynamicConfiguredSound.class, new DynamicConfiguredSoundTypeAdapter())
//    )));
//
//    @SerialEntry
//
//    @SerialEntry
//    public ConfiguredSound positiveStatusEffectGainSoundEffect = new ConfiguredSound("positiveStatusEffectGain", SoundEvents.BLOCK_NOTE_BLOCK_CHIME, true, 1.4F, 0.5F);
//    @SerialEntry
//    public ConfiguredSound negativeStatusEffectGainSoundEffect = new ConfiguredSound("negativeStatusEffectGain", SoundEvents.ENTITY_ALLAY_HURT, true, 0.5F, 0.5F);
//
//    @SerialEntry
//    public ConfiguredSound positiveStatusEffectLoseSoundEffect = new ConfiguredSound("positiveStatusEffectLose", SoundEvents.ENTITY_ALLAY_ITEM_THROWN, true, 0.5F, 0.5F);
//    @SerialEntry
//    public ConfiguredSound negativeStatusEffectLoseSoundEffect = new ConfiguredSound("negativeStatusEffectLose", SoundEvents.ENTITY_ALLAY_ITEM_THROWN, true, 0.5F, 0.5F);

//
//    public static void load() {
//        GSON.load();
//    }
//
//    public static GameplaySoundConfig get() {
//        return GSON.instance();
//    }
//
//    public static YetAnotherConfigLib getInstance() {
//        return YetAnotherConfigLib.create(GSON,
//                (defaults, config, builder) -> builder
//                        .category(ConfigCategory.createBuilder()
//                                .name(Text.translatable("sounds.config.gameplay.redstone"))
//                                .groups(List.of(
//                                        config.repeaterUseSoundEffect.getOptionGroup(defaults.repeaterUseSoundEffect, true),
//                                        config.jukeboxUseSoundEffect.getOptionGroup(defaults.jukeboxUseSoundEffect),
//                                        config.daylightDetectorUseSoundEffect.getOptionGroup(defaults.daylightDetectorUseSoundEffect),
//                                        config.furnaceMinecartFuelSoundEffect.getOptionGroup(defaults.furnaceMinecartFuelSoundEffect)
//                                ))
//                                .build())
//                        .category(ConfigCategory.createBuilder()
//                                .name(Text.translatable("sounds.config.gameplay.status_effects"))
//                                .groups(List.of(
//                                        config.positiveStatusEffectGainSoundEffect.getOptionGroup(defaults.positiveStatusEffectGainSoundEffect),
//                                        config.negativeStatusEffectGainSoundEffect.getOptionGroup(defaults.negativeStatusEffectGainSoundEffect),
//                                        config.positiveStatusEffectLoseSoundEffect.getOptionGroup(defaults.positiveStatusEffectLoseSoundEffect),
//                                        config.negativeStatusEffectLoseSoundEffect.getOptionGroup(defaults.negativeStatusEffectLoseSoundEffect)
//                                ))
//                                .build())
//                        .category(ConfigCategory.createBuilder()
//                                .name(Text.translatable("sounds.config.gameplay.entities"))
//                                .groups(List.of(
//                                        config.bowPullSoundEffect.getOptionGroup(defaults.bowPullSoundEffect)
//                                ))
//                                .build())
//                        .title(Text.empty())
//        );
//    }
//}
