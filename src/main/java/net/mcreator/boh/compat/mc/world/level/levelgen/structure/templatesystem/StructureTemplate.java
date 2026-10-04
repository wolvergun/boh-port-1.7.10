package net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.entity.BohMob;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.loot.LootTables;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Vec3i;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.level.block.Mirror;
import net.mcreator.boh.compat.mc.world.level.block.Rotation;
import net.mcreator.boh.compat.mc.world.level.block.entity.RandomizableContainerBlockEntity;
import net.mcreator.boh.compat.world.ItemNbt;
import net.mcreator.boh.compat.world.VanillaStates;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.nbt.NBTBase.NBTPrimitive;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class StructureTemplate {
    private final List<StructureTemplate.PState> palette = new ArrayList<>();
    private final List<StructureTemplate.Info> blocks = new ArrayList<>();
    private final List<NBTTagCompound> entities = new ArrayList<>();
    private int sx;
    private int sy;
    private int sz;

    void load(NBTTagCompound tag) {
        NBTTagList size = tag.getTagList("size", 3);
        if (size.tagCount() == 3) {
            this.sx = intAt(size, 0);
            this.sy = intAt(size, 1);
            this.sz = intAt(size, 2);
        }

        NBTTagList pal = tag.getTagList("palette", 10);
        if (pal.tagCount() == 0 && tag.hasKey("palettes")) {
            NBTTagList pl = tag.getTagList("palettes", 9);
            if (pl.tagCount() > 0) {
                pal = (NBTTagList)((NBTTagList)pl.copy()).removeTag(0);
            }
        }

        for (int i = 0; i < pal.tagCount(); i++) {
            NBTTagCompound s = pal.getCompoundTagAt(i);
            Map<String, String> props = new HashMap<>();
            NBTTagCompound p = s.getCompoundTag("Properties");

            for (Object k : p.func_150296_c()) {
                props.put((String)k, p.getString((String)k));
            }

            this.palette.add(new StructureTemplate.PState(s.getString("Name"), props));
        }

        NBTTagList bl = tag.getTagList("blocks", 10);

        for (int i = 0; i < bl.tagCount(); i++) {
            NBTTagCompound b = bl.getCompoundTagAt(i);
            NBTTagList pos = b.getTagList("pos", 3);
            this.blocks
                .add(
                    new StructureTemplate.Info(
                        intAt(pos, 0), intAt(pos, 1), intAt(pos, 2), b.getInteger("state"), b.hasKey("nbt") ? b.getCompoundTag("nbt") : null
                    )
                );
        }

        NBTTagList el = tag.getTagList("entities", 10);

        for (int i = 0; i < el.tagCount(); i++) {
            this.entities.add(el.getCompoundTagAt(i));
        }
    }

    private static int intAt(NBTTagList l, int i) {
        NBTBase b = ((NBTTagList)l.copy()).removeTag(i);
        return b instanceof NBTPrimitive ? ((NBTPrimitive)b).func_150287_d() : 0;
    }

    public boolean isEmpty() {
        return this.blocks.isEmpty();
    }

    public Vec3i getSize() {
        return new Vec3i(this.sx, this.sy, this.sz);
    }

    public Vec3i getSize(Rotation r) {
        return r != Rotation.CLOCKWISE_90 && r != Rotation.COUNTERCLOCKWISE_90 ? this.getSize() : new Vec3i(this.sz, this.sy, this.sx);
    }

    static int[] transform(int x, int y, int z, Mirror mirror, Rotation rot) {
        if (mirror == Mirror.LEFT_RIGHT) {
            z = -z;
        } else if (mirror == Mirror.FRONT_BACK) {
            x = -x;
        }

        switch (rot) {
            case COUNTERCLOCKWISE_90:
                return new int[]{z, y, -x};
            case CLOCKWISE_90:
                return new int[]{-z, y, x};
            case CLOCKWISE_180:
                return new int[]{-x, y, -z};
            default:
                return new int[]{x, y, z};
        }
    }

    private static int turns(Rotation r) {
        switch (r) {
            case COUNTERCLOCKWISE_90:
                return 3;
            case CLOCKWISE_90:
                return 1;
            case CLOCKWISE_180:
                return 2;
            default:
                return 0;
        }
    }

    public boolean placeInWorld(World world, BlockPos pos, BlockPos pivot, StructurePlaceSettings settings, Random random, int flags) {
        if (world != null && !world.isRemote && !this.blocks.isEmpty()) {
            Mirror mirror = settings.mirror;
            int t = turns(settings.rotation);
            boolean mx = mirror == Mirror.FRONT_BACK;
            if (mirror == Mirror.LEFT_RIGHT) {
                mx = true;
                t = t + 2 & 3;
            }

            List<Object[]> deferred = new ArrayList<>();

            for (int pass = 0; pass < 2; pass++) {
                for (StructureTemplate.Info b : this.blocks) {
                    if (b.state >= 0 && b.state < this.palette.size()) {
                        StructureTemplate.PState ps = this.palette.get(b.state);
                        if (!ignored(settings, ps.name)) {
                            VanillaStates.Legacy l = VanillaStates.convert(ps.name, VanillaStates.rotate(ps.props, t, mx));
                            if (l != null) {
                                boolean attach = needsSupport(l.block);
                                if (pass == 0 != attach) {
                                    int[] p = transform(b.x, b.y, b.z, mirror, settings.rotation);
                                    int wx = pos.getX() + p[0];
                                    int wy = pos.getY() + p[1];
                                    int wz = pos.getZ() + p[2];
                                    if (wy >= 0 && wy <= 255) {
                                        if (l.modState != null) {
                                            M.setBlock(world, new BlockPos(wx, wy, wz), l.modState, 2);
                                        } else {
                                            world.setBlock(wx, wy, wz, l.block, l.meta, 2);
                                        }

                                        if (b.nbt != null) {
                                            deferred.add(new Object[]{b, ps, new int[]{wx, wy, wz}});
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            for (Object[] d : deferred) {
                applyBlockEntity(world, (StructureTemplate.Info)d[0], (StructureTemplate.PState)d[1], (int[])d[2], random);
            }

            if (!settings.ignoreEntities) {
                for (NBTTagCompound e : this.entities) {
                    spawnEntity(world, e, pos, mirror, settings.rotation);
                }
            }

            if ((flags & 1) != 0) {
                for (StructureTemplate.Info bx : this.blocks) {
                    int[] p = transform(bx.x, bx.y, bx.z, mirror, settings.rotation);
                    int wx = pos.getX() + p[0];
                    int wy = pos.getY() + p[1];
                    int wz = pos.getZ() + p[2];
                    if (wy >= 0 && wy <= 255) {
                        world.notifyBlockChange(wx, wy, wz, world.getBlock(wx, wy, wz));
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean placeInWorld(World world, BlockPos pos, BlockPos pivot, StructurePlaceSettings settings, Object random, int flags) {
        return this.placeInWorld(world, pos, pivot, settings, random instanceof Random ? (Random)random : world.rand, flags);
    }

    private static boolean ignored(StructurePlaceSettings s, String name) {
        if (!name.equals("minecraft:structure_void") && !name.equals("minecraft:structure_block") && !name.equals("minecraft:jigsaw")) {
            for (StructureProcessor p : s.processors) {
                if (p.ignores(name)) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }

    private static boolean needsSupport(Block b) {
        return b == Blocks.torch
            || b == Blocks.redstone_torch
            || b == Blocks.unlit_redstone_torch
            || b == Blocks.ladder
            || b == Blocks.wall_sign
            || b == Blocks.standing_sign
            || b instanceof BlockDoor
            || b instanceof BlockBush
            || b == Blocks.vine
            || b == Blocks.lever
            || b instanceof BlockButton
            || b == Blocks.rail
            || b == Blocks.redstone_wire
            || b == Blocks.carpet
            || b == Blocks.wooden_pressure_plate
            || b == Blocks.stone_pressure_plate
            || b == Blocks.tripwire_hook
            || b instanceof BlockTrapDoor
            || b == Blocks.snow_layer
            || b == Blocks.bed;
    }

    private static void applyBlockEntity(World w, StructureTemplate.Info b, StructureTemplate.PState ps, int[] p, Random rand) {
        TileEntity te = w.getTileEntity(p[0], p[1], p[2]);
        if (te != null) {
            NBTTagCompound nbt = b.nbt;
            if (te instanceof RandomizableContainerBlockEntity) {
                NBTTagCompound c = (NBTTagCompound)nbt.copy();
                ItemNbt.convertItemList(c, "Items");
                c.setInteger("x", p[0]);
                c.setInteger("y", p[1]);
                c.setInteger("z", p[2]);
                te.readFromNBT(c);
            } else if (te instanceof IInventory inv) {
                NBTTagList items = nbt.getTagList("Items", 10);

                for (int i = 0; i < items.tagCount(); i++) {
                    NBTTagCompound it = items.getCompoundTagAt(i);
                    ItemStack s = ItemNbt.toStack(it);
                    int slot = it.getByte("Slot") & 255;
                    if (s != null && slot < inv.getSizeInventory()) {
                        inv.setInventorySlotContents(slot, s);
                    }
                }

                if (nbt.hasKey("LootTable")) {
                    LootTables.fill(
                        inv,
                        new ResourceLocation(nbt.getString("LootTable")),
                        nbt.hasKey("LootTableSeed") ? new Random(nbt.getLong("LootTableSeed")) : rand
                    );
                }
            } else if (te instanceof TileEntitySign) {
                NBTTagCompound front = nbt.hasKey("front_text") ? nbt.getCompoundTag("front_text") : null;
                String[] lines = ((TileEntitySign)te).signText;

                for (int ix = 0; ix < 4; ix++) {
                    String json = front != null ? front.getTagList("messages", 8).getStringTagAt(ix) : nbt.getString("Text" + (ix + 1));
                    lines[ix] = ItemNbt.plainText(json);
                }
            } else {
                if (te instanceof TileEntitySkull) {
                    String rot = ps.props.get("rotation");
                    if (rot != null) {
                        ((TileEntitySkull)te).func_145903_a(Integer.parseInt(rot));
                    }
                }
            }
        }
    }

    private static void spawnEntity(World w, NBTTagCompound e, BlockPos origin, Mirror mirror, Rotation rot) {
        NBTTagCompound nbt = e.getCompoundTag("nbt");
        String id = nbt.getString("id");
        if (!id.isEmpty() && !id.equals("minecraft:item")) {
            NBTTagList pos = e.getTagList("pos", 6);
            double ex = pos.func_150309_d(0);
            double ey = pos.func_150309_d(1);
            double ez = pos.func_150309_d(2);
            if (mirror == Mirror.LEFT_RIGHT) {
                ez = -ez;
            } else if (mirror == Mirror.FRONT_BACK) {
                ex = -ex;
            }

            double rx = ex;
            double rz = ez;
            switch (rot) {
                case COUNTERCLOCKWISE_90:
                    rx = ez;
                    rz = -ex;
                    break;
                case CLOCKWISE_90:
                    rx = -ez;
                    rz = ex;
                    break;
                case CLOCKWISE_180:
                    rx = -ex;
                    rz = -ez;
            }

            Entity ent;
            if (id.equals("minecraft:villager")) {
                ent = new EntityVillager(w);
            } else {
                EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(id));
                ent = type == null ? null : type.create(w);
            }

            if (ent != null) {
                NBTTagList r = nbt.getTagList("Rotation", 5);
                float yaw = r.tagCount() > 0 ? r.func_150308_e(0) : 0.0F;
                yaw += 90 * turns(rot);
                ent.setLocationAndAngles(origin.getX() + rx, origin.getY() + ey, origin.getZ() + rz, yaw, 0.0F);
                if (ent instanceof EntityLiving) {
                    ((EntityLiving)ent).onSpawnWithEgg(null);
                    if (nbt.getBoolean("PersistenceRequired")) {
                        ((EntityLiving)ent).func_110163_bv();
                    }

                    if (nbt.getBoolean("NoAI") && ent instanceof BohMob) {
                        ((BohMob)ent).setNoAi(true);
                    }
                }

                w.spawnEntityInWorld(ent);
            }
        }
    }

    static NBTTagString unused() {
        return null;
    }

    static final class Info {
        final int x;
        final int y;
        final int z;
        final int state;
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
}
