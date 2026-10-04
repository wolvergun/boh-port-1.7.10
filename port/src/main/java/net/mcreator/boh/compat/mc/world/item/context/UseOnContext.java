package net.mcreator.boh.compat.mc.world.item.context;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.phys.BlockHitResult;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/** 1.20 UseOnContext. */
public class UseOnContext {

    protected final World level;
    protected final EntityPlayer player;
    protected final InteractionHand hand;
    protected final ItemStack stack;
    protected final BlockPos pos;
    protected final Direction face;
    protected final Vec3 location;

    public UseOnContext(World level, EntityPlayer player, InteractionHand hand, ItemStack stack, BlockPos pos, Direction face,
        Vec3 location) {
        this.level = level;
        this.player = player;
        this.hand = hand;
        this.stack = stack;
        this.pos = pos;
        this.face = face;
        this.location = location;
    }

    public World getLevel() {
        return level;
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public InteractionHand getHand() {
        return hand;
    }

    public ItemStack getItemInHand() {
        return net.mcreator.boh.compat.M.stack(stack);
    }

    public BlockPos getClickedPos() {
        return pos;
    }

    public Direction getClickedFace() {
        return face;
    }

    public Vec3 getClickLocation() {
        return location;
    }

    public BlockHitResult getHitResult() {
        return new BlockHitResult(location, face, pos, false);
    }

    public Direction getHorizontalDirection() {
        return player == null ? Direction.NORTH : Direction.fromYRot(player.rotationYaw);
    }

    public float getRotation() {
        return player == null ? 0 : player.rotationYaw;
    }

    public boolean isSecondaryUseActive() {
        return player != null && player.isSneaking();
    }

    public boolean isInside() {
        return false;
    }
}
