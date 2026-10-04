package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.REDEntity;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.CoreGeoBone;
import net.mcreator.boh.geo.DataTickets;
import net.mcreator.boh.geo.EntityModelData;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class REDModel extends GeoModel<REDEntity> {
    public ResourceLocation getAnimationResource(REDEntity entity) {
        return new ResourceLocation("boh", "animations/red.animation.json");
    }

    public ResourceLocation getModelResource(REDEntity entity) {
        return new ResourceLocation("boh", "geo/red.geo.json");
    }

    public ResourceLocation getTextureResource(REDEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }

    public void setCustomAnimations(REDEntity animatable, long instanceId, AnimationState animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
            head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
        }
    }
}
