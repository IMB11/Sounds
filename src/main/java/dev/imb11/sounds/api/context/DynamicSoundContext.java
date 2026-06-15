package dev.imb11.sounds.api.context;

import dev.imb11.mru.LoaderUtils;
import dev.imb11.sounds.SoundsClient;
import dev.imb11.sounds.api.config.ConfiguredSimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;

public interface DynamicSoundContext<T> {
    ConfiguredSimpleSoundInstance handleContext(T context, Identifier fallback, float pitch, float volume);

    default ConfiguredSimpleSoundInstance createSoundInstance(Identifier event, float pitch, float volume, boolean captions) {
        var pos = Vec3.ZERO;
        var attenuation = SoundInstance.Attenuation.LINEAR;
        if (LoaderUtils.isModInstalled("sound_physics_perfected"))
            attenuation = SoundInstance.Attenuation.NONE;  // Disable Attenuation when using SPP

        return new ConfiguredSimpleSoundInstance(event, SoundSource.UI, volume, pitch, SoundsClient.RANDOM, false, 0, attenuation, pos.x, pos.y, pos.z, true, captions);
    }
}
