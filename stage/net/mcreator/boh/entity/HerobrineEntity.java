package net.mcreator.boh.entity;

import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.procedures.HerobrineOnEntityTickUpdateProcedure;
import net.minecraft.nbt.NBTTagCompound;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataAccessor;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataSerializers;
import net.mcreator.boh.compat.mc.network.syncher.SynchedEntityData;
import net.minecraft.util.DamageSource;
import net.mcreator.boh.compat.mc.world.damagesource.DamageTypes;
import net.mcreator.boh.compat.entity.BohAreaEffectCloud;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.MobType;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Attributes;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Builder;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.entity.player.EntityPlayer;
import net.mcreator.boh.compat.entity.BohAbstractArrow;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.world.World;
import net.mcreator.boh.compat.entity.BohMonster;
import net.mcreator.boh.compat.M;

public class HerobrineEntity extends BohMonster {

    public static final EntityDataAccessor<Integer> DATA_timer = SynchedEntityData.defineId(HerobrineEntity.class, EntityDataSerializers.INT);

    public HerobrineEntity(World world) {
        this((EntityType<HerobrineEntity>) BohModEntities.HEROBRINE.get(), world);
    }

    public HerobrineEntity(EntityType<HerobrineEntity> type, World world) {
        super(type, world);
        M.setMaxUpStep(this, 0.6F);
        M.set_xpReward(this, 0);
        M.setNoAi(this, false);
        M.setCustomName(this, Component.literal("Herobrine"));
        M.setCustomNameVisible(this, true);
        M.setPersistenceRequired(this);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_timer, 0);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, EntityPlayer.class, 10000.0F));
    }

    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    public double getMyRidingOffset() {
        return -0.35;
    }

    public boolean hurt(DamageSource damagesource, float amount) {
        if (M.is(damagesource, DamageTypes.IN_FIRE)) {
            return false;
        } else if (M.getDirectEntity(damagesource) instanceof BohAbstractArrow) {
            return false;
        } else if (M.getDirectEntity(damagesource) instanceof EntityPlayer) {
            return false;
        } else if (M.getDirectEntity(damagesource) instanceof EntityPotion || M.getDirectEntity(damagesource) instanceof BohAreaEffectCloud) {
            return false;
        } else if (M.is(damagesource, DamageTypes.FALL)) {
            return false;
        } else if (M.is(damagesource, DamageTypes.CACTUS)) {
            return false;
        } else if (M.is(damagesource, DamageTypes.DROWN)) {
            return false;
        } else if (M.is(damagesource, DamageTypes.LIGHTNING_BOLT)) {
            return false;
        } else if (M.is(damagesource, DamageTypes.EXPLOSION) || M.is(damagesource, DamageTypes.PLAYER_EXPLOSION)) {
            return false;
        } else if (M.is(damagesource, DamageTypes.TRIDENT)) {
            return false;
        } else if (M.is(damagesource, DamageTypes.FALLING_ANVIL)) {
            return false;
        } else if (M.is(damagesource, DamageTypes.DRAGON_BREATH)) {
            return false;
        } else {
            return !M.is(damagesource, DamageTypes.WITHER) && !M.is(damagesource, DamageTypes.WITHER_SKULL) ? super.hurt(damagesource, amount) : false;
        }
    }

    public boolean ignoreExplosion() {
        return true;
    }

    public boolean fireImmune() {
        return true;
    }

    public void addAdditionalSaveData(NBTTagCompound compound) {
        super.addAdditionalSaveData(compound);
        M.putInt(compound, "Datatimer", (Integer) this.entityData.get(DATA_timer));
    }

    public void readAdditionalSaveData(NBTTagCompound compound) {
        super.readAdditionalSaveData(compound);
        if (M.contains(compound, "Datatimer")) {
            M.set(this.entityData, DATA_timer, M.getInt(compound, "Datatimer"));
        }
    }

    public void baseTick() {
        super.baseTick();
        HerobrineOnEntityTickUpdateProcedure.execute(this);
    }

    public static void init() {
    }

    public static Builder createAttributes() {
        Builder builder = M.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
        builder = builder.add(Attributes.MAX_HEALTH, 1000.0);
        builder = builder.add(Attributes.ARMOR, 0.0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0.0);
        return builder.add(Attributes.FOLLOW_RANGE, 1000.0);
    }
}
