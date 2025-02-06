package dev.imb11.sounds.dynamic;

import com.mojang.serialization.Codec;
import dev.imb11.sounds.api.SoundDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;

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

    public static <T> ArrayList<SoundDefinition<T>> getDefinitions(String directory) {
        return (ArrayList<SoundDefinition<T>>) loadedDefinitions.get(directory);
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
