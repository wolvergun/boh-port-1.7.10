package net.mcreator.boh.geo;

import java.util.ArrayList;
import java.util.List;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.client.BufferSource;
import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.OverlayTexture;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.client.Quaternionf;
import net.mcreator.boh.compat.client.RenderType;
import net.mcreator.boh.compat.client.VertexConsumer;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GeoBlockRenderer<T extends TileEntity & GeoAnimatable> extends TileEntitySpecialRenderer implements GeoRenderer<T> {
    protected final List<GeoRenderLayer<T>> renderLayers = new ArrayList<>();
    protected final GeoModel<T> model;
    protected T animatable;
    protected float scaleWidth = 1.0F;
    protected float scaleHeight = 1.0F;

    public GeoBlockRenderer(GeoModel<T> model) {
        this.model = model;
    }

    public GeoBlockRenderer(Object context, GeoModel<T> model) {
        this(model);
    }

    @Override
    public GeoModel<T> getGeoModel() {
        return this.model;
    }

    public T getAnimatable() {
        return this.animatable;
    }

    public long getInstanceId(T te) {
        return (te.xCoord * 31L + te.yCoord) * 31L + te.zCoord;
    }

    public ResourceLocation getTextureLocation(T te) {
        return this.model.getTextureResource(te);
    }

    @Override
    public List<GeoRenderLayer<T>> getRenderLayers() {
        return this.renderLayers;
    }

    public GeoBlockRenderer<T> addRenderLayer(GeoRenderLayer<T> layer) {
        this.renderLayers.add(layer);
        return this;
    }

    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTick) {
        T t = (T)te;
        this.animatable = (T)te;
        RenderUtils.partialTick = partialTick;
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glEnable(32826);
        BufferSource buffers = new BufferSource();
        int light = te.getWorldObj() == null ? 15728880 : te.getWorldObj().getLightBrightnessForSkyBlocks(te.xCoord, te.yCoord, te.zCoord, 0);

        try {
            this.defaultRender(new PoseStack(), t, buffers, null, null, 0.0F, partialTick, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        } finally {
            buffers.endBatch();
            GL11.glDisable(32826);
            GL11.glPopMatrix();
        }

        this.animatable = null;
    }

    public void preRender(
        PoseStack poseStack,
        T animatable,
        BakedGeoModel model,
        MultiBufferSource bufferSource,
        VertexConsumer buffer,
        boolean isReRender,
        float partialTick,
        int packedLight,
        int packedOverlay,
        float red,
        float green,
        float blue,
        float alpha
    ) {
        if (!isReRender) {
            poseStack.translate(0.5, 0.0, 0.5);
            this.rotateBlock(this.getFacing(animatable), poseStack);
            if (this.scaleWidth != 1.0F || this.scaleHeight != 1.0F) {
                poseStack.scale(this.scaleWidth, this.scaleHeight, this.scaleWidth);
            }
        }
    }

    protected void rotateBlock(Direction facing, PoseStack poseStack) {
        float d = (float) (Math.PI / 180.0);
        switch (facing) {
            case SOUTH:
                poseStack.mulPose(new Quaternionf().rotationY(180.0F * d));
                break;
            case WEST:
                poseStack.mulPose(new Quaternionf().rotationY(90.0F * d));
                break;
            case EAST:
                poseStack.mulPose(new Quaternionf().rotationY(270.0F * d));
                break;
            case UP:
                poseStack.mulPose(new Quaternionf().rotationX(90.0F * d));
                break;
            case DOWN:
                poseStack.mulPose(new Quaternionf().rotationX(-90.0F * d));
        }
    }

    protected Direction getFacing(T te) {
        if (te.getWorldObj() == null) {
            return Direction.NORTH;
        } else {
            BlockState s = M.getBlockState(te);
            if (s.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
                return s.getValue(BlockStateProperties.HORIZONTAL_FACING);
            } else {
                return s.hasProperty(BlockStateProperties.FACING) ? s.getValue(BlockStateProperties.FACING) : Direction.NORTH;
            }
        }
    }

    public void actuallyRender(
        PoseStack poseStack,
        T animatable,
        BakedGeoModel model,
        RenderType renderType,
        MultiBufferSource bufferSource,
        VertexConsumer buffer,
        boolean isReRender,
        float partialTick,
        int packedLight,
        int packedOverlay,
        float red,
        float green,
        float blue,
        float alpha
    ) {
        if (!isReRender) {
            AnimationState<T> state = new AnimationState<>(animatable, 0.0F, 0.0F, partialTick, false);
            long id = this.getInstanceId(animatable);
            state.setData(DataTickets.TICK, animatable.getTick(animatable));
            this.model.addAdditionalStateData(animatable, id, state);
            this.model.handleAnimations(animatable, id, state);
        }

        GeoRenderer.super.actuallyRender(
            poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha
        );
    }
}
