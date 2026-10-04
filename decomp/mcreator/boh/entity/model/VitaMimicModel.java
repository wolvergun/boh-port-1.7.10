package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.VitaMimicEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class VitaMimicModel extends GeoModel<VitaMimicEntity> {
   public ResourceLocation getAnimationResource(VitaMimicEntity entity) {
      return new ResourceLocation("boh", "animations/vita_mimic.animation.json");
   }

   public ResourceLocation getModelResource(VitaMimicEntity entity) {
      return new ResourceLocation("boh", "geo/vita_mimic.geo.json");
   }

   public ResourceLocation getTextureResource(VitaMimicEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }

   public void setCustomAnimations(VitaMimicEntity animatable, long instanceId, AnimationState animationState) {
      CoreGeoBone head = this.getAnimationProcessor().getBone("head");
      if (head != null) {
         EntityModelData entityData = (EntityModelData)animationState.getData(DataTickets.ENTITY_MODEL_DATA);
         head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
         head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
      }
   }
}
