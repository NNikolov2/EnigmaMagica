package com.nikotokiller.enigmamagica.effect;

import com.mojang.blaze3d.shaders.Effect;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class PharaohsCurseEffect extends MobEffect {

    private static final UUID MOVEMENT_SPEED_MODIFIER_ID = UUID.fromString("7107DE5E-7CE8-4030-940E-514C1F160890");

    public PharaohsCurseEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        double reductionAmount = -0.50D; // 50% reduction
        AttributeModifier modifier = new AttributeModifier(MOVEMENT_SPEED_MODIFIER_ID, "PharaohsCurseMovementSpeed", reductionAmount, AttributeModifier.Operation.MULTIPLY_TOTAL);
        if (!entity.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(modifier)) {
            entity.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(modifier);
        }
    }

    @Override
    public void removeAttributeModifiers(LivingEntity entityLivingBaseIn, AttributeMap attributeMapIn, int amplifier) {
        super.removeAttributeModifiers(entityLivingBaseIn, attributeMapIn, amplifier);
        entityLivingBaseIn.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(MOVEMENT_SPEED_MODIFIER_ID);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        // Return true to make the effect tick every game tick
        return true;
    }

}
