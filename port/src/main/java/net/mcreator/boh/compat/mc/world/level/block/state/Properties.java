package net.mcreator.boh.compat.mc.world.level.block.state;

import java.util.function.ToIntFunction;

import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.NoteBlockInstrument;
import net.mcreator.boh.compat.mc.world.level.material.MapColor;
import net.mcreator.boh.compat.mc.world.level.material.PushReaction;
import net.minecraft.block.Block;

/** 1.20 BlockBehaviour.Properties. */
public class Properties {

    public SoundType sound = SoundType.STONE;
    public float hardness = 0, resistance = 0;
    public boolean noOcclusion, noCollission, randomTicks, requiresCorrectTool, instabreak, ignitedByLava, dynamicShape,
        replaceable, air, liquid, forceSolidOn, noLootTable, emissive;
    public ToIntFunction<BlockState> lightLevel = s -> 0;
    public float friction = 0.6F, speedFactor = 1.0F, jumpFactor = 1.0F;
    public PushReaction pushReaction = PushReaction.NORMAL;
    public StatePredicate redstoneConductor;
    public OffsetType offsetType = OffsetType.NONE;
    public MapColor mapColor = MapColor.STONE;

    @FunctionalInterface
    public interface StatePredicate {

        boolean test(BlockState state, Object level, Object pos);
    }

    public static Properties of() {
        return new Properties();
    }

    public static Properties copy(Block b) {
        Properties p = new Properties();
        p.hardness = b.getBlockHardness(null, 0, 0, 0);
        p.resistance = b.getExplosionResistance(null) * 5F;
        return p;
    }

    public static Properties ofFullCopy(Block b) {
        return copy(b);
    }

    public Properties sound(SoundType s) {
        sound = s;
        return this;
    }

    public Properties strength(float h, float r) {
        hardness = h;
        resistance = r;
        return this;
    }

    public Properties strength(float hr) {
        return strength(hr, hr);
    }

    public Properties destroyTime(float h) {
        hardness = h;
        return this;
    }

    public Properties explosionResistance(float r) {
        resistance = r;
        return this;
    }

    public Properties instrument(NoteBlockInstrument i) {
        return this;
    }

    public Properties noOcclusion() {
        noOcclusion = true;
        return this;
    }

    public Properties noCollission() {
        noCollission = true;
        noOcclusion = true;
        return this;
    }

    public Properties randomTicks() {
        randomTicks = true;
        return this;
    }

    public Properties requiresCorrectToolForDrops() {
        requiresCorrectTool = true;
        return this;
    }

    public Properties instabreak() {
        instabreak = true;
        hardness = 0;
        resistance = 0;
        return this;
    }

    public Properties lightLevel(ToIntFunction<BlockState> f) {
        lightLevel = f;
        return this;
    }

    public Properties friction(float f) {
        friction = f;
        return this;
    }

    public Properties speedFactor(float f) {
        speedFactor = f;
        return this;
    }

    public Properties jumpFactor(float f) {
        jumpFactor = f;
        return this;
    }

    public Properties pushReaction(PushReaction r) {
        pushReaction = r;
        return this;
    }

    public Properties isRedstoneConductor(StatePredicate p) {
        redstoneConductor = p;
        return this;
    }

    public Properties isSuffocating(StatePredicate p) {
        return this;
    }

    public Properties isViewBlocking(StatePredicate p) {
        return this;
    }

    public Properties hasPostProcess(StatePredicate p) {
        return this;
    }

    public Properties emissiveRendering(StatePredicate p) {
        emissive = true;
        return this;
    }

    public Properties isValidSpawn(Object p) {
        return this;
    }

    public Properties offsetType(OffsetType t) {
        offsetType = t;
        return this;
    }

    public Properties mapColor(MapColor c) {
        mapColor = c;
        return this;
    }

    public Properties mapColor(Object c) {
        return this;
    }

    public Properties ignitedByLava() {
        ignitedByLava = true;
        return this;
    }

    public Properties dynamicShape() {
        dynamicShape = true;
        return this;
    }

    public Properties forceSolidOn() {
        forceSolidOn = true;
        return this;
    }

    public Properties forceSolidOff() {
        return this;
    }

    public Properties replaceable() {
        replaceable = true;
        return this;
    }

    public Properties noLootTable() {
        noLootTable = true;
        return this;
    }

    public Properties air() {
        air = true;
        return this;
    }

    public Properties liquid() {
        liquid = true;
        return this;
    }

    public Properties noParticlesOnBreak() {
        return this;
    }

    public Properties randomTicks(boolean b) {
        randomTicks = b;
        return this;
    }
}
