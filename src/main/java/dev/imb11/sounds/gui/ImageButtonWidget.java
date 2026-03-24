package dev.imb11.sounds.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

import java.util.List;
import java.util.function.Consumer;

public class ImageButtonWidget extends AbstractWidget {
    float durationHovered = 0f;
    private final Identifier imageLocation;
    private final Consumer<ImageButtonWidget> onPress;
    private static final int ICON_SIZE = 32; // Fixed icon size
    private static final int ICON_TEXT_SPACING = 5;

    public ImageButtonWidget(int x, int y, int width, int height, Component message, Identifier imageLocation, Consumer<ImageButtonWidget> clickEvent) {
        super(x, y, width, height, message);
        this.imageLocation = imageLocation;
        this.onPress = clickEvent;
    }

    @Override
    public void onClick(MouseButtonEvent event, boolean bl) {
        if (this.onPress != null) {
            this.onPress.accept(this);
        }
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        this.isHovered = mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height;

        if (this.isHovered || this.isFocused()) {
            durationHovered = Math.min(durationHovered + delta / 2f, 1f);
        } else {
            durationHovered = Math.max(durationHovered - delta / 4f, 0f);
        }

        float alphaScale = Mth.clampedLerp(0.3f, 0.75f, durationHovered);

        // Grey overlay for hover effect (render first, behind icon and text)
        int a = (int) (255 * alphaScale);
        int greyColor = (a << 24);

        context.fill(getX(), getY(), getX() + width, getY() + height, greyColor);

        // Prepare for icon and text rendering
        Minecraft client = Minecraft.getInstance();
        int fontHeight = client.font.lineHeight;
        int textWidth = client.font.width(getMessage());

        // Determine layout: vertical or horizontal
        int totalHorizontalWidth = ICON_SIZE + ICON_TEXT_SPACING + textWidth;
        boolean preferHorizontal = totalHorizontalWidth <= this.width && this.width >= 2f * this.height;
        boolean verticalLayout = !preferHorizontal;

        int iconX, iconY; // Declare icon position variables here

        if (verticalLayout) {
            // Vertical Layout: Icon above text
            int totalHeight = ICON_SIZE + ICON_TEXT_SPACING + fontHeight;

            iconY = getY() + (this.height - totalHeight) / 2;

            List<FormattedCharSequence> wrappedText = client.font.split(getMessage(), this.width - 20);
            int wrappedTextHeight = wrappedText.size() * fontHeight;
            int textY = iconY + ICON_SIZE + ICON_TEXT_SPACING;

            if (wrappedText.size() > 1) {
                int textStartY = textY + (fontHeight - wrappedTextHeight) / 2;
                int currentTextY = textY;

                int combinedTotalHeight = ICON_SIZE + ICON_TEXT_SPACING + wrappedTextHeight;
                int overallStartY = getY() + (this.height - combinedTotalHeight) / 2;
                iconY = overallStartY;
                iconX = getX() + (this.width - ICON_SIZE) / 2;
                textY = overallStartY + ICON_SIZE + ICON_TEXT_SPACING;
                currentTextY = textY;
                // Re-render icon with recalculated position if needed, though in this case, position hasn't changed significantly in terms of icon rendering itself.
                extractIcon(context, iconX, iconY);


                for (FormattedCharSequence line : wrappedText) {
                    context.centeredText(client.font, line, getX() + this.width / 2, currentTextY, 0xFFFFFFFF);
                    currentTextY += fontHeight;
                }
            } else {
                int combinedTotalHeight = ICON_SIZE + ICON_TEXT_SPACING + fontHeight;
                int overallStartY = getY() + (this.height - combinedTotalHeight) / 2;
                iconY = overallStartY;
                iconX = getX() + (this.width - ICON_SIZE) / 2;
                textY = overallStartY + ICON_SIZE + ICON_TEXT_SPACING;
                extractIcon(context, iconX, iconY);
                context.centeredText(client.font, getMessage(), getX() + this.width / 2, textY, 0xFFFFFFFF);
            }
        } else {
            // Horizontal Layout: Icon left of text
            int totalWidth = ICON_SIZE + ICON_TEXT_SPACING + textWidth;
            int startX = getX() + (this.width - totalWidth) / 2;

            iconX = startX;
            iconY = getY() + (this.height - ICON_SIZE) / 2;

            extractIcon(context, iconX, iconY);

            int textX = iconX + ICON_SIZE + ICON_TEXT_SPACING;
            int textY = getY() + (this.height - fontHeight) / 2;

            List<FormattedCharSequence> wrappedText = client.font.split(getMessage(), this.width - ICON_SIZE - ICON_TEXT_SPACING - 10);
            if (wrappedText.size() > 1) {
                int wrappedTextHeight = wrappedText.size() * fontHeight;
                int textStartY = getY() + (this.height - wrappedTextHeight) / 2;
                int currentTextY = textStartY;
                for (FormattedCharSequence line : wrappedText) {
                    context.text(client.font, line, textX, currentTextY, 0xFFFFFFFF);
                    currentTextY += fontHeight;
                }
            } else {
                context.text(client.font, getMessage(), textX, textY, 0xFFFFFFFF);
            }
        }

        context.outline(getX(), getY(), width, height, 0x1FFFFFFF);
    }

    private static void extractTexture(GuiGraphicsExtractor drawContext, Identifier texture, int x, int y, int textureWidth, int textureHeight) {
        drawContext.blit(RenderPipelines.GUI_TEXTURED, texture, x, y, 0, 0, textureWidth, textureHeight, textureWidth, textureHeight);
    }


    private void extractIcon(GuiGraphicsExtractor context, int x, int y) {
        context.pose().pushMatrix();

        context.pose().scale(32f / 512f, 32f / 512f);
        context.blit(RenderPipelines.GUI_TEXTURED, this.imageLocation, (int) (x / (32f / 512f)), (int) (y / (32f / 512f)), 0, 0, 512, 512, 512, 512);
        context.pose().popMatrix();
    }


    @Override
    protected void updateWidgetNarration(NarrationElementOutput builder) {
        builder.add(NarratedElementType.HINT, this.getMessage());
    }
}