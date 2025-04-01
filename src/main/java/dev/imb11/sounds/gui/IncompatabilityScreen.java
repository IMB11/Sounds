package dev.imb11.sounds.gui;

import dev.imb11.mru.RenderUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class IncompatabilityScreen extends Screen {
    public IncompatabilityScreen() {
        super(Component.translatable("sounds.incompatability.title"));
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        context.drawCenteredString(this.font, Component.translatable("sounds.incompatability.title"), this.width / 2, 20, 0xFFff000d);
        RenderUtils.drawTextWrapped(context, this.font, Component.translatable("sounds.incompatability.message"), 10, 20 + (this.font.lineHeight * 2), this.width - 40, 0xFFFFFFFF);
    }

    @Override
    public void onClose() {}
}
