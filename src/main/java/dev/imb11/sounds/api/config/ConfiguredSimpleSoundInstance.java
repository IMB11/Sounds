package dev.imb11.sounds.api.config;

import dev.imb11.sounds.config.ModConfig;
import dev.imb11.sounds.config.SoundsConfig;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

public class ConfiguredSimpleSoundInstance extends SimpleSoundInstance {

    private final boolean showSubtitle;

    public ConfiguredSimpleSoundInstance(
            final Identifier location,
            final SoundSource source,
            final float volume,
            final float pitch,
            final RandomSource random,
            final boolean looping,
            final int delay,
            final Attenuation attenuation,
            final double x,
            final double y,
            final double z,
            final boolean relative,
            boolean subtitle) {
        super(location, source, volume*SoundsConfig.get(ModConfig.class).globalVolume, pitch, random, looping, delay, attenuation, x, y, z, relative);
        this.showSubtitle = subtitle;
    }

    public static ConfiguredSimpleSoundInstance forUI(final SoundEvent sound, final float pitch, final float volume) {
        return new ConfiguredSimpleSoundInstance(
                sound.location(), SoundSource.UI, volume, pitch, SoundInstance.createUnseededRandom(), false, 0, SoundInstance.Attenuation.NONE, 0.0, 0.0, 0.0, true,
                false);
    }

    public boolean shouldPreventSubtitle() {
        return !showSubtitle;
    }
}
