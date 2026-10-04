package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;

public class InHeadFacehuggerProcedure {
   public static boolean execute(Entity entity) {
      return entity == null
         ? false
         : ((entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof LivingEntity _entGetArmor
                  ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD)
                  : ItemStack.EMPTY)
               .getItem()
            != BohModItems.FACEHUGGER_FACE.get();
   }
}
