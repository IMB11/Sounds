package dev.imb11.sounds.mixin.ui;

import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.UISoundsConfig;
import dev.imb11.sounds.dynamic.DynamicSoundHelper;
import dev.imb11.sounds.sound.context.ItemStackSoundContext;
import dev.imb11.sounds.util.MixinStatics;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.item.ItemStack;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeInventoryScreen.CreativeScreenHandler.class)
public abstract class CreativeScreenHandlerMixin {
    @Unique
    private double prevTime = 0L;
    @Unique
    private float prevValue = -69420f;

    @Shadow
    public abstract ItemStack getCursorStack();

    @Inject(method = "setCursorStack", at = @At("HEAD"), cancellable = false)
    public void $item_delete_sound_effect(ItemStack stack, CallbackInfo ci) {
        if (MixinStatics.CURRENT_SLOT == MixinStatics.DELETE_ITEM_SLOT && !getCursorStack().isEmpty())
            SoundsConfig.get(UISoundsConfig.class).itemDeleteSoundEffect.playDynamicSound(getCursorStack(), ItemStackSoundContext.of(DynamicSoundHelper.BlockSoundType.HIT));
    }

    @Inject(method = "scrollItems", at = @At("TAIL"))
    public void $inventory_scroll_sound_effect(float position, CallbackInfo ci) {
        double currentTime = GLFW.glfwGetTime();
        double timeElapsed = currentTime - prevTime;

        if (timeElapsed >= 0.05 && prevValue != position) {
            SoundsConfig.get(UISoundsConfig.class).inventoryScrollSoundEffect.playSound();
            prevTime = currentTime;
            prevValue = position;
        }
    }
}
