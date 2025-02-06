package dev.imb11.sounds.util;

import java.util.Iterator;
import java.util.Objects;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;

public class RenderUtils {
    public static void drawTextWrapped(GuiGraphics context, Font textRenderer, FormattedText text, int x, int y, int width, int color) {
        for(Iterator var7 = textRenderer.split(text, width).iterator(); var7.hasNext(); y += 9) {
            FormattedCharSequence orderedText = (FormattedCharSequence)var7.next();
            context.drawString(textRenderer, orderedText, x, y, color, false);
            Objects.requireNonNull(textRenderer);
        }

    }
}
