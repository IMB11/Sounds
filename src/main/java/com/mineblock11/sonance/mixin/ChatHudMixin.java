package com.mineblock11.sonance.mixin;

import com.mineblock11.sonance.config.SonanceConfig;
import net.minecraft.client.GuiMessageTag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MessageSignature;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatComponent.class)
public class ChatHudMixin {
    @Shadow
    @Final
    private Minecraft client;

    @Inject(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V", at = @At("HEAD"), cancellable = false)
    public void $mention_recieve_sound_effect(Component message, MessageSignature signature, int ticks, GuiMessageTag indicator, boolean refresh, CallbackInfo ci) {
        String messageString = message.getString();
        String username = client.getUser().getName();

        messageString = messageString.replace("<" + username + ">", "");

        if (messageString.toLowerCase().contains(username.toLowerCase()))
            SonanceConfig.get().mentionSoundEffect.playSound();
        else SonanceConfig.get().messageSoundEffect.playSound();
    }
}
