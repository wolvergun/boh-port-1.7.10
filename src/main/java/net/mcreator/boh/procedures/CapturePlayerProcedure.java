package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.entity.StiltwalkerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class CapturePlayerProcedure {
    @SubscribeEvent
    public void onEntityAttacked(LivingHurtEvent event) {
        if (event != null && M.getEntity(event) != null) {
            execute(event, M.getEntity(event), M.getEntity(M.getSource(event)));
        }
    }

    public static void execute(Entity entity, Entity sourceentity) {
        execute(null, entity, sourceentity);
    }

    private static void execute(@Nullable Event event, Entity entity, Entity sourceentity) {
        if (entity != null && sourceentity != null && sourceentity instanceof StiltwalkerEntity && entity instanceof EntityPlayer) {
            M.startRiding(entity, sourceentity);
        }
    }
}
