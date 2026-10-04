package net.mcreator.boh.entity;

import java.util.Objects;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.entity.BohMonster;
import net.mcreator.boh.compat.forge.common.ForgeMod;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataAccessor;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataSerializers;
import net.mcreator.boh.compat.mc.network.syncher.SynchedEntityData;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.util.Mth;
import net.mcreator.boh.compat.mc.world.damagesource.DamageTypes;
import net.mcreator.boh.compat.mc.world.entity.EntityDimensions;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.MobType;
import net.mcreator.boh.compat.mc.world.entity.Pose;
import net.mcreator.boh.compat.mc.world.entity.RemovalReason;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Attributes;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Builder;
import net.mcreator.boh.compat.mc.world.entity.ai.control.MoveControl;
import net.mcreator.boh.compat.mc.world.entity.ai.control.Operation;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.MeleeAttackGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.RandomSwimmingGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.target.HurtByTargetGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.navigation.PathNavigation;
import net.mcreator.boh.compat.mc.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.pathfinder.BlockPathTypes;
import net.mcreator.boh.geo.AnimatableInstanceCache;
import net.mcreator.boh.geo.AnimatableManager;
import net.mcreator.boh.geo.AnimationController;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.GeckoLibUtil;
import net.mcreator.boh.geo.GeoEntity;
import net.mcreator.boh.geo.PlayState;
import net.mcreator.boh.geo.RawAnimation;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class BruceEntity extends BohMonster implements GeoEntity {
    public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(BruceEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(BruceEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(BruceEntity.class, EntityDataSerializers.STRING);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean swinging;
    private boolean lastloop;
    private long lastSwing;
    public String animationprocedure = "empty";
    String prevAnim = "empty";

    public BruceEntity(World world) {
        this(BohModEntities.BRUCE.get(), world);
    }

    public BruceEntity(EntityType<BruceEntity> type, World world) {
        super(type, world);
        M.set_xpReward(this, 0);
        M.setNoAi(this, false);
        M.setMaxUpStep(this, 0.6F);
        M.setPersistenceRequired(this);
        M.setPathfindingMalus(this, BlockPathTypes.WATER, 0.0F);
        this.moveControl = new MoveControl(this) {
            {
                Objects.requireNonNull(BruceEntity.this);
            }

            @Override
            public void tick() {
                if (M.isInWater(BruceEntity.this)) {
                    M.setDeltaMovement(BruceEntity.this, M.getDeltaMovement(BruceEntity.this).add(0.0, 0.005, 0.0));
                }

                if (this.operation == Operation.MOVE_TO && !M.isDone(M.getNavigation(BruceEntity.this))) {
                    double dx = M.wantedX(this) - M.getX(BruceEntity.this);
                    double dy = M.wantedY(this) - M.getY(BruceEntity.this);
                    double dz = M.wantedZ(this) - M.getZ(BruceEntity.this);
                    float f = (float)(Mth.atan2(dz, dx) * (180.0 / Math.PI)) - 90.0F;
                    float f1 = (float)(this.speedModifier * M.getAttribute(BruceEntity.this, Attributes.MOVEMENT_SPEED).getValue());
                    M.setYRot(BruceEntity.this, M.rotlerp(this, M.getYRot(BruceEntity.this), f, 10.0F));
                    M.set_yBodyRot(BruceEntity.this, M.getYRot(BruceEntity.this));
                    M.set_yHeadRot(BruceEntity.this, M.getYRot(BruceEntity.this));
                    if (M.isInWater(BruceEntity.this)) {
                        M.setSpeed(BruceEntity.this, (float)M.getAttribute(BruceEntity.this, Attributes.MOVEMENT_SPEED).getValue());
                        float f2 = -((float)(Mth.atan2(dy, (float)Math.sqrt(dx * dx + dz * dz)) * (180.0 / Math.PI)));
                        f2 = Mth.clamp(Mth.wrapDegrees(f2), -85.0F, 85.0F);
                        M.setXRot(BruceEntity.this, M.rotlerp(this, M.getXRot(BruceEntity.this), f2, 5.0F));
                        float f3 = Mth.cos(M.getXRot(BruceEntity.this) * (float) (Math.PI / 180.0));
                        M.setZza(BruceEntity.this, f3 * f1);
                        M.setYya(BruceEntity.this, (float)(f1 * dy));
                    } else {
                        M.setSpeed(BruceEntity.this, f1 * 0.05F);
                    }
                } else {
                    M.setSpeed(BruceEntity.this, 0.0F);
                    M.setYya(BruceEntity.this, 0.0F);
                    M.setZza(BruceEntity.this, 0.0F);
                }
            }
        };
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHOOT, false);
        this.entityData.define(ANIMATION, "undefined");
        this.entityData.define(TEXTURE, "bruce");
    }

    public void setTexture(String texture) {
        M.set(this.entityData, TEXTURE, texture);
    }

    public String getTexture() {
        return this.entityData.get(TEXTURE);
    }

    @Override
    protected PathNavigation createNavigation(World world) {
        return new WaterBoundPathNavigation(this, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false) {
            {
                Objects.requireNonNull(BruceEntity.this);
            }

            @Override
            protected double getAttackReachSqr(EntityLivingBase entity) {
                return 4.0;
            }
        });
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(3, new RandomSwimmingGoal(this, 1.0, 40));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, EntityPlayer.class, false, false));
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
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.cod.ambient"));
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        M.playSound(this, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.fish.swim")), 0.15F, 1.0F);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.cod.hurt"));
    }

    @Override
    public SoundEvent getDeathSoundEvent() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.cod.death"));
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return M.is(source, DamageTypes.DROWN) ? false : super.hurt(source, amount);
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

    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean checkSpawnObstruction(World world) {
        return M.isUnobstructed(world, this);
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    public static void init() {
    }

    public static Builder createAttributes() {
        Builder builder = M.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 3.0);
        builder = builder.add(Attributes.MAX_HEALTH, 80.0);
        builder = builder.add(Attributes.ARMOR, 0.0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 7.0);
        builder = builder.add(Attributes.FOLLOW_RANGE, 16.0);
        return builder.add(ForgeMod.SWIM_SPEED.get(), 3.0);
    }

    private PlayState movementPredicate(AnimationState event) {
        if (this.animationprocedure.equals("empty")) {
            return M.isInWaterOrBubble(this)
                ? event.setAndContinue(RawAnimation.begin().thenLoop("swim"))
                : event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
        } else {
            return PlayState.STOP;
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
