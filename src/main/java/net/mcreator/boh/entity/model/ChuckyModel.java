package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.ChuckyEntity;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.CoreGeoBone;
import net.mcreator.boh.geo.DataTickets;
import net.mcreator.boh.geo.EntityModelData;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class ChuckyModel extends GeoModel<ChuckyEntity> {
    public ResourceLocation getAnimationResource(ChuckyEntity entity) {
        return new ResourceLocation("boh", "animations/chucky.animation.json");
    }

    public ResourceLocation getModelResource(ChuckyEntity entity) {
        return new ResourceLocation("boh", "geo/chucky.geo.json");
    }

    public ResourceLocation getTextureResource(ChuckyEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }

    public void setCustomAnimations(ChuckyEntity animatable, long instanceId, AnimationState animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
            head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
        }
    }
}
