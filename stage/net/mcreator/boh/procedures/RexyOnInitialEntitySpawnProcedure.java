package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.RexyEntity;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.mcreator.boh.compat.M;

public class RexyOnInitialEntitySpawnProcedure {

    public static void execute(World world, Entity entity) {
        if (entity != null) {
            BohMod.queueServerWork(5, () -> {
                if (Math.random() < 0.25) {
                    if (entity instanceof RexyEntity animatable) {
                        animatable.setTexture("novel_rexy");
                    }
                    if (entity instanceof RexyEntity _datEntSetI) {
                        M.set(M.getEntityData(_datEntSetI), RexyEntity.DATA_Variant, 1);
                    }
                } else if (Math.random() < 0.05) {
                    if (entity instanceof RexyEntity animatable) {
                        animatable.setTexture("rex");
                    }
                    if (entity instanceof RexyEntity _datEntSetI) {
                        M.set(M.getEntityData(_datEntSetI), RexyEntity.DATA_Variant, 2);
                    }
                } else if (entity instanceof RexyEntity _datEntSetI) {
                    M.set(M.getEntityData(_datEntSetI), RexyEntity.DATA_Variant, 0);
                }
            });
        }
    }
}
