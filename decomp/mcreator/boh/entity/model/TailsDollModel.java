package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.TailsDollEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class TailsDollModel extends GeoModel<TailsDollEntity> {
   public ResourceLocation getAnimationResource(TailsDollEntity entity) {
      return new ResourceLocation("boh", "animations/tailsdoll.animation.json");
   }

   public ResourceLocation getModelResource(TailsDollEntity entity) {
      return new ResourceLocation("boh", "geo/tailsdoll.geo.json");
   }

   public ResourceLocation getTextureResource(TailsDollEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }

   public void setCustomAnimations(TailsDollEntity animatable, long instanceId, AnimationState animationState) {
      CoreGeoBone head = this.getAnimationProcessor().getBone("head");
      if (head != null) {
         EntityModelData entityData = (EntityModelData)animationState.getData(DataTickets.ENTITY_MODEL_DATA);
         head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
         head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
      }
   }
}
