package net.mcreator.boh.item.renderer;

import java.util.HashSet;
import java.util.Set;
import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.client.RenderType;
import net.mcreator.boh.compat.client.VertexConsumer;
import net.mcreator.boh.compat.mc.world.item.ItemDisplayContext;
import net.mcreator.boh.geo.BakedGeoModel;
import net.mcreator.boh.geo.GeoItemRenderer;
import net.mcreator.boh.item.TheGreatKnifeItem;
import net.mcreator.boh.item.model.TheGreatKnifeItemModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class TheGreatKnifeItemRenderer extends GeoItemRenderer<TheGreatKnifeItem> {
    private static final float SCALE_RECIPROCAL = 0.0625F;
    protected boolean renderArms = false;
    protected MultiBufferSource currentBuffer;
    protected RenderType renderType;
    public ItemDisplayContext transformType;
    protected TheGreatKnifeItem animatable;
    private final Set<String> hiddenBones = new HashSet<>();
    private final Set<String> suppressedBones = new HashSet<>();

    public TheGreatKnifeItemRenderer() {
        super(new TheGreatKnifeItemModel());
    }

    public RenderType getRenderType(TheGreatKnifeItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }

    @Override
    public void renderByItem(
        ItemStack stack, ItemDisplayContext transformType, PoseStack matrixStack, MultiBufferSource bufferIn, int combinedLightIn, int p_239207_6_
    ) {
        this.transformType = transformType;
        if (this.animatable != null) {
            this.animatable.getTransformType(transformType);
        }

        super.renderByItem(stack, transformType, matrixStack, bufferIn, combinedLightIn, p_239207_6_);
    }

    public void actuallyRender(
        PoseStack matrixStackIn,
        TheGreatKnifeItem animatable,
        BakedGeoModel model,
        RenderType type,
        MultiBufferSource renderTypeBuffer,
        VertexConsumer vertexBuilder,
        boolean isRenderer,
        float partialTicks,
        int packedLightIn,
        int packedOverlayIn,
        float red,
        float green,
        float blue,
        float alpha
    ) {
        this.currentBuffer = renderTypeBuffer;
        this.renderType = type;
        this.animatable = animatable;
        super.actuallyRender(
            matrixStackIn,
            animatable,
            model,
            type,
            renderTypeBuffer,
            vertexBuilder,
            isRenderer,
            partialTicks,
            packedLightIn,
            packedOverlayIn,
            red,
            green,
            blue,
            alpha
        );
        if (this.renderArms) {
            this.renderArms = false;
        }
    }

    public ResourceLocation getTextureLocation(TheGreatKnifeItem instance) {
        return super.getTextureLocation(instance);
    }
}
