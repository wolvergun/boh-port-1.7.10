package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.minecraft.world.World;

public class NaturalEntitySpawningConditionProcedure {
    public static boolean execute(World world) {
        return (world instanceof World ? M.dimension(world) : (world instanceof World ? M.dimension(M.getLevel(world)) : M.OVERWORLD)) == M.OVERWORLD;
    }
}
