package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.SonicExeEntity;
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
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class SonicExeEntityIsHurtProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity, Entity sourceentity) {
        if (entity != null && sourceentity != null) {
            if (entity instanceof SonicExeEntity) {
                ((SonicExeEntity) entity).setAnimation("hurt");
            }
            if (Math.random() < 0.01 && entity instanceof EntityLiving _entity && sourceentity instanceof EntityLivingBase _ent) {
                M.setTarget(_entity, _ent);
            }
            if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.WEAKNESS, 60, 255, false, false));
            }
            if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 60, 255, false, false));
            }
            BohMod.queueServerWork(8, () -> {
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:exe_teleport")), SoundSource.HOSTILE, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:exe_teleport")), SoundSource.HOSTILE, 1.0F, 1.0F, false);
                    }
                }
                Entity _entx = entity;
                if (!M.isClientSide(M.level(_entx)) && M.getServer(_entx) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_entx)), new CommandSourceStack(CommandSource.NULL, M.position(_entx), M.getRotationVector(_entx), M.level(_entx) instanceof WorldServer ? (WorldServer) M.level(_entx) : null, 4, M.getString(M.getName(_entx)), M.getDisplayName(_entx), M.getServer(M.level(_entx)), _entx), "/spreadplayers ~ ~ 20 20 false @e[type=boh:sonic_exe,limit=1,distance=0..2]");
                }
                if (entity instanceof SonicExeEntity) {
                    ((SonicExeEntity) entity).setAnimation("hurt");
                }
            });
            BohMod.queueServerWork(12, () -> {
                if (Math.random() < 0.6 && world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:exe_laugh")), SoundSource.HOSTILE, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:exe_laugh")), SoundSource.HOSTILE, 1.0F, 1.0F, false);
                    }
                }
                if (entity instanceof SonicExeEntity) {
                    ((SonicExeEntity) entity).setAnimation("laugh");
                }
            });
        }
    }
}
