package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.SaucerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class MartianDroneOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null
            && M.getBoolean(M.getPersistentData(entity), "spawn_thru_ship")
            && M.isEmpty(M.getEntitiesOfClass(world, SaucerEntity.class, AABB.ofSize(new Vec3(x, y, z), 100.0, 100.0, 100.0), e -> true))) {
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
                    "/particle minecraft:poof ~ ~.5 ~ 0.2 0.5 0.2 0 10"
                );
            }

            if (!M.isClientSide(M.level(entity))) {
                M.discard(entity);
            }
        }
    }
}
