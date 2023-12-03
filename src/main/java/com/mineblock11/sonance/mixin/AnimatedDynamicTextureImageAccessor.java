package com.mineblock11.sonance.mixin;

import dev.isxander.yacl3.gui.image.impl.AnimatedDynamicTextureImage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AnimatedDynamicTextureImage.class)
public interface AnimatedDynamicTextureImageAccessor {
    @Accessor("frameHeight")
    int getFrameHeight();

    @Accessor("frameWidth")
    int getFrameWidth();
}
