package net.mcreator.boh.entity;

import java.util.Objects;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.entity.BohPathfinderMob;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataAccessor;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataSerializers;
import net.mcreator.boh.compat.mc.network.syncher.SynchedEntityData;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.entity.EntityDimensions;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.MobType;
import net.mcreator.boh.compat.mc.world.entity.Pose;
import net.mcreator.boh.compat.mc.world.entity.RemovalReason;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Attributes;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Builder;
import net.mcreator.boh.compat.mc.world.entity.ai.control.FlyingMoveControl;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.FloatGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.LeapAtTargetGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.RandomLookAroundGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.RandomStrollGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.TemptGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.navigation.FlyingPathNavigation;
import net.mcreator.boh.compat.mc.world.entity.ai.navigation.PathNavigation;
import net.mcreator.boh.compat.mc.world.item.crafting.Ingredient;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.geo.AnimatableInstanceCache;
import net.mcreator.boh.geo.AnimatableManager;
import net.mcreator.boh.geo.AnimationController;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.GeckoLibUtil;
import net.mcreator.boh.geo.GeoEntity;
import net.mcreator.boh.geo.PlayState;
import net.mcreator.boh.geo.RawAnimation;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.procedures.WhitefaceFriendlyEntityIsHurtProcedure;
import net.mcreator.boh.procedures.WhitefaceOnEntityTickUpdateProcedure;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class WhitefaceFriendlyEntity extends BohPathfinderMob implements GeoEntity {
    public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(WhitefaceFriendlyEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(WhitefaceFriendlyEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(WhitefaceFriendlyEntity.class, EntityDataSerializers.STRING);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean swinging;
    private boolean lastloop;
    private long lastSwing;
    public String animationprocedure = "empty";
    String prevAnim = "empty";

    public WhitefaceFriendlyEntity(World world) {
        this(BohModEntities.WHITEFACE_FRIENDLY.get(), world);
    }

    public WhitefaceFriendlyEntity(EntityType<WhitefaceFriendlyEntity> type, World world) {
        super(type, world);
        M.set_xpReward(this, 0);
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
        this.entityData.define(TEXTURE, "whiteface_happy");
    }

    public void setTexture(String texture) {
        M.set(this.entityData, TEXTURE, texture);
    }

    public String getTexture() {
        return this.entityData.get(TEXTURE);
    }

    @Override
    protected PathNavigation createNavigation(World world) {
        return new FlyingPathNavigation(this, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new TemptGoal(this, 1.0, Ingredient.of(M.asItem(Blocks.AIR)), false));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 0.8, 20) {
            {
                Objects.requireNonNull(WhitefaceFriendlyEntity.this);
            }

            @Override
            protected Vec3 getPosition() {
                RandomSource random = M.getRandom(WhitefaceFriendlyEntity.this);
                double dir_x = M.getX(WhitefaceFriendlyEntity.this) + (M.nextFloat(random) * 2.0F - 1.0F) * 16.0F;
                double dir_y = M.getY(WhitefaceFriendlyEntity.this) + (M.nextFloat(random) * 2.0F - 1.0F) * 16.0F;
                double dir_z = M.getZ(WhitefaceFriendlyEntity.this) + (M.nextFloat(random) * 2.0F - 1.0F) * 16.0F;
                return new Vec3(dir_x, dir_y, dir_z);
            }
        });
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new FloatGoal(this));
        this.goalSelector.addGoal(5, new LeapAtTargetGoal(this, 0.5F));
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
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:whiteface_laugh"));
    }

    @Override
    public boolean causeFallDamage(float l, float d, DamageSource source) {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        WhitefaceFriendlyEntityIsHurtProcedure.execute(M.level(this), M.getX(this), M.getY(this), M.getZ(this), this, M.getEntity(source));
        return super.hurt(source, amount);
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
        WhitefaceOnEntityTickUpdateProcedure.execute(M.level(this), M.getX(this), M.getY(this), M.getZ(this), this);
        M.refreshDimensions(this);
    }

    @Override
    public EntityDimensions getDimensions(Pose p_33597_) {
        return super.getDimensions(p_33597_).scale(1.0F);
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
        M.updateSwingTime(this);
        this.setNoGravity(true);
    }

    public static void init() {
    }

    public static Builder createAttributes() {
        Builder builder = M.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
        builder = builder.add(Attributes.MAX_HEALTH, 30.0);
        builder = builder.add(Attributes.ARMOR, 20.0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 5.0);
        builder = builder.add(Attributes.FOLLOW_RANGE, 1000.0);
        return builder.add(Attributes.FLYING_SPEED, 0.3);
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
