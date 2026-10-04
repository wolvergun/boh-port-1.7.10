package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.LaughingJackEntity;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.CoreGeoBone;
import net.mcreator.boh.geo.DataTickets;
import net.mcreator.boh.geo.EntityModelData;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class LaughingJackModel extends GeoModel<LaughingJackEntity> {
    public ResourceLocation getAnimationResource(LaughingJackEntity entity) {
        return new ResourceLocation("boh", "animations/laughing_jack.animation.json");
    }

    public ResourceLocation getModelResource(LaughingJackEntity entity) {
        return new ResourceLocation("boh", "geo/laughing_jack.geo.json");
    }

    public ResourceLocation getTextureResource(LaughingJackEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }

    public void setCustomAnimations(LaughingJackEntity animatable, long instanceId, AnimationState animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
            head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
        }
    }
}
