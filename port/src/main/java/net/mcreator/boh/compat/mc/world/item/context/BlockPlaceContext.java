package net.mcreator.boh.compat.mc.world.item.context;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/** 1.20 BlockPlaceContext: clicked position is the placement position. */
public class BlockPlaceContext extends UseOnContext {

    public BlockPlaceContext(World level, EntityPlayer player, InteractionHand hand, ItemStack stack, BlockPos placePos, Direction face,
        Vec3 location) {
        super(level, player, hand, stack, placePos, face, location);
    }

    public Direction getNearestLookingDirection() {
        if (player == null) return Direction.NORTH;
        if (player.rotationPitch < -60) return Direction.UP;
        if (player.rotationPitch > 60) return Direction.DOWN;
        return getHorizontalDirection();
    }

    public Direction[] getNearestLookingDirections() {
        return new Direction[] { getNearestLookingDirection() };
    }

    public boolean canPlace() {
        return level.isAirBlock(pos.getX(), pos.getY(), pos.getZ()) || level.getBlock(pos.getX(), pos.getY(), pos.getZ())
            .isReplaceable(level, pos.getX(), pos.getY(), pos.getZ());
    }

    public boolean replacingClickedOnBlock() {
        return false;
    }
}
