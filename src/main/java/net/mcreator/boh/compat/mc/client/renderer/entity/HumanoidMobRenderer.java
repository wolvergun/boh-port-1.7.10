package net.mcreator.boh.compat.mc.client.renderer.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public abstract class HumanoidMobRenderer<T extends EntityLiving, M> extends RenderBiped {
    public HumanoidMobRenderer(Context context, Object model, float shadow) {
        super(model instanceof ModelBiped ? (ModelBiped)model : new ModelBiped(), shadow);
    }

    public abstract ResourceLocation getTextureLocation(T var1);

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getTextureLocation((T)entity);
    }

    protected ResourceLocation getEntityTexture(EntityLiving entity) {
        return this.getTextureLocation((T)entity);
    }

    public boolean addLayer(Object layer) {
        return true;
    }
}
