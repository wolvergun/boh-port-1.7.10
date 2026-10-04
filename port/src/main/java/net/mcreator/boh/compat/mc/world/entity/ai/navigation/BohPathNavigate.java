package net.mcreator.boh.compat.mc.world.entity.ai.navigation;

import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.world.World;

/**
 * Ground navigator with a capped search range. 1.7.10 pathfinding snapshots every chunk within (follow range + 16)
 * blocks before each search, so the original mod's huge follow ranges (300 for the Lifeform) made every attempt load
 * and generate ~1600 chunks around the mob, and the mob never got a path to its target. 1.20 has no such cost, so only
 * the path search is capped here; target selection still uses the full follow range.
 */
public class BohPathNavigate extends PathNavigate {

    public static final float MAX_SEARCH_RANGE = 64.0F;

    public BohPathNavigate(EntityLiving mob, World world) {
        super(mob, world);
    }

    @Override
    public float getPathSearchRange() {
        return Math.min(super.getPathSearchRange(), MAX_SEARCH_RANGE);
    }
}
