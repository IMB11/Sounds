package dev.imb11.sounds.gui;

import dev.imb11.mru.RenderUtils;
import dev.imb11.sounds.SoundsClient;
import dev.imb11.sounds.config.*;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class SoundsConfigScreen extends Screen {
    private final Screen parent;

    public SoundsConfigScreen(@Nullable Screen parent) {
        super(Component.translatable("sounds.config.title"));
        this.parent = parent;
    }

    @Override
    public void onClose() {
        assert this.minecraft != null;
        this.minecraft.setScreen(this.parent);
    }

    public float timeSinceLastSupporter = -1;
    public int supporterIndex = -1;

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        /*? if =1.20.1 {*/
        /*super.renderBackground(context);
        *//*?} else {*/
        super.renderBackground(context, mouseX, mouseY, delta);
        /*?}*/
        super.render(context, mouseX, mouseY, delta);

        assert this.minecraft != null;
        float soundsConfigTitleWidth = font.width(Component.translatable("sounds.config.title"));
        context.drawCenteredString(this.minecraft.font, Component.translatable("sounds.config.title"), this.width / 2, 10, 0xFFFFFF);

        if(timeSinceLastSupporter == -1) {
            timeSinceLastSupporter = Util.getMillis();
            supporterIndex = 0;
        }

        if(timeSinceLastSupporter + 2000 < Util.getMillis()) {
            supporterIndex++;
            if(supporterIndex >= SoundsClient.SUPPORTERS.length) {
                supporterIndex = 0;
            }
            timeSinceLastSupporter = Util.getMillis();
        }

        String supporter = SoundsClient.SUPPORTERS[supporterIndex];

        float timePassed = (Util.getMillis() - timeSinceLastSupporter) / 2000f;
        float fadeBetween = (Mth.sin(timePassed * (float)Math.PI) + 1) / 2;
        if(fadeBetween < 0.01) {
            fadeBetween = 0;
        }
        int alpha = (int) (fadeBetween * 255.0f);
        int color = (alpha << 24) | (0xFFFFFF);
        Component text = Component.literal(supporter).withStyle(ChatFormatting.GOLD).append(Component.literal(" supports me on Ko-Fi!").withStyle(ChatFormatting.WHITE));
        int textX = (int) 10;
        int spaceBetween = (this.width / 2 - 40);
        int textTotalHeight = font.wordWrapHeight(text, spaceBetween - 20);
        int targetY = 10 + font.lineHeight / 2;
        int textY = targetY - (textTotalHeight / 2);
        RenderUtils.drawTextWrapped(context, font, text, textX, textY, spaceBetween - 20, color);
    }

    @Override
    protected void init() {
        super.init();

        int fontHeight = font.lineHeight;
        int gridX = 10;
        int gridY = 10 + fontHeight + 10;
        int gridWidth = width - 15;
        int gridHeight = height - 20 - fontHeight - 10 - 20;

        int numColumns = 4;
        int numRows = 3;

        int cellWidth = gridWidth / numColumns;
        int cellHeight = gridHeight / numRows;

        WorldSoundsConfig worldSoundsConfig = SoundsConfig.getRaw(WorldSoundsConfig.class);
        ImageButtonWidget worldSoundsButton = new ImageButtonWidget(
                gridX + 0 * cellWidth, gridY + 0 * cellHeight, 2 * cellWidth - 6, 2 * cellHeight - 6, // -6 for padding of 3 on each side
                worldSoundsConfig.getName(), worldSoundsConfig.getIcon(), btn -> {
            this.minecraft.setScreen(worldSoundsConfig.getYACL().generateScreen(this));
        });
        addRenderableWidget(worldSoundsButton);

        ChatSoundsConfig chatSoundsConfig = SoundsConfig.getRaw(ChatSoundsConfig.class);
        ImageButtonWidget chatSoundsButton = new ImageButtonWidget(
                gridX + 2 * cellWidth, gridY + 0 * cellHeight, 2 * cellWidth - 6, cellHeight - 6,
                chatSoundsConfig.getName(), chatSoundsConfig.getIcon(), btn -> {
            this.minecraft.setScreen(chatSoundsConfig.getYACL().generateScreen(this));
        });
        addRenderableWidget(chatSoundsButton);

        EventSoundsConfig eventSoundsConfig = SoundsConfig.getRaw(EventSoundsConfig.class);
        ImageButtonWidget eventSoundsButton = new ImageButtonWidget(
                gridX + 2 * cellWidth, gridY + 1 * cellHeight, cellWidth - 6, cellHeight - 6,
                eventSoundsConfig.getName(), eventSoundsConfig.getIcon(), btn -> {
            this.minecraft.setScreen(eventSoundsConfig.getYACL().generateScreen(this));
        });
        addRenderableWidget(eventSoundsButton);

        ModConfig modConfig = SoundsConfig.getRaw(ModConfig.class);
        ImageButtonWidget modConfigButton = new ImageButtonWidget(
                gridX + 3 * cellWidth, gridY + 1 * cellHeight, cellWidth - 6, 2 * cellHeight - 6,
                modConfig.getName(), modConfig.getIcon(), btn -> {
            this.minecraft.setScreen(modConfig.getYACL().generateScreen(this));
        });
        addRenderableWidget(modConfigButton);

        UISoundsConfig uiSoundsConfig = SoundsConfig.getRaw(UISoundsConfig.class);
        ImageButtonWidget uiSoundsButton = new ImageButtonWidget(
                gridX + 0 * cellWidth, gridY + 2 * cellHeight, 3 * cellWidth - 6, cellHeight - 6,
                uiSoundsConfig.getName(), uiSoundsConfig.getIcon(), btn -> {
            this.minecraft.setScreen(uiSoundsConfig.getYACL().generateScreen(this));
        });
        addRenderableWidget(uiSoundsButton);

        int discordAndKoFiButtonsWidth = 100 + 100 + 30; // button widths + left margin of Ko-Fi button + right margin of Discord button
        int doneButtonWidth = this.width - discordAndKoFiButtonsWidth;
        Button buttonWidget = new GreyButton(this.width / 2 - doneButtonWidth / 2, this.height - 30, doneButtonWidth, 20, CommonComponents.GUI_DONE, (btn) -> this.minecraft.setScreen(this.parent), Supplier::get);
        Button koFiButton = new GreyButton(10, this.height - 30, 100, 20, Component.literal("Donate").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD), (btn) -> Util.getPlatform().openUri("https://ko-fi.com/imb11"), Supplier::get);
        Button discordButton = new GreyButton(this.width - 110, this.height - 30, 100, 20, Component.literal("Discord").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD), (btn) -> Util.getPlatform().openUri("https://discord.imb11.dev/"), Supplier::get);
        this.addRenderableWidget(buttonWidget);
        this.addRenderableWidget(koFiButton);
        this.addRenderableWidget(discordButton);
    }
}