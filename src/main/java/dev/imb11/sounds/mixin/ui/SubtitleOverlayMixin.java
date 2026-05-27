package dev.imb11.sounds.mixin.ui;

import dev.imb11.sounds.api.config.ConfiguredSimpleSoundInstance;
import net.minecraft.client.gui.components.SubtitleOverlay;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.WeighedSoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SubtitleOverlay.class)
public abstract class SubtitleOverlayMixin {

    @Inject(method = "onPlaySound", at = @At(value = "HEAD"), cancellable = true)
    public void $preventSubtitleFromUiSounds(SoundInstance sound, WeighedSoundEvents soundEvent, float range, CallbackInfo ci) {
        if (sound instanceof ConfiguredSimpleSoundInstance configuredSimpleSoundInstance && configuredSimpleSoundInstance.shouldPreventSubtitle()) {
            ci.cancel();
        }
    }
}
