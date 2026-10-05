package net.mcreator.boh.compat.world.gen;

import net.mcreator.boh.compat.world.VanillaStates;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.ChunkEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * Level 0's rooms replace the dimension's one-block floor (1.20 places them the same way), and one room has a grid of
 * 2x2 holes in its floor, which then open into the void: mobs walking over them fall out of the world (the Lifeform
 * every time). Every loaded chunk of a floored mod dimension gets its floor block back wherever y 0 is empty, so the
 * holes become floor; this also mends chunks generated before the fix.
 */
public final class FloorPatch {

    private FloorPatch() {}

    public static void install() {
        MinecraftForge.EVENT_BUS.register(new FloorPatch());
    }

    /** After a room was placed around (x, z) (2x2 chunks, all loaded while populating): floor back into its holes. */
    public static void refill(net.minecraft.world.World w, int x, int z) {
        if (!(w.provider instanceof BohWorldProvider)) return;
        VanillaStates.Legacy floor = floor(((BohWorldProvider) w.provider).spec());
        if (floor == null) return;
        for (int bx = x - 16; bx < x + 16; bx++) for (int bz = z - 16; bz < z + 16; bz++)
            if (w.getBlock(bx, 0, bz) == Blocks.air) w.setBlock(bx, 0, bz, floor.block, floor.meta, 2);
    }

    /** The dimension's floor block, as its chunk generator lays it. */
    private static VanillaStates.Legacy floor(BohWorldProvider.Spec spec) {
        if (spec.floor == null) return null;
        VanillaStates.Legacy l = VanillaStates.convert(spec.floor, null);
        return l == null || l.block == null ? null : l;
    }

    @SubscribeEvent
    public void onLoad(ChunkEvent.Load e) {
        if (e.world.isRemote || !(e.world.provider instanceof BohWorldProvider)) return;
        VanillaStates.Legacy floor = floor(((BohWorldProvider) e.world.provider).spec());
        if (floor == null) return;
        Chunk c = e.getChunk();
        ExtendedBlockStorage s = c.getBlockStorageArray()[0];
        if (s == null) return;
        boolean changed = false;
        for (int x = 0; x < 16; x++) for (int z = 0; z < 16; z++) {
            if (s.getBlockByExtId(x, 0, z) != Blocks.air) continue;
            s.func_150818_a(x, 0, z, floor.block);
            s.setExtBlockMetadata(x, 0, z, floor.meta);
            changed = true;
        }
        if (changed) c.setChunkModified();
    }
}
