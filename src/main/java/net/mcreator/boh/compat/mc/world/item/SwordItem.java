package net.mcreator.boh.compat.mc.world.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.mcreator.boh.compat.item.BohItem;
import net.mcreator.boh.compat.mc.world.entity.EquipmentSlot;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Attributes;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class SwordItem extends BohItem {
    protected final Tier tier;
    private final float attackDamage;

    public SwordItem(Tier tier, int damage, float speed, Properties props) {
        super(props.durability(tier.getUses()));
        this.tier = tier;
        this.attackDamage = damage + tier.getAttackDamageBonus();
    }

    public Tier getTier() {
        return this.tier;
    }

    public float getDamage() {
        return this.attackDamage;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return state.getBlock() == Blocks.web ? 15.0F : 1.0F;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        stack.damageItem(1, attacker);
        return true;
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState state) {
        return state.getBlock() == Blocks.web;
    }

    @Override
    public int getEnchantmentValue() {
        return this.tier.getEnchantmentValue();
    }

    @Override
    public Multimap<IAttribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        Multimap<IAttribute, AttributeModifier> m = HashMultimap.create();
        if (slot == EquipmentSlot.MAINHAND) {
            m.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(field_111210_e, "Weapon modifier", this.attackDamage, 0));
        }

        return m;
    }

    public boolean isFull3D() {
        return true;
    }
}
