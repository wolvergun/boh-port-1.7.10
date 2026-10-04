package net.mcreator.boh.item;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.SwordItem;
import net.mcreator.boh.compat.mc.world.item.Tier;
import net.mcreator.boh.compat.mc.world.item.crafting.Ingredient;
import net.mcreator.boh.init.BohModItems;

public class SoulStealerItem extends SwordItem {
    public SoulStealerItem() {
        super(
            new Tier() {
                @Override
                public int getUses() {
                    return 25;
                }

                @Override
                public float getSpeed() {
                    return 4.0F;
                }

                @Override
                public float getAttackDamageBonus() {
                    return -2.0F;
                }

                @Override
                public int getLevel() {
                    return 2;
                }

                @Override
                public int getEnchantmentValue() {
                    return 2;
                }

                @Override
                public Ingredient getRepairIngredient() {
                    return Ingredient.of(
                        M.new_ItemStack(BohModItems.EXOTIC_SOUL.get()),
                        M.new_ItemStack(BohModItems.DEMONIC_SOUL.get()),
                        M.new_ItemStack(BohModItems.KILLERS_SOUL.get()),
                        M.new_ItemStack(BohModItems.MONSTROUS_SOUL.get())
                    );
                }
            },
            3,
            -2.7F,
            new Properties()
        );
    }
}
