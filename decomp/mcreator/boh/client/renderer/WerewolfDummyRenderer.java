package net.mcreator.boh.client.renderer;

import net.mcreator.boh.entity.WerewolfDummyEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class WerewolfDummyRenderer extends HumanoidMobRenderer<WerewolfDummyEntity, HumanoidModel<WerewolfDummyEntity>> {
   public WerewolfDummyRenderer(Context context) {
      super(context, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER)), 0.5F);
      this.addLayer(
         new HumanoidArmorLayer(
            this, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()
         )
      );
   }

   public ResourceLocation getTextureLocation(WerewolfDummyEntity entity) {
      return new ResourceLocation("boh:textures/entities/werewolfdummy.png");
   }
}
