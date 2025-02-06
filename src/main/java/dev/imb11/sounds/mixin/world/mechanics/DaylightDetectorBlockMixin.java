package dev.imb11.sounds.mixin.world.mechanics;

import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.WorldSoundsConfig;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.DaylightDetectorBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DaylightDetectorBlock.class)
public class DaylightDetectorBlockMixin {
    @Inject(method = "useWithoutItem", at = @At(value = "RETURN", ordinal = 0))
    public void $daylight_detector_use_sound_effect(CallbackInfoReturnable<InteractionResult> cir) {
        SoundsConfig.get(WorldSoundsConfig.class).daylightDetectorUseSoundEffect.playSound();
    }
}
