package net.mcreator.boh.procedures;

import net.mcreator.boh.network.BohModVariables;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.mcreator.boh.compat.M;

public class SirenheadEntityDiesProcedure {

    public static void execute(World world, Entity entity) {
        if (entity != null) {
            BohModVariables.MapVariables.get(world).spawn_siren = 0.0;
            BohModVariables.MapVariables.get(world).syncData(world);
            Entity _ent = entity;
            if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "stopsound @a[distance=0..20] * boh:sirenhead_siren");
            }
        }
    }
}
