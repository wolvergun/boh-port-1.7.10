package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.tags.TagKey;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;

public class EntityDismountBoatProcedure {

    @SubscribeEvent
    public void onEntityTick(LivingUpdateEvent event) {
        execute(event, M.getEntity(event));
    }

    public static void execute(Entity entity) {
        execute(null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        if (entity != null) {
            Entity ridingEntities = null;
            if (M.isPassenger(entity) && M.is(M.getType(entity), TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("boh:boh_anti_boating")))) {
                M.stopRiding(entity);
            }
        }
    }
}
