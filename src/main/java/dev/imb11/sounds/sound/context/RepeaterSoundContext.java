package dev.imb11.sounds.sound.context;

import dev.imb11.sounds.api.config.ConfiguredSimpleSoundInstance;
import dev.imb11.sounds.api.context.DynamicSoundContext;
import net.minecraft.resources.Identifier;

public class RepeaterSoundContext implements DynamicSoundContext<Integer> {
    public static RepeaterSoundContext of() {
        return new RepeaterSoundContext();
    }

    @Override
    public ConfiguredSimpleSoundInstance handleContext(Integer context, Identifier fallback, float pitch, float volume) {
        pitch = 0.2f + (context - 1) * 0.4f;
        return createSoundInstance(fallback, pitch, volume, true);
    }

    private static int num = 1;
}
