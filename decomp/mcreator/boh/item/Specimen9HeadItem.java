package net.mcreator.boh.item;

import net.mcreator.boh.procedures.Specimen9HeadRightclickedOnBlockProcedure;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;

public class Specimen9HeadItem extends Item {
   public Specimen9HeadItem() {
      super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
   }

   public InteractionResult useOn(UseOnContext context) {
      super.useOn(context);
      Specimen9HeadRightclickedOnBlockProcedure.execute(
         context.getLevel(),
         context.getClickedPos().getX(),
         context.getClickedPos().getY(),
         context.getClickedPos().getZ(),
         context.getPlayer(),
         context.getItemInHand()
      );
      return InteractionResult.SUCCESS;
   }
}
