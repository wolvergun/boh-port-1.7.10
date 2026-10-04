package net.mcreator.boh.entity;

import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.procedures.AngerRangeXenomorphProcedure;
import net.mcreator.boh.procedures.InHeadFacehuggerProcedure;
import net.mcreator.boh.procedures.RunFromLightProcedure;
import net.mcreator.boh.procedures.XenomorphEntityIsHurtProcedure;
import net.minecraft.nbt.NBTTagCompound;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataAccessor;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataSerializers;
import net.mcreator.boh.compat.mc.network.syncher.SynchedEntityData;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.minecraft.util.DamageSource;
import net.mcreator.boh.compat.mc.world.damagesource.DamageTypes;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EntityDimensions;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.minecraft.entity.EntityLivingBase;
import net.mcreator.boh.compat.mc.world.entity.MobType;
import net.mcreator.boh.compat.mc.world.entity.Pose;
import net.mcreator.boh.compat.mc.world.entity.RemovalReason;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Attributes;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Builder;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.AvoidEntityGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.MeleeAttackGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.MoveBackToVillageGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.RandomLookAroundGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.RandomStrollGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.target.HurtByTargetGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.geo.GeoEntity;
import net.mcreator.boh.geo.AnimatableInstanceCache;
import net.mcreator.boh.geo.AnimationController;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.RawAnimation;
import net.mcreator.boh.geo.AnimatableManager.ControllerRegistrar;
import net.mcreator.boh.geo.AnimationController.State;
import net.mcreator.boh.geo.PlayState;
import net.mcreator.boh.geo.GeckoLibUtil;
import net.mcreator.boh.compat.entity.BohSpider;
import net.mcreator.boh.compat.M;

public class FacehuggerEntity extends BohSpider implements GeoEntity {

    public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(FacehuggerEntity.class, EntityDataSerializers.BOOLEAN);

    public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(FacehuggerEntity.class, EntityDataSerializers.STRING);

    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(FacehuggerEntity.class, EntityDataSerializers.STRING);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private boolean swinging;

    private boolean lastloop;

    private long lastSwing;

    public String animationprocedure = "empty";

    String prevAnim = "empty";

    public FacehuggerEntity(World world) {
        this((EntityType<FacehuggerEntity>) BohModEntities.FACEHUGGER.get(), world);
    }

    public FacehuggerEntity(EntityType<FacehuggerEntity> type, World world) {
        super(type, world);
        M.set_xpReward(this, 5);
        M.setNoAi(this, false);
        M.setMaxUpStep(this, 0.6F);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHOOT, false);
        this.entityData.define(ANIMATION, "undefined");
        this.entityData.define(TEXTURE, "facehugger");
    }

    public void setTexture(String texture) {
        M.set(this.entityData, TEXTURE, texture);
    }

    public String getTexture() {
        return (String) this.entityData.get(TEXTURE);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MoveBackToVillageGoal(this, 0.6, false));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2, true) {

            protected double getAttackReachSqr(EntityLivingBase entity) {
                return M.getBbWidth(this.mob) * M.getBbWidth(this.mob) + M.getBbWidth(entity);
            }

            public boolean canUse() {
                double x = M.getX(FacehuggerEntity.this);
                double y = M.getY(FacehuggerEntity.this);
                double z = M.getZ(FacehuggerEntity.this);
                Entity entity = FacehuggerEntity.this;
                World world = M.level(FacehuggerEntity.this);
                return super.canUse() && InHeadFacehuggerProcedure.execute(entity);
            }

            public boolean canContinueToUse() {
                double x = M.getX(FacehuggerEntity.this);
                double y = M.getY(FacehuggerEntity.this);
                double z = M.getZ(FacehuggerEntity.this);
                Entity entity = FacehuggerEntity.this;
                World world = M.level(FacehuggerEntity.this);
                return super.canContinueToUse() && InHeadFacehuggerProcedure.execute(entity);
            }
        });
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this, new Class[0]).setAlertOthers(new Class[0]));
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal(this, EntityPlayer.class, false, false) {

            public boolean canUse() {
                double x = M.getX(FacehuggerEntity.this);
                double y = M.getY(FacehuggerEntity.this);
                double z = M.getZ(FacehuggerEntity.this);
                Entity entity = FacehuggerEntity.this;
                World world = M.level(FacehuggerEntity.this);
                return super.canUse() && AngerRangeXenomorphProcedure.execute(world, x, y, z, entity);
            }

            public boolean canContinueToUse() {
                double x = M.getX(FacehuggerEntity.this);
                double y = M.getY(FacehuggerEntity.this);
                double z = M.getZ(FacehuggerEntity.this);
                Entity entity = FacehuggerEntity.this;
                World world = M.level(FacehuggerEntity.this);
                return super.canContinueToUse() && AngerRangeXenomorphProcedure.execute(world, x, y, z, entity);
            }
        });
        this.goalSelector.addGoal(7, new AvoidEntityGoal<EntityPlayer>(this, EntityPlayer.class, 6.0F, 1.0, 1.2) {

            public boolean canUse() {
                double x = M.getX(FacehuggerEntity.this);
                double y = M.getY(FacehuggerEntity.this);
                double z = M.getZ(FacehuggerEntity.this);
                Entity entity = FacehuggerEntity.this;
                World world = M.level(FacehuggerEntity.this);
                return super.canUse() && RunFromLightProcedure.execute(world, x, y, z);
            }
        });
    }

    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    public SoundEvent getAmbientSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:facehugger_idle"));
    }

    public SoundEvent getHurtSound(DamageSource ds) {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:facehugger_hurt"));
    }

    public SoundEvent getDeathSoundEvent() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:facehugger_death"));
    }

    public boolean hurt(DamageSource source, float amount) {
        XenomorphEntityIsHurtProcedure.execute(M.level(this), this);
        return M.is(source, DamageTypes.FALL) ? false : super.hurt(source, amount);
    }

    public void addAdditionalSaveData(NBTTagCompound compound) {
        super.addAdditionalSaveData(compound);
        M.putString(compound, "Texture", this.getTexture());
    }

    public void readAdditionalSaveData(NBTTagCompound compound) {
        super.readAdditionalSaveData(compound);
        if (M.contains(compound, "Texture")) {
            this.setTexture(M.getString(compound, "Texture"));
        }
    }

    public void baseTick() {
        super.baseTick();
        M.refreshDimensions(this);
    }

    public EntityDimensions getDimensions(Pose p_33597_) {
        return super.getDimensions(p_33597_).scale(0.9F);
    }

    public void aiStep() {
        super.aiStep();
        M.updateSwingTime(this);
    }

    public static void init() {
    }

    public static Builder createAttributes() {
        Builder builder = M.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.5);
        builder = builder.add(Attributes.MAX_HEALTH, 8.0);
        builder = builder.add(Attributes.ARMOR, 0.0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 1.0);
        return builder.add(Attributes.FOLLOW_RANGE, 32.0);
    }

    private PlayState movementPredicate(AnimationState event) {
        if (this.animationprocedure.equals("empty")) {
            if (event.isMoving() || !(event.getLimbSwingAmount() > -0.15F) || !(event.getLimbSwingAmount() < 0.15F)) {
                return event.setAndContinue(RawAnimation.begin().thenLoop("walk"));
            } else {
                return M.isDeadOrDying(this) ? event.setAndContinue(RawAnimation.begin().thenPlay("death")) : event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
            }
        } else {
            return PlayState.STOP;
        }
    }

    private PlayState attackingPredicate(AnimationState event) {
        double d1 = M.getX(this) - M.xOld(this);
        double d0 = M.getZ(this) - M.zOld(this);
        float velocity = (float) Math.sqrt(d1 * d1 + d0 * d0);
        if (M.getAttackAnim(this, event.getPartialTick()) > 0.0F && !this.swinging) {
            this.swinging = true;
            this.lastSwing = M.getGameTime(M.level(this));
        }
        if (this.swinging && this.lastSwing + 7L <= M.getGameTime(M.level(this))) {
            this.swinging = false;
        }
        if (this.swinging && event.getController().getAnimationState() == State.STOPPED) {
            event.getController().forceAnimationReset();
            return event.setAndContinue(RawAnimation.begin().thenPlay("attack"));
        } else {
            return PlayState.CONTINUE;
        }
    }

    private PlayState procedurePredicate(AnimationState event) {
        if (!this.animationprocedure.equals("empty") && event.getController().getAnimationState() == State.STOPPED || !this.animationprocedure.equals(this.prevAnim) && !this.animationprocedure.equals("empty")) {
            if (!this.animationprocedure.equals(this.prevAnim)) {
                event.getController().forceAnimationReset();
            }
            event.getController().setAnimation(RawAnimation.begin().thenPlay(this.animationprocedure));
            if (event.getController().getAnimationState() == State.STOPPED) {
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

    protected void tickDeath() {
        this.deathTime++;
        if (this.deathTime == 20) {
            M.remove(this, RemovalReason.KILLED);
            M.dropExperience(this);
        }
    }

    public String getSyncedAnimation() {
        return (String) this.entityData.get(ANIMATION);
    }

    public void setAnimation(String animation) {
        M.set(this.entityData, ANIMATION, animation);
    }

    public void registerControllers(ControllerRegistrar data) {
        data.add(new AnimationController[] { new AnimationController(this, "movement", 4, this::movementPredicate) });
        data.add(new AnimationController[] { new AnimationController(this, "attacking", 4, this::attackingPredicate) });
        data.add(new AnimationController[] { new AnimationController(this, "procedure", 4, this::procedurePredicate) });
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
