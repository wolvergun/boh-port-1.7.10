package net.mcreator.boh.compat.mc.world.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.mcreator.boh.compat.client.ItemExtensions;
import net.mcreator.boh.compat.forge.client.extensions.common.IClientItemExtensions;
import net.mcreator.boh.compat.item.BohItem;
import net.mcreator.boh.compat.mc.client.model.HumanoidModel;
import net.mcreator.boh.compat.mc.world.entity.EquipmentSlot;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;

public class ArmorItem extends BohItem implements ISpecialArmor {
    protected final ArmorMaterial material;
    protected final Type type;
    private static Object defaultArmorModel;

    public ArmorItem(ArmorMaterial material, Type type, Properties props) {
        super(props.durability(material.getDurabilityForType(type)));
        this.material = material;
        this.type = type;
    }

    public Type getType() {
        return this.type;
    }

    public ArmorMaterial getMaterial() {
        return this.material;
    }

    public int getDefense() {
        return this.material.getDefenseForType(this.type);
    }

    public boolean isValidArmor(ItemStack stack, int armorType, Entity entity) {
        return armorType == this.type.legacyArmorType();
    }

    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
        return source.isUnblockable() ? new ArmorProperties(0, 0.0, 0) : new ArmorProperties(0, this.getDefense() / 25.0, Integer.MAX_VALUE);
    }

    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
        return this.getDefense();
    }

    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
        stack.damageItem(damage, entity);
    }

    @Override
    public int getEnchantmentValue() {
        return this.material.getEnchantmentValue();
    }

    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entity, ItemStack stack, int slot) {
        IClientItemExtensions ext = ItemExtensions.of(this);
        if (ext == IClientItemExtensions.DEFAULT) {
            return null;
        } else {
            if (defaultArmorModel == null) {
                defaultArmorModel = new HumanoidModel(1.0F);
            }

            EquipmentSlot s = slot == 0 ? EquipmentSlot.HEAD : (slot == 1 ? EquipmentSlot.CHEST : (slot == 2 ? EquipmentSlot.LEGS : EquipmentSlot.FEET));
            HumanoidModel<?> m = ext.getHumanoidArmorModel(entity, stack, s, (HumanoidModel<?>)defaultArmorModel);
            return m == defaultArmorModel ? null : m;
        }
    }

    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return null;
    }

    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        EquipmentSlot s = slot == 0 ? EquipmentSlot.HEAD : (slot == 1 ? EquipmentSlot.CHEST : (slot == 2 ? EquipmentSlot.LEGS : EquipmentSlot.FEET));
        return this.getArmorTexture(stack, entity, s, type);
    }
}
