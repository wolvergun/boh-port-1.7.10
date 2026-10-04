package net.mcreator.boh.compat.world.gen;

import java.lang.reflect.Method;
import java.util.Arrays;
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

public class BohChunkProvider implements IChunkProvider {
    private final World world;
    private final BohWorldProvider.Spec spec;
    private Block floor;
    private int floorMeta;
    private static Method shortSetter;
    private static boolean looked;

    public BohChunkProvider(World world, BohWorldProvider.Spec spec) {
        this.world = world;
        this.spec = spec;
        if (spec.floor != null) {
            VanillaStates.Legacy l = VanillaStates.convert(spec.floor, null);
            if (l != null) {
                this.floor = l.block;
                this.floorMeta = l.meta;
            }
        }
    }

    public Chunk provideChunk(int cx, int cz) {
        Block[] blocks = new Block[65536];
        byte[] metas = new byte[65536];
        if (this.floor != null) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    int i = (x * 16 + z) * 256;
                    blocks[i] = this.floor;
                    metas[i] = (byte)this.floorMeta;
                }
            }
        }

        Chunk chunk = new Chunk(this.world, blocks, metas, cx, cz);
        fillBiome(chunk, this.spec.biome.biomeID);
        chunk.generateSkylightMap();
        return chunk;
    }

    static void fillBiome(Chunk chunk, int id) {
        if (!looked) {
            looked = true;

            try {
                shortSetter = Class.forName("com.falsepattern.endlessids.mixin.helpers.ChunkBiomeHook").getMethod("setBiomeShortArray", short[].class);
            } catch (Throwable var4) {
                shortSetter = null;
            }
        }

        if (shortSetter != null && shortSetter.getDeclaringClass().isInstance(chunk)) {
            short[] a = new short[256];
            Arrays.fill(a, (short)id);

            try {
                shortSetter.invoke(chunk, a);
                return;
            } catch (Exception var5) {
            }
        }

        byte[] b = new byte[256];
        Arrays.fill(b, (byte)id);
        chunk.setBiomeArray(b);
    }

    public Chunk loadChunk(int cx, int cz) {
        return this.provideChunk(cx, cz);
    }

    public boolean chunkExists(int cx, int cz) {
        return true;
    }

    public void populate(IChunkProvider p, int cx, int cz) {
        Random r = new Random(this.world.getSeed());
        long a = r.nextLong() / 2L * 2L + 1L;
        long b = r.nextLong() / 2L * 2L + 1L;
        r.setSeed(cx * a + cz * b ^ this.world.getSeed());
        ModWorldGen.INSTANCE.populate(r, cx, cz, this.world, this.spec.biome);
    }

    public boolean saveChunks(boolean all, IProgressUpdate progress) {
        return true;
    }

    public boolean unloadQueuedChunks() {
        return false;
    }

    public boolean canSave() {
        return true;
    }

    public String makeString() {
        return "BohChunkProvider:" + this.spec.name;
    }

    public List getPossibleCreatures(EnumCreatureType type, int x, int y, int z) {
        return this.spec.biome.getSpawnableList(type);
    }

    public ChunkPosition func_147416_a(World w, String name, int x, int y, int z) {
        return null;
    }

    public int getLoadedChunkCount() {
        return 0;
    }

    public void recreateStructures(int cx, int cz) {
    }

    public void saveExtraData() {
    }
}
