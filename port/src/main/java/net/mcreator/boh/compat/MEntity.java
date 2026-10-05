package net.mcreator.boh.compat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import net.mcreator.boh.compat.entity.BohLightningBolt;
import net.mcreator.boh.compat.entity.BohMob;
import net.mcreator.boh.compat.entity.ItemCooldowns;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.network.syncher.SynchedEntityData;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.EquipmentSlot;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.compat.mc.world.entity.RemovalReason;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.AttributeInstance;
import net.mcreator.boh.compat.mc.world.entity.ai.control.LookControl;
import net.mcreator.boh.compat.mc.world.entity.ai.control.MoveControl;
import net.mcreator.boh.compat.mc.world.entity.ai.navigation.PathNavigation;
import net.mcreator.boh.compat.mc.world.entity.ai.sensing.Sensing;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec2;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;

import com.mojang.authlib.GameProfile;

/** Entity / living / mob / player helpers. */
public class MEntity extends MWorld {

    protected MEntity() {}

    // ------------------------------------------------------------------ position / motion / rotation

    public static World level(Entity e) {
        return e.worldObj;
    }

    public static double getX(Entity e) {
        return e.posX;
    }

    public static double getY(Entity e) {
        return e.posY - e.yOffset;
    }

    public static double getZ(Entity e) {
        return e.posZ;
    }

    public static double getX(Entity e, double scale) {
        return e.posX + e.width * scale;
    }

    public static double getZ(Entity e, double scale) {
        return e.posZ + e.width * scale;
    }

    public static double getY(Entity e, double scale) {
        return getY(e) + e.height * scale;
    }

    public static double getEyeY(Entity e) {
        return getY(e) + getEyeHeight(e);
    }

    public static float getEyeHeight(Entity e) {
        return e instanceof EntityPlayer ? (e.isSneaking() ? 1.54F : 1.62F) : e.getEyeHeight();
    }

    public static Vec3 position(Entity e) {
        return new Vec3(e.posX, getY(e), e.posZ);
    }

    public static Vec3 getPosition(Entity e, float partial) {
        return new Vec3(e.prevPosX + (e.posX - e.prevPosX) * partial, e.prevPosY + (getY(e) - e.prevPosY) * partial,
            e.prevPosZ + (e.posZ - e.prevPosZ) * partial);
    }

    public static Vec3 getEyePosition(Entity e) {
        return new Vec3(e.posX, getY(e) + getEyeHeight(e), e.posZ);
    }

    public static Vec3 getEyePosition(Entity e, float partial) {
        return getEyePosition(e);
    }

    public static BlockPos blockPosition(Entity e) {
        return BlockPos.containing(e.posX, getY(e), e.posZ);
    }

    public static BlockPos getOnPos(Entity e) {
        return BlockPos.containing(e.posX, getY(e) - 0.2, e.posZ);
    }

    public static Vec3 getDeltaMovement(Entity e) {
        return new Vec3(e.motionX, e.motionY, e.motionZ);
    }

    public static void setDeltaMovement(Entity e, Vec3 v) {
        setDeltaMovement(e, v.x, v.y, v.z);
    }

    public static void setDeltaMovement(Entity e, double x, double y, double z) {
        e.motionX = x;
        e.motionY = y;
        e.motionZ = z;
        e.velocityChanged = true;
    }

    public static void push(Entity e, double x, double y, double z) {
        e.addVelocity(x, y, z);
    }

    public static void setPos(Entity e, double x, double y, double z) {
        e.setPosition(x, y + (e instanceof EntityPlayer ? e.yOffset : 0), z);
    }

    public static void setPos(Entity e, Vec3 v) {
        setPos(e, v.x, v.y, v.z);
    }

    public static void moveTo(Entity e, double x, double y, double z) {
        e.setLocationAndAngles(x, y, z, e.rotationYaw, e.rotationPitch);
    }

    public static void moveTo(Entity e, double x, double y, double z, float yaw, float pitch) {
        e.setLocationAndAngles(x, y, z, yaw, pitch);
    }

    public static void moveTo(Entity e, Vec3 v) {
        moveTo(e, v.x, v.y, v.z);
    }

    public static void moveTo(Entity e, BlockPos p, float yaw, float pitch) {
        moveTo(e, p.getX() + 0.5, p.getY(), p.getZ() + 0.5, yaw, pitch);
    }

    public static void teleportTo(Entity e, double x, double y, double z) {
        // procedures also run on the client; there a random teleport (the Saucer's hops) puts the entity somewhere
        // else than the server did, and the next sync drags it back. The server's move reaches the client anyway.
        if (e.worldObj.isRemote && !(e instanceof net.minecraft.entity.player.EntityPlayer)) return;
        // the Saucer's random hops glide instead of snapping (see Glides)
        if (net.mcreator.boh.compat.entity.Glides.glides(e, x, y, z)) {
            net.mcreator.boh.compat.entity.Glides.start(e, x, y, z);
            return;
        }
        if (e instanceof EntityPlayerMP) ((EntityPlayerMP) e).playerNetServerHandler.setPlayerLocation(x, y, z, e.rotationYaw, e.rotationPitch);
        else if (e instanceof EntityLivingBase) ((EntityLivingBase) e).setPositionAndUpdate(x, y, z);
        else e.setLocationAndAngles(x, y, z, e.rotationYaw, e.rotationPitch);
    }

    public static void teleportTo(Entity e, World target, double x, double y, double z, float yaw, float pitch) {
        if (target != null && target.provider.dimensionId != e.dimension && e instanceof EntityPlayerMP) {
            net.mcreator.boh.compat.world.Dimensions.transferPlayer((EntityPlayerMP) e, target.provider.dimensionId, x, y, z, yaw, pitch);
            return;
        }
        e.rotationYaw = yaw;
        e.rotationPitch = pitch;
        teleportTo(e, x, y, z);
    }

    public static void teleportTo(Entity e, World target, double x, double y, double z, java.util.Set<?> flags, float yaw, float pitch) {
        teleportTo(e, target, x, y, z, yaw, pitch);
    }

    public static float getYRot(Entity e) {
        return e.rotationYaw;
    }

    public static float getXRot(Entity e) {
        return e.rotationPitch;
    }

    public static void setYRot(Entity e, float v) {
        e.rotationYaw = v;
    }

    public static void setXRot(Entity e, float v) {
        e.rotationPitch = v;
    }

    public static void setRot(Entity e, float yaw, float pitch) {
        e.rotationYaw = yaw % 360f;
        e.rotationPitch = pitch % 360f;
    }

    public static void setYBodyRot(EntityLivingBase e, float v) {
        e.renderYawOffset = v;
    }

    public static void setYHeadRot(Entity e, float v) {
        if (e instanceof EntityLivingBase) ((EntityLivingBase) e).rotationYawHead = v; // setRotationYawHead is client-only
    }

    public static float getYHeadRot(Entity e) {
        return e.getRotationYawHead();
    }

    public static Vec2 getRotationVector(Entity e) {
        return new Vec2(e.rotationPitch, e.rotationYaw);
    }

    public static Vec3 getLookAngle(Entity e) {
        return Vec3.directionFromRotation(e.rotationPitch, e.rotationYaw);
    }

    public static Vec3 getViewVector(Entity e, float partial) {
        return getLookAngle(e);
    }

    public static net.mcreator.boh.compat.mc.core.Direction getDirection(Entity e) {
        return net.mcreator.boh.compat.mc.core.Direction.fromYRot(e.rotationYaw);
    }

    /** 1.20 Entity.lookAt(anchor, target). */
    public static void lookAt(Entity e, Object anchor, Vec3 target) {
        double dx = target.x - e.posX, dy = target.y - (getY(e) + getEyeHeight(e)), dz = target.z - e.posZ;
        double h = Math.sqrt(dx * dx + dz * dz);
        e.rotationPitch = MathHelper.wrapAngleTo180_float((float) (-(Math.atan2(dy, h) * 180.0 / Math.PI)));
        e.rotationYaw = MathHelper.wrapAngleTo180_float((float) (Math.atan2(dz, dx) * 180.0 / Math.PI) - 90.0F);
        if (e instanceof EntityLivingBase) ((EntityLivingBase) e).rotationYawHead = e.rotationYaw; // setRotationYawHead is client-only
        e.prevRotationPitch = e.rotationPitch;
        e.prevRotationYaw = e.rotationYaw;
        if (e instanceof EntityPlayerMP)
            ((EntityPlayerMP) e).playerNetServerHandler.setPlayerLocation(e.posX, e.posY, e.posZ, e.rotationYaw, e.rotationPitch);
    }

    public static float rotlerp(Object ctl, float from, float to, float max) {
        float d = MathHelper.wrapAngleTo180_float(to - from);
        if (d > max) d = max;
        if (d < -max) d = -max;
        return from + d;
    }

    public static float getBbWidth(Entity e) {
        return e.width;
    }

    public static float getBbHeight(Entity e) {
        return e.height;
    }

    public static AABB getBoundingBox(Entity e) {
        return AABB.of(e.boundingBox);
    }

    public static boolean onGround(Entity e) {
        return e.onGround;
    }

    public static boolean isInWater(Entity e) {
        return e.isInWater();
    }

    public static boolean isInWaterOrBubble(Entity e) {
        return e.isInWater();
    }

    public static boolean isInWaterRainOrBubble(Entity e) {
        return e.isWet();
    }

    public static boolean isInLava(Entity e) {
        return e.handleLavaMovement();
    }

    public static boolean isOnFire(Entity e) {
        return e.isBurning();
    }

    public static void setSecondsOnFire(Entity e, int s) {
        e.setFire(s);
    }

    public static int getRemainingFireTicks(Entity e) {
        return e.fire;
    }

    public static void setRemainingFireTicks(Entity e, int t) {
        e.fire = t;
    }

    public static void clearFire(Entity e) {
        e.extinguish();
    }

    public static boolean isInvisible(Entity e) {
        return e.isInvisible();
    }

    public static void setInvisible(Entity e, boolean b) {
        e.setInvisible(b);
    }

    public static boolean isInvulnerable(Entity e) {
        return e.isEntityInvulnerable();
    }

    public static boolean isShiftKeyDown(Entity e) {
        return e.isSneaking();
    }

    public static void setShiftKeyDown(Entity e, boolean b) {
        e.setSneaking(b);
    }

    public static boolean isSprinting(Entity e) {
        return e.isSprinting();
    }

    public static void setSprinting(Entity e, boolean b) {
        e.setSprinting(b);
    }

    public static boolean isCrouching(Entity e) {
        return e.isSneaking();
    }

    public static void setSilent(Entity e, boolean b) {
        e.getEntityData().setBoolean("boh_silent", b);
    }

    public static boolean isSilent(Entity e) {
        return e.getEntityData().getBoolean("boh_silent");
    }

    public static void setNoGravity(Entity e, boolean b) {
        BohMob.setNoGravity(e, b);
        if (!(e instanceof BohMob)) e.getEntityData().setBoolean("boh_nogravity", b);
    }

    public static boolean isNoGravity(Entity e) {
        return BohMob.isNoGravity(e) || e.getEntityData().getBoolean("boh_nogravity");
    }

    public static void makeStuckInBlock(Entity e, BlockState state, Vec3 factor) {
        e.setInWeb();
    }

    public static void resetFallDistance(Entity e) {
        e.fallDistance = 0;
    }

    // ------------------------------------------------------------------ identity

    public static NBTTagCompound getPersistentData(Entity e) {
        return e.getEntityData();
    }

    public static int getId(Entity e) {
        return e.getEntityId();
    }

    public static UUID getUUID(Entity e) {
        return e.getUniqueID();
    }

    public static String getStringUUID(Entity e) {
        return e.getUniqueID().toString();
    }

    public static EntityType<?> getType(Entity e) {
        return EntityType.of(e);
    }

    public static Component getName(Entity e) {
        return Component.literal(e.getCommandSenderName());
    }

    public static Component getDisplayName(Entity e) {
        return Component.of(e.func_145748_c_());
    }

    public static String getScoreboardName(Entity e) {
        return e instanceof EntityPlayer ? e.getCommandSenderName() : e.getUniqueID().toString();
    }

    public static void setCustomName(Entity e, Component name) {
        if (e instanceof EntityLiving) ((EntityLiving) e).setCustomNameTag(name == null ? "" : name.getString());
    }

    public static void setCustomNameVisible(Entity e, boolean b) {
        if (e instanceof EntityLiving) ((EntityLiving) e).setAlwaysRenderNameTag(b);
    }

    public static boolean hasCustomName(Entity e) {
        return e instanceof EntityLiving && ((EntityLiving) e).hasCustomNameTag();
    }

    public static Component getCustomName(Entity e) {
        return e instanceof EntityLiving ? Component.literal(((EntityLiving) e).getCustomNameTag()) : null;
    }

    public static int tickCount(Entity e) {
        return e.ticksExisted;
    }

    public static boolean isAlive(Entity e) {
        return e != null && e.isEntityAlive();
    }

    public static boolean isRemoved(Entity e) {
        return e.isDead;
    }

    public static void discard(Entity e) {
        e.setDead();
    }

    public static void remove(Entity e, RemovalReason reason) {
        e.setDead();
    }

    public static void kill(Entity e) {
        if (e instanceof EntityLivingBase) e.attackEntityFrom(DamageSource.outOfWorld, Float.MAX_VALUE);
        else e.setDead();
    }

    public static boolean hurt(Entity e, DamageSource src, float amount) {
        return e.attackEntityFrom(src, amount);
    }

    // ------------------------------------------------------------------ riding

    public static boolean isPassenger(Entity e) {
        return e.ridingEntity != null;
    }

    public static Entity getVehicle(Entity e) {
        return e.ridingEntity;
    }

    public static boolean isVehicle(Entity e) {
        return e.riddenByEntity != null;
    }

    public static List<Entity> getPassengers(Entity e) {
        List<Entity> l = new ArrayList<>();
        if (e.riddenByEntity != null) l.add(e.riddenByEntity);
        return l;
    }

    public static Entity getFirstPassenger(Entity e) {
        return e.riddenByEntity;
    }

    public static boolean startRiding(Entity e, Entity vehicle) {
        if (vehicle == null) return false;
        e.mountEntity(vehicle);
        return true;
    }

    public static boolean startRiding(Entity e, Entity vehicle, boolean force) {
        return startRiding(e, vehicle);
    }

    public static void stopRiding(Entity e) {
        if (e.ridingEntity != null) e.mountEntity(null);
    }

    public static void ejectPassengers(Entity e) {
        if (e.riddenByEntity != null) e.riddenByEntity.mountEntity(null);
    }

    // ------------------------------------------------------------------ living

    public static float getHealth(EntityLivingBase e) {
        return e.getHealth();
    }

    public static void setHealth(EntityLivingBase e, float h) {
        e.setHealth(h);
    }

    public static float getMaxHealth(EntityLivingBase e) {
        return e.getMaxHealth();
    }

    public static void heal(EntityLivingBase e, float amt) {
        e.heal(amt);
    }

    public static boolean isDeadOrDying(EntityLivingBase e) {
        return e.getHealth() <= 0;
    }

    public static boolean isBaby(EntityLivingBase e) {
        return e.isChild();
    }

    public static boolean isBlocking(EntityLivingBase e) {
        return e instanceof EntityPlayer && ((EntityPlayer) e).isBlocking();
    }

    public static net.mcreator.boh.compat.mc.util.RandomSource getRandom(Entity e) {
        return net.mcreator.boh.compat.mc.util.RandomSource.wrap(e instanceof EntityLivingBase ? ((EntityLivingBase) e).getRNG() : e.worldObj.rand);
    }

    public static float getAttackAnim(EntityLivingBase e, float partial) {
        // EntityLivingBase.getSwingProgress is client-only
        float d = e.swingProgress - e.prevSwingProgress;
        if (d < 0) d++;
        return e.prevSwingProgress + d * partial;
    }

    public static void updateSwingTime(EntityLivingBase e) {}

    public static void swing(EntityLivingBase e, InteractionHand hand) {
        e.swingItem();
    }

    public static void swing(EntityLivingBase e, InteractionHand hand, boolean sync) {
        e.swingItem();
    }

    public static int getArrowCount(EntityLivingBase e) {
        return e.getArrowCountInEntity();
    }

    public static void setArrowCount(EntityLivingBase e, int n) {
        e.setArrowCountInEntity(n);
    }

    public static Entity getLastHurtByMob(EntityLivingBase e) {
        return e.getAITarget();
    }

    public static Entity getLastHurtMob(EntityLivingBase e) {
        return e.getLastAttacker();
    }

    public static void setLastHurtByMob(EntityLivingBase e, EntityLivingBase attacker) {
        e.setRevengeTarget(attacker);
    }

    // effects

    public static boolean hasEffect(EntityLivingBase e, Potion p) {
        return p != null && e.isPotionActive(p);
    }

    public static boolean addEffect(EntityLivingBase e, PotionEffect pe) {
        if (pe == null || e.worldObj.isRemote && !(e instanceof EntityPlayer)) return false;
        if (!e.isPotionApplicable(pe)) return false;
        e.addPotionEffect(pe);
        return true;
    }

    public static boolean addEffect(EntityLivingBase e, PotionEffect pe, Entity source) {
        return addEffect(e, pe);
    }

    public static boolean removeEffect(EntityLivingBase e, Potion p) {
        if (p == null || !e.isPotionActive(p)) return false;
        e.removePotionEffect(p.id);
        return true;
    }

    public static void removeAllEffects(EntityLivingBase e) {
        e.clearActivePotions();
    }

    public static PotionEffect getEffect(EntityLivingBase e, Potion p) {
        return p == null ? null : e.getActivePotionEffect(p);
    }

    @SuppressWarnings("unchecked")
    public static Collection<PotionEffect> getActiveEffects(EntityLivingBase e) {
        return e.getActivePotionEffects();
    }

    public static int getAmplifier(PotionEffect pe) {
        return pe == null ? 0 : pe.getAmplifier();
    }

    public static int getDuration(PotionEffect pe) {
        return pe == null ? 0 : pe.getDuration();
    }

    public static Potion getEffect(PotionEffect pe) {
        return Potion.potionTypes[pe.getPotionID()];
    }

    public static PotionEffect new_PotionEffect(Potion p, int duration) {
        return new PotionEffect(p.id, duration, 0);
    }

    public static PotionEffect new_PotionEffect(Potion p, int duration, int amp) {
        return new PotionEffect(p.id, duration, amp);
    }

    public static PotionEffect new_PotionEffect(Potion p, int duration, int amp, boolean ambient, boolean visible) {
        return new PotionEffect(p.id, duration, amp, ambient);
    }

    public static PotionEffect new_PotionEffect(Potion p, int duration, int amp, boolean ambient, boolean visible, boolean icon) {
        return new PotionEffect(p.id, duration, amp, ambient);
    }

    // attributes

    public static AttributeInstance getAttribute(EntityLivingBase e, IAttribute a) {
        IAttributeInstance i = e.getAttributeMap().getAttributeInstance(a);
        return i == null ? null : new AttributeInstance(i);
    }

    public static double getAttributeValue(EntityLivingBase e, IAttribute a) {
        IAttributeInstance i = e.getAttributeMap().getAttributeInstance(a);
        return i == null ? a.getDefaultValue() : i.getAttributeValue();
    }

    public static double getAttributeBaseValue(EntityLivingBase e, IAttribute a) {
        IAttributeInstance i = e.getAttributeMap().getAttributeInstance(a);
        return i == null ? a.getDefaultValue() : i.getBaseValue();
    }

    public static void setSpeed(EntityLivingBase e, float s) {
        e.setAIMoveSpeed(s);
    }

    public static float getSpeed(EntityLivingBase e) {
        return e.getAIMoveSpeed();
    }

    public static void setYya(EntityLivingBase e, float v) {
        e.motionY += v * 0.1;
    }

    public static void setZza(EntityLivingBase e, float v) {
        e.moveForward = v;
    }

    public static void setXxa(EntityLivingBase e, float v) {
        e.moveStrafing = v;
    }

    public static void setMaxUpStep(Entity e, float v) {
        e.stepHeight = v;
    }

    public static float maxUpStep(Entity e) {
        return e.stepHeight;
    }

    // equipment

    public static ItemStack getMainHandItem(EntityLivingBase e) {
        return stack(e.getHeldItem());
    }

    /** 1.7.10 has no offhand. */
    public static ItemStack getOffhandItem(EntityLivingBase e) {
        return EMPTY;
    }

    public static ItemStack getItemInHand(EntityLivingBase e, InteractionHand hand) {
        return hand == InteractionHand.OFF_HAND ? EMPTY : getMainHandItem(e);
    }

    public static void setItemInHand(EntityLivingBase e, InteractionHand hand, ItemStack s) {
        if (hand == InteractionHand.OFF_HAND) return;
        setItemSlot(e, EquipmentSlot.MAINHAND, s);
    }

    public static ItemStack getItemBySlot(EntityLivingBase e, EquipmentSlot slot) {
        if (slot.legacyIndex() < 0) return EMPTY;
        if (e instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer) e;
            if (slot == EquipmentSlot.MAINHAND) return stack(p.inventory.getCurrentItem());
            return stack(p.inventory.armorInventory[slot.armorInventoryIndex()]);
        }
        return stack(e.getEquipmentInSlot(slot.legacyIndex()));
    }

    public static void setItemSlot(EntityLivingBase e, EquipmentSlot slot, ItemStack s) {
        if (slot.legacyIndex() < 0) return;
        ItemStack l = legacy(s);
        if (e instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer) e;
            if (slot == EquipmentSlot.MAINHAND) p.inventory.setInventorySlotContents(p.inventory.currentItem, l);
            else p.inventory.armorInventory[slot.armorInventoryIndex()] = l;
            return;
        }
        e.setCurrentItemOrArmor(slot.legacyIndex(), l);
    }

    public static Iterable<ItemStack> getArmorSlots(EntityLivingBase e) {
        List<ItemStack> l = new ArrayList<>();
        for (EquipmentSlot s : new EquipmentSlot[] { EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD })
            l.add(getItemBySlot(e, s));
        return l;
    }

    public static ItemStack getUseItem(EntityLivingBase e) {
        // EntityPlayer.getItemInUse is client-only; the item in use is always the held item
        return e instanceof EntityPlayer && ((EntityPlayer) e).isUsingItem() ? stack(e.getHeldItem()) : EMPTY;
    }

    public static InteractionHand getUsedItemHand(EntityLivingBase e) {
        return InteractionHand.MAIN_HAND;
    }

    public static boolean isUsingItem(EntityLivingBase e) {
        return e instanceof EntityPlayer && ((EntityPlayer) e).isUsingItem();
    }

    public static void startUsingItem(EntityLivingBase e, InteractionHand hand) {
        if (e instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer) e;
            ItemStack s = p.getHeldItem();
            if (s != null) p.setItemInUse(s, s.getMaxItemUseDuration());
        }
    }

    public static void releaseUsingItem(EntityLivingBase e) {
        if (e instanceof EntityPlayer) ((EntityPlayer) e).stopUsingItem();
    }

    public static void stopUsingItem(EntityLivingBase e) {
        releaseUsingItem(e);
    }

    // ------------------------------------------------------------------ mobs

    public static EntityLivingBase getTarget(EntityLiving m) {
        return m.getAttackTarget();
    }

    public static void setTarget(EntityLiving m, EntityLivingBase t) {
        m.setAttackTarget(t);
    }

    public static boolean isAggressive(EntityLiving m) {
        return m instanceof BohMob ? ((BohMob) m).isAggressive() : m.getAttackTarget() != null;
    }

    public static void setAggressive(EntityLiving m, boolean b) {
        BohMob.setAggressive(m, b);
    }

    public static void setNoAi(EntityLiving m, boolean b) {
        if (m instanceof BohMob) ((BohMob) m).setNoAi(b);
    }

    public static boolean isNoAi(EntityLiving m) {
        return m instanceof BohMob && ((BohMob) m).isNoAi();
    }

    public static void setPersistenceRequired(EntityLiving m) {
        m.persistenceRequired = true;
    }

    public static boolean isPersistenceRequired(EntityLiving m) {
        return m.persistenceRequired;
    }

    public static void set_xpReward(EntityLiving m, int xp) {
        m.experienceValue = xp;
    }

    public static int xpReward(EntityLiving m) {
        return m.experienceValue;
    }

    public static SynchedEntityData getEntityData(Entity e) {
        return e instanceof BohMob ? ((BohMob) e).bohEntityData() : null;
    }

    public static void refreshDimensions(Entity e) {
        if (e instanceof BohMob) ((BohMob) e).refreshDimensions();
    }

    public static PathNavigation getNavigation(EntityLiving m) {
        return m instanceof BohMob ? ((BohMob) m).bohNavigation() : PathNavigation.of(m);
    }

    public static MoveControl getMoveControl(EntityLiving m) {
        return m instanceof BohMob ? ((BohMob) m).bohMoveControl() : new MoveControl(m);
    }

    public static LookControl getLookControl(EntityLiving m) {
        return m instanceof BohMob ? ((BohMob) m).bohLookControl() : new LookControl(m);
    }

    public static Sensing getSensing(EntityLiving m) {
        return new Sensing(m);
    }

    public static boolean hasLineOfSight(EntityLivingBase e, Entity other) {
        return e.canEntityBeSeen(other);
    }

    public static boolean doHurtTarget(EntityLivingBase m, Entity target) {
        return m.attackEntityAsMob(target);
    }

    public static void dropExperience(Entity e) {
        if (e instanceof BohMob) ((BohMob) e).dropExperience();
        else if (e instanceof EntityLiving && !e.worldObj.isRemote)
            e.worldObj.spawnEntityInWorld(new EntityXPOrb(e.worldObj, e.posX, e.posY, e.posZ, ((EntityLiving) e).experienceValue));
    }

    public static EntityItem spawnAtLocation(Entity e, ItemStack s) {
        ItemStack l = legacy(s);
        return l == null || e.worldObj.isRemote ? null : e.entityDropItem(l, 0.0F);
    }

    public static EntityItem spawnAtLocation(Entity e, ItemStack s, float yOffset) {
        ItemStack l = legacy(s);
        return l == null || e.worldObj.isRemote ? null : e.entityDropItem(l, yOffset);
    }

    public static EntityItem spawnAtLocation(Entity e, Item item) {
        return spawnAtLocation(e, new_ItemStack(item));
    }

    public static void broadcastEntityEvent(World w, Entity e, byte event) {
        w.setEntityState(e, event);
    }

    public static <T extends Entity> T spawn(EntityType<T> type, World w, BlockPos pos, MobSpawnType reason) {
        return type.spawn(w, pos, reason);
    }

    public static <T extends Entity> T create(EntityType<T> type, World w) {
        return type.create(w);
    }

    // tamables

    public static boolean isTame(EntityTameable t) {
        return t.isTamed();
    }

    public static boolean isOwnedBy(EntityTameable t, EntityLivingBase owner) {
        return owner != null && t.func_152114_e(owner);
    }

    public static void tame(EntityTameable t, EntityPlayer player) {
        t.setTamed(true);
        t.func_152115_b(player.getUniqueID().toString());
    }

    public static EntityLivingBase getOwner(EntityTameable t) {
        return t.getOwner();
    }

    /** 1.20 Animal.usePlayerItem: consumes one of the stack unless the player is creative. */
    public static void usePlayerItem(Entity animal, EntityPlayer player, InteractionHand hand, ItemStack stack) {
        if (!player.capabilities.isCreativeMode && legacy(stack) != null) {
            stack.stackSize--;
            if (stack.stackSize <= 0) player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
        }
    }

    // ------------------------------------------------------------------ players

    public static InventoryPlayer getInventory(EntityPlayer p) {
        return p.inventory;
    }

    public static PlayerCapabilities getAbilities(EntityPlayer p) {
        return p.capabilities;
    }

    public static boolean instabuild(PlayerCapabilities c) {
        return c.isCreativeMode;
    }

    public static boolean mayfly(PlayerCapabilities c) {
        return c.allowFlying;
    }

    public static void set_mayfly(PlayerCapabilities c, boolean b) {
        c.allowFlying = b;
    }

    public static boolean flying(PlayerCapabilities c) {
        return c.isFlying;
    }

    public static void set_flying(PlayerCapabilities c, boolean b) {
        c.isFlying = b;
    }

    public static boolean invulnerable(PlayerCapabilities c) {
        return c.disableDamage;
    }

    public static void onUpdateAbilities(EntityPlayer p) {
        p.sendPlayerAbilities();
    }

    public static boolean isCreative(EntityPlayer p) {
        return p.capabilities.isCreativeMode;
    }

    public static boolean isSpectator(EntityPlayer p) {
        return false;
    }

    public static GameProfile getGameProfile(EntityPlayer p) {
        return p.getGameProfile();
    }

    public static UUID getId(GameProfile g) {
        return g.getId();
    }

    public static String getName(GameProfile g) {
        return g.getName();
    }

    public static void displayClientMessage(EntityPlayer p, Component msg, boolean actionBar) {
        if (msg == null) return;
        if (actionBar) {
            if (p.worldObj.isRemote) MClientImpl.setActionBar(msg.getFormattedText());
            else if (p instanceof EntityPlayerMP) net.mcreator.boh.compat.net.CompatNetwork.sendActionBar((EntityPlayerMP) p, msg.getFormattedText());
        } else {
            p.addChatComponentMessage(msg.toVanilla());
        }
    }

    public static void sendSystemMessage(EntityPlayer p, Component msg) {
        p.addChatComponentMessage(msg.toVanilla());
    }

    public static void sendSystemMessage(Object server, Component msg) {
        net.minecraft.server.MinecraftServer.getServer().getConfigurationManager().sendChatMsg(msg.toVanilla());
    }

    public static ItemCooldowns getCooldowns(EntityPlayer p) {
        return ItemCooldowns.of(p);
    }

    public static void addCooldown(ItemCooldowns c, Item item, int ticks) {
        c.addCooldown(item, ticks);
    }

    public static boolean isOnCooldown(ItemCooldowns c, Item item) {
        return c.isOnCooldown(item);
    }

    public static EntityItem drop(EntityPlayer p, ItemStack s, boolean randomly) {
        ItemStack l = legacy(s);
        return l == null ? null : p.dropPlayerItemWithRandomChoice(l, false);
    }

    public static boolean addItem(InventoryPlayer inv, ItemStack s) {
        ItemStack l = legacy(s);
        return l != null && inv.addItemStackToInventory(l);
    }

    public static void removeItem(InventoryPlayer inv, ItemStack s) {
        for (int i = 0; i < inv.getSizeInventory(); i++) if (inv.getStackInSlot(i) == s) inv.setInventorySlotContents(i, null);
    }

    public static ItemStack getItem(InventoryPlayer inv, int slot) {
        return stack(inv.getStackInSlot(slot));
    }

    public static ItemStack getSelected(InventoryPlayer inv) {
        return stack(inv.getCurrentItem());
    }

    public static int getContainerSize(InventoryPlayer inv) {
        return inv.getSizeInventory();
    }

    public static void setChanged(InventoryPlayer inv) {
        inv.markDirty();
    }

    public static boolean contains(InventoryPlayer inv, ItemStack s) {
        ItemStack l = legacy(s);
        if (l == null) return false;
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack x = inv.getStackInSlot(i);
            if (x != null && x.getItem() == l.getItem()) return true;
        }
        return false;
    }

    public static void placeItemBackInInventory(InventoryPlayer inv, ItemStack s) {
        ItemStack l = legacy(s);
        if (l != null && !inv.addItemStackToInventory(l)) inv.player.dropPlayerItemWithRandomChoice(l, false);
    }

    /** 1.20 Inventory.clearOrCountMatchingItems: removes up to {@code max} matching items, returns how many. */
    public static int clearOrCountMatchingItems(InventoryPlayer inv, java.util.function.Predicate<ItemStack> pred, int max, Object craftSlots) {
        int removed = 0;
        for (int i = 0; i < inv.getSizeInventory() && (max < 0 || removed < max || max == 0); i++) {
            ItemStack s = inv.getStackInSlot(i);
            if (s == null || !pred.test(s)) continue;
            if (max == 0) {
                removed += s.stackSize;
                continue;
            }
            int take = max < 0 ? s.stackSize : Math.min(s.stackSize, max - removed);
            s.stackSize -= take;
            removed += take;
            if (s.stackSize <= 0) inv.setInventorySlotContents(i, null);
        }
        inv.markDirty();
        return removed;
    }

    public static Object getCraftSlots(Object menu) {
        return null;
    }

    public static net.minecraft.inventory.Container inventoryMenu(EntityPlayer p) {
        return p.inventoryContainer;
    }

    public static net.minecraft.inventory.Container containerMenu(EntityPlayer p) {
        return p.openContainer;
    }

    public static void closeContainer(EntityPlayer p) {
        if (p instanceof EntityPlayerMP) ((EntityPlayerMP) p).closeScreen();
        else MClientImpl.closeScreen();
    }

    public static void broadcastChanges(net.minecraft.inventory.Container c) {
        c.detectAndSendChanges();
    }

    // server players

    public static net.mcreator.boh.compat.mc.network.protocol.game.PlayerConnection connection(EntityPlayerMP p) {
        return new net.mcreator.boh.compat.mc.network.protocol.game.PlayerConnection(p);
    }

    public static void teleport(net.mcreator.boh.compat.mc.network.protocol.game.PlayerConnection c, double x, double y, double z, float yaw, float pitch) {
        c.player.playerNetServerHandler.setPlayerLocation(x, y, z, yaw, pitch);
    }

    public static net.minecraft.server.management.ItemInWorldManager gameMode(EntityPlayerMP p) {
        return p.theItemInWorldManager;
    }

    public static net.mcreator.boh.compat.mc.world.level.GameType getGameModeForPlayer(net.minecraft.server.management.ItemInWorldManager m) {
        return net.mcreator.boh.compat.mc.world.level.GameType.of(m.getGameType());
    }

    public static void setGameMode(EntityPlayerMP p, net.mcreator.boh.compat.mc.world.level.GameType t) {
        p.setGameType(t.toVanilla());
    }

    public static net.minecraft.server.MinecraftServer server(EntityPlayerMP p) {
        return p.mcServer;
    }

    public static net.minecraft.server.MinecraftServer getServer(Entity e) {
        return e.worldObj.isRemote ? null : net.minecraft.server.MinecraftServer.getServer();
    }

    public static boolean hasDisconnected(EntityPlayerMP p) {
        return p.playerNetServerHandler == null;
    }

    public static BlockPos getRespawnPosition(EntityPlayerMP p) {
        net.minecraft.util.ChunkCoordinates c = p.getBedLocation(p.dimension);
        return c == null ? null : new BlockPos(c.posX, c.posY, c.posZ);
    }

    public static net.mcreator.boh.compat.mc.resources.ResourceKey<World> getRespawnDimension(EntityPlayerMP p) {
        return net.mcreator.boh.compat.world.Dimensions.OVERWORLD;
    }

    public static void setRespawnPosition(EntityPlayerMP p, Object dim, BlockPos pos, float angle, boolean forced, boolean msg) {
        if (pos == null) p.setSpawnChunk(null, false);
        else p.setSpawnChunk(new net.minecraft.util.ChunkCoordinates(pos.getX(), pos.getY(), pos.getZ()), forced);
    }

    public static void sendMessage(EntityPlayer p, String text) {
        p.addChatComponentMessage(new ChatComponentText(text));
    }

    // ------------------------------------------------------------------ item entities / projectiles / lightning

    public static EntityItem new_EntityItem(World w, double x, double y, double z, ItemStack s) {
        ItemStack l = legacy(s);
        return new EntityItem(w, x, y, z, l == null ? new ItemStack(net.minecraft.init.Items.stick, 0) : l);
    }

    public static EntityItem new_EntityItem(World w, double x, double y, double z, ItemStack s, double dx, double dy, double dz) {
        EntityItem e = new_EntityItem(w, x, y, z, s);
        e.motionX = dx;
        e.motionY = dy;
        e.motionZ = dz;
        return e;
    }

    public static ItemStack getItem(EntityItem e) {
        return stack(e.getEntityItem());
    }

    public static void setItem(EntityItem e, ItemStack s) {
        e.setEntityItemStack(legacy(s));
    }

    public static void setPickUpDelay(EntityItem e, int d) {
        e.delayBeforeCanPickup = d;
    }

    public static void setNoPickUpDelay(EntityItem e) {
        e.delayBeforeCanPickup = 0;
    }

    public static void setUnlimitedLifetime(EntityItem e) {
        e.lifespan = Integer.MAX_VALUE;
    }

    public static void setVisualOnly(EntityLightningBolt bolt, boolean b) {
        if (bolt instanceof BohLightningBolt) ((BohLightningBolt) bolt).setVisualOnly(b);
    }

    public static void setBaseDamage(net.mcreator.boh.compat.entity.BohAbstractArrow a, double d) {
        a.setBaseDamage(d);
    }

    public static double getBaseDamage(net.mcreator.boh.compat.entity.BohAbstractArrow a) {
        return a.getBaseDamage();
    }

    public static void setKnockback(net.mcreator.boh.compat.entity.BohAbstractArrow a, int k) {
        a.setKnockback(k);
    }

    public static void setCritArrow(net.mcreator.boh.compat.entity.BohAbstractArrow a, boolean b) {
        a.setCritArrow(b);
    }

    public static void setSilent(net.mcreator.boh.compat.entity.BohAbstractArrow a, boolean b) {
        a.setSilentArrow(b);
    }

    public static boolean inGround(net.mcreator.boh.compat.entity.BohAbstractArrow a) {
        return a.inGround;
    }

    public static void set_pickup(net.mcreator.boh.compat.entity.BohAbstractArrow a, net.mcreator.boh.compat.mc.world.entity.projectile.Pickup p) {
        a.canBePickedUp = p.ordinal();
    }

    public static void setPierceLevel(net.mcreator.boh.compat.entity.BohAbstractArrow a, byte level) {
        a.setPierceLevel(level);
    }

    public static void setBaseDamage(EntityArrow a, double d) {
        a.setDamage(d);
    }

    public static double getBaseDamage(EntityArrow a) {
        return a.getDamage();
    }

    public static void setKnockback(EntityArrow a, int k) {
        a.setKnockbackStrength(k);
    }

    public static void setCritArrow(EntityArrow a, boolean b) {
        a.setIsCritical(b);
    }

    public static void setPierceLevel(EntityArrow a, byte level) {}

    public static void set_pickup(EntityArrow a, net.mcreator.boh.compat.mc.world.entity.projectile.Pickup p) {
        a.canBePickedUp = p.ordinal();
    }

    public static void setOwner(Entity projectile, Entity owner) {
        if (projectile instanceof EntityArrow) ((EntityArrow) projectile).shootingEntity = owner;
        else if (projectile instanceof EntityFireball && owner instanceof EntityLivingBase) ((EntityFireball) projectile).shootingEntity = (EntityLivingBase) owner;
        else if (projectile instanceof net.mcreator.boh.compat.entity.BohAbstractArrow) ((net.mcreator.boh.compat.entity.BohAbstractArrow) projectile).shootingEntity = owner;
    }

    public static Entity getOwner(Entity projectile) {
        if (projectile instanceof EntityArrow) return ((EntityArrow) projectile).shootingEntity;
        if (projectile instanceof EntityFireball) return ((EntityFireball) projectile).shootingEntity;
        if (projectile instanceof EntityTameable) return ((EntityTameable) projectile).getOwner();
        return null;
    }

    /** 1.20 Projectile.shoot(x, y, z, velocity, inaccuracy). */
    public static void shoot(Entity projectile, double x, double y, double z, float velocity, float inaccuracy) {
        if (projectile instanceof net.minecraft.entity.IProjectile) {
            ((net.minecraft.entity.IProjectile) projectile).setThrowableHeading(x, y, z, velocity, inaccuracy);
            return;
        }
        double len = Math.sqrt(x * x + y * y + z * z);
        java.util.Random r = projectile.worldObj.rand;
        x = x / len + r.nextGaussian() * 0.0075 * inaccuracy;
        y = y / len + r.nextGaussian() * 0.0075 * inaccuracy;
        z = z / len + r.nextGaussian() * 0.0075 * inaccuracy;
        setDeltaMovement(projectile, x * velocity, y * velocity, z * velocity);
    }

    public static void set_xPower(EntityFireball f, double v) {
        f.accelerationX = v;
    }

    public static void set_yPower(EntityFireball f, double v) {
        f.accelerationY = v;
    }

    public static void set_zPower(EntityFireball f, double v) {
        f.accelerationZ = v;
    }

    public static boolean inGround(EntityArrow a) {
        return a.inGround;
    }

    // ------------------------------------------------------------------ sounds on entities

    public static void playSound(Entity e, SoundEvent sound, float vol, float pitch) {
        if (sound == null || sound.legacyName().isEmpty()) return;
        e.playSound(sound.legacyName(), vol, pitch);
    }

    // ------------------------------------------------------------------ legacy field access (fields.map)

    public static double xOld(Entity e) {
        return e.lastTickPosX;
    }

    public static double yOld(Entity e) {
        return e.lastTickPosY;
    }

    public static double zOld(Entity e) {
        return e.lastTickPosZ;
    }

    public static double xo(Entity e) {
        return e.prevPosX;
    }

    public static double yo(Entity e) {
        return e.prevPosY;
    }

    public static double zo(Entity e) {
        return e.prevPosZ;
    }

    public static void set_xo(Entity e, double v) {
        e.prevPosX = v;
    }

    public static void set_yo(Entity e, double v) {
        e.prevPosY = v;
    }

    public static void set_zo(Entity e, double v) {
        e.prevPosZ = v;
    }

    public static float yRotO(Entity e) {
        return e.prevRotationYaw;
    }

    public static float xRotO(Entity e) {
        return e.prevRotationPitch;
    }

    public static void set_yRotO(Entity e, float v) {
        e.prevRotationYaw = v;
    }

    public static void set_xRotO(Entity e, float v) {
        e.prevRotationPitch = v;
    }

    public static float yRot(Entity e) {
        return e.rotationYaw;
    }

    public static float xRot(Entity e) {
        return e.rotationPitch;
    }

    public static void set_yRot(Entity e, float v) {
        e.rotationYaw = v;
    }

    public static void set_xRot(Entity e, float v) {
        e.rotationPitch = v;
    }

    public static float yBodyRot(EntityLivingBase e) {
        return e.renderYawOffset;
    }

    public static float yBodyRotO(EntityLivingBase e) {
        return e.prevRenderYawOffset;
    }

    public static void set_yBodyRot(EntityLivingBase e, float v) {
        e.renderYawOffset = v;
    }

    public static void set_yBodyRotO(EntityLivingBase e, float v) {
        e.prevRenderYawOffset = v;
    }

    public static float yHeadRot(EntityLivingBase e) {
        return e.rotationYawHead;
    }

    public static float yHeadRotO(EntityLivingBase e) {
        return e.prevRotationYawHead;
    }

    public static void set_yHeadRot(EntityLivingBase e, float v) {
        e.rotationYawHead = v;
    }

    public static void set_yHeadRotO(EntityLivingBase e, float v) {
        e.prevRotationYawHead = v;
    }

    public static float xxa(EntityLivingBase e) {
        return e.moveStrafing;
    }

    public static float zza(EntityLivingBase e) {
        return e.moveForward;
    }

    public static float yya(EntityLivingBase e) {
        return 0;
    }

    public static void set_xxa(EntityLivingBase e, float v) {
        e.moveStrafing = v;
    }

    public static void set_zza(EntityLivingBase e, float v) {
        e.moveForward = v;
    }

    public static int hurtTime(EntityLivingBase e) {
        return e.hurtTime;
    }

    public static boolean noPhysics(Entity e) {
        return e.noClip;
    }

    public static void set_noPhysics(Entity e, boolean b) {
        e.noClip = b;
    }

    public static boolean horizontalCollision(Entity e) {
        return e.isCollidedHorizontally;
    }

    public static boolean verticalCollision(Entity e) {
        return e.isCollidedVertically;
    }

    public static net.mcreator.boh.compat.mc.world.entity.WalkAnimationState walkAnimation(EntityLivingBase e) {
        return new net.mcreator.boh.compat.mc.world.entity.WalkAnimationState(e);
    }

    public static float position(net.mcreator.boh.compat.mc.world.entity.WalkAnimationState w) {
        return w.position();
    }

    public static float position(net.mcreator.boh.compat.mc.world.entity.WalkAnimationState w, float partial) {
        return w.position(partial);
    }

    public static float speed(net.mcreator.boh.compat.mc.world.entity.WalkAnimationState w) {
        return w.speed();
    }

    public static float speed(net.mcreator.boh.compat.mc.world.entity.WalkAnimationState w, float partial) {
        return w.speed(partial);
    }

    public static void setSize(Entity e, float w, float h) {
        if (e instanceof BohMob) ((BohMob) e).setSizeCompat(w, h);
    }
}
