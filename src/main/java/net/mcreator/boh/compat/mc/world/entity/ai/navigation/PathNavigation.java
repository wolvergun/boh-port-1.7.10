package net.mcreator.boh.compat.mc.world.entity.ai.navigation;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.level.pathfinder.Path;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.world.World;

public class PathNavigation {
    protected final EntityLiving mob;
    protected final PathNavigate nav;
    protected double speedModifier = 1.0;

    public PathNavigation(EntityLiving mob, World world) {
        this(mob, new PathNavigate(mob, world));
    }

    public PathNavigation(EntityLiving mob, PathNavigate nav) {
        this.mob = mob;
        this.nav = nav;
    }

    public static PathNavigation of(EntityLiving mob) {
        return new PathNavigation(mob, mob.getNavigator());
    }

    public PathNavigate vanilla() {
        return this.nav;
    }

    public boolean moveTo(double x, double y, double z, double speed) {
        this.speedModifier = speed;
        return this.nav.tryMoveToXYZ(x, y, z, speed);
    }

    public boolean moveTo(Entity e, double speed) {
        this.speedModifier = speed;
        return e instanceof EntityLivingBase
            ? this.nav.tryMoveToEntityLiving(e, speed)
            : this.nav.tryMoveToXYZ(e.posX, e.posY, e.posZ, speed);
    }

    public boolean moveTo(Path path, double speed) {
        this.speedModifier = speed;
        return path != null && this.nav.setPath(path.toVanilla(), speed);
    }

    public Path createPath(double x, double y, double z, int accuracy) {
        return Path.of(this.nav.getPathToXYZ(x, y, z));
    }

    public Path createPath(BlockPos pos, int accuracy) {
        return this.createPath(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, accuracy);
    }

    public Path createPath(Entity e, int accuracy) {
        return e instanceof EntityLivingBase ? Path.of(this.nav.getPathToEntityLiving(e)) : this.createPath(e.posX, e.posY, e.posZ, accuracy);
    }

    public Path getPath() {
        return Path.of(this.nav.getPath());
    }

    public void stop() {
        this.nav.clearPathEntity();
    }

    public boolean isDone() {
        return this.nav.noPath();
    }

    public boolean isInProgress() {
        return !this.nav.noPath();
    }

    public void setSpeedModifier(double speed) {
        this.speedModifier = speed;
        this.nav.setSpeed(speed);
    }

    public void setCanFloat(boolean b) {
        this.nav.setCanSwim(b);
    }

    public boolean canFloat() {
        return this.nav.canSwim;
    }

    public void setCanOpenDoors(boolean b) {
        this.nav.setEnterDoors(b);
    }

    public void setCanPassDoors(boolean b) {
        this.nav.setEnterDoors(b);
    }

    public void recomputePath() {
    }

    public void tick() {
    }

    public boolean isStableDestination(BlockPos pos) {
        return World.doesBlockHaveSolidTopSurface(this.mob.worldObj, pos.getX(), pos.getY() - 1, pos.getZ());
    }
}
