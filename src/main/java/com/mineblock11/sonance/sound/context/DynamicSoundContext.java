package com.mineblock11.sonance.sound.context;

import net.minecraft.sound.SoundEvent;
import org.jetbrains.annotations.Nullable;

public interface DynamicSoundContext<T> {
    @Nullable SoundEvent handleContext(T context);
}
