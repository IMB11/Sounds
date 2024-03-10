package dev.imb11.sounds.config;

import dev.imb11.sounds.config.utils.ConfigGroup;
import dev.imb11.sounds.sound.ConfiguredSound;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.FloatFieldControllerBuilder;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ChatSoundsConfig extends ConfigGroup<ChatSoundsConfig> implements YetAnotherConfigLib.ConfigBackedBuilder<ChatSoundsConfig> {
    // == MESSAGING SOUNDS == //
    @SerialEntry
    public final ConfiguredSound typingSoundEffect = new ConfiguredSound("typing", SoundEvents.BLOCK_NOTE_BLOCK_HAT, true, 1.6f, 0.4f);
    @SerialEntry
    public final ConfiguredSound messageSoundEffect = new ConfiguredSound("message", SoundEvents.BLOCK_NOTE_BLOCK_HAT, true, 2.0f, 0.8f);
    @SerialEntry
    public final ConfiguredSound mentionSoundEffect = new ConfiguredSound("mention", SoundEvents.BLOCK_NOTE_BLOCK_CHIME, true, 1.8f, 0.9f);
    @SerialEntry
    public boolean useAtForChatMentions = true;
    @SerialEntry
    public boolean ignoreSystemChats = false;
    // == COOLDOWN SETTINGS == //
    @SerialEntry
    public boolean enableChatSoundCooldown = false;
    @SerialEntry
    public float chatSoundCooldown = 0.5f;

    public ChatSoundsConfig() {
        super(ChatSoundsConfig.class);
    }

    @Override
    public YetAnotherConfigLib getYACL() {
        return YetAnotherConfigLib.create(getHandler(), this);
    }

    @Override
    public Identifier getImage() {
        return new Identifier("sounds", "textures/gui/chat_sounds.webp");
    }

    @Override
    public Text getName() {
        return Text.translatable("sounds.config.chat");
    }

    @Override
    public String getID() {
        return "chat";
    }

    @Override
    public YetAnotherConfigLib.Builder build(ChatSoundsConfig defaults, ChatSoundsConfig config, YetAnotherConfigLib.Builder builder) {
        Option<Float> chatSoundCooldownOption = Option.<Float>createBuilder()
                .name(Text.translatable("sounds.config.chatSoundCooldown.name"))
                .description(OptionDescription.of(Text.translatable("sounds.config.chatSoundCooldown.description")))
                .binding(defaults.chatSoundCooldown, () -> config.chatSoundCooldown, (value) -> config.chatSoundCooldown = value)
                .controller(opt -> FloatFieldControllerBuilder.create(opt).min(0.0f))
                .build();

        builder.title(Text.of("Chat Sounds"));
        builder.category(ConfigCategory.createBuilder()
                .name(Text.translatable("sounds.config.chat.messaging"))
                .option(Option.<Boolean>createBuilder()
                        .name(Text.translatable("sounds.config.useAtForChatMentions.name"))
                        .description(OptionDescription.of(Text.translatable("sounds.config.useAtForChatMentions.description")))
                        .binding(defaults.useAtForChatMentions, () -> config.useAtForChatMentions, (value) -> config.useAtForChatMentions = value)
                        .controller(opt -> BooleanControllerBuilder.create(opt).coloured(true).yesNoFormatter())
                        .build())
                .option(Option.<Boolean>createBuilder()
                        .name(Text.translatable("sounds.config.ignoreSystemChats.name"))
                        .description(OptionDescription.of(Text.translatable("sounds.config.ignoreSystemChats.description")))
                        .binding(defaults.ignoreSystemChats, () -> config.ignoreSystemChats, (value) -> config.ignoreSystemChats = value)
                        .controller(opt -> BooleanControllerBuilder.create(opt).coloured(true).yesNoFormatter())
                        .build())
                .group(config.typingSoundEffect.getOptionGroup(defaults.typingSoundEffect))
                .group(config.messageSoundEffect.getOptionGroup(defaults.messageSoundEffect))
                .group(config.mentionSoundEffect.getOptionGroup(defaults.mentionSoundEffect))
                .build());

        builder.category(ConfigCategory.createBuilder()
                .name(Text.translatable("sounds.config.chat.cooldown"))
                .option(Option.<Boolean>createBuilder()
                        .name(Text.translatable("sounds.config.enableChatSoundCooldown.name"))
                        .description(OptionDescription.of(Text.translatable("sounds.config.enableChatSoundCooldown.description")))
                        .binding(defaults.enableChatSoundCooldown, () -> config.enableChatSoundCooldown, (value) -> config.enableChatSoundCooldown = value)
                        .controller(opt -> BooleanControllerBuilder.create(opt).coloured(true).yesNoFormatter())
                        .listener((opt, val) -> chatSoundCooldownOption.setAvailable(val))
                        .build())
                .option(chatSoundCooldownOption)
                .build());

        return builder;
    }
}
