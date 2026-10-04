package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.mcreator.boh.compat.mc.world.scores.criteria.ObjectiveCriteria;
import net.mcreator.boh.compat.mc.world.scores.criteria.RenderType;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class BendrownedOnInitialEntitySpawnProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            Entity _ent = entity;
            Scoreboard _sc = M.getScoreboard(M.level(_ent));
            ScoreObjective _so = M.getObjective(_sc, "anim");
            if (_so == null) {
                _so = M.addObjective(_sc, "anim", ObjectiveCriteria.DUMMY, Component.literal("anim"), RenderType.INTEGER);
            }
            M.setScore(M.getOrCreatePlayerScore(_sc, M.getScoreboardName(_ent), _so), 0);
            Entity _ent_r9 = entity;
            if (!M.isClientSide(M.level(_ent_r9)) && M.getServer(_ent_r9) != null) {
                M.performPrefixedCommand(M.getCommands(M.getServer(_ent_r9)), new CommandSourceStack(CommandSource.NULL, M.position(_ent_r9), M.getRotationVector(_ent_r9), M.level(_ent_r9) instanceof WorldServer ? (WorldServer) M.level(_ent_r9) : null, 4, M.getString(M.getName(_ent_r9)), M.getDisplayName(_ent_r9), M.getServer(M.level(_ent_r9)), _ent_r9), "/scoreboard objectives setdisplay sidebar.team.black teleport_ben");
            }
            if (world instanceof World _level) {
                if (!M.isClientSide(_level)) {
                    M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:ben_spawn")), SoundSource.HOSTILE, 1.0F, 1.0F);
                } else {
                    M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:ben_spawn")), SoundSource.HOSTILE, 1.0F, 1.0F, false);
                }
            }
        }
    }
}
