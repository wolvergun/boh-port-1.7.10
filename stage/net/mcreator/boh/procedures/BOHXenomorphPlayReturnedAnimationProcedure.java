package net.mcreator.boh.procedures;

import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.mcreator.boh.compat.M;

public class BOHXenomorphPlayReturnedAnimationProcedure {

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
            return "attack";
        } else if ((new Object() {

            public int getScore(String score, Entity _ent) {
                Scoreboard _sc = M.getScoreboard(M.level(_ent));
                ScoreObjective _so = M.getObjective(_sc, score);
                return _so != null ? M.getScore(M.getOrCreatePlayerScore(_sc, M.getScoreboardName(_ent), _so)) : 0;
            }
        }).getScore("anim", entity) == 2) {
            return "attack1";
        } else {
            return (new Object() {

                public int getScore(String score, Entity _ent) {
                    Scoreboard _sc = M.getScoreboard(M.level(_ent));
                    ScoreObjective _so = M.getObjective(_sc, score);
                    return _so != null ? M.getScore(M.getOrCreatePlayerScore(_sc, M.getScoreboardName(_ent), _so)) : 0;
                }
            }).getScore("anim", entity) == 3 ? "attack2" : "empty";
        }
    }
}
