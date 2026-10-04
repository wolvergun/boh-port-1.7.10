package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.client.Minecraft;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.mc.world.level.GameType;
import net.minecraft.world.World;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class LifeformEffigyItemInHandTickProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity != null) {
            if ((M.getItem((entity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY)) == M.getItem(itemstack) || M.getItem((entity instanceof EntityLivingBase _livEnt ? M.getOffhandItem(_livEnt) : M.EMPTY)) == M.getItem(itemstack)) && (entity instanceof EntityLivingBase _livEnt ? M.getHealth(_livEnt) : -1.0F) < (entity instanceof EntityLivingBase _livEnt ? M.getMaxHealth(_livEnt) : -1.0F) / 4.0F && !M.getBoolean(M.getOrCreateTag(itemstack), "use")) {
                M.putBoolean(M.getOrCreateTag(itemstack), "use", true);
                if (!(new Object() {

                    public boolean checkGamemode(Entity _ent) {
                        if (_ent instanceof EntityPlayerMP _serverPlayer) {
                            return M.getGameModeForPlayer(M.gameMode(_serverPlayer)) == GameType.CREATIVE;
                        } else {
                            return M.isClientSide(M.level(_ent)) && _ent instanceof EntityPlayer _player ? M.getPlayerInfo(M.getConnection(Minecraft.getMinecraft()), M.getId(M.getGameProfile(_player))) != null && M.getGameMode(M.getPlayerInfo(M.getConnection(Minecraft.getMinecraft()), M.getId(M.getGameProfile(_player)))) == GameType.CREATIVE : false;
                        }
                    }
                }).checkGamemode(entity)) {
                    Entity _ent = entity;
                    if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                        M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/particle minecraft:squid_ink ~ ~1 ~ .25 .5 .25 0 20");
                    }
                    if (world instanceof World _level) {
                        if (!M.isClientSide(_level)) {
                            M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:effigy_sound")), SoundSource.PLAYERS, 0.5F, 1.2F);
                        } else {
                            M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:effigy_sound")), SoundSource.PLAYERS, 0.5F, 1.2F, false);
                        }
                    }
                    if (M.isClientSide(world)) {
                        M.displayItemActivation(M.gameRenderer(Minecraft.getMinecraft()), M.new_ItemStack(BohModItems.LIFEFORM_EFFIGY.get()));
                    }
                    M.putBoolean(M.getOrCreateTag(itemstack), "sound", true);
                    if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.DAMAGE_RESISTANCE, 40, 255, false, false));
                    }
                    Entity _ent_r46 = entity;
                    M.teleportTo(_ent_r46, entity instanceof EntityPlayerMP _player && !M.isClientSide(M.level(_player)) ? (M.getRespawnDimension(_player).equals(M.dimension(M.level(_player))) && M.getRespawnPosition(_player) != null ? M.getX(M.getRespawnPosition(_player)) : M.getXSpawn(M.getLevelData(M.level(_player)))) : 0.0, entity instanceof EntityPlayerMP _player && !M.isClientSide(M.level(_player)) ? (M.getRespawnDimension(_player).equals(M.dimension(M.level(_player))) && M.getRespawnPosition(_player) != null ? M.getY(M.getRespawnPosition(_player)) : M.getYSpawn(M.getLevelData(M.level(_player)))) : 0.0, entity instanceof EntityPlayerMP _player && !M.isClientSide(M.level(_player)) ? (M.getRespawnDimension(_player).equals(M.dimension(M.level(_player))) && M.getRespawnPosition(_player) != null ? M.getZ(M.getRespawnPosition(_player)) : M.getZSpawn(M.getLevelData(M.level(_player)))) : 0.0);
                    if (_ent_r46 instanceof EntityPlayerMP _serverPlayer) {
                        M.teleport(M.connection(_serverPlayer), entity instanceof EntityPlayerMP _player && !M.isClientSide(M.level(_player)) ? (M.getRespawnDimension(_player).equals(M.dimension(M.level(_player))) && M.getRespawnPosition(_player) != null ? M.getX(M.getRespawnPosition(_player)) : M.getXSpawn(M.getLevelData(M.level(_player)))) : 0.0, entity instanceof EntityPlayerMP _player && !M.isClientSide(M.level(_player)) ? (M.getRespawnDimension(_player).equals(M.dimension(M.level(_player))) && M.getRespawnPosition(_player) != null ? M.getY(M.getRespawnPosition(_player)) : M.getYSpawn(M.getLevelData(M.level(_player)))) : 0.0, entity instanceof EntityPlayerMP _player && !M.isClientSide(M.level(_player)) ? (M.getRespawnDimension(_player).equals(M.dimension(M.level(_player))) && M.getRespawnPosition(_player) != null ? M.getZ(M.getRespawnPosition(_player)) : M.getZSpawn(M.getLevelData(M.level(_player)))) : 0.0, M.getYRot(_ent_r46), M.getXRot(_ent_r46));
                    }
                    BohMod.queueServerWork(10, () -> {
                        M.putBoolean(M.getOrCreateTag(itemstack), "use", false);
                        if (entity instanceof EntityPlayer _playerx) {
                            ItemStack _stktoremove = itemstack;
                            M.clearOrCountMatchingItems(M.getInventory(_playerx), p -> M.getItem(_stktoremove) == M.getItem(p), 1, M.getCraftSlots(M.inventoryMenu(_playerx)));
                        }
                    });
                }
            }
        }
    }
}
