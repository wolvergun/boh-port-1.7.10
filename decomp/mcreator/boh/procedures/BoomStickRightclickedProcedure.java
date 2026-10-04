package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.ShotgunProjectileEntity;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.item.BoomStickItem;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class BoomStickRightclickedProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         ItemStack _ist = itemstack;
         if (_ist.hurt(1, RandomSource.create(), null)) {
            _ist.shrink(1);
            _ist.setDamageValue(0);
         }

         if (entity instanceof Player _player) {
            _player.getCooldowns().addCooldown(itemstack.getItem(), 34);
         }

         Entity _shootFrom = entity;
         Level projectileLevel = _shootFrom.level();
         if (!projectileLevel.isClientSide()) {
            Projectile _entityToSpawn = (new Object() {
                  public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
                     AbstractArrow entityToSpawn = new ShotgunProjectileEntity(
                        (EntityType<? extends ShotgunProjectileEntity>)BohModEntities.SHOTGUN_PROJECTILE.get(), level
                     );
                     entityToSpawn.setOwner(shooter);
                     entityToSpawn.setBaseDamage(damage);
                     entityToSpawn.setKnockback(knockback);
                     entityToSpawn.setSilent(true);
                     return entityToSpawn;
                  }
               })
               .getArrow(projectileLevel, entity, 2.5F, 1);
            _entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
            _entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 3.0F, 10.0F);
            projectileLevel.addFreshEntity(_entityToSpawn);
         }

         Entity _shootFromx = entity;
         projectileLevel = _shootFromx.level();
         if (!projectileLevel.isClientSide()) {
            Projectile _entityToSpawn = (new Object() {
                  public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
                     AbstractArrow entityToSpawn = new ShotgunProjectileEntity(
                        (EntityType<? extends ShotgunProjectileEntity>)BohModEntities.SHOTGUN_PROJECTILE.get(), level
                     );
                     entityToSpawn.setOwner(shooter);
                     entityToSpawn.setBaseDamage(damage);
                     entityToSpawn.setKnockback(knockback);
                     entityToSpawn.setSilent(true);
                     return entityToSpawn;
                  }
               })
               .getArrow(projectileLevel, entity, 2.5F, 1);
            _entityToSpawn.setPos(_shootFromx.getX(), _shootFromx.getEyeY() - 0.1, _shootFromx.getZ());
            _entityToSpawn.shoot(_shootFromx.getLookAngle().x, _shootFromx.getLookAngle().y, _shootFromx.getLookAngle().z, 3.0F, 10.0F);
            projectileLevel.addFreshEntity(_entityToSpawn);
         }

         Entity _shootFromxx = entity;
         projectileLevel = _shootFromxx.level();
         if (!projectileLevel.isClientSide()) {
            Projectile _entityToSpawn = (new Object() {
                  public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
                     AbstractArrow entityToSpawn = new ShotgunProjectileEntity(
                        (EntityType<? extends ShotgunProjectileEntity>)BohModEntities.SHOTGUN_PROJECTILE.get(), level
                     );
                     entityToSpawn.setOwner(shooter);
                     entityToSpawn.setBaseDamage(damage);
                     entityToSpawn.setKnockback(knockback);
                     entityToSpawn.setSilent(true);
                     return entityToSpawn;
                  }
               })
               .getArrow(projectileLevel, entity, 2.5F, 1);
            _entityToSpawn.setPos(_shootFromxx.getX(), _shootFromxx.getEyeY() - 0.1, _shootFromxx.getZ());
            _entityToSpawn.shoot(_shootFromxx.getLookAngle().x, _shootFromxx.getLookAngle().y, _shootFromxx.getLookAngle().z, 3.0F, 10.0F);
            projectileLevel.addFreshEntity(_entityToSpawn);
         }

         Entity _shootFromxxx = entity;
         projectileLevel = _shootFromxxx.level();
         if (!projectileLevel.isClientSide()) {
            Projectile _entityToSpawn = (new Object() {
                  public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
                     AbstractArrow entityToSpawn = new ShotgunProjectileEntity(
                        (EntityType<? extends ShotgunProjectileEntity>)BohModEntities.SHOTGUN_PROJECTILE.get(), level
                     );
                     entityToSpawn.setOwner(shooter);
                     entityToSpawn.setBaseDamage(damage);
                     entityToSpawn.setKnockback(knockback);
                     entityToSpawn.setSilent(true);
                     return entityToSpawn;
                  }
               })
               .getArrow(projectileLevel, entity, 2.5F, 1);
            _entityToSpawn.setPos(_shootFromxxx.getX(), _shootFromxxx.getEyeY() - 0.1, _shootFromxxx.getZ());
            _entityToSpawn.shoot(_shootFromxxx.getLookAngle().x, _shootFromxxx.getLookAngle().y, _shootFromxxx.getLookAngle().z, 3.0F, 10.0F);
            projectileLevel.addFreshEntity(_entityToSpawn);
         }

         Entity _shootFromxxxx = entity;
         projectileLevel = _shootFromxxxx.level();
         if (!projectileLevel.isClientSide()) {
            Projectile _entityToSpawn = (new Object() {
                  public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
                     AbstractArrow entityToSpawn = new ShotgunProjectileEntity(
                        (EntityType<? extends ShotgunProjectileEntity>)BohModEntities.SHOTGUN_PROJECTILE.get(), level
                     );
                     entityToSpawn.setOwner(shooter);
                     entityToSpawn.setBaseDamage(damage);
                     entityToSpawn.setKnockback(knockback);
                     entityToSpawn.setSilent(true);
                     return entityToSpawn;
                  }
               })
               .getArrow(projectileLevel, entity, 2.5F, 1);
            _entityToSpawn.setPos(_shootFromxxxx.getX(), _shootFromxxxx.getEyeY() - 0.1, _shootFromxxxx.getZ());
            _entityToSpawn.shoot(_shootFromxxxx.getLookAngle().x, _shootFromxxxx.getLookAngle().y, _shootFromxxxx.getLookAngle().z, 3.0F, 10.0F);
            projectileLevel.addFreshEntity(_entityToSpawn);
         }

         Entity _shootFromxxxxx = entity;
         projectileLevel = _shootFromxxxxx.level();
         if (!projectileLevel.isClientSide()) {
            Projectile _entityToSpawn = (new Object() {
                  public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
                     AbstractArrow entityToSpawn = new ShotgunProjectileEntity(
                        (EntityType<? extends ShotgunProjectileEntity>)BohModEntities.SHOTGUN_PROJECTILE.get(), level
                     );
                     entityToSpawn.setOwner(shooter);
                     entityToSpawn.setBaseDamage(damage);
                     entityToSpawn.setKnockback(knockback);
                     entityToSpawn.setSilent(true);
                     return entityToSpawn;
                  }
               })
               .getArrow(projectileLevel, entity, 2.5F, 1);
            _entityToSpawn.setPos(_shootFromxxxxx.getX(), _shootFromxxxxx.getEyeY() - 0.1, _shootFromxxxxx.getZ());
            _entityToSpawn.shoot(_shootFromxxxxx.getLookAngle().x, _shootFromxxxxx.getLookAngle().y, _shootFromxxxxx.getLookAngle().z, 3.0F, 10.0F);
            projectileLevel.addFreshEntity(_entityToSpawn);
         }

         if (itemstack.getItem() instanceof BoomStickItem) {
            itemstack.getOrCreateTag().putString("geckoAnim", "shoot");
         }

         if (!world.isClientSide()) {
            if (world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:shotgun_shoot")),
                     SoundSource.AMBIENT,
                     1.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:shotgun_shoot")),
                     SoundSource.AMBIENT,
                     1.0F,
                     1.0F,
                     false
                  );
               }
            }

            BohMod.queueServerWork(
               34,
               () -> {
                  if (world instanceof Level _level) {
                     if (!_level.isClientSide()) {
                        _level.playSound(
                           null,
                           BlockPos.containing(x, y, z),
                           (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:shotgun_cocking")),
                           SoundSource.AMBIENT,
                           1.0F,
                           1.0F
                        );
                     } else {
                        _level.playLocalSound(
                           x,
                           y,
                           z,
                           (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:shotgun_cocking")),
                           SoundSource.AMBIENT,
                           1.0F,
                           1.0F,
                           false
                        );
                     }
                  }
               }
            );
         }

         entity.setDeltaMovement(
            new Vec3(
               entity.getDeltaMovement().x() - entity.getLookAngle().x * 1.0,
               entity.getDeltaMovement().y(),
               entity.getDeltaMovement().z() - entity.getLookAngle().z * 1.0
            )
         );
         Entity _ent = entity;
         _ent.setYRot(entity.getYRot());
         _ent.setXRot(entity.getXRot() - 15.0F);
         _ent.setYBodyRot(_ent.getYRot());
         _ent.setYHeadRot(_ent.getYRot());
         _ent.yRotO = _ent.getYRot();
         _ent.xRotO = _ent.getXRot();
         if (_ent instanceof LivingEntity _entity) {
            _entity.yBodyRotO = _entity.getYRot();
            _entity.yHeadRotO = _entity.getYRot();
         }
      }
   }
}
