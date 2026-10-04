package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;

public class EntityFixProcedure {

    @SubscribeEvent
    public void onEntitySpawned(EntityJoinWorldEvent event) {
        execute(event, M.getEntity(event));
    }

    public static void execute(Entity entity) {
        execute(null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        if (entity != null) {
            if (entity instanceof EntityPlayer) {
                Entity _ent = entity;
                if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/scoreboard objectives setdisplay sidebar.team.yellow anim");
                }
                Entity _ent_r21 = entity;
                if (!M.isClientSide(M.level(_ent_r21)) && M.getServer(_ent_r21) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent_r21)), new CommandSourceStack(CommandSource.NULL, M.position(_ent_r21), M.getRotationVector(_ent_r21), M.level(_ent_r21) instanceof WorldServer ? (WorldServer) M.level(_ent_r21) : null, 4, M.getString(M.getName(_ent_r21)), M.getDisplayName(_ent_r21), M.getServer(M.level(_ent_r21)), _ent_r21), "/team add cognito");
                }
            }
        }
    }
}
