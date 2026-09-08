package dev.imb11.sounds.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.imb11.sounds.SoundsClient;
import dev.imb11.sounds.util.MixinStatics;
import net.minecraft.client.User;
import net.minecraft.client.resources.sounds.SoundEventRegistration;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Mixin(User.class)
public class UserMixin {
    @Inject(method = "<init>", at = @At("HEAD"))
	private static void $sounds_clear_found(String name, UUID uuid, String accessToken, Optional xuid, Optional clientId, CallbackInfo ci) {
        SoundsClient.username = name;
    }

}
