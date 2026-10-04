package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModParticleTypes;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.particles.SimpleParticleType;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.util.Mth;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class CognitoHazartOnEffectActiveTickProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            Entity _entityTeam = entity;
            ScorePlayerTeam _pt = M.getPlayerTeam(M.getScoreboard(M.level(_entityTeam)), "cognito");
            if (_pt != null) {
                if (_entityTeam instanceof EntityPlayer _player) {
                    M.addPlayerToTeam(M.getScoreboard(M.level(_entityTeam)), M.getName(M.getGameProfile(_player)), _pt);
                } else {
                    M.addPlayerToTeam(M.getScoreboard(M.level(_entityTeam)), M.getStringUUID(_entityTeam), _pt);
                }
            }
            if (!M.getBoolean(M.getPersistentData(entity), "start") && Math.random() < 0.025) {
                M.putBoolean(M.getPersistentData(entity), "start", true);
                if (M.isClientSide(world) && world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:boiled_one_trumpet")), SoundSource.AMBIENT, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:boiled_one_trumpet")), SoundSource.AMBIENT, 1.0F, 1.0F, false);
                    }
                }
            }
            if (M.isClientSide(world) && M.getBoolean(M.getPersistentData(entity), "start")) {
                M.putDouble(M.getPersistentData(entity), "timer", M.getDouble(M.getPersistentData(entity), "timer") + 1.0);
                if (Math.random() < 0.5) {
                    M.addParticle(world, (SimpleParticleType) BohModParticleTypes.BLOOD_FALL.get(), M.getX(entity) + Mth.nextInt(RandomSource.create(), -10, 10), M.getY(entity) + 25.0, M.getZ(entity) + Mth.nextInt(RandomSource.create(), -10, 10), 0.0, 0.0, 0.0);
                    M.addParticle(world, (SimpleParticleType) BohModParticleTypes.BLOOD_FALL.get(), M.getX(entity) + Mth.nextInt(RandomSource.create(), -10, 10), M.getY(entity) + 25.0, M.getZ(entity) + Mth.nextInt(RandomSource.create(), -10, 10), 0.0, 0.0, 0.0);
                    M.addParticle(world, (SimpleParticleType) BohModParticleTypes.BLOOD_FALL.get(), M.getX(entity) + Mth.nextInt(RandomSource.create(), -10, 10), M.getY(entity) + 25.0, M.getZ(entity) + Mth.nextInt(RandomSource.create(), -10, 10), 0.0, 0.0, 0.0);
                    M.addParticle(world, (SimpleParticleType) BohModParticleTypes.BLOOD_FALL.get(), M.getX(entity) + Mth.nextInt(RandomSource.create(), -10, 10), M.getY(entity) + 25.0, M.getZ(entity) + Mth.nextInt(RandomSource.create(), -10, 10), 0.0, 0.0, 0.0);
                    M.addParticle(world, (SimpleParticleType) BohModParticleTypes.BLOOD_FALL.get(), M.getX(entity) + Mth.nextInt(RandomSource.create(), -10, 10), M.getY(entity) + 25.0, M.getZ(entity) + Mth.nextInt(RandomSource.create(), -10, 10), 0.0, 0.0, 0.0);
                    M.addParticle(world, (SimpleParticleType) BohModParticleTypes.BLOOD_FALL.get(), M.getX(entity) + Mth.nextInt(RandomSource.create(), -10, 10), M.getY(entity) + 25.0, M.getZ(entity) + Mth.nextInt(RandomSource.create(), -10, 10), 0.0, 0.0, 0.0);
                    M.addParticle(world, (SimpleParticleType) BohModParticleTypes.BLOOD_FALL.get(), M.getX(entity) + Mth.nextInt(RandomSource.create(), -10, 10), M.getY(entity) + 25.0, M.getZ(entity) + Mth.nextInt(RandomSource.create(), -10, 10), 0.0, 0.0, 0.0);
                    M.addParticle(world, (SimpleParticleType) BohModParticleTypes.BLOOD_FALL.get(), M.getX(entity) + Mth.nextInt(RandomSource.create(), -10, 10), M.getY(entity) + 25.0, M.getZ(entity) + Mth.nextInt(RandomSource.create(), -10, 10), 0.0, 0.0, 0.0);
                    if (Math.random() < 0.33 && world instanceof World _level) {
                        if (!M.isClientSide(_level)) {
                            M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("weather.rain")), SoundSource.AMBIENT, 1.0F, 1.0F);
                        } else {
                            M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("weather.rain")), SoundSource.AMBIENT, 1.0F, 1.0F, false);
                        }
                    }
                }
            }
            if (M.getDouble(M.getPersistentData(entity), "timer") == 200.0) {
                M.putDouble(M.getPersistentData(entity), "timer", 0.0);
                M.putBoolean(M.getPersistentData(entity), "start", false);
            }
        }
    }
}
