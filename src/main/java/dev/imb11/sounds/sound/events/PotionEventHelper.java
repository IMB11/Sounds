package dev.imb11.sounds.sound.events;

import dev.imb11.sounds.config.old.GameplaySoundConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PotionEventHelper {
    private static final AtomicReference<List<Identifier>> previousEffects = new AtomicReference<>(null);;

    public static void initialize() {
        ClientTickEvents.START_CLIENT_TICK.register(listenForEffectChanges());
    }

    @NotNull
    private static ClientTickEvents.StartTick listenForEffectChanges() {
        return client -> {
            if (client.player == null) return;

            List<Identifier> currentEffects = client.player.getStatusEffects().stream().map(instance -> Registries.STATUS_EFFECT.getId(instance.getEffectType())).toList();

            if (previousEffects.get() != null) {
                Collection<Identifier> removedEffects = new ArrayList<>(previousEffects.get());
                removedEffects.removeAll(currentEffects);

                for (Identifier effect : removedEffects) {
                    StatusEffect statusEffect = Registries.STATUS_EFFECT.get(effect);

                    if (statusEffect == null) continue;

                    if (statusEffect.isBeneficial()) {
                        GameplaySoundConfig.get().positiveStatusEffectLoseSoundEffect.playSound();
                    } else {
                        GameplaySoundConfig.get().negativeStatusEffectLoseSoundEffect.playSound();
                    }
                }

                // Find out what was added
                List<Identifier> addedEffects = new ArrayList<>(currentEffects);
                addedEffects.removeAll(previousEffects.get());

                for (Identifier effect : addedEffects) {
                    StatusEffect statusEffect = Registries.STATUS_EFFECT.get(effect);

                    if (statusEffect == null) continue;

                    if (statusEffect.isBeneficial()) {
                        GameplaySoundConfig.get().positiveStatusEffectGainSoundEffect.playSound();
                    } else {
                        GameplaySoundConfig.get().negativeStatusEffectGainSoundEffect.playSound();
                    }
                }
            }

            previousEffects.set(currentEffects);
        };
    }
}
