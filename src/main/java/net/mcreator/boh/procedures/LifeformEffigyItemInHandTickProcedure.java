package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.compat.mc.world.level.GameType;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class LifeformEffigyItemInHandTickProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity != null
            && (
                M.getItem(entity instanceof EntityLivingBase _livEntxxx ? M.getMainHandItem(_livEntxxx) : M.EMPTY) == M.getItem(itemstack)
                    || M.getItem(entity instanceof EntityLivingBase _livEntxx ? M.getOffhandItem(_livEntxx) : M.EMPTY) == M.getItem(itemstack)
            )
            && (entity instanceof EntityLivingBase _livEntx ? M.getHealth(_livEntx) : -1.0F)
                < (entity instanceof EntityLivingBase _livEnt ? M.getMaxHealth(_livEnt) : -1.0F) / 4.0F
            && !M.getBoolean(M.getOrCreateTag(itemstack), "use")) {
            M.putBoolean(M.getOrCreateTag(itemstack), "use", true);
            if (!(new Object() {
                    public boolean checkGamemode(Entity _ent) {
                        if (_ent instanceof EntityPlayerMP _serverPlayer) {
                            return M.getGameModeForPlayer(M.gameMode(_serverPlayer)) == GameType.CREATIVE;
                        } else {
                            return M.isClientSide(M.level(_ent)) && _ent instanceof EntityPlayer _player
                                ? M.getPlayerInfo(M.getConnection(Minecraft.getMinecraft()), M.getId(M.getGameProfile(_player))) != null
                                    && M.getGameMode(M.getPlayerInfo(M.getConnection(Minecraft.getMinecraft()), M.getId(M.getGameProfile(_player))))
                                        == GameType.CREATIVE
                                : false;
                        }
                    }
                })
                .checkGamemode(entity)) {
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
                        "/particle minecraft:squid_ink ~ ~1 ~ .25 .5 .25 0 20"
                    );
                }

                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:effigy_sound")),
                            SoundSource.PLAYERS,
                            0.5F,
                            1.2F
                        );
                    } else {
                        M.playLocalSound(
                            world,
                            x,
                            y,
                            z,
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:effigy_sound")),
                            SoundSource.PLAYERS,
                            0.5F,
                            1.2F,
                            false
                        );
                    }
                }

                if (M.isClientSide(world)) {
                    M.displayItemActivation(M.gameRenderer(Minecraft.getMinecraft()), M.new_ItemStack(BohModItems.LIFEFORM_EFFIGY.get()));
                }

                M.putBoolean(M.getOrCreateTag(itemstack), "sound", true);
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.DAMAGE_RESISTANCE, 40, 255, false, false));
                }

                M.teleportTo(
                    entity,
                    entity instanceof EntityPlayerMP _playerxx && !M.isClientSide(M.level(_playerxx))
                        ? (
                            M.getRespawnDimension(_playerxx).equals(M.dimension(M.level(_playerxx))) && M.getRespawnPosition(_playerxx) != null
                                ? M.getX(M.getRespawnPosition(_playerxx))
                                : M.getXSpawn(M.getLevelData(M.level(_playerxx)))
                        )
                        : 0.0,
                    entity instanceof EntityPlayerMP _playerx && !M.isClientSide(M.level(_playerx))
                        ? (
                            M.getRespawnDimension(_playerx).equals(M.dimension(M.level(_playerx))) && M.getRespawnPosition(_playerx) != null
                                ? M.getY(M.getRespawnPosition(_playerx))
                                : M.getYSpawn(M.getLevelData(M.level(_playerx)))
                        )
                        : 0.0,
                    entity instanceof EntityPlayerMP _player && !M.isClientSide(M.level(_player))
                        ? (
                            M.getRespawnDimension(_player).equals(M.dimension(M.level(_player))) && M.getRespawnPosition(_player) != null
                                ? M.getZ(M.getRespawnPosition(_player))
                                : M.getZSpawn(M.getLevelData(M.level(_player)))
                        )
                        : 0.0
                );
                if (entity instanceof EntityPlayerMP _serverPlayer) {
                    M.teleport(
                        M.connection(_serverPlayer),
                        entity instanceof EntityPlayerMP _playerxxxxx && !M.isClientSide(M.level(_playerxxxxx))
                            ? (
                                M.getRespawnDimension(_playerxxxxx).equals(M.dimension(M.level(_playerxxxxx))) && M.getRespawnPosition(_playerxxxxx) != null
                                    ? M.getX(M.getRespawnPosition(_playerxxxxx))
                                    : M.getXSpawn(M.getLevelData(M.level(_playerxxxxx)))
                            )
                            : 0.0,
                        entity instanceof EntityPlayerMP _playerxxxx && !M.isClientSide(M.level(_playerxxxx))
                            ? (
                                M.getRespawnDimension(_playerxxxx).equals(M.dimension(M.level(_playerxxxx))) && M.getRespawnPosition(_playerxxxx) != null
                                    ? M.getY(M.getRespawnPosition(_playerxxxx))
                                    : M.getYSpawn(M.getLevelData(M.level(_playerxxxx)))
                            )
                            : 0.0,
                        entity instanceof EntityPlayerMP _playerxxx && !M.isClientSide(M.level(_playerxxx))
                            ? (
                                M.getRespawnDimension(_playerxxx).equals(M.dimension(M.level(_playerxxx))) && M.getRespawnPosition(_playerxxx) != null
                                    ? M.getZ(M.getRespawnPosition(_playerxxx))
                                    : M.getZSpawn(M.getLevelData(M.level(_playerxxx)))
                            )
                            : 0.0,
                        M.getYRot(entity),
                        M.getXRot(entity)
                    );
                }

                BohMod.queueServerWork(
                    10,
                    () -> {
                        M.putBoolean(M.getOrCreateTag(itemstack), "use", false);
                        if (entity instanceof EntityPlayer _playerxxx) {
                            M.clearOrCountMatchingItems(
                                M.getInventory(_playerxxx), p -> M.getItem(itemstack) == M.getItem(p), 1, M.getCraftSlots(M.inventoryMenu(_playerxxx))
                            );
                        }
                    }
                );
            }
        }
    }
}
