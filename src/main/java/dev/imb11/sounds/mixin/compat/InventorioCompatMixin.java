package dev.imb11.sounds.mixin.compat;
import dev.imb11.sounds.util.MixinStatics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "de.rubixdev.inventorio.player.InventorioScreenHandler", remap = false)
public class InventorioCompatMixin {
    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "updateDeepPocketsCapacity", at = @At("HEAD"), remap = false)
    public void $update_deep_pockets_capacity(CallbackInfo ci) {
        MixinStatics.hasOpenedInventorioScreen = true;
    }
}
