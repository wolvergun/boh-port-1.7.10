package net.mcreator.boh.entity;

import java.util.Objects;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.entity.BohMonster;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataAccessor;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataSerializers;
import net.mcreator.boh.compat.mc.network.syncher.SynchedEntityData;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.world.entity.EntityDimensions;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.MobType;
import net.mcreator.boh.compat.mc.world.entity.Pose;
import net.mcreator.boh.compat.mc.world.entity.RemovalReason;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Attributes;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Builder;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.MeleeAttackGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.RandomLookAroundGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.RandomStrollGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.target.HurtByTargetGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.mcreator.boh.geo.AnimatableInstanceCache;
import net.mcreator.boh.geo.AnimatableManager;
import net.mcreator.boh.geo.AnimationController;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.GeckoLibUtil;
import net.mcreator.boh.geo.GeoEntity;
import net.mcreator.boh.geo.PlayState;
import net.mcreator.boh.geo.RawAnimation;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.procedures.SotirisMoveAIProcedure;
import net.mcreator.boh.procedures.SotirisOnEntityTickUpdateProcedure;
import net.mcreator.boh.procedures.SotirisThisEntityKillsAnotherOneProcedure;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class SotirisEntity extends BohMonster implements GeoEntity {
    public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(SotirisEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(SotirisEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(SotirisEntity.class, EntityDataSerializers.STRING);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean swinging;
    private boolean lastloop;
    private long lastSwing;
    public String animationprocedure = "empty";
    String prevAnim = "empty";

    public SotirisEntity(World world) {
        this(BohModEntities.SOTIRIS.get(), world);
    }

    public SotirisEntity(EntityType<SotirisEntity> type, World world) {
        super(type, world);
        M.set_xpReward(this, 0);
        M.setNoAi(this, false);
        M.setMaxUpStep(this, 0.6F);
        M.setPersistenceRequired(this);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHOOT, false);
        this.entityData.define(ANIMATION, "undefined");
        this.entityData.define(TEXTURE, "soteyeless");
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
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, EntityPlayer.class, false, false) {
            {
                Objects.requireNonNull(SotirisEntity.this);
            }

            @Override
            public boolean canUse() {
                double x = M.getX(SotirisEntity.this);
                double y = M.getY(SotirisEntity.this);
                double z = M.getZ(SotirisEntity.this);
                Entity entity = SotirisEntity.this;
                World world = M.level(SotirisEntity.this);
                return super.canUse() && SotirisMoveAIProcedure.execute(entity);
            }

            @Override
            public boolean canContinueToUse() {
                double x = M.getX(SotirisEntity.this);
                double y = M.getY(SotirisEntity.this);
                double z = M.getZ(SotirisEntity.this);
                Entity entity = SotirisEntity.this;
                World world = M.level(SotirisEntity.this);
                return super.canContinueToUse() && SotirisMoveAIProcedure.execute(entity);
            }
        });
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2, false) {
            {
                Objects.requireNonNull(SotirisEntity.this);
            }

            @Override
            protected double getAttackReachSqr(EntityLivingBase entity) {
                return 6.25;
            }

            @Override
            public boolean canUse() {
                double x = M.getX(SotirisEntity.this);
                double y = M.getY(SotirisEntity.this);
                double z = M.getZ(SotirisEntity.this);
                Entity entity = SotirisEntity.this;
                World world = M.level(SotirisEntity.this);
                return super.canUse() && SotirisMoveAIProcedure.execute(entity);
            }

            @Override
            public boolean canContinueToUse() {
                double x = M.getX(SotirisEntity.this);
                double y = M.getY(SotirisEntity.this);
                double z = M.getZ(SotirisEntity.this);
                Entity entity = SotirisEntity.this;
                World world = M.level(SotirisEntity.this);
                return super.canContinueToUse() && SotirisMoveAIProcedure.execute(entity);
            }
        });
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this) {
            {
                Objects.requireNonNull(SotirisEntity.this);
            }

            @Override
            public boolean canUse() {
                double x = M.getX(SotirisEntity.this);
                double y = M.getY(SotirisEntity.this);
                double z = M.getZ(SotirisEntity.this);
                Entity entity = SotirisEntity.this;
                World world = M.level(SotirisEntity.this);
                return super.canUse() && SotirisMoveAIProcedure.execute(entity);
            }

            @Override
            public boolean canContinueToUse() {
                double x = M.getX(SotirisEntity.this);
                double y = M.getY(SotirisEntity.this);
                double z = M.getZ(SotirisEntity.this);
                Entity entity = SotirisEntity.this;
                World world = M.level(SotirisEntity.this);
                return super.canContinueToUse() && SotirisMoveAIProcedure.execute(entity);
            }
        });
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 0.8) {
            {
                Objects.requireNonNull(SotirisEntity.this);
            }

            @Override
            public boolean canUse() {
                double x = M.getX(SotirisEntity.this);
                double y = M.getY(SotirisEntity.this);
                double z = M.getZ(SotirisEntity.this);
                Entity entity = SotirisEntity.this;
                World world = M.level(SotirisEntity.this);
                return super.canUse() && SotirisMoveAIProcedure.execute(entity);
            }

            @Override
            public boolean canContinueToUse() {
                double x = M.getX(SotirisEntity.this);
                double y = M.getY(SotirisEntity.this);
                double z = M.getZ(SotirisEntity.this);
                Entity entity = SotirisEntity.this;
                World world = M.level(SotirisEntity.this);
                return super.canContinueToUse() && SotirisMoveAIProcedure.execute(entity);
            }
        });
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this) {
            {
                Objects.requireNonNull(SotirisEntity.this);
            }

            @Override
            public boolean canUse() {
                double x = M.getX(SotirisEntity.this);
                double y = M.getY(SotirisEntity.this);
                double z = M.getZ(SotirisEntity.this);
                Entity entity = SotirisEntity.this;
                World world = M.level(SotirisEntity.this);
                return super.canUse() && SotirisMoveAIProcedure.execute(entity);
            }

            @Override
            public boolean canContinueToUse() {
                double x = M.getX(SotirisEntity.this);
                double y = M.getY(SotirisEntity.this);
                double z = M.getZ(SotirisEntity.this);
                Entity entity = SotirisEntity.this;
                World world = M.level(SotirisEntity.this);
                return super.canContinueToUse() && SotirisMoveAIProcedure.execute(entity);
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
    public SoundEvent getHurtSound(DamageSource ds) {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
    }

    @Override
    public SoundEvent getDeathSoundEvent() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
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
    public void awardKillScore(Entity entity, int score, DamageSource damageSource) {
        super.awardKillScore(entity, score, damageSource);
        SotirisThisEntityKillsAnotherOneProcedure.execute(M.level(this), M.getX(this), M.getY(this), M.getZ(this), this);
    }

    @Override
    public void baseTick() {
        super.baseTick();
        SotirisOnEntityTickUpdateProcedure.execute(this);
        M.refreshDimensions(this);
    }

    @Override
    public EntityDimensions getDimensions(Pose p_33597_) {
        return super.getDimensions(p_33597_).scale(1.0F);
    }

    public static void init() {
    }

    public static Builder createAttributes() {
        Builder builder = M.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.0);
        builder = builder.add(Attributes.MAX_HEALTH, 60.0);
        builder = builder.add(Attributes.ARMOR, 0.0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 99.0);
        builder = builder.add(Attributes.FOLLOW_RANGE, 16.0);
        return builder.add(Attributes.KNOCKBACK_RESISTANCE, 100.0);
    }

    private PlayState movementPredicate(AnimationState event) {
        return this.animationprocedure.equals("empty") ? event.setAndContinue(RawAnimation.begin().thenLoop("idle")) : PlayState.STOP;
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
        data.add(new AnimationController<>(this, "procedure", 4, this::procedurePredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
