package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.JamesSunderlandEntity;
import net.minecraft.entity.Entity;

public class JamesSunderlandEntityIsHurtProcedure {
    public static void execute(Entity entity) {
        if (entity != null && entity instanceof JamesSunderlandEntity) {
            ((JamesSunderlandEntity)entity).setAnimation("hurt");
        }
    }
}
