package dev.imb11.sounds.gui;

import dev.imb11.sounds.SoundsClient;
import dev.imb11.sounds.config.ChatSoundsConfig;
import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.UISoundsConfig;
import dev.imb11.sounds.config.WorldSoundsConfig;
import dev.imb11.sounds.config.utils.ConfigGroup;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Comparator;

public class SoundsConfigScreen extends Screen {
    private final Screen parent;

    public SoundsConfigScreen(@Nullable Screen parent) {
        super(Text.translatable("sounds.config.title"));
        this.parent = parent;
    }

    @Override
    public void close() {
        assert this.client != null;
        this.client.setScreen(this.parent);
    }

    public float timeSinceLastSupporter = -1;
    public int supporterIndex = -1;

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        /*? if =1.20.1 {*/
        /*super.renderBackground(context);
        *//*?} else {*/
        super.renderBackground(context, mouseX, mouseY, delta);
        /*?}*/
        super.render(context, mouseX, mouseY, delta);

        assert this.client != null;
        float soundsConfigTitleWidth = textRenderer.getWidth(Text.translatable("sounds.config.title"));
        context.drawCenteredTextWithShadow(this.client.textRenderer, Text.translatable("sounds.config.title"), this.width / 2, 10, 0xFFFFFF);

        if(timeSinceLastSupporter == -1) {
            timeSinceLastSupporter = Util.getMeasuringTimeMs();
            supporterIndex = 0;
        }

        if(timeSinceLastSupporter + 2000 < Util.getMeasuringTimeMs()) {
            supporterIndex++;
            if(supporterIndex >= SoundsClient.SUPPORTERS.length) {
                supporterIndex = 0;
            }
            timeSinceLastSupporter = Util.getMeasuringTimeMs();
        }

        String supporter = SoundsClient.SUPPORTERS[supporterIndex];

        float timePassed = (Util.getMeasuringTimeMs() - timeSinceLastSupporter) / 2000f;
        float fadeBetween = (MathHelper.sin(timePassed * (float)Math.PI) + 1) / 2;
        if(fadeBetween < 0.01) {
            fadeBetween = 0;
        }
        int alpha = (int) (fadeBetween * 255.0f);
        int color = (alpha << 24) | (0xFFFFFF);
        Text text = Text.literal(supporter).formatted(Formatting.GOLD).append(Text.literal(" supports me on Ko-Fi!").formatted(Formatting.WHITE));
        int textX = (int) 10;
        int spaceBetween = (this.width / 2 - 40);
        int textTotalHeight = textRenderer.getWrappedLinesHeight(text, spaceBetween - 20);
        int targetY = 10 + textRenderer.fontHeight / 2;
        int textY = targetY - (textTotalHeight / 2);
        context.drawTextWrapped(textRenderer, text, textX, textY, spaceBetween - 20, color);
    }

    @Override
    protected void init() {
        super.init();

        int fontHeight = textRenderer.fontHeight;
        DynamicGridWidget grid = new DynamicGridWidget(10, 10 + fontHeight + 10, width - 20, height - 20 - fontHeight - 10 - 20);
//        ImageButtonWidget koFiWidget = new ImageButtonWidget(0, 0, 0, 0, Text.of("Support Me"), Identifier.of("sounds", "textures/gui/kofi.webp"), btn -> {
//            Util.getOperatingSystem().open("https://ko-fi.com/mineblock11");
//        });

        grid.setPadding(3);
//        grid.addChild(koFiWidget);

        ConfigGroup<?>[] configGroups = SoundsConfig.getAll();
        // Sort by class name.
        configGroups = Arrays.stream(configGroups).sorted(Comparator.comparing(o -> o.getClass().getSimpleName())).toArray(ConfigGroup[]::new);

        for (ConfigGroup<?> configGroup : configGroups) {
            if (configGroup instanceof ChatSoundsConfig) {
                grid.addChild(new ImageButtonWidget(0, 0, 0, 0, configGroup.getName(), configGroup.getImage(), btn -> {
                    this.client.setScreen(configGroup.getYACL().generateScreen(this));
                }), 1, 2);
            } else if (configGroup instanceof WorldSoundsConfig) {
                grid.addChild(new ImageButtonWidget(0, 0, 0, 0, configGroup.getName(), configGroup.getImage(), btn -> {
                    this.client.setScreen(configGroup.getYACL().generateScreen(this));
                }), 2, 2);
            } else if (configGroup instanceof UISoundsConfig) {
                grid.addChild(new ImageButtonWidget(0, 0, 0, 0, configGroup.getName(), configGroup.getImage(), btn -> {
                    this.client.setScreen(configGroup.getYACL().generateScreen(this));
                }), 2, 1);
            } else {
                grid.addChild(new ImageButtonWidget(0, 0, 0, 0, configGroup.getName(), configGroup.getImage(), btn -> {
                    this.client.setScreen(configGroup.getYACL().generateScreen(this));
                }));
            }
        }

        grid.calculateLayout();

        grid.forEachChild(this::addDrawableChild);

        int discordAndKoFiButtonsWidth = 100 + 100 + 30; // button widths + left margin of Ko-Fi button + right margin of Discord button
        int doneButtonWidth = this.width - discordAndKoFiButtonsWidth;
        ButtonWidget buttonWidget = ButtonWidget.builder(ScreenTexts.DONE, (btn) -> this.client.setScreen(this.parent)).dimensions(this.width / 2 - doneButtonWidth / 2, this.height - 30, doneButtonWidth, 20).build();
        ButtonWidget koFiButton = ButtonWidget.builder(Text.literal("Donate").formatted(Formatting.GOLD).formatted(Formatting.BOLD), (btn) -> Util.getOperatingSystem().open("https://ko-fi.com/mineblock11")).dimensions(10, this.height - 30, 100, 20).build();
        ButtonWidget discordButton = ButtonWidget.builder(Text.literal("Discord").formatted(Formatting.AQUA).formatted(Formatting.BOLD), (btn) -> Util.getOperatingSystem().open("https://discord.imb11.dev/")).dimensions(this.width - 110, this.height - 30, 100, 20).build();
        this.addDrawableChild(buttonWidget);
        this.addDrawableChild(koFiButton);
        this.addDrawableChild(discordButton);
    }
}