package net.mcreator.boh.compat.mc.client.model;

import java.util.HashMap;

import net.mcreator.boh.compat.mc.client.model.geom.ModelPart;
import net.mcreator.boh.compat.mc.client.model.geom.PartPose;
import net.mcreator.boh.compat.mc.client.model.geom.builders.CubeDeformation;
import net.mcreator.boh.compat.mc.client.model.geom.builders.CubeListBuilder;
import net.mcreator.boh.compat.mc.client.model.geom.builders.LayerDefinition;
import net.mcreator.boh.compat.mc.client.model.geom.builders.MeshDefinition;
import net.mcreator.boh.compat.mc.client.model.geom.builders.PartDefinition;

/**
 * 1.20 PlayerModel; the arm parts are real ModelParts (used by first-person gun renderers) built for 1.7.10's
 * 64x32 skins, where the left arm mirrors the right arm's texture and sleeves are empty.
 */
public class PlayerModel<T> extends HumanoidModel<T> {

    public final ModelPart rightArm, leftArm, rightSleeve, leftSleeve;

    public PlayerModel(Object root, boolean slim) {
        super();
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition r = mesh.getRoot();
        int w = slim ? 3 : 4;
        r.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(slim ? -2 : -3, -2, -2, w, 12, 4, CubeDeformation.NONE),
            PartPose.offset(-5, 2, 0));
        r.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1, -2, -2, w, 12, 4, CubeDeformation.NONE),
            PartPose.offset(5, 2, 0));
        ModelPart baked = LayerDefinition.create(mesh, 64, 32).bakeRoot();
        rightArm = baked.getChild("right_arm");
        leftArm = baked.getChild("left_arm");
        rightSleeve = new ModelPart(new java.util.ArrayList<>(), new HashMap<>());
        leftSleeve = new ModelPart(new java.util.ArrayList<>(), new HashMap<>());
    }
}
