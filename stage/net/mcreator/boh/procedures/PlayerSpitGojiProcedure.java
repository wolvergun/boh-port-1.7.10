package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.item.GojiHeadItem;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.entity.BohAbstractArrow;
import net.mcreator.boh.compat.entity.BohArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.mcreator.boh.geo.GeoItem;
import net.mcreator.boh.compat.M;

public class PlayerSpitGojiProcedure {

    public static void execute(World world, Entity entity, ItemStack itemstack) {
        if (entity != null) {
            if (!M.getBoolean(M.getPersistentData(entity), "logic_spit") && M.isShiftKeyDown(entity)) {
                if (M.getItem(itemstack) instanceof GojiHeadItem armor && armor instanceof GeoItem) {
                    M.putString(M.getOrCreateTag(itemstack), "geckoAnim", "spit");
                }
                BohMod.queueServerWork(5, () -> {
                    Entity _shootFrom = entity;
                    World projectileLevel = M.level(_shootFrom);
                    if (!M.isClientSide(projectileLevel)) {
                        Entity _entityToSpawn = (new Object() {

                            public Entity getArrow(World level, Entity shooter, float damage, int knockback) {
                                BohAbstractArrow entityToSpawn = new BohArrow(EntityType.ARROW, level);
                                M.setOwner(entityToSpawn, shooter);
                                M.setBaseDamage(entityToSpawn, damage);
                                M.setKnockback(entityToSpawn, knockback);
                                return entityToSpawn;
                            }
                        }).getArrow(projectileLevel, entity, 5.0F, 1);
                        M.setPos(_entityToSpawn, M.getX(_shootFrom), M.getEyeY(_shootFrom) - 0.1, M.getZ(_shootFrom));
                        M.shoot(_entityToSpawn, M.getLookAngle(_shootFrom).x, M.getLookAngle(_shootFrom).y, M.getLookAngle(_shootFrom).z, 1.0F, 0.0F);
                        M.addFreshEntity(projectileLevel, _entityToSpawn);
                    }
                });
                M.putBoolean(M.getPersistentData(entity), "logic_spit", true);
            }
            if (!M.isShiftKeyDown(entity)) {
                M.putBoolean(M.getPersistentData(entity), "logic_spit", true);
            }
        }
    }
}
