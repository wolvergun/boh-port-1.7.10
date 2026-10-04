package net.mcreator.boh.procedures;

import net.mcreator.boh.world.inventory.KillscreenWFMenu;
import io.netty.buffer.Unpooled;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.FriendlyByteBuf;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.entity.player.EntityPlayerMP;
import net.mcreator.boh.compat.mc.world.MenuProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.mcreator.boh.compat.mc.world.inventory.AbstractContainerMenu;
import net.minecraft.world.World;
import net.mcreator.boh.compat.forge.network.NetworkHooks;
import net.mcreator.boh.compat.M;

public class WhitefaceheartRightclickedOnBlockProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (entity instanceof EntityPlayerMP _ent) {
                final BlockPos _bpos = BlockPos.containing(x, y, z);
                NetworkHooks.openScreen(_ent, new MenuProvider() {

                    public Component getDisplayName() {
                        return Component.literal("KillscreenWF");
                    }

                    public AbstractContainerMenu createMenu(int id, InventoryPlayer inventory, EntityPlayer player) {
                        return new KillscreenWFMenu(id, inventory, M.writeBlockPos(new FriendlyByteBuf(Unpooled.buffer()), _bpos));
                    }
                }, _bpos);
            }
        }
    }
}
