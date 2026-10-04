package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.items.ItemHandlerHelper;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundGameEventPacket;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundLevelEventPacket;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.mcreator.boh.compat.world.Dimensions;
import net.mcreator.boh.init.BohModItems;
import net.mcreator.boh.network.BohModVariables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class LifeformEntityDiesProcedure {
    public static void execute(World world, Entity sourceentity) {
        if (sourceentity != null) {
            BohModVariables.MapVariables.get(world).spawn_lifeform = false;
            BohModVariables.MapVariables.get(world).syncData(world);
            if (M.dimension(M.level(sourceentity)) == Dimensions.dimensionKey(new ResourceLocation("boh:level_0"))) {
                if (sourceentity instanceof EntityPlayerMP _player && !M.isClientSide(M.level(_player))) {
                    ResourceKey<World> destinationType = M.OVERWORLD;
                    if (M.dimension(M.level(_player)) == destinationType) {
                        return;
                    }

                    WorldServer nextLevel = M.getLevel(M.server(_player), destinationType);
                    if (nextLevel != null) {
                        M.connection(_player).send(new ClientboundGameEventPacket(4, 0.0F));
                        M.teleportTo(_player, nextLevel, M.getX(_player), M.getY(_player), M.getZ(_player), M.getYRot(_player), M.getXRot(_player));
                        M.connection(_player).send(new ClientboundPlayerAbilitiesPacket(M.getAbilities(_player)));

                        for (PotionEffect _effectinstance : M.getActiveEffects(_player)) {
                            M.connection(_player).send(new ClientboundUpdateMobEffectPacket(M.getId(_player), _effectinstance));
                        }

                        M.connection(_player).send(new ClientboundLevelEventPacket(1032, BlockPos.ZERO, 0, false));
                    }
                }

                M.teleportTo(
                    sourceentity,
                    sourceentity instanceof EntityPlayerMP _playerxx && !M.isClientSide(M.level(_playerxx))
                        ? (
                            M.getRespawnDimension(_playerxx).equals(M.dimension(M.level(_playerxx))) && M.getRespawnPosition(_playerxx) != null
                                ? M.getX(M.getRespawnPosition(_playerxx))
                                : M.getXSpawn(M.getLevelData(M.level(_playerxx)))
                        )
                        : 0.0,
                    sourceentity instanceof EntityPlayerMP _playerx && !M.isClientSide(M.level(_playerx))
                        ? (
                            M.getRespawnDimension(_playerx).equals(M.dimension(M.level(_playerx))) && M.getRespawnPosition(_playerx) != null
                                ? M.getY(M.getRespawnPosition(_playerx))
                                : M.getYSpawn(M.getLevelData(M.level(_playerx)))
                        )
                        : 0.0,
                    sourceentity instanceof EntityPlayerMP _player && !M.isClientSide(M.level(_player))
                        ? (
                            M.getRespawnDimension(_player).equals(M.dimension(M.level(_player))) && M.getRespawnPosition(_player) != null
                                ? M.getZ(M.getRespawnPosition(_player))
                                : M.getZSpawn(M.getLevelData(M.level(_player)))
                        )
                        : 0.0
                );
                if (sourceentity instanceof EntityPlayerMP _serverPlayer) {
                    M.teleport(
                        M.connection(_serverPlayer),
                        sourceentity instanceof EntityPlayerMP _playerxxxxx && !M.isClientSide(M.level(_playerxxxxx))
                            ? (
                                M.getRespawnDimension(_playerxxxxx).equals(M.dimension(M.level(_playerxxxxx))) && M.getRespawnPosition(_playerxxxxx) != null
                                    ? M.getX(M.getRespawnPosition(_playerxxxxx))
                                    : M.getXSpawn(M.getLevelData(M.level(_playerxxxxx)))
                            )
                            : 0.0,
                        sourceentity instanceof EntityPlayerMP _playerxxxx && !M.isClientSide(M.level(_playerxxxx))
                            ? (
                                M.getRespawnDimension(_playerxxxx).equals(M.dimension(M.level(_playerxxxx))) && M.getRespawnPosition(_playerxxxx) != null
                                    ? M.getY(M.getRespawnPosition(_playerxxxx))
                                    : M.getYSpawn(M.getLevelData(M.level(_playerxxxx)))
                            )
                            : 0.0,
                        sourceentity instanceof EntityPlayerMP _playerxxx && !M.isClientSide(M.level(_playerxxx))
                            ? (
                                M.getRespawnDimension(_playerxxx).equals(M.dimension(M.level(_playerxxx))) && M.getRespawnPosition(_playerxxx) != null
                                    ? M.getZ(M.getRespawnPosition(_playerxxx))
                                    : M.getZSpawn(M.getLevelData(M.level(_playerxxx)))
                            )
                            : 0.0,
                        M.getYRot(sourceentity),
                        M.getXRot(sourceentity)
                    );
                }

                if (sourceentity instanceof EntityPlayer _playerxxx) {
                    ItemStack _setstack = M.copy(M.new_ItemStack(BohModItems.LIFEFORM_EFFIGY.get()));
                    M.setCount(_setstack, 1);
                    ItemHandlerHelper.giveItemToPlayer(_playerxxx, _setstack);
                }
            }
        }
    }
}
