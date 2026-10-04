package net.mcreator.boh.compat.entity;

import net.mcreator.boh.compat.loot.LootTables;
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
import net.mcreator.boh.compat.mc.world.entity.MobCategory;
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
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class BohTamableAnimal extends EntityTameable implements BohMob {
    protected final RandomSource random = RandomSource.wrap(this.rand);
    private static final EntityDataAccessor<Byte> MOB_FLAGS = new BohTamableAnimal.EntityDataAccessorFactory().flags();
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
            this.setSize(type.getWidth(), type.getHeight());
            this.isImmuneToFire = this.fireImmune();
        }

        this.goalSelector = new GoalSelector(this.tasks);
        this.targetSelector = new GoalSelector(this.targetTasks);
        this.lookControl = new LookControl(this);
        this.moveControl = new MoveControl(this);
        this.navigation = this.createNavigation(world);
        if (this.navigation != null) {
            this.navigator = this.navigation.vanilla();
        }

        this.experienceValue = 5;
        this.registerGoals();
    }

    @Override
    public EntityType<?> bohType() {
        return this.type;
    }

    @Override
    public SynchedEntityData bohEntityData() {
        return this.entityData;
    }

    @Override
    public GoalSelector bohGoals() {
        return this.goalSelector;
    }

    @Override
    public GoalSelector bohTargets() {
        return this.targetSelector;
    }

    @Override
    public MoveControl bohMoveControl() {
        return this.moveControl;
    }

    @Override
    public LookControl bohLookControl() {
        return this.lookControl;
    }

    @Override
    public PathNavigation bohNavigation() {
        return this.navigation;
    }

    public SynchedEntityData getEntityDataModern() {
        return this.entityData;
    }

    @Override
    public boolean isAggressive() {
        return this.entityData != null && (this.entityData.get(MOB_FLAGS) & 4) != 0;
    }

    @Override
    public void setAggressive(boolean aggressive) {
        this.setMobFlag(4, aggressive);
    }

    @Override
    public boolean isNoAi() {
        return this.noAi;
    }

    @Override
    public void setNoAi(boolean noAi) {
        this.noAi = noAi;
    }

    @Override
    public boolean isNoGravity() {
        return this.noGravity || this.entityData != null && (this.entityData.get(MOB_FLAGS) & 1) != 0;
    }

    @Override
    public void setNoGravity(boolean noGravity) {
        this.noGravity = noGravity;
        this.setMobFlag(1, noGravity);
    }

    private void setMobFlag(int bit, boolean on) {
        if (this.entityData != null) {
            byte b = this.entityData.get(MOB_FLAGS);
            this.entityData.set(MOB_FLAGS, (byte)(on ? b | bit : b & ~bit));
        }
    }

    @Override
    public void setSizeCompat(float w, float h) {
        this.setSize(w, h);
    }

    @Override
    public void refreshDimensions() {
        EntityDimensions d = this.getDimensions(Pose.STANDING);
        if (d != null) {
            if (this.lastDimensions == null || d.width != this.width || d.height != this.height) {
                this.lastDimensions = d;
                if (d.width != this.width || d.height != this.height) {
                    this.setSize(d.width, d.height);
                }
            }
        }
    }

    protected void defineSynchedData() {
    }

    protected void registerGoals() {
    }

    protected PathNavigation createNavigation(World world) {
        return new PathNavigation(this, this.navigator);
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

    protected void customServerAiStep() {
    }

    public boolean hurt(DamageSource source, float amount) {
        if (this.ignoreExplosion() && source.isExplosion()) {
            return false;
        } else {
            this.lastHurtSource = source;
            return super.attackEntityFrom(source, amount);
        }
    }

    public void die(DamageSource source) {
        this.lastDeathSource = source;
        super.onDeath(source);
    }

    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
    }

    protected void tickDeath() {
        super.onDeathUpdate();
    }

    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    public boolean isCreatureType(EnumCreatureType t, boolean forSpawnCount) {
        if (forSpawnCount && this.isNoDespawnRequired()) {
            return false;
        } else {
            MobCategory c = this.type == null ? null : this.type.getCategory();
            EnumCreatureType mine = c == null ? null : c.toVanilla();
            return mine != null ? mine == t : super.isCreatureType(t, forSpawnCount);
        }
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
        if (b != null) {
            this.playSound(b.stepSound.getStepResourcePath(), b.stepSound.getVolume() * 0.15F, b.stepSound.getPitch());
        }
    }

    public SpawnGroupData finalizeSpawn(World world, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData data, NBTTagCompound tag) {
        return data;
    }

    public EntityDimensions getDimensions(Pose pose) {
        return this.type != null ? this.type.getDimensions() : EntityDimensions.scalable(this.width, this.height);
    }

    public boolean doHurtTarget(Entity target) {
        IAttributeInstance dmgAttr = this.getEntityAttribute(SharedMonsterAttributes.attackDamage);
        float dmg = dmgAttr == null ? 2.0F : (float)dmgAttr.getAttributeValue();
        IAttributeInstance kbAttr = this.getAttributeMap().getAttributeInstance(Attributes.ATTACK_KNOCKBACK);
        float kb = kbAttr == null ? 0.0F : (float)kbAttr.getAttributeValue();
        if (target instanceof EntityLivingBase) {
            dmg += EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLivingBase)target);
            kb += EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase)target);
        }

        boolean hit = target.attackEntityFrom(DamageSource.causeMobDamage(this), dmg);
        if (hit) {
            if (kb > 0.0F && target instanceof EntityLivingBase) {
                target.addVelocity(
                    -MathHelper.sin(this.rotationYaw * (float) Math.PI / 180.0F) * kb * 0.5F,
                    0.1,
                    MathHelper.cos(this.rotationYaw * (float) Math.PI / 180.0F) * kb * 0.5F
                );
                this.motionX *= 0.6;
                this.motionZ *= 0.6;
            }

            int fire = EnchantmentHelper.getFireAspectModifier(this);
            if (fire > 0) {
                target.setFire(fire * 4);
            }

            this.setLastAttacker(target);
        }

        return hit;
    }

    @Override
    public void dropExperience() {
        if (!this.worldObj.isRemote) {
            if (this.recentlyHit > 0 || this.attackingPlayer != null) {
                int xp = this.getExperiencePoints(this.attackingPlayer);

                while (xp > 0) {
                    int split = EntityXPOrb.getXPSplit(xp);
                    xp -= split;
                    this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, split));
                }
            }
        }
    }

    public InteractionResult mobInteract(EntityPlayer player, InteractionHand hand) {
        return InteractionResult.PASS;
    }

    public void awardKillScore(Entity killed, int score, DamageSource source) {
    }

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
        return this.type != null && this.type.fireImmune();
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

    public void travel(Vec3 input) {
        this.travelVanilla((float)input.x, (float)input.z);
    }

    protected float getStandingEyeHeight(Pose pose, EntityDimensions dims) {
        return dims.height * 0.85F;
    }

    public boolean checkSpawnObstruction(World world) {
        return world.checkNoEntityCollision(this.boundingBox) && world.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !world.isAnyLiquid(this.boundingBox);
    }

    public boolean canCollideWith(Entity e) {
        return false;
    }

    public void startSeenByPlayer(EntityPlayerMP player) {
    }

    public void stopSeenByPlayer(EntityPlayerMP player) {
    }

    public void setPersistenceRequired() {
        this.persistenceRequired = true;
    }

    protected void entityInit() {
        super.entityInit();
        this.entityData = new SynchedEntityData(this);
        this.entityData.define(MOB_FLAGS, (byte)0);
        this.defineSynchedData();
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(Attributes.ARMOR);
        this.getAttributeMap().registerAttribute(Attributes.ATTACK_KNOCKBACK);
        this.getAttributeMap().registerAttribute(Attributes.FLYING_SPEED);
        this.getAttributeMap().registerAttribute(Attributes.ATTACK_SPEED);
        AttributeSupplier s = AttributeSupplier.forClass(this.getClass());
        if (s != null) {
            s.applyTo(this);
        }
    }

    public void writeEntityToNBT(NBTTagCompound tag) {
        this.addAdditionalSaveData(tag);
        tag.setBoolean("NoAI", this.noAi);
        tag.setBoolean("NoGravity", this.noGravity);
    }

    public void readEntityFromNBT(NBTTagCompound tag) {
        this.readAdditionalSaveData(tag);
        this.noAi = tag.getBoolean("NoAI");
        if (tag.hasKey("NoGravity")) {
            this.setNoGravity(tag.getBoolean("NoGravity"));
        }
    }

    public void onUpdate() {
        this.tick();
    }

    public void onEntityUpdate() {
        this.baseTick();
    }

    public void onLivingUpdate() {
        this.aiStep();
    }

    protected boolean isAIEnabled() {
        return !this.noAi;
    }

    protected void updateAITasks() {
        super.updateAITasks();
        this.customServerAiStep();
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        return this.hurt(source, amount);
    }

    public void onDeath(DamageSource source) {
        this.die(source);
    }

    protected void onDeathUpdate() {
        this.tickDeath();
    }

    protected void dropFewItems(boolean recentlyHit, int looting) {
        LootTables.dropEntityLoot(this, recentlyHit, looting);
        this.dropCustomDeathLoot(this.lastDeathSource != null ? this.lastDeathSource : DamageSource.generic, looting, recentlyHit);
    }

    public EnumCreatureAttribute getCreatureAttribute() {
        return this.getMobType().toVanilla();
    }

    protected boolean canDespawn() {
        EntityPlayer p = this.worldObj.getClosestPlayerToEntity(this, -1.0);
        double d = p == null ? Double.MAX_VALUE : p.getDistanceSqToEntity(this);
        return !this.persistenceRequired && this.removeWhenFarAway(d);
    }

    protected String getLivingSound() {
        SoundEvent s = this.getAmbientSound();
        return s != null && !s.legacyName().isEmpty() ? s.legacyName() : null;
    }

    protected String getHurtSound() {
        SoundEvent s = this.getHurtSound(this.lastHurtSource != null ? this.lastHurtSource : DamageSource.generic);
        return s != null && !s.legacyName().isEmpty() ? s.legacyName() : null;
    }

    protected String getDeathSound() {
        SoundEvent s = this.getDeathSoundEvent();
        return s != null && !s.legacyName().isEmpty() ? s.legacyName() : null;
    }

    protected void func_145780_a(int x, int y, int z, Block block) {
        this.playStepSound(new BlockPos(x, y, z), BlockState.of(block, this.worldObj.getBlockMetadata(x, y, z)));
    }

    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        SpawnGroupData sd = this.finalizeSpawn(
            this.worldObj,
            new DifficultyInstance(this.worldObj),
            MobSpawnType.NATURAL,
            data instanceof SpawnGroupData ? (SpawnGroupData)data : null,
            null
        );
        return (IEntityLivingData)(sd != null ? sd : data);
    }

    public boolean attackEntityAsMob(Entity target) {
        return this.doHurtTarget(target);
    }

    public int getTotalArmorValue() {
        IAttributeInstance armor = this.getAttributeMap().getAttributeInstance(Attributes.ARMOR);
        return super.getTotalArmorValue() + (armor == null ? 0 : (int)armor.getAttributeValue());
    }

    public boolean interact(EntityPlayer player) {
        InteractionResult r = this.mobInteract(player, InteractionHand.MAIN_HAND);
        return r != null && r.consumesAction() || super.interact(player);
    }

    public void onKillEntity(EntityLivingBase victim) {
        super.onKillEntity(victim);
        this.awardKillScore(victim, 0, victim.getLastAttacker() == this ? DamageSource.causeMobDamage(this) : DamageSource.generic);
    }

    public void onStruckByLightning(EntityLightningBolt bolt) {
        this.thunderHit(this.worldObj, bolt);
    }

    protected void fall(float distance) {
        this.causeFallDamage(distance, 1.0F, DamageSource.fall);
    }

    protected void updateFallState(double y, boolean onGround) {
        int bx = MathHelper.floor_double(this.posX);
        int by = MathHelper.floor_double(this.posY - 0.2 - this.yOffset);
        int bz = MathHelper.floor_double(this.posZ);
        this.checkFallDamage(
            y, onGround, BlockState.of(this.worldObj.getBlock(bx, by, bz), this.worldObj.getBlockMetadata(bx, by, bz)), new BlockPos(bx, by, bz)
        );
    }

    public boolean canBePushed() {
        return this.isPushable();
    }

    protected void collideWithEntity(Entity e) {
        this.doPush(e);
    }

    protected void collideWithNearbyEntities() {
        this.pushEntities();
    }

    public void onCollideWithPlayer(EntityPlayer player) {
        this.playerTouch(player);
    }

    public boolean isPushedByWater() {
        return this.isPushedByFluid();
    }

    public double getYOffset() {
        return this.getMyRidingOffset();
    }

    public double getMountedYOffset() {
        return this.getPassengersRidingOffset();
    }

    public float getEyeHeight() {
        return this.getStandingEyeHeight(Pose.STANDING, EntityDimensions.scalable(this.width, this.height));
    }

    public void travelToDimension(int dim) {
        if (this.canChangeDimensions()) {
            super.travelToDimension(dim);
        }
    }

    public boolean getCanSpawnHere() {
        SpawnPlacements.Data d = this.type == null ? null : SpawnPlacements.get(this.type);
        if (d == null) {
            return super.getCanSpawnHere();
        } else {
            BlockPos pos = new BlockPos(
                MathHelper.floor_double(this.posX),
                MathHelper.floor_double(this.boundingBox.minY),
                MathHelper.floor_double(this.posZ)
            );
            return SpawnPlacements.checkSpawnRules(this.type, this.worldObj, MobSpawnType.NATURAL, pos, RandomSource.wrap(this.rand))
                && this.checkSpawnObstruction(this.worldObj);
        }
    }

    public void moveEntityWithHeading(float strafe, float forward) {
        this.travel(new Vec3(strafe, 0.0, forward));
    }

    protected void travelVanilla(float strafe, float forward) {
        if (this.isNoGravity() && !this.isInWater() && !this.handleLavaMovement()) {
            this.moveFlying(strafe, forward, this.onGround ? this.getAIMoveSpeed() * 0.16277136F / 0.6F : this.jumpMovementFactor);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.91;
            this.motionY *= 0.91;
            this.motionZ *= 0.91;
            this.prevLimbSwingAmount = this.limbSwingAmount;
            double dx = this.posX - this.prevPosX;
            double dz = this.posZ - this.prevPosZ;
            float f = MathHelper.sqrt_double(dx * dx + dz * dz) * 4.0F;
            if (f > 1.0F) {
                f = 1.0F;
            }

            this.limbSwingAmount = this.limbSwingAmount + (f - this.limbSwingAmount) * 0.4F;
            this.limbSwing = this.limbSwing + this.limbSwingAmount;
        } else {
            super.moveEntityWithHeading(strafe, forward);
        }
    }

    protected void updateEntityActionState() {
        if (!this.noAi) {
            super.updateEntityActionState();
        }
    }

    public EntityAgeable getBreedOffspring(World world, EntityAgeable mate) {
        return null;
    }

    public boolean isFood(ItemStack stack) {
        return false;
    }

    public EntityAgeable createChild(EntityAgeable mate) {
        return this.getBreedOffspring(this.worldObj, mate);
    }

    public boolean isBreedingItem(ItemStack stack) {
        return stack != null && this.isFood(stack);
    }

    public boolean isBaby() {
        return this.isChild();
    }

    public boolean isTame() {
        return this.isTamed();
    }

    public void tame(EntityPlayer player) {
        this.setTamed(true);
        this.func_152115_b(player.getUniqueID().toString());
    }

    public boolean isOwnedBy(EntityLivingBase entity) {
        return entity != null && this.func_152114_e(entity);
    }

    public boolean isOrderedToSit() {
        return this.isSitting();
    }

    public void setOrderedToSit(boolean sit) {
        this.func_70907_r().setSitting(sit);
        this.setSitting(sit);
    }

    public void setInSittingPose(boolean sit) {
        this.setSitting(sit);
    }

    private static final class EntityDataAccessorFactory {
        EntityDataAccessor<Byte> flags() {
            return SynchedEntityData.defineId(BohTamableAnimal.class, EntityDataSerializers.BYTE);
        }
    }
}
