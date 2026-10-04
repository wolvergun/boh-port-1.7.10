package net.mcreator.boh.entity;

import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.entity.BohAreaEffectCloud;
import net.mcreator.boh.compat.entity.BohMonster;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataAccessor;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataSerializers;
import net.mcreator.boh.compat.mc.network.syncher.SynchedEntityData;
import net.mcreator.boh.compat.mc.server.level.ServerBossEvent;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.world.BossBarColor;
import net.mcreator.boh.compat.mc.world.BossBarOverlay;
import net.mcreator.boh.compat.mc.world.DifficultyInstance;
import net.mcreator.boh.compat.mc.world.damagesource.DamageTypes;
import net.mcreator.boh.compat.mc.world.entity.EntityDimensions;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.compat.mc.world.entity.MobType;
import net.mcreator.boh.compat.mc.world.entity.Pose;
import net.mcreator.boh.compat.mc.world.entity.RemovalReason;
import net.mcreator.boh.compat.mc.world.entity.SpawnGroupData;
import net.mcreator.boh.compat.mc.world.entity.SpawnPlacements;
import net.mcreator.boh.compat.mc.world.entity.Type;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Attributes;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Builder;
import net.mcreator.boh.compat.mc.world.entity.ai.control.FlyingMoveControl;
import net.mcreator.boh.compat.mc.world.entity.ai.navigation.FlyingPathNavigation;
import net.mcreator.boh.compat.mc.world.entity.ai.navigation.PathNavigation;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.levelgen.Types;
import net.mcreator.boh.geo.AnimatableInstanceCache;
import net.mcreator.boh.geo.AnimatableManager;
import net.mcreator.boh.geo.AnimationController;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.GeckoLibUtil;
import net.mcreator.boh.geo.GeoEntity;
import net.mcreator.boh.geo.PlayState;
import net.mcreator.boh.geo.RawAnimation;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.procedures.ExeMonitorSolidBoundingBoxConditionProcedure;
import net.mcreator.boh.procedures.SaucerEntityDiesProcedure;
import net.mcreator.boh.procedures.SaucerNaturalEntitySpawningConditionProcedure;
import net.mcreator.boh.procedures.SaucerOnEntityTickUpdateProcedure;
import net.mcreator.boh.procedures.SaucerOnInitialEntitySpawnProcedure;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class SaucerEntity extends BohMonster implements GeoEntity {
    public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(SaucerEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(SaucerEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(SaucerEntity.class, EntityDataSerializers.STRING);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean swinging;
    private boolean lastloop;
    private long lastSwing;
    public String animationprocedure = "empty";
    private final ServerBossEvent bossInfo = new ServerBossEvent(M.getDisplayName(this), BossBarColor.GREEN, BossBarOverlay.NOTCHED_6);
    String prevAnim = "empty";

    public SaucerEntity(World world) {
        this(BohModEntities.SAUCER.get(), world);
    }

    public SaucerEntity(EntityType<SaucerEntity> type, World world) {
        super(type, world);
        M.set_xpReward(this, 500);
        M.setNoAi(this, false);
        M.setMaxUpStep(this, 0.6F);
        M.setPersistenceRequired(this);
        this.moveControl = new FlyingMoveControl(this, 10, true);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHOOT, false);
        this.entityData.define(ANIMATION, "undefined");
        this.entityData.define(TEXTURE, "saucer");
    }

    public void setTexture(String texture) {
        M.set(this.entityData, TEXTURE, texture);
    }

    public String getTexture() {
        return this.entityData.get(TEXTURE);
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return true;
    }

    public boolean canBeCollidedWith() {
        World world = M.level(this);
        double x = M.getX(this);
        double y = M.getY(this);
        double z = M.getZ(this);
        return ExeMonitorSolidBoundingBoxConditionProcedure.execute();
    }

    @Override
    protected PathNavigation createNavigation(World world) {
        return new FlyingPathNavigation(this, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
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
    public boolean causeFallDamage(float l, float d, DamageSource source) {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!(M.getDirectEntity(source) instanceof EntityPotion) && !(M.getDirectEntity(source) instanceof BohAreaEffectCloud)) {
            return M.is(source, DamageTypes.FALL) ? false : super.hurt(source, amount);
        } else {
            return false;
        }
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        SaucerEntityDiesProcedure.execute(M.level(this), M.getX(this), M.getY(this), M.getZ(this), this);
    }

    @Override
    public SpawnGroupData finalizeSpawn(
        World world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable NBTTagCompound tag
    ) {
        SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
        SaucerOnInitialEntitySpawnProcedure.execute(world, M.getX(this), M.getY(this), M.getZ(this), this);
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
        SaucerOnEntityTickUpdateProcedure.execute(M.level(this), M.getX(this), M.getY(this), M.getZ(this), this);
        M.refreshDimensions(this);
    }

    @Override
    public EntityDimensions getDimensions(Pose p_33597_) {
        return super.getDimensions(p_33597_).scale(3.0F);
    }

    @Override
    public boolean canChangeDimensions() {
        return false;
    }

    @Override
    public void startSeenByPlayer(EntityPlayerMP player) {
        super.startSeenByPlayer(player);
        M.addPlayer(this.bossInfo, player);
    }

    @Override
    public void stopSeenByPlayer(EntityPlayerMP player) {
        super.stopSeenByPlayer(player);
        M.removePlayer(this.bossInfo, player);
    }

    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        M.setProgress(this.bossInfo, M.getHealth(this) / M.getMaxHealth(this));
    }

    @Override
    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    @Override
    public void setNoGravity(boolean ignored) {
        super.setNoGravity(true);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.setNoGravity(true);
    }

    public static void init() {
        SpawnPlacements.register(BohModEntities.SAUCER.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, (entityType, world, reason, pos, random) -> {
            int x = M.getX(pos);
            int y = M.getY(pos);
            int z = M.getZ(pos);
            return SaucerNaturalEntitySpawningConditionProcedure.execute(world);
        });
    }

    public static Builder createAttributes() {
        Builder builder = M.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.0);
        builder = builder.add(Attributes.MAX_HEALTH, 400.0);
        builder = builder.add(Attributes.ARMOR, 10.0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0.0);
        builder = builder.add(Attributes.FOLLOW_RANGE, 32.0);
        builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1000.0);
        return builder.add(Attributes.FLYING_SPEED, 0.0);
    }

    private PlayState movementPredicate(AnimationState event) {
        if (this.animationprocedure.equals("empty")) {
            return M.isDeadOrDying(this)
                ? event.setAndContinue(RawAnimation.begin().thenPlay("death"))
                : event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
        } else {
            return PlayState.STOP;
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
