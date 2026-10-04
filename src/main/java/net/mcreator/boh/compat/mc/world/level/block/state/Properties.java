package net.mcreator.boh.compat.mc.world.level.block.state;

import java.util.function.ToIntFunction;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.NoteBlockInstrument;
import net.mcreator.boh.compat.mc.world.level.material.MapColor;
import net.mcreator.boh.compat.mc.world.level.material.PushReaction;
import net.minecraft.block.Block;

public class Properties {
    public SoundType sound = SoundType.STONE;
    public float hardness = 0.0F;
    public float resistance = 0.0F;
    public boolean noOcclusion;
    public boolean noCollission;
    public boolean randomTicks;
    public boolean requiresCorrectTool;
    public boolean instabreak;
    public boolean ignitedByLava;
    public boolean dynamicShape;
    public boolean replaceable;
    public boolean air;
    public boolean liquid;
    public boolean forceSolidOn;
    public boolean noLootTable;
    public boolean emissive;
    public ToIntFunction<BlockState> lightLevel = s -> 0;
    public float friction = 0.6F;
    public float speedFactor = 1.0F;
    public float jumpFactor = 1.0F;
    public PushReaction pushReaction = PushReaction.NORMAL;
    public Properties.StatePredicate redstoneConductor;
    public OffsetType offsetType = OffsetType.NONE;
    public MapColor mapColor = MapColor.STONE;

    public static Properties of() {
        return new Properties();
    }

    public static Properties copy(Block b) {
        Properties p = new Properties();
        p.hardness = b.getBlockHardness(null, 0, 0, 0);
        p.resistance = b.getExplosionResistance(null) * 5.0F;
        return p;
    }

    public static Properties ofFullCopy(Block b) {
        return copy(b);
    }

    public Properties sound(SoundType s) {
        this.sound = s;
        return this;
    }

    public Properties strength(float h, float r) {
        this.hardness = h;
        this.resistance = r;
        return this;
    }

    public Properties strength(float hr) {
        return this.strength(hr, hr);
    }

    public Properties destroyTime(float h) {
        this.hardness = h;
        return this;
    }

    public Properties explosionResistance(float r) {
        this.resistance = r;
        return this;
    }

    public Properties instrument(NoteBlockInstrument i) {
        return this;
    }

    public Properties noOcclusion() {
        this.noOcclusion = true;
        return this;
    }

    public Properties noCollission() {
        this.noCollission = true;
        this.noOcclusion = true;
        return this;
    }

    public Properties randomTicks() {
        this.randomTicks = true;
        return this;
    }

    public Properties requiresCorrectToolForDrops() {
        this.requiresCorrectTool = true;
        return this;
    }

    public Properties instabreak() {
        this.instabreak = true;
        this.hardness = 0.0F;
        this.resistance = 0.0F;
        return this;
    }

    public Properties lightLevel(ToIntFunction<BlockState> f) {
        this.lightLevel = f;
        return this;
    }

    public Properties friction(float f) {
        this.friction = f;
        return this;
    }

    public Properties speedFactor(float f) {
        this.speedFactor = f;
        return this;
    }

    public Properties jumpFactor(float f) {
        this.jumpFactor = f;
        return this;
    }

    public Properties pushReaction(PushReaction r) {
        this.pushReaction = r;
        return this;
    }

    public Properties isRedstoneConductor(Properties.StatePredicate p) {
        this.redstoneConductor = p;
        return this;
    }

    public Properties isSuffocating(Properties.StatePredicate p) {
        return this;
    }

    public Properties isViewBlocking(Properties.StatePredicate p) {
        return this;
    }

    public Properties hasPostProcess(Properties.StatePredicate p) {
        return this;
    }

    public Properties emissiveRendering(Properties.StatePredicate p) {
        this.emissive = true;
        return this;
    }

    public Properties isValidSpawn(Object p) {
        return this;
    }

    public Properties offsetType(OffsetType t) {
        this.offsetType = t;
        return this;
    }

    public Properties mapColor(MapColor c) {
        this.mapColor = c;
        return this;
    }

    public Properties mapColor(Object c) {
        return this;
    }

    public Properties ignitedByLava() {
        this.ignitedByLava = true;
        return this;
    }

    public Properties dynamicShape() {
        this.dynamicShape = true;
        return this;
    }

    public Properties forceSolidOn() {
        this.forceSolidOn = true;
        return this;
    }

    public Properties forceSolidOff() {
        return this;
    }

    public Properties replaceable() {
        this.replaceable = true;
        return this;
    }

    public Properties noLootTable() {
        this.noLootTable = true;
        return this;
    }

    public Properties air() {
        this.air = true;
        return this;
    }

    public Properties liquid() {
        this.liquid = true;
        return this;
    }

    public Properties noParticlesOnBreak() {
        return this;
    }

    public Properties randomTicks(boolean b) {
        this.randomTicks = b;
        return this;
    }

    @FunctionalInterface
    public interface StatePredicate {
        boolean test(BlockState var1, Object var2, Object var3);
    }
}
