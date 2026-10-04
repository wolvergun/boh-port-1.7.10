package net.mcreator.boh.compat.mc.client.model;

import java.util.ArrayList;
import java.util.HashMap;
import net.mcreator.boh.compat.mc.client.model.geom.ModelPart;
import net.mcreator.boh.compat.mc.client.model.geom.PartPose;
import net.mcreator.boh.compat.mc.client.model.geom.builders.CubeDeformation;
import net.mcreator.boh.compat.mc.client.model.geom.builders.CubeListBuilder;
import net.mcreator.boh.compat.mc.client.model.geom.builders.LayerDefinition;
import net.mcreator.boh.compat.mc.client.model.geom.builders.MeshDefinition;
import net.mcreator.boh.compat.mc.client.model.geom.builders.PartDefinition;

public class PlayerModel<T> extends HumanoidModel<T> {
    public final ModelPart rightArm;
    public final ModelPart leftArm;
    public final ModelPart rightSleeve;
    public final ModelPart leftSleeve;

    public PlayerModel(Object root, boolean slim) {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition r = mesh.getRoot();
        int w = slim ? 3 : 4;
        r.addOrReplaceChild(
            "right_arm",
            CubeListBuilder.create().texOffs(40, 16).addBox(slim ? -2.0F : -3.0F, -2.0F, -2.0F, w, 12.0F, 4.0F, CubeDeformation.NONE),
            PartPose.offset(-5.0F, 2.0F, 0.0F)
        );
        r.addOrReplaceChild(
            "left_arm",
            CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -2.0F, w, 12.0F, 4.0F, CubeDeformation.NONE),
            PartPose.offset(5.0F, 2.0F, 0.0F)
        );
        ModelPart baked = LayerDefinition.create(mesh, 64, 32).bakeRoot();
        this.rightArm = baked.getChild("right_arm");
        this.leftArm = baked.getChild("left_arm");
        this.rightSleeve = new ModelPart(new ArrayList<>(), new HashMap<>());
        this.leftSleeve = new ModelPart(new ArrayList<>(), new HashMap<>());
    }
}
