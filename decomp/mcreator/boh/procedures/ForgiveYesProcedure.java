package net.mcreator.boh.procedures;

import java.util.Map;
import java.util.function.Supplier;
import net.mcreator.boh.network.BohModVariables;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.LevelAccessor;

public class ForgiveYesProcedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity != null) {
         BohModVariables.MapVariables.get(world).Kill_WF = 0.0;
         BohModVariables.MapVariables.get(world).syncData(world);
         if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
            ((Slot)_slots.get(0)).remove(1);
            _player.containerMenu.broadcastChanges();
         }
      }
   }
}
