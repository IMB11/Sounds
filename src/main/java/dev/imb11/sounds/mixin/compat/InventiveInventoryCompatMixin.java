package dev.imb11.sounds.mixin.compat;

import dev.imb11.sounds.util.MixinStatics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("UnresolvedMixinReference")
@Pseudo
@Mixin(targets = "net.strobel.inventive_inventory.features.sorting.SortingHandler", remap = false)
public class InventiveInventoryCompatMixin {
    @Inject(method = "sort", at = @At("HEAD"), remap = false)
    private static void temporarilyDisableInventorySounds(CallbackInfo ci) {
        MixinStatics.temporarilyDisableInventorySounds = true;
    }

    @Inject(method = "sort", at = @At("RETURN"), remap = false)
    private static void reenableInventorySounds(CallbackInfo ci) {
        MixinStatics.temporarilyDisableInventorySounds = false;
    }
}
