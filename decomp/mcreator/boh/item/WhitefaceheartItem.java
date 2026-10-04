package net.mcreator.boh.item;

import net.mcreator.boh.procedures.WhitefaceheartItemInInventoryTickProcedure;
import net.mcreator.boh.procedures.WhitefaceheartRightclickedOnBlockProcedure;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class WhitefaceheartItem extends Item {
   public WhitefaceheartItem() {
      super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
   }

   public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
      InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
      WhitefaceheartRightclickedOnBlockProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity);
      return ar;
   }

   public InteractionResult useOn(UseOnContext context) {
      super.useOn(context);
      WhitefaceheartRightclickedOnBlockProcedure.execute(
         context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), context.getPlayer()
      );
      return InteractionResult.SUCCESS;
   }

   public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
      super.inventoryTick(itemstack, world, entity, slot, selected);
      WhitefaceheartItemInInventoryTickProcedure.execute(entity);
   }
}
