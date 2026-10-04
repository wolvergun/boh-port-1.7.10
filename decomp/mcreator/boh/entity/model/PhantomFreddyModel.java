package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.PhantomFreddyEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class PhantomFreddyModel extends GeoModel<PhantomFreddyEntity> {
   public ResourceLocation getAnimationResource(PhantomFreddyEntity entity) {
      return new ResourceLocation("boh", "animations/phantom_freddy.animation.json");
   }

   public ResourceLocation getModelResource(PhantomFreddyEntity entity) {
      return new ResourceLocation("boh", "geo/phantom_freddy.geo.json");
   }

   public ResourceLocation getTextureResource(PhantomFreddyEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }

   public void setCustomAnimations(PhantomFreddyEntity animatable, long instanceId, AnimationState animationState) {
      CoreGeoBone head = this.getAnimationProcessor().getBone("head");
      if (head != null) {
         EntityModelData entityData = (EntityModelData)animationState.getData(DataTickets.ENTITY_MODEL_DATA);
         head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
         head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
      }
   }
}
