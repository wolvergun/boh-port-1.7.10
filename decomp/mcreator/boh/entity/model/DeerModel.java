package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.DeerEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class DeerModel extends GeoModel<DeerEntity> {
   public ResourceLocation getAnimationResource(DeerEntity entity) {
      return new ResourceLocation("boh", "animations/deer.animation.json");
   }

   public ResourceLocation getModelResource(DeerEntity entity) {
      return new ResourceLocation("boh", "geo/deer.geo.json");
   }

   public ResourceLocation getTextureResource(DeerEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }

   public void setCustomAnimations(DeerEntity animatable, long instanceId, AnimationState animationState) {
      CoreGeoBone head = this.getAnimationProcessor().getBone("neck");
      if (head != null) {
         EntityModelData entityData = (EntityModelData)animationState.getData(DataTickets.ENTITY_MODEL_DATA);
         head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
         head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
      }
   }
}
