package net.mcreator.boh.entity;

import javax.annotation.Nullable;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.procedures.GoldHostileEntityDiesProcedure;
import net.mcreator.boh.procedures.GoldHostileOnEntityTickUpdateProcedure;
import net.mcreator.boh.procedures.GoldHostileOnInitialEntitySpawnProcedure;
import net.minecraft.nbt.NBTTagCompound;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataAccessor;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataSerializers;
import net.mcreator.boh.compat.mc.network.syncher.SynchedEntityData;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.world.DifficultyInstance;
import net.minecraft.util.DamageSource;
import net.mcreator.boh.compat.mc.world.entity.EntityDimensions;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.minecraft.entity.EntityLivingBase;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.compat.mc.world.entity.MobType;
import net.mcreator.boh.compat.mc.world.entity.Pose;
import net.mcreator.boh.compat.mc.world.entity.SpawnGroupData;
import net.mcreator.boh.compat.mc.world.entity.RemovalReason;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Attributes;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Builder;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.MeleeAttackGoal;
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
import net.mcreator.boh.compat.entity.BohMonster;
import net.mcreator.boh.compat.M;

public class GoldHostileEntity extends BohMonster implements GeoEntity {

    public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(GoldHostileEntity.class, EntityDataSerializers.BOOLEAN);

    public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(GoldHostileEntity.class, EntityDataSerializers.STRING);

    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(GoldHostileEntity.class, EntityDataSerializers.STRING);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private boolean swinging;

    private boolean lastloop;

    private long lastSwing;

    public String animationprocedure = "empty";

    String prevAnim = "empty";

    public GoldHostileEntity(World world) {
        this((EntityType<GoldHostileEntity>) BohModEntities.GOLD_HOSTILE.get(), world);
    }

    public GoldHostileEntity(EntityType<GoldHostileEntity> type, World world) {
        super(type, world);
        M.set_xpReward(this, 13);
        M.setNoAi(this, false);
        M.setMaxUpStep(this, 0.6F);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHOOT, false);
        this.entityData.define(ANIMATION, "undefined");
        this.entityData.define(TEXTURE, "gold_unown_open");
    }

    public void setTexture(String texture) {
        M.set(this.entityData, TEXTURE, texture);
    }

    public String getTexture() {
        return (String) this.entityData.get(TEXTURE);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, EntityPlayer.class, false, false));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2, false) {

            protected double getAttackReachSqr(EntityLivingBase entity) {
                return M.getBbWidth(this.mob) * M.getBbWidth(this.mob) + M.getBbWidth(entity);
            }
        });
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this, new Class[0]));
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    public SoundEvent getHurtSound(DamageSource ds) {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
    }

    public SoundEvent getDeathSoundEvent() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
    }

    public void die(DamageSource source) {
        super.die(source);
        GoldHostileEntityDiesProcedure.execute(M.level(this), M.getX(this), M.getY(this), M.getZ(this));
    }

    public SpawnGroupData finalizeSpawn(World world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable NBTTagCompound tag) {
        SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
        GoldHostileOnInitialEntitySpawnProcedure.execute(world, M.getX(this), M.getY(this), M.getZ(this));
        return retval;
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
        GoldHostileOnEntityTickUpdateProcedure.execute(M.level(this), M.getX(this), M.getY(this), M.getZ(this), this);
        M.refreshDimensions(this);
    }

    public EntityDimensions getDimensions(Pose p_33597_) {
        return super.getDimensions(p_33597_).scale(1.0F);
    }

    public static void init() {
    }

    public static Builder createAttributes() {
        Builder builder = M.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
        builder = builder.add(Attributes.MAX_HEALTH, 100.0);
        builder = builder.add(Attributes.ARMOR, 10.0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0.0);
        builder = builder.add(Attributes.FOLLOW_RANGE, 16.0);
        return builder.add(Attributes.KNOCKBACK_RESISTANCE, 100.0);
    }

    private PlayState movementPredicate(AnimationState event) {
        if (this.animationprocedure.equals("empty")) {
            return !event.isMoving() && event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F ? event.setAndContinue(RawAnimation.begin().thenLoop("idle")) : event.setAndContinue(RawAnimation.begin().thenLoop("walk"));
        } else {
            return PlayState.STOP;
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
        data.add(new AnimationController[] { new AnimationController(this, "procedure", 4, this::procedurePredicate) });
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
