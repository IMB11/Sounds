package dev.imb11.sounds.mixin.compat;

import dev.emi.emi.api.stack.EmiStackInteraction;
import dev.emi.emi.input.EmiBind;
import dev.emi.emi.screen.EmiScreenManager;
import dev.imb11.sounds.config.old.CompatConfig;
import dev.imb11.sounds.dynamic.DynamicSoundHelper;
import dev.imb11.sounds.sound.context.ItemStackSoundContext;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;

@Pseudo
@Mixin(value = EmiScreenManager.class, remap = false)
public abstract class EmiSoundEffects {
    @Inject(method = "stackInteraction", at = @At("RETURN"), remap = false)
    private static void $item_picked_up(EmiStackInteraction stack, Function<EmiBind, Boolean> function, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue()) return;
        ItemStack item = stack.getStack().getEmiStacks().get(0).getItemStack();
        CompatConfig.get().emi_click_item.playDynamicSound(item, ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.PLACE));
    }
}
