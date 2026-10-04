package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.mcreator.boh.compat.M;

public class BoilerRoomDimensionPlayerLeavesDimensionProcedure {

    public static void execute(World world, Entity entity) {
        if (entity != null) {
            if (entity instanceof EntityLivingBase _entity) {
                M.removeAllEffects(_entity);
            }
            BohMod.queueServerWork(5, () -> entity.fallDistance = 0.0F);
        }
    }
}
