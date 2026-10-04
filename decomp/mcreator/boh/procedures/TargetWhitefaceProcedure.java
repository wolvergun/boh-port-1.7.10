package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.entity.WhitefaceEntity;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class TargetWhitefaceProcedure {
   @SubscribeEvent
   public static void onEntitySetsAttackTarget(LivingChangeTargetEvent event) {
      execute(event, event.getOriginalTarget(), event.getEntity());
   }

   public static void execute(Entity entity, Entity sourceentity) {
      execute(null, entity, sourceentity);
   }

   private static void execute(@Nullable Event event, Entity entity, Entity sourceentity) {
      if (entity != null && sourceentity != null) {
         if (entity instanceof Player _playerHasItem
            && _playerHasItem.getInventory().contains(new ItemStack((ItemLike)BohModItems.WHITEFACEHEART.get()))
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
