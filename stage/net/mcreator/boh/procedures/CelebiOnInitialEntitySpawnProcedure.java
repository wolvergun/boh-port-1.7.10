package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.CelebiEntity;
import net.minecraft.entity.Entity;

public class CelebiOnInitialEntitySpawnProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            if (entity instanceof CelebiEntity) {
                ((CelebiEntity) entity).setAnimation("spawn");
            }
        }
    }
}
