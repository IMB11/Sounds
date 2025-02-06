package dev.imb11.sounds.api.event;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import net.minecraft.world.level.block.SoundType;

public class SoundDefinitionReplacementEvent {
    private static final ArrayList<SoundDefinitionReplacementListener> EVENT_LISTENERS = new ArrayList<>();

    /**
     * Register a listener for the sound definition replacement event. **WARNING:** Do not register too many listeners, it WILL slow down the game's audio engine.
     * @param listener The listener to register.
     */
    public static void registerListener(SoundDefinitionReplacementListener listener) {
        EVENT_LISTENERS.add(listener);
    }

    /**
     * Unregister a listener for the sound definition replacement event. **WARNING:** Do not register too many listeners, it WILL slow down the game's audio engine.
     * @param listener The listener to unregister.
     */
    public static void unregisterListener(SoundDefinitionReplacementListener listener) {
        EVENT_LISTENERS.remove(listener);
    }

    @ApiStatus.Internal
    public static Response fire(SoundType event) {
        for (SoundDefinitionReplacementListener listener : EVENT_LISTENERS) {
            Response response = listener.onSoundDefinitionReplacement(event);
            if (response.response() != ResponseType.PASS) {
                return response;
            }
        }
        return Response.pass();
    }

    public enum ResponseType {
        /**
         * Pass the event to the next listener, if any.
         */
        PASS,
        /**
         * Stop the event from being passed to the next listener and set the sound definition to the new one.
         */
        OVERRIDE,
        /**
         * Stop the event from being passed to the next listener and cancel the sound definition.
         */
        CANCEL
    }

    /**
     * Represents the response of a listener.
     * @param response The type of response - see the {@link ResponseType} enum for more information.
     * @param override The new block sound group to use, if the response is {@link ResponseType#OVERRIDE}.
     */
    public record Response(ResponseType response, @Nullable SoundType override) {
        public static Response pass() {
            return new Response(ResponseType.PASS, null);
        }

        public static Response override(SoundType override) {
            return new Response(ResponseType.OVERRIDE, override);
        }

        public static Response cancel() {
            return new Response(ResponseType.CANCEL, null);
        }
    }

    @FunctionalInterface
    public interface SoundDefinitionReplacementListener {
        /**
         * Called when a block sound group is about to be replaced.
         * @param plannedSoundGroup The block sound group that is planned to be used.
         * @return The response to the event - see the {@link Response} class for more information.
         */
        Response onSoundDefinitionReplacement(@Nullable SoundType plannedSoundGroup);
    }
}
