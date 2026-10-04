package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.Subject3Entity;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.CoreGeoBone;
import net.mcreator.boh.geo.DataTickets;
import net.mcreator.boh.geo.EntityModelData;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class Subject3Model extends GeoModel<Subject3Entity> {
    public ResourceLocation getAnimationResource(Subject3Entity entity) {
        return new ResourceLocation("boh", "animations/subject_3.animation.json");
    }

    public ResourceLocation getModelResource(Subject3Entity entity) {
        return new ResourceLocation("boh", "geo/subject_3.geo.json");
    }

    public ResourceLocation getTextureResource(Subject3Entity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }

    public void setCustomAnimations(Subject3Entity animatable, long instanceId, AnimationState animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
            head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
        }
    }
}
