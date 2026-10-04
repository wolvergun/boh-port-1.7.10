package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.util.Mth;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.item.Items;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class JackalopeEntityDiesProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            for (int index0 = 0; index0 < Mth.nextInt(RandomSource.create(), 1, 1); index0++) {
                if (M.isOnFire(entity)) {
                    if (world instanceof WorldServer _level) {
                        EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(Items.COOKED_RABBIT));
                        M.setPickUpDelay(entityToSpawn, 10);
                        M.addFreshEntity(_level, entityToSpawn);
                    }
                } else if (world instanceof WorldServer _level) {
                    EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(Items.RABBIT));
                    M.setPickUpDelay(entityToSpawn, 10);
                    M.addFreshEntity(_level, entityToSpawn);
                }
            }

            for (int index1 = 0; index1 < Mth.nextInt(RandomSource.create(), 0, 1); index1++) {
                if (world instanceof WorldServer _level) {
                    EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(Items.RABBIT_HIDE));
                    M.setPickUpDelay(entityToSpawn, 10);
                    M.addFreshEntity(_level, entityToSpawn);
                }
            }

            if (Math.random() < 0.001 && world instanceof WorldServer _level) {
                EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(Items.RABBIT_FOOT));
                M.setPickUpDelay(entityToSpawn, 10);
                M.addFreshEntity(_level, entityToSpawn);
            }
        }
    }
}
