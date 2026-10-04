package net.mcreator.boh.client.renderer;

import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.RenderType;
import net.mcreator.boh.geo.GeoArmorRenderer;
import net.mcreator.boh.geo.GeoBone;
import net.mcreator.boh.item.GojiHeadItem;
import net.mcreator.boh.item.model.GojiHeadModel;
import net.minecraft.util.ResourceLocation;

public class GojiHeadArmorRenderer extends GeoArmorRenderer<GojiHeadItem> {
    public GojiHeadArmorRenderer() {
        super(new GojiHeadModel());
        this.head = new GeoBone(null, "armorHead", false, 0.0, false, false);
        this.body = new GeoBone(null, "armorBody", false, 0.0, false, false);
        this.rightArm = new GeoBone(null, "armorRightArm", false, 0.0, false, false);
        this.leftArm = new GeoBone(null, "armorLeftArm", false, 0.0, false, false);
        this.rightLeg = new GeoBone(null, "armorRightLeg", false, 0.0, false, false);
        this.leftLeg = new GeoBone(null, "armorLeftLeg", false, 0.0, false, false);
        this.rightBoot = new GeoBone(null, "armorRightBoot", false, 0.0, false, false);
        this.leftBoot = new GeoBone(null, "armorLeftBoot", false, 0.0, false, false);
    }

    public RenderType getRenderType(GojiHeadItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }
}
