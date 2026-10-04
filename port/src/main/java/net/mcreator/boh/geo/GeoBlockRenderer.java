package net.mcreator.boh.geo;

import java.util.ArrayList;
import java.util.List;

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
import org.lwjgl.opengl.GL12;

/** GeckoLib GeoBlockRenderer port on a 1.7.10 TileEntitySpecialRenderer. */
public class GeoBlockRenderer<T extends TileEntity & GeoAnimatable> extends TileEntitySpecialRenderer implements GeoRenderer<T> {

    protected final List<GeoRenderLayer<T>> renderLayers = new ArrayList<>();
    protected final GeoModel<T> model;
    protected T animatable;
    protected float scaleWidth = 1, scaleHeight = 1;

    public GeoBlockRenderer(GeoModel<T> model) {
        this.model = model;
    }

    public GeoBlockRenderer(Object context, GeoModel<T> model) {
        this(model);
    }

    @Override
    public GeoModel<T> getGeoModel() {
        return model;
    }

    @Override
    public T getAnimatable() {
        return animatable;
    }

    @Override
    public long getInstanceId(T te) {
        return ((long) te.xCoord * 31 + te.yCoord) * 31 + te.zCoord;
    }

    @Override
    public ResourceLocation getTextureLocation(T te) {
        return model.getTextureResource(te);
    }

    @Override
    public List<GeoRenderLayer<T>> getRenderLayers() {
        return renderLayers;
    }

    public GeoBlockRenderer<T> addRenderLayer(GeoRenderLayer<T> layer) {
        renderLayers.add(layer);
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTick) {
        T t = (T) te;
        animatable = t;
        RenderUtils.partialTick = partialTick;
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        BufferSource buffers = new BufferSource();
        int light = te.getWorldObj() == null ? 0xF000F0 : te.getWorldObj().getLightBrightnessForSkyBlocks(te.xCoord, te.yCoord, te.zCoord, 0);
        try {
            defaultRender(new PoseStack(), t, buffers, null, null, 0, partialTick, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        } finally {
            buffers.endBatch();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
        }
        animatable = null;
    }

    @Override
    public void preRender(PoseStack poseStack, T animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer,
        boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (isReRender) return;
        poseStack.translate(0.5, 0, 0.5);
        rotateBlock(getFacing(animatable), poseStack);
        if (scaleWidth != 1 || scaleHeight != 1) poseStack.scale(scaleWidth, scaleHeight, scaleWidth);
    }

    protected void rotateBlock(Direction facing, PoseStack poseStack) {
        float d = (float) (Math.PI / 180);
        switch (facing) {
            case SOUTH:
                poseStack.mulPose(new Quaternionf().rotationY(180 * d));
                break;
            case WEST:
                poseStack.mulPose(new Quaternionf().rotationY(90 * d));
                break;
            case EAST:
                poseStack.mulPose(new Quaternionf().rotationY(270 * d));
                break;
            case UP:
                poseStack.mulPose(new Quaternionf().rotationX(90 * d));
                break;
            case DOWN:
                poseStack.mulPose(new Quaternionf().rotationX(-90 * d));
                break;
            default:
        }
    }

    protected Direction getFacing(T te) {
        if (te.getWorldObj() == null) return Direction.NORTH;
        BlockState s = net.mcreator.boh.compat.M.getBlockState(te);
        if (s.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) return s.getValue(BlockStateProperties.HORIZONTAL_FACING);
        if (s.hasProperty(BlockStateProperties.FACING)) return s.getValue(BlockStateProperties.FACING);
        return Direction.NORTH;
    }

    @Override
    public void actuallyRender(PoseStack poseStack, T animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource,
        VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue,
        float alpha) {
        if (!isReRender) {
            AnimationState<T> state = new AnimationState<>(animatable, 0, 0, partialTick, false);
            long id = getInstanceId(animatable);
            state.setData(DataTickets.TICK, animatable.getTick(animatable));
            this.model.addAdditionalStateData(animatable, id, state);
            this.model.handleAnimations(animatable, id, state);
        }
        GeoRenderer.super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight,
            packedOverlay, red, green, blue, alpha);
    }
}
