package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.AoOniEntity;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.CoreGeoBone;
import net.mcreator.boh.geo.DataTickets;
import net.mcreator.boh.geo.EntityModelData;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class AoOniModel extends GeoModel<AoOniEntity> {
    public ResourceLocation getAnimationResource(AoOniEntity entity) {
        return new ResourceLocation("boh", "animations/aooni.animation.json");
    }

    public ResourceLocation getModelResource(AoOniEntity entity) {
        return new ResourceLocation("boh", "geo/aooni.geo.json");
    }

    public ResourceLocation getTextureResource(AoOniEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }

    public void setCustomAnimations(AoOniEntity animatable, long instanceId, AnimationState animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("Head");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
            head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
        }
    }
}
