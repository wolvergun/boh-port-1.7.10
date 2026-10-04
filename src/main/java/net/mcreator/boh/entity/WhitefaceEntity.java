package net.mcreator.boh.entity;

import java.util.EnumSet;
import java.util.Objects;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.entity.BohMonster;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataAccessor;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataSerializers;
import net.mcreator.boh.compat.mc.network.syncher.SynchedEntityData;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.DifficultyInstance;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.InteractionResult;
import net.mcreator.boh.compat.mc.world.entity.EntityDimensions;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.EquipmentSlot;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.compat.mc.world.entity.MobType;
import net.mcreator.boh.compat.mc.world.entity.Pose;
import net.mcreator.boh.compat.mc.world.entity.RemovalReason;
import net.mcreator.boh.compat.mc.world.entity.SpawnGroupData;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Attributes;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Builder;
import net.mcreator.boh.compat.mc.world.entity.ai.control.FlyingMoveControl;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.Flag;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.Goal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.MeleeAttackGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.RandomLookAroundGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.RandomStrollGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.target.HurtByTargetGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.navigation.FlyingPathNavigation;
import net.mcreator.boh.compat.mc.world.entity.ai.navigation.PathNavigation;
import net.mcreator.boh.compat.mc.world.item.Items;
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
import net.mcreator.boh.procedures.WhitefaceOnEntityTickUpdateProcedure;
import net.mcreator.boh.procedures.WhitefaceOnInitialEntitySpawnProcedure;
import net.mcreator.boh.procedures.WhitefaceRightClickedOnEntityProcedure;
import net.mcreator.boh.procedures.WhitefaceThisEntityKillsAnotherOneProcedure;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class WhitefaceEntity extends BohMonster implements GeoEntity {
    public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(WhitefaceEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(WhitefaceEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(WhitefaceEntity.class, EntityDataSerializers.STRING);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean swinging;
    private boolean lastloop;
    private long lastSwing;
    public String animationprocedure = "empty";
    String prevAnim = "empty";

    public WhitefaceEntity(World world) {
        this(BohModEntities.WHITEFACE.get(), world);
    }

    public WhitefaceEntity(EntityType<WhitefaceEntity> type, World world) {
        super(type, world);
        M.set_xpReward(this, 13);
        M.setNoAi(this, false);
        M.setMaxUpStep(this, 0.6F);
        M.setPersistenceRequired(this);
        M.setItemSlot(this, EquipmentSlot.MAINHAND, M.new_ItemStack(Items.WOODEN_AXE));
        this.moveControl = new FlyingMoveControl(this, 10, true);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHOOT, false);
        this.entityData.define(ANIMATION, "undefined");
        this.entityData.define(TEXTURE, "whiteface");
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
        this.goalSelector
            .addGoal(
                1,
                new Goal() {
                    {
                        Objects.requireNonNull(WhitefaceEntity.this);
                        this.setFlags(EnumSet.of(Flag.MOVE));
                    }

                    @Override
                    public boolean canUse() {
                        return M.getTarget(WhitefaceEntity.this) != null && !M.getMoveControl(WhitefaceEntity.this).hasWanted();
                    }

                    @Override
                    public boolean canContinueToUse() {
                        return M.getMoveControl(WhitefaceEntity.this).hasWanted()
                            && M.getTarget(WhitefaceEntity.this) != null
                            && M.isAlive(M.getTarget(WhitefaceEntity.this));
                    }

                    @Override
                    public void start() {
                        EntityLivingBase livingentity = M.getTarget(WhitefaceEntity.this);
                        Vec3 vec3d = M.getEyePosition(livingentity, 1.0F);
                        WhitefaceEntity.this.moveControl.setWantedPosition(vec3d.x, vec3d.y, vec3d.z, 1.2);
                    }

                    @Override
                    public void tick() {
                        EntityLivingBase livingentity = M.getTarget(WhitefaceEntity.this);
                        if (M.getBoundingBox(WhitefaceEntity.this).intersects(M.getBoundingBox(livingentity))) {
                            M.doHurtTarget(WhitefaceEntity.this, livingentity);
                        } else {
                            double d0 = M.distanceToSqr(WhitefaceEntity.this, livingentity);
                            if (d0 < 16.0) {
                                Vec3 vec3d = M.getEyePosition(livingentity, 1.0F);
                                WhitefaceEntity.this.moveControl.setWantedPosition(vec3d.x, vec3d.y, vec3d.z, 1.2);
                            }
                        }
                    }
                }
            );
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 100.0, 20) {
            {
                Objects.requireNonNull(WhitefaceEntity.this);
            }

            @Override
            protected Vec3 getPosition() {
                RandomSource random = M.getRandom(WhitefaceEntity.this);
                double dir_x = M.getX(WhitefaceEntity.this) + (M.nextFloat(random) * 2.0F - 1.0F) * 16.0F;
                double dir_y = M.getY(WhitefaceEntity.this) + (M.nextFloat(random) * 2.0F - 1.0F) * 16.0F;
                double dir_z = M.getZ(WhitefaceEntity.this) + (M.nextFloat(random) * 2.0F - 1.0F) * 16.0F;
                return new Vec3(dir_x, dir_y, dir_z);
            }
        });
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2, false) {
            {
                Objects.requireNonNull(WhitefaceEntity.this);
            }

            @Override
            protected double getAttackReachSqr(EntityLivingBase entity) {
                return M.getBbWidth(this.mob) * M.getBbWidth(this.mob) + M.getBbWidth(entity);
            }
        });
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, EntityPlayer.class, false, false));
        this.targetSelector.addGoal(6, new HurtByTargetGoal(this));
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
    public SpawnGroupData finalizeSpawn(
        World world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable NBTTagCompound tag
    ) {
        SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
        WhitefaceOnInitialEntitySpawnProcedure.execute(world, this);
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
    public InteractionResult mobInteract(EntityPlayer sourceentity, InteractionHand hand) {
        ItemStack itemstack = M.getItemInHand(sourceentity, hand);
        InteractionResult retval = InteractionResult.sidedSuccess(M.isClientSide(M.level(this)));
        super.mobInteract(sourceentity, hand);
        double x = M.getX(this);
        double y = M.getY(this);
        double z = M.getZ(this);
        World world = M.level(this);
        WhitefaceRightClickedOnEntityProcedure.execute(world, x, y, z, this, sourceentity);
        return retval;
    }

    @Override
    public void awardKillScore(Entity entity, int score, DamageSource damageSource) {
        super.awardKillScore(entity, score, damageSource);
        WhitefaceThisEntityKillsAnotherOneProcedure.execute(entity);
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
        this.setNoGravity(true);
    }

    public static void init() {
    }

    public static Builder createAttributes() {
        Builder builder = M.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.4);
        builder = builder.add(Attributes.MAX_HEALTH, 30.0);
        builder = builder.add(Attributes.ARMOR, 20.0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 5.0);
        builder = builder.add(Attributes.FOLLOW_RANGE, 1000.0);
        return builder.add(Attributes.FLYING_SPEED, 0.4);
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
