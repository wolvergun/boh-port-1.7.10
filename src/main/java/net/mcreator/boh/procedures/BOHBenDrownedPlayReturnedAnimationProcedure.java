package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;

public class BOHBenDrownedPlayReturnedAnimationProcedure {
    public static String execute(Entity entity) {
        if (entity == null) {
            return "";
        } else if ((new Object() {
            public int getScore(String score, Entity _ent) {
                Scoreboard _sc = M.getScoreboard(M.level(_ent));
                ScoreObjective _so = M.getObjective(_sc, score);
                return _so != null ? M.getScore(M.getOrCreatePlayerScore(_sc, M.getScoreboardName(_ent), _so)) : 0;
            }
        }).getScore("anim", entity) == 1) {
            return "teleportout";
        } else {
            return (new Object() {
                public int getScore(String score, Entity _ent) {
                    Scoreboard _sc = M.getScoreboard(M.level(_ent));
                    ScoreObjective _so = M.getObjective(_sc, score);
                    return _so != null ? M.getScore(M.getOrCreatePlayerScore(_sc, M.getScoreboardName(_ent), _so)) : 0;
                }
            }).getScore("anim", entity) == 2 ? "teleportin" : "empty";
        }
    }
}
