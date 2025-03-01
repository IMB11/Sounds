package dev.imb11.sounds.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class GreyButton extends Button {
    private final Minecraft minecraft;
    private float durationHovered = 0;
    public GreyButton(int x, int y, int width, int height, Component message, OnPress onPress, CreateNarration createNarration) {
        super(x, y, width, height, message, onPress, createNarration);
        this.minecraft = Minecraft.getInstance();
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        this.isHovered = mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height;

        if (this.isHovered || this.isFocused()) {
            durationHovered = Math.min(durationHovered + delta / 2f, 1f);
        } else {
            durationHovered = Math.max(durationHovered - delta / 4f, 0f);
        }

        float alphaScale = Mth.clampedLerp(0.9f, 0.5f, durationHovered);

        // Grey overlay for hover effect (render first, behind icon and text)
        int a = (int) (255 * alphaScale);
        int greyColor = (a << 24);

        guiGraphics.fill(getX(), getY(), getX() + width, getY() + height, greyColor);

        int i = this.active ? 16777215 : 10526880;
        this.renderString(guiGraphics, this.minecraft.font, i | Mth.ceil(this.alpha * 255.0F) << 24);
    }
}
