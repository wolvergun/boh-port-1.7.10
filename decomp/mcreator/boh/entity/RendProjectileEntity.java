package net.mcreator.boh.entity;

import javax.annotation.Nullable;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.procedures.RendProjectileProjectileHitsLivingEntityProcedure;
import net.mcreator.boh.procedures.RendProjectileWhileProjectileFlyingTickProcedure;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class RendProjectileEntity extends AbstractArrow implements ItemSupplier {
   public static final ItemStack PROJECTILE_ITEM = new ItemStack(Blocks.AIR);

   public RendProjectileEntity(SpawnEntity packet, Level world) {
      super((EntityType)BohModEntities.REND_PROJECTILE.get(), world);
   }

   public RendProjectileEntity(EntityType<? extends RendProjectileEntity> type, Level world) {
      super(type, world);
   }

   public RendProjectileEntity(EntityType<? extends RendProjectileEntity> type, double x, double y, double z, Level world) {
      super(type, x, y, z, world);
   }

   public RendProjectileEntity(EntityType<? extends RendProjectileEntity> type, LivingEntity entity, Level world) {
      super(type, entity, world);
   }

   public Packet<ClientGamePacketListener> getAddEntityPacket() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   @OnlyIn(Dist.CLIENT)
   public ItemStack getItem() {
      return PROJECTILE_ITEM;
   }

   protected ItemStack getPickupItem() {
      return PROJECTILE_ITEM;
   }

   protected void doPostHurtEffects(LivingEntity entity) {
      super.doPostHurtEffects(entity);
      entity.setArrowCount(entity.getArrowCount() - 1);
   }

   @Nullable
   protected EntityHitResult findHitEntity(Vec3 projectilePosition, Vec3 deltaPosition) {
      double d0 = Double.MAX_VALUE;
      Entity entity = null;
      AABB lookupBox = this.getBoundingBox().expandTowards(deltaPosition).inflate(1.0);

      for (Entity entity1 : this.level().getEntities(this, lookupBox, x$0 -> this.canHitEntity(x$0))) {
         if (entity1 != this.getOwner()) {
            AABB aabb = entity1.getBoundingBox();
            if (aabb.intersects(lookupBox)) {
               double d1 = projectilePosition.distanceToSqr(projectilePosition);
               if (d1 < d0) {
                  entity = entity1;
                  d0 = d1;
               }
            }
         }
      }

      return entity == null ? null : new EntityHitResult(entity);
   }

   public void onHitEntity(EntityHitResult entityHitResult) {
      super.onHitEntity(entityHitResult);
      RendProjectileProjectileHitsLivingEntityProcedure.execute();
   }

   public void tick() {
      super.tick();
      RendProjectileWhileProjectileFlyingTickProcedure.execute(this.level(), this);
      if (this.inGround) {
         this.discard();
      }
   }

   public static RendProjectileEntity shoot(Level world, LivingEntity entity, RandomSource source) {
      return shoot(world, entity, source, 0.0F, 1.0, 0);
   }

   public static RendProjectileEntity shoot(Level world, LivingEntity entity, RandomSource source, float pullingPower) {
      return shoot(world, entity, source, pullingPower * 0.0F, 1.0, 0);
   }

   public static RendProjectileEntity shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
      RendProjectileEntity entityarrow = new RendProjectileEntity(
         (EntityType<? extends RendProjectileEntity>)BohModEntities.REND_PROJECTILE.get(), entity, world
      );
      entityarrow.shoot(entity.getViewVector(1.0F).x, entity.getViewVector(1.0F).y, entity.getViewVector(1.0F).z, power * 2.0F, 0.0F);
      entityarrow.setSilent(true);
      entityarrow.setCritArrow(false);
      entityarrow.setBaseDamage(damage);
      entityarrow.setKnockback(knockback);
      world.addFreshEntity(entityarrow);
      world.playSound(
         null,
         entity.getX(),
         entity.getY(),
         entity.getZ(),
         (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:wf_pistol_shoot")),
         SoundSource.PLAYERS,
         1.0F,
         1.0F / (random.nextFloat() * 0.5F + 1.0F) + power / 2.0F
      );
      return entityarrow;
   }

   public static RendProjectileEntity shoot(LivingEntity entity, LivingEntity target) {
      RendProjectileEntity entityarrow = new RendProjectileEntity(
         (EntityType<? extends RendProjectileEntity>)BohModEntities.REND_PROJECTILE.get(), entity, entity.level()
      );
      double dx = target.getX() - entity.getX();
      double dy = target.getY() + target.getEyeHeight() - 1.1;
      double dz = target.getZ() - entity.getZ();
      entityarrow.shoot(dx, dy - entityarrow.getY() + Math.hypot(dx, dz) * 0.2F, dz, 0.0F, 12.0F);
      entityarrow.setSilent(true);
      entityarrow.setBaseDamage(1.0);
      entityarrow.setKnockback(0);
      entityarrow.setCritArrow(false);
      entity.level().addFreshEntity(entityarrow);
      entity.level()
         .playSound(
            null,
            entity.getX(),
            entity.getY(),
            entity.getZ(),
            (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:wf_pistol_shoot")),
            SoundSource.PLAYERS,
            1.0F,
            1.0F / (RandomSource.create().nextFloat() * 0.5F + 1.0F)
         );
      return entityarrow;
   }
}
