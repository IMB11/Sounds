package dev.imb11.sounds.gui;

import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.utils.ConfigGroup;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;

public class SoundsConfigScreen extends Screen {
    private final Screen parent;

    public SoundsConfigScreen(@Nullable Screen parent) {
        super(Text.translatable("sounds.config.title"));
        this.parent = parent;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        context.drawCenteredTextWithShadow(this.client.textRenderer, Text.translatable("sounds.config.title"), this.width / 2, 10, 0xFFFFFF);
    }

    @Override
    public void close() {
        this.client.setScreen(this.parent);
    }

    @Override
    protected void init() {
        super.init();

        int fontHeight = this.client.textRenderer.fontHeight;
        DynamicGridWidget grid = new DynamicGridWidget(10, 10 + fontHeight + 10, width - 20, height - 20 - fontHeight - 10 - 20);

        ImageButtonWidget koFiWidget = new ImageButtonWidget(0, 0, 0, 0, Text.empty(), new Identifier("sounds", "textures/gui/kofi.webp"), btn -> {
            Util.getOperatingSystem().open("https://ko-fi.com/mineblock11");
        });

        grid.setPadding(3);
        grid.addChild(koFiWidget, 3, 1);

        for (ConfigGroup configGroup : SoundsConfig.getAll()) {
            grid.addChild(new ImageButtonWidget(0, 0, 0, 0, configGroup.getName(), configGroup.getImage(), btn -> {
                this.client.setScreen(configGroup.getYACL().generateScreen(this));
            }));
        }


        grid.calculateLayout();

        grid.forEachChild(this::addDrawableChild);

        ButtonWidget buttonWidget = ButtonWidget.builder(ScreenTexts.DONE, (btn) -> this.client.setScreen(this.parent)).dimensions(this.width / 2 - 100, this.height - 30, 200, 20).build();

        this.addDrawableChild(buttonWidget);
    }
}
