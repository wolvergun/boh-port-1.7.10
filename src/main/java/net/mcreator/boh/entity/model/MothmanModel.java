package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.MothmanEntity;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.CoreGeoBone;
import net.mcreator.boh.geo.DataTickets;
import net.mcreator.boh.geo.EntityModelData;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class MothmanModel extends GeoModel<MothmanEntity> {
    public ResourceLocation getAnimationResource(MothmanEntity entity) {
        return new ResourceLocation("boh", "animations/mothman.animation.json");
    }

    public ResourceLocation getModelResource(MothmanEntity entity) {
        return new ResourceLocation("boh", "geo/mothman.geo.json");
    }

    public ResourceLocation getTextureResource(MothmanEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }

    public void setCustomAnimations(MothmanEntity animatable, long instanceId, AnimationState animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
            head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
        }
    }
}
