package dev.imb11.sounds.mixin.ui;

import dev.isxander.yacl3.gui.image.ImageRendererManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Mixin(ImageRendererManager.class)
public class ImageRenderManagerMixin {
    @Mutable
    @Shadow @Final private static ExecutorService SINGLE_THREAD_EXECUTOR;

    static {
       SINGLE_THREAD_EXECUTOR = Executors.newFixedThreadPool(10, task -> new Thread(task, "YACL Image Prep (" + task.hashCode() + ")"));
    }
}
