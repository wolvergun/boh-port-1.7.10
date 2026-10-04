package net.mcreator.boh.entity;

import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.entity.BohAbstractArrow;
import net.mcreator.boh.compat.entity.BohAreaEffectCloud;
import net.mcreator.boh.compat.entity.BohTamableAnimal;
import net.mcreator.boh.compat.forge.event.ForgeEventFactory;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataAccessor;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataSerializers;
import net.mcreator.boh.compat.mc.network.syncher.SynchedEntityData;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.world.DifficultyInstance;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.InteractionResult;
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
import net.mcreator.boh.compat.mc.world.entity.ai.goal.RandomStrollGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.mcreator.boh.compat.mc.world.item.SpawnEggItem;
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
import net.mcreator.boh.procedures.PhantomFreddyOnInitialEntitySpawnProcedure;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class PhantomFoxyEntity extends BohTamableAnimal implements GeoEntity {
    public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(PhantomFoxyEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(PhantomFoxyEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(PhantomFoxyEntity.class, EntityDataSerializers.STRING);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean swinging;
    private boolean lastloop;
    private long lastSwing;
    public String animationprocedure = "empty";
    String prevAnim = "empty";

    public PhantomFoxyEntity(World world) {
        this(BohModEntities.PHANTOM_FOXY.get(), world);
    }

    public PhantomFoxyEntity(EntityType<PhantomFoxyEntity> type, World world) {
        super(type, world);
        M.set_xpReward(this, 0);
        M.setNoAi(this, false);
        M.setMaxUpStep(this, 0.6F);
        M.setPersistenceRequired(this);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHOOT, false);
        this.entityData.define(ANIMATION, "undefined");
        this.entityData.define(TEXTURE, "phantom_foxy");
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
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, false) {
            {
                Objects.requireNonNull(PhantomFoxyEntity.this);
            }

            @Override
            protected double getAttackReachSqr(EntityLivingBase entity) {
                return 4.0;
            }
        });
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0));
        this.targetSelector.addGoal(3, new OwnerHurtTargetGoal(this));
        this.goalSelector.addGoal(4, new OwnerHurtByTargetGoal(this));
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
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        M.playSound(this, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:springtrap_step")), 0.15F, 1.0F);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.land"));
    }

    @Override
    public SoundEvent getDeathSoundEvent() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.destroy"));
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (M.is(source, DamageTypes.IN_FIRE)) {
            return false;
        } else if (M.getDirectEntity(source) instanceof BohAbstractArrow) {
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
        PhantomFreddyOnInitialEntitySpawnProcedure.execute(world, this);
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
        Item item = M.getItem(itemstack);
        if (M.getItem(itemstack) instanceof SpawnEggItem) {
            retval = super.mobInteract(sourceentity, hand);
        } else if (M.isClientSide(M.level(this))) {
            retval = (!M.isTame(this) || !M.isOwnedBy(this, sourceentity)) && !this.isFood(itemstack)
                ? InteractionResult.PASS
                : InteractionResult.sidedSuccess(M.isClientSide(M.level(this)));
        } else if (M.isTame(this)) {
            if (M.isOwnedBy(this, sourceentity)) {
                if (M.isEdible(item) && this.isFood(itemstack) && M.getHealth(this) < M.getMaxHealth(this)) {
                    M.usePlayerItem(this, sourceentity, hand, itemstack);
                    M.heal(this, M.getNutrition(M.getFoodProperties(item)));
                    retval = InteractionResult.sidedSuccess(M.isClientSide(M.level(this)));
                } else if (this.isFood(itemstack) && M.getHealth(this) < M.getMaxHealth(this)) {
                    M.usePlayerItem(this, sourceentity, hand, itemstack);
                    M.heal(this, 4.0F);
                    retval = InteractionResult.sidedSuccess(M.isClientSide(M.level(this)));
                } else {
                    retval = super.mobInteract(sourceentity, hand);
                }
            }
        } else if (this.isFood(itemstack)) {
            M.usePlayerItem(this, sourceentity, hand, itemstack);
            if (M.nextInt(this.random, 3) == 0 && !ForgeEventFactory.onAnimalTame(this, sourceentity)) {
                M.tame(this, sourceentity);
                M.broadcastEntityEvent(M.level(this), this, (byte)7);
            } else {
                M.broadcastEntityEvent(M.level(this), this, (byte)6);
            }

            M.setPersistenceRequired(this);
            retval = InteractionResult.sidedSuccess(M.isClientSide(M.level(this)));
        } else {
            retval = super.mobInteract(sourceentity, hand);
            if (retval == InteractionResult.SUCCESS || retval == InteractionResult.CONSUME) {
                M.setPersistenceRequired(this);
            }
        }

        return retval;
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

    public EntityAgeable getBreedOffspring(WorldServer serverWorld, EntityAgeable ageable) {
        PhantomFoxyEntity retval = M.create(BohModEntities.PHANTOM_FOXY.get(), serverWorld);
        retval.finalizeSpawn(serverWorld, M.getCurrentDifficultyAt(serverWorld, M.blockPosition(retval)), MobSpawnType.BREEDING, null, null);
        return retval;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return M.contains(List.of(), M.getItem(stack));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        M.updateSwingTime(this);
    }

    public static void init() {
    }

    public static Builder createAttributes() {
        Builder builder = M.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.4);
        builder = builder.add(Attributes.MAX_HEALTH, 1.0);
        builder = builder.add(Attributes.ARMOR, 0.0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 6.0);
        return builder.add(Attributes.FOLLOW_RANGE, 16.0);
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
