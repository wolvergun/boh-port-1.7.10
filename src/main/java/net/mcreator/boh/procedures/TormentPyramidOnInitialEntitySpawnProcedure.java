package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.world.damagesource.DamageTypes;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.TormentPyramidEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class TormentPyramidOnInitialEntitySpawnProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (entity instanceof TormentPyramidEntity) {
                ((TormentPyramidEntity)entity).setAnimation("spawn");
            }

            BohMod.queueServerWork(
                2,
                () -> {
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
                            "spreadplayers ~ ~ 1 1 under 1 true @e[type=boh:torment_pyramid,limit=1,distance=0..3]"
                        );
                    }
                }
            );
            BohMod.queueServerWork(32, () -> M.putBoolean(M.getPersistentData(entity), "slow", true));
            BohMod.queueServerWork(
                52,
                () -> {
                    Vec3 _center = new Vec3(x, y, z);

                    for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(1.0), e -> true)
                        .stream()
                        .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                        .toList()) {
                        if (entityiterator instanceof EntityPlayer) {
                            M.hurt(
                                entityiterator,
                                M.new_DamageSource(M.getHolderOrThrow(M.registryOrThrow(M.registryAccess(world), Registries.DAMAGE_TYPE), DamageTypes.GENERIC)),
                                4.0F
                            );
                        }
                    }
                }
            );
            BohMod.queueServerWork(72, () -> {
                if (!M.isClientSide(M.level(entity))) {
                    M.discard(entity);
                }
            });
        }
    }
}
