package dev.imb11.sounds.gui;

import dev.imb11.sounds.SoundsClient;
import dev.imb11.sounds.config.*;
import dev.imb11.sounds.config.utils.ConfigGroup;
import dev.imb11.sounds.util.RenderUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Comparator;
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
        DynamicGridWidget grid = new DynamicGridWidget(10, 10 + fontHeight + 10, width - 20, height - 20 - fontHeight - 10 - 20);
//        ImageButtonWidget koFiWidget = new ImageButtonWidget(0, 0, 0, 0, Text.of("Support Me"), Identifier.of("sounds", "textures/gui/kofi.webp"), btn -> {
//            Util.getOperatingSystem().open("https://ko-fi.com/mineblock11");
//        });

        grid.setPadding(3);
//        grid.addChild(koFiWidget);

        ConfigGroup<?>[] configGroups = SoundsConfig.getAll();
        // Sort by class name.
        configGroups = Arrays.stream(configGroups).sorted(Comparator.comparing(o -> o.getClass().getSimpleName())).toArray(ConfigGroup[]::new);

        // remove ModConfig.class from the list
        configGroups = Arrays.stream(configGroups).filter(configGroup -> configGroup.getClass() != ModConfig.class).toArray(ConfigGroup[]::new);

        for (ConfigGroup<?> configGroup : configGroups) {
            if (configGroup instanceof ChatSoundsConfig) {
                grid.addChild(new ImageButtonWidget(0, 0, 0, 0, configGroup.getName(), configGroup.getImage(), btn -> {
                    this.minecraft.setScreen(configGroup.getYACL().generateScreen(this));
                }), 1, 2);
            } else if (configGroup instanceof WorldSoundsConfig) {
                grid.addChild(new ImageButtonWidget(0, 0, 0, 0, configGroup.getName(), configGroup.getImage(), btn -> {
                    this.minecraft.setScreen(configGroup.getYACL().generateScreen(this));
                }), 2, 2);
            } else if (configGroup instanceof UISoundsConfig) {
                grid.addChild(new ImageButtonWidget(0, 0, 0, 0, configGroup.getName(), configGroup.getImage(), btn -> {
                    this.minecraft.setScreen(configGroup.getYACL().generateScreen(this));
                }), 2, 1);
            } else {
                grid.addChild(new ImageButtonWidget(0, 0, 0, 0, configGroup.getName(), configGroup.getImage(), btn -> {
                    this.minecraft.setScreen(configGroup.getYACL().generateScreen(this));
                }));
            }
        }

        grid.calculateLayout();

        grid.visitWidgets(this::addRenderableWidget);

        int discordAndKoFiButtonsWidth = 100 + 100 + 30; // button widths + left margin of Ko-Fi button + right margin of Discord button
        int doneButtonWidth = this.width - discordAndKoFiButtonsWidth;
        Button buttonWidget = Button.builder(CommonComponents.GUI_DONE, (btn) -> this.minecraft.setScreen(this.parent)).bounds(this.width / 2 - doneButtonWidth / 2, this.height - 30, doneButtonWidth, 20).build();
        Button koFiButton = Button.builder(Component.literal("Donate").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD), (btn) -> Util.getPlatform().openUri("https://ko-fi.com/mineblock11")).bounds(10, this.height - 30, 100, 20).build();
        Button discordButton = Button.builder(Component.literal("Discord").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD), (btn) -> Util.getPlatform().openUri("https://discord.imb11.dev/")).bounds(this.width - 110, this.height - 30, 100, 20).build();
        this.addRenderableWidget(buttonWidget);
        this.addRenderableWidget(koFiButton);
        this.addRenderableWidget(discordButton);
    }
}