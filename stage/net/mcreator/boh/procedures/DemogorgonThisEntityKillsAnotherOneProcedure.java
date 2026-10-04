package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.mcreator.boh.compat.mc.world.scores.criteria.ObjectiveCriteria;
import net.mcreator.boh.compat.mc.world.scores.criteria.RenderType;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class DemogorgonThisEntityKillsAnotherOneProcedure {

    public static void execute(World world, double x, double y, double z, Entity sourceentity) {
        if (sourceentity != null) {
            if (world instanceof World _level) {
                if (!M.isClientSide(_level)) {
                    M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:demogorgon_roar")), SoundSource.HOSTILE, 1.0F, 1.0F);
                } else {
                    M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:demogorgon_roar")), SoundSource.HOSTILE, 1.0F, 1.0F, false);
                }
            }
            if (sourceentity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 20, 255));
            }
            Entity _ent = sourceentity;
            Scoreboard _sc = M.getScoreboard(M.level(_ent));
            ScoreObjective _so = M.getObjective(_sc, "anim");
            if (_so == null) {
                _so = M.addObjective(_sc, "anim", ObjectiveCriteria.DUMMY, Component.literal("anim"), RenderType.INTEGER);
            }
            M.setScore(M.getOrCreatePlayerScore(_sc, M.getScoreboardName(_ent), _so), 2);
            BohMod.queueServerWork(20, () -> {
                Entity _entx = sourceentity;
                Scoreboard _scx = M.getScoreboard(M.level(_entx));
                ScoreObjective _sox = M.getObjective(_scx, "anim");
                if (_sox == null) {
                    _sox = M.addObjective(_scx, "anim", ObjectiveCriteria.DUMMY, Component.literal("anim"), RenderType.INTEGER);
                }
                M.setScore(M.getOrCreatePlayerScore(_scx, M.getScoreboardName(_entx), _sox), 0);
            });
        }
    }
}
