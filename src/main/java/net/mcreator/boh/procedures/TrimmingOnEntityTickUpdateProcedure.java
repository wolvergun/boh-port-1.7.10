package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class TrimmingOnEntityTickUpdateProcedure {
    public static void execute(Entity entity) {
        if (entity != null && entity instanceof EntityLivingBase _livEnt0 && M.hasEffect(_livEnt0, MobEffects.MOVEMENT_SLOWDOWN)) {
            M.makeStuckInBlock(entity, M.defaultBlockState(Blocks.AIR), new Vec3(0.25, 0.05, 0.25));
        }
    }
}
