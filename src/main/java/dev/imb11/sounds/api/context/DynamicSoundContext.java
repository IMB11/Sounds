package dev.imb11.sounds.api.context;

import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundEvent;

public interface DynamicSoundContext<T> {
    SoundInstance handleContext(T context, SoundEvent fallback, float pitch, float volume);
    SoundInstance getExample(SoundEvent fallback, float pitch, float volume);

    default PositionedSoundInstance createSoundInstance(SoundEvent event, float pitch, float volume) {
        return PositionedSoundInstance.master(event, pitch, volume);
    }
}
