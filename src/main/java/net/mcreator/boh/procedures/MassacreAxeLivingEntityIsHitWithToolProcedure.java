package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.WorldServer;

public class MassacreAxeLivingEntityIsHitWithToolProcedure {
    public static void execute(Entity entity) {
        if (entity != null
            && !(entity instanceof EntityLivingBase _livEnt0 && M.isBlocking(_livEnt0))
            && Math.random() < 0.45
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
                "effect give @s kurolib:bleeding 0 60"
            );
        }
    }
}
