package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.SuicideMouseEntity;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.DataTickets;
import net.mcreator.boh.geo.CoreGeoBone;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.GeoModel;
import net.mcreator.boh.geo.EntityModelData;

public class SuicideMouseModel extends GeoModel<SuicideMouseEntity> {

    public ResourceLocation getAnimationResource(SuicideMouseEntity entity) {
        return new ResourceLocation("boh", "animations/suicidemouse.animation.json");
    }

    public ResourceLocation getModelResource(SuicideMouseEntity entity) {
        return new ResourceLocation("boh", "geo/suicidemouse.geo.json");
    }

    public ResourceLocation getTextureResource(SuicideMouseEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }

    public void setCustomAnimations(SuicideMouseEntity animatable, long instanceId, AnimationState animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
            head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
        }
    }
}
