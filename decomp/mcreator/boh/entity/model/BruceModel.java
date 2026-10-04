package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.BruceEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

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
         EntityModelData entityData = (EntityModelData)animationState.getData(DataTickets.ENTITY_MODEL_DATA);
         head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
         head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
      }
   }
}
