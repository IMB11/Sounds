package dev.imb11.sounds.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class IncompatabilityScreen extends Screen {
    public IncompatabilityScreen() {
        super(Text.translatable("sounds.incompatability.title"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        context.drawCenteredTextWithShadow(this.textRenderer, Text.translatable("sounds.incompatability.title"), this.width / 2, 20, 0xFFff000d);
        context.drawTextWrapped(this.textRenderer, Text.translatable("sounds.incompatability.message"), 10, 20 + (this.textRenderer.fontHeight * 2), this.width - 40, 0xFFFFFFFF);
    }

    @Override
    public void close() {}
}
