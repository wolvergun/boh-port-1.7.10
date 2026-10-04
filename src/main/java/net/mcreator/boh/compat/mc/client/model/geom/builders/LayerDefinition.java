package net.mcreator.boh.compat.mc.client.model.geom.builders;

import net.mcreator.boh.compat.mc.client.model.geom.ModelPart;

public final class LayerDefinition {
    private final MeshDefinition mesh;
    private final int texW;
    private final int texH;

    private LayerDefinition(MeshDefinition mesh, int texW, int texH) {
        this.mesh = mesh;
        this.texW = texW;
        this.texH = texH;
    }

    public static LayerDefinition create(MeshDefinition mesh, int texW, int texH) {
        return new LayerDefinition(mesh, texW, texH);
    }

    public ModelPart bakeRoot() {
        return this.mesh.getRoot().bake(this.texW, this.texH);
    }
}
