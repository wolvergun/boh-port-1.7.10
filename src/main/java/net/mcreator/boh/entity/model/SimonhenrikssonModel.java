package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.SimonhenrikssonEntity;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.CoreGeoBone;
import net.mcreator.boh.geo.DataTickets;
import net.mcreator.boh.geo.EntityModelData;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class SimonhenrikssonModel extends GeoModel<SimonhenrikssonEntity> {
    public ResourceLocation getAnimationResource(SimonhenrikssonEntity entity) {
        return new ResourceLocation("boh", "animations/simon_henriksson.animation.json");
    }

    public ResourceLocation getModelResource(SimonhenrikssonEntity entity) {
        return new ResourceLocation("boh", "geo/simon_henriksson.geo.json");
    }

    public ResourceLocation getTextureResource(SimonhenrikssonEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }

    public void setCustomAnimations(SimonhenrikssonEntity animatable, long instanceId, AnimationState animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
            head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
        }
    }
}
