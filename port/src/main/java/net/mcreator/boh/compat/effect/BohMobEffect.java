package net.mcreator.boh.compat.effect;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import net.mcreator.boh.compat.mc.world.effect.MobEffectCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Compat base for mod effects: a 1.7.10 {@link Potion} (id from {@link PotionIds}) exposing the 1.20 MobEffect hooks.
 * Inventory icons come from the original textures/mob_effect/<name>.png.
 */
public class BohMobEffect extends Potion {

    private final MobEffectCategory category;
    private ResourceLocation iconTexture;
    private boolean visibleInInventory = true, visibleInGui = true, renderInventoryText = true;
    private boolean clientInitialized;

    public BohMobEffect(MobEffectCategory category, int color) {
        super(PotionIds.next(), category == MobEffectCategory.HARMFUL, color);
        this.category = category;
    }

    public MobEffectCategory getCategory() {
        return category;
    }

    public boolean isBeneficial() {
        return category == MobEffectCategory.BENEFICIAL;
    }

    public void setIconTexture(ResourceLocation tex) {
        iconTexture = tex;
    }

    // ------------------------------------------------------------------ 1.20 hooks

    public void applyEffectTick(EntityLivingBase entity, int amplifier) {}

    public void applyInstantenousEffect(net.minecraft.entity.Entity source, net.minecraft.entity.Entity indirect, EntityLivingBase target,
        int amplifier, double health) {
        applyEffectTick(target, amplifier);
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return false;
    }

    public boolean isInstantenous() {
        return false;
    }

    public void addAttributeModifiers(EntityLivingBase entity, BaseAttributeMap map, int amplifier) {
        super.applyAttributesModifiersToEntity(entity, map, amplifier);
    }

    public void removeAttributeModifiers(EntityLivingBase entity, BaseAttributeMap map, int amplifier) {
        super.removeAttributesModifiersFromEntity(entity, map, amplifier);
    }

    public BohMobEffect addAttributeModifier(IAttribute attr, String uuid, double amount,
        net.mcreator.boh.compat.mc.world.entity.ai.attributes.Operation op) {
        func_111184_a(attr, uuid, amount, op.ordinal());
        return this;
    }

    public List<ItemStack> getCurativeItems() {
        List<ItemStack> l = new ArrayList<>();
        l.add(new ItemStack(net.minecraft.init.Items.milk_bucket));
        return l;
    }

    public void initializeClient(Consumer<net.mcreator.boh.compat.forge.client.extensions.common.IClientMobEffectExtensions> consumer) {}

    public String getDescriptionId() {
        return getName();
    }

    // ------------------------------------------------------------------ 1.7.10 bridge

    /** diagnostics for /bohclient: client-side effect ticks */
    public static int clientTicks;

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        if (entity.worldObj.isRemote) clientTicks++;
        applyEffectTick(entity, amplifier);
    }

    @Override
    public void affectEntity(EntityLivingBase source, EntityLivingBase target, int amplifier, double health) {
        applyInstantenousEffect(source, source, target, amplifier, health);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return isDurationEffectTick(duration, amplifier);
    }

    @Override
    public boolean isInstant() {
        return isInstantenous();
    }

    @Override
    public void applyAttributesModifiersToEntity(EntityLivingBase entity, BaseAttributeMap map, int amplifier) {
        addAttributeModifiers(entity, map, amplifier);
    }

    @Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase entity, BaseAttributeMap map, int amplifier) {
        removeAttributeModifiers(entity, map, amplifier);
    }

    @SideOnly(Side.CLIENT)
    private void ensureClientInit() {
        if (clientInitialized) return;
        clientInitialized = true;
        initializeClient(ext -> {
            if (ext instanceof net.mcreator.boh.compat.forge.client.extensions.common.IClientMobEffectExtensions) {
                net.mcreator.boh.compat.forge.client.extensions.common.IClientMobEffectExtensions e =
                    (net.mcreator.boh.compat.forge.client.extensions.common.IClientMobEffectExtensions) ext;
                PotionEffect probe = new PotionEffect(id, 1, 0);
                visibleInInventory = e.isVisibleInInventory(probe);
                visibleInGui = e.isVisibleInGui(probe);
                renderInventoryText = visibleInInventory;
            }
        });
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldRender(PotionEffect effect) {
        ensureClientInit();
        return visibleInInventory;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldRenderInvText(PotionEffect effect) {
        ensureClientInit();
        return renderInventoryText;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasStatusIcon() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
        if (iconTexture == null) return;
        mc.getTextureManager().bindTexture(iconTexture);
        net.minecraft.client.renderer.Tessellator t = net.minecraft.client.renderer.Tessellator.instance;
        int ix = x + 6, iy = y + 7;
        t.startDrawingQuads();
        t.addVertexWithUV(ix, iy + 18, 0, 0, 1);
        t.addVertexWithUV(ix + 18, iy + 18, 0, 1, 1);
        t.addVertexWithUV(ix + 18, iy, 0, 1, 0);
        t.addVertexWithUV(ix, iy, 0, 0, 0);
        t.draw();
    }
}
