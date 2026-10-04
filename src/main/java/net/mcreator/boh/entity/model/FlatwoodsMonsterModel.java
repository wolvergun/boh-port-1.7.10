package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.FlatwoodsMonsterEntity;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.CoreGeoBone;
import net.mcreator.boh.geo.DataTickets;
import net.mcreator.boh.geo.EntityModelData;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class FlatwoodsMonsterModel extends GeoModel<FlatwoodsMonsterEntity> {
    public ResourceLocation getAnimationResource(FlatwoodsMonsterEntity entity) {
        return new ResourceLocation("boh", "animations/flatwood_monster.animation.json");
    }

    public ResourceLocation getModelResource(FlatwoodsMonsterEntity entity) {
        return new ResourceLocation("boh", "geo/flatwood_monster.geo.json");
    }

    public ResourceLocation getTextureResource(FlatwoodsMonsterEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }

    public void setCustomAnimations(FlatwoodsMonsterEntity animatable, long instanceId, AnimationState animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
            head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
        }
    }
}
