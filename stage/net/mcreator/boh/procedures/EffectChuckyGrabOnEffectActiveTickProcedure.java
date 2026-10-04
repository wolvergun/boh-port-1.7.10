package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.world.damagesource.DamageTypes;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.mcreator.boh.compat.M;

public class EffectChuckyGrabOnEffectActiveTickProcedure {

    public static void execute(World world, Entity entity) {
        if (entity != null) {
            if (Math.random() < 0.01 && Math.random() < 0.05) {
                M.hurt(entity, M.new_DamageSource(M.getHolderOrThrow(M.registryOrThrow(M.registryAccess(world), Registries.DAMAGE_TYPE), DamageTypes.GENERIC)), 3.0F);
            }
        }
    }
}
