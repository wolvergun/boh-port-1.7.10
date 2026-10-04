package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.TamedRatEntity;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class RatRightClickedOnEntityProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity, Entity sourceentity) {
        if (entity != null
            && sourceentity != null
            && M.getItem(sourceentity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY) == BohModItems.PRETZEL.get()) {
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
                    "/particle item boh:pretzel ~ ~.2 ~ .1 .1 .1 .05 5"
                );
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
                    "/playsound minecraft:entity.generic.eat ambient @a ~ ~ ~ 0.5 1.5"
                );
            }

            if (!(sourceentity instanceof EntityPlayer _plr && M.instabuild(M.getAbilities(_plr))) && sourceentity instanceof EntityPlayer _player) {
                ItemStack _stktoremove;
                ItemStack var25 = _stktoremove = sourceentity instanceof EntityLivingBase _livEntx ? M.getMainHandItem(_livEntx) : M.EMPTY;
                M.clearOrCountMatchingItems(M.getInventory(_player), p -> M.getItem(_stktoremove) == M.getItem(p), 1, M.getCraftSlots(M.inventoryMenu(_player)));
            }

            if (Math.random() < 0.5) {
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
                        "/particle minecraft:heart ~ ~.2 ~ .1 .1 .1 .05 5"
                    );
                }

                if (world instanceof WorldServer _level) {
                    Entity entityToSpawn = M.spawn(BohModEntities.TAMED_RAT.get(), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                    if (entityToSpawn != null) {
                        M.setYRot(entityToSpawn, M.getYRot(entity));
                        M.setYBodyRot(entityToSpawn, M.getYRot(entity));
                        M.setYHeadRot(entityToSpawn, M.getYRot(entity));
                        M.setXRot(entityToSpawn, M.getXRot(entity));
                        M.setDeltaMovement(entityToSpawn, M.getDeltaMovement(entity).x(), M.getDeltaMovement(entity).y(), M.getDeltaMovement(entity).z());
                    }
                }

                Vec3 _center = new Vec3(x, y, z);

                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(2.0), e -> true)
                    .stream()
                    .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                    .toList()) {
                    if (entityiterator instanceof TamedRatEntity) {
                        if (entityiterator instanceof EntityTameable _toTame && sourceentity instanceof EntityPlayer _owner) {
                            M.tame(_toTame, _owner);
                        }

                        M.putDouble(M.getPersistentData(entityiterator), "skin", M.getDouble(M.getPersistentData(entity), "skin"));
                        if (entityiterator instanceof EntityLivingBase _entity) {
                            M.setHealth(_entity, entity instanceof EntityLivingBase _livEntx ? M.getHealth(_livEntx) : -1.0F);
                        }
                    }
                }

                BohMod.queueServerWork(4, () -> {
                    if (!M.isClientSide(M.level(entity))) {
                        M.discard(entity);
                    }
                });
            }
        }
    }
}
