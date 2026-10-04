package net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Vec3i;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.level.block.Mirror;
import net.mcreator.boh.compat.mc.world.level.block.Rotation;
import net.mcreator.boh.compat.world.ItemNbt;
import net.mcreator.boh.compat.world.VanillaStates;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/** 1.20 StructureTemplate: palette + blocks + entities from a structure .nbt, placed with rotation/mirror. */
public class StructureTemplate {

    static final class Info {

        final int x, y, z, state;
        final NBTTagCompound nbt;

        Info(int x, int y, int z, int state, NBTTagCompound nbt) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.state = state;
            this.nbt = nbt;
        }
    }

    static final class PState {

        final String name;
        final Map<String, String> props;

        PState(String name, Map<String, String> props) {
            this.name = name;
            this.props = props;
        }
    }

    private final List<PState> palette = new ArrayList<>();
    private final List<Info> blocks = new ArrayList<>();
    private final List<NBTTagCompound> entities = new ArrayList<>();
    private int sx, sy, sz;
    /** Converted palette per (quarter turns, mirror): converting per block cost a registry lookup and map copies. */
    private final Map<Integer, VanillaStates.Legacy[]> converted = new HashMap<>();

    void load(NBTTagCompound tag) {
        NBTTagList size = tag.getTagList("size", 3);
        if (size.tagCount() == 3) {
            sx = intAt(size, 0);
            sy = intAt(size, 1);
            sz = intAt(size, 2);
        }
        NBTTagList pal = tag.getTagList("palette", 10);
        if (pal.tagCount() == 0 && tag.hasKey("palettes")) {
            NBTTagList pl = tag.getTagList("palettes", 9);
            if (pl.tagCount() > 0) pal = (NBTTagList) ((NBTTagList) pl.copy()).removeTag(0);
        }
        for (int i = 0; i < pal.tagCount(); i++) {
            NBTTagCompound s = pal.getCompoundTagAt(i);
            Map<String, String> props = new HashMap<>();
            NBTTagCompound p = s.getCompoundTag("Properties");
            for (Object k : p.func_150296_c()) props.put((String) k, p.getString((String) k));
            palette.add(new PState(s.getString("Name"), props));
        }
        NBTTagList bl = tag.getTagList("blocks", 10);
        for (int i = 0; i < bl.tagCount(); i++) {
            NBTTagCompound b = bl.getCompoundTagAt(i);
            NBTTagList pos = b.getTagList("pos", 3);
            blocks.add(new Info(intAt(pos, 0), intAt(pos, 1), intAt(pos, 2), b.getInteger("state"), b.hasKey("nbt") ? b.getCompoundTag("nbt") : null));
        }
        NBTTagList el = tag.getTagList("entities", 10);
        for (int i = 0; i < el.tagCount(); i++) entities.add(el.getCompoundTagAt(i));
    }

    /** IntTag list entries (1.7.10 has no typed accessor for them). */
    private static int intAt(NBTTagList l, int i) {
        NBTBase b = ((NBTTagList) l.copy()).removeTag(i);
        return b instanceof net.minecraft.nbt.NBTBase.NBTPrimitive ? ((net.minecraft.nbt.NBTBase.NBTPrimitive) b).func_150287_d() : 0;
    }

    public boolean isEmpty() {
        return blocks.isEmpty();
    }

    public Vec3i getSize() {
        return new Vec3i(sx, sy, sz);
    }

    public Vec3i getSize(Rotation r) {
        return r == Rotation.CLOCKWISE_90 || r == Rotation.COUNTERCLOCKWISE_90 ? new Vec3i(sz, sy, sx) : getSize();
    }

    /** Mojang StructureTemplate.transform with pivot (0,0,0). */
    static int[] transform(int x, int y, int z, Mirror mirror, Rotation rot) {
        if (mirror == Mirror.LEFT_RIGHT) z = -z;
        else if (mirror == Mirror.FRONT_BACK) x = -x;
        switch (rot) {
            case COUNTERCLOCKWISE_90:
                return new int[] { z, y, -x };
            case CLOCKWISE_90:
                return new int[] { -z, y, x };
            case CLOCKWISE_180:
                return new int[] { -x, y, -z };
            default:
                return new int[] { x, y, z };
        }
    }

    private static int turns(Rotation r) {
        switch (r) {
            case CLOCKWISE_90:
                return 1;
            case CLOCKWISE_180:
                return 2;
            case COUNTERCLOCKWISE_90:
                return 3;
            default:
                return 0;
        }
    }

    public boolean placeInWorld(World world, BlockPos pos, BlockPos pivot, StructurePlaceSettings settings, Random random, int flags) {
        return place(world, pos, settings, random, flags, false);
    }

    /**
     * Placement during world generation: blocks in chunks that are not populated yet (so never sent to a client) are
     * written straight into chunk storage, without per-block lighting, neighbour updates or onBlockAdded (as 1.20
     * worldgen does); sky light is recomputed once per chunk and block light only around light sources.
     */
    public boolean placeInWorldgen(World world, BlockPos pos, StructurePlaceSettings settings, Random random) {
        return place(world, pos, settings, random, 2, true);
    }

    private boolean place(World world, BlockPos pos, StructurePlaceSettings settings, Random random, int flags, boolean worldgen) {
        if (world == null || world.isRemote || blocks.isEmpty()) return false;
        Mirror mirror = settings.mirror;
        int t = turns(settings.rotation);
        boolean mx = mirror == Mirror.FRONT_BACK;
        if (mirror == Mirror.LEFT_RIGHT) {
            mx = true;
            t = (t + 2) & 3;
        }
        VanillaStates.Legacy[] pal = convertedPalette(t, mx);
        boolean[] skip = new boolean[palette.size()];
        for (int i = 0; i < skip.length; i++) skip[i] = pal[i] == null || ignored(settings, palette.get(i).name);
        List<Object[]> deferred = new ArrayList<>();
        java.util.Set<net.minecraft.world.chunk.Chunk> fastChunks = new java.util.HashSet<>();
        List<int[]> lights = new ArrayList<>();
        // two passes: solid blocks first, then attachables (torches, doors, plants) so they find support
        for (int pass = 0; pass < 2; pass++) {
            for (Info b : blocks) {
                if (b.state < 0 || b.state >= skip.length || skip[b.state]) continue;
                VanillaStates.Legacy l = pal[b.state];
                boolean attach = needsSupport(l.block);
                if ((pass == 0) == attach) continue;
                int[] p = transform(b.x, b.y, b.z, mirror, settings.rotation);
                int wx = pos.getX() + p[0], wy = pos.getY() + p[1], wz = pos.getZ() + p[2];
                if (wy < 0 || wy > 255) continue;
                if (!worldgen || !setFast(world, wx, wy, wz, l, fastChunks, lights)) {
                    if (l.modState != null) M.setBlock(world, new BlockPos(wx, wy, wz), l.modState, 2);
                    else world.setBlock(wx, wy, wz, l.block, l.meta, 2);
                }
                // block entities without data are created lazily by the chunk when first used
                if (b.nbt != null && !isEmptyBlockEntity(b.nbt)) deferred.add(new Object[] { b, palette.get(b.state), new int[] { wx, wy, wz } });
            }
        }
        for (net.minecraft.world.chunk.Chunk c : fastChunks) {
            c.generateSkylightMap();
            c.setChunkModified();
        }
        for (int[] l : lights) world.func_147451_t(l[0], l[1], l[2]);
        for (Object[] d : deferred) applyBlockEntity(world, (Info) d[0], (PState) d[1], (int[]) d[2], random);
        if (!settings.ignoreEntities) for (NBTTagCompound e : entities) spawnEntity(world, e, pos, mirror, settings.rotation);
        if ((flags & 1) != 0) {
            for (Info b : blocks) {
                int[] p = transform(b.x, b.y, b.z, mirror, settings.rotation);
                int wx = pos.getX() + p[0], wy = pos.getY() + p[1], wz = pos.getZ() + p[2];
                if (wy >= 0 && wy <= 255) world.notifyBlockChange(wx, wy, wz, world.getBlock(wx, wy, wz));
            }
        }
        return true;
    }

    public boolean placeInWorld(World world, BlockPos pos, BlockPos pivot, StructurePlaceSettings settings, Object random, int flags) {
        return placeInWorld(world, pos, pivot, settings, random instanceof Random ? (Random) random : world.rand, flags);
    }

    private VanillaStates.Legacy[] convertedPalette(int turns, boolean mirrorX) {
        return converted.computeIfAbsent(turns * 2 + (mirrorX ? 1 : 0), k -> {
            VanillaStates.Legacy[] out = new VanillaStates.Legacy[palette.size()];
            for (int i = 0; i < out.length; i++) {
                PState ps = palette.get(i);
                out[i] = VanillaStates.convert(ps.name, VanillaStates.rotate(ps.props, turns, mirrorX));
            }
            return out;
        });
    }

    /** {"id": ..., "Items": []} and the like: nothing to restore, so the block entity can be created lazily. */
    private static boolean isEmptyBlockEntity(NBTTagCompound nbt) {
        for (Object k : nbt.func_150296_c()) {
            String key = (String) k;
            if (key.equals("id")) continue;
            NBTBase v = nbt.getTag(key);
            if (v instanceof NBTTagList && ((NBTTagList) v).tagCount() == 0) continue;
            return false;
        }
        return true;
    }

    /** Direct chunk-storage write for chunks that are still being generated; false to use World.setBlock. */
    private static boolean setFast(World world, int x, int y, int z, VanillaStates.Legacy l, java.util.Set<net.minecraft.world.chunk.Chunk> touched,
        List<int[]> lights) {
        if (!world.blockExists(x, y, z)) return false;
        net.minecraft.world.chunk.Chunk c = world.getChunkFromChunkCoords(x >> 4, z >> 4);
        if (c.isTerrainPopulated) return false;
        net.minecraft.world.chunk.storage.ExtendedBlockStorage[] st = c.getBlockStorageArray();
        int sec = y >> 4;
        if (st[sec] == null) {
            if (l.block == net.minecraft.init.Blocks.air) return true;
            st[sec] = new net.minecraft.world.chunk.storage.ExtendedBlockStorage(sec << 4, !world.provider.hasNoSky);
        }
        if (st[sec].getBlockByExtId(x & 15, y & 15, z & 15).hasTileEntity(st[sec].getExtBlockMetadata(x & 15, y & 15, z & 15)))
            c.removeTileEntity(x & 15, y, z & 15);
        int meta = l.modState != null ? l.modState.meta() : l.meta;
        st[sec].func_150818_a(x & 15, y & 15, z & 15, l.block);
        st[sec].setExtBlockMetadata(x & 15, y & 15, z & 15, meta);
        if (l.modState != null && l.block instanceof net.mcreator.boh.compat.block.BohBlock
            && ((net.mcreator.boh.compat.block.BohBlock) l.block).getStateDefinition().needsExtended())
            net.mcreator.boh.compat.mc.world.level.block.state.ExtendedStateStore.get(world).setExt(x, y, z, l.modState.ext());
        if (l.block.getLightValue() > 0) lights.add(new int[] { x, y, z });
        touched.add(c);
        return true;
    }

    private static boolean ignored(StructurePlaceSettings s, String name) {
        if (name.equals("minecraft:structure_void") || name.equals("minecraft:structure_block") || name.equals("minecraft:jigsaw")) return true;
        for (StructureProcessor p : s.processors) if (p.ignores(name)) return true;
        return false;
    }

    private static boolean needsSupport(Block b) {
        return b == Blocks.torch || b == Blocks.redstone_torch || b == Blocks.unlit_redstone_torch || b == Blocks.ladder || b == Blocks.wall_sign
            || b == Blocks.standing_sign || b instanceof net.minecraft.block.BlockDoor || b instanceof net.minecraft.block.BlockBush
            || b == Blocks.vine || b == Blocks.lever || b instanceof net.minecraft.block.BlockButton || b == Blocks.rail || b == Blocks.redstone_wire
            || b == Blocks.carpet || b == Blocks.wooden_pressure_plate || b == Blocks.stone_pressure_plate || b == Blocks.tripwire_hook
            || b instanceof net.minecraft.block.BlockTrapDoor || b == Blocks.snow_layer || b == Blocks.bed;
    }

    private static void applyBlockEntity(World w, Info b, PState ps, int[] p, Random rand) {
        TileEntity te = w.getTileEntity(p[0], p[1], p[2]);
        if (te == null) return;
        NBTTagCompound nbt = b.nbt;
        if (te instanceof net.mcreator.boh.compat.mc.world.level.block.entity.RandomizableContainerBlockEntity) {
            NBTTagCompound c = (NBTTagCompound) nbt.copy();
            ItemNbt.convertItemList(c, "Items");
            c.setInteger("x", p[0]);
            c.setInteger("y", p[1]);
            c.setInteger("z", p[2]);
            te.readFromNBT(c);
            return;
        }
        if (te instanceof IInventory) {
            IInventory inv = (IInventory) te;
            NBTTagList items = nbt.getTagList("Items", 10);
            for (int i = 0; i < items.tagCount(); i++) {
                NBTTagCompound it = items.getCompoundTagAt(i);
                net.minecraft.item.ItemStack s = ItemNbt.toStack(it);
                int slot = it.getByte("Slot") & 255;
                if (s != null && slot < inv.getSizeInventory()) inv.setInventorySlotContents(slot, s);
            }
            if (nbt.hasKey("LootTable")) net.mcreator.boh.compat.loot.LootTables.fill(inv, new ResourceLocation(nbt.getString("LootTable")),
                nbt.hasKey("LootTableSeed") ? new Random(nbt.getLong("LootTableSeed")) : rand);
            return;
        }
        if (te instanceof TileEntitySign) {
            NBTTagCompound front = nbt.hasKey("front_text") ? nbt.getCompoundTag("front_text") : null;
            String[] lines = ((TileEntitySign) te).signText;
            for (int i = 0; i < 4; i++) {
                String json = front != null ? ((NBTTagList) front.getTagList("messages", 8)).getStringTagAt(i) : nbt.getString("Text" + (i + 1));
                lines[i] = ItemNbt.plainText(json);
            }
            return;
        }
        if (te instanceof TileEntitySkull) {
            String rot = ps.props.get("rotation");
            if (rot != null) ((TileEntitySkull) te).func_145903_a(Integer.parseInt(rot));
        }
    }

    private static void spawnEntity(World w, NBTTagCompound e, BlockPos origin, Mirror mirror, Rotation rot) {
        NBTTagCompound nbt = e.getCompoundTag("nbt");
        String id = nbt.getString("id");
        if (id.isEmpty() || id.equals("minecraft:item")) return;
        NBTTagList pos = e.getTagList("pos", 6);
        double ex = pos.func_150309_d(0), ey = pos.func_150309_d(1), ez = pos.func_150309_d(2);
        if (mirror == Mirror.LEFT_RIGHT) ez = -ez;
        else if (mirror == Mirror.FRONT_BACK) ex = -ex;
        double rx = ex, rz = ez;
        switch (rot) {
            case CLOCKWISE_90:
                rx = -ez;
                rz = ex;
                break;
            case CLOCKWISE_180:
                rx = -ex;
                rz = -ez;
                break;
            case COUNTERCLOCKWISE_90:
                rx = ez;
                rz = -ex;
                break;
            default:
        }
        Entity ent;
        if (id.equals("minecraft:villager")) ent = new EntityVillager(w);
        else {
            EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(id));
            ent = type == null ? null : type.create(w);
        }
        if (ent == null) return;
        NBTTagList r = nbt.getTagList("Rotation", 5);
        float yaw = r.tagCount() > 0 ? r.func_150308_e(0) : 0;
        yaw += 90 * turns(rot);
        ent.setLocationAndAngles(origin.getX() + rx, origin.getY() + ey, origin.getZ() + rz, yaw, 0);
        if (ent instanceof EntityLiving) {
            ((EntityLiving) ent).onSpawnWithEgg(null);
            if (nbt.getBoolean("PersistenceRequired")) ((EntityLiving) ent).func_110163_bv();
            if (nbt.getBoolean("NoAI") && ent instanceof net.mcreator.boh.compat.entity.BohMob) ((net.mcreator.boh.compat.entity.BohMob) ent).setNoAi(true);
        }
        w.spawnEntityInWorld(ent);
    }

    static NBTTagString unused() {
        return null;
    }
}
