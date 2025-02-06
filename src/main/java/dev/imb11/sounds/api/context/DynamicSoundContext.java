package dev.imb11.sounds.api.context;

import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;

public interface DynamicSoundContext<T> {
    SoundInstance handleContext(T context, SoundEvent fallback, float pitch, float volume);
    SoundInstance getExample(SoundEvent fallback, float pitch, float volume);

    default SimpleSoundInstance createSoundInstance(SoundEvent event, float pitch, float volume) {
        return SimpleSoundInstance.forUI(event, pitch, volume);
    }
}
