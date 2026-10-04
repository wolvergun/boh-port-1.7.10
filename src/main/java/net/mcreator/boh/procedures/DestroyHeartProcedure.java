package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.world.phys.Vec2;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;

public class DestroyHeartProcedure {
    @SubscribeEvent
    public void onItemDestroyed(PlayerDestroyItemEvent event) {
        execute(event, M.level(M.getEntity(event)), M.getX(M.getEntity(event)), M.getY(M.getEntity(event)), M.getZ(M.getEntity(event)), M.getEntity(event));
    }

    public static void execute(World world, double x, double y, double z, Entity entity) {
        execute(null, world, x, y, z, entity);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z, Entity entity) {
        if (entity != null && M.getItem(entity instanceof EntityItem _itemEnt ? M.getItem(_itemEnt) : M.EMPTY) == BohModItems.WHITEFACEHEART.get()) {
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
                    "/playsound minecraft:entity.ghast.warn master @a"
                );
            }

            if (world instanceof WorldServer _level) {
                M.performPrefixedCommand(
                    M.getCommands(M.getServer(_level)),
                    M.withSuppressedOutput(
                        new CommandSourceStack(
                            CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), M.getServer(_level), null
                        )
                    ),
                    "kill @e[type=boh:whiteface]"
                );
            }
        }
    }
}
