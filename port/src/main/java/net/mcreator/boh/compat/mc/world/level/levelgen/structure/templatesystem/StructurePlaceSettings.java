package net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem;

import java.util.ArrayList;
import java.util.List;

import net.mcreator.boh.compat.mc.world.level.block.Mirror;
import net.mcreator.boh.compat.mc.world.level.block.Rotation;

/** 1.20 StructurePlaceSettings. */
public class StructurePlaceSettings {

    Rotation rotation = Rotation.NONE;
    Mirror mirror = Mirror.NONE;
    boolean ignoreEntities;
    final List<StructureProcessor> processors = new ArrayList<>();

    public StructurePlaceSettings setRotation(Rotation r) {
        rotation = r == null ? Rotation.NONE : r;
        return this;
    }

    public StructurePlaceSettings setMirror(Mirror m) {
        mirror = m == null ? Mirror.NONE : m;
        return this;
    }

    public StructurePlaceSettings setIgnoreEntities(boolean b) {
        ignoreEntities = b;
        return this;
    }

    public StructurePlaceSettings addProcessor(StructureProcessor p) {
        processors.add(p);
        return this;
    }

    public StructurePlaceSettings setRandom(Object random) {
        return this;
    }

    public StructurePlaceSettings setKnownShape(boolean b) {
        return this;
    }

    public StructurePlaceSettings setFinalizeEntities(boolean b) {
        return this;
    }

    public Rotation getRotation() {
        return rotation;
    }

    public Mirror getMirror() {
        return mirror;
    }
}
