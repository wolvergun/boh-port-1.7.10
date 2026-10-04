package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.SwampMonsterEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class SwampMonsterModel extends GeoModel<SwampMonsterEntity> {
   public ResourceLocation getAnimationResource(SwampMonsterEntity entity) {
      return new ResourceLocation("boh", "animations/swamp_monster.animation.json");
   }

   public ResourceLocation getModelResource(SwampMonsterEntity entity) {
      return new ResourceLocation("boh", "geo/swamp_monster.geo.json");
   }

   public ResourceLocation getTextureResource(SwampMonsterEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }

   public void setCustomAnimations(SwampMonsterEntity animatable, long instanceId, AnimationState animationState) {
      CoreGeoBone head = this.getAnimationProcessor().getBone("Head");
      if (head != null) {
         EntityModelData entityData = (EntityModelData)animationState.getData(DataTickets.ENTITY_MODEL_DATA);
         head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
         head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
      }
   }
}
