package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.WhitefaceFriendlyEntity;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class WhitefaceFriendlyEntityIsHurtProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity, Entity sourceentity) {
        if (entity != null && sourceentity != null && sourceentity instanceof EntityPlayer) {
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
                    "/tellraw @a {\"text\":\"<White Face> We shall continue the game then\",\"color\":\"COLOR\"}"
                );
            }

            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(2.0), e -> true)
                .stream()
                .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                .toList()) {
                if (entityiterator instanceof WhitefaceFriendlyEntity) {
                    if (!M.isClientSide(M.level(entity))) {
                        M.discard(entity);
                    }

                    if (world instanceof WorldServer _level) {
                        Entity entityToSpawn = M.spawn(BohModEntities.WHITEFACE.get(), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                            M.setYRot(entityToSpawn, M.getYRot(entity));
                            M.setYBodyRot(entityToSpawn, M.getYRot(entity));
                            M.setYHeadRot(entityToSpawn, M.getYRot(entity));
                            M.setXRot(entityToSpawn, M.getXRot(entity));
                            M.setDeltaMovement(entityToSpawn, M.getDeltaMovement(entity).x(), M.getDeltaMovement(entity).y(), M.getDeltaMovement(entity).z());
                        }
                    }

                    if (entityiterator instanceof EntityLivingBase _entity) {
                        M.setHealth(_entity, entity instanceof EntityLivingBase _livEnt ? M.getHealth(_livEnt) : -1.0F);
                    }
                }
            }
        }
    }
}
