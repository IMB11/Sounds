package dev.imb11.sounds.config;

import dev.isxander.yacl3.api.YetAnotherConfigLib;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class EventSoundsConfig extends ConfigGroup {
    public EventSoundsConfig() {
        super(EventSoundsConfig.class);
    }

    @Override
    public YetAnotherConfigLib getYACL() {
        return null;
    }

    @Override
    public Identifier getImage() {
        return new Identifier("sounds", "textures/gui/event_sounds.webp");
    }

    @Override
    public Text getName() {
        return Text.translatable("sounds.config.event");
    }

    @Override
    public String getID() {
        return "event";
    }
}
