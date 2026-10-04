package net.mcreator.boh.entity;

import java.util.List;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.init.BohModItems;
import net.mcreator.boh.procedures.DisableTeleportProcedure;
import net.mcreator.boh.procedures.RabbidEntityDiesProcedure;
import net.mcreator.boh.procedures.RabbidOnEntityTickUpdateProcedure;
import net.mcreator.boh.procedures.RabbidRightClickedOnEntityProcedure;
import net.mcreator.boh.procedures.WanderTameProcedure;
import net.minecraft.nbt.NBTTagCompound;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataAccessor;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataSerializers;
import net.mcreator.boh.compat.mc.network.syncher.SynchedEntityData;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.InteractionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EntityDimensions;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.minecraft.entity.EntityLivingBase;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.compat.mc.world.entity.MobType;
import net.mcreator.boh.compat.mc.world.entity.Pose;
import net.mcreator.boh.compat.mc.world.entity.RemovalReason;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Attributes;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Builder;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.FloatGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.FollowOwnerGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.MeleeAttackGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.RandomLookAroundGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.RandomStrollGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.TemptGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.mc.world.item.SpawnEggItem;
import net.mcreator.boh.compat.mc.world.item.crafting.Ingredient;
import net.minecraft.world.World;
import net.mcreator.boh.compat.forge.event.ForgeEventFactory;
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
import net.mcreator.boh.compat.entity.BohTamableAnimal;
import net.mcreator.boh.compat.M;

public class RabbidEntity extends BohTamableAnimal implements GeoEntity {

    public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(RabbidEntity.class, EntityDataSerializers.BOOLEAN);

    public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(RabbidEntity.class, EntityDataSerializers.STRING);

    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(RabbidEntity.class, EntityDataSerializers.STRING);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private boolean swinging;

    private boolean lastloop;

    private long lastSwing;

    public String animationprocedure = "empty";

    String prevAnim = "empty";

    public RabbidEntity(World world) {
        this((EntityType<RabbidEntity>) BohModEntities.RABBID.get(), world);
    }

    public RabbidEntity(EntityType<RabbidEntity> type, World world) {
        super(type, world);
        M.set_xpReward(this, 10);
        M.setNoAi(this, false);
        M.setMaxUpStep(this, 0.6F);
        M.setPersistenceRequired(this);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHOOT, false);
        this.entityData.define(ANIMATION, "undefined");
        this.entityData.define(TEXTURE, "rabbids");
    }

    public void setTexture(String texture) {
        M.set(this.entityData, TEXTURE, texture);
    }

    public String getTexture() {
        return (String) this.entityData.get(TEXTURE);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new FollowOwnerGoal(this, 1.0, 10.0F, 2.0F, false) {

            public boolean canUse() {
                double x = M.getX(RabbidEntity.this);
                double y = M.getY(RabbidEntity.this);
                double z = M.getZ(RabbidEntity.this);
                Entity entity = RabbidEntity.this;
                World world = M.level(RabbidEntity.this);
                return super.canUse() && DisableTeleportProcedure.execute(entity);
            }

            public boolean canContinueToUse() {
                double x = M.getX(RabbidEntity.this);
                double y = M.getY(RabbidEntity.this);
                double z = M.getZ(RabbidEntity.this);
                Entity entity = RabbidEntity.this;
                World world = M.level(RabbidEntity.this);
                return super.canContinueToUse() && DisableTeleportProcedure.execute(entity);
            }
        });
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.0, Ingredient.of(new Object[] { BohModItems.PRETZEL.get() }), false));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2, false) {

            protected double getAttackReachSqr(EntityLivingBase entity) {
                return M.getBbWidth(this.mob) * M.getBbWidth(this.mob) + M.getBbWidth(entity);
            }
        });
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1.0) {

            public boolean canUse() {
                double x = M.getX(RabbidEntity.this);
                double y = M.getY(RabbidEntity.this);
                double z = M.getZ(RabbidEntity.this);
                Entity entity = RabbidEntity.this;
                World world = M.level(RabbidEntity.this);
                return super.canUse() && WanderTameProcedure.execute(entity);
            }

            public boolean canContinueToUse() {
                double x = M.getX(RabbidEntity.this);
                double y = M.getY(RabbidEntity.this);
                double z = M.getZ(RabbidEntity.this);
                Entity entity = RabbidEntity.this;
                World world = M.level(RabbidEntity.this);
                return super.canContinueToUse() && WanderTameProcedure.execute(entity);
            }
        });
        this.targetSelector.addGoal(5, new HurtByTargetGoal(this, new Class[0]));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new FloatGoal(this));
    }

    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    public SoundEvent getHurtSound(DamageSource ds) {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
    }

    public SoundEvent getDeathSoundEvent() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
    }

    public void die(DamageSource source) {
        super.die(source);
        RabbidEntityDiesProcedure.execute(M.level(this), M.getX(this), M.getY(this), M.getZ(this));
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

    public InteractionResult mobInteract(EntityPlayer sourceentity, InteractionHand hand) {
        ItemStack itemstack = M.getItemInHand(sourceentity, hand);
        InteractionResult retval = InteractionResult.sidedSuccess(M.isClientSide(M.level(this)));
        Item item = M.getItem(itemstack);
        if (M.getItem(itemstack) instanceof SpawnEggItem) {
            retval = super.mobInteract(sourceentity, hand);
        } else if (M.isClientSide(M.level(this))) {
            retval = (!M.isTame(this) || !M.isOwnedBy(this, sourceentity)) && !this.isFood(itemstack) ? InteractionResult.PASS : InteractionResult.sidedSuccess(M.isClientSide(M.level(this)));
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
                M.broadcastEntityEvent(M.level(this), this, (byte) 7);
            } else {
                M.broadcastEntityEvent(M.level(this), this, (byte) 6);
            }
            M.setPersistenceRequired(this);
            retval = InteractionResult.sidedSuccess(M.isClientSide(M.level(this)));
        } else {
            retval = super.mobInteract(sourceentity, hand);
            if (retval == InteractionResult.SUCCESS || retval == InteractionResult.CONSUME) {
                M.setPersistenceRequired(this);
            }
        }
        double x = M.getX(this);
        double y = M.getY(this);
        double z = M.getZ(this);
        Entity entity = this;
        World world = M.level(this);
        RabbidRightClickedOnEntityProcedure.execute(world, x, y, z, entity, sourceentity);
        return retval;
    }

    public void baseTick() {
        super.baseTick();
        RabbidOnEntityTickUpdateProcedure.execute(M.level(this), M.getX(this), M.getY(this), M.getZ(this), this);
        M.refreshDimensions(this);
    }

    public EntityDimensions getDimensions(Pose p_33597_) {
        return super.getDimensions(p_33597_).scale(1.0F);
    }

    public EntityAgeable getBreedOffspring(WorldServer serverWorld, EntityAgeable ageable) {
        RabbidEntity retval = (RabbidEntity) M.create(((EntityType) BohModEntities.RABBID.get()), serverWorld);
        M.finalizeSpawn(retval, serverWorld, M.getCurrentDifficultyAt(serverWorld, M.blockPosition(retval)), MobSpawnType.BREEDING, null, null);
        return retval;
    }

    public boolean isFood(ItemStack stack) {
        return M.contains(List.of(), M.getItem(stack));
    }

    public void aiStep() {
        super.aiStep();
        M.updateSwingTime(this);
    }

    public static void init() {
    }

    public static Builder createAttributes() {
        Builder builder = M.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
        builder = builder.add(Attributes.MAX_HEALTH, 15.0);
        builder = builder.add(Attributes.ARMOR, 0.0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 3.0);
        return builder.add(Attributes.FOLLOW_RANGE, 16.0);
    }

    private PlayState movementPredicate(AnimationState event) {
        if (this.animationprocedure.equals("empty")) {
            if (event.isMoving() || !(event.getLimbSwingAmount() > -0.15F) || !(event.getLimbSwingAmount() < 0.15F)) {
                return event.setAndContinue(RawAnimation.begin().thenLoop("walk"));
            } else {
                return M.isShiftKeyDown(this) ? event.setAndContinue(RawAnimation.begin().thenLoop("sleep")) : event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
            }
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
