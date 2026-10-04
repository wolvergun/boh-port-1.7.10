package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.KirieHimuroEntity;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.CoreGeoBone;
import net.mcreator.boh.geo.DataTickets;
import net.mcreator.boh.geo.EntityModelData;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class KirieHimuroModel extends GeoModel<KirieHimuroEntity> {
    public ResourceLocation getAnimationResource(KirieHimuroEntity entity) {
        return new ResourceLocation("boh", "animations/kirie.animation.json");
    }

    public ResourceLocation getModelResource(KirieHimuroEntity entity) {
        return new ResourceLocation("boh", "geo/kirie.geo.json");
    }

    public ResourceLocation getTextureResource(KirieHimuroEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }

    public void setCustomAnimations(KirieHimuroEntity animatable, long instanceId, AnimationState animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
            head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
        }
    }
}
