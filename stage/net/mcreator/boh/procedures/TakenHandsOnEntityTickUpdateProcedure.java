package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.Specimen9BossEntity;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.M;

public class TakenHandsOnEntityTickUpdateProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (M.isEmpty(M.getEntitiesOfClass(world, Specimen9BossEntity.class, AABB.ofSize(new Vec3(x, y, z), 40.0, 40.0, 40.0), e -> true)) && !M.isClientSide(M.level(entity))) {
                M.discard(entity);
            }
            Entity _ent = entity;
            if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/particle minecraft:squid_ink ~ ~ ~ 0.1 0.1 0.1 0 10");
            }
        }
    }
}
