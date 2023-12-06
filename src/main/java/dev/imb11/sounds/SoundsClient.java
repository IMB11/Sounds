package dev.imb11.sounds;

import dev.imb11.sounds.config.GameplaySoundConfig;
import dev.imb11.sounds.config.SoundsConfig;
import dev.imb11.sounds.dynamic.DynamicSoundHelper;
import dev.imb11.sounds.dynamic.SoundsReloadListener;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.Registries;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class SoundsClient implements ClientModInitializer {
    public static Identifier id(String id) {
        return new Identifier("sounds", id);
    }

    public static final Logger LOGGER = LoggerFactory.getLogger("Sounds");

    @Override
    public void onInitializeClient() {
        SoundsConfig.loadAll();
        DynamicSoundHelper.initialize();

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SoundsReloadListener());

        AtomicReference<List<Identifier>> previousEffects = new AtomicReference<>(null);
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if(client.player == null) return;

            List<Identifier> currentEffects = client.player.getStatusEffects().stream().map(instance -> Registries.STATUS_EFFECT.getId(instance.getEffectType())).toList();

            if(previousEffects.get() != null) {
                Collection<Identifier> removedEffects = new ArrayList<>(previousEffects.get());
                removedEffects.removeAll(currentEffects);

                for (Identifier effect : removedEffects) {
                    StatusEffect statusEffect = Registries.STATUS_EFFECT.get(effect);

                    if (statusEffect == null) continue;

                    LOGGER.info("Removed status effect: " + statusEffect.getTranslationKey());

                    if(statusEffect.isBeneficial()) {
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

                    LOGGER.info("Added status effect: " + statusEffect.getTranslationKey());

                    if(statusEffect.isBeneficial()) {
                        GameplaySoundConfig.get().positiveStatusEffectGainSoundEffect.playSound();
                    } else {
                        GameplaySoundConfig.get().negativeStatusEffectGainSoundEffect.playSound();
                    }
                }
            }

            previousEffects.set(currentEffects);
        });
    }
}
