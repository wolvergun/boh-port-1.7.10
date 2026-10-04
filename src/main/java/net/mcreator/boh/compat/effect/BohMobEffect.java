package net.mcreator.boh.compat.effect;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.mcreator.boh.compat.forge.client.extensions.common.IClientMobEffectExtensions;
import net.mcreator.boh.compat.mc.world.effect.MobEffectCategory;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Operation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class BohMobEffect extends Potion {
    private final MobEffectCategory category;
    private ResourceLocation iconTexture;
    private boolean visibleInInventory = true;
    private boolean visibleInGui = true;
    private boolean renderInventoryText = true;
    private boolean clientInitialized;
    public static int clientTicks;

    public BohMobEffect(MobEffectCategory category, int color) {
        super(PotionIds.next(), category == MobEffectCategory.HARMFUL, color);
        this.category = category;
    }

    public MobEffectCategory getCategory() {
        return this.category;
    }

    public boolean isBeneficial() {
        return this.category == MobEffectCategory.BENEFICIAL;
    }

    public void setIconTexture(ResourceLocation tex) {
        this.iconTexture = tex;
    }

    public void applyEffectTick(EntityLivingBase entity, int amplifier) {
    }

    public void applyInstantenousEffect(Entity source, Entity indirect, EntityLivingBase target, int amplifier, double health) {
        this.applyEffectTick(target, amplifier);
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

    public BohMobEffect addAttributeModifier(IAttribute attr, String uuid, double amount, Operation op) {
        this.func_111184_a(attr, uuid, amount, op.ordinal());
        return this;
    }

    public List<ItemStack> getCurativeItems() {
        List<ItemStack> l = new ArrayList<>();
        l.add(new ItemStack(Items.milk_bucket));
        return l;
    }

    public void initializeClient(Consumer<IClientMobEffectExtensions> consumer) {
    }

    public String getDescriptionId() {
        return this.getName();
    }

    public void performEffect(EntityLivingBase entity, int amplifier) {
        if (entity.worldObj.isRemote) {
            clientTicks++;
        }

        this.applyEffectTick(entity, amplifier);
    }

    public void affectEntity(EntityLivingBase source, EntityLivingBase target, int amplifier, double health) {
        this.applyInstantenousEffect(source, source, target, amplifier, health);
    }

    public boolean isReady(int duration, int amplifier) {
        return this.isDurationEffectTick(duration, amplifier);
    }

    public boolean isInstant() {
        return this.isInstantenous();
    }

    public void applyAttributesModifiersToEntity(EntityLivingBase entity, BaseAttributeMap map, int amplifier) {
        this.addAttributeModifiers(entity, map, amplifier);
    }

    public void removeAttributesModifiersFromEntity(EntityLivingBase entity, BaseAttributeMap map, int amplifier) {
        this.removeAttributeModifiers(entity, map, amplifier);
    }

    @SideOnly(Side.CLIENT)
    private void ensureClientInit() {
        if (!this.clientInitialized) {
            this.clientInitialized = true;
            this.initializeClient(ext -> {
                if (ext instanceof IClientMobEffectExtensions) {
                    PotionEffect probe = new PotionEffect(this.id, 1, 0);
                    this.visibleInInventory = ext.isVisibleInInventory(probe);
                    this.visibleInGui = ext.isVisibleInGui(probe);
                    this.renderInventoryText = this.visibleInInventory;
                }
            });
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldRender(PotionEffect effect) {
        this.ensureClientInit();
        return this.visibleInInventory;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldRenderInvText(PotionEffect effect) {
        this.ensureClientInit();
        return this.renderInventoryText;
    }

    @SideOnly(Side.CLIENT)
    public boolean hasStatusIcon() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
        if (this.iconTexture != null) {
            mc.getTextureManager().bindTexture(this.iconTexture);
            Tessellator t = Tessellator.instance;
            int ix = x + 6;
            int iy = y + 7;
            t.startDrawingQuads();
            t.addVertexWithUV(ix, iy + 18, 0.0, 0.0, 1.0);
            t.addVertexWithUV(ix + 18, iy + 18, 0.0, 1.0, 1.0);
            t.addVertexWithUV(ix + 18, iy, 0.0, 1.0, 0.0);
            t.addVertexWithUV(ix, iy, 0.0, 0.0, 0.0);
            t.draw();
        }
    }
}
