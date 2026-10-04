package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.GasterEntity;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundGameEventPacket;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundLevelEventPacket;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.PotionEffect;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.M;

public class GasterBlockExitOnBlockRightClickedProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            BohMod.queueServerWork(2, () -> {
                if (entity instanceof EntityPlayerMP _player && !M.isClientSide(M.level(_player))) {
                    ResourceKey<World> destinationType = M.OVERWORLD;
                    if (M.dimension(M.level(_player)) == destinationType) {
                        return;
                    }
                    WorldServer nextLevel = M.getLevel(M.server(_player), destinationType);
                    if (nextLevel != null) {
                        M.connection(_player).send(new ClientboundGameEventPacket(ClientboundGameEventPacket.WIN_GAME, 0.0F));
                        M.teleportTo(_player, nextLevel, M.getX(_player), M.getY(_player), M.getZ(_player), M.getYRot(_player), M.getXRot(_player));
                        M.connection(_player).send(new ClientboundPlayerAbilitiesPacket(M.getAbilities(_player)));
                        for (PotionEffect _effectinstance : M.getActiveEffects(_player)) {
                            M.connection(_player).send(new ClientboundUpdateMobEffectPacket(M.getId(_player), _effectinstance));
                        }
                        M.connection(_player).send(new ClientboundLevelEventPacket(1032, BlockPos.ZERO, 0, false));
                    }
                }
                BohMod.queueServerWork(2, () -> {
                    Entity _ent = entity;
                    M.teleportTo(_ent, M.getDouble(M.getPersistentData(entity), "xposp"), M.getDouble(M.getPersistentData(entity), "yposp"), M.getDouble(M.getPersistentData(entity), "zposp"));
                    if (_ent instanceof EntityPlayerMP _serverPlayer) {
                        M.teleport(M.connection(_serverPlayer), M.getDouble(M.getPersistentData(entity), "xposp"), M.getDouble(M.getPersistentData(entity), "yposp"), M.getDouble(M.getPersistentData(entity), "zposp"), M.getYRot(_ent), M.getXRot(_ent));
                    }
                });
            });
            Vec3 _center = new Vec3(x, y, z);
            for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(50.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                if (entityiterator instanceof GasterEntity && !M.isClientSide(M.level(entityiterator))) {
                    M.discard(entityiterator);
                }
            }
        }
    }
}
