package net.mcreator.boh.item;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.InteractionResult;
import net.mcreator.boh.compat.mc.world.InteractionResultHolder;
import net.mcreator.boh.compat.mc.world.item.PickaxeItem;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.Tier;
import net.mcreator.boh.compat.mc.world.item.context.UseOnContext;
import net.mcreator.boh.compat.mc.world.item.crafting.Ingredient;
import net.mcreator.boh.procedures.PartyPopperRightclickedOnBlockProcedure;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PartyPopperItem extends PickaxeItem {
    public PartyPopperItem() {
        super(new Tier() {
            @Override
            public int getUses() {
                return 0;
            }

            @Override
            public float getSpeed() {
                return 0.0F;
            }

            @Override
            public float getAttackDamageBonus() {
                return -2.0F;
            }

            @Override
            public int getLevel() {
                return 0;
            }

            @Override
            public int getEnchantmentValue() {
                return 0;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }
        }, 1, -4.0F, new Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(World world, EntityPlayer entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        PartyPopperRightclickedOnBlockProcedure.execute(world, M.getX(entity), M.getY(entity), M.getZ(entity), entity, M.getObject(ar));
        return ar;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        super.useOn(context);
        PartyPopperRightclickedOnBlockProcedure.execute(
            M.getLevel(context),
            M.getX(M.getClickedPos(context)),
            M.getY(M.getClickedPos(context)),
            M.getZ(M.getClickedPos(context)),
            M.getPlayer(context),
            M.getItemInHand(context)
        );
        return InteractionResult.SUCCESS;
    }
}
