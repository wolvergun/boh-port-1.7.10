package net.mcreator.boh.compat.mc.world.level.pathfinder;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;

/** 1.20 Path wrapping a 1.7.10 {@link PathEntity}. */
public class Path {

    private final PathEntity handle;

    public Path(PathEntity handle) {
        this.handle = handle;
    }

    public static Path of(PathEntity p) {
        return p == null ? null : new Path(p);
    }

    public PathEntity toVanilla() {
        return handle;
    }

    public boolean isDone() {
        return handle.isFinished();
    }

    public boolean canReach() {
        return true;
    }

    public int getNodeCount() {
        return handle.getCurrentPathLength();
    }

    public BlockPos getTarget() {
        PathPoint p = handle.getFinalPathPoint();
        return p == null ? BlockPos.ZERO : new BlockPos(p.xCoord, p.yCoord, p.zCoord);
    }

    public BlockPos getEndNodePos() {
        return getTarget();
    }
}
