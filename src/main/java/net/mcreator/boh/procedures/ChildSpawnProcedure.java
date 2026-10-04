package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.level.ExplosionInteraction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class ChildSpawnProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null && entity instanceof EntityLivingBase _livEnt0 && M.isBaby(_livEnt0) && world instanceof World && !M.isClientSide(world)) {
            M.explode(world, null, x, y, z, 4.0F, ExplosionInteraction.NONE);
        }
    }
}
