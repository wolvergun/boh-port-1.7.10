package net.mcreator.boh.compat.mc.client.model.geom.builders;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.mcreator.boh.compat.mc.client.model.geom.ModelPart;
import net.mcreator.boh.compat.mc.client.model.geom.PartPose;

public final class PartDefinition {
    private final List<CubeListBuilder.Def> cubes;
    private final PartPose pose;
    private final Map<String, PartDefinition> children = new LinkedHashMap<>();

    PartDefinition(List<CubeListBuilder.Def> cubes, PartPose pose) {
        this.cubes = cubes;
        this.pose = pose;
    }

    public PartDefinition addOrReplaceChild(String name, CubeListBuilder builder, PartPose pose) {
        PartDefinition c = new PartDefinition(builder.cubes, pose);
        PartDefinition old = this.children.put(name, c);
        if (old != null) {
            c.children.putAll(old.children);
        }

        return c;
    }

    public PartDefinition getChild(String name) {
        return this.children.get(name);
    }

    public ModelPart bake(int texW, int texH) {
        Map<String, ModelPart> baked = new LinkedHashMap<>();

        for (Entry<String, PartDefinition> e : this.children.entrySet()) {
            baked.put(e.getKey(), e.getValue().bake(texW, texH));
        }

        List<ModelPart.Cube> cs = new ArrayList<>();

        for (CubeListBuilder.Def d : this.cubes) {
            cs.add(d.bake(texW, texH));
        }

        ModelPart p = new ModelPart(cs, baked);
        p.setInitialPose(this.pose);
        p.loadPose(this.pose);
        return p;
    }
}
