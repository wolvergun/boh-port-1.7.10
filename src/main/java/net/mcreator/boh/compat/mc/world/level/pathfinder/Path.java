package net.mcreator.boh.compat.mc.world.level.pathfinder;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;

public class Path {
    private final PathEntity handle;

    public Path(PathEntity handle) {
        this.handle = handle;
    }

    public static Path of(PathEntity p) {
        return p == null ? null : new Path(p);
    }

    public PathEntity toVanilla() {
        return this.handle;
    }

    public boolean isDone() {
        return this.handle.isFinished();
    }

    public boolean canReach() {
        return true;
    }

    public int getNodeCount() {
        return this.handle.getCurrentPathLength();
    }

    public BlockPos getTarget() {
        PathPoint p = this.handle.getFinalPathPoint();
        return p == null ? BlockPos.ZERO : new BlockPos(p.xCoord, p.yCoord, p.zCoord);
    }

    public BlockPos getEndNodePos() {
        return this.getTarget();
    }
}
