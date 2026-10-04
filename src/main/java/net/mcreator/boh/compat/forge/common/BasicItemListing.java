package net.mcreator.boh.compat.forge.common;

import net.mcreator.boh.compat.M;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;

public class BasicItemListing {
    public final ItemStack price;
    public final ItemStack price2;
    public final ItemStack forSale;
    public final int maxTrades;
    public final int xp;
    public final float priceMult;

    public BasicItemListing(ItemStack price, ItemStack forSale, int maxTrades, int xp, float priceMult) {
        this(price, M.EMPTY, forSale, maxTrades, xp, priceMult);
    }

    public BasicItemListing(ItemStack price, ItemStack price2, ItemStack forSale, int maxTrades, int xp, float priceMult) {
        this.price = price;
        this.price2 = price2;
        this.forSale = forSale;
        this.maxTrades = maxTrades;
        this.xp = xp;
        this.priceMult = priceMult;
    }

    public MerchantRecipe toRecipe() {
        ItemStack b = M.legacy(this.price2);
        MerchantRecipe r = b == null
            ? new MerchantRecipe(M.legacy(this.price).copy(), M.legacy(this.forSale).copy())
            : new MerchantRecipe(M.legacy(this.price).copy(), b.copy(), M.legacy(this.forSale).copy());
        r.func_82783_a(this.maxTrades - 7);
        return r;
    }
}
