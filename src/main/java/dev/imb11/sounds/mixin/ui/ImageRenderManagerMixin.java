package dev.imb11.sounds.mixin.ui;

import dev.isxander.yacl3.gui.image.ImageRendererManager;
import org.spongepowered.asm.mixin.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Pseudo
@Mixin(value = ImageRendererManager.class, remap = false)
public class ImageRenderManagerMixin {
    @Mutable
    @Shadow
    @Final
    private static ExecutorService SINGLE_THREAD_EXECUTOR;

    static {
        SINGLE_THREAD_EXECUTOR = Executors.newFixedThreadPool(5, task -> new Thread(task, "YACL Image Prep (" + task.hashCode() + ")"));
    }
}
