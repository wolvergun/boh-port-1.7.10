package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.Specimen9BossEntity;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class TakenHandsOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (M.isEmpty(M.getEntitiesOfClass(world, Specimen9BossEntity.class, AABB.ofSize(new Vec3(x, y, z), 40.0, 40.0, 40.0), e -> true))
                && !M.isClientSide(M.level(entity))) {
                M.discard(entity);
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
                    "/particle minecraft:squid_ink ~ ~ ~ 0.1 0.1 0.1 0 10"
                );
            }
        }
    }
}
