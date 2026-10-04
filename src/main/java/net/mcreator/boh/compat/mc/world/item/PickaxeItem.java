package net.mcreator.boh.compat.mc.world.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.mcreator.boh.compat.item.BohItem;
import net.mcreator.boh.compat.mc.world.entity.EquipmentSlot;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Attributes;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.item.ItemStack;

public class PickaxeItem extends BohItem {
    protected final Tier tier;
    private final float attackDamage;

    public PickaxeItem(Tier tier, int damage, float speed, Properties props) {
        super(props.durability(tier.getUses()));
        this.tier = tier;
        this.attackDamage = damage + tier.getAttackDamageBonus();
        this.setHarvestLevel("pickaxe", tier.getLevel());
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        Material m = state.getBlock().getMaterial();
        return m != Material.rock && m != Material.iron && m != Material.anvil ? 1.0F : this.tier.getSpeed();
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState state) {
        return state.getBlock().getHarvestLevel(state.meta()) <= this.tier.getLevel();
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        stack.damageItem(2, attacker);
        return true;
    }

    @Override
    public int getEnchantmentValue() {
        return this.tier.getEnchantmentValue();
    }

    @Override
    public Multimap<IAttribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        Multimap<IAttribute, AttributeModifier> m = HashMultimap.create();
        if (slot == EquipmentSlot.MAINHAND) {
            m.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(field_111210_e, "Tool modifier", this.attackDamage, 0));
        }

        return m;
    }

    public boolean isFull3D() {
        return true;
    }
}
