package net.mcreator.boh.entity;

import java.util.Objects;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.entity.BohMonster;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataAccessor;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataSerializers;
import net.mcreator.boh.compat.mc.network.syncher.SynchedEntityData;
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
import net.mcreator.boh.procedures.TailsEntityIsHurtProcedure;
import net.mcreator.boh.procedures.TailsOnEntityTickUpdateProcedure;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class TailsEntity extends BohMonster implements GeoEntity {
    public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(TailsEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(TailsEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(TailsEntity.class, EntityDataSerializers.STRING);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean swinging;
    private boolean lastloop;
    private long lastSwing;
    public String animationprocedure = "empty";
    String prevAnim = "empty";

    public TailsEntity(World world) {
        this(BohModEntities.TAILS.get(), world);
    }

    public TailsEntity(EntityType<TailsEntity> type, World world) {
        super(type, world);
        M.set_xpReward(this, 40);
        M.setNoAi(this, false);
        M.setMaxUpStep(this, 0.6F);
        M.setPersistenceRequired(this);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHOOT, false);
        this.entityData.define(ANIMATION, "undefined");
        this.entityData.define(TEXTURE, "tails");
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
                Objects.requireNonNull(TailsEntity.this);
            }

            @Override
            protected double getAttackReachSqr(EntityLivingBase entity) {
                return 0.64;
            }
        });
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, EntityPlayer.class, true, true));
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
    public boolean hurt(DamageSource source, float amount) {
        TailsEntityIsHurtProcedure.execute(M.level(this), M.getX(this), M.getY(this), M.getZ(this), this, M.getEntity(source));
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
        TailsOnEntityTickUpdateProcedure.execute(M.level(this), M.getX(this), M.getY(this), M.getZ(this), this);
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
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
        builder = builder.add(Attributes.MAX_HEALTH, 1000.0);
        builder = builder.add(Attributes.ARMOR, 100.0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 1.0);
        builder = builder.add(Attributes.FOLLOW_RANGE, 1000.0);
        return builder.add(Attributes.KNOCKBACK_RESISTANCE, 1000.0);
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
            return event.setAndContinue(RawAnimation.begin().thenPlay("grab"));
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
