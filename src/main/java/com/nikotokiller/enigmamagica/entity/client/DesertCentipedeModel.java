package com.nikotokiller.enigmamagica.entity.client;// Made with Blockbench 4.10.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.nikotokiller.enigmamagica.entity.animations.DesertCentipedeAnimation;
import com.nikotokiller.enigmamagica.entity.custom.DesertCentipedeEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class DesertCentipedeModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	private final ModelPart sand;
	private final ModelPart body;
	private final ModelPart partOne;
	private final ModelPart partTwo;
	private final ModelPart partThree;
	private final ModelPart partFour;
	private final ModelPart partFive;
	private final ModelPart head;

	public DesertCentipedeModel(ModelPart root) {
		this.sand = root.getChild("sand");
		this.body = root.getChild("body");
		this.partOne = body.getChild("partOne");
		this.partTwo = body.getChild("partTwo");
		this.partThree = body.getChild("partThree");
		this.partFour = body.getChild("partFour");
		this.partFive = body.getChild("partFive");
		this.head = body.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition sand = partdefinition.addOrReplaceChild("sand", CubeListBuilder.create().texOffs(11, 53).addBox(-4.0F, -2.0F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(11, 53).addBox(-2.0F, -2.0F, 4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(11, 53).addBox(-2.0F, -3.0F, 2.0F, 6.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(12, 54).addBox(-2.01F, -3.0F, -0.01F, 5.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(14, 53).addBox(-5.0F, -3.0F, 0.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(12, 52).addBox(-6.0F, -1.0F, -2.0F, 4.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(13, 53).addBox(1.0F, -1.0F, 1.0F, 4.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(13, 53).addBox(-3.0F, -1.0F, 2.0F, 4.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(11, 53).addBox(-2.0F, -1.0F, -3.0F, 6.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, -1.0F));

		PartDefinition partOne = body.addOrReplaceChild("partOne", CubeListBuilder.create().texOffs(22, 28).addBox(-3.0F, 14.0F, 0.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(39, 18).addBox(2.0F, 14.0F, -3.0F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(28, 36).addBox(-2.0F, 14.0F, -3.0F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition legsright15_r1 = partOne.addOrReplaceChild("legsright15_r1", CubeListBuilder.create().texOffs(39, 18).addBox(-1.65F, -9.0F, -3.25F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(39, 18).addBox(1.75F, -9.0F, -3.25F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 20.0F, 1.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition partTwo = body.addOrReplaceChild("partTwo", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition legsleft25_r1 = partTwo.addOrReplaceChild("legsleft25_r1", CubeListBuilder.create().texOffs(22, 36).addBox(1.75F, -14.25F, -3.25F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(22, 36).addBox(-1.65F, -14.25F, -3.25F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(22, 36).addBox(-2.0F, -12.0F, -3.25F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(34, 36).addBox(2.0F, -12.0F, -3.25F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 20.0F, 1.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition bodypart2_r1 = partTwo.addOrReplaceChild("bodypart2_r1", CubeListBuilder.create().texOffs(0, 23).addBox(-2.99F, -2.5522F, -2.3916F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 11.0F, 2.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition partThree = body.addOrReplaceChild("partThree", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition legsright3_r1 = partThree.addOrReplaceChild("legsright3_r1", CubeListBuilder.create().texOffs(0, 40).addBox(-2.0F, -16.0F, -2.75F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(12, 40).addBox(2.0F, -16.0F, -2.75F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 20.0F, 1.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition bodypart3_r1 = partThree.addOrReplaceChild("bodypart3_r1", CubeListBuilder.create().texOffs(22, 0).addBox(-3.0F, -2.3079F, -3.5033F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 2.0F, 0.2182F, 0.0F, 0.0F));

		PartDefinition partFour = body.addOrReplaceChild("partFour", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition legsleft4_r1 = partFour.addOrReplaceChild("legsleft4_r1", CubeListBuilder.create().texOffs(6, 40).addBox(2.0F, -19.0F, 1.25F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(39, 24).addBox(-2.0F, -19.0F, 1.25F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 20.0F, 1.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition bodypart4_r1 = partFour.addOrReplaceChild("bodypart4_r1", CubeListBuilder.create().texOffs(0, 11).addBox(-2.99F, -2.2435F, -3.9829F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6109F, 0.0F, 0.0F));

		PartDefinition partFive = body.addOrReplaceChild("partFive", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bodyoart5_r1 = partFive.addOrReplaceChild("bodyoart5_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.0726F, -4.8006F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, -4.0F, 1.0908F, 0.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 34).addBox(-3.5F, -3.25F, -2.5F, 7.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(34, 12).addBox(2.5F, 1.5F, -2.5F, 0.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(25, 7).addBox(2.5F, -7.5F, -2.5F, 0.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(33, 7).addBox(-2.5F, 1.5F, -2.5F, 0.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(17, 7).addBox(-2.5F, -7.5F, -2.5F, 0.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -7.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.animate(((DesertCentipedeEntity) entity).idleAnimation, DesertCentipedeAnimation.passive, ageInTicks, 1f);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		sand.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return body;
	}
}