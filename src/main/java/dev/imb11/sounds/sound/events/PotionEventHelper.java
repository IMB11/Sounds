package dev.imb11.sounds.sound.events;

import dev.imb11.sounds.config.EventSoundsConfig;
import dev.imb11.sounds.config.SoundsConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

public class PotionEventHelper {
    private final AtomicReference<Map<Identifier, MobEffectInstance>> previousEffects = new AtomicReference<>(null);

    public void listenForEffectChanges(ClientLevel clientWorld) {
        Minecraft client = Minecraft.getInstance();

        if (client.player == null) return;

        Map<Identifier, MobEffectInstance> currentEffects = new HashMap<>();
        client.player.getActiveEffects().forEach(effectInstance -> {
            MobEffect effect = effectInstance.getEffect().value();
            currentEffects.put(BuiltInRegistries.MOB_EFFECT.getKey(effect), effectInstance);
        });

        if (previousEffects.get() != null) {
            Map<Identifier, MobEffectInstance> removedEffects = new HashMap<>(previousEffects.get());
            removedEffects.keySet().removeAll(currentEffects.keySet());

            for (Identifier effectId : removedEffects.keySet()) {
                MobEffect statusEffect = BuiltInRegistries.MOB_EFFECT.getValue(effectId);

                if (statusEffect == null) continue;
                if(SoundsConfig.get(EventSoundsConfig.class).ignoreSilencedStatusEffects && !removedEffects.get(effectId).showIcon()) continue;
                if (statusEffect.isBeneficial()) {
                    SoundsConfig.get(EventSoundsConfig.class).positiveStatusEffectLoseSoundEffect.playSound(true);
                } else {
                    SoundsConfig.get(EventSoundsConfig.class).negativeStatusEffectLoseSoundEffect.playSound(true);
                }
            }

            Map<Identifier, MobEffectInstance> addedEffects = new HashMap<>(currentEffects);
            addedEffects.keySet().removeAll(previousEffects.get().keySet());

            for (Identifier effectId : addedEffects.keySet()) {
                MobEffect statusEffect = BuiltInRegistries.MOB_EFFECT.getValue(effectId);

                if (statusEffect == null) continue;
                if(SoundsConfig.get(EventSoundsConfig.class).ignoreSilencedStatusEffects && !addedEffects.get(effectId).showIcon()) continue;
                if (statusEffect.isBeneficial()) {
                    SoundsConfig.get(EventSoundsConfig.class).positiveStatusEffectGainSoundEffect.playSound(true);
                } else {
                    SoundsConfig.get(EventSoundsConfig.class).negativeStatusEffectGainSoundEffect.playSound(true);
                }
            }
        }

        previousEffects.set(currentEffects);
    }
}
