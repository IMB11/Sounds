package com.mineblock11.sonance.sound.context;

import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundEvent;
import org.jetbrains.annotations.Nullable;

public interface DynamicSoundContext<T> {
    SoundInstance handleContext(T context, SoundEvent fallback, float pitch, float volume);

    default PositionedSoundInstance createSoundInstance(SoundEvent event, float pitch, float volume) {
        return PositionedSoundInstance.master(event, pitch, volume);
    }
}
