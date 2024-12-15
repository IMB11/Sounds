package dev.imb11.sounds.gui;

import dev.imb11.sounds.util.RenderUtils;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;

import java.util.Iterator;
import java.util.Objects;

public class IncompatabilityScreen extends Screen {
    public IncompatabilityScreen() {
        super(Text.translatable("sounds.incompatability.title"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        context.drawCenteredTextWithShadow(this.textRenderer, Text.translatable("sounds.incompatability.title"), this.width / 2, 20, 0xFFff000d);
        RenderUtils.drawTextWrapped(context, this.textRenderer, Text.translatable("sounds.incompatability.message"), 10, 20 + (this.textRenderer.fontHeight * 2), this.width - 40, 0xFFFFFFFF);
    }

    @Override
    public void close() {}
}
