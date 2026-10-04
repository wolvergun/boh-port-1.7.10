package net.mcreator.boh.entity;

import javax.annotation.Nullable;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.procedures.HypnoShotProjectileProjectileHitsLivingEntityProcedure;
import net.mcreator.boh.procedures.HypnoShotProjectileProjectileHitsPlayerProcedure;
import net.mcreator.boh.procedures.HypnoShotProjectileWhileProjectileFlyingTickProcedure;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.mcreator.boh.compat.entity.BohAbstractArrow;
import net.mcreator.boh.compat.mc.world.entity.projectile.ItemSupplier;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.EntityHitResult;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

/* client-only ItemSupplier */
public class HypnoShotProjectileEntity extends BohAbstractArrow implements ItemSupplier {

    public static final ItemStack PROJECTILE_ITEM = M.new_ItemStack(Blocks.AIR);

    public HypnoShotProjectileEntity(World world) {
        super((EntityType) BohModEntities.HYPNO_SHOT_PROJECTILE.get(), world);
    }

    public HypnoShotProjectileEntity(EntityType<? extends HypnoShotProjectileEntity> type, World world) {
        super(type, world);
    }

    public HypnoShotProjectileEntity(EntityType<? extends HypnoShotProjectileEntity> type, double x, double y, double z, World world) {
        super(type, x, y, z, world);
    }

    public HypnoShotProjectileEntity(EntityType<? extends HypnoShotProjectileEntity> type, EntityLivingBase entity, World world) {
        super(type, entity, world);
    }

    @SideOnly(Side.CLIENT)
    public ItemStack getItem() {
        return PROJECTILE_ITEM;
    }

    protected ItemStack getPickupItem() {
        return PROJECTILE_ITEM;
    }

    protected void doPostHurtEffects(EntityLivingBase entity) {
        super.doPostHurtEffects(entity);
        M.setArrowCount(entity, M.getArrowCount(entity) - 1);
    }

    @Nullable
    protected EntityHitResult findHitEntity(Vec3 projectilePosition, Vec3 deltaPosition) {
        double d0 = Double.MAX_VALUE;
        Entity entity = null;
        AABB lookupBox = M.getBoundingBox(this).expandTowards(deltaPosition).inflate(1.0);
        for (Entity entity1 : M.getEntities(M.level(this), this, lookupBox, x$0 -> M.canHitEntity(this, x$0))) {
            if (entity1 != M.getOwner(this)) {
                AABB aabb = M.getBoundingBox(entity1);
                if (aabb.intersects(lookupBox)) {
                    double d1 = M.distanceToSqr(projectilePosition, projectilePosition);
                    if (d1 < d0) {
                        entity = entity1;
                        d0 = d1;
                    }
                }
            }
        }
        return entity == null ? null : new EntityHitResult(entity);
    }

    public void playerTouch(EntityPlayer entity) {
        super.playerTouch(entity);
        HypnoShotProjectileProjectileHitsPlayerProcedure.execute(entity);
    }

    public void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        HypnoShotProjectileProjectileHitsLivingEntityProcedure.execute(this);
    }

    public void tick() {
        super.tick();
        HypnoShotProjectileWhileProjectileFlyingTickProcedure.execute(M.level(this), this);
        if (M.inGround(this)) {
            M.discard(this);
        }
    }

    public static HypnoShotProjectileEntity shoot(World world, EntityLivingBase entity, RandomSource source) {
        return shoot(world, entity, source, 0.7F, 1.0, 0);
    }

    public static HypnoShotProjectileEntity shoot(World world, EntityLivingBase entity, RandomSource source, float pullingPower) {
        return shoot(world, entity, source, pullingPower * 0.7F, 1.0, 0);
    }

    public static HypnoShotProjectileEntity shoot(World world, EntityLivingBase entity, RandomSource random, float power, double damage, int knockback) {
        HypnoShotProjectileEntity entityarrow = new HypnoShotProjectileEntity((EntityType<? extends HypnoShotProjectileEntity>) BohModEntities.HYPNO_SHOT_PROJECTILE.get(), entity, world);
        entityarrow.shoot(M.getViewVector(entity, 1.0F).x, M.getViewVector(entity, 1.0F).y, M.getViewVector(entity, 1.0F).z, power * 2.0F, 0.0F);
        M.setSilent(entityarrow, true);
        M.setCritArrow(entityarrow, false);
        M.setBaseDamage(entityarrow, damage);
        M.setKnockback(entityarrow, knockback);
        M.addFreshEntity(world, entityarrow);
        M.playSound(world, null, M.getX(entity), M.getY(entity), M.getZ(entity), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.illusioner.cast_spell")), SoundSource.PLAYERS, 1.0F, 1.0F / (M.nextFloat(random) * 0.5F + 1.0F) + power / 2.0F);
        return entityarrow;
    }

    public static HypnoShotProjectileEntity shoot(EntityLivingBase entity, EntityLivingBase target) {
        HypnoShotProjectileEntity entityarrow = new HypnoShotProjectileEntity((EntityType<? extends HypnoShotProjectileEntity>) BohModEntities.HYPNO_SHOT_PROJECTILE.get(), entity, M.level(entity));
        double dx = M.getX(target) - M.getX(entity);
        double dy = M.getY(target) + M.getEyeHeight(target) - 1.1;
        double dz = M.getZ(target) - M.getZ(entity);
        entityarrow.shoot(dx, dy - M.getY(entityarrow) + Math.hypot(dx, dz) * 0.2F, dz, 1.4F, 12.0F);
        M.setSilent(entityarrow, true);
        M.setBaseDamage(entityarrow, 1.0);
        M.setKnockback(entityarrow, 0);
        M.setCritArrow(entityarrow, false);
        M.addFreshEntity(M.level(entity), entityarrow);
        M.playSound(M.level(entity), null, M.getX(entity), M.getY(entity), M.getZ(entity), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.illusioner.cast_spell")), SoundSource.PLAYERS, 1.0F, 1.0F / (M.nextFloat(RandomSource.create()) * 0.5F + 1.0F));
        return entityarrow;
    }
}
