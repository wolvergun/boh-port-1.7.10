package net.mcreator.boh.compat.mc.world.entity.ai.navigation;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * A {@link PathNavigate} for flying/swimming mobs: no ground pathfinding, the mob steers straight at its target
 * through its move helper (see FlyingMoveControl), rising over obstacles in the way.
 */
public class FlyingNavigator extends PathNavigate {

    private final EntityLiving mob;
    private double tx, ty, tz;
    private Entity targetEntity;
    private boolean active;
    private double speed;
    private int stuckTicks;
    private double lastDist = Double.MAX_VALUE;

    public FlyingNavigator(EntityLiving mob, World world) {
        super(mob, world);
        this.mob = mob;
    }

    private PathEntity pathTo(double x, double y, double z) {
        return new PathEntity(new PathPoint[] {
            new PathPoint(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z)) });
    }

    @Override
    public PathEntity getPathToXYZ(double x, double y, double z) {
        return pathTo(x, y, z);
    }

    @Override
    public PathEntity getPathToEntityLiving(Entity e) {
        return pathTo(e.posX, e.posY, e.posZ);
    }

    @Override
    public boolean tryMoveToXYZ(double x, double y, double z, double speed) {
        targetEntity = null;
        tx = x;
        ty = y;
        tz = z;
        return start(speed);
    }

    @Override
    public boolean tryMoveToEntityLiving(Entity e, double speed) {
        targetEntity = e;
        return start(speed);
    }

    @Override
    public boolean setPath(PathEntity path, double speed) {
        if (path == null) return false;
        PathPoint p = path.getFinalPathPoint();
        if (p == null) return false;
        return tryMoveToXYZ(p.xCoord + 0.5, p.yCoord, p.zCoord + 0.5, speed);
    }

    private boolean start(double speed) {
        this.speed = speed;
        active = true;
        stuckTicks = 0;
        lastDist = Double.MAX_VALUE;
        return true;
    }

    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public boolean noPath() {
        return !active;
    }

    @Override
    public void clearPathEntity() {
        active = false;
        targetEntity = null;
    }

    @Override
    public PathEntity getPath() {
        return active ? pathTo(tx, ty, tz) : null;
    }

    @Override
    public void onUpdateNavigation() {
        if (!active) return;
        if (targetEntity != null) {
            if (targetEntity.isDead) {
                clearPathEntity();
                return;
            }
            tx = targetEntity.posX;
            ty = targetEntity.posY + targetEntity.height * 0.5;
            tz = targetEntity.posZ;
        }
        double dx = tx - mob.posX, dy = ty - mob.posY, dz = tz - mob.posZ;
        double dist = dx * dx + dy * dy + dz * dz;
        if (targetEntity == null && dist < Math.max(1.0, mob.width * mob.width)) {
            clearPathEntity();
            return;
        }
        if (dist > lastDist - 0.01) stuckTicks++;
        else stuckTicks = 0;
        lastDist = dist;
        double aimY = ty;
        if (mob.isCollidedHorizontally || stuckTicks > 20) aimY = mob.posY + 2.0;
        if (stuckTicks > 100) {
            clearPathEntity();
            return;
        }
        mob.getMoveHelper().setMoveTo(tx, aimY, tz, speed);
    }
}
