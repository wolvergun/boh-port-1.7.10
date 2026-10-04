package net.mcreator.boh.compat.item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.client.ItemModels;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.InteractionResult;
import net.mcreator.boh.compat.mc.world.InteractionResultHolder;
import net.mcreator.boh.compat.mc.world.entity.EquipmentSlot;
import net.mcreator.boh.compat.mc.world.food.FoodProperties;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.TooltipFlag;
import net.mcreator.boh.compat.mc.world.item.UseAnim;
import net.mcreator.boh.compat.mc.world.item.context.UseOnContext;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/** Compat base for mod items: 1.20 Item hooks bridged onto the 1.7.10 Item callbacks. */
public class BohItem extends Item {

    protected static final java.util.UUID BASE_ATTACK_DAMAGE_UUID = java.util.UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    protected static final java.util.UUID BASE_ATTACK_SPEED_UUID = java.util.UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");

    protected final Properties properties;
    protected ResourceLocation registryName;

    public BohItem(Properties properties) {
        this.properties = properties == null ? new Properties() : properties;
        setMaxStackSize(this.properties.maxStackSize);
        if (this.properties.maxDamage > 0) setMaxDamage(this.properties.maxDamage);
        if (!this.properties.canRepair) setNoRepair();
        if (this.properties.craftingRemainder != null) setContainerItem(this.properties.craftingRemainder);
    }

    public void onRegistered(ResourceLocation id) {
        registryName = id;
        setTextureName(ItemModels.iconFor(id));
    }

    public ResourceLocation registryName() {
        return registryName;
    }

    public FoodProperties food() {
        return properties.food;
    }

    // ------------------------------------------------------------------ 1.20 hooks

    public InteractionResultHolder<ItemStack> use(World world, EntityPlayer player, InteractionHand hand) {
        ItemStack stack = M.getItemInHand(player, hand);
        if (properties.food != null) {
            if (player.canEat(properties.food.canAlwaysEat())) {
                M.startUsingItem(player, hand);
                return InteractionResultHolder.consume(stack);
            }
            return InteractionResultHolder.fail(stack);
        }
        return InteractionResultHolder.pass(stack);
    }

    public InteractionResult useOn(UseOnContext context) {
        return InteractionResult.PASS;
    }

    public InteractionResult interactLivingEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, InteractionHand hand) {
        return InteractionResult.PASS;
    }

    public boolean hurtEnemy(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        return false;
    }

    public boolean mineBlock(ItemStack stack, World world, BlockState state, BlockPos pos, EntityLivingBase miner) {
        return false;
    }

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {}

    public Multimap<IAttribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        return HashMultimap.create();
    }

    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return 1.0F;
    }

    public int getUseDuration(ItemStack stack) {
        if (properties.food != null) return properties.food.isFastFood() ? 16 : 32;
        return 0;
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return properties.food != null ? UseAnim.EAT : UseAnim.NONE;
    }

    public void appendHoverText(ItemStack stack, World world, List<Component> list, TooltipFlag flag) {}

    public int getEnchantmentValue() {
        return 0;
    }

    public boolean onEntitySwing(ItemStack stack, EntityLivingBase entity) {
        return false;
    }

    public void releaseUsing(ItemStack stack, World world, EntityLivingBase entity, int timeLeft) {}

    public ItemStack finishUsingItem(ItemStack stack, World world, EntityLivingBase entity) {
        if (properties.food != null && entity instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer) entity;
            p.getFoodStats().addStats(properties.food.getNutrition(), properties.food.getSaturationModifier());
            world.playSoundAtEntity(p, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
            if (!world.isRemote) for (Object[] e : properties.food.getEffects()) {
                @SuppressWarnings("unchecked")
                Supplier<PotionEffect> s = (Supplier<PotionEffect>) e[0];
                if (world.rand.nextFloat() < (Float) e[1]) p.addPotionEffect(s.get());
            }
            if (!p.capabilities.isCreativeMode) stack.stackSize--;
        }
        return M.stack(stack);
    }

    public void onUseTick(World world, EntityLivingBase entity, ItemStack stack, int count) {}

    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return true;
    }

    public boolean isFoil(ItemStack stack) {
        return stack.isItemEnchanted();
    }

    public void initializeClient(Consumer<net.mcreator.boh.compat.forge.client.extensions.common.IClientItemExtensions> consumer) {}

    public boolean canAttackBlock(BlockState state, World world, BlockPos pos, EntityPlayer player) {
        return true;
    }

    public boolean isCorrectToolForDrops(BlockState state) {
        return false;
    }

    public boolean isEnchantable(ItemStack stack) {
        return getEnchantmentValue() > 0 && getItemStackLimit(stack) == 1;
    }

    // ------------------------------------------------------------------ 1.7.10 bridge

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        InteractionResultHolder<ItemStack> r = use(world, player, InteractionHand.MAIN_HAND);
        ItemStack out = r == null ? stack : r.getObject();
        return M.legacy(out) == null && out != stack ? null : out == M.EMPTY ? null : out;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hx, float hy,
        float hz) {
        UseOnContext ctx = new UseOnContext(world, player, InteractionHand.MAIN_HAND, stack, new BlockPos(x, y, z),
            Direction.from3DDataValue(side), new Vec3(x + hx, y + hy, z + hz));
        InteractionResult r = useOn(ctx);
        return r != null && r.consumesAction();
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target) {
        InteractionResult r = interactLivingEntity(stack, player, target, InteractionHand.MAIN_HAND);
        return r != null && r.consumesAction();
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        return hurtEnemy(stack, target, attacker);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase miner) {
        return mineBlock(stack, world, BlockState.of(block, world.getBlockMetadata(x, y, z)), new BlockPos(x, y, z), miner);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Multimap getAttributeModifiers(ItemStack stack) {
        Multimap out = super.getAttributeModifiers(stack);
        Multimap<IAttribute, AttributeModifier> mods = getDefaultAttributeModifiers(EquipmentSlot.MAINHAND);
        for (java.util.Map.Entry<IAttribute, AttributeModifier> e : mods.entries()) {
            // only attributes 1.7.10 players actually have (attack speed etc. do not exist)
            if (e.getKey() instanceof net.minecraft.entity.ai.attributes.BaseAttribute
                && !e.getKey().getAttributeUnlocalizedName().startsWith("generic.")) continue;
            out.put(e.getKey().getAttributeUnlocalizedName(), e.getValue());
        }
        return out;
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta) {
        return getDestroySpeed(stack, BlockState.of(block, meta));
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return getUseDuration(stack);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        UseAnim a = getUseAnimation(stack);
        return a == null ? EnumAction.none : a.toVanilla();
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
        List<Component> comps = new ArrayList<>();
        appendHoverText(stack, player == null ? null : player.worldObj, comps, advanced ? TooltipFlag.ADVANCED : TooltipFlag.NORMAL);
        for (Component c : comps) list.add(c.getFormattedText());
    }

    @Override
    public int getItemEnchantability() {
        return getEnchantmentValue();
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entity, ItemStack stack) {
        return onEntitySwing(stack, entity);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int timeLeft) {
        releaseUsing(stack, world, player, timeLeft);
    }

    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
        return M.legacy(finishUsingItem(stack, world, player));
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        onUseTick(player.worldObj, player, stack, count);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return properties.rarity.toVanilla();
    }

    @Override
    public boolean hasEffect(ItemStack stack, int pass) {
        return isFoil(stack);
    }

    @Override
    public boolean canHarvestBlock(Block block, ItemStack stack) {
        return isCorrectToolForDrops(BlockState.of(block, 0));
    }

    @Override
    public boolean isItemTool(ItemStack stack) {
        return isEnchantable(stack);
    }

    @Override
    public void registerIcons(IIconRegister reg) {
        itemIcon = reg.registerIcon(getIconString());
    }
}
