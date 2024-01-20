package dev.imb11.sounds.mixin.accessors;

import dev.isxander.yacl3.gui.image.impl.AnimatedDynamicTextureImage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = AnimatedDynamicTextureImage.class, remap = false)
public interface AnimatedDynamicTextureImageAccessor {
    @Accessor("frameHeight")
    int getFrameHeight();

    @Accessor("frameWidth")
    int getFrameWidth();
}
