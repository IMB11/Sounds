package dev.imb11.sounds.mixin;

import dev.imb11.mru.LoaderUtils;
import dev.imb11.sounds.gui.IncompatabilityScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;

@Mixin(TitleScreen.class)
public abstract class TitleScreenWidget extends Screen {
    protected TitleScreenWidget(Component title) {
        super(title);
    }

    @Inject(method = "init", at = @At("HEAD"))
    public void init(CallbackInfo ci) {

        if(LoaderUtils.isModInstalled("qualitysounds")) {
            this.minecraft.setScreen(new IncompatabilityScreen());
        }
    }
}
