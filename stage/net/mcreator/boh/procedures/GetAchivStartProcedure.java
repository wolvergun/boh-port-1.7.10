package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.compat.mc.advancements.Advancement;
import net.mcreator.boh.compat.mc.advancements.AdvancementProgress;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.Entity;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;

public class GetAchivStartProcedure {

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
        execute(event, M.getEntity(event));
    }

    public static void execute(Entity entity) {
        execute(null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        if (entity != null) {
            if (entity instanceof EntityPlayerMP _player) {
                Advancement _adv = M.getAdvancement(M.getAdvancements(M.server(_player)), new ResourceLocation("boh:start_boh"));
                AdvancementProgress _ap = M.getOrStartProgress(M.getAdvancements(_player), _adv);
                if (!M.isDone(_ap)) {
                    for (String criteria : M.getRemainingCriteria(_ap)) {
                        M.award(M.getAdvancements(_player), _adv, criteria);
                    }
                }
            }
        }
    }
}
