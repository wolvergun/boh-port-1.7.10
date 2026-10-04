package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.TheThingVillagerEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class TheThingVillagerModel extends GeoModel<TheThingVillagerEntity> {
   public ResourceLocation getAnimationResource(TheThingVillagerEntity entity) {
      return new ResourceLocation("boh", "animations/thething_villager.animation.json");
   }

   public ResourceLocation getModelResource(TheThingVillagerEntity entity) {
      return new ResourceLocation("boh", "geo/thething_villager.geo.json");
   }

   public ResourceLocation getTextureResource(TheThingVillagerEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }

   public void setCustomAnimations(TheThingVillagerEntity animatable, long instanceId, AnimationState animationState) {
      CoreGeoBone head = this.getAnimationProcessor().getBone("head");
      if (head != null) {
         EntityModelData entityData = (EntityModelData)animationState.getData(DataTickets.ENTITY_MODEL_DATA);
         head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
         head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
      }
   }
}
