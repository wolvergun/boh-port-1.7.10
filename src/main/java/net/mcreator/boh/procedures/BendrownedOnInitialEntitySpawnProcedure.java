package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.scores.criteria.ObjectiveCriteria;
import net.mcreator.boh.compat.mc.world.scores.criteria.RenderType;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class BendrownedOnInitialEntitySpawnProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            Scoreboard _sc = M.getScoreboard(M.level(entity));
            ScoreObjective _so = M.getObjective(_sc, "anim");
            if (_so == null) {
                _so = M.addObjective(_sc, "anim", ObjectiveCriteria.DUMMY, Component.literal("anim"), RenderType.INTEGER);
            }

            M.setScore(M.getOrCreatePlayerScore(_sc, M.getScoreboardName(entity), _so), 0);
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
                    "/scoreboard objectives setdisplay sidebar.team.black teleport_ben"
                );
            }

            if (world instanceof World) {
                if (!M.isClientSide(world)) {
                    M.playSound(
                        world,
                        null,
                        BlockPos.containing(x, y, z),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:ben_spawn")),
                        SoundSource.HOSTILE,
                        1.0F,
                        1.0F
                    );
                } else {
                    M.playLocalSound(
                        world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:ben_spawn")), SoundSource.HOSTILE, 1.0F, 1.0F, false
                    );
                }
            }
        }
    }
}
