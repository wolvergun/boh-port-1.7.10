package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.LeatherfaceEntity;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.CoreGeoBone;
import net.mcreator.boh.geo.DataTickets;
import net.mcreator.boh.geo.EntityModelData;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class LeatherfaceModel extends GeoModel<LeatherfaceEntity> {
    public ResourceLocation getAnimationResource(LeatherfaceEntity entity) {
        return new ResourceLocation("boh", "animations/leatherface.animation.json");
    }

    public ResourceLocation getModelResource(LeatherfaceEntity entity) {
        return new ResourceLocation("boh", "geo/leatherface.geo.json");
    }

    public ResourceLocation getTextureResource(LeatherfaceEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }

    public void setCustomAnimations(LeatherfaceEntity animatable, long instanceId, AnimationState animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("Head");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
            head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
        }
    }
}
