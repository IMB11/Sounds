//package dev.imb11.sounds.mixin;
//
//import dev.imb11.sounds.gui.TestScreen;
//import net.minecraft.client.gui.screen.Screen;
//import net.minecraft.client.gui.screen.TitleScreen;
//import net.minecraft.client.gui.widget.ButtonWidget;
//import net.minecraft.text.Text;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//@Mixin(TitleScreen.class)
//public abstract class TitleScreenWidget extends Screen {
//    protected TitleScreenWidget(Text title) {
//        super(title);
//    }
//
//    @Inject(method = "init", at = @At("HEAD"))
//    public void init(CallbackInfo ci) {
//        this.addDrawableChild(ButtonWidget.builder(Text.of("test"), (button -> {
//            assert this.client != null;
//            this.client.setScreen(new TestScreen());
//        })).dimensions(10, 10, 100, 20).build());
//    }
//}
