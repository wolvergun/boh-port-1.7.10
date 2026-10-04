package net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem;

import java.util.ArrayList;
import java.util.List;
import net.mcreator.boh.compat.mc.world.level.block.Mirror;
import net.mcreator.boh.compat.mc.world.level.block.Rotation;

public class StructurePlaceSettings {
    Rotation rotation = Rotation.NONE;
    Mirror mirror = Mirror.NONE;
    boolean ignoreEntities;
    final List<StructureProcessor> processors = new ArrayList<>();

    public StructurePlaceSettings setRotation(Rotation r) {
        this.rotation = r == null ? Rotation.NONE : r;
        return this;
    }

    public StructurePlaceSettings setMirror(Mirror m) {
        this.mirror = m == null ? Mirror.NONE : m;
        return this;
    }

    public StructurePlaceSettings setIgnoreEntities(boolean b) {
        this.ignoreEntities = b;
        return this;
    }

    public StructurePlaceSettings addProcessor(StructureProcessor p) {
        this.processors.add(p);
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
        return this.rotation;
    }

    public Mirror getMirror() {
        return this.mirror;
    }
}
