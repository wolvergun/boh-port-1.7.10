package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.entity.SadakoEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.mcreator.boh.compat.mc.world.level.GameType;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.M;

public class VHSTapeItemInInventoryTickProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            Vec3 _center = new Vec3(x, y, z);
            for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(50.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                if (((new Object() {

                    public boolean checkGamemode(Entity _ent) {
                        if (_ent instanceof EntityPlayerMP _serverPlayer) {
                            return M.getGameModeForPlayer(M.gameMode(_serverPlayer)) == GameType.SURVIVAL;
                        } else {
                            return M.isClientSide(M.level(_ent)) && _ent instanceof EntityPlayer _player ? M.getPlayerInfo(M.getConnection(Minecraft.getMinecraft()), M.getId(M.getGameProfile(_player))) != null && M.getGameMode(M.getPlayerInfo(M.getConnection(Minecraft.getMinecraft()), M.getId(M.getGameProfile(_player)))) == GameType.SURVIVAL : false;
                        }
                    }
                }).checkGamemode(entity) || (new Object() {

                    public boolean checkGamemode(Entity _ent) {
                        if (_ent instanceof EntityPlayerMP _serverPlayer) {
                            return M.getGameModeForPlayer(M.gameMode(_serverPlayer)) == GameType.ADVENTURE;
                        } else {
                            return M.isClientSide(M.level(_ent)) && _ent instanceof EntityPlayer _player ? M.getPlayerInfo(M.getConnection(Minecraft.getMinecraft()), M.getId(M.getGameProfile(_player))) != null && M.getGameMode(M.getPlayerInfo(M.getConnection(Minecraft.getMinecraft()), M.getId(M.getGameProfile(_player)))) == GameType.ADVENTURE : false;
                        }
                    }
                }).checkGamemode(entity)) && entityiterator instanceof SadakoEntity && entityiterator instanceof EntityLiving _entity) {
                    M.moveTo(M.getNavigation(_entity), x, y, z, 1.0);
                }
            }
        }
    }
}
