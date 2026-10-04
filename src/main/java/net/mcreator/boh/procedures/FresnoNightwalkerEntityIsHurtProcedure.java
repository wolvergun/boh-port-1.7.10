package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.FresnoNightcrawlerEntity;
import net.minecraft.entity.Entity;

public class FresnoNightwalkerEntityIsHurtProcedure {
    public static void execute(Entity entity) {
        if (entity != null && entity instanceof FresnoNightcrawlerEntity) {
            ((FresnoNightcrawlerEntity)entity).setAnimation("hurt");
        }
    }
}
