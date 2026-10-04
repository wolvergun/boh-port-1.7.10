package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.entity.KrasueEntity;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class KrasueHurtAttackEntityProcedure {
    @SubscribeEvent
    public void onEntityAttacked(LivingHurtEvent event) {
        if (event != null && M.getEntity(event) != null) {
            execute(event, M.getEntity(M.getSource(event)));
        }
    }

    public static void execute(Entity sourceentity) {
        execute(null, sourceentity);
    }

    private static void execute(@Nullable Event event, Entity sourceentity) {
        if (sourceentity != null && sourceentity instanceof KrasueEntity && sourceentity instanceof KrasueEntity) {
            ((KrasueEntity)sourceentity).setAnimation("attack");
        }
    }
}
