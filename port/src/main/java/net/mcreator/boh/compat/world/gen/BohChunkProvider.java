package net.mcreator.boh.compat.world.gen;

import java.util.List;
import java.util.Random;

import net.mcreator.boh.compat.world.VanillaStates;
import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

/** Void (optionally one floor layer) generator; mod features and structure sets are placed on population. */
public class BohChunkProvider implements IChunkProvider {

    private final World world;
    private final BohWorldProvider.Spec spec;
    private Block floor;
    private int floorMeta;

    public BohChunkProvider(World world, BohWorldProvider.Spec spec) {
        this.world = world;
        this.spec = spec;
        if (spec.floor != null) {
            VanillaStates.Legacy l = VanillaStates.convert(spec.floor, null);
            if (l != null) {
                floor = l.block;
                floorMeta = l.meta;
            }
        }
    }

    @Override
    public Chunk provideChunk(int cx, int cz) {
        Block[] blocks = new Block[65536];
        byte[] metas = new byte[65536];
        if (floor != null) for (int x = 0; x < 16; x++) for (int z = 0; z < 16; z++) {
            int i = (x * 16 + z) * 256;
            blocks[i] = floor;
            metas[i] = (byte) floorMeta;
        }
        Chunk chunk = new Chunk(world, blocks, metas, cx, cz);
        fillBiome(chunk, spec.biome.biomeID);
        chunk.generateSkylightMap();
        return chunk;
    }

    private static java.lang.reflect.Method shortSetter;
    private static boolean looked;

    /** EndlessIDs stores biomes as short[] (and crashes on the vanilla byte[] accessors); fall back to vanilla otherwise. */
    static void fillBiome(Chunk chunk, int id) {
        if (!looked) {
            looked = true;
            try {
                shortSetter = Class.forName("com.falsepattern.endlessids.mixin.helpers.ChunkBiomeHook").getMethod("setBiomeShortArray", short[].class);
            } catch (Throwable t) {
                shortSetter = null;
            }
        }
        if (shortSetter != null && shortSetter.getDeclaringClass().isInstance(chunk)) {
            short[] a = new short[256];
            java.util.Arrays.fill(a, (short) id);
            try {
                shortSetter.invoke(chunk, (Object) a);
                return;
            } catch (Exception e) {
                // fall through
            }
        }
        byte[] b = new byte[256];
        java.util.Arrays.fill(b, (byte) id);
        chunk.setBiomeArray(b);
    }

    @Override
    public Chunk loadChunk(int cx, int cz) {
        return provideChunk(cx, cz);
    }

    @Override
    public boolean chunkExists(int cx, int cz) {
        return true;
    }

    @Override
    public void populate(IChunkProvider p, int cx, int cz) {
        Random r = new Random(world.getSeed());
        long a = r.nextLong() / 2L * 2L + 1L, b = r.nextLong() / 2L * 2L + 1L;
        r.setSeed(cx * a + cz * b ^ world.getSeed());
        ModWorldGen.INSTANCE.populate(r, cx, cz, world, spec.biome);
    }

    @Override
    public boolean saveChunks(boolean all, IProgressUpdate progress) {
        return true;
    }

    @Override
    public boolean unloadQueuedChunks() {
        return false;
    }

    @Override
    public boolean canSave() {
        return true;
    }

    @Override
    public String makeString() {
        return "BohChunkProvider:" + spec.name;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List getPossibleCreatures(EnumCreatureType type, int x, int y, int z) {
        return spec.biome.getSpawnableList(type);
    }

    @Override
    public ChunkPosition func_147416_a(World w, String name, int x, int y, int z) {
        return null;
    }

    @Override
    public int getLoadedChunkCount() {
        return 0;
    }

    @Override
    public void recreateStructures(int cx, int cz) {}

    @Override
    public void saveExtraData() {}
}
