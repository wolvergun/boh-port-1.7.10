package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.PhantomChicaEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class PhantomChicaModel extends GeoModel<PhantomChicaEntity> {
   public ResourceLocation getAnimationResource(PhantomChicaEntity entity) {
      return new ResourceLocation("boh", "animations/phantom_chica.animation.json");
   }

   public ResourceLocation getModelResource(PhantomChicaEntity entity) {
      return new ResourceLocation("boh", "geo/phantom_chica.geo.json");
   }

   public ResourceLocation getTextureResource(PhantomChicaEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }

   public void setCustomAnimations(PhantomChicaEntity animatable, long instanceId, AnimationState animationState) {
      CoreGeoBone head = this.getAnimationProcessor().getBone("head");
      if (head != null) {
         EntityModelData entityData = (EntityModelData)animationState.getData(DataTickets.ENTITY_MODEL_DATA);
         head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
         head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
      }
   }
}
