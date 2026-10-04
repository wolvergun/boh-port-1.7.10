package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class OverlayNPC001DisplayOverlayIngameProcedure {
    public static boolean execute(Entity entity) {
        return entity == null ? false : entity instanceof EntityLivingBase _livEnt0 && M.hasEffect(_livEnt0, BohModMobEffects.NPC_000INFLUENCE.get());
    }
}
