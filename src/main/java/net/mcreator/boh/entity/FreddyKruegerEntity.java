package net.mcreator.boh.entity;

import java.util.Objects;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.entity.BohAbstractArrow;
import net.mcreator.boh.compat.entity.BohAreaEffectCloud;
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
import net.mcreator.boh.compat.mc.world.entity.ai.goal.AvoidEntityGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.FloatGoal;
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
import net.mcreator.boh.init.BohModItems;
import net.mcreator.boh.procedures.FreddyKruegerEntityIsHurtProcedure;
import net.mcreator.boh.procedures.FreddyKruegerOnEntityTickUpdateProcedure;
import net.mcreator.boh.procedures.FreddyKruegerOnInitialEntitySpawnProcedure;
import net.mcreator.boh.procedures.OnFireRunProcedure;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class FreddyKruegerEntity extends BohMonster implements GeoEntity {
    public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(FreddyKruegerEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(FreddyKruegerEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(FreddyKruegerEntity.class, EntityDataSerializers.STRING);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean swinging;
    private boolean lastloop;
    private long lastSwing;
    public String animationprocedure = "empty";
    String prevAnim = "empty";

    public FreddyKruegerEntity(World world) {
        this(BohModEntities.FREDDY_KRUEGER.get(), world);
    }

    public FreddyKruegerEntity(EntityType<FreddyKruegerEntity> type, World world) {
        super(type, world);
        M.set_xpReward(this, 10);
        M.setNoAi(this, false);
        M.setMaxUpStep(this, 0.6F);
        M.setPersistenceRequired(this);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHOOT, false);
        this.entityData.define(ANIMATION, "undefined");
        this.entityData.define(TEXTURE, "freddy_krueger");
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
        this.goalSelector.addGoal(1, new AvoidEntityGoal<EntityPlayer>(this, EntityPlayer.class, 15.0F, 1.0, 1.1) {
            {
                Objects.requireNonNull(FreddyKruegerEntity.this);
            }

            @Override
            public boolean canUse() {
                double x = M.getX(FreddyKruegerEntity.this);
                double y = M.getY(FreddyKruegerEntity.this);
                double z = M.getZ(FreddyKruegerEntity.this);
                Entity entity = FreddyKruegerEntity.this;
                World world = M.level(FreddyKruegerEntity.this);
                return super.canUse() && OnFireRunProcedure.execute(entity);
            }

            @Override
            public boolean canContinueToUse() {
                double x = M.getX(FreddyKruegerEntity.this);
                double y = M.getY(FreddyKruegerEntity.this);
                double z = M.getZ(FreddyKruegerEntity.this);
                Entity entity = FreddyKruegerEntity.this;
                World world = M.level(FreddyKruegerEntity.this);
                return super.canContinueToUse() && OnFireRunProcedure.execute(entity);
            }
        });
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, EntityPlayer.class, false, false));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2, true) {
            {
                Objects.requireNonNull(FreddyKruegerEntity.this);
            }

            @Override
            protected double getAttackReachSqr(EntityLivingBase entity) {
                return 4.0;
            }
        });
        this.targetSelector.addGoal(4, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(6, new FloatGoal(this));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
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
        M.spawnAtLocation(this, M.new_ItemStack(BohModItems.FREDDY_CLAW.get()));
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
    public boolean hurt(DamageSource source, float amount) {
        FreddyKruegerEntityIsHurtProcedure.execute(M.level(this), M.getX(this), M.getY(this), M.getZ(this));
        if (M.getDirectEntity(source) instanceof BohAbstractArrow) {
            return false;
        } else if (M.getDirectEntity(source) instanceof EntityPlayer) {
            return false;
        } else if (M.getDirectEntity(source) instanceof EntityPotion || M.getDirectEntity(source) instanceof BohAreaEffectCloud) {
            return false;
        } else if (M.is(source, DamageTypes.FALL)) {
            return false;
        } else if (M.is(source, DamageTypes.CACTUS)) {
            return false;
        } else if (M.is(source, DamageTypes.DROWN)) {
            return false;
        } else if (M.is(source, DamageTypes.LIGHTNING_BOLT)) {
            return false;
        } else if (M.is(source, DamageTypes.EXPLOSION)) {
            return false;
        } else if (M.is(source, DamageTypes.TRIDENT)) {
            return false;
        } else if (M.is(source, DamageTypes.FALLING_ANVIL)) {
            return false;
        } else if (M.is(source, DamageTypes.DRAGON_BREATH)) {
            return false;
        } else if (M.is(source, DamageTypes.WITHER)) {
            return false;
        } else {
            return M.is(source, DamageTypes.WITHER_SKULL) ? false : super.hurt(source, amount);
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(
        World world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable NBTTagCompound tag
    ) {
        SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
        FreddyKruegerOnInitialEntitySpawnProcedure.execute(world, M.getX(this), M.getY(this), M.getZ(this), this);
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
        FreddyKruegerOnEntityTickUpdateProcedure.execute(M.level(this), M.getX(this), M.getY(this), M.getZ(this), this);
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
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
        builder = builder.add(Attributes.MAX_HEALTH, 20.0);
        builder = builder.add(Attributes.ARMOR, 0.0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 9.0);
        return builder.add(Attributes.FOLLOW_RANGE, 100.0);
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
