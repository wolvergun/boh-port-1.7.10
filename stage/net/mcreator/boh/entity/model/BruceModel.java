package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.BruceEntity;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.DataTickets;
import net.mcreator.boh.geo.CoreGeoBone;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.GeoModel;
import net.mcreator.boh.geo.EntityModelData;

public class BruceModel extends GeoModel<BruceEntity> {

    public ResourceLocation getAnimationResource(BruceEntity entity) {
        return new ResourceLocation("boh", "animations/bruce.animation.json");
    }

    public ResourceLocation getModelResource(BruceEntity entity) {
        return new ResourceLocation("boh", "geo/bruce.geo.json");
    }

    public ResourceLocation getTextureResource(BruceEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }

    public void setCustomAnimations(BruceEntity animatable, long instanceId, AnimationState animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("rotation_pivot");
        if (head != null) {
            EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
            head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
        }
    }
}
