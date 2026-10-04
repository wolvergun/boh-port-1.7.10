package net.mcreator.boh.procedures;

import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.M;

public class TormentPyramidPlayerCollidesWithThisEntityProcedure {

    public static void execute(Entity entity, Entity sourceentity) {
        if (entity != null && sourceentity != null) {
            if (M.getBoolean(M.getPersistentData(entity), "slow")) {
                M.makeStuckInBlock(sourceentity, M.defaultBlockState(Blocks.AIR), new Vec3(0.25, 0.05, 0.25));
            }
        }
    }
}
