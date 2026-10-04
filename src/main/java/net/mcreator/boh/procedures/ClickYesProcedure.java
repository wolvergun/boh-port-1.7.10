package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.items.ItemHandlerHelper;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.init.BohModItems;
import net.mcreator.boh.network.BohModVariables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ClickYesProcedure {
    public static void execute(World world, Entity entity) {
        if (entity != null) {
            if (!M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
                M.performPrefixedCommand(
                    M.getCommands(M.getServer(entity)),
                    new CommandSourceStack(
                        CommandSource.NULL,
                        M.position(entity),
                        M.getRotationVector(entity),
                        M.level(entity) instanceof WorldServer ? (WorldServer)M.level(entity) : null,
                        4,
                        M.getString(M.getName(entity)),
                        M.getDisplayName(entity),
                        M.getServer(M.level(entity)),
                        entity
                    ),
                    "/particle minecraft:block redstone_block ~ ~1 ~ .1 .1 .1 2 30"
                );
            }

            if (!M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
                M.performPrefixedCommand(
                    M.getCommands(M.getServer(entity)),
                    new CommandSourceStack(
                        CommandSource.NULL,
                        M.position(entity),
                        M.getRotationVector(entity),
                        M.level(entity) instanceof WorldServer ? (WorldServer)M.level(entity) : null,
                        4,
                        M.getString(M.getName(entity)),
                        M.getDisplayName(entity),
                        M.getServer(M.level(entity)),
                        entity
                    ),
                    "/playsound boh:whiteface_scream master @a"
                );
            }

            if (entity instanceof EntityPlayer _player) {
                ItemStack _stktoremove = M.new_ItemStack(BohModItems.WHITEFACEHEART.get());
                M.clearOrCountMatchingItems(
                    M.getInventory(_player), p -> M.getItem(_stktoremove) == M.getItem(p), 999, M.getCraftSlots(M.inventoryMenu(_player))
                );
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
