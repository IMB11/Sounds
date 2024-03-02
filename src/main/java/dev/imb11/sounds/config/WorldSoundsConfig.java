package dev.imb11.sounds.config;

import dev.imb11.sounds.sound.ConfiguredSound;
import dev.imb11.sounds.sound.DynamicConfiguredSound;
import dev.imb11.sounds.sound.context.RepeaterSoundContext;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class WorldSoundsConfig extends ConfigGroup {
    /// == MECHANICS == ///
    public DynamicConfiguredSound<Integer, RepeaterSoundContext> repeaterUseSoundEffect = new DynamicConfiguredSound<>("repeaterUse", SoundEvents.BLOCK_NOTE_BLOCK_HAT, true, 1.0F, 0.45F, true);
    @SerialEntry
    public ConfiguredSound jukeboxUseSoundEffect = new ConfiguredSound("jukeboxUse", SoundEvents.BLOCK_NOTE_BLOCK_BASEDRUM, true, 0.8F, 0.75F);
    @SerialEntry
    public ConfiguredSound daylightDetectorUseSoundEffect = new ConfiguredSound("daylightDetectorUse", SoundEvents.BLOCK_NOTE_BLOCK_HAT, true, 0.8F, 0.45F);
    @SerialEntry
    public ConfiguredSound furnaceMinecartFuelSoundEffect = new ConfiguredSound("furnaceMinecartFuel", SoundEvents.ENTITY_CREEPER_HURT, true, 1.9F, 0.2F);
    @SerialEntry
    public ConfiguredSound stickyPistonSoundEffect = new ConfiguredSound("stickyPistonSoundEffect", SoundEvents.BLOCK_SLIME_BLOCK_PLACE, true, 0.5F, 0.5F);

    /// == ACTIONS == ///
    @SerialEntry
    public boolean enableDynamicEatingSounds = true;
    @SerialEntry
    public ConfiguredSound frostWalkerSoundEffect = new ConfiguredSound("frostWalker", SoundEvents.BLOCK_GLASS_HIT, true, 1.0F, 0.5F);
    @SerialEntry
    public ConfiguredSound leadSnappingSoundEffect = new ConfiguredSound("leadSnapping", SoundEvents.ENTITY_LEASH_KNOT_BREAK, true, 1.0F, 0.5F);
    @SerialEntry
    public ConfiguredSound bowPullSoundEffect = new ConfiguredSound("bowPull", SoundEvents.ITEM_CROSSBOW_LOADING_MIDDLE, true, 1.0F, 0.25F);

    /// == MOBS == ///
    @SerialEntry
    public ConfiguredSound babyChickenChirpSoundEffect = new ConfiguredSound("babyChickenChirp", SoundEvents.ENTITY_PARROT_AMBIENT, true, 2.0F, 0.3F);
    @SerialEntry
    public ConfiguredSound wolfHowlingSoundEffect = new ConfiguredSound("wolfHowling", SoundEvents.ENTITY_WOLF_HOWL, true, 1.0F, 0.5F);
    @SerialEntry
    public ConfiguredSound chargedCreeperAmbienceSoundEffect = new ConfiguredSound("chargedCreeperAmbience", SoundEvents.BLOCK_BEACON_AMBIENT, true, 0.4F, 0.5F);
    @SerialEntry
    public ConfiguredSound huskCoughSoundEffect = new ConfiguredSound("huskCough", SoundEvents.ENTITY_HORSE_BREATHE, true, 0.2F, 0.5F);

    public WorldSoundsConfig() {
        super(WorldSoundsConfig.class);
    }

    @Override
    public WorldSoundsConfig get() {
        return super.get();
    }

    @Override
    public YetAnotherConfigLib getYACL() {
        return null;
    }

    @Override
    public Identifier getImage() {
        return new Identifier("sounds", "textures/gui/gameplay_sounds.webp");
    }

    @Override
    public Text getName() {
        return Text.translatable("sounds.config.world");
    }

    @Override
    public String getID() {
        return "world";
    }
}
