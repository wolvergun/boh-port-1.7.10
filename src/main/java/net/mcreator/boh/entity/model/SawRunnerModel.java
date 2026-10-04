package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.SawRunnerEntity;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.CoreGeoBone;
import net.mcreator.boh.geo.DataTickets;
import net.mcreator.boh.geo.EntityModelData;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class SawRunnerModel extends GeoModel<SawRunnerEntity> {
    public ResourceLocation getAnimationResource(SawRunnerEntity entity) {
        return new ResourceLocation("boh", "animations/sawrunner.animation.json");
    }

    public ResourceLocation getModelResource(SawRunnerEntity entity) {
        return new ResourceLocation("boh", "geo/sawrunner.geo.json");
    }

    public ResourceLocation getTextureResource(SawRunnerEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }

    public void setCustomAnimations(SawRunnerEntity animatable, long instanceId, AnimationState animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("Head");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
            head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
        }
    }
}
