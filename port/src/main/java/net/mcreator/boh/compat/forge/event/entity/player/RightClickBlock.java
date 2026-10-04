package net.mcreator.boh.compat.forge.event.entity.player;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import cpw.mods.fml.common.eventhandler.Cancelable;

/** 1.20 PlayerInteractEvent.RightClickBlock (bridged from 1.7.10 PlayerInteractEvent RIGHT_CLICK_BLOCK). */
@Cancelable
public class RightClickBlock extends PlayerEvent {

    public RightClickBlock() {
        this(null, null, null);
    }

    private final BlockPos pos;
    private final Direction face;

    public RightClickBlock(EntityPlayer player, BlockPos pos, Direction face) {
        super(player);
        this.pos = pos;
        this.face = face;
    }

    public World getLevel() {
        return entityPlayer.worldObj;
    }

    public BlockPos getPos() {
        return pos;
    }

    public Direction getFace() {
        return face;
    }

    public InteractionHand getHand() {
        return InteractionHand.MAIN_HAND;
    }

    public ItemStack getItemStack() {
        return net.mcreator.boh.compat.M.stack(entityPlayer.getHeldItem());
    }
}
