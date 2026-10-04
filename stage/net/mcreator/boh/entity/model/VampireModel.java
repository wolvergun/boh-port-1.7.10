package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.VampireEntity;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.DataTickets;
import net.mcreator.boh.geo.CoreGeoBone;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.GeoModel;
import net.mcreator.boh.geo.EntityModelData;

public class VampireModel extends GeoModel<VampireEntity> {

    public ResourceLocation getAnimationResource(VampireEntity entity) {
        return new ResourceLocation("boh", "animations/vampire.animation.json");
    }

    public ResourceLocation getModelResource(VampireEntity entity) {
        return new ResourceLocation("boh", "geo/vampire.geo.json");
    }

    public ResourceLocation getTextureResource(VampireEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }

    public void setCustomAnimations(VampireEntity animatable, long instanceId, AnimationState animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("Head");
        if (head != null) {
            EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
            head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
        }
    }
}
