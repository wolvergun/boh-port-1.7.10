package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.item.GojiHeadItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import software.bernie.geckolib.animatable.GeoItem;

public class PlayerSpitGojiProcedure {
   public static void execute(LevelAccessor world, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         if (!entity.getPersistentData().getBoolean("logic_spit") && entity.isShiftKeyDown()) {
            if (itemstack.getItem() instanceof GojiHeadItem armor && armor instanceof GeoItem) {
               itemstack.getOrCreateTag().putString("geckoAnim", "spit");
            }

            BohMod.queueServerWork(5, () -> {
               Entity _shootFrom = entity;
               Level projectileLevel = _shootFrom.level();
               if (!projectileLevel.isClientSide()) {
                  Projectile _entityToSpawn = (new Object() {
                     public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
                        AbstractArrow entityToSpawn = new Arrow(EntityType.ARROW, level);
                        entityToSpawn.setOwner(shooter);
                        entityToSpawn.setBaseDamage(damage);
                        entityToSpawn.setKnockback(knockback);
                        return entityToSpawn;
                     }
                  }).getArrow(projectileLevel, entity, 5.0F, 1);
                  _entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
                  _entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 1.0F, 0.0F);
                  projectileLevel.addFreshEntity(_entityToSpawn);
               }
            });
            entity.getPersistentData().putBoolean("logic_spit", true);
         }

         if (!entity.isShiftKeyDown()) {
            entity.getPersistentData().putBoolean("logic_spit", true);
         }
      }
   }
}
