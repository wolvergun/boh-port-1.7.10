package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.MothlingEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class MothlingModel extends GeoModel<MothlingEntity> {
   public ResourceLocation getAnimationResource(MothlingEntity entity) {
      return new ResourceLocation("boh", "animations/mothling.animation.json");
   }

   public ResourceLocation getModelResource(MothlingEntity entity) {
      return new ResourceLocation("boh", "geo/mothling.geo.json");
   }

   public ResourceLocation getTextureResource(MothlingEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }

   public void setCustomAnimations(MothlingEntity animatable, long instanceId, AnimationState animationState) {
      CoreGeoBone head = this.getAnimationProcessor().getBone("body");
      if (head != null) {
         EntityModelData entityData = (EntityModelData)animationState.getData(DataTickets.ENTITY_MODEL_DATA);
         head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
         head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
      }
   }
}
