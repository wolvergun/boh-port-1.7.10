package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.TormentPyramidEntity;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.world.damagesource.DamageTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.M;

public class TormentPyramidOnInitialEntitySpawnProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (entity instanceof TormentPyramidEntity) {
                ((TormentPyramidEntity) entity).setAnimation("spawn");
            }
            BohMod.queueServerWork(2, () -> {
                Entity _ent = entity;
                if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "spreadplayers ~ ~ 1 1 under 1 true @e[type=boh:torment_pyramid,limit=1,distance=0..3]");
                }
            });
            BohMod.queueServerWork(32, () -> M.putBoolean(M.getPersistentData(entity), "slow", true));
            BohMod.queueServerWork(52, () -> {
                Vec3 _center = new Vec3(x, y, z);
                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(1.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                    if (entityiterator instanceof EntityPlayer) {
                        M.hurt(entityiterator, M.new_DamageSource(M.getHolderOrThrow(M.registryOrThrow(M.registryAccess(world), Registries.DAMAGE_TYPE), DamageTypes.GENERIC)), 4.0F);
                    }
                }
            });
            BohMod.queueServerWork(72, () -> {
                if (!M.isClientSide(M.level(entity))) {
                    M.discard(entity);
                }
            });
        }
    }
}
