package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.mcreator.boh.compat.M;

public class MassacreAxeLivingEntityIsHitWithToolProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            if (!(entity instanceof EntityLivingBase _livEnt0 && M.isBlocking(_livEnt0)) && Math.random() < 0.45) {
                Entity _ent = entity;
                if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "effect give @s kurolib:bleeding 0 60");
                }
            }
        }
    }
}
