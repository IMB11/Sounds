package dev.imb11.sounds.dynamic;

import com.mojang.serialization.Codec;
import dev.imb11.mru.RegistryUtils;
import dev.imb11.sounds.api.SoundDefinition;
import net.minecraft.resources.ResourceLocation;
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
        PLACE(t -> RegistryUtils.getId(t.getPlaceSound())),
        HIT(t -> RegistryUtils.getId(t.getHitSound())),
        BREAK(t -> RegistryUtils.getId(t.getBreakSound())),
        FALL(t -> RegistryUtils.getId(t.getFallSound())),
        STEP(t -> RegistryUtils.getId(t.getStepSound()));

        private final Function<SoundType, ResourceLocation> transformer;

        BlockSoundType(Function<SoundType, ResourceLocation> transformer) {
            this.transformer = transformer;
        }

        public Function<SoundType, ResourceLocation> getTransformer() {
            return transformer;
        }
    }
}
