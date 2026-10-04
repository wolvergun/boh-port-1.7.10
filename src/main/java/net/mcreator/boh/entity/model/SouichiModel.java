package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.SouichiEntity;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.CoreGeoBone;
import net.mcreator.boh.geo.DataTickets;
import net.mcreator.boh.geo.EntityModelData;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class SouichiModel extends GeoModel<SouichiEntity> {
    public ResourceLocation getAnimationResource(SouichiEntity entity) {
        return new ResourceLocation("boh", "animations/souichi.animation.json");
    }

    public ResourceLocation getModelResource(SouichiEntity entity) {
        return new ResourceLocation("boh", "geo/souichi.geo.json");
    }

    public ResourceLocation getTextureResource(SouichiEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }

    public void setCustomAnimations(SouichiEntity animatable, long instanceId, AnimationState animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
            head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
        }
    }
}
