package dev.imb11.sounds.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.imb11.sounds.util.MixinStatics;
import net.minecraft.client.resources.sounds.SoundEventRegistration;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(SoundManager.class)
public class SoundManagerMixin {
    @Inject(method = "prepare(Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)Lnet/minecraft/client/sounds/SoundManager$Preparations;", at = @At("HEAD"))
    public void $sounds_clear_found(ResourceManager resourceManager, ProfilerFiller profiler, CallbackInfoReturnable<?> cir) {
        MixinStatics.FOUND_SOUND_EVENTS.clear();
    }

    @Inject(
            method = "prepare(Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)Lnet/minecraft/client/sounds/SoundManager$Preparations;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sounds/SoundManager$Preparations;handleRegistration(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/client/resources/sounds/SoundEventRegistration;)V")
    )
    public void $sounds_capture_soundsjson(ResourceManager resourceManager, ProfilerFiller profiler, CallbackInfoReturnable<?> cir, @Local(ordinal = 0) String namespace, @Local Map.Entry<String, SoundEventRegistration> entry) {
        MixinStatics.FOUND_SOUND_EVENTS.add(ResourceLocation.fromNamespaceAndPath(namespace, entry.getKey()));
    }
}
