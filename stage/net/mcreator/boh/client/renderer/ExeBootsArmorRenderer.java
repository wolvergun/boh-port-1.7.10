package net.mcreator.boh.client.renderer;

import net.mcreator.boh.item.ExeBootsItem;
import net.mcreator.boh.item.model.ExeBootsModel;
import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.RenderType;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.GeoBone;
import net.mcreator.boh.geo.GeoArmorRenderer;

public class ExeBootsArmorRenderer extends GeoArmorRenderer<ExeBootsItem> {

    public ExeBootsArmorRenderer() {
        super(new ExeBootsModel());
        this.head = new GeoBone(null, "armorHead", false, 0.0, false, false);
        this.body = new GeoBone(null, "armorBody", false, 0.0, false, false);
        this.rightArm = new GeoBone(null, "armorRightArm", false, 0.0, false, false);
        this.leftArm = new GeoBone(null, "armorLeftArm", false, 0.0, false, false);
        this.rightLeg = new GeoBone(null, "armorRightLeg", false, 0.0, false, false);
        this.leftLeg = new GeoBone(null, "armorLeftLeg", false, 0.0, false, false);
        this.rightBoot = new GeoBone(null, "armorLeftBoot", false, 0.0, false, false);
        this.leftBoot = new GeoBone(null, "armorRightBoot", false, 0.0, false, false);
    }

    public RenderType getRenderType(ExeBootsItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }
}
