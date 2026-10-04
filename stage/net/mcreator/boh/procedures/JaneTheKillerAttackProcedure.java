package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.entity.JaneTheKillerEntity;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;

public class JaneTheKillerAttackProcedure {

    @SubscribeEvent
    public void onEntityAttacked(LivingAttackEvent event) {
        if (event != null && M.getEntity(event) != null) {
            execute(event, M.getEntity(event), M.getEntity(M.getSource(event)));
        }
    }

    public static void execute(Entity entity, Entity sourceentity) {
        execute(null, entity, sourceentity);
    }

    private static void execute(@Nullable Event event, Entity entity, Entity sourceentity) {
        if (entity != null && sourceentity != null) {
            if (sourceentity instanceof JaneTheKillerEntity) {
                Entity _ent = sourceentity;
                if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/particle minecraft:sweep_attack ~ ~1.2 ~");
                }
                Entity _ent_r41 = sourceentity;
                if (!M.isClientSide(M.level(_ent_r41)) && M.getServer(_ent_r41) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent_r41)), new CommandSourceStack(CommandSource.NULL, M.position(_ent_r41), M.getRotationVector(_ent_r41), M.level(_ent_r41) instanceof WorldServer ? (WorldServer) M.level(_ent_r41) : null, 4, M.getString(M.getName(_ent_r41)), M.getDisplayName(_ent_r41), M.getServer(M.level(_ent_r41)), _ent_r41), "/playsound minecraft:entity.player.attack.strong hostile @a");
                }
                if (Math.random() < 0.6) {
                    _ent_r41 = entity;
                    if (!M.isClientSide(M.level(_ent_r41)) && M.getServer(_ent_r41) != null) {
                        M.performPrefixedCommand(M.getCommands(M.getServer(_ent_r41)), new CommandSourceStack(CommandSource.NULL, M.position(_ent_r41), M.getRotationVector(_ent_r41), M.level(_ent_r41) instanceof WorldServer ? (WorldServer) M.level(_ent_r41) : null, 4, M.getString(M.getName(_ent_r41)), M.getDisplayName(_ent_r41), M.getServer(M.level(_ent_r41)), _ent_r41), "effect give @s kurolib:bleeding 1 10");
                    }
                }
            }
        }
    }
}
