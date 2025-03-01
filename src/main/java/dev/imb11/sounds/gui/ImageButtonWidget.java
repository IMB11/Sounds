package dev.imb11.sounds.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

import static org.lwjgl.opengl.GL20.*;

import java.util.List;
import java.util.function.Consumer;

public class ImageButtonWidget extends AbstractWidget {
    float durationHovered = 0f;
    private final ResourceLocation imageLocation;
    private final Consumer<ImageButtonWidget> onPress;
    private static final int ICON_SIZE = 32; // Fixed icon size
    private static final int ICON_TEXT_SPACING = 5;

    public ImageButtonWidget(int x, int y, int width, int height, Component message, ResourceLocation imageLocation, Consumer<ImageButtonWidget> clickEvent) {
        super(x, y, width, height, message);
        this.imageLocation = imageLocation;
        this.onPress = clickEvent;
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        if (this.onPress != null) {
            this.onPress.accept(this);
        }
    }

    @Override
    protected void renderWidget(GuiGraphics context, int mouseX, int mouseY, float delta) {
        this.isHovered = mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height;

        if (this.isHovered || this.isFocused()) {
            durationHovered = Math.min(durationHovered + delta / 2f, 1f);
        } else {
            durationHovered = Math.max(durationHovered - delta / 4f, 0f);
        }

        float alphaScale = Mth.clampedLerp(0.9f, 0.5f, durationHovered);

        // Grey overlay for hover effect (render first, behind icon and text)
        int a = (int) (((0xFF000000) & 0xFF) * alphaScale);
        int greyColor = (a << 24) | (0);

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
                renderIcon(context, iconX, iconY);


                for (FormattedCharSequence line : wrappedText) {
                    context.drawCenteredString(client.font, line, getX() + this.width / 2, currentTextY, 0xFFFFFF);
                    currentTextY += fontHeight;
                }
            } else {
                int combinedTotalHeight = ICON_SIZE + ICON_TEXT_SPACING + fontHeight;
                int overallStartY = getY() + (this.height - combinedTotalHeight) / 2;
                iconY = overallStartY;
                iconX = getX() + (this.width - ICON_SIZE) / 2;
                textY = overallStartY + ICON_SIZE + ICON_TEXT_SPACING;
                renderIcon(context, iconX, iconY);
                context.drawCenteredString(client.font, getMessage(), getX() + this.width / 2, textY, 0xFFFFFF);
            }
        } else {
            // Horizontal Layout: Icon left of text
            int totalWidth = ICON_SIZE + ICON_TEXT_SPACING + textWidth;
            int startX = getX() + (this.width - totalWidth) / 2;

            iconX = startX;
            iconY = getY() + (this.height - ICON_SIZE) / 2;

            renderIcon(context, iconX, iconY);

            int textX = iconX + ICON_SIZE + ICON_TEXT_SPACING;
            int textY = getY() + (this.height - fontHeight) / 2;

            List<FormattedCharSequence> wrappedText = client.font.split(getMessage(), this.width - ICON_SIZE - ICON_TEXT_SPACING - 10);
            if (wrappedText.size() > 1) {
                int wrappedTextHeight = wrappedText.size() * fontHeight;
                int textStartY = getY() + (this.height - wrappedTextHeight) / 2;
                int currentTextY = textStartY;
                for (FormattedCharSequence line : wrappedText) {
                    context.drawString(client.font, line, textX, currentTextY, 0xFFFFFF);
                    currentTextY += fontHeight;
                }
            } else {
                context.drawString(client.font, getMessage(), textX, textY, 0xFFFFFF);
            }
        }

        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

        context.renderOutline(getX(), getY(), width, height, 0x0FFFFFFF);
    }

    private static void renderTexture(GuiGraphics drawContext, ResourceLocation texture, int x, int y, int textureWidth, int textureHeight) {
        //? 1.21 {
        /*drawContext.blit(texture, x, y, 0, 0, textureWidth, textureHeight, textureWidth, textureHeight);
         *///?} else {
        drawContext.blit(RenderType::guiTexturedOverlay, texture, x, y, 0, 0, textureWidth, textureHeight, textureWidth, textureHeight);
        //?}
    }


    private void renderIcon(GuiGraphics context, int x, int y) {
        int minFilterScalingTypePrev = glGetTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER);
        int magFilterScalingTypePrev = glGetTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER);
        try {
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            context.pose().pushPose();
            context.pose().scale(32f / 512f, 32f / 512f, 1f);
            //? 1.21 {
            /*context.blit(this.imageLocation, (int) (x / (32f / 512f)), (int) (y / (32f / 512f)), 0, 0, 512, 512, 512, 512);
             *///?} else {
            context.blit(RenderType::guiTexturedOverlay, this.imageLocation, (int) (x / (32f / 512f)), (int) (y / (32f / 512f)), 0, 0, 512, 512, 512, 512);
            //?}
            context.pose().popPose();
        } catch (Exception ignored) {} finally {
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, minFilterScalingTypePrev);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, magFilterScalingTypePrev);
        }
    }


    @Override
    protected void updateWidgetNarration(NarrationElementOutput builder) {
        builder.add(NarratedElementType.HINT, this.getMessage());
    }
}