package dev.imb11.sounds.config;

import dev.imb11.sounds.config.utils.ConfigGroup;
import dev.imb11.sounds.sound.ConfiguredSound;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class EventSoundsConfig extends ConfigGroup<EventSoundsConfig> implements YetAnotherConfigLib.ConfigBackedBuilder<EventSoundsConfig> {
    // == STATUS EFFECTS == //
    @SerialEntry
    public ConfiguredSound positiveStatusEffectGainSoundEffect = new ConfiguredSound("positiveStatusEffectGain", SoundEvents.ITEM_TRIDENT_THUNDER, true, 2F, 0.1F);
    @SerialEntry
    public ConfiguredSound negativeStatusEffectGainSoundEffect = new ConfiguredSound("negativeStatusEffectGain", SoundEvents.ENTITY_ILLUSIONER_MIRROR_MOVE, true, 0.3F, 0.2F);

    @SerialEntry
    public ConfiguredSound positiveStatusEffectLoseSoundEffect = new ConfiguredSound("positiveStatusEffectLose", SoundEvents.ITEM_TRIDENT_RIPTIDE_1, true, 0.5F, 0.1F);
    @SerialEntry
    public ConfiguredSound negativeStatusEffectLoseSoundEffect = new ConfiguredSound("negativeStatusEffectLose", SoundEvents.ITEM_TRIDENT_RIPTIDE_1, true, 0.5F, 0.1F);

    public EventSoundsConfig() {
        super(EventSoundsConfig.class);
    }

    @Override
    public YetAnotherConfigLib getYACL() {
        return YetAnotherConfigLib.create(getHandler(), this);
    }

    @Override
    public Identifier getImage() {
        return new Identifier("sounds", "textures/gui/event_sounds.webp");
    }

    @Override
    public Text getName() {
        return Text.translatable("sounds.config.events");
    }

    @Override
    public String getID() {
        return "event";
    }

    @Override
    public YetAnotherConfigLib.Builder build(EventSoundsConfig defaults, EventSoundsConfig config, YetAnotherConfigLib.Builder builder) {
        builder.title(Text.of("Event Sounds"));

        builder.category(ConfigCategory.createBuilder()
                .name(Text.translatable("sounds.config.events.statusEffects"))
                .group(config.positiveStatusEffectGainSoundEffect.getOptionGroup(defaults.positiveStatusEffectGainSoundEffect))
                .group(config.positiveStatusEffectLoseSoundEffect.getOptionGroup(defaults.positiveStatusEffectLoseSoundEffect))
                .group(config.negativeStatusEffectGainSoundEffect.getOptionGroup(defaults.negativeStatusEffectGainSoundEffect))
                .group(config.negativeStatusEffectLoseSoundEffect.getOptionGroup(defaults.negativeStatusEffectLoseSoundEffect))
                .build());

        return builder;
    }
}
