package net.mcreator.boh.item;

import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.SwordItem;
import net.mcreator.boh.compat.mc.world.item.Tier;
import net.mcreator.boh.compat.mc.world.item.crafting.Ingredient;

public class PlungerItem extends SwordItem {
    public PlungerItem() {
        super(new Tier() {
            @Override
            public int getUses() {
                return 750;
            }

            @Override
            public float getSpeed() {
                return 4.0F;
            }

            @Override
            public float getAttackDamageBonus() {
                return 2.5F;
            }

            @Override
            public int getLevel() {
                return 2;
            }

            @Override
            public int getEnchantmentValue() {
                return 14;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }
        }, 3, -2.8F, new Properties());
    }
}
