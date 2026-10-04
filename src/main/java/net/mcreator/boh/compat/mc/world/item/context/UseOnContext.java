package net.mcreator.boh.compat.mc.world.item.context;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.phys.BlockHitResult;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class UseOnContext {
    protected final World level;
    protected final EntityPlayer player;
    protected final InteractionHand hand;
    protected final ItemStack stack;
    protected final BlockPos pos;
    protected final Direction face;
    protected final Vec3 location;

    public UseOnContext(World level, EntityPlayer player, InteractionHand hand, ItemStack stack, BlockPos pos, Direction face, Vec3 location) {
        this.level = level;
        this.player = player;
        this.hand = hand;
        this.stack = stack;
        this.pos = pos;
        this.face = face;
        this.location = location;
    }

    public World getLevel() {
        return this.level;
    }

    public EntityPlayer getPlayer() {
        return this.player;
    }

    public InteractionHand getHand() {
        return this.hand;
    }

    public ItemStack getItemInHand() {
        return M.stack(this.stack);
    }

    public BlockPos getClickedPos() {
        return this.pos;
    }

    public Direction getClickedFace() {
        return this.face;
    }

    public Vec3 getClickLocation() {
        return this.location;
    }

    public BlockHitResult getHitResult() {
        return new BlockHitResult(this.location, this.face, this.pos, false);
    }

    public Direction getHorizontalDirection() {
        return this.player == null ? Direction.NORTH : Direction.fromYRot(this.player.rotationYaw);
    }

    public float getRotation() {
        return this.player == null ? 0.0F : this.player.rotationYaw;
    }

    public boolean isSecondaryUseActive() {
        return this.player != null && this.player.isSneaking();
    }

    public boolean isInside() {
        return false;
    }
}
