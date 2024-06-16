package com.nikotokiller.enigmamagica.entity.custom;

import com.nikotokiller.enigmamagica.entity.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;

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
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
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
