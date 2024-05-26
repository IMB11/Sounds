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
                .option(ConfigUtil.create(defaults.useAtForChatMentions, v -> config.useAtForChatMentions = v, () -> config.useAtForChatMentions, "sounds.config.useAtForChatMentions"))
                .option(ConfigUtil.create(defaults.ignoreSystemChats, v -> config.ignoreSystemChats = v, () -> config.ignoreSystemChats, "sounds.config.ignoreSystemChats"))
                .group(config.typingSoundEffect.getOptionGroup(defaults.typingSoundEffect))
                .group(config.messageSoundEffect.getOptionGroup(defaults.messageSoundEffect))
                .group(config.mentionSoundEffect.getOptionGroup(defaults.mentionSoundEffect))
                .build());

        builder.category(ConfigCategory.createBuilder()
                .name(Text.translatable("sounds.config.chat.cooldown"))
                .option(ConfigUtil.create(defaults.enableChatSoundCooldown, v -> config.enableChatSoundCooldown = v, () -> config.enableChatSoundCooldown, "sounds.config.enableChatSoundCooldown"))
                .option(chatSoundCooldownOption)
                .build());

        return builder;
    }
}
