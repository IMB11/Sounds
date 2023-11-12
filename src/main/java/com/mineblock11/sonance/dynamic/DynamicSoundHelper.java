package com.mineblock11.sonance.dynamic;

import com.mineblock11.sonance.api.SoundDefinition;
import com.mineblock11.sonance.config.SonanceConfig;
import com.mojang.serialization.Codec;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.SmithingScreen;
import net.minecraft.client.gui.screen.ingame.StonecutterScreen;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.screen.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class DynamicSoundHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicSoundHelper.class);
    protected static HashMap<String, Codec<?>> loadDirectories = new HashMap<>();
    protected static HashMap<String, ArrayList<?>> loadedDefinitions = new HashMap<>();

    protected static void declareDefinitionsToLoad(String directory, Codec<?> codec) {
        loadDirectories.put(directory, codec);
        loadedDefinitions.put(directory, new ArrayList<>());
    }

    protected static void clearDefinitions() {
        loadedDefinitions.values().forEach(ArrayList::clear);
    }

    public static void initialize() {
        declareDefinitionsToLoad("items", SoundDefinition.getCodec(Registries.ITEM.getKey()));
        declareDefinitionsToLoad("screens", SoundDefinition.getCodec(Registries.SCREEN_HANDLER.getKey()));
    }

    @Deprecated
    public static SoundEvent getScreenSound(ScreenHandler screen, boolean isOpening) {
        try {
            var type = screen.getType();
            for (SoundDefinition<ScreenHandlerType<?>> definition : (ArrayList<SoundDefinition<ScreenHandlerType<?>>>) loadedDefinitions.get("screens")) {
                if(definition.keys.isValid(type)) {
                    return definition.soundEvent;
                }
            }
        } catch (Exception ignored) {
            LOGGER.warn("Screen of type {} has no declared ScreenHandlerType - ignoring.", screen.getClass().getName());
        }

        return isOpening ?
                SonanceConfig.get().inventoryOpenSoundEffect.fetchSoundEvent() :
                SonanceConfig.get().inventoryCloseSoundEffect.fetchSoundEvent();
    }

    @Deprecated
    public static SoundEvent getItemSound(ItemStack itemStack, SoundEvent defaultSoundEvent, BlockSoundType type) {
        var item = itemStack.getItem();

        for (SoundDefinition<Item> definition : (ArrayList<SoundDefinition<Item>>) loadedDefinitions.get("items")) {
            if(definition.keys.isValid(item)) {
                return definition.soundEvent;
            }
        }

        if (item instanceof BlockItem blockItem) {
            var block = blockItem.getBlock();
            return type.getTransformer().apply(block.getSoundGroup(block.getDefaultState()));
        }

        return defaultSoundEvent;
    }

    public enum BlockSoundType {
        PLACE(BlockSoundGroup::getPlaceSound),
        HIT(BlockSoundGroup::getHitSound),
        BREAK(BlockSoundGroup::getBreakSound),
        FALL(BlockSoundGroup::getFallSound),
        STEP(BlockSoundGroup::getStepSound);

        private final Function<BlockSoundGroup, SoundEvent> transformer;

        BlockSoundType(Function<BlockSoundGroup, SoundEvent> transformer) {
            this.transformer = transformer;
        }

        public Function<BlockSoundGroup, SoundEvent> getTransformer() {
            return transformer;
        }
    }
}
