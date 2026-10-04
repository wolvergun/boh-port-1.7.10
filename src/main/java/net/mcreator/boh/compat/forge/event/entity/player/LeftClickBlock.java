package net.mcreator.boh.compat.forge.event.entity.player;

import cpw.mods.fml.common.eventhandler.Cancelable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;

@Cancelable
public class LeftClickBlock extends PlayerEvent {
    private final BlockPos pos;
    private final Direction face;

    public LeftClickBlock() {
        this(null, null, null);
    }

    public LeftClickBlock(EntityPlayer player, BlockPos pos, Direction face) {
        super(player);
        this.pos = pos;
        this.face = face;
    }

    public World getLevel() {
        return this.entityPlayer.worldObj;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public Direction getFace() {
        return this.face;
    }

    public InteractionHand getHand() {
        return InteractionHand.MAIN_HAND;
    }

    public ItemStack getItemStack() {
        return M.stack(this.entityPlayer.getHeldItem());
    }
}
