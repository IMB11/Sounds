package com.mineblock11.sonance.sound.context;

import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import org.jetbrains.annotations.Nullable;

public class RepeaterSoundContext implements DynamicSoundContext<Integer> {
    @Override
    public SoundInstance handleContext(Integer context, SoundEvent fallback, float pitch, float volume) {
        pitch = 0.2f + (context - 1) * 0.4f;
        return createSoundInstance(fallback, pitch, volume);
    }

    public static RepeaterSoundContext of() {
        return new RepeaterSoundContext();
    }
}
