package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModItems;
import net.mcreator.boh.network.BohModVariables;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundGameEventPacket;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundLevelEventPacket;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.PotionEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.mcreator.boh.compat.forge.items.ItemHandlerHelper;
import net.mcreator.boh.compat.M;

public class LifeformEntityDiesProcedure {

    public static void execute(World world, Entity sourceentity) {
        if (sourceentity != null) {
            BohModVariables.MapVariables.get(world).spawn_lifeform = false;
            BohModVariables.MapVariables.get(world).syncData(world);
            if (M.dimension(M.level(sourceentity)) == net.mcreator.boh.compat.world.Dimensions.dimensionKey( new ResourceLocation("boh:level_0"))) {
                if (sourceentity instanceof EntityPlayerMP _player && !M.isClientSide(M.level(_player))) {
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
                Entity _ent = sourceentity;
                M.teleportTo(_ent, sourceentity instanceof EntityPlayerMP _player && !M.isClientSide(M.level(_player)) ? (M.getRespawnDimension(_player).equals(M.dimension(M.level(_player))) && M.getRespawnPosition(_player) != null ? M.getX(M.getRespawnPosition(_player)) : M.getXSpawn(M.getLevelData(M.level(_player)))) : 0.0, sourceentity instanceof EntityPlayerMP _player && !M.isClientSide(M.level(_player)) ? (M.getRespawnDimension(_player).equals(M.dimension(M.level(_player))) && M.getRespawnPosition(_player) != null ? M.getY(M.getRespawnPosition(_player)) : M.getYSpawn(M.getLevelData(M.level(_player)))) : 0.0, sourceentity instanceof EntityPlayerMP _player && !M.isClientSide(M.level(_player)) ? (M.getRespawnDimension(_player).equals(M.dimension(M.level(_player))) && M.getRespawnPosition(_player) != null ? M.getZ(M.getRespawnPosition(_player)) : M.getZSpawn(M.getLevelData(M.level(_player)))) : 0.0);
                if (_ent instanceof EntityPlayerMP _serverPlayer) {
                    M.teleport(M.connection(_serverPlayer), sourceentity instanceof EntityPlayerMP _player && !M.isClientSide(M.level(_player)) ? (M.getRespawnDimension(_player).equals(M.dimension(M.level(_player))) && M.getRespawnPosition(_player) != null ? M.getX(M.getRespawnPosition(_player)) : M.getXSpawn(M.getLevelData(M.level(_player)))) : 0.0, sourceentity instanceof EntityPlayerMP _player && !M.isClientSide(M.level(_player)) ? (M.getRespawnDimension(_player).equals(M.dimension(M.level(_player))) && M.getRespawnPosition(_player) != null ? M.getY(M.getRespawnPosition(_player)) : M.getYSpawn(M.getLevelData(M.level(_player)))) : 0.0, sourceentity instanceof EntityPlayerMP _player && !M.isClientSide(M.level(_player)) ? (M.getRespawnDimension(_player).equals(M.dimension(M.level(_player))) && M.getRespawnPosition(_player) != null ? M.getZ(M.getRespawnPosition(_player)) : M.getZSpawn(M.getLevelData(M.level(_player)))) : 0.0, M.getYRot(_ent), M.getXRot(_ent));
                }
                if (sourceentity instanceof EntityPlayer _player) {
                    ItemStack _setstack = M.copy(M.new_ItemStack(BohModItems.LIFEFORM_EFFIGY.get()));
                    M.setCount(_setstack, 1);
                    ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
                }
            }
        }
    }
}
