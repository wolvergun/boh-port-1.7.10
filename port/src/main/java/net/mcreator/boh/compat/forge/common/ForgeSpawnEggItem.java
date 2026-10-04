package net.mcreator.boh.compat.forge.common;

import java.util.function.Supplier;

import net.mcreator.boh.compat.item.BohItem;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.InteractionResult;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.context.UseOnContext;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/** 1.20 ForgeSpawnEggItem with the vanilla two-layer egg icon tinted by the given colors. */
public class ForgeSpawnEggItem extends net.mcreator.boh.compat.mc.world.item.SpawnEggItem {

    private final Supplier<? extends EntityType<?>> type;
    private final int primary, secondary;
    private IIcon overlay;

    public ForgeSpawnEggItem(Supplier<? extends EntityType<?>> type, int primary, int secondary, Properties props) {
        super(props);
        this.type = type;
        this.primary = primary;
        this.secondary = secondary;
    }

    public EntityType<?> getType(Object nbt) {
        return type.get();
    }

    @Override
    public void onRegistered(net.minecraft.util.ResourceLocation id) {
        registryName = id;
        setTextureName("spawn_egg");
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        if (ctx.getLevel().isRemote) return InteractionResult.SUCCESS;
        BlockPos pos = ctx.getClickedPos().relative(ctx.getClickedFace());
        Entity e = type.get().spawn(ctx.getLevel(), pos, MobSpawnType.SPAWN_EGG);
        if (e instanceof EntityLiving && ctx.getItemInHand().hasDisplayName())
            ((EntityLiving) e).setCustomNameTag(ctx.getItemInHand().getDisplayName());
        if (e != null && ctx.getPlayer() != null && !ctx.getPlayer().capabilities.isCreativeMode) ctx.getItemInHand().stackSize--;
        return InteractionResult.CONSUME;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String key = getUnlocalizedName() + ".name";
        return StatCollector.canTranslate(key) ? StatCollector.translateToLocal(key) : super.getItemStackDisplayName(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass) {
        return pass == 0 ? primary : secondary;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int damage, int pass) {
        return pass > 0 ? overlay : super.getIconFromDamageForRenderPass(damage, pass);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        itemIcon = reg.registerIcon("spawn_egg");
        overlay = reg.registerIcon("spawn_egg_overlay");
    }
}
