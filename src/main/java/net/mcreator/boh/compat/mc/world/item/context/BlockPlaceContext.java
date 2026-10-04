package net.mcreator.boh.compat.mc.world.item.context;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockPlaceContext extends UseOnContext {
    public BlockPlaceContext(World level, EntityPlayer player, InteractionHand hand, ItemStack stack, BlockPos placePos, Direction face, Vec3 location) {
        super(level, player, hand, stack, placePos, face, location);
    }

    public Direction getNearestLookingDirection() {
        if (this.player == null) {
            return Direction.NORTH;
        } else if (this.player.rotationPitch < -60.0F) {
            return Direction.UP;
        } else {
            return this.player.rotationPitch > 60.0F ? Direction.DOWN : this.getHorizontalDirection();
        }
    }

    public Direction[] getNearestLookingDirections() {
        return new Direction[]{this.getNearestLookingDirection()};
    }

    public boolean canPlace() {
        return this.level.isAirBlock(this.pos.getX(), this.pos.getY(), this.pos.getZ())
            || this.level
                .getBlock(this.pos.getX(), this.pos.getY(), this.pos.getZ())
                .isReplaceable(this.level, this.pos.getX(), this.pos.getY(), this.pos.getZ());
    }

    public boolean replacingClickedOnBlock() {
        return false;
    }
}
