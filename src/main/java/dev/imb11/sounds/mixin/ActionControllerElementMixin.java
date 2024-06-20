package dev.imb11.sounds.mixin;

import dev.isxander.yacl3.api.utils.Dimension;
import dev.isxander.yacl3.gui.YACLScreen;
import dev.isxander.yacl3.gui.controllers.ActionController;
import dev.isxander.yacl3.gui.controllers.ControllerWidget;
import dev.isxander.yacl3.gui.utils.GuiUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(value = ActionController.ActionControllerElement.class, remap = false)
public abstract class ActionControllerElementMixin extends ControllerWidget<ActionController> {
    public ActionControllerElementMixin(ActionController control, YACLScreen screen, Dimension<Integer> dim) {
        super(control, screen, dim);
    }

    @Inject(method = "executeAction", at = @At("HEAD"), cancellable = true)
    public void $dontClickSound(CallbackInfo ci) {
        if(this.control.option().name().getString().contains(Text.translatable("sounds.config.preview.name").getString().split(" ")[0])) {
            ci.cancel();
            control.option().action().accept(screen, control.option());
        }
    }

    @Override
    public void render(DrawContext graphics, int mouseX, int mouseY, float delta) {
        if(this.control.option().name().getString().contains(Text.translatable("sounds.config.preview.name").getString().split(" ")[0])) {
            hovered = isMouseOver(mouseX, mouseY);

            Text name = control.option().changed() ? modifiedOptionName : control.option().name();
            Text shortenedName = Text.literal(GuiUtils.shortenString(name.getString(), textRenderer, getDimension().width() - getControlWidth() - getXPadding() - 7, "...")).setStyle(name.getStyle());

            drawButtonRect(graphics, getDimension().x(), getDimension().y(), getDimension().xLimit(), getDimension().yLimit(), hovered || focused, isAvailable());
            graphics.drawText(textRenderer, shortenedName, getDimension().x() + getXPadding(), getTextY(), getValueColor(), true);

            String valueText = "LISTEN";
            graphics.drawText(textRenderer, valueText, getDimension().xLimit() - textRenderer.getWidth(valueText) - getXPadding(), getTextY(), getValueColor(), true);
            if (isHovered()) {
                drawHoveredControl(graphics, mouseX, mouseY, delta);
            }
        } else {
            super.render(graphics, mouseX, mouseY, delta);
        }
    }
}
