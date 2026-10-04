package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldServer;

public class NotinInventoryHeartProcedure {
    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent event) {
        if (event.phase == Phase.END) {
            execute(event, M.player(event));
        }
    }

    public static void execute(Entity entity) {
        execute(null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        if (entity != null
            && !(entity instanceof EntityPlayer _playerHasItem && M.contains(M.getInventory(_playerHasItem), M.new_ItemStack(BohModItems.WHITEFACEHEART.get())))
            && !M.isClientSide(M.level(entity))
            && M.getServer(entity) != null) {
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
                "/tag @p remove whiteface"
            );
        }
    }
}
