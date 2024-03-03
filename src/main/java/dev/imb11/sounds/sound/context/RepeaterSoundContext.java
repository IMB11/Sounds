package dev.imb11.sounds.sound.context;

import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundEvent;

public class RepeaterSoundContext implements DynamicSoundContext<Integer> {
    public static RepeaterSoundContext of() {
        return new RepeaterSoundContext();
    }

    @Override
    public SoundInstance handleContext(Integer context, SoundEvent fallback, float pitch, float volume) {
        pitch = 0.2f + (context - 1) * 0.4f;
        return createSoundInstance(fallback, pitch, volume);
    }
}
