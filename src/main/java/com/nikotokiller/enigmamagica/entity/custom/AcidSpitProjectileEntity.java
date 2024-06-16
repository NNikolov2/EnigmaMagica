package com.nikotokiller.enigmamagica.entity.custom;

import com.nikotokiller.enigmamagica.entity.ModEntities;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Inherited;
import java.util.Arrays;

public class AcidSpitProjectileEntity extends AbstractAcidSpitProjectile{

    public AcidSpitProjectileEntity(EntityType<AcidSpitProjectileEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public AcidSpitProjectileEntity(Level pLevel, double pX, double pY, double pZ) {
        super(ModEntities.ACID_SPIT_PROJECTILE.get(), pX, pY, pZ, pLevel);
    }

    public AcidSpitProjectileEntity(Level pLevel, LivingEntity pShooter) {
        super(ModEntities.ACID_SPIT_PROJECTILE.get(), pShooter, pLevel);
    }

    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            if (this.inGround) {
                if (this.inGroundTime % 5 == 0) {
                    this.makeParticle(1);
                }
            } else {
                this.makeParticle(2);
            }
        }
    }

    private void makeParticle(int pParticleAmount) {
        int i = 02550;
        if (i != -1 && pParticleAmount > 0) {
            double d0 = (double)(i >> 16 & 255) / 255.0D;
            double d1 = (double)(i >> 8 & 255) / 255.0D;
            double d2 = (double)(i >> 0 & 255) / 255.0D;

            for(int j = 0; j < pParticleAmount; ++j) {
                this.level().addParticle(ParticleTypes.ENTITY_EFFECT, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), d0, d1, d2);
            }

        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    protected ItemStack getPickupItem() {
       return null;
    }

    public void handleEntityEvent(byte pId) {
        if (pId == 0) {
            int i = 02550;
            if (i != -1) {
                double d0 = (double)(i >> 16 & 255) / 255.0D;
                double d1 = (double)(i >> 8 & 255) / 255.0D;
                double d2 = (double)(i >> 0 & 255) / 255.0D;

                for(int j = 0; j < 20; ++j) {
                    this.level().addParticle(ParticleTypes.ENTITY_EFFECT, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), d0, d1, d2);
                }
            }
        } else {
            super.handleEntityEvent(pId);
        }

    }

}
