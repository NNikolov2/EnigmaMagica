package com.nikotokiller.enigmamagica.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.nikotokiller.enigmamagica.EnigmaMagica;
import com.nikotokiller.enigmamagica.entity.custom.DesertCentipedeEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DesertCentipedeRenderer extends MobRenderer<DesertCentipedeEntity, DesertCentipedeModel<DesertCentipedeEntity>> {

    public DesertCentipedeRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new DesertCentipedeModel<>(pContext.bakeLayer(ModModelLayers.DESERT_CENTIPEDE_LAYER)), 0.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(DesertCentipedeEntity pEntity) {
        return new ResourceLocation(EnigmaMagica.MODID, "textures/entity/sandworm_texture.png");
    }

    @Override
    public void render(DesertCentipedeEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.scale(2f,2f,2f);
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
}
