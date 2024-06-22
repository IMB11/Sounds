package dev.imb11.sounds.mixin.compat;

import de.rubixdev.inventorio.player.InventorioScreenHandler;
import dev.imb11.sounds.MixinStatics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(value = InventorioScreenHandler.class, remap = false)
public class InventorioCompatMixin {
    @Inject(method = "updateDeepPocketsCapacity", at = @At("HEAD"), remap = false)
    public void $update_deep_pockets_capacity(CallbackInfo ci) {
        MixinStatics.hasOpenedInventorioScreen = true;
    }
}
