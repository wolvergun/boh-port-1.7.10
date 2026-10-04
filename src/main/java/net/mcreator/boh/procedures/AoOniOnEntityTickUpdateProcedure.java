package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec2;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.FuwattiEntity;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.network.BohModVariables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class AoOniOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if ((entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer) {
                Vec3 _center = new Vec3(x, y, z);

                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(5.0), e -> true)
                    .stream()
                    .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                    .toList()) {
                    if (entityiterator instanceof EntityPlayer) {
                        boolean _setval = true;
                        M.getCapability(entityiterator, BohModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                            capability.chase_aoni = _setval;
                            capability.syncPlayerVariables(entityiterator);
                        });
                    }
                }
            }

            if (!((entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer) && world instanceof WorldServer _level) {
                M.performPrefixedCommand(
                    M.getCommands(M.getServer(_level)),
                    M.withSuppressedOutput(
                        new CommandSourceStack(
                            CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), M.getServer(_level), null
                        )
                    ),
                    "/stopsound @a music boh:ao_oni_chase"
                );
            }

            if (M.isEmpty(M.getEntitiesOfClass(world, FuwattiEntity.class, AABB.ofSize(new Vec3(x, y, z), 4.0, 4.0, 4.0), e -> true))
                && entity instanceof EntityLiving _mob
                && M.isAggressive(_mob)
                && Math.random() < 0.2
                && Math.random() < 0.1
                && world instanceof WorldServer _level) {
                Entity entityToSpawn = M.spawn(BohModEntities.FUWATTI.get(), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                if (entityToSpawn != null) {
                }
            }
        }
    }
}
