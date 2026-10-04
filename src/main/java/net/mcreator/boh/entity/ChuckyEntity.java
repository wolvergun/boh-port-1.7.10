package net.mcreator.boh.entity;

import java.util.Objects;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.entity.BohMonster;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataAccessor;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataSerializers;
import net.mcreator.boh.compat.mc.network.syncher.SynchedEntityData;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.world.DifficultyInstance;
import net.mcreator.boh.compat.mc.world.damagesource.DamageTypes;
import net.mcreator.boh.compat.mc.world.entity.EntityDimensions;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.compat.mc.world.entity.MobType;
import net.mcreator.boh.compat.mc.world.entity.Pose;
import net.mcreator.boh.compat.mc.world.entity.RemovalReason;
import net.mcreator.boh.compat.mc.world.entity.SpawnGroupData;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Attributes;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Builder;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.MeleeAttackGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.RandomLookAroundGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.RandomSwimmingGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
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
import net.mcreator.boh.init.BohModItems;
import net.mcreator.boh.procedures.ChuckyEntityIsHurtProcedure;
import net.mcreator.boh.procedures.ChuckyOnEntityTickUpdateProcedure;
import net.mcreator.boh.procedures.ChuckyOnInitialEntitySpawnProcedure;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ChuckyEntity extends BohMonster implements GeoEntity {
    public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(ChuckyEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(ChuckyEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(ChuckyEntity.class, EntityDataSerializers.STRING);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean swinging;
    private boolean lastloop;
    private long lastSwing;
    public String animationprocedure = "empty";
    String prevAnim = "empty";

    public ChuckyEntity(World world) {
        this(BohModEntities.CHUCKY.get(), world);
    }

    public ChuckyEntity(EntityType<ChuckyEntity> type, World world) {
        super(type, world);
        M.set_xpReward(this, 13);
        M.setNoAi(this, false);
        M.setMaxUpStep(this, 0.3F);
        M.setPersistenceRequired(this);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHOOT, false);
        this.entityData.define(ANIMATION, "undefined");
        this.entityData.define(TEXTURE, "chucky");
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
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false) {
            {
                Objects.requireNonNull(ChuckyEntity.this);
            }

            @Override
            protected double getAttackReachSqr(EntityLivingBase entity) {
                return M.getBbWidth(this.mob) * M.getBbWidth(this.mob) + M.getBbWidth(entity);
            }
        });
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0, 40));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
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
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
        M.spawnAtLocation(this, M.new_ItemStack(BohModItems.KILLER_KNIFE.get()));
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.wool.break"));
    }

    @Override
    public SoundEvent getDeathSoundEvent() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chucky_death"));
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        ChuckyEntityIsHurtProcedure.execute(M.level(this), M.getX(this), M.getY(this), M.getZ(this));
        return M.is(source, DamageTypes.FALL) ? false : super.hurt(source, amount);
    }

    @Override
    public SpawnGroupData finalizeSpawn(
        World world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable NBTTagCompound tag
    ) {
        SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
        ChuckyOnInitialEntitySpawnProcedure.execute(world, M.getX(this), M.getY(this), M.getZ(this));
        return retval;
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
        ChuckyOnEntityTickUpdateProcedure.execute(M.level(this), M.getX(this), M.getY(this), M.getZ(this), this);
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
        builder = builder.add(Attributes.MAX_HEALTH, 60.0);
        builder = builder.add(Attributes.ARMOR, 10.0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 6.0);
        builder = builder.add(Attributes.FOLLOW_RANGE, 16.0);
        return builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.3);
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

        if (this.swinging && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
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
}
