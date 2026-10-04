package net.mcreator.boh.compat.forge.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.function.Supplier;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.InteractionResult;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.SpawnEggItem;
import net.mcreator.boh.compat.mc.world.item.context.UseOnContext;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class ForgeSpawnEggItem extends SpawnEggItem {
    private final Supplier<? extends EntityType<?>> type;
    private final int primary;
    private final int secondary;
    private IIcon overlay;

    public ForgeSpawnEggItem(Supplier<? extends EntityType<?>> type, int primary, int secondary, Properties props) {
        super(props);
        this.type = type;
        this.primary = primary;
        this.secondary = secondary;
    }

    public EntityType<?> getType(Object nbt) {
        return (EntityType<?>)this.type.get();
    }

    @Override
    public void onRegistered(ResourceLocation id) {
        this.registryName = id;
        this.setTextureName("spawn_egg");
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        if (ctx.getLevel().isRemote) {
            return InteractionResult.SUCCESS;
        } else {
            BlockPos pos = ctx.getClickedPos().relative(ctx.getClickedFace());
            Entity e = this.type.get().spawn(ctx.getLevel(), pos, MobSpawnType.SPAWN_EGG);
            if (e instanceof EntityLiving && ctx.getItemInHand().hasDisplayName()) {
                ((EntityLiving)e).setCustomNameTag(ctx.getItemInHand().getDisplayName());
            }

            if (e != null && ctx.getPlayer() != null && !ctx.getPlayer().capabilities.isCreativeMode) {
                ctx.getItemInHand().stackSize--;
            }

            return InteractionResult.CONSUME;
        }
    }

    public String getItemStackDisplayName(ItemStack stack) {
        String key = this.getUnlocalizedName() + ".name";
        return StatCollector.canTranslate(key) ? StatCollector.translateToLocal(key) : super.getItemStackDisplayName(stack);
    }

    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass) {
        return pass == 0 ? this.primary : this.secondary;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int damage, int pass) {
        return pass > 0 ? this.overlay : super.getIconFromDamageForRenderPass(damage, pass);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister reg) {
        this.itemIcon = reg.registerIcon("spawn_egg");
        this.overlay = reg.registerIcon("spawn_egg_overlay");
    }
}
