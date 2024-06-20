package dev.imb11.sounds.config.utils;

import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import net.minecraft.text.Text;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ConfigUtil {
    public static Option<Boolean> create(boolean defaultValue, Consumer<Boolean> setter, Supplier<Boolean> getter, String i18n) {
        return Option.<Boolean>createBuilder()
                .name(Text.translatable(i18n + ".name"))
                .description(OptionDescription.of(Text.translatable(i18n + ".description")))
                .binding(defaultValue, getter, setter)
                .controller((opt) -> BooleanControllerBuilder.create(opt).coloured(true).yesNoFormatter())
                .build();
    }
}
