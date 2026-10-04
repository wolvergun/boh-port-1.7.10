package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.VampireBatEntity;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.CoreGeoBone;
import net.mcreator.boh.geo.DataTickets;
import net.mcreator.boh.geo.EntityModelData;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class VampireBatModel extends GeoModel<VampireBatEntity> {
    public ResourceLocation getAnimationResource(VampireBatEntity entity) {
        return new ResourceLocation("boh", "animations/vampire_bat.animation.json");
    }

    public ResourceLocation getModelResource(VampireBatEntity entity) {
        return new ResourceLocation("boh", "geo/vampire_bat.geo.json");
    }

    public ResourceLocation getTextureResource(VampireBatEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }

    public void setCustomAnimations(VampireBatEntity animatable, long instanceId, AnimationState animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("Head");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
            head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
        }
    }
}
