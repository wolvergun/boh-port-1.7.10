package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModItems;
import net.mcreator.boh.network.BohModVariables;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.mcreator.boh.compat.forge.items.ItemHandlerHelper;
import net.mcreator.boh.compat.M;

public class ClickYesProcedure {

    public static void execute(World world, Entity entity) {
        if (entity != null) {
            Entity _ent = entity;
            if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/particle minecraft:block redstone_block ~ ~1 ~ .1 .1 .1 2 30");
            }
            Entity _ent_r14 = entity;
            if (!M.isClientSide(M.level(_ent_r14)) && M.getServer(_ent_r14) != null) {
                M.performPrefixedCommand(M.getCommands(M.getServer(_ent_r14)), new CommandSourceStack(CommandSource.NULL, M.position(_ent_r14), M.getRotationVector(_ent_r14), M.level(_ent_r14) instanceof WorldServer ? (WorldServer) M.level(_ent_r14) : null, 4, M.getString(M.getName(_ent_r14)), M.getDisplayName(_ent_r14), M.getServer(M.level(_ent_r14)), _ent_r14), "/playsound boh:whiteface_scream master @a");
            }
            if (entity instanceof EntityPlayer _player) {
                ItemStack _stktoremove = M.new_ItemStack(BohModItems.WHITEFACEHEART.get());
                M.clearOrCountMatchingItems(M.getInventory(_player), p -> M.getItem(_stktoremove) == M.getItem(p), 999, M.getCraftSlots(M.inventoryMenu(_player)));
            }
            if (entity instanceof EntityPlayer _player) {
                ItemStack _setstack = M.copy(M.new_ItemStack(BohModItems.WF_PISTOL.get()));
                M.setCount(_setstack, 1);
                ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
            }
            if (entity instanceof EntityPlayer _player) {
                M.closeContainer(_player);
            }
            BohModVariables.MapVariables.get(world).Kill_WF = 1.0;
            BohModVariables.MapVariables.get(world).syncData(world);
        }
    }
}
