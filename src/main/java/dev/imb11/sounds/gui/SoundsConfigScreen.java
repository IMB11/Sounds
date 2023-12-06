package dev.imb11.sounds.gui;

import dev.imb11.sounds.config.GameplaySoundConfig;
import dev.imb11.sounds.config.UISoundConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class SoundsConfigScreen extends Screen {
    private final Screen parent;
    private ImageButtonWidget openUIButton;
    private ImageButtonWidget openGameplayButton;
    private ImageButtonWidget openModButton;

    public SoundsConfigScreen(@Nullable Screen parent) {
        super(Text.translatable("sounds.config.title"));
        this.parent = parent;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        context.drawCenteredTextWithShadow(this.client.textRenderer, Text.translatable("sounds.config.title"), this.width / 2, 10, 0xFFFFFF);
    }

    @Override
    public void close() {
        this.client.setScreen(this.parent);
    }

    @Override
    protected void init() {
        super.init();

        int fontHeight = this.client.textRenderer.fontHeight;
        DynamicGridWidget grid = new DynamicGridWidget(10, 10 + fontHeight + 10, width - 20, height - 20 - fontHeight - 10 - 20);

        this.openUIButton = new ImageButtonWidget(0, 0, 0, 0, Text.translatable("sounds.config.ui"), new Identifier("sounds", "textures/gui/ui_sounds.webp"));
        this.openGameplayButton = new ImageButtonWidget(0, 0, 0, 0, Text.translatable("sounds.config.gameplay"), new Identifier("sounds", "textures/gui/gameplay_sounds.webp"));
        this.openModButton = new ImageButtonWidget(0, 0, 0, 0, Text.translatable("sounds.config.mod"), new Identifier("sounds", "textures/gui/mod_sounds.webp"));

        this.openUIButton.setClickEvent((btn) -> {
            this.client.setScreen(UISoundConfig.getInstance().generateScreen(this));
        });

        this.openGameplayButton.setClickEvent((btn) -> {
            this.client.setScreen(GameplaySoundConfig.getInstance().generateScreen(this));
        });

        grid.addChild(this.openUIButton, 3, 1);
        grid.addChild(this.openGameplayButton, 2, 2);
        grid.addChild(this.openModButton, 1, 2);

        grid.setPadding(3);

        grid.calculateLayout();

        grid.forEachChild(this::addDrawableChild);

        // new ButtonWidget(, Text.of("Close"), (btn) -> this.client.setScreen(this.parent));
        ButtonWidget buttonWidget = ButtonWidget.builder(ScreenTexts.DONE, (btn) -> this.client.setScreen(this.parent)).dimensions(this.width / 2 - 100, this.height - 30, 200, 20).build();

        this.addDrawableChild(buttonWidget);
    }
}
