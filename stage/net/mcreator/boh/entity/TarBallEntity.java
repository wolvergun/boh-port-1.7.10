package net.mcreator.boh.entity;

import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.procedures.TarBallProjectileHitsBlockProcedure;
import net.mcreator.boh.procedures.TarBallWhileProjectileFlyingTickProcedure;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.minecraft.entity.EntityLivingBase;
import net.mcreator.boh.compat.entity.BohAbstractArrow;
import net.mcreator.boh.compat.mc.world.entity.projectile.ItemSupplier;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.compat.mc.world.phys.BlockHitResult;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

/* client-only ItemSupplier */
public class TarBallEntity extends BohAbstractArrow implements ItemSupplier {

    public static final ItemStack PROJECTILE_ITEM = M.new_ItemStack(Blocks.AIR);

    public TarBallEntity(World world) {
        super((EntityType) BohModEntities.TAR_BALL.get(), world);
    }

    public TarBallEntity(EntityType<? extends TarBallEntity> type, World world) {
        super(type, world);
    }

    public TarBallEntity(EntityType<? extends TarBallEntity> type, double x, double y, double z, World world) {
        super(type, x, y, z, world);
    }

    public TarBallEntity(EntityType<? extends TarBallEntity> type, EntityLivingBase entity, World world) {
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

    public void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        TarBallProjectileHitsBlockProcedure.execute(M.level(this), M.getX(M.getBlockPos(blockHitResult)), M.getY(M.getBlockPos(blockHitResult)), M.getZ(M.getBlockPos(blockHitResult)), this);
    }

    public void tick() {
        super.tick();
        TarBallWhileProjectileFlyingTickProcedure.execute(this);
        if (M.inGround(this)) {
            M.discard(this);
        }
    }

    public static TarBallEntity shoot(World world, EntityLivingBase entity, RandomSource source) {
        return shoot(world, entity, source, 1.0F, 5.0, 5);
    }

    public static TarBallEntity shoot(World world, EntityLivingBase entity, RandomSource source, float pullingPower) {
        return shoot(world, entity, source, pullingPower * 1.0F, 5.0, 5);
    }

    public static TarBallEntity shoot(World world, EntityLivingBase entity, RandomSource random, float power, double damage, int knockback) {
        TarBallEntity entityarrow = new TarBallEntity((EntityType<? extends TarBallEntity>) BohModEntities.TAR_BALL.get(), entity, world);
        entityarrow.shoot(M.getViewVector(entity, 1.0F).x, M.getViewVector(entity, 1.0F).y, M.getViewVector(entity, 1.0F).z, power * 2.0F, 0.0F);
        M.setSilent(entityarrow, true);
        M.setCritArrow(entityarrow, false);
        M.setBaseDamage(entityarrow, damage);
        M.setKnockback(entityarrow, knockback);
        M.addFreshEntity(world, entityarrow);
        M.playSound(world, null, M.getX(entity), M.getY(entity), M.getZ(entity), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.llama.spit")), SoundSource.PLAYERS, 1.0F, 1.0F / (M.nextFloat(random) * 0.5F + 1.0F) + power / 2.0F);
        return entityarrow;
    }

    public static TarBallEntity shoot(EntityLivingBase entity, EntityLivingBase target) {
        TarBallEntity entityarrow = new TarBallEntity((EntityType<? extends TarBallEntity>) BohModEntities.TAR_BALL.get(), entity, M.level(entity));
        double dx = M.getX(target) - M.getX(entity);
        double dy = M.getY(target) + M.getEyeHeight(target) - 1.1;
        double dz = M.getZ(target) - M.getZ(entity);
        entityarrow.shoot(dx, dy - M.getY(entityarrow) + Math.hypot(dx, dz) * 0.2F, dz, 2.0F, 12.0F);
        M.setSilent(entityarrow, true);
        M.setBaseDamage(entityarrow, 5.0);
        M.setKnockback(entityarrow, 5);
        M.setCritArrow(entityarrow, false);
        M.addFreshEntity(M.level(entity), entityarrow);
        M.playSound(M.level(entity), null, M.getX(entity), M.getY(entity), M.getZ(entity), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.llama.spit")), SoundSource.PLAYERS, 1.0F, 1.0F / (M.nextFloat(RandomSource.create()) * 0.5F + 1.0F));
        return entityarrow;
    }
}
