package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.event.entity.living.LivingChangeTargetEvent;
import net.mcreator.boh.entity.WhitefaceEntity;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class TargetWhitefaceProcedure {
    @SubscribeEvent
    public void onEntitySetsAttackTarget(LivingChangeTargetEvent event) {
        execute(event, M.getOriginalTarget(event), M.getEntity(event));
    }

    public static void execute(Entity entity, Entity sourceentity) {
        execute(null, entity, sourceentity);
    }

    private static void execute(@Nullable Event event, Entity entity, Entity sourceentity) {
        if (entity != null && sourceentity != null) {
            if (entity instanceof EntityPlayer _playerHasItem
                && M.contains(M.getInventory(_playerHasItem), M.new_ItemStack(BohModItems.WHITEFACEHEART.get()))
                && sourceentity instanceof WhitefaceEntity) {
                if (sourceentity instanceof WhitefaceEntity animatable) {
                    animatable.setTexture("whiteface_anger");
                }
            } else if (sourceentity instanceof WhitefaceEntity animatable) {
                animatable.setTexture("whiteface");
            }
        }
    }
}
