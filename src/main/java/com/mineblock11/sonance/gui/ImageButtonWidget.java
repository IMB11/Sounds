package com.mineblock11.sonance.gui;

import com.mineblock11.sonance.mixin.AnimatedDynamicTextureImageAccessor;
import dev.isxander.yacl3.gui.image.ImageRendererManager;
import dev.isxander.yacl3.gui.image.impl.AnimatedDynamicTextureImage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import static org.lwjgl.opengl.GL20.*;

public class ImageButtonWidget extends ClickableWidget {
    private CompletableFuture<AnimatedDynamicTextureImage> image;
    private Consumer<ClickableWidget> onPress;

    public ImageButtonWidget(int x, int y, int width, int height, Text message, Identifier image) {
        super(x, y, width, height, message);
        this.image = ImageRendererManager.registerImage(image, AnimatedDynamicTextureImage.createWEBPFromTexture(image));
    }

    public void setClickEvent(Consumer<ClickableWidget> clickEvent) {
        this.onPress = clickEvent;
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        if(this.onPress != null) {
            this.onPress.accept(this);
        }
    }

    float durationHovered = 1f;

    @Override
    protected void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        context.enableScissor(getX(), getY(), getX() + width, getY() + height);
        this.hovered = mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height;

        if(this.hovered) {
            durationHovered += delta / 2f;
        } else {
            if(durationHovered < 0) {
                durationHovered = 0;
            } else {
                durationHovered -= durationHovered / 4f;
            }
        }

        // Ease in out lerp.
        float alphaScale = MathHelper.clampedLerp(0.9f, 0.2f, MathHelper.clamp(durationHovered - 1f, 0.0f, 1.0f));

        if(image.isDone()) {
            try {
                var contentImage = image.get();
                if(contentImage != null) {

                    // Scale the image so that the image height is the same as the button height.
                    float neededWidth = ((AnimatedDynamicTextureImageAccessor) contentImage).getFrameWidth() * ((float) this.height / ((AnimatedDynamicTextureImageAccessor) contentImage).getFrameHeight());

                    // Scale the image to fit within the width and height of the button.
                    context.getMatrices().push();
                    // gl bilinear scaling.
                    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
                    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
                    contentImage.render(context, getX(), getY(), (int) Math.max(neededWidth, this.width), delta);
                    context.getMatrices().pop();
                }
            } catch (InterruptedException | ExecutionException ignored) {
            }
        }

//        context.drawTexture(image, getX(), getY(), this.width, this.height, 0, 0, 1920, 1080, 1920, 1080);

        int greyColor = ColorHelper.Argb.getArgb((int) (alphaScale * 255), 0, 0, 0);
        context.fill(getX(), getY(), getX() + width, getY() + height, greyColor);

        // Draw text.
        var client = MinecraftClient.getInstance();

        float fontScaling = 1.24f;

        int unscaledTextX = this.getX() + 5;
        int unscaledTextY = this.getY() + this.height - client.textRenderer.fontHeight - 5;
        int textX = (int) (unscaledTextX / fontScaling);
        int textY = (int) (unscaledTextY / fontScaling);
        int endX = (int) ((this.getX() + this.width - 5) / fontScaling);
        int endY = (int) ((this.getY() + this.height - 5) / fontScaling);

        context.fill(unscaledTextX - 5, unscaledTextY - 5, unscaledTextX + this.width - 5, unscaledTextY + client.textRenderer.fontHeight + 5, 0xAF000000);

        context.getMatrices().push();
        context.getMatrices().scale(fontScaling, fontScaling, 1.0f);

//            context.fill(textX, textY, endX, endY, 0xFFFF2F00);
        drawScrollableText(context, client.textRenderer, getMessage(), textX, textX, textY, endX, endY, 0xFFFFFF);

        context.getMatrices().pop();

        // Draw border.
        context.drawBorder(getX(), getY(), width, height, 0x0FFFFFFF);
        context.disableScissor();
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {}
}
