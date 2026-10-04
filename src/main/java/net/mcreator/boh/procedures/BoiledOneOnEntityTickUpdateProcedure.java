package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.commands.arguments.Anchor;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.util.Mth;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class BoiledOneOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            Entity _entfound = M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 90.0, 90.0, 90.0), e -> true)
                .stream()
                .sorted((new Object() {
                    Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                        return Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _x, _y, _z));
                    }
                }).compareDistOf(x, y, z))
                .findFirst()
                .orElse(null);
            if (_entfound instanceof EntityLivingBase _livEnt1
                && M.hasEffect(_livEnt1, BohModMobEffects.COGNITO_HAZART.get())
                && M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 50.0, 50.0, 50.0), e -> true))) {
                Vec3 _center = new Vec3(x, y, z);

                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(30.0), e -> true)
                    .stream()
                    .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                    .toList()) {
                    if (entityiterator instanceof EntityLivingBase _livEnt3
                        && M.hasEffect(_livEnt3, BohModMobEffects.COGNITO_HAZART.get())
                        && entityiterator instanceof EntityPlayer
                        && !M.isClientSide(M.level(entityiterator))
                        && M.getServer(entityiterator) != null) {
                        M.performPrefixedCommand(
                            M.getCommands(M.getServer(entityiterator)),
                            new CommandSourceStack(
                                CommandSource.NULL,
                                M.position(entityiterator),
                                M.getRotationVector(entityiterator),
                                M.level(entityiterator) instanceof WorldServer ? (WorldServer)M.level(entityiterator) : null,
                                4,
                                M.getString(M.getName(entityiterator)),
                                M.getDisplayName(entityiterator),
                                M.getServer(M.level(entityiterator)),
                                entityiterator
                            ),
                            "/spreadplayers ~ ~ 30 30 under 1 true @e[type=boh:boiled_one,limit=1,sort=nearest]"
                        );
                    }
                }
            }

            if (!M.isEmptyBlock(world, BlockPos.containing(x, Mth.nextDouble(RandomSource.create(), 2.0, 5.0) + y, z))
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
                    "/spreadplayers ~ ~ 30 30 under 1 true @e[type=boh:boiled_one,limit=1,sort=nearest]"
                );
            }

            if (!M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 30.0, 30.0, 30.0), e -> true))) {
                M.lookAt(
                    entity,
                    Anchor.EYES,
                    new Vec3(
                        M.getX(
                            M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 30.0, 30.0, 30.0), e -> true)
                                .stream()
                                .sorted((new Object() {
                                    Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                                        return Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _x, _y, _z));
                                    }
                                }).compareDistOf(x, y, z))
                                .findFirst()
                                .orElse(null)
                        ),
                        M.getY(
                                M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 30.0, 30.0, 30.0), e -> true)
                                    .stream()
                                    .sorted((new Object() {
                                        Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                                            return Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _x, _y, _z));
                                        }
                                    }).compareDistOf(x, y, z))
                                    .findFirst()
                                    .orElse(null)
                            )
                            + 1.6,
                        M.getZ(
                            M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 30.0, 30.0, 30.0), e -> true)
                                .stream()
                                .sorted((new Object() {
                                    Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                                        return Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _x, _y, _z));
                                    }
                                }).compareDistOf(x, y, z))
                                .findFirst()
                                .orElse(null)
                        )
                    )
                );
            }

            if (!M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 15.0, 15.0, 15.0), e -> true))) {
                Vec3 _center = new Vec3(x, y, z);

                for (Entity entityiteratorx : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(7.5), e -> true)
                    .stream()
                    .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                    .toList()) {
                    if (entityiteratorx instanceof EntityLivingBase _livEnt19
                        && M.hasEffect(_livEnt19, BohModMobEffects.COGNITO_HAZART.get())
                        && entityiteratorx instanceof EntityPlayer
                        && !M.isClientSide(M.level(entityiteratorx))
                        && M.getServer(entityiteratorx) != null) {
                        M.performPrefixedCommand(
                            M.getCommands(M.getServer(entityiteratorx)),
                            new CommandSourceStack(
                                CommandSource.NULL,
                                M.position(entityiteratorx),
                                M.getRotationVector(entityiteratorx),
                                M.level(entityiteratorx) instanceof WorldServer ? (WorldServer)M.level(entityiteratorx) : null,
                                4,
                                M.getString(M.getName(entityiteratorx)),
                                M.getDisplayName(entityiteratorx),
                                M.getServer(M.level(entityiteratorx)),
                                entityiteratorx
                            ),
                            "/spreadplayers ~ ~ 30 30 under 1 true @e[type=boh:boiled_one,limit=1,sort=nearest]"
                        );
                    }
                }
            }

            if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.INVISIBILITY, 9999, 2, false, false));
            }
        }
    }
}
