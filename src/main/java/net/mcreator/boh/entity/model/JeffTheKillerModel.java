package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.JeffTheKillerEntity;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.CoreGeoBone;
import net.mcreator.boh.geo.DataTickets;
import net.mcreator.boh.geo.EntityModelData;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class JeffTheKillerModel extends GeoModel<JeffTheKillerEntity> {
    public ResourceLocation getAnimationResource(JeffTheKillerEntity entity) {
        return new ResourceLocation("boh", "animations/jeff_the_killer.animation.json");
    }

    public ResourceLocation getModelResource(JeffTheKillerEntity entity) {
        return new ResourceLocation("boh", "geo/jeff_the_killer.geo.json");
    }

    public ResourceLocation getTextureResource(JeffTheKillerEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }

    public void setCustomAnimations(JeffTheKillerEntity animatable, long instanceId, AnimationState animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
            head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
        }
    }
}
