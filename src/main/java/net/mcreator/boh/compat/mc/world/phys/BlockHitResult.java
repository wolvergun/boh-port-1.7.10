package net.mcreator.boh.compat.mc.world.phys;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

public class BlockHitResult extends HitResult {
    private final BlockPos pos;
    private final Direction direction;
    private final boolean miss;
    private final boolean inside;

    public BlockHitResult(Vec3 location, Direction direction, BlockPos pos, boolean inside) {
        this(false, location, direction, pos, inside);
    }

    private BlockHitResult(boolean miss, Vec3 location, Direction direction, BlockPos pos, boolean inside) {
        super(location);
        this.miss = miss;
        this.direction = direction;
        this.pos = pos;
        this.inside = inside;
    }

    public static BlockHitResult miss(Vec3 location, Direction direction, BlockPos pos) {
        return new BlockHitResult(true, location, direction, pos, false);
    }

    public static BlockHitResult of(MovingObjectPosition mop, Vec3 fallback) {
        return mop != null && mop.typeOfHit == MovingObjectType.BLOCK
            ? new BlockHitResult(
                Vec3.of(mop.hitVec),
                Direction.from3DDataValue(mop.sideHit),
                new BlockPos(mop.blockX, mop.blockY, mop.blockZ),
                false
            )
            : miss(fallback, Direction.UP, BlockPos.containing(fallback));
    }

    public BlockPos getBlockPos() {
        return this.pos;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public boolean isInside() {
        return this.inside;
    }

    @Override
    public HitResultType getType() {
        return this.miss ? HitResultType.MISS : HitResultType.BLOCK;
    }
}
