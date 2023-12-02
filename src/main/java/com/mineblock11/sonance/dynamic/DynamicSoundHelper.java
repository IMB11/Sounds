package com.mineblock11.sonance.dynamic;

import com.mineblock11.sonance.api.SoundDefinition;
import com.mineblock11.sonance.config.SonanceConfig;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.item.*;
import net.minecraft.screen.*;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
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
        declareDefinitionsToLoad("items", SoundDefinition.getCodec(BuiltInRegistries.ITEM.key()));
        declareDefinitionsToLoad("screens", SoundDefinition.getCodec(BuiltInRegistries.MENU.key()));
    }

    @Deprecated
    public static SoundEvent getScreenSound(AbstractContainerMenu screen, boolean isOpening) {
        try {
            var type = screen.getType();
            for (SoundDefinition<MenuType<?>> definition : (ArrayList<SoundDefinition<MenuType<?>>>) loadedDefinitions.get("screens")) {
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
            return type.getTransformer().apply(block.getSoundType(block.defaultBlockState()));
        }

        return defaultSoundEvent;
    }

    public enum BlockSoundType {
        PLACE(SoundType::getPlaceSound),
        HIT(SoundType::getHitSound),
        BREAK(SoundType::getBreakSound),
        FALL(SoundType::getFallSound),
        STEP(SoundType::getStepSound);

        private final Function<SoundType, SoundEvent> transformer;

        BlockSoundType(Function<SoundType, SoundEvent> transformer) {
            this.transformer = transformer;
        }

        public Function<SoundType, SoundEvent> getTransformer() {
            return transformer;
        }
    }
}
