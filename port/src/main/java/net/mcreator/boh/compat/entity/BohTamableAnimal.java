package net.mcreator.boh.compat.entity;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataAccessor;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataSerializers;
import net.mcreator.boh.compat.mc.network.syncher.SynchedEntityData;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundEvents;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.DifficultyInstance;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.InteractionResult;
import net.mcreator.boh.compat.mc.world.entity.EntityDimensions;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.compat.mc.world.entity.MobType;
import net.mcreator.boh.compat.mc.world.entity.Pose;
import net.mcreator.boh.compat.mc.world.entity.SpawnGroupData;
import net.mcreator.boh.compat.mc.world.entity.SpawnPlacements;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.AttributeSupplier;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Attributes;
import net.mcreator.boh.compat.mc.world.entity.ai.control.LookControl;
import net.mcreator.boh.compat.mc.world.entity.ai.control.MoveControl;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.GoalSelector;
import net.mcreator.boh.compat.mc.world.entity.ai.navigation.PathNavigation;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.loot.LootTables;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * Compat base for mod monsters: exposes the 1.20 Mob/Monster lifecycle (defineSynchedData, registerGoals,
 * baseTick, aiStep, hurt, die, ...) on top of the 1.7.10 EntityMob methods that drive it.
 * Generated from BohMonster by tools/genbases.sh; do not edit by hand.
 */
public abstract class BohTamableAnimal extends net.minecraft.entity.passive.EntityTameable implements BohMob {

    /** 1.20 Entity.random. */
    protected final net.mcreator.boh.compat.mc.util.RandomSource random = net.mcreator.boh.compat.mc.util.RandomSource.wrap(rand);

    private static final EntityDataAccessor<Byte> MOB_FLAGS = new EntityDataAccessorFactory().flags();

    protected EntityType<?> type;
    protected SynchedEntityData entityData;
    public GoalSelector goalSelector;
    public GoalSelector targetSelector;
    protected MoveControl moveControl;
    protected LookControl lookControl;
    protected PathNavigation navigation;
    private boolean noAi;
    private boolean noGravity;
    private DamageSource lastDeathSource;
    private DamageSource lastHurtSource;
    private EntityDimensions lastDimensions;

    public BohTamableAnimal(EntityType<?> type, World world) {
        super(world);
        this.type = type;
        if (type != null) {
            setSize(type.getWidth(), type.getHeight());
            isImmuneToFire = fireImmune();
        }
        goalSelector = new GoalSelector(tasks);
        targetSelector = new GoalSelector(targetTasks);
        lookControl = new LookControl(this);
        moveControl = new MoveControl(this);
        navigation = createNavigation(world);
        if (navigation != null) navigator = navigation.vanilla();
        experienceValue = 5;
        registerGoals();
    }

    // ------------------------------------------------------------------ BohMob

    @Override
    public EntityType<?> bohType() {
        return type;
    }

    @Override
    public SynchedEntityData bohEntityData() {
        return entityData;
    }

    @Override
    public GoalSelector bohGoals() {
        return goalSelector;
    }

    @Override
    public GoalSelector bohTargets() {
        return targetSelector;
    }

    @Override
    public MoveControl bohMoveControl() {
        return moveControl;
    }

    @Override
    public LookControl bohLookControl() {
        return lookControl;
    }

    @Override
    public PathNavigation bohNavigation() {
        return navigation;
    }

    public SynchedEntityData getEntityDataModern() {
        return entityData;
    }

    @Override
    public boolean isAggressive() {
        return entityData != null && (entityData.get(MOB_FLAGS) & 4) != 0;
    }

    @Override
    public void setAggressive(boolean aggressive) {
        setMobFlag(4, aggressive);
    }

    @Override
    public boolean isNoAi() {
        return noAi;
    }

    @Override
    public void setNoAi(boolean noAi) {
        this.noAi = noAi;
    }

    @Override
    public boolean isNoGravity() {
        return noGravity || (entityData != null && (entityData.get(MOB_FLAGS) & 1) != 0);
    }

    @Override
    public void setNoGravity(boolean noGravity) {
        this.noGravity = noGravity;
        setMobFlag(1, noGravity);
    }

    private void setMobFlag(int bit, boolean on) {
        if (entityData == null) return;
        byte b = entityData.get(MOB_FLAGS);
        entityData.set(MOB_FLAGS, (byte) (on ? b | bit : b & ~bit));
    }

    @Override
    public void setSizeCompat(float w, float h) {
        setSize(w, h);
    }

    @Override
    public void refreshDimensions() {
        EntityDimensions d = getDimensions(Pose.STANDING);
        if (d == null) return;
        if (lastDimensions == null || d.width != width || d.height != height) {
            lastDimensions = d;
            if (d.width != width || d.height != height) setSize(d.width, d.height);
        }
    }

    // ------------------------------------------------------------------ modern hooks (overridden by the mod)

    protected void defineSynchedData() {}

    protected void registerGoals() {}

    protected PathNavigation createNavigation(World world) {
        return new PathNavigation(this, navigator);
    }

    public void addAdditionalSaveData(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
    }

    public void readAdditionalSaveData(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
    }

    public void tick() {
        super.onUpdate();
    }

    public void baseTick() {
        super.onEntityUpdate();
    }

    public void aiStep() {
        super.onLivingUpdate();
    }

    protected void customServerAiStep() {}

    public boolean hurt(DamageSource source, float amount) {
        if (ignoreExplosion() && source.isExplosion()) return false;
        lastHurtSource = source;
        return super.attackEntityFrom(source, amount);
    }

    public void die(DamageSource source) {
        lastDeathSource = source;
        super.onDeath(source);
    }

    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {}

    protected void tickDeath() {
        super.onDeathUpdate();
    }

    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    /** Spawn caps: count this mob in its 1.20 MobCategory (vanilla only checks EntityAnimal / IMob classes). */
    @Override
    public boolean isCreatureType(net.minecraft.entity.EnumCreatureType t, boolean forSpawnCount) {
        if (forSpawnCount && isNoDespawnRequired()) return false;
        net.mcreator.boh.compat.mc.world.entity.MobCategory c = type == null ? null : type.getCategory();
        net.minecraft.entity.EnumCreatureType mine = c == null ? null : c.toVanilla();
        return mine != null ? mine == t : super.isCreatureType(t, forSpawnCount);
    }

    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return true;
    }

    public SoundEvent getAmbientSound() {
        return null;
    }

    public SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.GENERIC_HURT;
    }

    public SoundEvent getDeathSoundEvent() {
        return SoundEvents.GENERIC_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        Block b = state.getBlock();
        if (b != null) playSound(b.stepSound.getStepResourcePath(), b.stepSound.getVolume() * 0.15F, b.stepSound.getPitch());
    }

    public SpawnGroupData finalizeSpawn(World world, DifficultyInstance difficulty, MobSpawnType reason,
        SpawnGroupData data, NBTTagCompound tag) {
        return data;
    }

    public EntityDimensions getDimensions(Pose pose) {
        return type != null ? type.getDimensions() : EntityDimensions.scalable(width, height);
    }

    /** Port of 1.20 Mob.doHurtTarget (attack damage + attack knockback + enchantments). */
    public boolean doHurtTarget(Entity target) {
        IAttributeInstance dmgAttr = getEntityAttribute(net.minecraft.entity.SharedMonsterAttributes.attackDamage);
        float dmg = dmgAttr == null ? 2.0F : (float) dmgAttr.getAttributeValue();
        IAttributeInstance kbAttr = getAttributeMap().getAttributeInstance(Attributes.ATTACK_KNOCKBACK);
        float kb = kbAttr == null ? 0 : (float) kbAttr.getAttributeValue();
        if (target instanceof EntityLivingBase) {
            dmg += net.minecraft.enchantment.EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLivingBase) target);
            kb += net.minecraft.enchantment.EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase) target);
        }
        boolean hit = target.attackEntityFrom(DamageSource.causeMobDamage(this), dmg);
        if (hit) {
            if (kb > 0 && target instanceof EntityLivingBase) {
                target.addVelocity(-MathHelper.sin(rotationYaw * (float) Math.PI / 180.0F) * kb * 0.5F, 0.1,
                    MathHelper.cos(rotationYaw * (float) Math.PI / 180.0F) * kb * 0.5F);
                motionX *= 0.6;
                motionZ *= 0.6;
            }
            int fire = net.minecraft.enchantment.EnchantmentHelper.getFireAspectModifier(this);
            if (fire > 0) target.setFire(fire * 4);
            setLastAttacker(target);
        }
        return hit;
    }

    /** 1.20 dropExperience(): the MCreator tickDeath overrides call this at deathTime 20. */
    @Override
    public void dropExperience() {
        if (worldObj.isRemote) return;
        if (recentlyHit > 0 || attackingPlayer != null) {
            int xp = getExperiencePoints(attackingPlayer);
            while (xp > 0) {
                int split = net.minecraft.entity.item.EntityXPOrb.getXPSplit(xp);
                xp -= split;
                worldObj.spawnEntityInWorld(new net.minecraft.entity.item.EntityXPOrb(worldObj, posX, posY, posZ, split));
            }
        }
    }

    public InteractionResult mobInteract(EntityPlayer player, InteractionHand hand) {
        return InteractionResult.PASS;
    }

    public void awardKillScore(Entity killed, int score, DamageSource source) {}

    public void thunderHit(World world, EntityLightningBolt bolt) {
        super.onStruckByLightning(bolt);
    }

    public boolean causeFallDamage(float distance, float multiplier, DamageSource source) {
        super.fall(distance * multiplier);
        return true;
    }

    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
        super.updateFallState(y, onGround);
    }

    public boolean isPushable() {
        return super.canBePushed();
    }

    protected void doPush(Entity e) {
        super.collideWithEntity(e);
    }

    protected void pushEntities() {
        super.collideWithNearbyEntities();
    }

    public void playerTouch(EntityPlayer player) {
        super.onCollideWithPlayer(player);
    }

    public boolean isPushedByFluid() {
        return true;
    }

    public boolean ignoreExplosion() {
        return false;
    }

    public boolean fireImmune() {
        return type != null && type.fireImmune();
    }

    public boolean canChangeDimensions() {
        return true;
    }

    public double getMyRidingOffset() {
        return super.getYOffset();
    }

    public double getPassengersRidingOffset() {
        return super.getMountedYOffset();
    }

    public void travel(net.mcreator.boh.compat.mc.world.phys.Vec3 input) {
        travelVanilla((float) input.x, (float) input.z);
    }

    protected float getStandingEyeHeight(Pose pose, EntityDimensions dims) {
        return dims.height * 0.85F;
    }

    public boolean checkSpawnObstruction(World world) {
        return world.checkNoEntityCollision(boundingBox) && world.getCollidingBoundingBoxes(this, boundingBox).isEmpty()
            && !world.isAnyLiquid(boundingBox);
    }

    public boolean canCollideWith(Entity e) {
        return false;
    }

    public void startSeenByPlayer(net.minecraft.entity.player.EntityPlayerMP player) {}

    public void stopSeenByPlayer(net.minecraft.entity.player.EntityPlayerMP player) {}

    public void setPersistenceRequired() {
        persistenceRequired = true;
    }

    // ------------------------------------------------------------------ 1.7.10 bridge

    @Override
    protected void entityInit() {
        super.entityInit();
        entityData = new SynchedEntityData(this);
        entityData.define(MOB_FLAGS, (byte) 0);
        defineSynchedData();
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getAttributeMap().registerAttribute(Attributes.ARMOR);
        getAttributeMap().registerAttribute(Attributes.ATTACK_KNOCKBACK);
        getAttributeMap().registerAttribute(Attributes.FLYING_SPEED);
        getAttributeMap().registerAttribute(Attributes.ATTACK_SPEED);
        AttributeSupplier s = AttributeSupplier.forClass(getClass());
        if (s != null) s.applyTo(this);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tag) {
        addAdditionalSaveData(tag);
        tag.setBoolean("NoAI", noAi);
        tag.setBoolean("NoGravity", noGravity);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tag) {
        readAdditionalSaveData(tag);
        noAi = tag.getBoolean("NoAI");
        if (tag.hasKey("NoGravity")) setNoGravity(tag.getBoolean("NoGravity"));
    }

    @Override
    public void onUpdate() {
        tick();
    }

    @Override
    public void onEntityUpdate() {
        baseTick();
    }

    @Override
    public void onLivingUpdate() {
        aiStep();
    }

    @Override
    protected boolean isAIEnabled() {
        return !noAi;
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        customServerAiStep();
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        return hurt(source, amount);
    }

    @Override
    public void onDeath(DamageSource source) {
        die(source);
    }

    @Override
    protected void onDeathUpdate() {
        tickDeath();
    }

    @Override
    protected void dropFewItems(boolean recentlyHit, int looting) {
        LootTables.dropEntityLoot(this, recentlyHit, looting);
        dropCustomDeathLoot(lastDeathSource != null ? lastDeathSource : DamageSource.generic, looting, recentlyHit);
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return getMobType().toVanilla();
    }

    @Override
    protected boolean canDespawn() {
        EntityPlayer p = worldObj.getClosestPlayerToEntity(this, -1.0);
        double d = p == null ? Double.MAX_VALUE : p.getDistanceSqToEntity(this);
        return !persistenceRequired && removeWhenFarAway(d);
    }

    @Override
    protected String getLivingSound() {
        SoundEvent s = getAmbientSound();
        return s == null || s.legacyName().isEmpty() ? null : s.legacyName();
    }

    @Override
    protected String getHurtSound() {
        SoundEvent s = getHurtSound(lastHurtSource != null ? lastHurtSource : DamageSource.generic);
        return s == null || s.legacyName().isEmpty() ? null : s.legacyName();
    }

    @Override
    protected String getDeathSound() {
        SoundEvent s = getDeathSoundEvent();
        return s == null || s.legacyName().isEmpty() ? null : s.legacyName();
    }

    @Override
    protected void func_145780_a(int x, int y, int z, Block block) {
        playStepSound(new BlockPos(x, y, z), BlockState.of(block, worldObj.getBlockMetadata(x, y, z)));
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        SpawnGroupData sd = finalizeSpawn(worldObj, new DifficultyInstance(worldObj), MobSpawnType.NATURAL,
            data instanceof SpawnGroupData ? (SpawnGroupData) data : null, null);
        return sd != null ? sd : data;
    }

    @Override
    public boolean attackEntityAsMob(Entity target) {
        return doHurtTarget(target);
    }

    @Override
    public int getTotalArmorValue() {
        IAttributeInstance armor = getAttributeMap().getAttributeInstance(Attributes.ARMOR);
        return super.getTotalArmorValue() + (armor == null ? 0 : (int) armor.getAttributeValue());
    }

    @Override
    public boolean interact(EntityPlayer player) {
        InteractionResult r = mobInteract(player, InteractionHand.MAIN_HAND);
        return r != null && r.consumesAction() || super.interact(player);
    }

    @Override
    public void onKillEntity(EntityLivingBase victim) {
        super.onKillEntity(victim);
        awardKillScore(victim, 0, victim.getLastAttacker() == this ? DamageSource.causeMobDamage(this) : DamageSource.generic);
    }

    @Override
    public void onStruckByLightning(EntityLightningBolt bolt) {
        thunderHit(worldObj, bolt);
    }

    @Override
    protected void fall(float distance) {
        causeFallDamage(distance, 1.0F, DamageSource.fall);
    }

    @Override
    protected void updateFallState(double y, boolean onGround) {
        int bx = MathHelper.floor_double(posX), by = MathHelper.floor_double(posY - 0.2 - yOffset),
            bz = MathHelper.floor_double(posZ);
        checkFallDamage(y, onGround, BlockState.of(worldObj.getBlock(bx, by, bz), worldObj.getBlockMetadata(bx, by, bz)),
            new BlockPos(bx, by, bz));
    }

    @Override
    public boolean canBePushed() {
        return isPushable();
    }

    @Override
    protected void collideWithEntity(Entity e) {
        doPush(e);
    }

    @Override
    protected void collideWithNearbyEntities() {
        pushEntities();
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer player) {
        playerTouch(player);
    }

    @Override
    public boolean isPushedByWater() {
        return isPushedByFluid();
    }

    @Override
    public double getYOffset() {
        return getMyRidingOffset();
    }

    @Override
    public double getMountedYOffset() {
        return getPassengersRidingOffset();
    }

    @Override
    public float getEyeHeight() {
        return getStandingEyeHeight(Pose.STANDING, EntityDimensions.scalable(width, height));
    }

    @Override
    public void travelToDimension(int dim) {
        if (canChangeDimensions()) super.travelToDimension(dim);
    }

    @Override
    public boolean getCanSpawnHere() {
        SpawnPlacements.Data d = type == null ? null : SpawnPlacements.get(type);
        if (d == null) return super.getCanSpawnHere();
        BlockPos pos = new BlockPos(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY),
            MathHelper.floor_double(posZ));
        return SpawnPlacements.checkSpawnRules(type, worldObj, MobSpawnType.NATURAL, pos, RandomSource.wrap(rand))
            && checkSpawnObstruction(worldObj);
    }

    @Override
    public void moveEntityWithHeading(float strafe, float forward) {
        travel(new net.mcreator.boh.compat.mc.world.phys.Vec3(strafe, 0, forward));
    }

    /** Vanilla movement, minus gravity while noGravity is set. */
    protected void travelVanilla(float strafe, float forward) {
        if (!isNoGravity() || isInWater() || handleLavaMovement()) {
            super.moveEntityWithHeading(strafe, forward);
            return;
        }
        moveFlying(strafe, forward, onGround ? getAIMoveSpeed() * 0.16277136F / 0.6F : jumpMovementFactor);
        moveEntity(motionX, motionY, motionZ);
        motionX *= 0.91;
        motionY *= 0.91;
        motionZ *= 0.91;
        prevLimbSwingAmount = limbSwingAmount;
        double dx = posX - prevPosX, dz = posZ - prevPosZ;
        float f = MathHelper.sqrt_double(dx * dx + dz * dz) * 4.0F;
        if (f > 1.0F) f = 1.0F;
        limbSwingAmount += (f - limbSwingAmount) * 0.4F;
        limbSwing += limbSwingAmount;
    }

    @Override
    protected void updateEntityActionState() {
        if (!noAi) super.updateEntityActionState();
    }

    /** Factory for the base class' own synced flag, allocated before the mod's accessors. */
    private static final class EntityDataAccessorFactory {

        EntityDataAccessor<Byte> flags() {
            return SynchedEntityData.defineId(BohTamableAnimal.class, EntityDataSerializers.BYTE);
        }
    }

    // ------------------------------------------------------------------ breeding + taming (1.20 TamableAnimal)

    public net.minecraft.entity.EntityAgeable getBreedOffspring(World world, net.minecraft.entity.EntityAgeable mate) {
        return null;
    }

    public boolean isFood(net.minecraft.item.ItemStack stack) {
        return false;
    }

    @Override
    public net.minecraft.entity.EntityAgeable createChild(net.minecraft.entity.EntityAgeable mate) {
        return getBreedOffspring(worldObj, mate);
    }

    @Override
    public boolean isBreedingItem(net.minecraft.item.ItemStack stack) {
        return stack != null && isFood(stack);
    }

    public boolean isBaby() {
        return isChild();
    }

    public boolean isTame() {
        return isTamed();
    }

    public void tame(EntityPlayer player) {
        setTamed(true);
        func_152115_b(player.getUniqueID().toString());
    }

    public boolean isOwnedBy(EntityLivingBase entity) {
        return entity != null && func_152114_e(entity);
    }

    public boolean isOrderedToSit() {
        return isSitting();
    }

    public void setOrderedToSit(boolean sit) {
        func_70907_r().setSitting(sit);
        setSitting(sit);
    }

    public void setInSittingPose(boolean sit) {
        setSitting(sit);
    }
}
