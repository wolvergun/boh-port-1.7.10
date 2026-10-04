package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.init.BohModItems;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.Vec2;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;

public class DestroyHeartProcedure {

    @SubscribeEvent
    public void onItemDestroyed(PlayerDestroyItemEvent event) {
        execute(event, M.level(M.getEntity(event)), M.getX(M.getEntity(event)), M.getY(M.getEntity(event)), M.getZ(M.getEntity(event)), M.getEntity(event));
    }

    public static void execute(World world, double x, double y, double z, Entity entity) {
        execute(null, world, x, y, z, entity);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (M.getItem((entity instanceof EntityItem _itemEnt ? M.getItem(_itemEnt) : M.EMPTY)) == BohModItems.WHITEFACEHEART.get()) {
                Entity _ent = entity;
                if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/particle minecraft:block redstone_block ~ ~1 ~ .1 .1 .1 2 30");
                }
                Entity _ent_r18 = entity;
                if (!M.isClientSide(M.level(_ent_r18)) && M.getServer(_ent_r18) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent_r18)), new CommandSourceStack(CommandSource.NULL, M.position(_ent_r18), M.getRotationVector(_ent_r18), M.level(_ent_r18) instanceof WorldServer ? (WorldServer) M.level(_ent_r18) : null, 4, M.getString(M.getName(_ent_r18)), M.getDisplayName(_ent_r18), M.getServer(M.level(_ent_r18)), _ent_r18), "/playsound minecraft:entity.ghast.warn master @a");
                }
                if (world instanceof WorldServer _level) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_level)), M.withSuppressedOutput(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), M.getServer(_level), null)), "kill @e[type=boh:whiteface]");
                }
            }
        }
    }
}
