package net.mcreator.boh.compat.mc.world.entity.ai.navigation;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class FlyingNavigator extends PathNavigate {
    private final EntityLiving mob;
    private double tx;
    private double ty;
    private double tz;
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
        return new PathEntity(new PathPoint[]{new PathPoint(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z))});
    }

    public PathEntity getPathToXYZ(double x, double y, double z) {
        return this.pathTo(x, y, z);
    }

    public PathEntity getPathToEntityLiving(Entity e) {
        return this.pathTo(e.posX, e.posY, e.posZ);
    }

    public boolean tryMoveToXYZ(double x, double y, double z, double speed) {
        this.targetEntity = null;
        this.tx = x;
        this.ty = y;
        this.tz = z;
        return this.start(speed);
    }

    public boolean tryMoveToEntityLiving(Entity e, double speed) {
        this.targetEntity = e;
        return this.start(speed);
    }

    public boolean setPath(PathEntity path, double speed) {
        if (path == null) {
            return false;
        } else {
            PathPoint p = path.getFinalPathPoint();
            return p == null ? false : this.tryMoveToXYZ(p.xCoord + 0.5, p.yCoord, p.zCoord + 0.5, speed);
        }
    }

    private boolean start(double speed) {
        this.speed = speed;
        this.active = true;
        this.stuckTicks = 0;
        this.lastDist = Double.MAX_VALUE;
        return true;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public boolean noPath() {
        return !this.active;
    }

    public void clearPathEntity() {
        this.active = false;
        this.targetEntity = null;
    }

    public PathEntity getPath() {
        return this.active ? this.pathTo(this.tx, this.ty, this.tz) : null;
    }

    public void onUpdateNavigation() {
        if (this.active) {
            if (this.targetEntity != null) {
                if (this.targetEntity.isDead) {
                    this.clearPathEntity();
                    return;
                }

                this.tx = this.targetEntity.posX;
                this.ty = this.targetEntity.posY + this.targetEntity.height * 0.5;
                this.tz = this.targetEntity.posZ;
            }

            double dx = this.tx - this.mob.posX;
            double dy = this.ty - this.mob.posY;
            double dz = this.tz - this.mob.posZ;
            double dist = dx * dx + dy * dy + dz * dz;
            if (this.targetEntity == null && dist < Math.max(1.0, (double)(this.mob.width * this.mob.width))) {
                this.clearPathEntity();
            } else {
                if (dist > this.lastDist - 0.01) {
                    this.stuckTicks++;
                } else {
                    this.stuckTicks = 0;
                }

                this.lastDist = dist;
                double aimY = this.ty;
                if (this.mob.isCollidedHorizontally || this.stuckTicks > 20) {
                    aimY = this.mob.posY + 2.0;
                }

                if (this.stuckTicks > 100) {
                    this.clearPathEntity();
                } else {
                    this.mob.getMoveHelper().setMoveTo(this.tx, aimY, this.tz, this.speed);
                }
            }
        }
    }
}
