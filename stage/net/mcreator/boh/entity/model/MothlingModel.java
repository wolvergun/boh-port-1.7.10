package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.MothlingEntity;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.DataTickets;
import net.mcreator.boh.geo.CoreGeoBone;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.GeoModel;
import net.mcreator.boh.geo.EntityModelData;

public class MothlingModel extends GeoModel<MothlingEntity> {

    public ResourceLocation getAnimationResource(MothlingEntity entity) {
        return new ResourceLocation("boh", "animations/mothling.animation.json");
    }

    public ResourceLocation getModelResource(MothlingEntity entity) {
        return new ResourceLocation("boh", "geo/mothling.geo.json");
    }

    public ResourceLocation getTextureResource(MothlingEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }

    public void setCustomAnimations(MothlingEntity animatable, long instanceId, AnimationState animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("body");
        if (head != null) {
            EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
            head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
        }
    }
}
