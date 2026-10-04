package net.mcreator.boh.compat.mc.client.model.geom.builders;

import java.util.ArrayList;

import net.mcreator.boh.compat.mc.client.model.geom.PartPose;

/** 1.20 MeshDefinition. */
public final class MeshDefinition {

    private final PartDefinition root = new PartDefinition(new ArrayList<>(), PartPose.ZERO);

    public PartDefinition getRoot() {
        return root;
    }
}
