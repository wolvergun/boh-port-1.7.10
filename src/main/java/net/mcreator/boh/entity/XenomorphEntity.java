package net.mcreator.boh.entity;

import java.util.Objects;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.entity.BohAreaEffectCloud;
import net.mcreator.boh.compat.entity.BohMonster;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataAccessor;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataSerializers;
import net.mcreator.boh.compat.mc.network.syncher.SynchedEntityData;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.world.damagesource.DamageTypes;
import net.mcreator.boh.compat.mc.world.entity.EntityDimensions;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.MobType;
import net.mcreator.boh.compat.mc.world.entity.Pose;
import net.mcreator.boh.compat.mc.world.entity.RemovalReason;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Attributes;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Builder;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.FloatGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.MeleeAttackGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.RandomLookAroundGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.RandomStrollGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.target.HurtByTargetGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.geo.AnimatableInstanceCache;
import net.mcreator.boh.geo.AnimatableManager;
import net.mcreator.boh.geo.AnimationController;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.GeckoLibUtil;
import net.mcreator.boh.geo.GeoEntity;
import net.mcreator.boh.geo.PlayState;
import net.mcreator.boh.geo.RawAnimation;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.procedures.AngerRangeXenomorphProcedure;
import net.mcreator.boh.procedures.InHeadFacehuggerProcedure;
import net.mcreator.boh.procedures.XenomorphEntityIsHurtProcedure;
import net.mcreator.boh.procedures.XenomorphOnEntityTickUpdateProcedure;
import net.mcreator.boh.procedures.XenomorphThisEntityKillsAnotherOneProcedure;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class XenomorphEntity extends BohMonster implements GeoEntity {
    public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(XenomorphEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(XenomorphEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(XenomorphEntity.class, EntityDataSerializers.STRING);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean swinging;
    private boolean lastloop;
    private long lastSwing;
    public String animationprocedure = "empty";
    String prevAnim = "empty";

    public XenomorphEntity(World world) {
        this(BohModEntities.XENOMORPH.get(), world);
    }

    public XenomorphEntity(EntityType<XenomorphEntity> type, World world) {
        super(type, world);
        M.set_xpReward(this, 13);
        M.setNoAi(this, false);
        M.setMaxUpStep(this, 0.6F);
        M.setPersistenceRequired(this);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHOOT, false);
        this.entityData.define(ANIMATION, "undefined");
        this.entityData.define(TEXTURE, "xenomorph");
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
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, true) {
            {
                Objects.requireNonNull(XenomorphEntity.this);
            }

            @Override
            protected double getAttackReachSqr(EntityLivingBase entity) {
                return M.getBbWidth(this.mob) * M.getBbWidth(this.mob) + M.getBbWidth(entity);
            }

            @Override
            public boolean canUse() {
                double x = M.getX(XenomorphEntity.this);
                double y = M.getY(XenomorphEntity.this);
                double z = M.getZ(XenomorphEntity.this);
                Entity entity = XenomorphEntity.this;
                World world = M.level(XenomorphEntity.this);
                return super.canUse() && InHeadFacehuggerProcedure.execute(entity);
            }

            @Override
            public boolean canContinueToUse() {
                double x = M.getX(XenomorphEntity.this);
                double y = M.getY(XenomorphEntity.this);
                double z = M.getZ(XenomorphEntity.this);
                Entity entity = XenomorphEntity.this;
                World world = M.level(XenomorphEntity.this);
                return super.canContinueToUse() && InHeadFacehuggerProcedure.execute(entity);
            }
        });
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, EntityPlayer.class, false, false) {
            {
                Objects.requireNonNull(XenomorphEntity.this);
            }

            @Override
            public boolean canUse() {
                double x = M.getX(XenomorphEntity.this);
                double y = M.getY(XenomorphEntity.this);
                double z = M.getZ(XenomorphEntity.this);
                Entity entity = XenomorphEntity.this;
                World world = M.level(XenomorphEntity.this);
                return super.canUse() && AngerRangeXenomorphProcedure.execute(world, x, y, z, entity);
            }

            @Override
            public boolean canContinueToUse() {
                double x = M.getX(XenomorphEntity.this);
                double y = M.getY(XenomorphEntity.this);
                double z = M.getZ(XenomorphEntity.this);
                Entity entity = XenomorphEntity.this;
                World world = M.level(XenomorphEntity.this);
                return super.canContinueToUse() && AngerRangeXenomorphProcedure.execute(world, x, y, z, entity);
            }
        });
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, PredatorEntity.class, false, false) {
            {
                Objects.requireNonNull(XenomorphEntity.this);
            }

            @Override
            public boolean canUse() {
                double x = M.getX(XenomorphEntity.this);
                double y = M.getY(XenomorphEntity.this);
                double z = M.getZ(XenomorphEntity.this);
                Entity entity = XenomorphEntity.this;
                World world = M.level(XenomorphEntity.this);
                return super.canUse() && AngerRangeXenomorphProcedure.execute(world, x, y, z, entity);
            }

            @Override
            public boolean canContinueToUse() {
                double x = M.getX(XenomorphEntity.this);
                double y = M.getY(XenomorphEntity.this);
                double z = M.getZ(XenomorphEntity.this);
                Entity entity = XenomorphEntity.this;
                World world = M.level(XenomorphEntity.this);
                return super.canContinueToUse() && AngerRangeXenomorphProcedure.execute(world, x, y, z, entity);
            }
        });
        this.targetSelector.addGoal(4, new HurtByTargetGoal(this).setAlertOthers());
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new FloatGoal(this));
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
    public SoundEvent getAmbientSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:xenomorph_idle"));
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        M.playSound(this, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:xenomorph_step")), 0.15F, 1.0F);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:xenomorph_hurt"));
    }

    @Override
    public SoundEvent getDeathSoundEvent() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:xenomorph_death"));
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        XenomorphEntityIsHurtProcedure.execute(M.level(this), this);
        if (M.getDirectEntity(source) instanceof EntityPotion || M.getDirectEntity(source) instanceof BohAreaEffectCloud) {
            return false;
        } else if (M.is(source, DamageTypes.FALL)) {
            return false;
        } else {
            return M.is(source, DamageTypes.CACTUS) ? false : super.hurt(source, amount);
        }
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
        XenomorphThisEntityKillsAnotherOneProcedure.execute(M.level(this), M.getX(this), M.getY(this), M.getZ(this));
    }

    @Override
    public void baseTick() {
        super.baseTick();
        XenomorphOnEntityTickUpdateProcedure.execute(this);
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
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.4);
        builder = builder.add(Attributes.MAX_HEALTH, 75.0);
        builder = builder.add(Attributes.ARMOR, 10.0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 8.0);
        return builder.add(Attributes.FOLLOW_RANGE, 90.0);
    }

    private PlayState movementPredicate(AnimationState event) {
        if (!this.animationprocedure.equals("empty")) {
            return PlayState.STOP;
        } else if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.15F) || !(event.getLimbSwingAmount() < 0.15F)) && !M.isAggressive(this)) {
            return event.setAndContinue(RawAnimation.begin().thenLoop("walk"));
        } else {
            return M.isAggressive(this) && event.isMoving()
                ? event.setAndContinue(RawAnimation.begin().thenLoop("aggro"))
                : event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
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
        data.add(new AnimationController<>(this, "procedure", 4, this::procedurePredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
