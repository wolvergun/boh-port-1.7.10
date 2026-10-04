package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModItems;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelAccessor;

public class BookSimonEntityDiesProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         Entity _ent = entity;
         if (!_ent.level().isClientSide() && _ent.getServer() != null) {
            _ent.getServer()
               .getCommands()
               .performPrefixedCommand(
                  new CommandSourceStack(
                     CommandSource.NULL,
                     _ent.position(),
                     _ent.getRotationVector(),
                     _ent.level() instanceof ServerLevel ? (ServerLevel)_ent.level() : null,
                     4,
                     _ent.getName().getString(),
                     _ent.getDisplayName(),
                     _ent.level().getServer(),
                     _ent
                  ),
                  "/stopsound @a music boh:simon_ost"
               );
         }

         if (Math.random() < 0.3 && world instanceof ServerLevel _level) {
            ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack((ItemLike)BohModItems.SIMONS_BOOK.get()));
            entityToSpawn.setPickUpDelay(10);
            _level.addFreshEntity(entityToSpawn);
         }
      }
   }
}
