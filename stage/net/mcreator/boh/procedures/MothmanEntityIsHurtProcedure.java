package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.MothmanEntity;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class MothmanEntityIsHurtProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (Math.random() < 0.1) {
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mothman_fly")), SoundSource.HOSTILE, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mothman_fly")), SoundSource.HOSTILE, 1.0F, 1.0F, false);
                    }
                }
                Entity _ent = entity;
                if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/particle minecraft:squid_ink ~ ~ ~ 0.5 .5 0.5 0 100");
                }
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 20, 254, false, false));
                }
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.WEAKNESS, 20, 254, false, false));
                }
                if (entity instanceof MothmanEntity) {
                    ((MothmanEntity) entity).setAnimation("flight");
                }
                BohMod.queueServerWork(20, () -> {
                    Entity _entx = entity;
                    if (!M.isClientSide(M.level(_entx)) && M.getServer(_entx) != null) {
                        M.performPrefixedCommand(M.getCommands(M.getServer(_entx)), new CommandSourceStack(CommandSource.NULL, M.position(_entx), M.getRotationVector(_entx), M.level(_entx) instanceof WorldServer ? (WorldServer) M.level(_entx) : null, 4, M.getString(M.getName(_entx)), M.getDisplayName(_entx), M.getServer(M.level(_entx)), _entx), "/spreadplayers ~ ~ 30 30 false @e[type=boh:mothman,limit=1,sort=nearest]");
                    }
                    if (entity instanceof MothmanEntity) {
                        ((MothmanEntity) entity).setAnimation("landing");
                    }
                    if (world instanceof World _level) {
                        if (!M.isClientSide(_level)) {
                            M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mothman_land")), SoundSource.HOSTILE, 1.0F, 1.0F);
                        } else {
                            M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mothman_land")), SoundSource.HOSTILE, 1.0F, 1.0F, false);
                        }
                    }
                    BohMod.queueServerWork(12, () -> {
                        Entity _entx_l47 = entity;
                        if (!M.isClientSide(M.level(_entx_l47)) && M.getServer(_entx_l47) != null) {
                            M.performPrefixedCommand(M.getCommands(M.getServer(_entx_l47)), new CommandSourceStack(CommandSource.NULL, M.position(_entx_l47), M.getRotationVector(_entx_l47), M.level(_entx_l47) instanceof WorldServer ? (WorldServer) M.level(_entx_l47) : null, 4, M.getString(M.getName(_entx_l47)), M.getDisplayName(_entx_l47), M.getServer(M.level(_entx_l47)), _entx_l47), "/particle minecraft:squid_ink ~ ~ ~ 1 .1 1 0 100");
                        }
                    });
                });
            }
        }
    }
}
