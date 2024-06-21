package com.nikotokiller.enigmamagica.entity.custom;

import com.nikotokiller.enigmamagica.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.joml.Vector3d;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class DesertCentipedeEntity extends Monster implements RangedAttackMob{

    public final AnimationState idleAnimation = new AnimationState();
    private int idleAnimationTimeout = 0;
    public final AnimationState spitAnimation = new AnimationState();
    public final AnimationState appearAnimation = new AnimationState();
    public final AnimationState disappearAnimation = new AnimationState();

    public DesertCentipedeEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public void tick(){
         super.tick();

         if(this.level().isClientSide){
             SetupAnimationState();
         }
    }

    private void SetupAnimationState(){
        if(this.idleAnimationTimeout <= 0){
            this.idleAnimationTimeout = random.nextInt(40) + 80;
            this.idleAnimation.start(this.tickCount);
        } else{
            --this.idleAnimationTimeout;
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MovePauseGoal(this, 2.0D, 240)); // Custom goal
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.goalSelector.addGoal(2, new RangedAttackGoal(this, 1.0D, 20, 15.0F));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 10f));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20D)
                .add(Attributes.MOVEMENT_SPEED, .25D)
                .add(Attributes.ARMOR_TOUGHNESS, 0.1f)
                .add(Attributes.ATTACK_DAMAGE, 4f)
                .add(Attributes.ATTACK_KNOCKBACK, .2f)
                .add(Attributes.FOLLOW_RANGE, 35f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 100f);
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
           AcidSpitProjectileEntity projectile = new AcidSpitProjectileEntity(ModEntities.ACID_SPIT_PROJECTILE.get(), this.level());


            // Set the initial position of the projectile to be near the mob
            double posX = this.getX();
            double posY = this.getEyeY() - 0.1F; // Starting slightly below the eye level of the mob
            double posZ = this.getZ();
            projectile.setPos(posX, posY, posZ);

            // Set the shooter of the projectile
            projectile.setOwner(this);

            // Calculate direction to the target
            double dx = target.getX() - this.getX();
            double dy = target.getY(0.3333333333333333D) - projectile.getY();
            double dz = target.getZ() - this.getZ();
            double distance = Math.sqrt(dx * dx + dz * dz);

            projectile.shoot(dx, dy + distance * 0.2, dz, 1.6F, (float) (14 - this.level().getDifficulty().getId() * 4));

            // Add the projectile to the world
            this.level().addFreshEntity(projectile);

    }

    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean result = super.hurt(source, amount);
        if (result) {
            this.goalSelector.getAvailableGoals().forEach(wrappedGoal -> {
                Goal goal = wrappedGoal.getGoal();
                if (goal instanceof MovePauseGoal) {
                    ((MovePauseGoal) goal).trigger();
                }
            });
        }
        return super.hurt(source, amount);
    }


    @SubscribeEvent
    public static boolean isDarkEnoughToSpawn(){
        return true;
    }

    // Custom Attribute
    private static class MovePauseGoal extends Goal {
        public static final int DEFAULT_INTERVAL = 240;
        protected final PathfinderMob mob;
        protected double wantedX;
        protected double wantedY;
        protected double wantedZ;
        protected final double speedModifier;
        protected int interval;
        protected boolean forceTrigger;
        private final boolean checkNoActionTime;
        private boolean wasHit = false;
        public static final float PROBABILITY = 0.001F;
        protected final float probability;

        public MovePauseGoal(PathfinderMob pMob, double pSpeedModifier) {
            this(pMob, pSpeedModifier, 120);
        }

        public MovePauseGoal(PathfinderMob pMob, double pSpeedModifier, int pInterval) {
            this(pMob, pSpeedModifier, pInterval, true, 0.001f);
        }

        public MovePauseGoal(PathfinderMob pMob, double pSpeedModifier, int pInterval, boolean pCheckNoActionTime, float pProbability) {
            this.mob = pMob;
            this.speedModifier = pSpeedModifier;
            this.interval = pInterval;
            this.checkNoActionTime = pCheckNoActionTime;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
            this.probability = pProbability;
        }

        @Override
        public boolean canUse() {
            if (this.mob.isVehicle()) {
                return false;
            } else {
                if (!this.forceTrigger) {
                    if (this.checkNoActionTime && this.mob.getNoActionTime() >= 100) {
                        return false;
                    }

                    if (this.mob.getRandom().nextInt(reducedTickDelay(this.interval)) != 0) {
                        return false;
                    }
                }

                Vec3 vec3 = this.getPosition();
                if (vec3 == null) {
                    return false;
                } else {
                    this.wantedX = vec3.x;
                    this.wantedY = vec3.y;
                    this.wantedZ = vec3.z;
                    this.forceTrigger = false;
                    this.wasHit = false;
                    return true;
                }
            }
        }

        @Nullable
        protected Vec3 getPosition() {
            if (this.mob.isInWaterOrBubble()) {
                Vec3 vec3 = LandRandomPos.getPos(this.mob, 20, 7);
                return vec3 == null ? DefaultRandomPos.getPos(this.mob, 15, 7) : vec3;
            } else {
                return this.mob.getRandom().nextFloat() >= probability ? LandRandomPos.getPos(this.mob, 15, 7) : DefaultRandomPos.getPos(this.mob, 15, 7);
            }
        }

        @Override
        public boolean canContinueToUse() {
            return !this.mob.getNavigation().isDone() && !this.mob.isVehicle();
        }

        @Override
        public void start() {
            this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
        }

        @Override
        public void stop() {
            this.mob.getNavigation().stop();
            super.stop();
        }

        public void trigger() {
            this.forceTrigger = true;
            this.wasHit = true;
        }

        public void setInterval(int pNewchance) {
            this.interval = pNewchance;
        }
    }

}
