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
public class ChatComponentMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;ILnet/minecraft/client/GuiMessageTag;Z)V", at = @At("HEAD"), cancellable = false)
    public void $mention_recieve_sound_effect(Component message, MessageSignature signature, int ticks, GuiMessageTag indicator, boolean refresh, CallbackInfo ci) {
        String messageString = message.getString();
        String username = minecraft.getUser().getName();

        messageString = messageString.replace("<" + username + ">", "");

        if (messageString.toLowerCase().contains(username.toLowerCase()))
            SonanceConfig.get().mentionSoundEffect.playSound();
        else SonanceConfig.get().messageSoundEffect.playSound();
    }
}
