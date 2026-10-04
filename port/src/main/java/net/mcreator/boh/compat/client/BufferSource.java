package net.mcreator.boh.compat.client;

import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.opengl.GL11;

/**
 * Immediate-mode buffer source: one RenderType is open at a time on the shared Tessellator, and switching
 * types (or {@link #endBatch()}) draws what was collected.
 */
public class BufferSource implements MultiBufferSource {

    private RenderType current;
    private final TessellatorConsumer consumer = new TessellatorConsumer();

    @Override
    public VertexConsumer getBuffer(RenderType type) {
        if (current != null && current.equals(type)) return consumer;
        endBatch();
        current = type;
        type.setup();
        boolean sort = type.mode() == RenderType.Mode.TRANSLUCENT || type.mode() == RenderType.Mode.TRANSLUCENT_CULL;
        consumer.begin(type.mode() == RenderType.Mode.LINES ? GL11.GL_LINES : GL11.GL_QUADS, type.fullBright(), sort);
        return consumer;
    }

    public void endBatch() {
        if (current == null) return;
        consumer.end();
        current.clear();
        current = null;
    }

    /** Draws without the Tessellator: used by code that wants a plain quad sink outside any batch. */
    public static Tessellator tessellator() {
        return Tessellator.instance;
    }
}
