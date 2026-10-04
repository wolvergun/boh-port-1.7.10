package net.mcreator.boh.compat.mc.world.entity.ai.navigation;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.level.pathfinder.Path;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.world.World;

/** 1.20 PathNavigation facade over a 1.7.10 {@link PathNavigate}. */
public class PathNavigation {

    protected final EntityLiving mob;
    protected final PathNavigate nav;
    protected double speedModifier = 1.0;

    public PathNavigation(EntityLiving mob, World world) {
        this(mob, new BohPathNavigate(mob, world));
    }

    public PathNavigation(EntityLiving mob, PathNavigate nav) {
        this.mob = mob;
        this.nav = nav;
    }

    public static PathNavigation of(EntityLiving mob) {
        return new PathNavigation(mob, mob.getNavigator());
    }

    public PathNavigate vanilla() {
        return nav;
    }

    public boolean moveTo(double x, double y, double z, double speed) {
        speedModifier = speed;
        return nav.tryMoveToXYZ(x, y, z, speed);
    }

    public boolean moveTo(Entity e, double speed) {
        speedModifier = speed;
        if (e instanceof EntityLivingBase) return nav.tryMoveToEntityLiving(e, speed);
        return nav.tryMoveToXYZ(e.posX, e.posY, e.posZ, speed);
    }

    public boolean moveTo(Path path, double speed) {
        speedModifier = speed;
        return path != null && nav.setPath(path.toVanilla(), speed);
    }

    public Path createPath(double x, double y, double z, int accuracy) {
        return Path.of(nav.getPathToXYZ(x, y, z));
    }

    public Path createPath(BlockPos pos, int accuracy) {
        return createPath(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, accuracy);
    }

    public Path createPath(Entity e, int accuracy) {
        if (e instanceof EntityLivingBase) return Path.of(nav.getPathToEntityLiving(e));
        return createPath(e.posX, e.posY, e.posZ, accuracy);
    }

    public Path getPath() {
        return Path.of(nav.getPath());
    }

    public void stop() {
        nav.clearPathEntity();
    }

    public boolean isDone() {
        return nav.noPath();
    }

    public boolean isInProgress() {
        return !nav.noPath();
    }

    public void setSpeedModifier(double speed) {
        speedModifier = speed;
        nav.setSpeed(speed);
    }

    public void setCanFloat(boolean b) {
        nav.setCanSwim(b);
    }

    public boolean canFloat() {
        return nav.canSwim;
    }

    public void setCanOpenDoors(boolean b) {
        nav.setEnterDoors(b);
    }

    public void setCanPassDoors(boolean b) {
        nav.setEnterDoors(b);
    }

    public void recomputePath() {}

    public void tick() {}

    public boolean isStableDestination(BlockPos pos) {
        return World.doesBlockHaveSolidTopSurface(mob.worldObj, pos.getX(), pos.getY() - 1, pos.getZ());
    }
}
