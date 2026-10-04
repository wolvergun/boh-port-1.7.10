package net.mcreator.boh.compat.mc.client.renderer.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

/** 1.20 HumanoidMobRenderer as a 1.7.10 RenderBiped (armor and held items handled by vanilla). */
public abstract class HumanoidMobRenderer<T extends EntityLiving, M> extends RenderBiped {

    public HumanoidMobRenderer(Context context, Object model, float shadow) {
        super(model instanceof ModelBiped ? (ModelBiped) model : new ModelBiped(), shadow);
    }

    public abstract ResourceLocation getTextureLocation(T entity);

    @Override
    @SuppressWarnings("unchecked")
    protected ResourceLocation getEntityTexture(Entity entity) {
        return getTextureLocation((T) entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected ResourceLocation getEntityTexture(EntityLiving entity) {
        return getTextureLocation((T) entity);
    }

    /** Render layers are handled by RenderBiped's own armor pass. */
    public boolean addLayer(Object layer) {
        return true;
    }
}
