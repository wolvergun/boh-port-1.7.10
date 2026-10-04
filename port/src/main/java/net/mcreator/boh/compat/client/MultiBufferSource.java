package net.mcreator.boh.compat.client;

/** Stand-in for net.minecraft.client.renderer.MultiBufferSource. */
public interface MultiBufferSource {

    VertexConsumer getBuffer(RenderType type);
}
