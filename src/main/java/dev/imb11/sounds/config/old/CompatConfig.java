//package dev.imb11.sounds.config.old;
//
//import com.google.gson.GsonBuilder;
//import com.mineblock11.mru.config.YACLHelper;
//import dev.imb11.sounds.config.adapters.ConfiguredSoundTypeAdapter;
//import dev.imb11.sounds.config.adapters.DynamicConfiguredSoundTypeAdapter;
//import dev.imb11.sounds.sound.ConfiguredSound;
//import dev.imb11.sounds.sound.DynamicConfiguredSound;
//import dev.imb11.sounds.sound.context.ItemStackSoundContext;
//import dev.isxander.yacl3.api.ConfigCategory;
//import dev.isxander.yacl3.api.LabelOption;
//import dev.isxander.yacl3.api.YetAnotherConfigLib;
//import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
//import dev.isxander.yacl3.config.v2.api.SerialEntry;
//import net.fabricmc.loader.api.FabricLoader;
//import net.minecraft.item.ItemStack;
//import net.minecraft.sound.SoundEvents;
//import net.minecraft.text.Text;
//import net.minecraft.util.Formatting;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.function.UnaryOperator;
//
//public class CompatConfig {
//    private static final YACLHelper.NamespacedHelper HELPER = new YACLHelper.NamespacedHelper("sounds.mods");
//    private static final ConfigClassHandler<CompatConfig> GSON = HELPER.createHandler(CompatConfig.class, new ArrayList<UnaryOperator<GsonBuilder>>(List.of(
//            gsonBuilder -> gsonBuilder.registerTypeAdapter(ConfiguredSound.class, new ConfiguredSoundTypeAdapter()),
//            builder -> builder.registerTypeAdapter(DynamicConfiguredSound.class, new DynamicConfiguredSoundTypeAdapter())
//    )));
//    @SerialEntry
//    public final DynamicConfiguredSound<ItemStack, ItemStackSoundContext> emi_click_item = new DynamicConfiguredSound<>("emi_click_item",  SoundEvents.BLOCK_NOTE_BLOCK_HAT, true, 1.4f, 0.2f, true);
//
//    public static CompatConfig get() {
//        return GSON.instance();
//    }
//
//    public static void load() {
//        GSON.load();
//    }
//
//    public static YetAnotherConfigLib getInstance() {
//        return YetAnotherConfigLib.create(GSON,
//                (defaults, config, builder) -> {
//                    var EMI = ConfigCategory.createBuilder()
//                            .name(Text.literal("EMI"));
//
//                    if(FabricLoader.getInstance().isModLoaded("emi")) {
//                        EMI = EMI.groups(List.of(
//                                config.emi_click_item.getOptionGroup(defaults.emi_click_item)
//                        ));
//                    } else {
//                        EMI = EMI.option(LabelOption.create(Text.translatable("sounds.config.mod_not_loaded", "EMI").formatted(Formatting.ITALIC, Formatting.GRAY, Formatting.BOLD)));
//                    }
//
//                    return builder
//                            .category(EMI.build())
//                            .title(Text.empty());
//                });
//    }
//}
