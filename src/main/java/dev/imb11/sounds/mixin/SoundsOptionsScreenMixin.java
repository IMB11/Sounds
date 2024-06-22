package dev.imb11.sounds.mixin;

import dev.imb11.sounds.gui.SoundsConfigScreen;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.SoundOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundOptionsScreen.class)
public class SoundsOptionsScreenMixin extends Screen {
    protected SoundsOptionsScreenMixin(Text title) {
        super(title);
    }

    /*? if <1.21 {*/
    /*@Inject(method = "init", at = @At("TAIL"), cancellable = false)
    *//*?} else {*/
    @Inject(method = "addOptions", at = @At("TAIL"), cancellable = false)
    /*?}*/
    public void $add_sounds_button(CallbackInfo ci) {
        assert this.client != null;
        int textWidth = this.client.textRenderer.getWidth("Sounds");
        this.addDrawableChild(
                ButtonWidget
                        .builder(Text.translatable("sounds.config.static"), (btn) -> this.client.setScreen(new SoundsConfigScreen(this)))
                        .dimensions(this.width - textWidth - 20, 5, textWidth + 10, 20)
                        .build()
        );
    }
}
