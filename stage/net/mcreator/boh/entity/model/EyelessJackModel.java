package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.EyelessJackEntity;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.DataTickets;
import net.mcreator.boh.geo.CoreGeoBone;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.GeoModel;
import net.mcreator.boh.geo.EntityModelData;

public class EyelessJackModel extends GeoModel<EyelessJackEntity> {

    public ResourceLocation getAnimationResource(EyelessJackEntity entity) {
        return new ResourceLocation("boh", "animations/custom_biped.animation.json");
    }

    public ResourceLocation getModelResource(EyelessJackEntity entity) {
        return new ResourceLocation("boh", "geo/custom_biped.geo.json");
    }

    public ResourceLocation getTextureResource(EyelessJackEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }

    public void setCustomAnimations(EyelessJackEntity animatable, long instanceId, AnimationState animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
            head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
        }
    }
}
