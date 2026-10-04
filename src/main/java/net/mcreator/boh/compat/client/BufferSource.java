package net.mcreator.boh.compat.client;

import net.minecraft.client.renderer.Tessellator;

public class BufferSource implements MultiBufferSource {
    private RenderType current;
    private final TessellatorConsumer consumer = new TessellatorConsumer();

    @Override
    public VertexConsumer getBuffer(RenderType type) {
        if (this.current != null && this.current.equals(type)) {
            return this.consumer;
        } else {
            this.endBatch();
            this.current = type;
            type.setup();
            boolean sort = type.mode() == RenderType.Mode.TRANSLUCENT || type.mode() == RenderType.Mode.TRANSLUCENT_CULL;
            this.consumer.begin(type.mode() == RenderType.Mode.LINES ? 1 : 7, type.fullBright(), sort);
            return this.consumer;
        }
    }

    public void endBatch() {
        if (this.current != null) {
            this.consumer.end();
            this.current.clear();
            this.current = null;
        }
    }

    public static Tessellator tessellator() {
        return Tessellator.instance;
    }
}
