package net.mcreator.boh.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;

public class DemogorgonPlayReturnedAnimationProcedure {
   public static String execute(Entity entity) {
      if (entity == null) {
         return "";
      } else if ((new Object() {
         public int getScore(String score, Entity _ent) {
            Scoreboard _sc = _ent.level().getScoreboard();
            Objective _so = _sc.getObjective(score);
            return _so != null ? _sc.getOrCreatePlayerScore(_ent.getScoreboardName(), _so).getScore() : 0;
         }
      }).getScore("anim", entity) == 1) {
         return "lunge";
      } else {
         return (new Object() {
            public int getScore(String score, Entity _ent) {
               Scoreboard _sc = _ent.level().getScoreboard();
               Objective _so = _sc.getObjective(score);
               return _so != null ? _sc.getOrCreatePlayerScore(_ent.getScoreboardName(), _so).getScore() : 0;
            }
         }).getScore("anim", entity) == 2 ? "roar" : "empty";
      }
   }
}
