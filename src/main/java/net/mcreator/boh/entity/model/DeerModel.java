package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.DeerEntity;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.CoreGeoBone;
import net.mcreator.boh.geo.DataTickets;
import net.mcreator.boh.geo.EntityModelData;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class DeerModel extends GeoModel<DeerEntity> {
    public ResourceLocation getAnimationResource(DeerEntity entity) {
        return new ResourceLocation("boh", "animations/deer.animation.json");
    }

    public ResourceLocation getModelResource(DeerEntity entity) {
        return new ResourceLocation("boh", "geo/deer.geo.json");
    }

    public ResourceLocation getTextureResource(DeerEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }

    public void setCustomAnimations(DeerEntity animatable, long instanceId, AnimationState animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("neck");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
            head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
        }
    }
}
