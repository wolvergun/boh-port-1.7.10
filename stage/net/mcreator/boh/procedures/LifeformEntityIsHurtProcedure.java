package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.world.damagesource.DamageTypes;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.mcreator.boh.compat.M;

public class LifeformEntityIsHurtProcedure {

    public static void execute(World world, Entity entity) {
        if (entity != null) {
            if (M.isOnFire(entity)) {
                M.hurt(entity, M.new_DamageSource(M.getHolderOrThrow(M.registryOrThrow(M.registryAccess(world), Registries.DAMAGE_TYPE), DamageTypes.LAVA)), 5.0F);
            }
        }
    }
}
