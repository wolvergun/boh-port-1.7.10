package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.entity.WhitefaceEntity;
import net.mcreator.boh.entity.WhitefaceFriendlyEntity;
import net.mcreator.boh.network.BohModVariables;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class KillWFProcedure {
    public static void execute(World world, Entity entity) {
        if (entity != null
            && BohModVariables.MapVariables.get(world).Kill_WF == 1.0
            && (entity instanceof WhitefaceEntity || entity instanceof WhitefaceFriendlyEntity)
            && !M.isClientSide(M.level(entity))) {
            M.discard(entity);
        }
    }
}
