package net.mcreator.boh.entity;

import java.util.List;
import javax.annotation.Nullable;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.procedures.DeerBoundingBoxScaleProcedure;
import net.mcreator.boh.procedures.DeerEntityDiesProcedure;
import net.mcreator.boh.procedures.DeerEntityIsHurtProcedure;
import net.mcreator.boh.procedures.DeerOnEntityTickUpdateProcedure;
import net.mcreator.boh.procedures.DeerOnInitialEntitySpawnProcedure;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.nbt.NBTTagCompound;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataAccessor;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataSerializers;
import net.mcreator.boh.compat.mc.network.syncher.SynchedEntityData;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.world.DifficultyInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EntityDimensions;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.compat.mc.world.entity.MobType;
import net.mcreator.boh.compat.mc.world.entity.Pose;
import net.mcreator.boh.compat.mc.world.entity.SpawnGroupData;
import net.mcreator.boh.compat.mc.world.entity.SpawnPlacements;
import net.mcreator.boh.compat.mc.world.entity.RemovalReason;
import net.mcreator.boh.compat.mc.world.entity.Type;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Attributes;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Builder;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.AvoidEntityGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.BreedGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.EatBlockGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.FloatGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.FollowParentGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.PanicGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.RandomLookAroundGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.RandomStrollGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.TemptGoal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.mc.world.item.Items;
import net.mcreator.boh.compat.mc.world.item.crafting.Ingredient;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.levelgen.Types;
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
import net.mcreator.boh.compat.entity.BohAnimal;
import net.mcreator.boh.compat.M;

public class DeerEntity extends BohAnimal implements GeoEntity {

    public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(DeerEntity.class, EntityDataSerializers.BOOLEAN);

    public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(DeerEntity.class, EntityDataSerializers.STRING);

    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(DeerEntity.class, EntityDataSerializers.STRING);

    public static final EntityDataAccessor<Integer> DATA_scale = SynchedEntityData.defineId(DeerEntity.class, EntityDataSerializers.INT);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private boolean swinging;

    private boolean lastloop;

    private long lastSwing;

    public String animationprocedure = "empty";

    String prevAnim = "empty";

    public DeerEntity(World world) {
        this((EntityType<DeerEntity>) BohModEntities.DEER.get(), world);
    }

    public DeerEntity(EntityType<DeerEntity> type, World world) {
        super(type, world);
        M.set_xpReward(this, 3);
        M.setNoAi(this, false);
        M.setMaxUpStep(this, 0.6F);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHOOT, false);
        this.entityData.define(ANIMATION, "undefined");
        this.entityData.define(TEXTURE, "deer");
        this.entityData.define(DATA_scale, 1);
    }

    public void setTexture(String texture) {
        M.set(this.entityData, TEXTURE, texture);
    }

    public String getTexture() {
        return (String) this.entityData.get(TEXTURE);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(2, new FollowParentGoal(this, 0.8));
        this.goalSelector.addGoal(3, new EatBlockGoal(this));
        this.goalSelector.addGoal(4, new PanicGoal(this, 1.0));
        this.goalSelector.addGoal(5, new AvoidEntityGoal(this, EntityPlayer.class, 5.0F, 4.0, 2.0));
        this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new FloatGoal(this));
        this.goalSelector.addGoal(9, new TemptGoal(this, 1.0, Ingredient.of(new Object[] { M.asItem(Blocks.WHEAT) }), true));
        this.goalSelector.addGoal(10, new TemptGoal(this, 1.0, Ingredient.of(new Object[] { Items.WHEAT }), true));
    }

    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    public SoundEvent getAmbientSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:deer_idle"));
    }

    public void playStepSound(BlockPos pos, BlockState blockIn) {
        M.playSound(this, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.horse.step")), 0.15F, 1.0F);
    }

    public SoundEvent getHurtSound(DamageSource ds) {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:deer_hurt"));
    }

    public SoundEvent getDeathSoundEvent() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:deer_death"));
    }

    public boolean hurt(DamageSource source, float amount) {
        DeerEntityIsHurtProcedure.execute(this);
        return super.hurt(source, amount);
    }

    public void die(DamageSource source) {
        super.die(source);
        DeerEntityDiesProcedure.execute(M.level(this), M.getX(this), M.getY(this), M.getZ(this), this);
    }

    public SpawnGroupData finalizeSpawn(World world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable NBTTagCompound tag) {
        SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
        DeerOnInitialEntitySpawnProcedure.execute(world, this);
        return retval;
    }

    public void addAdditionalSaveData(NBTTagCompound compound) {
        super.addAdditionalSaveData(compound);
        M.putString(compound, "Texture", this.getTexture());
        M.putInt(compound, "Datascale", (Integer) this.entityData.get(DATA_scale));
    }

    public void readAdditionalSaveData(NBTTagCompound compound) {
        super.readAdditionalSaveData(compound);
        if (M.contains(compound, "Texture")) {
            this.setTexture(M.getString(compound, "Texture"));
        }
        if (M.contains(compound, "Datascale")) {
            M.set(this.entityData, DATA_scale, M.getInt(compound, "Datascale"));
        }
    }

    public void baseTick() {
        super.baseTick();
        DeerOnEntityTickUpdateProcedure.execute(this);
        M.refreshDimensions(this);
    }

    public EntityDimensions getDimensions(Pose p_33597_) {
        Entity entity = this;
        World world = M.level(this);
        double x = M.getX(this);
        double y = M.getY(entity);
        double z = M.getZ(entity);
        return super.getDimensions(p_33597_).scale((float) DeerBoundingBoxScaleProcedure.execute(entity));
    }

    public EntityAgeable getBreedOffspring(WorldServer serverWorld, EntityAgeable ageable) {
        DeerEntity retval = (DeerEntity) M.create(((EntityType) BohModEntities.DEER.get()), serverWorld);
        retval.finalizeSpawn(serverWorld, M.getCurrentDifficultyAt(serverWorld, M.blockPosition(retval)), MobSpawnType.BREEDING, null, null);
        return retval;
    }

    public boolean isFood(ItemStack stack) {
        return M.contains(List.of(M.asItem(Blocks.WHEAT), Items.WHEAT), M.getItem(stack));
    }

    public void aiStep() {
        super.aiStep();
        M.updateSwingTime(this);
    }

    public static void init() {
        SpawnPlacements.register((EntityType) BohModEntities.DEER.get(), Type.NO_RESTRICTIONS, Types.MOTION_BLOCKING_NO_LEAVES, M::checkMobSpawnRules);
    }

    public static Builder createAttributes() {
        Builder builder = M.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
        builder = builder.add(Attributes.MAX_HEALTH, 10.0);
        builder = builder.add(Attributes.ARMOR, 0.0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 3.0);
        return builder.add(Attributes.FOLLOW_RANGE, 16.0);
    }

    private PlayState movementPredicate(AnimationState event) {
        if (this.animationprocedure.equals("empty")) {
            if (event.isMoving() || !(event.getLimbSwingAmount() > -0.15F) || !(event.getLimbSwingAmount() < 0.15F)) {
                return event.setAndContinue(RawAnimation.begin().thenLoop("walk"));
            } else {
                return M.isShiftKeyDown(this) ? event.setAndContinue(RawAnimation.begin().thenLoop("sprint")) : event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
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
        if (this.deathTime == 40) {
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
