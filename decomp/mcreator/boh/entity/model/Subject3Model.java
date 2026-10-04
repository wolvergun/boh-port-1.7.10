package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.Subject3Entity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class Subject3Model extends GeoModel<Subject3Entity> {
   public ResourceLocation getAnimationResource(Subject3Entity entity) {
      return new ResourceLocation("boh", "animations/subject_3.animation.json");
   }

   public ResourceLocation getModelResource(Subject3Entity entity) {
      return new ResourceLocation("boh", "geo/subject_3.geo.json");
   }

   public ResourceLocation getTextureResource(Subject3Entity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }

   public void setCustomAnimations(Subject3Entity animatable, long instanceId, AnimationState animationState) {
      CoreGeoBone head = this.getAnimationProcessor().getBone("head");
      if (head != null) {
         EntityModelData entityData = (EntityModelData)animationState.getData(DataTickets.ENTITY_MODEL_DATA);
         head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
         head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
      }
   }
}
