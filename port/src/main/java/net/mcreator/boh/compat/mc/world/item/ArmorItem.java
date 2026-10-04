package net.mcreator.boh.compat.mc.world.item;

import net.mcreator.boh.compat.item.BohItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;

/** 1.20 ArmorItem as a 1.7.10 ISpecialArmor (armor points from the material, any slot type). */
public class ArmorItem extends BohItem implements ISpecialArmor {

    protected final ArmorMaterial material;
    protected final Type type;

    public ArmorItem(ArmorMaterial material, Type type, Properties props) {
        super(props.durability(material.getDurabilityForType(type)));
        this.material = material;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public ArmorMaterial getMaterial() {
        return material;
    }

    public int getDefense() {
        return material.getDefenseForType(type);
    }

    @Override
    public boolean isValidArmor(ItemStack stack, int armorType, Entity entity) {
        return armorType == type.legacyArmorType();
    }

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
        if (source.isUnblockable()) return new ArmorProperties(0, 0, 0);
        return new ArmorProperties(0, getDefense() / 25.0, Integer.MAX_VALUE);
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
        return getDefense();
    }

    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
        stack.damageItem(damage, entity);
    }

    @Override
    public int getEnchantmentValue() {
        return material.getEnchantmentValue();
    }

    private static Object defaultArmorModel;

    /** 1.20 custom armor models (GeoArmorRenderer) through IClientItemExtensions. */
    @Override
    @cpw.mods.fml.relauncher.SideOnly(cpw.mods.fml.relauncher.Side.CLIENT)
    public net.minecraft.client.model.ModelBiped getArmorModel(net.minecraft.entity.EntityLivingBase entity, ItemStack stack, int slot) {
        net.mcreator.boh.compat.forge.client.extensions.common.IClientItemExtensions ext = net.mcreator.boh.compat.client.ItemExtensions.of(this);
        if (ext == net.mcreator.boh.compat.forge.client.extensions.common.IClientItemExtensions.DEFAULT) return null;
        if (defaultArmorModel == null) defaultArmorModel = new net.mcreator.boh.compat.mc.client.model.HumanoidModel<Object>(1.0F);
        net.mcreator.boh.compat.mc.world.entity.EquipmentSlot s = slot == 0 ? net.mcreator.boh.compat.mc.world.entity.EquipmentSlot.HEAD
            : slot == 1 ? net.mcreator.boh.compat.mc.world.entity.EquipmentSlot.CHEST : slot == 2 ? net.mcreator.boh.compat.mc.world.entity.EquipmentSlot.LEGS
                : net.mcreator.boh.compat.mc.world.entity.EquipmentSlot.FEET;
        net.mcreator.boh.compat.mc.client.model.HumanoidModel<?> m = ext.getHumanoidArmorModel(entity, stack, s,
            (net.mcreator.boh.compat.mc.client.model.HumanoidModel<?>) defaultArmorModel);
        return m == defaultArmorModel ? null : m;
    }

    /** 1.20 signature; the mod's armor items override this. */
    public String getArmorTexture(ItemStack stack, Entity entity, net.mcreator.boh.compat.mc.world.entity.EquipmentSlot slot, String type) {
        return null;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        net.mcreator.boh.compat.mc.world.entity.EquipmentSlot s = slot == 0 ? net.mcreator.boh.compat.mc.world.entity.EquipmentSlot.HEAD
            : slot == 1 ? net.mcreator.boh.compat.mc.world.entity.EquipmentSlot.CHEST : slot == 2 ? net.mcreator.boh.compat.mc.world.entity.EquipmentSlot.LEGS
                : net.mcreator.boh.compat.mc.world.entity.EquipmentSlot.FEET;
        return getArmorTexture(stack, entity, s, type);
    }
}
