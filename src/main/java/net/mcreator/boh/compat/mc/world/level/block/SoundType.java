package net.mcreator.boh.compat.mc.world.level.block;

import java.util.Objects;
import java.util.function.Supplier;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.minecraft.block.Block;

public class SoundType {
    public static final SoundType WOOD = new SoundType(Block.soundTypeWood, "wood");
    public static final SoundType GRAVEL = new SoundType(Block.soundTypeGravel, "ground");
    public static final SoundType GRASS = new SoundType(Block.soundTypeGrass, "plants");
    public static final SoundType LILY_PAD = GRASS;
    public static final SoundType STONE = new SoundType(Block.soundTypeStone, "rock");
    public static final SoundType METAL = new SoundType(Block.soundTypeMetal, "iron");
    public static final SoundType GLASS = new SoundType(Block.soundTypeGlass, "glass");
    public static final SoundType WOOL = new SoundType(Block.soundTypeCloth, "cloth");
    public static final SoundType SAND = new SoundType(Block.soundTypeSand, "sand");
    public static final SoundType SNOW = new SoundType(Block.soundTypeSnow, "snow");
    public static final SoundType LADDER = new SoundType(Block.soundTypeLadder, "wood");
    public static final SoundType ANVIL = new SoundType(Block.soundTypeAnvil, "anvil");
    public static final SoundType SLIME_BLOCK = new SoundType(Block.soundTypeGravel, "clay");
    public static final SoundType HONEY_BLOCK = SLIME_BLOCK;
    public static final SoundType CROP = GRASS;
    public static final SoundType HARD_CROP = GRASS;
    public static final SoundType VINE = GRASS;
    public static final SoundType SWEET_BERRY_BUSH = GRASS;
    public static final SoundType ROOTS = GRASS;
    public static final SoundType MOSS = GRASS;
    public static final SoundType MOSS_CARPET = GRASS;
    public static final SoundType AZALEA = GRASS;
    public static final SoundType FUNGUS = GRASS;
    public static final SoundType NETHER_WOOD = WOOD;
    public static final SoundType BAMBOO_WOOD = WOOD;
    public static final SoundType CHERRY_WOOD = WOOD;
    public static final SoundType NETHERRACK = STONE;
    public static final SoundType NETHER_BRICKS = STONE;
    public static final SoundType BASALT = STONE;
    public static final SoundType DEEPSLATE = STONE;
    public static final SoundType DEEPSLATE_BRICKS = STONE;
    public static final SoundType POLISHED_DEEPSLATE = STONE;
    public static final SoundType TUFF = STONE;
    public static final SoundType CALCITE = STONE;
    public static final SoundType BONE_BLOCK = STONE;
    public static final SoundType COPPER = METAL;
    public static final SoundType CHAIN = METAL;
    public static final SoundType LANTERN = METAL;
    public static final SoundType NETHERITE_BLOCK = METAL;
    public static final SoundType AMETHYST = GLASS;
    public static final SoundType MUD = GRAVEL;
    public static final SoundType MUD_BRICKS = STONE;
    public static final SoundType PACKED_MUD = GRAVEL;
    public static final SoundType SOUL_SAND = SAND;
    public static final SoundType SOUL_SOIL = SAND;
    public static final SoundType SCAFFOLDING = WOOD;
    public static final SoundType FROGLIGHT = GLASS;
    public static final SoundType SCULK = WOOL;
    public static final SoundType POWDER_SNOW = SNOW;
    public static final SoundType NETHER_WART = GRASS;
    public static final SoundType STEM = WOOD;
    public static final SoundType DECORATED_POT = STONE;
    public static final SoundType EMPTY = new SoundType(Block.soundTypeCloth, "cloth");
    private final net.minecraft.block.Block.SoundType legacy;
    private final String materialHint;

    private SoundType(net.minecraft.block.Block.SoundType legacy, String materialHint) {
        this.legacy = legacy;
        this.materialHint = materialHint;
    }

    public SoundType(
        float volume,
        float pitch,
        final Supplier<SoundEvent> breakSound,
        final Supplier<SoundEvent> stepSound,
        final Supplier<SoundEvent> placeSound,
        Supplier<SoundEvent> hitSound,
        Supplier<SoundEvent> fallSound
    ) {
        this.materialHint = "rock";
        this.legacy = new net.minecraft.block.Block.SoundType("stone", volume, pitch) {
            {
                Objects.requireNonNull(SoundType.this);
            }

            public String getBreakSound() {
                SoundEvent s = breakSound.get();
                return s == null ? super.getBreakSound() : s.legacyName();
            }

            public String getStepResourcePath() {
                SoundEvent s = stepSound.get();
                return s == null ? super.getStepResourcePath() : s.legacyName();
            }

            public String func_150496_b() {
                SoundEvent s = placeSound.get();
                return s == null ? this.getBreakSound() : s.legacyName();
            }
        };
    }

    public net.minecraft.block.Block.SoundType toVanilla() {
        return this.legacy;
    }

    public String materialHint() {
        return this.materialHint;
    }

    public float getVolume() {
        return this.legacy.getVolume();
    }

    public float getPitch() {
        return this.legacy.getPitch();
    }
}
