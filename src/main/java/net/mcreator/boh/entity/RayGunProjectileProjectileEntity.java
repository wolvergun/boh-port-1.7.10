package net.mcreator.boh.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.entity.BohAbstractArrow;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.projectile.ItemSupplier;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.compat.mc.world.phys.EntityHitResult;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.procedures.RayGunProjectileProjectileProjectileHitsLivingEntityProcedure;
import net.mcreator.boh.procedures.RayGunProjectileWhileProjectileFlyingTickProcedure;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RayGunProjectileProjectileEntity extends BohAbstractArrow implements ItemSupplier {
    public static final ItemStack PROJECTILE_ITEM = M.new_ItemStack(Blocks.AIR);

    public RayGunProjectileProjectileEntity(World world) {
        super(BohModEntities.RAY_GUN_PROJECTILE_PROJECTILE.get(), world);
    }

    public RayGunProjectileProjectileEntity(EntityType<? extends RayGunProjectileProjectileEntity> type, World world) {
        super(type, world);
    }

    public RayGunProjectileProjectileEntity(EntityType<? extends RayGunProjectileProjectileEntity> type, double x, double y, double z, World world) {
        super(type, x, y, z, world);
    }

    public RayGunProjectileProjectileEntity(EntityType<? extends RayGunProjectileProjectileEntity> type, EntityLivingBase entity, World world) {
        super(type, entity, world);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ItemStack getItem() {
        return PROJECTILE_ITEM;
    }

    @Override
    protected ItemStack getPickupItem() {
        return PROJECTILE_ITEM;
    }

    @Override
    protected void doPostHurtEffects(EntityLivingBase entity) {
        super.doPostHurtEffects(entity);
        M.setArrowCount(entity, M.getArrowCount(entity) - 1);
    }

    @Override
    public void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        RayGunProjectileProjectileProjectileHitsLivingEntityProcedure.execute(this);
    }

    @Override
    public void tick() {
        super.tick();
        RayGunProjectileWhileProjectileFlyingTickProcedure.execute(M.level(this), this);
        if (M.inGround(this)) {
            M.discard(this);
        }
    }

    public static RayGunProjectileProjectileEntity shoot(World world, EntityLivingBase entity, RandomSource source) {
        return shoot(world, entity, source, 0.7F, 1.0, 0);
    }

    public static RayGunProjectileProjectileEntity shoot(World world, EntityLivingBase entity, RandomSource source, float pullingPower) {
        return shoot(world, entity, source, pullingPower * 0.7F, 1.0, 0);
    }

    public static RayGunProjectileProjectileEntity shoot(World world, EntityLivingBase entity, RandomSource random, float power, double damage, int knockback) {
        RayGunProjectileProjectileEntity entityarrow = new RayGunProjectileProjectileEntity(BohModEntities.RAY_GUN_PROJECTILE_PROJECTILE.get(), entity, world);
        entityarrow.shoot(M.getViewVector(entity, 1.0F).x, M.getViewVector(entity, 1.0F).y, M.getViewVector(entity, 1.0F).z, power * 2.0F, 0.0F);
        M.setSilent(entityarrow, true);
        M.setCritArrow(entityarrow, false);
        M.setBaseDamage(entityarrow, damage);
        M.setKnockback(entityarrow, knockback);
        M.addFreshEntity(world, entityarrow);
        M.playSound(
            world,
            null,
            M.getX(entity),
            M.getY(entity),
            M.getZ(entity),
            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:raygun")),
            SoundSource.PLAYERS,
            1.0F,
            1.0F / (M.nextFloat(random) * 0.5F + 1.0F) + power / 2.0F
        );
        return entityarrow;
    }

    public static RayGunProjectileProjectileEntity shoot(EntityLivingBase entity, EntityLivingBase target) {
        RayGunProjectileProjectileEntity entityarrow = new RayGunProjectileProjectileEntity(
            BohModEntities.RAY_GUN_PROJECTILE_PROJECTILE.get(), entity, M.level(entity)
        );
        double dx = M.getX(target) - M.getX(entity);
        double dy = M.getY(target) + M.getEyeHeight(target) - 1.1;
        double dz = M.getZ(target) - M.getZ(entity);
        entityarrow.shoot(dx, dy - M.getY(entityarrow) + Math.hypot(dx, dz) * 0.2F, dz, 1.4F, 12.0F);
        M.setSilent(entityarrow, true);
        M.setBaseDamage(entityarrow, 1.0);
        M.setKnockback(entityarrow, 0);
        M.setCritArrow(entityarrow, false);
        M.addFreshEntity(M.level(entity), entityarrow);
        M.playSound(
            M.level(entity),
            null,
            M.getX(entity),
            M.getY(entity),
            M.getZ(entity),
            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:raygun")),
            SoundSource.PLAYERS,
            1.0F,
            1.0F / (M.nextFloat(RandomSource.create()) * 0.5F + 1.0F)
        );
        return entityarrow;
    }
}
