package net.mcreator.boh.compat.entity;

import java.util.List;

import io.netty.buffer.ByteBuf;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.phys.BlockHitResult;
import net.mcreator.boh.compat.mc.world.phys.EntityHitResult;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

/**
 * 1.20 AbstractArrow for the mod's projectiles: the 1.7.10 arrow flight, with the modern hooks (onHitEntity,
 * onHitBlock, doPostHurtEffects, getPickupItem) called at the matching points.
 */
public abstract class BohAbstractArrow extends Entity implements IEntityAdditionalSpawnData {

    /** 1.20 Entity.random. */
    protected final net.mcreator.boh.compat.mc.util.RandomSource random = net.mcreator.boh.compat.mc.util.RandomSource.wrap(rand);

    protected EntityType<?> type;
    public Entity shootingEntity;
    public int canBePickedUp;
    public boolean inGround;
    protected int ticksInGround;
    private int ticksInAir;
    private double damage = 2.0;
    private int knockback;
    private boolean critical;
    private int xTile = -1, yTile = -1, zTile = -1;
    private Block inTile;
    private int inData;
    public int arrowShake;
    private boolean silent;

    public BohAbstractArrow(EntityType<?> type, World world) {
        super(world);
        this.type = type;
        renderDistanceWeight = 10.0;
        setSize(type != null ? type.getWidth() : 0.5F, type != null ? type.getHeight() : 0.5F);
    }

    public BohAbstractArrow(EntityType<?> type, double x, double y, double z, World world) {
        this(type, world);
        setPosition(x, y, z);
        yOffset = 0.0F;
    }

    public BohAbstractArrow(EntityType<?> type, EntityLivingBase shooter, World world) {
        this(type, shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.1, shooter.posZ, world);
        shootingEntity = shooter;
        if (shooter instanceof EntityPlayer) canBePickedUp = 1;
    }

    @Override
    protected void entityInit() {
        dataWatcher.addObject(16, (byte) 0);
    }

    // ------------------------------------------------------------------ 1.20 API

    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        float len = MathHelper.sqrt_double(x * x + y * y + z * z);
        x /= len;
        y /= len;
        z /= len;
        x += rand.nextGaussian() * 0.0075 * inaccuracy;
        y += rand.nextGaussian() * 0.0075 * inaccuracy;
        z += rand.nextGaussian() * 0.0075 * inaccuracy;
        motionX = x * velocity;
        motionY = y * velocity;
        motionZ = z * velocity;
        float h = MathHelper.sqrt_double(x * x + z * z);
        prevRotationYaw = rotationYaw = (float) (Math.atan2(x, z) * 180.0 / Math.PI);
        prevRotationPitch = rotationPitch = (float) (Math.atan2(y, h) * 180.0 / Math.PI);
        ticksInGround = 0;
    }

    public void tick() {
        super.onUpdate();
        arrowFlight();
    }

    protected void onHitEntity(EntityHitResult hit) {
        Entity target = hit.getEntity();
        float speed = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
        int dmg = MathHelper.ceiling_double_int(speed * damage);
        if (critical) dmg += rand.nextInt(dmg / 2 + 2);
        DamageSource src = shootingEntity == null ? DamageSource.causeIndirectMagicDamage(this, this)
            : new net.minecraft.util.EntityDamageSourceIndirect("arrow", this, shootingEntity).setProjectile();
        if (isBurning() && !(target instanceof net.minecraft.entity.monster.EntityEnderman)) target.setFire(5);
        if (target.attackEntityFrom(src, dmg)) {
            if (target instanceof EntityLivingBase) {
                EntityLivingBase living = (EntityLivingBase) target;
                if (!worldObj.isRemote) living.setArrowCountInEntity(living.getArrowCountInEntity() + 1);
                if (knockback > 0) {
                    float h = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
                    if (h > 0) target.addVelocity(motionX * knockback * 0.6 / h, 0.1, motionZ * knockback * 0.6 / h);
                }
                if (shootingEntity instanceof EntityLivingBase) {
                    EnchantmentHelper.func_151384_a(living, shootingEntity);
                    EnchantmentHelper.func_151385_b((EntityLivingBase) shootingEntity, living);
                }
                doPostHurtEffects(living);
                if (shootingEntity != null && living != shootingEntity && living instanceof EntityPlayer && shootingEntity instanceof EntityPlayerMP)
                    ((EntityPlayerMP) shootingEntity).playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(6, 0.0F));
            }
            if (!silent) playSound("random.bowhit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
            if (!(target instanceof net.minecraft.entity.monster.EntityEnderman)) setDead();
        } else {
            motionX *= -0.1;
            motionY *= -0.1;
            motionZ *= -0.1;
            rotationYaw += 180.0F;
            prevRotationYaw += 180.0F;
            ticksInAir = 0;
        }
    }

    protected void onHitBlock(BlockHitResult hit) {
        BlockPos p = hit.getBlockPos();
        xTile = p.getX();
        yTile = p.getY();
        zTile = p.getZ();
        inTile = worldObj.getBlock(xTile, yTile, zTile);
        inData = worldObj.getBlockMetadata(xTile, yTile, zTile);
        motionX = hit.getLocation().x - posX;
        motionY = hit.getLocation().y - posY;
        motionZ = hit.getLocation().z - posZ;
        float len = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
        posX -= motionX / len * 0.05;
        posY -= motionY / len * 0.05;
        posZ -= motionZ / len * 0.05;
        if (!silent) playSound("random.bowhit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
        inGround = true;
        arrowShake = 7;
        critical = false;
        if (inTile.getMaterial() != Material.air) inTile.onEntityCollidedWithBlock(worldObj, xTile, yTile, zTile, this);
    }

    protected void doPostHurtEffects(EntityLivingBase target) {}

    protected ItemStack getPickupItem() {
        return M.EMPTY;
    }

    public ItemStack getItem() {
        return M.EMPTY;
    }

    public void playerTouch(EntityPlayer player) {
        if (worldObj.isRemote || !inGround || arrowShake > 0) return;
        boolean pickup = canBePickedUp == 1 || canBePickedUp == 2 && player.capabilities.isCreativeMode;
        ItemStack s = M.legacy(getPickupItem());
        if (canBePickedUp == 1 && (s == null || !player.inventory.addItemStackToInventory(s.copy()))) pickup = false;
        if (pickup) {
            playSound("random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            player.onItemPickup(this, 1);
            setDead();
        }
    }

    public void setBaseDamage(double d) {
        damage = d;
    }

    public double getBaseDamage() {
        return damage;
    }

    public void setKnockback(int k) {
        knockback = k;
    }

    public void setCritArrow(boolean b) {
        critical = b;
        dataWatcher.updateObject(16, (byte) (b ? 1 : 0));
    }

    public boolean isCritArrow() {
        return dataWatcher.getWatchableObjectByte(16) == 1;
    }

    public void setSilentArrow(boolean b) {
        silent = b;
    }

    public void setOwner(Entity owner) {
        shootingEntity = owner;
    }

    public Entity getOwner() {
        return shootingEntity;
    }

    /** 1.20 Projectile.canHitEntity. */
    public boolean canHitEntity(Entity e) {
        return e != null && e.canBeCollidedWith() && !e.isDead && (e != shootingEntity || ticksExisted >= 5);
    }

    // ------------------------------------------------------------------ 1.7.10 bridge

    @Override
    public void onUpdate() {
        tick();
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer player) {
        playerTouch(player);
    }

    @Override
    public void setVelocity(double x, double y, double z) {
        motionX = x;
        motionY = y;
        motionZ = z;
        if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F) {
            float h = MathHelper.sqrt_double(x * x + z * z);
            prevRotationYaw = rotationYaw = (float) (Math.atan2(x, z) * 180.0 / Math.PI);
            prevRotationPitch = rotationPitch = (float) (Math.atan2(y, h) * 180.0 / Math.PI);
            ticksInGround = 0;
        }
    }

    /** Port of the 1.7.10 EntityArrow.onUpdate flight, routing hits through the 1.20 hooks. */
    private void arrowFlight() {
        if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F) {
            float h = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
            prevRotationYaw = rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0 / Math.PI);
            prevRotationPitch = rotationPitch = (float) (Math.atan2(motionY, h) * 180.0 / Math.PI);
        }
        if (xTile >= 0) {
            Block b = worldObj.getBlock(xTile, yTile, zTile);
            if (b.getMaterial() != Material.air) {
                b.setBlockBoundsBasedOnState(worldObj, xTile, yTile, zTile);
                AxisAlignedBB bb = b.getCollisionBoundingBoxFromPool(worldObj, xTile, yTile, zTile);
                if (bb != null && bb.isVecInside(net.minecraft.util.Vec3.createVectorHelper(posX, posY, posZ))) inGround = true;
            }
        }
        if (arrowShake > 0) --arrowShake;
        if (inGround) {
            Block b = worldObj.getBlock(xTile, yTile, zTile);
            int meta = worldObj.getBlockMetadata(xTile, yTile, zTile);
            if (b == inTile && meta == inData) {
                ++ticksInGround;
                if (ticksInGround == 1200) setDead();
            } else {
                inGround = false;
                motionX *= rand.nextFloat() * 0.2F;
                motionY *= rand.nextFloat() * 0.2F;
                motionZ *= rand.nextFloat() * 0.2F;
                ticksInGround = 0;
                ticksInAir = 0;
            }
            return;
        }
        ++ticksInAir;
        net.minecraft.util.Vec3 from = net.minecraft.util.Vec3.createVectorHelper(posX, posY, posZ);
        net.minecraft.util.Vec3 to = net.minecraft.util.Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
        MovingObjectPosition mop = worldObj.func_147447_a(from, to, false, true, false);
        from = net.minecraft.util.Vec3.createVectorHelper(posX, posY, posZ);
        to = net.minecraft.util.Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
        if (mop != null) to = net.minecraft.util.Vec3.createVectorHelper(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
        Entity hitEntity = null;
        @SuppressWarnings("unchecked")
        List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this,
            boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0, 1.0, 1.0));
        double best = 0.0;
        for (Entity e : list) {
            if (!e.canBeCollidedWith() || e == shootingEntity && ticksInAir < 5) continue;
            AxisAlignedBB bb = e.boundingBox.expand(0.3, 0.3, 0.3);
            MovingObjectPosition m = bb.calculateIntercept(from, to);
            if (m == null) continue;
            double d = from.distanceTo(m.hitVec);
            if (d < best || best == 0.0) {
                hitEntity = e;
                best = d;
            }
        }
        if (hitEntity instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer) hitEntity;
            if (p.capabilities.disableDamage || shootingEntity instanceof EntityPlayer && !((EntityPlayer) shootingEntity).canAttackPlayer(p))
                hitEntity = null;
        }
        if (hitEntity != null) {
            if (!worldObj.isRemote) onHitEntity(new EntityHitResult(hitEntity));
        } else if (mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            onHitBlock(new BlockHitResult(Vec3.of(mop.hitVec), Direction.from3DDataValue(mop.sideHit),
                new BlockPos(mop.blockX, mop.blockY, mop.blockZ), false));
        }
        if (isCritArrow()) for (int i = 0; i < 4; ++i)
            worldObj.spawnParticle("crit", posX + motionX * i / 4.0, posY + motionY * i / 4.0, posZ + motionZ * i / 4.0, -motionX,
                -motionY + 0.2, -motionZ);
        if (isDead || inGround) return;
        posX += motionX;
        posY += motionY;
        posZ += motionZ;
        float h = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0 / Math.PI);
        for (rotationPitch = (float) (Math.atan2(motionY, h) * 180.0 / Math.PI); rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F);
        while (rotationPitch - prevRotationPitch >= 180.0F) prevRotationPitch += 360.0F;
        while (rotationYaw - prevRotationYaw < -180.0F) prevRotationYaw -= 360.0F;
        while (rotationYaw - prevRotationYaw >= 180.0F) prevRotationYaw += 360.0F;
        rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
        rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
        float drag = 0.99F;
        if (isInWater()) {
            for (int i = 0; i < 4; ++i)
                worldObj.spawnParticle("bubble", posX - motionX * 0.25, posY - motionY * 0.25, posZ - motionZ * 0.25, motionX, motionY, motionZ);
            drag = 0.8F;
        }
        if (isWet()) extinguish();
        motionX *= drag;
        motionY *= drag;
        motionZ *= drag;
        if (!M.isNoGravity(this)) motionY -= 0.05;
        setPosition(posX, posY, posZ);
        func_145775_I();
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound tag) {
        tag.setShort("xTile", (short) xTile);
        tag.setShort("yTile", (short) yTile);
        tag.setShort("zTile", (short) zTile);
        tag.setShort("life", (short) ticksInGround);
        tag.setByte("inTile", (byte) Block.getIdFromBlock(inTile));
        tag.setByte("inData", (byte) inData);
        tag.setByte("shake", (byte) arrowShake);
        tag.setByte("inGround", (byte) (inGround ? 1 : 0));
        tag.setByte("pickup", (byte) canBePickedUp);
        tag.setDouble("damage", damage);
        addAdditionalSaveData(tag);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound tag) {
        xTile = tag.getShort("xTile");
        yTile = tag.getShort("yTile");
        zTile = tag.getShort("zTile");
        ticksInGround = tag.getShort("life");
        inTile = Block.getBlockById(tag.getByte("inTile") & 255);
        inData = tag.getByte("inData") & 255;
        arrowShake = tag.getByte("shake") & 255;
        inGround = tag.getByte("inGround") == 1;
        if (tag.hasKey("damage", 99)) damage = tag.getDouble("damage");
        canBePickedUp = tag.getByte("pickup");
        readAdditionalSaveData(tag);
    }

    public void addAdditionalSaveData(NBTTagCompound tag) {}

    public void readAdditionalSaveData(NBTTagCompound tag) {}

    @Override
    public void writeSpawnData(ByteBuf buf) {
        buf.writeInt(shootingEntity == null ? -1 : shootingEntity.getEntityId());
        buf.writeDouble(motionX);
        buf.writeDouble(motionY);
        buf.writeDouble(motionZ);
    }

    @Override
    public void readSpawnData(ByteBuf buf) {
        int owner = buf.readInt();
        if (owner >= 0) shootingEntity = worldObj.getEntityByID(owner);
        setVelocity(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public float getShadowSize() {
        return 0.0F;
    }

    @Override
    public boolean canAttackWithItem() {
        return false;
    }
}
