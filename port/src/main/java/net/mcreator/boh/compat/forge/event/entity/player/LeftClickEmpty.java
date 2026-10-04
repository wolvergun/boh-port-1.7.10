package net.mcreator.boh.compat.forge.event.entity.player;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;

/** 1.20 PlayerInteractEvent.LeftClickEmpty: client-side swing at nothing. */
public class LeftClickEmpty extends PlayerEvent {

    public LeftClickEmpty() {
        this(null);
    }

    public LeftClickEmpty(EntityPlayer player) {
        super(player);
    }

    public World getLevel() {
        return entityPlayer.worldObj;
    }

    public BlockPos getPos() {
        return BlockPos.containing(entityPlayer.posX, entityPlayer.boundingBox.minY, entityPlayer.posZ);
    }

    public InteractionHand getHand() {
        return InteractionHand.MAIN_HAND;
    }
}
