//? if forge {
/*package dev.imb11.sounds.loaders.forge;

import dev.architectury.platform.forge.EventBuses;
import dev.imb11.mru.LoaderUtils;
import dev.imb11.sounds.SoundsClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("sounds")
public class SoundsForge {
    public SoundsForge() {
        EventBuses.registerModEventBus("sounds", FMLJavaModLoadingContext.get().getModEventBus());

        SoundsClient.init();

        ModLoadingContext.get().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> new NoConfigScreenWarning(parent))
        );
    }

    public static class NoConfigScreenWarning extends Screen {
        private final Screen parent;
        public NoConfigScreenWarning(Screen parent) {
            super(Text.empty());
            this.parent = parent;
        }

        @Override
        protected void init() {
            super.init();

            // "Go Back" button:
            var btn = ButtonWidget.builder(Text.of("Go Back"), button -> this.client.setScreen(parent)).dimensions(this.width / 2 - 100, this.height / 2, 200, 20).build();
            this.addDrawableChild(btn);

            // "Open Config Folder" button:
            var btn2 = ButtonWidget.builder(Text.of("Open Config Folder"), button -> Util.getOperatingSystem().open(LoaderUtils.getConfigFolder("sounds").toFile())).dimensions(this.width / 2 - 100, this.height / 2 + 24, 200, 20).build();
            this.addDrawableChild(btn2);
        }

        @Override
        public void renderBackground(DrawContext context) {
            this.renderBackgroundTexture(context);
        }

        @Override
        public void render(DrawContext context, int mouseX, int mouseY, float delta) {
            this.renderBackground(context);
            super.render(context, mouseX, mouseY, delta);

            String warning = "Sounds for Forge 1.20.1 is unable to display a configuration screen due to a limitation in Forge. Please use the configuration file instead. Sorry for the inconvenience!";
            context.drawTextWrapped(this.textRenderer, Text.literal(warning), this.width / 2 - 100, this.height / 2 - 50, 200, 0xFFFFFFFF);
        }
    }
}
*///?}
