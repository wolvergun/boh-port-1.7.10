package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.SotirisEntity;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.CoreGeoBone;
import net.mcreator.boh.geo.DataTickets;
import net.mcreator.boh.geo.EntityModelData;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class SotirisModel extends GeoModel<SotirisEntity> {
    public ResourceLocation getAnimationResource(SotirisEntity entity) {
        return new ResourceLocation("boh", "animations/sotiris.animation.json");
    }

    public ResourceLocation getModelResource(SotirisEntity entity) {
        return new ResourceLocation("boh", "geo/sotiris.geo.json");
    }

    public ResourceLocation getTextureResource(SotirisEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }

    public void setCustomAnimations(SotirisEntity animatable, long instanceId, AnimationState animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("RealEye");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
            head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
        }
    }
}
