package net.mcreator.boh.compat.entity;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import java.util.List;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.phys.BlockHitResult;
import net.mcreator.boh.compat.mc.world.phys.EntityHitResult;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public abstract class BohAbstractArrow extends Entity implements IEntityAdditionalSpawnData {
    protected final RandomSource random = RandomSource.wrap(this.rand);
    protected EntityType<?> type;
    public Entity shootingEntity;
    public int canBePickedUp;
    public boolean inGround;
    protected int ticksInGround;
    private int ticksInAir;
    private double damage = 2.0;
    private int knockback;
    private boolean critical;
    private int xTile = -1;
    private int yTile = -1;
    private int zTile = -1;
    private Block inTile;
    private int inData;
    public int arrowShake;
    private boolean silent;

    public BohAbstractArrow(EntityType<?> type, World world) {
        super(world);
        this.type = type;
        this.renderDistanceWeight = 10.0;
        this.setSize(type != null ? type.getWidth() : 0.5F, type != null ? type.getHeight() : 0.5F);
    }

    public BohAbstractArrow(EntityType<?> type, double x, double y, double z, World world) {
        this(type, world);
        this.setPosition(x, y, z);
        this.yOffset = 0.0F;
    }

    public BohAbstractArrow(EntityType<?> type, EntityLivingBase shooter, World world) {
        this(type, shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.1, shooter.posZ, world);
        this.shootingEntity = shooter;
        if (shooter instanceof EntityPlayer) {
            this.canBePickedUp = 1;
        }
    }

    protected void entityInit() {
        this.dataWatcher.addObject(16, (byte)0);
    }

    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        float len = MathHelper.sqrt_double(x * x + y * y + z * z);
        x /= len;
        y /= len;
        z /= len;
        x += this.rand.nextGaussian() * 0.0075 * inaccuracy;
        y += this.rand.nextGaussian() * 0.0075 * inaccuracy;
        z += this.rand.nextGaussian() * 0.0075 * inaccuracy;
        this.motionX = x * velocity;
        this.motionY = y * velocity;
        this.motionZ = z * velocity;
        float h = MathHelper.sqrt_double(x * x + z * z);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(x, z) * 180.0 / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(y, h) * 180.0 / Math.PI);
        this.ticksInGround = 0;
    }

    public void tick() {
        super.onUpdate();
        this.arrowFlight();
    }

    protected void onHitEntity(EntityHitResult hit) {
        Entity target = hit.getEntity();
        float speed = MathHelper.sqrt_double(
            this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ
        );
        int dmg = MathHelper.ceiling_double_int(speed * this.damage);
        if (this.critical) {
            dmg += this.rand.nextInt(dmg / 2 + 2);
        }

        DamageSource src = this.shootingEntity == null
            ? DamageSource.causeIndirectMagicDamage(this, this)
            : new EntityDamageSourceIndirect("arrow", this, this.shootingEntity).setProjectile();
        if (this.isBurning() && !(target instanceof EntityEnderman)) {
            target.setFire(5);
        }

        if (target.attackEntityFrom(src, dmg)) {
            if (target instanceof EntityLivingBase living) {
                if (!this.worldObj.isRemote) {
                    living.setArrowCountInEntity(living.getArrowCountInEntity() + 1);
                }

                if (this.knockback > 0) {
                    float h = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
                    if (h > 0.0F) {
                        target.addVelocity(this.motionX * this.knockback * 0.6 / h, 0.1, this.motionZ * this.knockback * 0.6 / h);
                    }
                }

                if (this.shootingEntity instanceof EntityLivingBase) {
                    EnchantmentHelper.func_151384_a(living, this.shootingEntity);
                    EnchantmentHelper.func_151385_b((EntityLivingBase)this.shootingEntity, living);
                }

                this.doPostHurtEffects(living);
                if (this.shootingEntity != null
                    && living != this.shootingEntity
                    && living instanceof EntityPlayer
                    && this.shootingEntity instanceof EntityPlayerMP) {
                    ((EntityPlayerMP)this.shootingEntity).playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(6, 0.0F));
                }
            }

            if (!this.silent) {
                this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
            }

            if (!(target instanceof EntityEnderman)) {
                this.setDead();
            }
        } else {
            this.motionX *= -0.1;
            this.motionY *= -0.1;
            this.motionZ *= -0.1;
            this.rotationYaw += 180.0F;
            this.prevRotationYaw += 180.0F;
            this.ticksInAir = 0;
        }
    }

    protected void onHitBlock(BlockHitResult hit) {
        BlockPos p = hit.getBlockPos();
        this.xTile = p.getX();
        this.yTile = p.getY();
        this.zTile = p.getZ();
        this.inTile = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
        this.inData = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
        this.motionX = hit.getLocation().x - this.posX;
        this.motionY = hit.getLocation().y - this.posY;
        this.motionZ = hit.getLocation().z - this.posZ;
        float len = MathHelper.sqrt_double(
            this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ
        );
        this.posX = this.posX - this.motionX / len * 0.05;
        this.posY = this.posY - this.motionY / len * 0.05;
        this.posZ = this.posZ - this.motionZ / len * 0.05;
        if (!this.silent) {
            this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
        }

        this.inGround = true;
        this.arrowShake = 7;
        this.critical = false;
        if (this.inTile.getMaterial() != Material.air) {
            this.inTile.onEntityCollidedWithBlock(this.worldObj, this.xTile, this.yTile, this.zTile, this);
        }
    }

    protected void doPostHurtEffects(EntityLivingBase target) {
    }

    protected ItemStack getPickupItem() {
        return M.EMPTY;
    }

    public ItemStack getItem() {
        return M.EMPTY;
    }

    public void playerTouch(EntityPlayer player) {
        if (!this.worldObj.isRemote && this.inGround && this.arrowShake <= 0) {
            boolean pickup = this.canBePickedUp == 1 || this.canBePickedUp == 2 && player.capabilities.isCreativeMode;
            ItemStack s = M.legacy(this.getPickupItem());
            if (this.canBePickedUp == 1 && (s == null || !player.inventory.addItemStackToInventory(s.copy()))) {
                pickup = false;
            }

            if (pickup) {
                this.playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                player.onItemPickup(this, 1);
                this.setDead();
            }
        }
    }

    public void setBaseDamage(double d) {
        this.damage = d;
    }

    public double getBaseDamage() {
        return this.damage;
    }

    public void setKnockback(int k) {
        this.knockback = k;
    }

    public void setCritArrow(boolean b) {
        this.critical = b;
        this.dataWatcher.updateObject(16, (byte)(b ? 1 : 0));
    }

    public boolean isCritArrow() {
        return this.dataWatcher.getWatchableObjectByte(16) == 1;
    }

    public void setSilentArrow(boolean b) {
        this.silent = b;
    }

    public void setOwner(Entity owner) {
        this.shootingEntity = owner;
    }

    public Entity getOwner() {
        return this.shootingEntity;
    }

    public boolean canHitEntity(Entity e) {
        return e != null && e.canBeCollidedWith() && !e.isDead && (e != this.shootingEntity || this.ticksExisted >= 5);
    }

    public void onUpdate() {
        this.tick();
    }

    public void onCollideWithPlayer(EntityPlayer player) {
        this.playerTouch(player);
    }

    public void setVelocity(double x, double y, double z) {
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float h = MathHelper.sqrt_double(x * x + z * z);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(x, z) * 180.0 / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(y, h) * 180.0 / Math.PI);
            this.ticksInGround = 0;
        }
    }

    private void arrowFlight() {
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float h = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, h) * 180.0 / Math.PI);
        }

        if (this.xTile >= 0) {
            Block b = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
            if (b.getMaterial() != Material.air) {
                b.setBlockBoundsBasedOnState(this.worldObj, this.xTile, this.yTile, this.zTile);
                AxisAlignedBB bb = b.getCollisionBoundingBoxFromPool(this.worldObj, this.xTile, this.yTile, this.zTile);
                if (bb != null && bb.isVecInside(Vec3.createVectorHelper(this.posX, this.posY, this.posZ))) {
                    this.inGround = true;
                }
            }
        }

        if (this.arrowShake > 0) {
            this.arrowShake--;
        }

        if (this.inGround) {
            Block b = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
            int meta = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
            if (b == this.inTile && meta == this.inData) {
                this.ticksInGround++;
                if (this.ticksInGround == 1200) {
                    this.setDead();
                }
            } else {
                this.inGround = false;
                this.motionX = this.motionX * (this.rand.nextFloat() * 0.2F);
                this.motionY = this.motionY * (this.rand.nextFloat() * 0.2F);
                this.motionZ = this.motionZ * (this.rand.nextFloat() * 0.2F);
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }
        } else {
            this.ticksInAir++;
            Vec3 from = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
            Vec3 to = Vec3.createVectorHelper(
                this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ
            );
            MovingObjectPosition mop = this.worldObj.func_147447_a(from, to, false, true, false);
            from = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
            to = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            if (mop != null) {
                to = Vec3.createVectorHelper(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
            }

            Entity hitEntity = null;
            List<Entity> list = this.worldObj
                .getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
            double best = 0.0;

            for (Entity e : list) {
                if (e.canBeCollidedWith() && (e != this.shootingEntity || this.ticksInAir >= 5)) {
                    AxisAlignedBB bb = e.boundingBox.expand(0.3, 0.3, 0.3);
                    MovingObjectPosition m = bb.calculateIntercept(from, to);
                    if (m != null) {
                        double d = from.distanceTo(m.hitVec);
                        if (d < best || best == 0.0) {
                            hitEntity = e;
                            best = d;
                        }
                    }
                }
            }

            if (hitEntity instanceof EntityPlayer p
                && (p.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer && !((EntityPlayer)this.shootingEntity).canAttackPlayer(p))) {
                hitEntity = null;
            }

            if (hitEntity != null) {
                if (!this.worldObj.isRemote) {
                    this.onHitEntity(new EntityHitResult(hitEntity));
                }
            } else if (mop != null && mop.typeOfHit == MovingObjectType.BLOCK) {
                this.onHitBlock(
                    new BlockHitResult(
                        net.mcreator.boh.compat.mc.world.phys.Vec3.of(mop.hitVec),
                        Direction.from3DDataValue(mop.sideHit),
                        new BlockPos(mop.blockX, mop.blockY, mop.blockZ),
                        false
                    )
                );
            }

            if (this.isCritArrow()) {
                for (int i = 0; i < 4; i++) {
                    this.worldObj
                        .spawnParticle(
                            "crit",
                            this.posX + this.motionX * i / 4.0,
                            this.posY + this.motionY * i / 4.0,
                            this.posZ + this.motionZ * i / 4.0,
                            -this.motionX,
                            -this.motionY + 0.2,
                            -this.motionZ
                        );
                }
            }

            if (!this.isDead && !this.inGround) {
                this.posX = this.posX + this.motionX;
                this.posY = this.posY + this.motionY;
                this.posZ = this.posZ + this.motionZ;
                float h = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
                this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / Math.PI);
                this.rotationPitch = (float)(Math.atan2(this.motionY, h) * 180.0 / Math.PI);

                while (this.rotationPitch - this.prevRotationPitch < -180.0F) {
                    this.prevRotationPitch -= 360.0F;
                }

                while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
                    this.prevRotationPitch += 360.0F;
                }

                while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
                    this.prevRotationYaw -= 360.0F;
                }

                while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
                    this.prevRotationYaw += 360.0F;
                }

                this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
                this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
                float drag = 0.99F;
                if (this.isInWater()) {
                    for (int i = 0; i < 4; i++) {
                        this.worldObj
                            .spawnParticle(
                                "bubble",
                                this.posX - this.motionX * 0.25,
                                this.posY - this.motionY * 0.25,
                                this.posZ - this.motionZ * 0.25,
                                this.motionX,
                                this.motionY,
                                this.motionZ
                            );
                    }

                    drag = 0.8F;
                }

                if (this.isWet()) {
                    this.extinguish();
                }

                this.motionX *= drag;
                this.motionY *= drag;
                this.motionZ *= drag;
                if (!M.isNoGravity(this)) {
                    this.motionY -= 0.05;
                }

                this.setPosition(this.posX, this.posY, this.posZ);
                this.func_145775_I();
            }
        }
    }

    protected void writeEntityToNBT(NBTTagCompound tag) {
        tag.setShort("xTile", (short)this.xTile);
        tag.setShort("yTile", (short)this.yTile);
        tag.setShort("zTile", (short)this.zTile);
        tag.setShort("life", (short)this.ticksInGround);
        tag.setByte("inTile", (byte)Block.getIdFromBlock(this.inTile));
        tag.setByte("inData", (byte)this.inData);
        tag.setByte("shake", (byte)this.arrowShake);
        tag.setByte("inGround", (byte)(this.inGround ? 1 : 0));
        tag.setByte("pickup", (byte)this.canBePickedUp);
        tag.setDouble("damage", this.damage);
        this.addAdditionalSaveData(tag);
    }

    protected void readEntityFromNBT(NBTTagCompound tag) {
        this.xTile = tag.getShort("xTile");
        this.yTile = tag.getShort("yTile");
        this.zTile = tag.getShort("zTile");
        this.ticksInGround = tag.getShort("life");
        this.inTile = Block.getBlockById(tag.getByte("inTile") & 255);
        this.inData = tag.getByte("inData") & 255;
        this.arrowShake = tag.getByte("shake") & 255;
        this.inGround = tag.getByte("inGround") == 1;
        if (tag.hasKey("damage", 99)) {
            this.damage = tag.getDouble("damage");
        }

        this.canBePickedUp = tag.getByte("pickup");
        this.readAdditionalSaveData(tag);
    }

    public void addAdditionalSaveData(NBTTagCompound tag) {
    }

    public void readAdditionalSaveData(NBTTagCompound tag) {
    }

    public void writeSpawnData(ByteBuf buf) {
        buf.writeInt(this.shootingEntity == null ? -1 : this.shootingEntity.getEntityId());
        buf.writeDouble(this.motionX);
        buf.writeDouble(this.motionY);
        buf.writeDouble(this.motionZ);
    }

    public void readSpawnData(ByteBuf buf) {
        int owner = buf.readInt();
        if (owner >= 0) {
            this.shootingEntity = this.worldObj.getEntityByID(owner);
        }

        this.setVelocity(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    public float getShadowSize() {
        return 0.0F;
    }

    public boolean canAttackWithItem() {
        return false;
    }
}
