package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.compat.mc.advancements.Advancement;
import net.mcreator.boh.compat.mc.advancements.AdvancementProgress;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.mcreator.boh.compat.mc.tags.ItemTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;

public class GiveAchievementFirstDocProcedure {

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent event) {
        if (event.phase == Phase.END) {
            execute(event, M.player(event));
        }
    }

    public static void execute(Entity entity) {
        execute(null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        if (entity != null) {
            if (!(entity instanceof EntityPlayerMP _plr0 && M.level(_plr0) instanceof WorldServer && M.isDone(M.getOrStartProgress(M.getAdvancements(_plr0), M.getAdvancement(M.getAdvancements(M.server(_plr0)), new ResourceLocation("boh:obtain_first_document_achievement"))))) && M.is((entity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY), ItemTags.create(new ResourceLocation("boh:documents"))) && entity instanceof EntityPlayerMP _player) {
                Advancement _adv = M.getAdvancement(M.getAdvancements(M.server(_player)), new ResourceLocation("boh:obtain_first_document_achievement"));
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
