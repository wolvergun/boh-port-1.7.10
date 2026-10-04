package net.mcreator.boh.entity;

import java.util.EnumSet;
import java.util.Objects;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.entity.BohMonster;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataAccessor;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataSerializers;
import net.mcreator.boh.compat.mc.network.syncher.SynchedEntityData;
import net.mcreator.boh.compat.mc.util.Mth;
import net.mcreator.boh.compat.mc.world.damagesource.DamageTypes;
import net.mcreator.boh.compat.mc.world.entity.EntityDimensions;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.MobType;
import net.mcreator.boh.compat.mc.world.entity.Pose;
import net.mcreator.boh.compat.mc.world.entity.RemovalReason;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Attributes;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Builder;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.Flag;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.FloatGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.Goal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.LookAtPlayerGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.PanicGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.RandomLookAroundGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.RandomStrollGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.target.HurtByTargetGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.mcreator.boh.compat.mc.world.entity.monster.RangedAttackMob;
import net.mcreator.boh.geo.AnimatableInstanceCache;
import net.mcreator.boh.geo.AnimatableManager;
import net.mcreator.boh.geo.AnimationController;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.GeckoLibUtil;
import net.mcreator.boh.geo.GeoEntity;
import net.mcreator.boh.geo.PlayState;
import net.mcreator.boh.geo.RawAnimation;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class KirieHimuroEntity extends BohMonster implements RangedAttackMob, GeoEntity {
    public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(KirieHimuroEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(KirieHimuroEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(KirieHimuroEntity.class, EntityDataSerializers.STRING);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean swinging;
    private boolean lastloop;
    private long lastSwing;
    public String animationprocedure = "empty";
    String prevAnim = "empty";

    public KirieHimuroEntity(World world) {
        this(BohModEntities.KIRIE_HIMURO.get(), world);
    }

    public KirieHimuroEntity(EntityType<KirieHimuroEntity> type, World world) {
        super(type, world);
        M.set_xpReward(this, 5);
        M.setNoAi(this, false);
        M.setMaxUpStep(this, 0.6F);
        M.setPersistenceRequired(this);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHOOT, false);
        this.entityData.define(ANIMATION, "undefined");
        this.entityData.define(TEXTURE, "kirie");
    }

    public void setTexture(String texture) {
        M.set(this.entityData, TEXTURE, texture);
    }

    public String getTexture() {
        return this.entityData.get(TEXTURE);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, EntityPlayer.class, false, false));
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.2));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, EntityPlayer.class, 32.0F));
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1.0));
        this.targetSelector.addGoal(5, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new FloatGoal(this));
        this.goalSelector.addGoal(1, new KirieHimuroEntity.RangedAttackGoal(this, 1.25, 40, 10.0F) {
            {
                Objects.requireNonNull(KirieHimuroEntity.this);
            }

            @Override
            public boolean canContinueToUse() {
                return this.canUse();
            }
        });
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return M.is(source, DamageTypes.FALL) ? false : super.hurt(source, amount);
    }

    @Override
    public void addAdditionalSaveData(NBTTagCompound compound) {
        super.addAdditionalSaveData(compound);
        M.putString(compound, "Texture", this.getTexture());
    }

    @Override
    public void readAdditionalSaveData(NBTTagCompound compound) {
        super.readAdditionalSaveData(compound);
        if (M.contains(compound, "Texture")) {
            this.setTexture(M.getString(compound, "Texture"));
        }
    }

    @Override
    public void baseTick() {
        super.baseTick();
        M.refreshDimensions(this);
    }

    @Override
    public EntityDimensions getDimensions(Pose p_33597_) {
        return super.getDimensions(p_33597_).scale(1.0F);
    }

    @Override
    public void performRangedAttack(EntityLivingBase target, float flval) {
        RayGunProjectileProjectileEntity.shoot(this, target);
    }

    public static void init() {
    }

    public static Builder createAttributes() {
        Builder builder = M.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
        builder = builder.add(Attributes.MAX_HEALTH, 75.0);
        builder = builder.add(Attributes.ARMOR, 0.0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0.0);
        return builder.add(Attributes.FOLLOW_RANGE, 32.0);
    }

    private PlayState movementPredicate(AnimationState event) {
        if (!this.animationprocedure.equals("empty")) {
            return PlayState.STOP;
        } else {
            return !event.isMoving() && event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F
                ? event.setAndContinue(RawAnimation.begin().thenLoop("idle"))
                : event.setAndContinue(RawAnimation.begin().thenLoop("walk"));
        }
    }

    private PlayState attackingPredicate(AnimationState event) {
        double d1 = M.getX(this) - M.xOld(this);
        double d0 = M.getZ(this) - M.zOld(this);
        float velocity = (float)Math.sqrt(d1 * d1 + d0 * d0);
        if (M.getAttackAnim(this, event.getPartialTick()) > 0.0F && !this.swinging) {
            this.swinging = true;
            this.lastSwing = M.getGameTime(M.level(this));
        }

        if (this.swinging && this.lastSwing + 7L <= M.getGameTime(M.level(this))) {
            this.swinging = false;
        }

        if ((this.swinging || this.entityData.get(SHOOT)) && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
            event.getController().forceAnimationReset();
            return event.setAndContinue(RawAnimation.begin().thenPlay("attack"));
        } else {
            return PlayState.CONTINUE;
        }
    }

    private PlayState procedurePredicate(AnimationState event) {
        if (!this.animationprocedure.equals("empty") && event.getController().getAnimationState() == AnimationController.State.STOPPED
            || !this.animationprocedure.equals(this.prevAnim) && !this.animationprocedure.equals("empty")) {
            if (!this.animationprocedure.equals(this.prevAnim)) {
                event.getController().forceAnimationReset();
            }

            event.getController().setAnimation(RawAnimation.begin().thenPlay(this.animationprocedure));
            if (event.getController().getAnimationState() == AnimationController.State.STOPPED) {
                this.animationprocedure = "empty";
                event.getController().forceAnimationReset();
            }
        } else if (this.animationprocedure.equals("empty")) {
            this.prevAnim = "empty";
            return PlayState.STOP;
        }

        this.prevAnim = this.animationprocedure;
        return PlayState.CONTINUE;
    }

    @Override
    protected void tickDeath() {
        this.deathTime++;
        if (this.deathTime == 20) {
            M.remove(this, RemovalReason.KILLED);
            M.dropExperience(this);
        }
    }

    public String getSyncedAnimation() {
        return this.entityData.get(ANIMATION);
    }

    public void setAnimation(String animation) {
        M.set(this.entityData, ANIMATION, animation);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "movement", 4, this::movementPredicate));
        data.add(new AnimationController<>(this, "attacking", 4, this::attackingPredicate));
        data.add(new AnimationController<>(this, "procedure", 4, this::procedurePredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public class RangedAttackGoal extends Goal {
        private final EntityLiving mob;
        private final RangedAttackMob rangedAttackMob;
        @Nullable
        private EntityLivingBase target;
        private int attackTime;
        private final double speedModifier;
        private int seeTime;
        private final int attackIntervalMin;
        private final int attackIntervalMax;
        private final float attackRadius;
        private final float attackRadiusSqr;

        public RangedAttackGoal(RangedAttackMob p_25768_, double p_25769_, int p_25770_, float p_25771_) {
            this(p_25768_, p_25769_, p_25770_, p_25770_, p_25771_);
        }

        public RangedAttackGoal(RangedAttackMob p_25773_, double p_25774_, int p_25775_, int p_25776_, float p_25777_) {
            Objects.requireNonNull(KirieHimuroEntity.this);
            super();
            this.attackTime = -1;
            if (!(p_25773_ instanceof EntityLivingBase)) {
                throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
            } else {
                this.rangedAttackMob = p_25773_;
                this.mob = (EntityLiving)p_25773_;
                this.speedModifier = p_25774_;
                this.attackIntervalMin = p_25775_;
                this.attackIntervalMax = p_25776_;
                this.attackRadius = p_25777_;
                this.attackRadiusSqr = p_25777_ * p_25777_;
                this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
            }
        }

        @Override
        public boolean canUse() {
            EntityLivingBase livingentity = M.getTarget(this.mob);
            if (livingentity != null && M.isAlive(livingentity)) {
                this.target = livingentity;
                return true;
            } else {
                return false;
            }
        }

        @Override
        public boolean canContinueToUse() {
            return this.canUse() || M.isAlive(this.target) && !M.isDone(M.getNavigation(this.mob));
        }

        @Override
        public void stop() {
            this.target = null;
            this.seeTime = 0;
            this.attackTime = -1;
            M.set(((KirieHimuroEntity)this.rangedAttackMob).entityData, KirieHimuroEntity.SHOOT, false);
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            double d0 = M.distanceToSqr(this.mob, M.getX(this.target), M.getY(this.target), M.getZ(this.target));
            boolean flag = M.hasLineOfSight(M.getSensing(this.mob), this.target);
            if (flag) {
                this.seeTime++;
            } else {
                this.seeTime = 0;
            }

            if (!(d0 > this.attackRadiusSqr) && this.seeTime >= 5) {
                M.stop(M.getNavigation(this.mob));
            } else {
                M.moveTo(M.getNavigation(this.mob), this.target, this.speedModifier);
            }

            M.getLookControl(this.mob).setLookAt(this.target, 30.0F, 30.0F);
            if (--this.attackTime == 0) {
                if (!flag) {
                    M.set(((KirieHimuroEntity)this.rangedAttackMob).entityData, KirieHimuroEntity.SHOOT, false);
                    return;
                }

                M.set(((KirieHimuroEntity)this.rangedAttackMob).entityData, KirieHimuroEntity.SHOOT, true);
                float f = (float)Math.sqrt(d0) / this.attackRadius;
                float f1 = Mth.clamp(f, 0.1F, 1.0F);
                M.performRangedAttack(this.rangedAttackMob, this.target, f1);
                this.attackTime = Mth.floor(f * (this.attackIntervalMax - this.attackIntervalMin) + this.attackIntervalMin);
            } else if (this.attackTime < 0) {
                this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(d0) / this.attackRadius, (double)this.attackIntervalMin, (double)this.attackIntervalMax));
            } else {
                M.set(((KirieHimuroEntity)this.rangedAttackMob).entityData, KirieHimuroEntity.SHOOT, false);
            }
        }
    }
}
