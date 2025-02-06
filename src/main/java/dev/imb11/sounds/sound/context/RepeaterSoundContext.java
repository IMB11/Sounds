package dev.imb11.sounds.sound.context;

import dev.imb11.sounds.api.context.DynamicSoundContext;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;

public class RepeaterSoundContext implements DynamicSoundContext<Integer> {
    public static RepeaterSoundContext of() {
        return new RepeaterSoundContext();
    }

    @Override
    public SoundInstance handleContext(Integer context, SoundEvent fallback, float pitch, float volume) {
        pitch = 0.2f + (context - 1) * 0.4f;
        return createSoundInstance(fallback, pitch, volume);
    }

    private static int num = 1;

    @Override
    public SoundInstance getExample(SoundEvent fallback, float pitch, float volume) {
        num++;
        if(num > 4) num = 1;
        return handleContext(num, fallback, pitch, volume);
    }
}
