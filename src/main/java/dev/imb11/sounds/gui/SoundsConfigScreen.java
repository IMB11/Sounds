package dev.imb11.sounds.gui;

import dev.imb11.sounds.config.ChatSoundsConfig;
import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.config.UISoundsConfig;
import dev.imb11.sounds.config.utils.ConfigGroup;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
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
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        assert this.client != null;
        context.drawCenteredTextWithShadow(this.client.textRenderer, Text.translatable("sounds.config.title"), this.width / 2, 10, 0xFFFFFF);
    }

    @Override
    public void close() {
        assert this.client != null;
        this.client.setScreen(this.parent);
    }

    @Override
    protected void init() {
        super.init();

        assert this.client != null;
        int fontHeight = this.client.textRenderer.fontHeight;
        DynamicGridWidget grid = new DynamicGridWidget(10, 10 + fontHeight + 10, width - 20, height - 20 - fontHeight - 10 - 20);

        ImageButtonWidget koFiWidget = new ImageButtonWidget(0, 0, 0, 0, Text.of("Support Me"), Identifier.of("sounds", "textures/gui/kofi.webp"), btn -> {
            Util.getOperatingSystem().open("https://ko-fi.com/mineblock11");
        });

        grid.setPadding(3);
        grid.addChild(koFiWidget);

        ConfigGroup<?>[] configGroups = SoundsConfig.getAll();
        // Sort by class name.
        configGroups = Arrays.stream(configGroups).sorted(Comparator.comparing(o -> o.getClass().getSimpleName())).toArray(ConfigGroup[]::new);

        for (ConfigGroup<?> configGroup : configGroups) {
            if(configGroup instanceof ChatSoundsConfig) {
                grid.addChild(new ImageButtonWidget(0, 0, 0, 0, configGroup.getName(), configGroup.getImage(), btn -> {
                    this.client.setScreen(configGroup.getYACL().generateScreen(this));
                }), 3, 1);
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

        grid.addChild(new ImageButtonWidget(0, 0, 0, 0, Text.of("Discord Server"), Identifier.of("sounds", "textures/gui/discord.webp"), btn -> {
            Util.getOperatingSystem().open("https://discord.imb11.dev/"); // Rick roll lol.
        }));

        grid.calculateLayout();

        grid.forEachChild(this::addDrawableChild);

        ButtonWidget buttonWidget = ButtonWidget.builder(ScreenTexts.DONE, (btn) -> this.client.setScreen(this.parent)).dimensions(this.width / 2 - 100, this.height - 30, 200, 20).build();

        this.addDrawableChild(buttonWidget);
    }
}
