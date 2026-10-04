package net.mcreator.boh.compat.block;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.loot.LootTables;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.tags.BlockTags;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.InteractionResult;
import net.mcreator.boh.compat.mc.world.item.TooltipFlag;
import net.mcreator.boh.compat.mc.world.item.context.BlockPlaceContext;
import net.mcreator.boh.compat.mc.world.level.block.EntityBlock;
import net.mcreator.boh.compat.mc.world.level.block.Mirror;
import net.mcreator.boh.compat.mc.world.level.block.RenderShape;
import net.mcreator.boh.compat.mc.world.level.block.Rotation;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Builder;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.StateDefinition;
import net.mcreator.boh.compat.mc.world.level.material.FluidState;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.BlockHitResult;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.mc.world.phys.shapes.CollisionContext;
import net.mcreator.boh.compat.mc.world.phys.shapes.Shapes;
import net.mcreator.boh.compat.mc.world.phys.shapes.VoxelShape;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Compat base for mod blocks: 1.20 Block hooks (states, shapes, use, tick, onPlace, onRemove, ...) bridged onto
 * the 1.7.10 Block callbacks. Properties live in metadata (plus {@code ExtendedStateStore} for large ones).
 */
public class BohBlock extends Block implements BlockState.HasStateDefinition {

    /** Positions whose next updateTick was scheduled by the mod (anything else is a random tick). */
    private static final Set<String> SCHEDULED = new HashSet<>();
    private static final ThreadLocal<Integer> PLACE_SIDE = ThreadLocal.withInitial(() -> 1);
    private static final ThreadLocal<float[]> PLACE_HIT = ThreadLocal.withInitial(() -> new float[] { 0.5f, 0.5f, 0.5f });

    protected final Properties properties;
    protected final StateDefinition stateDefinition;
    private BlockState defaultState;
    protected ResourceLocation registryName;
    public int renderType = 0;

    /** 1.20 Block.box: shape in pixel units (0..16). */
    public static VoxelShape box(double x1, double y1, double z1, double x2, double y2, double z2) {
        return net.mcreator.boh.compat.mc.world.phys.shapes.Shapes.box(x1 / 16.0, y1 / 16.0, z1 / 16.0, x2 / 16.0, y2 / 16.0, z2 / 16.0);
    }

    public BohBlock(Properties properties) {
        super(materialFor(properties));
        this.properties = properties == null ? Properties.of() : properties;
        Builder<Block, BlockState> builder = new Builder<>(this);
        createBlockStateDefinition(builder);
        stateDefinition = builder.create();
        defaultState = stateDefinition.any();
        setHardness(this.properties.instabreak ? 0 : this.properties.hardness);
        setResistance(this.properties.resistance / 3.0F);
        setStepSound(this.properties.sound.toVanilla());
        setTickRandomly(this.properties.randomTicks);
        slipperiness = this.properties.friction;
        int light = this.properties.lightLevel.applyAsInt(defaultState);
        if (light > 0) setLightLevel(light / 15.0F);
        opaque = isOpaqueCube();
        lightOpacity = opaque ? 255 : 0;
        if (this.properties.noOcclusion) setLightOpacity(0);
    }

    private static Material materialFor(Properties p) {
        if (p == null) return Material.rock;
        if (p.air) return Material.air;
        if (p.liquid) return Material.water;
        switch (p.sound.materialHint()) {
            case "wood":
                return Material.wood;
            case "plants":
                return p.noCollission ? Material.plants : Material.grass;
            case "iron":
            case "anvil":
                return Material.iron;
            case "glass":
                return Material.glass;
            case "cloth":
                return Material.cloth;
            case "sand":
                return Material.sand;
            case "ground":
                return Material.ground;
            case "snow":
                return Material.craftedSnow;
            case "clay":
                return Material.clay;
            default:
                return p.noCollission ? Material.circuits : Material.rock;
        }
    }

    public void onRegistered(ResourceLocation id) {
        registryName = id;
        BlockModels.configure(this, id);
        for (String tool : new String[] { "pickaxe", "axe", "shovel", "hoe" }) {
            if (BlockTags.create(new ResourceLocation("minecraft", "mineable/" + tool)).contains(id)) {
                int level = BlockTags.create(new ResourceLocation("minecraft", "needs_diamond_tool")).contains(id) ? 3
                    : BlockTags.create(new ResourceLocation("minecraft", "needs_iron_tool")).contains(id) ? 2
                        : BlockTags.create(new ResourceLocation("minecraft", "needs_stone_tool")).contains(id) ? 1 : 0;
                setHarvestLevel("hoe".equals(tool) ? "hoe" : tool, level);
            }
        }
    }

    public ResourceLocation registryName() {
        return registryName;
    }

    // ------------------------------------------------------------------ states

    @Override
    public StateDefinition getStateDefinition() {
        return stateDefinition;
    }

    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {}

    public final void registerDefaultState(BlockState s) {
        defaultState = s;
    }

    public BlockState defaultBlockState() {
        return defaultState;
    }

    protected BlockState stateAt(IBlockAccess w, int x, int y, int z) {
        // a chunk being generated isn't in the provider yet: reading it would load (generate) it again, recursively
        if (w instanceof net.minecraft.world.World && !((net.minecraft.world.World) w).blockExists(x, y, z)) return defaultBlockState();
        return M.getBlockState(w, new BlockPos(x, y, z));
    }

    // ------------------------------------------------------------------ 1.20 hooks (overridden by the mod)

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState();
    }

    public VoxelShape getShape(BlockState state, IBlockAccess world, BlockPos pos, CollisionContext context) {
        return Shapes.block();
    }

    public VoxelShape getCollisionShape(BlockState state, IBlockAccess world, BlockPos pos, CollisionContext context) {
        return properties.noCollission ? Shapes.empty() : getShape(state, world, pos, context);
    }

    public VoxelShape getVisualShape(BlockState state, IBlockAccess world, BlockPos pos, CollisionContext context) {
        return getCollisionShape(state, world, pos, context);
    }

    public int getLightBlock(BlockState state, IBlockAccess world, BlockPos pos) {
        return properties.noOcclusion ? 0 : 15;
    }

    public boolean propagatesSkylightDown(BlockState state, IBlockAccess world, BlockPos pos) {
        return properties.noOcclusion;
    }

    public int getFlammability(BlockState state, IBlockAccess world, BlockPos pos, Direction face) {
        return properties.ignitedByLava ? 5 : 0;
    }

    public int getFireSpreadSpeed(BlockState state, IBlockAccess world, BlockPos pos, Direction face) {
        return properties.ignitedByLava ? 5 : 0;
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state;
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
        return state;
    }

    public void onPlace(BlockState state, World world, BlockPos pos, BlockState oldState, boolean moving) {}

    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean moving) {}

    public void setPlacedBy(World world, BlockPos pos, BlockState state, EntityLivingBase placer, ItemStack stack) {}

    public void tick(BlockState state, WorldServer world, BlockPos pos, RandomSource random) {}

    public void randomTick(BlockState state, WorldServer world, BlockPos pos, RandomSource random) {
        if (properties.randomTicks) tick(state, world, pos, random);
    }

    public void animateTick(BlockState state, World world, BlockPos pos, RandomSource random) {}

    public InteractionResult use(BlockState state, World world, BlockPos pos, EntityPlayer player, InteractionHand hand, BlockHitResult hit) {
        return InteractionResult.PASS;
    }

    public void attack(BlockState state, World world, BlockPos pos, EntityPlayer player) {}

    public boolean triggerEvent(BlockState state, World world, BlockPos pos, int id, int param) {
        return false;
    }

    public boolean hasAnalogOutputSignal(BlockState state) {
        return false;
    }

    public int getAnalogOutputSignal(BlockState state, World world, BlockPos pos) {
        return 0;
    }

    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, World world, BlockPos pos, BlockPos facingPos) {
        return state;
    }

    public void neighborChanged(BlockState state, World world, BlockPos pos, Block neighbor, BlockPos fromPos, boolean moving) {}

    public FluidState getFluidState(BlockState state) {
        return FluidState.of(net.minecraft.init.Blocks.air, 0);
    }

    public boolean onDestroyedByPlayer(BlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest,
        FluidState fluid) {
        return world.setBlockToAir(pos.getX(), pos.getY(), pos.getZ());
    }

    public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {}

    public void stepOn(World world, BlockPos pos, BlockState state, Entity entity) {}

    public boolean isRandomlyTicking(BlockState state) {
        return properties.randomTicks;
    }

    public float getExplosionResistance(BlockState state, IBlockAccess world, BlockPos pos, Explosion explosion) {
        return properties.resistance;
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public List<ItemStack> getDrops(BlockState state, Object lootParams) {
        if (properties.noLootTable || registryName == null) return new ArrayList<>();
        ResourceLocation table = new ResourceLocation(registryName.getResourceDomain(), "blocks/" + registryName.getResourcePath());
        if (!LootTables.exists(table)) {
            List<ItemStack> l = new ArrayList<>();
            l.add(new ItemStack(this));
            return l;
        }
        return LootTables.roll(table, new Random(), state);
    }

    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return properties.replaceable;
    }

    public void wasExploded(World world, BlockPos pos, Explosion explosion) {}

    public boolean canSurvive(BlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }

    public ItemStack getCloneItemStack(IBlockAccess world, BlockPos pos, BlockState state) {
        return new ItemStack(this);
    }

    public void appendHoverText(ItemStack stack, IBlockAccess world, List<Component> list, TooltipFlag flag) {}

    public Object getBlockPathType(BlockState state, IBlockAccess world, BlockPos pos, Object mob) {
        return null;
    }

    public Object getMenuProvider(BlockState state, World world, BlockPos pos) {
        return null;
    }

    public float getSpeedFactor() {
        return properties.speedFactor;
    }

    public float getJumpFactor() {
        return properties.jumpFactor;
    }

    // ------------------------------------------------------------------ 1.7.10 bridge: lifecycle

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hx, float hy, float hz, int meta) {
        PLACE_SIDE.set(side);
        PLACE_HIT.set(new float[] { hx, hy, hz });
        return defaultState.meta();
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack stack) {
        BlockPos pos = new BlockPos(x, y, z);
        float[] hit = PLACE_HIT.get();
        BlockPlaceContext ctx = new BlockPlaceContext(world, placer instanceof EntityPlayer ? (EntityPlayer) placer : null,
            InteractionHand.MAIN_HAND, stack, pos, Direction.from3DDataValue(PLACE_SIDE.get()), new Vec3(x + hit[0], y + hit[1], z + hit[2]));
        BlockState s = getStateForPlacement(ctx);
        if (s == null) s = defaultState;
        if (s.getBlock() == this) M.setBlock(world, pos, s, 2);
        setPlacedBy(world, pos, s, placer, stack);
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        onPlace(stateAt(world, x, y, z), world, new BlockPos(x, y, z), BlockState.of(net.minecraft.init.Blocks.air), false);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        BlockState old = BlockState.of(block, meta);
        onRemove(old, world, new BlockPos(x, y, z), stateAt(world, x, y, z), false);
        if (stateDefinition.needsExtended())
            net.mcreator.boh.compat.mc.world.level.block.state.ExtendedStateStore.get(world).setExt(x, y, z, 0);
        super.breakBlock(world, x, y, z, block, meta);
    }

    public static void markScheduled(World w, int x, int y, int z) {
        synchronized (SCHEDULED) {
            SCHEDULED.add(w.provider.dimensionId + ":" + x + ":" + y + ":" + z);
        }
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        boolean scheduled;
        synchronized (SCHEDULED) {
            scheduled = SCHEDULED.remove(world.provider.dimensionId + ":" + x + ":" + y + ":" + z);
        }
        if (!(world instanceof WorldServer)) return;
        BlockState s = stateAt(world, x, y, z);
        if (scheduled) tick(s, (WorldServer) world, new BlockPos(x, y, z), RandomSource.wrap(rand));
        else randomTick(s, (WorldServer) world, new BlockPos(x, y, z), RandomSource.wrap(rand));
    }

    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        animateTick(stateAt(world, x, y, z), world, new BlockPos(x, y, z), RandomSource.wrap(rand));
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hx, float hy, float hz) {
        BlockHitResult hit = new BlockHitResult(new Vec3(x + hx, y + hy, z + hz), Direction.from3DDataValue(side), new BlockPos(x, y, z), false);
        InteractionResult r = use(stateAt(world, x, y, z), world, new BlockPos(x, y, z), player, InteractionHand.MAIN_HAND, hit);
        return r != null && r.consumesAction();
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
        attack(stateAt(world, x, y, z), world, new BlockPos(x, y, z), player);
    }

    @Override
    public boolean onBlockEventReceived(World world, int x, int y, int z, int id, int param) {
        super.onBlockEventReceived(world, x, y, z, id, param);
        return triggerEvent(stateAt(world, x, y, z), world, new BlockPos(x, y, z), id, param);
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return hasAnalogOutputSignal(defaultState);
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
        return getAnalogOutputSignal(stateAt(world, x, y, z), world, new BlockPos(x, y, z));
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor) {
        BlockPos pos = new BlockPos(x, y, z);
        BlockState s = stateAt(world, x, y, z);
        neighborChanged(s, world, pos, neighbor, pos, false);
        BlockState updated = s;
        for (Direction d : Direction.values()) {
            BlockPos np = pos.relative(d);
            updated = updateShape(updated, d, M.getBlockState(world, np), world, pos, np);
            if (updated == null || updated.getBlock() != this) break;
        }
        if (updated == null || updated.isAir()) world.func_147480_a(x, y, z, true);
        else if (!updated.equals(s)) M.setBlock(world, pos, updated, 3);
        if (!canSurvive(stateAt(world, x, y, z), world, pos)) world.func_147480_a(x, y, z, true);
    }

    @Override
    public boolean canBlockStay(World world, int x, int y, int z) {
        return canSurvive(stateAt(world, x, y, z), world, new BlockPos(x, y, z));
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return super.canPlaceBlockAt(world, x, y, z) && canSurvive(defaultState, world, new BlockPos(x, y, z));
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
        return onDestroyedByPlayer(stateAt(world, x, y, z), world, new BlockPos(x, y, z), player, willHarvest, null);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        entityInside(stateAt(world, x, y, z), world, new BlockPos(x, y, z), entity);
    }

    @Override
    public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
        stepOn(world, new BlockPos(x, y, z), stateAt(world, x, y, z), entity);
        float sf = getSpeedFactor();
        if (sf != 1.0F) {
            entity.motionX *= sf;
            entity.motionZ *= sf;
        }
    }

    @Override
    public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion) {
        wasExploded(world, new BlockPos(x, y, z), explosion);
    }

    @Override
    public float getExplosionResistance(Entity e, World world, int x, int y, int z, double ex, double ey, double ez) {
        return getExplosionResistance(stateAt(world, x, y, z), world, new BlockPos(x, y, z), null) / 5.0F;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune) {
        BlockState s = BlockState.of(this, meta);
        return new ArrayList<>(getDrops(s, null));
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
        ItemStack s = getCloneItemStack(world, new BlockPos(x, y, z), stateAt(world, x, y, z));
        return M.legacy(s);
    }

    @Override
    public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
        return properties.replaceable;
    }

    @Override
    public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return getFlammability(stateAt(world, x, y, z), world, new BlockPos(x, y, z), Direction.from3DDataValue(face.ordinal()));
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return getFireSpreadSpeed(stateAt(world, x, y, z), world, new BlockPos(x, y, z), Direction.from3DDataValue(face.ordinal()));
    }

    @Override
    public int getLightOpacity(IBlockAccess world, int x, int y, int z) {
        return getLightBlock(stateAt(world, x, y, z), world, new BlockPos(x, y, z));
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        if (world instanceof net.minecraft.world.World && !((net.minecraft.world.World) world).blockExists(x, y, z)) return getLightValue();
        Block b = world.getBlock(x, y, z);
        if (b != this) return b.getLightValue(world, x, y, z);
        return properties.lightLevel.applyAsInt(stateAt(world, x, y, z));
    }

    @Override
    public boolean hasTileEntity(int meta) {
        return this instanceof EntityBlock;
    }

    @Override
    public TileEntity createTileEntity(World world, int meta) {
        return this instanceof EntityBlock ? ((EntityBlock) this).newBlockEntity(BlockPos.ZERO, BlockState.of(this, meta)) : null;
    }

    @Override
    public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity) {
        return properties.pushReaction != net.mcreator.boh.compat.mc.world.level.material.PushReaction.BLOCK;
    }

    @Override
    public int getMobilityFlag() {
        switch (properties.pushReaction) {
            case DESTROY:
                return 1;
            case BLOCK:
                return 2;
            default:
                return 0;
        }
    }

    // ------------------------------------------------------------------ 1.7.10 bridge: shapes & rendering

    private VoxelShape shapeAt(IBlockAccess world, int x, int y, int z) {
        try {
            return getShape(stateAt(world, x, y, z), world, new BlockPos(x, y, z), CollisionContext.empty());
        } catch (RuntimeException e) {
            return Shapes.block();
        }
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list, Entity entity) {
        VoxelShape shape = getCollisionShape(stateAt(world, x, y, z), world, new BlockPos(x, y, z), CollisionContext.of(entity));
        for (AABB b : shape.toAabbs()) {
            AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(x + b.minX, y + b.minY, z + b.minZ, x + b.maxX, y + b.maxY, z + b.maxZ);
            if (mask.intersectsWith(bb)) list.add(bb);
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        VoxelShape shape = getCollisionShape(stateAt(world, x, y, z), world, new BlockPos(x, y, z), CollisionContext.empty());
        if (shape.isEmpty()) return null;
        AABB b = shape.bounds();
        return AxisAlignedBB.getBoundingBox(x + b.minX, y + b.minY, z + b.minZ, x + b.maxX, y + b.maxY, z + b.maxZ);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        VoxelShape shape = shapeAt(world, x, y, z);
        AABB b = shape.isEmpty() ? new AABB(0, 0, 0, 1, 1, 1) : shape.bounds();
        setBlockBounds((float) Math.max(0, b.minX), (float) Math.max(0, b.minY), (float) Math.max(0, b.minZ),
            (float) Math.min(1, b.maxX), (float) Math.min(1, b.maxY), (float) Math.min(1, b.maxZ));
    }

    @Override
    public void setBlockBoundsForItemRender() {
        setBlockBounds(0, 0, 0, 1, 1, 1);
    }

    @Override
    public boolean isOpaqueCube() {
        // called by Block.<init> before our fields exist
        return properties != null && !properties.noOcclusion;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return properties != null && !properties.noOcclusion && renderType == 0;
    }

    @Override
    public boolean isNormalCube(IBlockAccess world, int x, int y, int z) {
        if (properties.redstoneConductor != null)
            return properties.redstoneConductor.test(stateAt(world, x, y, z), world, new BlockPos(x, y, z));
        return super.isNormalCube(world, x, y, z);
    }

    @Override
    public int getRenderType() {
        return renderType;
    }

    @Override
    public int getRenderBlockPass() {
        return BlockModels.renderPass(this);
    }

    @Override
    public boolean canRenderInPass(int pass) {
        return pass == getRenderBlockPass();
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        BlockModels.registerIcons(this, reg);
    }

    @Override
    public net.minecraft.util.IIcon getIcon(int side, int meta) {
        net.minecraft.util.IIcon i = BlockModels.icon(this, side, meta);
        return i != null ? i : blockIcon;
    }

    public void setBlockIcon(net.minecraft.util.IIcon icon) {
        blockIcon = icon;
    }
}
