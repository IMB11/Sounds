package dev.imb11.sounds.api.context;

import dev.imb11.mru.LoaderUtils;
import dev.imb11.sounds.SoundsClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;

public interface DynamicSoundContext<T> {
    SoundInstance handleContext(T context, ResourceLocation fallback, float pitch, float volume);

    default SimpleSoundInstance createSoundInstance(ResourceLocation event, float pitch, float volume) {
        var pos = Vec3.ZERO;
        if (LoaderUtils.isModInstalled("sound_physics_perfected"))
            return new SimpleSoundInstance(event, SoundSource.MASTER, volume, pitch, SoundsClient.RANDOM, false, 0, SoundInstance.Attenuation.NONE, pos.x, pos.y, pos.z, true); // Disable Attenuation when using SPP

        return new SimpleSoundInstance(event, SoundSource.MASTER, volume, pitch, SoundsClient.RANDOM, false, 0, SoundInstance.Attenuation.LINEAR, pos.x, pos.y, pos.z, true);
    }
}
