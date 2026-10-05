package net.mcreator.boh.compat.mc.world.entity.ai.navigation;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.world.World;

/**
 * Ground navigator whose search area fits the trip. 1.7.10 pathfinding snapshots every chunk within (search range +
 * 16) blocks of the mob before each search, and Hodgepodge (in most 1.7.10 packs) refuses the search outright when any
 * of those chunks is not loaded. The original mod gives many mobs follow ranges of 100 to 1000 blocks, so they found
 * no path near the edge of the loaded area and stood still, or stopped mid-chase. 1.20 has neither cost, so here only
 * the path search is narrowed: to the distance to the destination plus 16 blocks (at least 16, at most 64). Target
 * selection still uses the full follow range.
 */
public class BohPathNavigate extends PathNavigate {

    public static final float MAX_SEARCH_RANGE = 64.0F;
    private static final float MIN_SEARCH_RANGE = 16.0F, MARGIN = 16.0F;

    private final EntityLiving mob;
    /** Search range for the search in progress, or 0 outside one. */
    private float tripRange;

    public BohPathNavigate(EntityLiving mob, World world) {
        super(mob, world);
        this.mob = mob;
    }

    @Override
    public float getPathSearchRange() {
        float r = Math.min(super.getPathSearchRange(), MAX_SEARCH_RANGE);
        return tripRange > 0 ? Math.min(r, tripRange) : r;
    }

    @Override
    public PathEntity getPathToEntityLiving(Entity target) {
        return withTrip(target.posX, target.posY, target.posZ, () -> super.getPathToEntityLiving(target));
    }

    @Override
    public PathEntity getPathToXYZ(double x, double y, double z) {
        return withTrip(x, y, z, () -> super.getPathToXYZ(x, y, z));
    }

    private PathEntity withTrip(double x, double y, double z, java.util.function.Supplier<PathEntity> search) {
        double dx = x - mob.posX, dy = y - mob.posY, dz = z - mob.posZ;
        tripRange = (float) Math.max(MIN_SEARCH_RANGE, Math.sqrt(dx * dx + dy * dy + dz * dz) + MARGIN);
        try {
            return search.get();
        } finally {
            tripRange = 0;
        }
    }
}
