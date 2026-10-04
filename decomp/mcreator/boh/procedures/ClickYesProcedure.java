package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModItems;
import net.mcreator.boh.network.BohModVariables;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.items.ItemHandlerHelper;

public class ClickYesProcedure {
   public static void execute(LevelAccessor world, Entity entity) {
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
                  "/particle minecraft:block redstone_block ~ ~1 ~ .1 .1 .1 2 30"
               );
         }

         _ent = entity;
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
                  "/playsound boh:whiteface_scream master @a"
               );
         }

         if (entity instanceof Player _player) {
            ItemStack _stktoremove = new ItemStack((ItemLike)BohModItems.WHITEFACEHEART.get());
            _player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 999, _player.inventoryMenu.getCraftSlots());
         }

         if (entity instanceof Player _player) {
            ItemStack _setstack = new ItemStack((ItemLike)BohModItems.WF_PISTOL.get()).copy();
            _setstack.setCount(1);
            ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
         }

         if (entity instanceof Player _player) {
            _player.closeContainer();
         }

         BohModVariables.MapVariables.get(world).Kill_WF = 1.0;
         BohModVariables.MapVariables.get(world).syncData(world);
      }
   }
}
