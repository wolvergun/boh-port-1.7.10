package net.mcreator.boh.client.renderer;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.client.model.HumanoidModel;
import net.mcreator.boh.compat.mc.client.model.geom.ModelLayers;
import net.mcreator.boh.compat.mc.client.renderer.entity.Context;
import net.mcreator.boh.compat.mc.client.renderer.entity.HumanoidMobRenderer;
import net.mcreator.boh.compat.mc.client.renderer.entity.layers.HumanoidArmorLayer;
import net.mcreator.boh.entity.HerobrineEntity;
import net.minecraft.util.ResourceLocation;

public class HerobrineRenderer extends HumanoidMobRenderer<HerobrineEntity, HumanoidModel<HerobrineEntity>> {
    public HerobrineRenderer(Context context) {
        super(context, new HumanoidModel(M.bakeLayer(context, ModelLayers.PLAYER)), 0.5F);
        M.addLayer(
            this,
            new HumanoidArmorLayer(
                this,
                new HumanoidModel(M.bakeLayer(context, ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidModel(M.bakeLayer(context, ModelLayers.PLAYER_OUTER_ARMOR)),
                M.getModelManager(context)
            )
        );
    }

    public ResourceLocation getTextureLocation(HerobrineEntity entity) {
        return new ResourceLocation("boh:textures/entities/herobrine.png");
    }
}
