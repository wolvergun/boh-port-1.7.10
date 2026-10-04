package net.mcreator.boh.entity;

import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.procedures.SotirisMoveAIProcedure;
import net.mcreator.boh.procedures.SotirisOnEntityTickUpdateProcedure;
import net.mcreator.boh.procedures.SotirisThisEntityKillsAnotherOneProcedure;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController.State;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SotirisEntity extends Monster implements GeoEntity {
   public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(SotirisEntity.class, EntityDataSerializers.BOOLEAN);
   public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(SotirisEntity.class, EntityDataSerializers.STRING);
   public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(SotirisEntity.class, EntityDataSerializers.STRING);
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private boolean swinging;
   private boolean lastloop;
   private long lastSwing;
   public String animationprocedure = "empty";
   String prevAnim = "empty";

   public SotirisEntity(SpawnEntity packet, Level world) {
      this((EntityType<SotirisEntity>)BohModEntities.SOTIRIS.get(), world);
   }

   public SotirisEntity(EntityType<SotirisEntity> type, Level world) {
      super(type, world);
      this.xpReward = 0;
      this.setNoAi(false);
      this.setMaxUpStep(0.6F);
      this.setPersistenceRequired();
   }

   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(SHOOT, false);
      this.entityData.define(ANIMATION, "undefined");
      this.entityData.define(TEXTURE, "soteyeless");
   }

   public void setTexture(String texture) {
      this.entityData.set(TEXTURE, texture);
   }

   public String getTexture() {
      return (String)this.entityData.get(TEXTURE);
   }

   public Packet<ClientGamePacketListener> getAddEntityPacket() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   protected void registerGoals() {
      super.registerGoals();
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, false, false) {
         public boolean canUse() {
            double x = SotirisEntity.this.getX();
            double y = SotirisEntity.this.getY();
            double z = SotirisEntity.this.getZ();
            Entity entity = SotirisEntity.this;
            Level world = SotirisEntity.this.level();
            return super.canUse() && SotirisMoveAIProcedure.execute(entity);
         }

         public boolean canContinueToUse() {
            double x = SotirisEntity.this.getX();
            double y = SotirisEntity.this.getY();
            double z = SotirisEntity.this.getZ();
            Entity entity = SotirisEntity.this;
            Level world = SotirisEntity.this.level();
            return super.canContinueToUse() && SotirisMoveAIProcedure.execute(entity);
         }
      });
      this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2, false) {
         protected double getAttackReachSqr(LivingEntity entity) {
            return 6.25;
         }

         public boolean canUse() {
            double x = SotirisEntity.this.getX();
            double y = SotirisEntity.this.getY();
            double z = SotirisEntity.this.getZ();
            Entity entity = SotirisEntity.this;
            Level world = SotirisEntity.this.level();
            return super.canUse() && SotirisMoveAIProcedure.execute(entity);
         }

         public boolean canContinueToUse() {
            double x = SotirisEntity.this.getX();
            double y = SotirisEntity.this.getY();
            double z = SotirisEntity.this.getZ();
            Entity entity = SotirisEntity.this;
            Level world = SotirisEntity.this.level();
            return super.canContinueToUse() && SotirisMoveAIProcedure.execute(entity);
         }
      });
      this.targetSelector.addGoal(3, new HurtByTargetGoal(this) {
         public boolean canUse() {
            double x = SotirisEntity.this.getX();
            double y = SotirisEntity.this.getY();
            double z = SotirisEntity.this.getZ();
            Entity entity = SotirisEntity.this;
            Level world = SotirisEntity.this.level();
            return super.canUse() && SotirisMoveAIProcedure.execute(entity);
         }

         public boolean canContinueToUse() {
            double x = SotirisEntity.this.getX();
            double y = SotirisEntity.this.getY();
            double z = SotirisEntity.this.getZ();
            Entity entity = SotirisEntity.this;
            Level world = SotirisEntity.this.level();
            return super.canContinueToUse() && SotirisMoveAIProcedure.execute(entity);
         }
      });
      this.goalSelector.addGoal(4, new RandomStrollGoal(this, 0.8) {
         public boolean canUse() {
            double x = SotirisEntity.this.getX();
            double y = SotirisEntity.this.getY();
            double z = SotirisEntity.this.getZ();
            Entity entity = SotirisEntity.this;
            Level world = SotirisEntity.this.level();
            return super.canUse() && SotirisMoveAIProcedure.execute(entity);
         }

         public boolean canContinueToUse() {
            double x = SotirisEntity.this.getX();
            double y = SotirisEntity.this.getY();
            double z = SotirisEntity.this.getZ();
            Entity entity = SotirisEntity.this;
            Level world = SotirisEntity.this.level();
            return super.canContinueToUse() && SotirisMoveAIProcedure.execute(entity);
         }
      });
      this.goalSelector.addGoal(5, new RandomLookAroundGoal(this) {
         public boolean canUse() {
            double x = SotirisEntity.this.getX();
            double y = SotirisEntity.this.getY();
            double z = SotirisEntity.this.getZ();
            Entity entity = SotirisEntity.this;
            Level world = SotirisEntity.this.level();
            return super.canUse() && SotirisMoveAIProcedure.execute(entity);
         }

         public boolean canContinueToUse() {
            double x = SotirisEntity.this.getX();
            double y = SotirisEntity.this.getY();
            double z = SotirisEntity.this.getZ();
            Entity entity = SotirisEntity.this;
            Level world = SotirisEntity.this.level();
            return super.canContinueToUse() && SotirisMoveAIProcedure.execute(entity);
         }
      });
   }

   public MobType getMobType() {
      return MobType.UNDEFINED;
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
      return false;
   }

   public SoundEvent getHurtSound(DamageSource ds) {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
   }

   public SoundEvent getDeathSound() {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putString("Texture", this.getTexture());
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      if (compound.contains("Texture")) {
         this.setTexture(compound.getString("Texture"));
      }
   }

   public void awardKillScore(Entity entity, int score, DamageSource damageSource) {
      super.awardKillScore(entity, score, damageSource);
      SotirisThisEntityKillsAnotherOneProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
   }

   public void baseTick() {
      super.baseTick();
      SotirisOnEntityTickUpdateProcedure.execute(this);
      this.refreshDimensions();
   }

   public EntityDimensions getDimensions(Pose p_33597_) {
      return super.getDimensions(p_33597_).scale(1.0F);
   }

   public static void init() {
   }

   public static Builder createAttributes() {
      Builder builder = Mob.createMobAttributes();
      builder = builder.add(Attributes.MOVEMENT_SPEED, 0.0);
      builder = builder.add(Attributes.MAX_HEALTH, 60.0);
      builder = builder.add(Attributes.ARMOR, 0.0);
      builder = builder.add(Attributes.ATTACK_DAMAGE, 99.0);
      builder = builder.add(Attributes.FOLLOW_RANGE, 16.0);
      return builder.add(Attributes.KNOCKBACK_RESISTANCE, 100.0);
   }

   private PlayState movementPredicate(AnimationState event) {
      return this.animationprocedure.equals("empty") ? event.setAndContinue(RawAnimation.begin().thenLoop("idle")) : PlayState.STOP;
   }

   private PlayState procedurePredicate(AnimationState event) {
      if (!this.animationprocedure.equals("empty") && event.getController().getAnimationState() == State.STOPPED
         || !this.animationprocedure.equals(this.prevAnim) && !this.animationprocedure.equals("empty")) {
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
         this.remove(RemovalReason.KILLED);
         this.dropExperience();
      }
   }

   public String getSyncedAnimation() {
      return (String)this.entityData.get(ANIMATION);
   }

   public void setAnimation(String animation) {
      this.entityData.set(ANIMATION, animation);
   }

   public void registerControllers(ControllerRegistrar data) {
      data.add(new AnimationController[]{new AnimationController(this, "movement", 4, this::movementPredicate)});
      data.add(new AnimationController[]{new AnimationController(this, "procedure", 4, this::procedurePredicate)});
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }
}
