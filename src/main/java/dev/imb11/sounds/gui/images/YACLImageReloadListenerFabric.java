package dev.imb11.sounds.gui.images;

import dev.isxander.yacl3.gui.image.ImageRendererManager;
import dev.isxander.yacl3.gui.image.impl.AnimatedDynamicTextureImage;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class YACLImageReloadListenerFabric implements IdentifiableResourceReloadListener {
    @Override
    public Identifier getFabricId() {
        return new Identifier("sounds", "image_reload_listener");
    }

    @Override
    public CompletableFuture<Void> reload(Synchronizer synchronizer, ResourceManager resourceManager, Profiler prepareProfiler, Profiler applyProfiler, Executor prepareExecutor, Executor applyExecutor) {
        Map<Identifier, Resource> imageResources = resourceManager.findResources(
                "textures",
                location -> location.getPath().endsWith(".webp")
        );

        synchronizer.whenPrepared(null);

        for (Identifier location : imageResources.keySet()) {
            ImageRendererManager.registerImage(location, AnimatedDynamicTextureImage.createWEBPFromTexture(location));
        }

        return CompletableFuture.allOf();
    }
}