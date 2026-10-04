package net.mcreator.boh.compat.forge.event.entity.player;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class LeftClickEmpty extends PlayerEvent {
    public LeftClickEmpty() {
        this(null);
    }

    public LeftClickEmpty(EntityPlayer player) {
        super(player);
    }

    public World getLevel() {
        return this.entityPlayer.worldObj;
    }

    public BlockPos getPos() {
        return BlockPos.containing(this.entityPlayer.posX, this.entityPlayer.boundingBox.minY, this.entityPlayer.posZ);
    }

    public InteractionHand getHand() {
        return InteractionHand.MAIN_HAND;
    }
}
