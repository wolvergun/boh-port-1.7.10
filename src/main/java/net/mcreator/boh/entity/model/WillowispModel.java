package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.WillowispEntity;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.CoreGeoBone;
import net.mcreator.boh.geo.DataTickets;
import net.mcreator.boh.geo.EntityModelData;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class WillowispModel extends GeoModel<WillowispEntity> {
    public ResourceLocation getAnimationResource(WillowispEntity entity) {
        return new ResourceLocation("boh", "animations/will_o_wisp.animation.json");
    }

    public ResourceLocation getModelResource(WillowispEntity entity) {
        return new ResourceLocation("boh", "geo/will_o_wisp.geo.json");
    }

    public ResourceLocation getTextureResource(WillowispEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }

    public void setCustomAnimations(WillowispEntity animatable, long instanceId, AnimationState animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("bone");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
            head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
        }
    }
}
