package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.item.Items;
import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class DogwoodLeavesBlockDestroyedByPlayerProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null
            && M.getItem(entity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY) != Items.SHEARS
            && Math.random() < 0.05
            && world instanceof WorldServer _level) {
            EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(BohModBlocks.DOGWOOD_SAPLING.get()));
            M.setPickUpDelay(entityToSpawn, 10);
            M.addFreshEntity(_level, entityToSpawn);
        }
    }
}
