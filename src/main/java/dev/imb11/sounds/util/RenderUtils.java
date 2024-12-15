package dev.imb11.sounds.util;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;

import java.util.Iterator;
import java.util.Objects;

public class RenderUtils {
    public static void drawTextWrapped(DrawContext context, TextRenderer textRenderer, StringVisitable text, int x, int y, int width, int color) {
        for(Iterator var7 = textRenderer.wrapLines(text, width).iterator(); var7.hasNext(); y += 9) {
            OrderedText orderedText = (OrderedText)var7.next();
            context.drawText(textRenderer, orderedText, x, y, color, false);
            Objects.requireNonNull(textRenderer);
        }

    }
}
