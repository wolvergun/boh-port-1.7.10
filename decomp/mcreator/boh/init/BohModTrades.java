package net.mcreator.boh.init;

import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE)
public class BohModTrades {
   @SubscribeEvent
   public static void registerTrades(VillagerTradesEvent event) {
      if (event.getType() == BohModVillagerProfessions.MERCHANT.get()) {
         ((List)event.getTrades().get(4))
            .add(
               new BasicItemListing(new ItemStack((ItemLike)BohModItems.SPINEL.get(), 8), new ItemStack((ItemLike)BohModItems.CRUCIFIXITEM.get()), 10, 5, 0.05F)
            );
      }

      if (event.getType() == BohModVillagerProfessions.MERCHANT.get()) {
         ((List)event.getTrades().get(5))
            .add(new BasicItemListing(new ItemStack((ItemLike)BohModItems.SPINEL.get(), 32), new ItemStack((ItemLike)BohModItems.POLAROID.get()), 10, 5, 0.05F));
      }

      if (event.getType() == BohModVillagerProfessions.MERCHANT.get()) {
         ((List)event.getTrades().get(2))
            .add(new BasicItemListing(new ItemStack((ItemLike)BohModItems.SPINEL.get(), 2), new ItemStack(Items.EMERALD), 10, 5, 0.05F));
      }

      if (event.getType() == BohModVillagerProfessions.MERCHANT.get()) {
         ((List)event.getTrades().get(2))
            .add(new BasicItemListing(new ItemStack((ItemLike)BohModItems.SPINEL.get(), 2), new ItemStack(Items.AMETHYST_SHARD), 10, 5, 0.05F));
      }

      if (event.getType() == BohModVillagerProfessions.MERCHANT.get()) {
         ((List)event.getTrades().get(2))
            .add(new BasicItemListing(new ItemStack((ItemLike)BohModItems.SPINEL.get(), 4), new ItemStack(Items.DIAMOND), 10, 5, 0.05F));
      }

      if (event.getType() == BohModVillagerProfessions.MERCHANT.get()) {
         ((List)event.getTrades().get(1))
            .add(new BasicItemListing(new ItemStack((ItemLike)BohModItems.SPINEL.get(), 2), new ItemStack(Items.QUARTZ, 8), 10, 5, 0.05F));
      }

      if (event.getType() == BohModVillagerProfessions.MERCHANT.get()) {
         ((List)event.getTrades().get(1))
            .add(new BasicItemListing(new ItemStack((ItemLike)BohModItems.SPINEL.get()), new ItemStack(Items.ENDER_PEARL), 10, 5, 0.05F));
      }
   }
}
