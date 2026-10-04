package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.BookSimonEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class BookSimonModel extends GeoModel<BookSimonEntity> {
   public ResourceLocation getAnimationResource(BookSimonEntity entity) {
      return new ResourceLocation("boh", "animations/simon_book.animation.json");
   }

   public ResourceLocation getModelResource(BookSimonEntity entity) {
      return new ResourceLocation("boh", "geo/simon_book.geo.json");
   }

   public ResourceLocation getTextureResource(BookSimonEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }

   public void setCustomAnimations(BookSimonEntity animatable, long instanceId, AnimationState animationState) {
      CoreGeoBone head = this.getAnimationProcessor().getBone("Head");
      if (head != null) {
         EntityModelData entityData = (EntityModelData)animationState.getData(DataTickets.ENTITY_MODEL_DATA);
         head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
         head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
      }
   }
}
