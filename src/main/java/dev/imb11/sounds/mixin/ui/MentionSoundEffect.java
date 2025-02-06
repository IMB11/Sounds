package dev.imb11.sounds.mixin.ui;

import dev.imb11.mru.LoaderUtils;
import dev.imb11.sounds.config.ChatSoundsConfig;
import dev.imb11.sounds.config.SoundsConfig;
import net.minecraft.client.GuiMessageTag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MessageSignature;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatComponent.class)
public class MentionSoundEffect {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Unique
    private float cooldownPeriod = 0f;

    @Inject(method = "render", at = @At("HEAD"))
    public void $cooldown_period(GuiGraphics context, int currentTick, int mouseX, int mouseY, boolean focused, CallbackInfo ci) {
        if (cooldownPeriod > 0) {
            cooldownPeriod -= this.minecraft.getDeltaTracker().getGameTimeDeltaPartialTick(true);
        }
    }

    @Inject(method = "addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/GuiMessageTag;)V", at = @At("HEAD"), cancellable = false)
    public void $mention_recieve_sound_effect(Component message, MessageSignature signatureData, GuiMessageTag indicator, CallbackInfo ci) {
        if (cooldownPeriod > 0 && (SoundsConfig.get(ChatSoundsConfig.class).enableChatSoundCooldown || LoaderUtils.isModInstalled("chatpatches"))) {
            return;
        }

        cooldownPeriod = (LoaderUtils.isModInstalled("chatpatches") ? 0.01f : SoundsConfig.get(ChatSoundsConfig.class).chatSoundCooldown) * 20f;

        String messageString = message.getString();

        // Generate regex from mentionKeywords
        StringBuilder regex = new StringBuilder();
        regex.append("(?i).*(");
        for (String keyword : SoundsConfig.get(ChatSoundsConfig.class).mentionKeywords) {
            regex.append(keyword).append("|");
        }
        regex.deleteCharAt(regex.length() - 1);
        regex.append(").*");

        boolean isMention = messageString.matches(regex.toString());

        if (SoundsConfig.get(ChatSoundsConfig.class).ignoreSystemChats) {
            if (indicator == GuiMessageTag.system() || indicator == GuiMessageTag.chatError()) {
                return;
            }
        }

        if (isMention)
            SoundsConfig.get(ChatSoundsConfig.class).mentionSoundEffect.playSound();
        else SoundsConfig.get(ChatSoundsConfig.class).messageSoundEffect.playSound();
    }
}
