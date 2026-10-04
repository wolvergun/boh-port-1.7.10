package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.SlenderManEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class SlenderManModel extends GeoModel<SlenderManEntity> {
   public ResourceLocation getAnimationResource(SlenderManEntity entity) {
      return new ResourceLocation("boh", "animations/slender.animation.json");
   }

   public ResourceLocation getModelResource(SlenderManEntity entity) {
      return new ResourceLocation("boh", "geo/slender.geo.json");
   }

   public ResourceLocation getTextureResource(SlenderManEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }

   public void setCustomAnimations(SlenderManEntity animatable, long instanceId, AnimationState animationState) {
      CoreGeoBone head = this.getAnimationProcessor().getBone("Head");
      if (head != null) {
         EntityModelData entityData = (EntityModelData)animationState.getData(DataTickets.ENTITY_MODEL_DATA);
         head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
         head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
      }
   }
}
