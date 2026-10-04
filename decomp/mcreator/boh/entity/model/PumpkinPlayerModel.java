package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.PumpkinPlayerEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class PumpkinPlayerModel extends GeoModel<PumpkinPlayerEntity> {
   public ResourceLocation getAnimationResource(PumpkinPlayerEntity entity) {
      return new ResourceLocation("boh", "animations/pumpkin_player.animation.json");
   }

   public ResourceLocation getModelResource(PumpkinPlayerEntity entity) {
      return new ResourceLocation("boh", "geo/pumpkin_player.geo.json");
   }

   public ResourceLocation getTextureResource(PumpkinPlayerEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }

   public void setCustomAnimations(PumpkinPlayerEntity animatable, long instanceId, AnimationState animationState) {
      CoreGeoBone head = this.getAnimationProcessor().getBone("head");
      if (head != null) {
         EntityModelData entityData = (EntityModelData)animationState.getData(DataTickets.ENTITY_MODEL_DATA);
         head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
         head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
      }
   }
}
