package net.mcreator.boh.procedures;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.level.ExplosionInteraction;
import net.mcreator.boh.compat.M;

public class ChildSpawnProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (entity instanceof EntityLivingBase _livEnt0 && M.isBaby(_livEnt0) && world instanceof World _level && !M.isClientSide(_level)) {
                M.explode(_level, null, x, y, z, 4.0F, ExplosionInteraction.NONE);
            }
        }
    }
}
