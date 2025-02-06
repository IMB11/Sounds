package dev.imb11.sounds.config;

import dev.imb11.sounds.config.utils.ConfigGroup;
import dev.imb11.sounds.api.config.ConfiguredSound;
import dev.imb11.sounds.sound.CustomSounds;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;

import static dev.imb11.sounds.config.SoundsConfig.HELPER;

public class EventSoundsConfig extends ConfigGroup<EventSoundsConfig> implements YetAnotherConfigLib.ConfigBackedBuilder<EventSoundsConfig> {
    // == STATUS EFFECTS == //
    @SerialEntry
    public boolean ignoreSilencedStatusEffects = true;
    @SerialEntry
    public ConfiguredSound positiveStatusEffectGainSoundEffect = new ConfiguredSound("positiveStatusEffectGain", SoundEvents.TRIDENT_THUNDER, true, 2F, 0.1F);
    @SerialEntry
    public ConfiguredSound negativeStatusEffectGainSoundEffect = new ConfiguredSound("negativeStatusEffectGain", SoundEvents.ILLUSIONER_MIRROR_MOVE, true, 0.3F, 0.2F);

    @SerialEntry
    public ConfiguredSound positiveStatusEffectLoseSoundEffect = new ConfiguredSound("positiveStatusEffectLose", SoundEvents.TRIDENT_RIPTIDE_1, true, 0.5F, 0.1F);
    @SerialEntry
    public ConfiguredSound negativeStatusEffectLoseSoundEffect = new ConfiguredSound("negativeStatusEffectLose", SoundEvents.TRIDENT_RIPTIDE_1, true, 0.5F, 0.1F);

    public EventSoundsConfig() {
        super(EventSoundsConfig.class);
    }

    @Override
    public YetAnotherConfigLib getYACL() {
        return YetAnotherConfigLib.create(getHandler(), this);
    }

    @Override
    public ResourceLocation getImage() {
        return ResourceLocation.fromNamespaceAndPath("sounds", "textures/gui/event_sounds.webp");
    }

    @Override
    public Component getName() {
        return Component.translatable("sounds.config.events");
    }

    @Override
    public String getID() {
        return "event";
    }

    @Override
    public YetAnotherConfigLib.Builder build(EventSoundsConfig defaults, EventSoundsConfig config, YetAnotherConfigLib.Builder builder) {
        builder.title(Component.nullToEmpty("Event Sounds"));

        builder.category(ConfigCategory.createBuilder()
                .name(Component.translatable("sounds.config.events.statusEffects"))
                .option(HELPER.get("ignoreSilencedStatusEffects", config.ignoreSilencedStatusEffects, () -> config.ignoreSilencedStatusEffects, v -> config.ignoreSilencedStatusEffects = v))
                .group(config.positiveStatusEffectGainSoundEffect.getOptionGroup(defaults.positiveStatusEffectGainSoundEffect))
                .group(config.positiveStatusEffectLoseSoundEffect.getOptionGroup(defaults.positiveStatusEffectLoseSoundEffect))
                .group(config.negativeStatusEffectGainSoundEffect.getOptionGroup(defaults.negativeStatusEffectGainSoundEffect))
                .group(config.negativeStatusEffectLoseSoundEffect.getOptionGroup(defaults.negativeStatusEffectLoseSoundEffect))
                .build());

        return builder;
    }
}
