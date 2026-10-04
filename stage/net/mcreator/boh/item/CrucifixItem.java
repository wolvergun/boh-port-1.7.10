package net.mcreator.boh.item;

import java.util.List;
import net.mcreator.boh.entity.CrucifixProjectileEntity;
import net.mcreator.boh.procedures.CrucifixCanUseRangedItemProcedure;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.entity.player.EntityPlayerMP;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.InteractionResultHolder;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.mcreator.boh.compat.mc.world.entity.projectile.Pickup;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.mc.world.item.ProjectileWeaponItem;
import net.mcreator.boh.compat.mc.world.item.Rarity;
import net.mcreator.boh.compat.mc.world.item.TooltipFlag;
import net.mcreator.boh.compat.mc.world.item.UseAnim;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.item.BohItem;
import net.mcreator.boh.compat.M;

public class CrucifixItem extends BohItem {

    public CrucifixItem() {
        super(new Properties().durability(32).rarity(Rarity.COMMON));
    }

    public UseAnim getUseAnimation(ItemStack itemstack) {
        return UseAnim.EAT;
    }

    public int getUseDuration(ItemStack itemstack) {
        return 72000;
    }

    public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
        return 0.0F;
    }

    public void appendHoverText(ItemStack itemstack, World level, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, level, list, flag);
        list.add(Component.translatable("item.boh.crucifix.description_0"));
    }

    public InteractionResultHolder<ItemStack> use(World world, EntityPlayer entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = InteractionResultHolder.fail(M.getItemInHand(entity, hand));
        if (M.instabuild(M.getAbilities(entity)) || this.findAmmo(entity) != M.EMPTY) {
            ar = InteractionResultHolder.success(M.getItemInHand(entity, hand));
            M.startUsingItem(entity, hand);
        }
        return ar;
    }

    public void onUseTick(World world, EntityLivingBase entity, ItemStack itemstack, int count) {
        if (!M.isClientSide(world) && entity instanceof EntityPlayerMP player) {
            ItemStack stack = this.findAmmo(player);
            if (M.instabuild(M.getAbilities(player)) || stack != M.EMPTY) {
                CrucifixProjectileEntity projectile = CrucifixProjectileEntity.shoot(world, entity, M.getRandom(world));
                M.hurtAndBreak(itemstack, 1, entity, e -> M.broadcastBreakEvent(e, M.getUsedItemHand(entity)));
                if (M.instabuild(M.getAbilities(player))) {
                    M.set_pickup(projectile, Pickup.CREATIVE_ONLY);
                } else if (M.isDamageableItem(stack)) {
                    if (M.hurt(stack, 1, M.getRandom(world), player)) {
                        M.shrink(stack, 1);
                        M.setDamageValue(stack, 0);
                        if (M.isEmpty(stack)) {
                            M.removeItem(M.getInventory(player), stack);
                        }
                    }
                } else {
                    M.shrink(stack, 1);
                    if (M.isEmpty(stack)) {
                        M.removeItem(M.getInventory(player), stack);
                    }
                }
                CrucifixCanUseRangedItemProcedure.execute(world, entity, itemstack);
            }
            M.releaseUsingItem(entity);
        }
    }

    private ItemStack findAmmo(EntityPlayer player) {
        ItemStack stack = ProjectileWeaponItem.getHeldProjectile(player, e -> M.getItem(e) == M.getItem(CrucifixProjectileEntity.PROJECTILE_ITEM));
        if (stack == M.EMPTY) {
            for (int i = 0; i < M.items(M.getInventory(player)).size(); i++) {
                ItemStack teststack = (ItemStack) M.items(M.getInventory(player)).get(i);
                if (teststack != null && M.getItem(teststack) == M.getItem(CrucifixProjectileEntity.PROJECTILE_ITEM)) {
                    stack = teststack;
                    break;
                }
            }
        }
        return stack;
    }
}
