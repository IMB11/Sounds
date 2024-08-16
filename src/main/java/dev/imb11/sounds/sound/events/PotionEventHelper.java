package dev.imb11.sounds.sound.events;

import dev.architectury.event.events.client.ClientTickEvent;
import dev.imb11.sounds.config.EventSoundsConfig;
import dev.imb11.sounds.config.SoundsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class PotionEventHelper {
    private static final AtomicReference<Map<Identifier, StatusEffectInstance>> previousEffects = new AtomicReference<>(null);

    public static void initialize() {
        ClientTickEvent.CLIENT_LEVEL_PRE.register(PotionEventHelper::listenForEffectChanges);
    }

    private static void listenForEffectChanges(ClientWorld clientWorld) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null) return;

        Map<Identifier, StatusEffectInstance> currentEffects = new HashMap<>();
        client.player.getStatusEffects().forEach(effectInstance -> {
            /*? if =1.20.1 {*/
            /*StatusEffect effect = effectInstance.getEffectType();
             *//*?} else {*/
            StatusEffect effect = effectInstance.getEffectType().value();
            /*?}*/
            currentEffects.put(Registries.STATUS_EFFECT.getId(effect), effectInstance);
        });

        if (previousEffects.get() != null) {
            Map<Identifier, StatusEffectInstance> removedEffects = new HashMap<>(previousEffects.get());
            removedEffects.keySet().removeAll(currentEffects.keySet());

            for (Identifier effectId : removedEffects.keySet()) {
                StatusEffect statusEffect = Registries.STATUS_EFFECT.get(effectId);

                if (statusEffect == null) continue;
                if(SoundsConfig.get(EventSoundsConfig.class).ignoreSilencedStatusEffects && !removedEffects.get(effectId).shouldShowIcon()) continue;
                if (statusEffect.isBeneficial()) {
                    SoundsConfig.get(EventSoundsConfig.class).positiveStatusEffectLoseSoundEffect.playSound();
                } else {
                    SoundsConfig.get(EventSoundsConfig.class).negativeStatusEffectLoseSoundEffect.playSound();
                }
            }

            Map<Identifier, StatusEffectInstance> addedEffects = new HashMap<>(currentEffects);
            addedEffects.keySet().removeAll(previousEffects.get().keySet());

            for (Identifier effectId : addedEffects.keySet()) {
                StatusEffect statusEffect = Registries.STATUS_EFFECT.get(effectId);

                if (statusEffect == null) continue;
                if(SoundsConfig.get(EventSoundsConfig.class).ignoreSilencedStatusEffects && !addedEffects.get(effectId).shouldShowIcon()) continue;
                if (statusEffect.isBeneficial()) {
                    SoundsConfig.get(EventSoundsConfig.class).positiveStatusEffectGainSoundEffect.playSound();
                } else {
                    SoundsConfig.get(EventSoundsConfig.class).negativeStatusEffectGainSoundEffect.playSound();
                }
            }
        }

        previousEffects.set(currentEffects);
    }
}
