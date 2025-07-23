package dev.imb11.sounds.mixin;

import dev.imb11.sounds.config.ModConfig;
import dev.imb11.sounds.config.SoundsConfig;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.SoundOptionsScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundOptionsScreen.class)
public class SoundsOptionsScreenMixin extends Screen {
    protected SoundsOptionsScreenMixin(Component title) {
        super(title);
    }

    /*? if <1.21 {*/
    /*@Inject(method = "init", at = @At("TAIL"), cancellable = false)
    *//*?} else {*/
    @Inject(method = "addOptions", at = @At("TAIL"), cancellable = false)
    /*?}*/
    public void $add_sounds_button(CallbackInfo ci) {
        assert this.minecraft != null;

        if (SoundsConfig.get(ModConfig.class).hideSoundsButtonInSoundMenu) return;

        int textWidth = this.minecraft.font.width("Sounds");
        this.addRenderableWidget(
                Button
                        .builder(Component.translatable("sounds.config.static"), (btn) -> {
                            this.minecraft.setScreen(new dev.imb11.sounds.gui.SoundsConfigScreen(this));
                        })
                        .bounds(this.width - textWidth - 20, 5, textWidth + 10, 20)
                        .build()
        );
    }
}