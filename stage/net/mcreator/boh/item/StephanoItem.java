package net.mcreator.boh.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import net.mcreator.boh.procedures.StephanoItemInHandTickProcedure;
import net.mcreator.boh.procedures.StephanoLivingEntityIsHitWithItemProcedure;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EquipmentSlot;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Attributes;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Operation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.mc.world.item.Rarity;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.item.BohItem;
import net.mcreator.boh.compat.M;

public class StephanoItem extends BohItem {

    public StephanoItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
        return 2.0F;
    }

    public Multimap<IAttribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        if (equipmentSlot == EquipmentSlot.MAINHAND) {
            Builder<IAttribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(super.getDefaultAttributeModifiers(equipmentSlot));
            builder.put(Attributes.ATTACK_DAMAGE, M.new_AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", 2.0, Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, M.new_AttributeModifier(BASE_ATTACK_SPEED_UUID, "Item modifier", -2.4, Operation.ADDITION));
            return builder.build();
        } else {
            return super.getDefaultAttributeModifiers(equipmentSlot);
        }
    }

    public boolean hurtEnemy(ItemStack itemstack, EntityLivingBase entity, EntityLivingBase sourceentity) {
        boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
        StephanoLivingEntityIsHitWithItemProcedure.execute(M.level(entity), entity);
        return retval;
    }

    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        if (selected) {
            StephanoItemInHandTickProcedure.execute(entity);
        }
    }
}
