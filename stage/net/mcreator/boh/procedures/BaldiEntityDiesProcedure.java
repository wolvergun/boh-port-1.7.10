package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModItems;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.world.World;
import net.mcreator.boh.compat.M;

public class BaldiEntityDiesProcedure {

    public static void execute(World world, double x, double y, double z) {
        if (Math.random() < 0.2 && world instanceof WorldServer _level) {
            EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(BohModItems.BALDI_RULER.get()));
            M.setPickUpDelay(entityToSpawn, 10);
            M.addFreshEntity(_level, entityToSpawn);
        }
    }
}
