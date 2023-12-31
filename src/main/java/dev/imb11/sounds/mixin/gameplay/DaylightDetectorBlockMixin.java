package dev.imb11.sounds.mixin.gameplay;

import dev.imb11.sounds.config.GameplaySoundConfig;
import net.minecraft.block.DaylightDetectorBlock;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DaylightDetectorBlock.class)
public class DaylightDetectorBlockMixin {
    @Inject(method = "onUse", at = @At(value = "RETURN", ordinal = 0))
    public void $daylight_detector_use_sound_effect(CallbackInfoReturnable<ActionResult> cir) {
        GameplaySoundConfig.get().daylightDetectorUseSoundEffect.playSound();
    }
}
