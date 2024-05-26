package dev.imb11.sounds.mixin.ui;

import dev.imb11.sounds.config.ChatSoundsConfig;
import dev.imb11.sounds.config.SoundsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHud.class)
public class MentionSoundEffect {
    @Shadow
    @Final
    private MinecraftClient client;

    @Unique
    private float cooldownPeriod = 0f;

    @Inject(method = "render", at = @At("HEAD"))
    /*? if <1.20.5 {*//*
    public void $cooldown_period(DrawContext context, int currentTick, int mouseX, int mouseY, CallbackInfo ci) {
    *//*? } else {*/
    public void $cooldown_period(DrawContext context, int currentTick, int mouseX, int mouseY, boolean focused, CallbackInfo ci) {
    /*? }*/
        if (cooldownPeriod > 0) {
            cooldownPeriod -= this.client.getTickDelta() / 2f;
        }
    }
    /*? if <1.20.5 {*//*
    @Inject(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V", at = @At("HEAD"), cancellable = false)
    public void $mention_recieve_sound_effect(Text message, MessageSignatureData signature, int ticks, MessageIndicator indicator, boolean refresh, CallbackInfo ci) {
    *//*? } else {*/
    @Inject(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V", at = @At("HEAD"), cancellable = false)
    public void $mention_recieve_sound_effect(Text message, MessageSignatureData signatureData, MessageIndicator indicator, CallbackInfo ci) {
    /*? }*/
        if (cooldownPeriod > 0 && SoundsConfig.get(ChatSoundsConfig.class).enableChatSoundCooldown) {
            return;
        }

        cooldownPeriod = SoundsConfig.get(ChatSoundsConfig.class).chatSoundCooldown * 20f;

        String messageString = message.getString();
        String username = client.getSession().getUsername();

        boolean isMention = messageString.toLowerCase().contains(username.toLowerCase());

        if (SoundsConfig.get(ChatSoundsConfig.class).useAtForChatMentions) {
            isMention = messageString.toLowerCase().contains("@" + username.toLowerCase());
        }

        if (SoundsConfig.get(ChatSoundsConfig.class).ignoreSystemChats) {
            /*? >1.20.1 {*/
            if (indicator == MessageIndicator.system() || indicator == MessageIndicator.chatError()) {
                /*?} else {*//*
            if(indicator == MessageIndicator.system()) {
            *//*?}*/
                return;
            }
        }

        if (isMention)
            SoundsConfig.get(ChatSoundsConfig.class).mentionSoundEffect.playSound();
        else SoundsConfig.get(ChatSoundsConfig.class).messageSoundEffect.playSound();
    }
}
