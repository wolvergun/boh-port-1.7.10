package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.XenomorphEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class XenomorphModel extends GeoModel<XenomorphEntity> {
   public ResourceLocation getAnimationResource(XenomorphEntity entity) {
      return new ResourceLocation("boh", "animations/xenomorph.animation.json");
   }

   public ResourceLocation getModelResource(XenomorphEntity entity) {
      return new ResourceLocation("boh", "geo/xenomorph.geo.json");
   }

   public ResourceLocation getTextureResource(XenomorphEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }

   public void setCustomAnimations(XenomorphEntity animatable, long instanceId, AnimationState animationState) {
      CoreGeoBone head = this.getAnimationProcessor().getBone("Head");
      if (head != null) {
         EntityModelData entityData = (EntityModelData)animationState.getData(DataTickets.ENTITY_MODEL_DATA);
         head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
         head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
      }
   }
}
