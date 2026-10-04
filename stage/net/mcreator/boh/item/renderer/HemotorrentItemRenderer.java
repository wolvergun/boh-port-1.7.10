package net.mcreator.boh.item.renderer;

import java.util.HashSet;
import java.util.Set;
import net.mcreator.boh.item.HemotorrentItem;
import net.mcreator.boh.item.model.HemotorrentItemModel;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.client.VertexConsumer;
import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.RenderType;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.world.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.geo.BakedGeoModel;
import net.mcreator.boh.geo.GeoItemRenderer;

public class HemotorrentItemRenderer extends GeoItemRenderer<HemotorrentItem> {

    private static final float SCALE_RECIPROCAL = 0.0625F;

    protected boolean renderArms = false;

    protected MultiBufferSource currentBuffer;

    protected RenderType renderType;

    public ItemDisplayContext transformType;

    protected HemotorrentItem animatable;

    private final Set<String> hiddenBones = new HashSet<>();

    private final Set<String> suppressedBones = new HashSet<>();

    public HemotorrentItemRenderer() {
        super(new HemotorrentItemModel());
    }

    public RenderType getRenderType(HemotorrentItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }

    public void renderByItem(ItemStack stack, ItemDisplayContext transformType, PoseStack matrixStack, MultiBufferSource bufferIn, int combinedLightIn, int p_239207_6_) {
        this.transformType = transformType;
        super.renderByItem(stack, transformType, matrixStack, bufferIn, combinedLightIn, p_239207_6_);
    }

    public void actuallyRender(PoseStack matrixStackIn, HemotorrentItem animatable, BakedGeoModel model, RenderType type, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, boolean isRenderer, float partialTicks, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.currentBuffer = renderTypeBuffer;
        this.renderType = type;
        this.animatable = animatable;
        super.actuallyRender(matrixStackIn, animatable, model, type, renderTypeBuffer, vertexBuilder, isRenderer, partialTicks, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        if (this.renderArms) {
            this.renderArms = false;
        }
    }

    public ResourceLocation getTextureLocation(HemotorrentItem instance) {
        return super.getTextureLocation(instance);
    }
}
