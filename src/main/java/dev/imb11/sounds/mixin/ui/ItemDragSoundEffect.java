package dev.imb11.sounds.mixin.ui;

import dev.imb11.sounds.config.old.UISoundConfig;
import dev.imb11.sounds.dynamic.DynamicSoundHelper;
import dev.imb11.sounds.sound.context.ItemStackSoundContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Set;

@Mixin(HandledScreen.class)
public class ItemDragSoundEffect<T extends ScreenHandler> {
    @Shadow
    @Final
    protected Set<Slot> cursorDragSlots;

    @Shadow @Final protected T handler;

    @Inject(method = "mouseDragged", at = @At(value = "INVOKE", target = "Ljava/util/Set;add(Ljava/lang/Object;)Z"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void $item_drag_sound_effect(double mouseX, double mouseY, int button, double deltaX, double deltaY, CallbackInfoReturnable<Boolean> cir, Slot slot) {
        if (!cursorDragSlots.contains(slot) && cursorDragSlots.size() > 0)
            UISoundConfig.get().itemDragSoundEffect.playDynamicSound(this.handler.getCursorStack(), ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.PLACE));
    }
}
