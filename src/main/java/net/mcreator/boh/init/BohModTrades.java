package net.mcreator.boh.init;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.common.BasicItemListing;
import net.mcreator.boh.compat.forge.event.village.VillagerTradesEvent;
import net.mcreator.boh.compat.mc.world.item.Items;

public class BohModTrades {
    @SubscribeEvent
    public void registerTrades(VillagerTradesEvent event) {
        if (M.getType(event) == BohModVillagerProfessions.MERCHANT.get()) {
            M.getTrades(event)
                .get(4)
                .add(new BasicItemListing(M.new_ItemStack(BohModItems.SPINEL.get(), 8), M.new_ItemStack(BohModItems.CRUCIFIXITEM.get()), 10, 5, 0.05F));
        }

        if (M.getType(event) == BohModVillagerProfessions.MERCHANT.get()) {
            M.getTrades(event)
                .get(5)
                .add(new BasicItemListing(M.new_ItemStack(BohModItems.SPINEL.get(), 32), M.new_ItemStack(BohModItems.POLAROID.get()), 10, 5, 0.05F));
        }

        if (M.getType(event) == BohModVillagerProfessions.MERCHANT.get()) {
            M.getTrades(event).get(2).add(new BasicItemListing(M.new_ItemStack(BohModItems.SPINEL.get(), 2), M.new_ItemStack(Items.EMERALD), 10, 5, 0.05F));
        }

        if (M.getType(event) == BohModVillagerProfessions.MERCHANT.get()) {
            M.getTrades(event)
                .get(2)
                .add(new BasicItemListing(M.new_ItemStack(BohModItems.SPINEL.get(), 2), M.new_ItemStack(Items.AMETHYST_SHARD), 10, 5, 0.05F));
        }

        if (M.getType(event) == BohModVillagerProfessions.MERCHANT.get()) {
            M.getTrades(event).get(2).add(new BasicItemListing(M.new_ItemStack(BohModItems.SPINEL.get(), 4), M.new_ItemStack(Items.DIAMOND), 10, 5, 0.05F));
        }

        if (M.getType(event) == BohModVillagerProfessions.MERCHANT.get()) {
            M.getTrades(event).get(1).add(new BasicItemListing(M.new_ItemStack(BohModItems.SPINEL.get(), 2), M.new_ItemStack(Items.QUARTZ, 8), 10, 5, 0.05F));
        }

        if (M.getType(event) == BohModVillagerProfessions.MERCHANT.get()) {
            M.getTrades(event).get(1).add(new BasicItemListing(M.new_ItemStack(BohModItems.SPINEL.get()), M.new_ItemStack(Items.ENDER_PEARL), 10, 5, 0.05F));
        }
    }
}
