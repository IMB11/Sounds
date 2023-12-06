package dev.imb11.sounds.mixin.ui;

import dev.imb11.sounds.config.UISoundConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHud.class)
public class MentionSoundEffect {
    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V", at = @At("HEAD"), cancellable = false)
    public void $mention_recieve_sound_effect(Text message, MessageSignatureData signature, int ticks, MessageIndicator indicator, boolean refresh, CallbackInfo ci) {
        String messageString = message.getString();
        String username = client.getSession().getUsername();

        if (messageString.toLowerCase().contains("@" + username.toLowerCase()))
            UISoundConfig.get().mentionSoundEffect.playSound();
        else UISoundConfig.get().messageSoundEffect.playSound();
    }
}
