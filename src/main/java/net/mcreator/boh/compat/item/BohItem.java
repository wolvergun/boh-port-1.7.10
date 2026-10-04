package net.mcreator.boh.compat.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.client.ItemModels;
import net.mcreator.boh.compat.forge.client.extensions.common.IClientItemExtensions;
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
import net.minecraft.entity.ai.attributes.BaseAttribute;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class BohItem extends Item {
    protected static final UUID BASE_ATTACK_DAMAGE_UUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    protected static final UUID BASE_ATTACK_SPEED_UUID = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");
    protected final Properties properties;
    protected ResourceLocation registryName;

    public BohItem(Properties properties) {
        this.properties = properties == null ? new Properties() : properties;
        this.setMaxStackSize(this.properties.maxStackSize);
        if (this.properties.maxDamage > 0) {
            this.setMaxDamage(this.properties.maxDamage);
        }

        if (!this.properties.canRepair) {
            this.setNoRepair();
        }

        if (this.properties.craftingRemainder != null) {
            this.setContainerItem(this.properties.craftingRemainder);
        }
    }

    public void onRegistered(ResourceLocation id) {
        this.registryName = id;
        this.setTextureName(ItemModels.iconFor(id));
    }

    public ResourceLocation registryName() {
        return this.registryName;
    }

    public FoodProperties food() {
        return this.properties.food;
    }

    public InteractionResultHolder<ItemStack> use(World world, EntityPlayer player, InteractionHand hand) {
        ItemStack stack = M.getItemInHand(player, hand);
        if (this.properties.food != null) {
            if (player.canEat(this.properties.food.canAlwaysEat())) {
                M.startUsingItem(player, hand);
                return InteractionResultHolder.consume(stack);
            } else {
                return InteractionResultHolder.fail(stack);
            }
        } else {
            return InteractionResultHolder.pass(stack);
        }
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

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
    }

    public Multimap<IAttribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        return HashMultimap.create();
    }

    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return 1.0F;
    }

    public int getUseDuration(ItemStack stack) {
        if (this.properties.food != null) {
            return this.properties.food.isFastFood() ? 16 : 32;
        } else {
            return 0;
        }
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return this.properties.food != null ? UseAnim.EAT : UseAnim.NONE;
    }

    public void appendHoverText(ItemStack stack, World world, List<Component> list, TooltipFlag flag) {
    }

    public int getEnchantmentValue() {
        return 0;
    }

    public boolean onEntitySwing(ItemStack stack, EntityLivingBase entity) {
        return false;
    }

    public void releaseUsing(ItemStack stack, World world, EntityLivingBase entity, int timeLeft) {
    }

    public ItemStack finishUsingItem(ItemStack stack, World world, EntityLivingBase entity) {
        if (this.properties.food != null && entity instanceof EntityPlayer p) {
            p.getFoodStats().addStats(this.properties.food.getNutrition(), this.properties.food.getSaturationModifier());
            world.playSoundAtEntity(p, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
            if (!world.isRemote) {
                for (Object[] e : this.properties.food.getEffects()) {
                    Supplier<PotionEffect> s = (Supplier<PotionEffect>)e[0];
                    if (world.rand.nextFloat() < (Float)e[1]) {
                        p.addPotionEffect(s.get());
                    }
                }
            }

            if (!p.capabilities.isCreativeMode) {
                stack.stackSize--;
            }
        }

        return M.stack(stack);
    }

    public void onUseTick(World world, EntityLivingBase entity, ItemStack stack, int count) {
    }

    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return true;
    }

    public boolean isFoil(ItemStack stack) {
        return stack.isItemEnchanted();
    }

    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
    }

    public boolean canAttackBlock(BlockState state, World world, BlockPos pos, EntityPlayer player) {
        return true;
    }

    public boolean isCorrectToolForDrops(BlockState state) {
        return false;
    }

    public boolean isEnchantable(ItemStack stack) {
        return this.getEnchantmentValue() > 0 && this.getItemStackLimit(stack) == 1;
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        InteractionResultHolder<ItemStack> r = this.use(world, player, InteractionHand.MAIN_HAND);
        ItemStack out = r == null ? stack : r.getObject();
        return M.legacy(out) == null && out != stack ? null : (out == M.EMPTY ? null : out);
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hx, float hy, float hz) {
        UseOnContext ctx = new UseOnContext(
            world, player, InteractionHand.MAIN_HAND, stack, new BlockPos(x, y, z), Direction.from3DDataValue(side), new Vec3(x + hx, y + hy, z + hz)
        );
        InteractionResult r = this.useOn(ctx);
        return r != null && r.consumesAction();
    }

    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target) {
        InteractionResult r = this.interactLivingEntity(stack, player, target, InteractionHand.MAIN_HAND);
        return r != null && r.consumesAction();
    }

    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        return this.hurtEnemy(stack, target, attacker);
    }

    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase miner) {
        return this.mineBlock(stack, world, BlockState.of(block, world.getBlockMetadata(x, y, z)), new BlockPos(x, y, z), miner);
    }

    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        this.inventoryTick(stack, world, entity, slot, selected);
    }

    public Multimap getAttributeModifiers(ItemStack stack) {
        Multimap out = super.getAttributeModifiers(stack);
        Multimap<IAttribute, AttributeModifier> mods = this.getDefaultAttributeModifiers(EquipmentSlot.MAINHAND);

        for (Entry<IAttribute, AttributeModifier> e : mods.entries()) {
            if (!(e.getKey() instanceof BaseAttribute) || e.getKey().getAttributeUnlocalizedName().startsWith("generic.")) {
                out.put(e.getKey().getAttributeUnlocalizedName(), e.getValue());
            }
        }

        return out;
    }

    public float getDigSpeed(ItemStack stack, Block block, int meta) {
        return this.getDestroySpeed(stack, BlockState.of(block, meta));
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return this.getUseDuration(stack);
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        UseAnim a = this.getUseAnimation(stack);
        return a == null ? EnumAction.none : a.toVanilla();
    }

    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
        List<Component> comps = new ArrayList<>();
        this.appendHoverText(stack, player == null ? null : player.worldObj, comps, advanced ? TooltipFlag.ADVANCED : TooltipFlag.NORMAL);

        for (Component c : comps) {
            list.add(c.getFormattedText());
        }
    }

    public int getItemEnchantability() {
        return this.getEnchantmentValue();
    }

    public boolean onEntitySwing(EntityLivingBase entity, ItemStack stack) {
        return this.onEntitySwing(stack, entity);
    }

    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int timeLeft) {
        this.releaseUsing(stack, world, player, timeLeft);
    }

    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
        return M.legacy(this.finishUsingItem(stack, world, player));
    }

    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        this.onUseTick(player.worldObj, player, stack, count);
    }

    public EnumRarity getRarity(ItemStack stack) {
        return this.properties.rarity.toVanilla();
    }

    public boolean hasEffect(ItemStack stack, int pass) {
        return this.isFoil(stack);
    }

    public boolean canHarvestBlock(Block block, ItemStack stack) {
        return this.isCorrectToolForDrops(BlockState.of(block, 0));
    }

    public boolean isItemTool(ItemStack stack) {
        return this.isEnchantable(stack);
    }

    public void registerIcons(IIconRegister reg) {
        this.itemIcon = reg.registerIcon(this.getIconString());
    }
}
