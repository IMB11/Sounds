package dev.imb11.sounds.config;

import dev.imb11.mru.LoaderUtils;
import dev.imb11.sounds.config.utils.ConfigGroup;
import dev.imb11.sounds.api.config.ConfiguredSound;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.FloatFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;

import static dev.imb11.sounds.config.SoundsConfig.HELPER;

public class ChatSoundsConfig extends ConfigGroup<ChatSoundsConfig> implements YetAnotherConfigLib.ConfigBackedBuilder<ChatSoundsConfig> {
    // == MESSAGING SOUNDS == //
    @SerialEntry
    public final ConfiguredSound typingSoundEffect = new ConfiguredSound("typing", SoundEvents.NOTE_BLOCK_HAT, true, 1.6f, 0.4f);
    @SerialEntry
    public final ConfiguredSound messageSoundEffect = new ConfiguredSound("message", SoundEvents.NOTE_BLOCK_HAT, true, 2.0f, 0.8f);
    @SerialEntry
    public final ConfiguredSound mentionSoundEffect = new ConfiguredSound("mention", SoundEvents.NOTE_BLOCK_CHIME, true, 1.8f, 0.9f);
    @SerialEntry
    public List<String> mentionKeywords = List.of(
            "@" + Minecraft.getInstance().getUser().getName()
    );
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
    public ResourceLocation getIcon() {
        return ResourceLocation.fromNamespaceAndPath("sounds", "textures/gui/chat_sounds.png");
    }

    @Override
    public Component getName() {
        return Component.translatable("sounds.config.chat");
    }

    @Override
    public String getID() {
        return "chat";
    }

    @Override
    public YetAnotherConfigLib.Builder build(ChatSoundsConfig defaults, ChatSoundsConfig config, YetAnotherConfigLib.Builder builder) {
        Option<Float> chatSoundCooldownOption = HELPER.getField("chatSoundCooldown", defaults.chatSoundCooldown, () -> config.chatSoundCooldown, v -> config.chatSoundCooldown = v);

        ListOption<String> mentionKeywordsOption = ListOption.<String>createBuilder()
                .name(Component.translatable("sounds.config.mentionKeywords.option"))
                .description(OptionDescription.of(Component.translatable("sounds.config.mentionKeywords.option.description")))
                .binding(defaults.mentionKeywords, () -> config.mentionKeywords, (value) -> config.mentionKeywords = value)
                .controller(StringControllerBuilder::create)
                .initial("@" + Minecraft.getInstance().getUser().getName())
                .insertEntriesAtEnd(true)
                .build();

        builder.title(Component.nullToEmpty("Chat Sounds"));
        builder.category(ConfigCategory.createBuilder()
                .name(Component.translatable("sounds.config.chat.messaging"))
                .option(HELPER.get("ignoreSystemChats", defaults.ignoreSystemChats, () -> config.ignoreSystemChats, v -> config.ignoreSystemChats = v))
                .group(config.typingSoundEffect.getOptionGroup(defaults.typingSoundEffect))
                .group(config.messageSoundEffect.getOptionGroup(defaults.messageSoundEffect))
                .group(config.mentionSoundEffect.getOptionGroup(defaults.mentionSoundEffect))
                .option(mentionKeywordsOption)
                .build());

        builder.category(ConfigCategory.createBuilder()
                .name(Component.translatable("sounds.config.chat.cooldown"))
                .option(Option.<Boolean>createBuilder()
                        .name(Component.translatable("sounds.config.option.enableChatSoundCooldown"))
                        .description(OptionDescription.of(Component.translatable("sounds.config.option.description.enableChatSoundCooldown")))
                        .binding(defaults.enableChatSoundCooldown, () -> config.enableChatSoundCooldown, v -> config.enableChatSoundCooldown = v)
                        .controller((opt) -> BooleanControllerBuilder.create(opt).coloured(true).yesNoFormatter())
                        .available(!(LoaderUtils.isModInstalled("chatpatches")))
                        .build())
                .option(chatSoundCooldownOption)
                .build());

        return builder;
    }
}
