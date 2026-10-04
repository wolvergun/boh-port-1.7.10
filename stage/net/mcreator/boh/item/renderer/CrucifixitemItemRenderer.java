package net.mcreator.boh.item.renderer;

import java.util.HashSet;
import java.util.Set;
import net.mcreator.boh.item.CrucifixitemItem;
import net.mcreator.boh.item.model.CrucifixitemItemModel;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.client.VertexConsumer;
import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.RenderType;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.world.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.geo.BakedGeoModel;
import net.mcreator.boh.geo.GeoItemRenderer;

public class CrucifixitemItemRenderer extends GeoItemRenderer<CrucifixitemItem> {

    private static final float SCALE_RECIPROCAL = 0.0625F;

    protected boolean renderArms = false;

    protected MultiBufferSource currentBuffer;

    protected RenderType renderType;

    public ItemDisplayContext transformType;

    protected CrucifixitemItem animatable;

    private final Set<String> hiddenBones = new HashSet<>();

    private final Set<String> suppressedBones = new HashSet<>();

    public CrucifixitemItemRenderer() {
        super(new CrucifixitemItemModel());
    }

    public RenderType getRenderType(CrucifixitemItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }

    public void renderByItem(ItemStack stack, ItemDisplayContext transformType, PoseStack matrixStack, MultiBufferSource bufferIn, int combinedLightIn, int p_239207_6_) {
        this.transformType = transformType;
        super.renderByItem(stack, transformType, matrixStack, bufferIn, combinedLightIn, p_239207_6_);
    }

    public void actuallyRender(PoseStack matrixStackIn, CrucifixitemItem animatable, BakedGeoModel model, RenderType type, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, boolean isRenderer, float partialTicks, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.currentBuffer = renderTypeBuffer;
        this.renderType = type;
        this.animatable = animatable;
        super.actuallyRender(matrixStackIn, animatable, model, type, renderTypeBuffer, vertexBuilder, isRenderer, partialTicks, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        if (this.renderArms) {
            this.renderArms = false;
        }
    }

    public ResourceLocation getTextureLocation(CrucifixitemItem instance) {
        return super.getTextureLocation(instance);
    }
}
