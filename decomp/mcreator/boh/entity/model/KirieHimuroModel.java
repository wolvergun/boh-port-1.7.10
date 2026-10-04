package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.KirieHimuroEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

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
         EntityModelData entityData = (EntityModelData)animationState.getData(DataTickets.ENTITY_MODEL_DATA);
         head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
         head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
      }
   }
}
