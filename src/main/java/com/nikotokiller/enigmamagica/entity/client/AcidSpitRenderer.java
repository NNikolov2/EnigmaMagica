package com.nikotokiller.enigmamagica.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.nikotokiller.enigmamagica.EnigmaMagica;
import com.nikotokiller.enigmamagica.entity.custom.AcidSpitProjectileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.joml.Matrix3dStack;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class AcidSpitRenderer extends EntityRenderer<AcidSpitProjectileEntity> {


    public AcidSpitRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(AcidSpitProjectileEntity entity) {
        return new ResourceLocation(EnigmaMagica.MODID, "textures/entity/acid_spit.png");
    }

    public void render(AcidSpitProjectileEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();

        // Apply rotations
        pPoseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.yRotO, pEntity.getYRot()) - 90.0F));
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot())));

        // Scale and translation adjustments
        float scale = 0.1f; // Scale factor for the entity, adjust as needed
        pPoseStack.scale(scale, scale, scale);
        pPoseStack.translate(-20.0F, 0.0F, 0.0F); // Translate along the X-axis to separate textures

        // Vertex consumer to draw the entity
        VertexConsumer vertexConsumer = pBuffer.getBuffer(RenderType.entityCutout(getTextureLocation(pEntity)));
        PoseStack.Pose posestack$pose = pPoseStack.last();
        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();

        float minU = 0.0F; // Minimum U coordinate (left)
        float maxU = 1.0F; // Maximum U coordinate (right)
        float minV = 0.0F; // Minimum V coordinate (bottom)
        float maxV = 1.0F; // Maximum V coordinate (top)

        // Length and width of the projectile
        float length = 8.0F; // Length
        float width = 4.0F;  // Width

        // First plane (aligned with the X axis)
        vertex(matrix4f, matrix3f, vertexConsumer, -length, -width, 0, minU, minV, -1, 0, 0, pPackedLight);
        vertex(matrix4f,matrix3f, vertexConsumer, length, -width, 0, maxU, minV, 1, 0, 0, pPackedLight);
        vertex(matrix4f, matrix3f, vertexConsumer, length, width, 0, maxU, maxV, 1, 0, 0, pPackedLight);
        vertex(matrix4f, matrix3f, vertexConsumer, -length, width, 0, minU, maxV, -1, 0, 0, pPackedLight);

        vertex(matrix4f, matrix3f, vertexConsumer, -length, width, 0, minU, maxV, -1, 0, 0, pPackedLight);
        vertex(matrix4f, matrix3f, vertexConsumer, length, width, 0, maxU, maxV, 1, 0, 0, pPackedLight);
        vertex(matrix4f, matrix3f, vertexConsumer, length, -width, 0, maxU, minV, 1, 0, 0, pPackedLight);
        vertex(matrix4f, matrix3f, vertexConsumer, -length, -width, 0, minU, minV, -1, 0, 0, pPackedLight);

        // Second plane (looks up)
        vertex(matrix4f, matrix3f, vertexConsumer, -length, 0, -width, minU, minV, -1, 0, 0, pPackedLight);
        vertex(matrix4f,matrix3f, vertexConsumer, length, 0, -width, maxU, minV, 1, 0, 0, pPackedLight);
        vertex(matrix4f, matrix3f, vertexConsumer, length, 0, width, maxU, maxV, 1, 0, 0, pPackedLight);
        vertex(matrix4f, matrix3f, vertexConsumer, -length, 0, width, minU, maxV, -1, 0, 0, pPackedLight);

        vertex(matrix4f, matrix3f, vertexConsumer, -length, 0, width, minU, maxV, -1, 0, 0, pPackedLight);
        vertex(matrix4f, matrix3f, vertexConsumer, length, 0, width, maxU, maxV, 1, 0, 0, pPackedLight);
        vertex(matrix4f, matrix3f, vertexConsumer, length, 0, -width, maxU, minV, 1, 0, 0, pPackedLight);
        vertex(matrix4f, matrix3f, vertexConsumer, -length, 0, -width, minU, minV, -1, 0, 0, pPackedLight);



        pPoseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }



    private void vertex(Matrix4f matrix, Matrix3f normalMatrix, VertexConsumer vertexConsumer, float x, float y, float z, float u, float v, int normalX, int normalY, int normalZ, int PackedLight) {
        vertexConsumer.vertex(matrix, x, y, z)
                .color(255, 255, 255, 255)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(PackedLight)
                .normal(normalMatrix, normalX, normalY, normalZ)
                .endVertex();
    }

}
