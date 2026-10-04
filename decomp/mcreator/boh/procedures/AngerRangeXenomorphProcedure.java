package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class AngerRangeXenomorphProcedure {
   public static boolean execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      return entity == null
         ? false
         : !world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 20.0, 20.0, 20.0), e -> true).isEmpty()
            || ((entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof LivingEntity _entGetArmor
                     ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD)
                     : ItemStack.EMPTY)
                  .getItem()
               != BohModItems.FACEHUGGER_FACE.get();
   }
}
