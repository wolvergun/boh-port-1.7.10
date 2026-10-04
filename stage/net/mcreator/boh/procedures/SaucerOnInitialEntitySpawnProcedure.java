package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.network.BohModVariables;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class SaucerOnInitialEntitySpawnProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            Entity _ent = entity;
            if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/team add alien");
            }
            BohMod.queueServerWork(2, () -> {
                Entity _entx = entity;
                M.teleportTo(_entx, x, y + 20.0, z);
                if (_entx instanceof EntityPlayerMP _serverPlayer) {
                    M.teleport(M.connection(_serverPlayer), x, y + 20.0, z, M.getYRot(_entx), M.getXRot(_entx));
                }
                Entity _entx_r55 = entity;
                if (!M.isClientSide(M.level(_entx_r55)) && M.getServer(_entx_r55) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_entx_r55)), new CommandSourceStack(CommandSource.NULL, M.position(_entx_r55), M.getRotationVector(_entx_r55), M.level(_entx_r55) instanceof WorldServer ? (WorldServer) M.level(_entx_r55) : null, 4, M.getString(M.getName(_entx_r55)), M.getDisplayName(_entx_r55), M.getServer(M.level(_entx_r55)), _entx_r55), "/team modify alien friendlyFire false");
                }
            });
            BohMod.queueServerWork(5, () -> {
                Entity _entx = entity;
                if (!M.isClientSide(M.level(_entx)) && M.getServer(_entx) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_entx)), new CommandSourceStack(CommandSource.NULL, M.position(_entx), M.getRotationVector(_entx), M.level(_entx) instanceof WorldServer ? (WorldServer) M.level(_entx) : null, 4, M.getString(M.getName(_entx)), M.getDisplayName(_entx), M.getServer(M.level(_entx)), _entx), "/team join alien @e[type=boh:saucer]");
                }
            });
            if (world instanceof World _level) {
                if (!M.isClientSide(_level)) {
                    M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mother_ship_loop")), SoundSource.HOSTILE, 100.0F, 1.0F);
                } else {
                    M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mother_ship_loop")), SoundSource.HOSTILE, 100.0F, 1.0F, false);
                }
            }
            if (world instanceof World _level) {
                if (!M.isClientSide(_level)) {
                    M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:gray_ost")), SoundSource.MUSIC, 1.0F, 1.0F);
                } else {
                    M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:gray_ost")), SoundSource.MUSIC, 1.0F, 1.0F, false);
                }
            }
            BohModVariables.MapVariables.get(world).spawn_saucer = 1.0;
            BohModVariables.MapVariables.get(world).syncData(world);
        }
    }
}
