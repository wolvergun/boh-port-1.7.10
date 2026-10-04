package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.compat.mc.world.scores.criteria.ObjectiveCriteria;
import net.mcreator.boh.compat.mc.world.scores.criteria.RenderType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class DemogorgonThisEntityKillsAnotherOneProcedure {
    public static void execute(World world, double x, double y, double z, Entity sourceentity) {
        if (sourceentity != null) {
            if (world instanceof World) {
                if (!M.isClientSide(world)) {
                    M.playSound(
                        world,
                        null,
                        BlockPos.containing(x, y, z),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:demogorgon_roar")),
                        SoundSource.HOSTILE,
                        1.0F,
                        1.0F
                    );
                } else {
                    M.playLocalSound(
                        world,
                        x,
                        y,
                        z,
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:demogorgon_roar")),
                        SoundSource.HOSTILE,
                        1.0F,
                        1.0F,
                        false
                    );
                }
            }

            if (sourceentity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 20, 255));
            }

            Scoreboard _sc = M.getScoreboard(M.level(sourceentity));
            ScoreObjective _so = M.getObjective(_sc, "anim");
            if (_so == null) {
                _so = M.addObjective(_sc, "anim", ObjectiveCriteria.DUMMY, Component.literal("anim"), RenderType.INTEGER);
            }

            M.setScore(M.getOrCreatePlayerScore(_sc, M.getScoreboardName(sourceentity), _so), 2);
            BohMod.queueServerWork(20, () -> {
                Scoreboard _scx = M.getScoreboard(M.level(sourceentity));
                ScoreObjective _sox = M.getObjective(_scx, "anim");
                if (_sox == null) {
                    _sox = M.addObjective(_scx, "anim", ObjectiveCriteria.DUMMY, Component.literal("anim"), RenderType.INTEGER);
                }

                M.setScore(M.getOrCreatePlayerScore(_scx, M.getScoreboardName(sourceentity), _sox), 0);
            });
        }
    }
}
