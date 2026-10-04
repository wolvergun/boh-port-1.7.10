package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.world.damagesource.DamageTypes;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class TakenPillarPlayerCollidesWithThisEntityProcedure {
    public static void execute(World world, Entity entity, Entity sourceentity) {
        if (entity != null && sourceentity != null && M.getBoolean(M.getPersistentData(entity), "damage")) {
            M.hurt(
                sourceentity,
                M.new_DamageSource(M.getHolderOrThrow(M.registryOrThrow(M.registryAccess(world), Registries.DAMAGE_TYPE), DamageTypes.MAGIC)),
                2.0F
            );
        }
    }
}
