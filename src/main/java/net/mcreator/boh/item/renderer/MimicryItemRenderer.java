package net.mcreator.boh.item.renderer;

import java.util.HashSet;
import java.util.Set;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.OverlayTexture;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.client.RenderType;
import net.mcreator.boh.compat.client.VertexConsumer;
import net.mcreator.boh.compat.mc.client.model.PlayerModel;
import net.mcreator.boh.compat.mc.client.renderer.entity.player.PlayerRenderer;
import net.mcreator.boh.compat.mc.world.item.ItemDisplayContext;
import net.mcreator.boh.geo.BakedGeoModel;
import net.mcreator.boh.geo.GeoBone;
import net.mcreator.boh.geo.GeoItemRenderer;
import net.mcreator.boh.geo.RenderUtils;
import net.mcreator.boh.item.MimicryItem;
import net.mcreator.boh.item.model.MimicryItemModel;
import net.mcreator.boh.utils.AnimUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class MimicryItemRenderer extends GeoItemRenderer<MimicryItem> {
    private static final float SCALE_RECIPROCAL = 0.0625F;
    protected boolean renderArms = false;
    protected MultiBufferSource currentBuffer;
    protected RenderType renderType;
    public ItemDisplayContext transformType;
    protected MimicryItem animatable;
    private final Set<String> hiddenBones = new HashSet<>();
    private final Set<String> suppressedBones = new HashSet<>();

    public MimicryItemRenderer() {
        super(new MimicryItemModel());
    }

    public RenderType getRenderType(MimicryItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }

    @Override
    public void renderByItem(
        ItemStack stack, ItemDisplayContext transformType, PoseStack matrixStack, MultiBufferSource bufferIn, int combinedLightIn, int p_239207_6_
    ) {
        this.transformType = transformType;
        super.renderByItem(stack, transformType, matrixStack, bufferIn, combinedLightIn, p_239207_6_);
    }

    public void actuallyRender(
        PoseStack matrixStackIn,
        MimicryItem animatable,
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

    public void renderRecursively(
        PoseStack stack,
        MimicryItem animatable,
        GeoBone bone,
        RenderType type,
        MultiBufferSource buffer,
        VertexConsumer bufferIn,
        boolean isReRender,
        float partialTick,
        int packedLightIn,
        int packedOverlayIn,
        float red,
        float green,
        float blue,
        float alpha
    ) {
        Minecraft mc = Minecraft.getMinecraft();
        String name = M.getName(bone);
        boolean renderingArms = false;
        if (!name.equals("left") && !name.equals("right")) {
            bone.setHidden(M.contains(this.hiddenBones, name));
        } else {
            bone.setHidden(true);
            renderingArms = true;
        }

        if (M.firstPerson(this.transformType) && renderingArms) {
            AbstractClientPlayer player = M.player(mc);
            float armsAlpha = M.isInvisible(player) ? 0.15F : 1.0F;
            PlayerRenderer playerRenderer = (PlayerRenderer)M.getEntityRenderDispatcher(mc).getRenderer(player);
            PlayerModel<AbstractClientPlayer> model = M.getModel(playerRenderer);
            stack.pushPose();
            RenderUtils.translateMatrixToBone(stack, bone);
            RenderUtils.translateToPivotPoint(stack, bone);
            RenderUtils.rotateMatrixAroundBone(stack, bone);
            RenderUtils.scaleMatrixForBone(stack, bone);
            RenderUtils.translateAwayFromPivotPoint(stack, bone);
            ResourceLocation loc = M.getSkinTextureLocation(player);
            VertexConsumer armBuilder = this.currentBuffer.getBuffer(RenderType.entitySolid(loc));
            VertexConsumer sleeveBuilder = this.currentBuffer.getBuffer(RenderType.entityTranslucent(loc));
            if (name.equals("left")) {
                stack.translate(-0.0625F, 0.125F, 0.0F);
                AnimUtils.renderPartOverBone(model.leftArm, bone, stack, armBuilder, packedLightIn, OverlayTexture.NO_OVERLAY, armsAlpha);
                AnimUtils.renderPartOverBone(model.leftSleeve, bone, stack, sleeveBuilder, packedLightIn, OverlayTexture.NO_OVERLAY, armsAlpha);
            } else if (name.equals("right")) {
                stack.translate(0.0625F, 0.125F, 0.0F);
                AnimUtils.renderPartOverBone(model.rightArm, bone, stack, armBuilder, packedLightIn, OverlayTexture.NO_OVERLAY, armsAlpha);
                AnimUtils.renderPartOverBone(model.rightSleeve, bone, stack, sleeveBuilder, packedLightIn, OverlayTexture.NO_OVERLAY, armsAlpha);
            }

            this.currentBuffer.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(this.animatable)));
            stack.popPose();
        }

        super.renderRecursively(
            stack, animatable, bone, type, buffer, bufferIn, isReRender, partialTick, packedLightIn, packedOverlayIn, red, green, blue, alpha
        );
    }

    public ResourceLocation getTextureLocation(MimicryItem instance) {
        return super.getTextureLocation(instance);
    }
}
