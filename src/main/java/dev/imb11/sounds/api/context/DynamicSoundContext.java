package dev.imb11.sounds.api.context;

import dev.imb11.sounds.SoundsClient;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public interface DynamicSoundContext<T> {
    SoundInstance handleContext(T context, ResourceLocation fallback, float pitch, float volume);

    default SimpleSoundInstance createSoundInstance(ResourceLocation event, float pitch, float volume) {
        return new SimpleSoundInstance(event, SoundSource.MASTER, volume, pitch, SoundsClient.RANDOM, false, 0, SoundInstance.Attenuation.LINEAR, 0, 0, 0, true);
    }
}
