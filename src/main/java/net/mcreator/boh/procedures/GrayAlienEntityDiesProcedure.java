package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class GrayAlienEntityDiesProcedure {
    public static void execute(World world, double x, double y, double z) {
        if (Math.random() < 0.05 && world instanceof WorldServer _level) {
            EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(BohModItems.RAY_GUN.get()));
            M.setPickUpDelay(entityToSpawn, 10);
            M.addFreshEntity(_level, entityToSpawn);
        }
    }
}
