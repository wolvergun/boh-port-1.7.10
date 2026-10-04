package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.event.entity.living.LivingChangeTargetEvent;
import net.mcreator.boh.entity.SixEntity;
import net.minecraft.entity.Entity;

public class SixTargetingProcedure {
    @SubscribeEvent
    public void onEntitySetsAttackTarget(LivingChangeTargetEvent event) {
        execute(event, M.getEntity(event));
    }

    public static void execute(Entity sourceentity) {
        execute(null, sourceentity);
    }

    private static void execute(@Nullable Event event, Entity sourceentity) {
        if (sourceentity != null) {
            if (sourceentity instanceof SixEntity) {
                if (sourceentity instanceof SixEntity animatable) {
                    animatable.setTexture("six_happy");
                }
            } else if (sourceentity instanceof SixEntity animatable) {
                animatable.setTexture("six");
            }
        }
    }
}
