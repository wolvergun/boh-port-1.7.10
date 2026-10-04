package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.PatrickBatemanEntity;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.CoreGeoBone;
import net.mcreator.boh.geo.DataTickets;
import net.mcreator.boh.geo.EntityModelData;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class PatrickBatemanModel extends GeoModel<PatrickBatemanEntity> {
    public ResourceLocation getAnimationResource(PatrickBatemanEntity entity) {
        return new ResourceLocation("boh", "animations/bateman.animation.json");
    }

    public ResourceLocation getModelResource(PatrickBatemanEntity entity) {
        return new ResourceLocation("boh", "geo/bateman.geo.json");
    }

    public ResourceLocation getTextureResource(PatrickBatemanEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }

    public void setCustomAnimations(PatrickBatemanEntity animatable, long instanceId, AnimationState animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("Head");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
            head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
        }
    }
}
