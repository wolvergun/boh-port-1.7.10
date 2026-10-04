package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.MartianDroneEntity;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.CoreGeoBone;
import net.mcreator.boh.geo.DataTickets;
import net.mcreator.boh.geo.EntityModelData;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class MartianDroneModel extends GeoModel<MartianDroneEntity> {
    public ResourceLocation getAnimationResource(MartianDroneEntity entity) {
        return new ResourceLocation("boh", "animations/martian_drone.animation.json");
    }

    public ResourceLocation getModelResource(MartianDroneEntity entity) {
        return new ResourceLocation("boh", "geo/martian_drone.geo.json");
    }

    public ResourceLocation getTextureResource(MartianDroneEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }

    public void setCustomAnimations(MartianDroneEntity animatable, long instanceId, AnimationState animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
            head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
        }
    }
}
