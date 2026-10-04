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
import net.mcreator.boh.compat.mc.world.level.block.state.ExtendedStateStore;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.StateDefinition;
import net.mcreator.boh.compat.mc.world.level.material.FluidState;
import net.mcreator.boh.compat.mc.world.level.material.PushReaction;
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
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;

public class BohBlock extends Block implements BlockState.HasStateDefinition {
    private static final Set<String> SCHEDULED = new HashSet<>();
    private static final ThreadLocal<Integer> PLACE_SIDE = ThreadLocal.withInitial(() -> 1);
    private static final ThreadLocal<float[]> PLACE_HIT = ThreadLocal.withInitial(() -> new float[]{0.5F, 0.5F, 0.5F});
    protected final Properties properties;
    protected final StateDefinition stateDefinition;
    private BlockState defaultState;
    protected ResourceLocation registryName;
    public int renderType = 0;

    public static VoxelShape box(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Shapes.box(x1 / 16.0, y1 / 16.0, z1 / 16.0, x2 / 16.0, y2 / 16.0, z2 / 16.0);
    }

    public BohBlock(Properties properties) {
        super(materialFor(properties));
        this.properties = properties == null ? Properties.of() : properties;
        Builder<Block, BlockState> builder = new Builder<>(this);
        this.createBlockStateDefinition(builder);
        this.stateDefinition = builder.create();
        this.defaultState = this.stateDefinition.any();
        this.setHardness(this.properties.instabreak ? 0.0F : this.properties.hardness);
        this.setResistance(this.properties.resistance / 3.0F);
        this.setStepSound(this.properties.sound.toVanilla());
        this.setTickRandomly(this.properties.randomTicks);
        this.slipperiness = this.properties.friction;
        int light = this.properties.lightLevel.applyAsInt(this.defaultState);
        if (light > 0) {
            this.setLightLevel(light / 15.0F);
        }

        this.opaque = this.isOpaqueCube();
        this.lightOpacity = this.opaque ? 255 : 0;
        if (this.properties.noOcclusion) {
            this.setLightOpacity(0);
        }
    }

    private static Material materialFor(Properties p) {
        if (p == null) {
            return Material.rock;
        } else if (p.air) {
            return Material.air;
        } else if (p.liquid) {
            return Material.water;
        } else {
            String var1 = p.sound.materialHint();
            switch (var1) {
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
    }

    public void onRegistered(ResourceLocation id) {
        this.registryName = id;
        BlockModels.configure(this, id);

        for (String tool : new String[]{"pickaxe", "axe", "shovel", "hoe"}) {
            if (BlockTags.create(new ResourceLocation("minecraft", "mineable/" + tool)).contains(id)) {
                int level = BlockTags.create(new ResourceLocation("minecraft", "needs_diamond_tool")).contains(id)
                    ? 3
                    : (
                        BlockTags.create(new ResourceLocation("minecraft", "needs_iron_tool")).contains(id)
                            ? 2
                            : (BlockTags.create(new ResourceLocation("minecraft", "needs_stone_tool")).contains(id) ? 1 : 0)
                    );
                this.setHarvestLevel("hoe".equals(tool) ? "hoe" : tool, level);
            }
        }
    }

    public ResourceLocation registryName() {
        return this.registryName;
    }

    @Override
    public StateDefinition getStateDefinition() {
        return this.stateDefinition;
    }

    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
    }

    public final void registerDefaultState(BlockState s) {
        this.defaultState = s;
    }

    public BlockState defaultBlockState() {
        return this.defaultState;
    }

    protected BlockState stateAt(IBlockAccess w, int x, int y, int z) {
        return w instanceof World && !((World)w).blockExists(x, y, z) ? this.defaultBlockState() : M.getBlockState(w, new BlockPos(x, y, z));
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState();
    }

    public VoxelShape getShape(BlockState state, IBlockAccess world, BlockPos pos, CollisionContext context) {
        return Shapes.block();
    }

    public VoxelShape getCollisionShape(BlockState state, IBlockAccess world, BlockPos pos, CollisionContext context) {
        return this.properties.noCollission ? Shapes.empty() : this.getShape(state, world, pos, context);
    }

    public VoxelShape getVisualShape(BlockState state, IBlockAccess world, BlockPos pos, CollisionContext context) {
        return this.getCollisionShape(state, world, pos, context);
    }

    public int getLightBlock(BlockState state, IBlockAccess world, BlockPos pos) {
        return this.properties.noOcclusion ? 0 : 15;
    }

    public boolean propagatesSkylightDown(BlockState state, IBlockAccess world, BlockPos pos) {
        return this.properties.noOcclusion;
    }

    public int getFlammability(BlockState state, IBlockAccess world, BlockPos pos, Direction face) {
        return this.properties.ignitedByLava ? 5 : 0;
    }

    public int getFireSpreadSpeed(BlockState state, IBlockAccess world, BlockPos pos, Direction face) {
        return this.properties.ignitedByLava ? 5 : 0;
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state;
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
        return state;
    }

    public void onPlace(BlockState state, World world, BlockPos pos, BlockState oldState, boolean moving) {
    }

    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean moving) {
    }

    public void setPlacedBy(World world, BlockPos pos, BlockState state, EntityLivingBase placer, ItemStack stack) {
    }

    public void tick(BlockState state, WorldServer world, BlockPos pos, RandomSource random) {
    }

    public void randomTick(BlockState state, WorldServer world, BlockPos pos, RandomSource random) {
        if (this.properties.randomTicks) {
            this.tick(state, world, pos, random);
        }
    }

    public void animateTick(BlockState state, World world, BlockPos pos, RandomSource random) {
    }

    public InteractionResult use(BlockState state, World world, BlockPos pos, EntityPlayer player, InteractionHand hand, BlockHitResult hit) {
        return InteractionResult.PASS;
    }

    public void attack(BlockState state, World world, BlockPos pos, EntityPlayer player) {
    }

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

    public void neighborChanged(BlockState state, World world, BlockPos pos, Block neighbor, BlockPos fromPos, boolean moving) {
    }

    public FluidState getFluidState(BlockState state) {
        return FluidState.of(Blocks.air, 0);
    }

    public boolean onDestroyedByPlayer(BlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest, FluidState fluid) {
        return world.setBlockToAir(pos.getX(), pos.getY(), pos.getZ());
    }

    public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {
    }

    public void stepOn(World world, BlockPos pos, BlockState state, Entity entity) {
    }

    public boolean isRandomlyTicking(BlockState state) {
        return this.properties.randomTicks;
    }

    public float getExplosionResistance(BlockState state, IBlockAccess world, BlockPos pos, Explosion explosion) {
        return this.properties.resistance;
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public List<ItemStack> getDrops(BlockState state, Object lootParams) {
        if (!this.properties.noLootTable && this.registryName != null) {
            ResourceLocation table = new ResourceLocation(this.registryName.getResourceDomain(), "blocks/" + this.registryName.getResourcePath());
            if (!LootTables.exists(table)) {
                List<ItemStack> l = new ArrayList<>();
                l.add(new ItemStack(this));
                return l;
            } else {
                return LootTables.roll(table, new Random(), state);
            }
        } else {
            return new ArrayList<>();
        }
    }

    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return this.properties.replaceable;
    }

    public void wasExploded(World world, BlockPos pos, Explosion explosion) {
    }

    public boolean canSurvive(BlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }

    public ItemStack getCloneItemStack(IBlockAccess world, BlockPos pos, BlockState state) {
        return new ItemStack(this);
    }

    public void appendHoverText(ItemStack stack, IBlockAccess world, List<Component> list, TooltipFlag flag) {
    }

    public Object getBlockPathType(BlockState state, IBlockAccess world, BlockPos pos, Object mob) {
        return null;
    }

    public Object getMenuProvider(BlockState state, World world, BlockPos pos) {
        return null;
    }

    public float getSpeedFactor() {
        return this.properties.speedFactor;
    }

    public float getJumpFactor() {
        return this.properties.jumpFactor;
    }

    public int onBlockPlaced(World world, int x, int y, int z, int side, float hx, float hy, float hz, int meta) {
        PLACE_SIDE.set(side);
        PLACE_HIT.set(new float[]{hx, hy, hz});
        return this.defaultState.meta();
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack stack) {
        BlockPos pos = new BlockPos(x, y, z);
        float[] hit = PLACE_HIT.get();
        BlockPlaceContext ctx = new BlockPlaceContext(
            world,
            placer instanceof EntityPlayer ? (EntityPlayer)placer : null,
            InteractionHand.MAIN_HAND,
            stack,
            pos,
            Direction.from3DDataValue(PLACE_SIDE.get()),
            new Vec3(x + hit[0], y + hit[1], z + hit[2])
        );
        BlockState s = this.getStateForPlacement(ctx);
        if (s == null) {
            s = this.defaultState;
        }

        if (s.getBlock() == this) {
            M.setBlock(world, pos, s, 2);
        }

        this.setPlacedBy(world, pos, s, placer, stack);
    }

    public void onBlockAdded(World world, int x, int y, int z) {
        this.onPlace(this.stateAt(world, x, y, z), world, new BlockPos(x, y, z), BlockState.of(Blocks.air), false);
    }

    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        BlockState old = BlockState.of(block, meta);
        this.onRemove(old, world, new BlockPos(x, y, z), this.stateAt(world, x, y, z), false);
        if (this.stateDefinition.needsExtended()) {
            ExtendedStateStore.get(world).setExt(x, y, z, 0);
        }

        super.breakBlock(world, x, y, z, block, meta);
    }

    public static void markScheduled(World w, int x, int y, int z) {
        synchronized (SCHEDULED) {
            SCHEDULED.add(w.provider.dimensionId + ":" + x + ":" + y + ":" + z);
        }
    }

    public void updateTick(World world, int x, int y, int z, Random rand) {
        boolean scheduled;
        synchronized (SCHEDULED) {
            scheduled = SCHEDULED.remove(world.provider.dimensionId + ":" + x + ":" + y + ":" + z);
        }

        if (world instanceof WorldServer) {
            BlockState s = this.stateAt(world, x, y, z);
            if (scheduled) {
                this.tick(s, (WorldServer)world, new BlockPos(x, y, z), RandomSource.wrap(rand));
            } else {
                this.randomTick(s, (WorldServer)world, new BlockPos(x, y, z), RandomSource.wrap(rand));
            }
        }
    }

    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        this.animateTick(this.stateAt(world, x, y, z), world, new BlockPos(x, y, z), RandomSource.wrap(rand));
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hx, float hy, float hz) {
        BlockHitResult hit = new BlockHitResult(new Vec3(x + hx, y + hy, z + hz), Direction.from3DDataValue(side), new BlockPos(x, y, z), false);
        InteractionResult r = this.use(this.stateAt(world, x, y, z), world, new BlockPos(x, y, z), player, InteractionHand.MAIN_HAND, hit);
        return r != null && r.consumesAction();
    }

    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
        this.attack(this.stateAt(world, x, y, z), world, new BlockPos(x, y, z), player);
    }

    public boolean onBlockEventReceived(World world, int x, int y, int z, int id, int param) {
        super.onBlockEventReceived(world, x, y, z, id, param);
        return this.triggerEvent(this.stateAt(world, x, y, z), world, new BlockPos(x, y, z), id, param);
    }

    public boolean hasComparatorInputOverride() {
        return this.hasAnalogOutputSignal(this.defaultState);
    }

    public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
        return this.getAnalogOutputSignal(this.stateAt(world, x, y, z), world, new BlockPos(x, y, z));
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor) {
        BlockPos pos = new BlockPos(x, y, z);
        BlockState s = this.stateAt(world, x, y, z);
        this.neighborChanged(s, world, pos, neighbor, pos, false);
        BlockState updated = s;

        for (Direction d : Direction.values()) {
            BlockPos np = pos.relative(d);
            updated = this.updateShape(updated, d, M.getBlockState(world, np), world, pos, np);
            if (updated == null || updated.getBlock() != this) {
                break;
            }
        }

        if (updated == null || updated.isAir()) {
            world.func_147480_a(x, y, z, true);
        } else if (!updated.equals(s)) {
            M.setBlock(world, pos, updated, 3);
        }

        if (!this.canSurvive(this.stateAt(world, x, y, z), world, pos)) {
            world.func_147480_a(x, y, z, true);
        }
    }

    public boolean canBlockStay(World world, int x, int y, int z) {
        return this.canSurvive(this.stateAt(world, x, y, z), world, new BlockPos(x, y, z));
    }

    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return super.canPlaceBlockAt(world, x, y, z) && this.canSurvive(this.defaultState, world, new BlockPos(x, y, z));
    }

    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
        return this.onDestroyedByPlayer(this.stateAt(world, x, y, z), world, new BlockPos(x, y, z), player, willHarvest, null);
    }

    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        this.entityInside(this.stateAt(world, x, y, z), world, new BlockPos(x, y, z), entity);
    }

    public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
        this.stepOn(world, new BlockPos(x, y, z), this.stateAt(world, x, y, z), entity);
        float sf = this.getSpeedFactor();
        if (sf != 1.0F) {
            entity.motionX *= sf;
            entity.motionZ *= sf;
        }
    }

    public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion) {
        this.wasExploded(world, new BlockPos(x, y, z), explosion);
    }

    public float getExplosionResistance(Entity e, World world, int x, int y, int z, double ex, double ey, double ez) {
        return this.getExplosionResistance(this.stateAt(world, x, y, z), world, new BlockPos(x, y, z), null) / 5.0F;
    }

    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune) {
        BlockState s = BlockState.of(this, meta);
        return new ArrayList<>(this.getDrops(s, null));
    }

    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
        ItemStack s = this.getCloneItemStack(world, new BlockPos(x, y, z), this.stateAt(world, x, y, z));
        return M.legacy(s);
    }

    public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
        return this.properties.replaceable;
    }

    public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return this.getFlammability(this.stateAt(world, x, y, z), world, new BlockPos(x, y, z), Direction.from3DDataValue(face.ordinal()));
    }

    public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return this.getFireSpreadSpeed(this.stateAt(world, x, y, z), world, new BlockPos(x, y, z), Direction.from3DDataValue(face.ordinal()));
    }

    public int getLightOpacity(IBlockAccess world, int x, int y, int z) {
        return this.getLightBlock(this.stateAt(world, x, y, z), world, new BlockPos(x, y, z));
    }

    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        if (world instanceof World && !((World)world).blockExists(x, y, z)) {
            return this.getLightValue();
        } else {
            Block b = world.getBlock(x, y, z);
            return b != this ? b.getLightValue(world, x, y, z) : this.properties.lightLevel.applyAsInt(this.stateAt(world, x, y, z));
        }
    }

    public boolean hasTileEntity(int meta) {
        return this instanceof EntityBlock;
    }

    public TileEntity createTileEntity(World world, int meta) {
        return this instanceof EntityBlock ? ((EntityBlock)this).newBlockEntity(BlockPos.ZERO, BlockState.of(this, meta)) : null;
    }

    public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity) {
        return this.properties.pushReaction != PushReaction.BLOCK;
    }

    public int getMobilityFlag() {
        switch (this.properties.pushReaction) {
            case DESTROY:
                return 1;
            case BLOCK:
                return 2;
            default:
                return 0;
        }
    }

    private VoxelShape shapeAt(IBlockAccess world, int x, int y, int z) {
        try {
            return this.getShape(this.stateAt(world, x, y, z), world, new BlockPos(x, y, z), CollisionContext.empty());
        } catch (RuntimeException var6) {
            return Shapes.block();
        }
    }

    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list, Entity entity) {
        VoxelShape shape = this.getCollisionShape(this.stateAt(world, x, y, z), world, new BlockPos(x, y, z), CollisionContext.of(entity));

        for (AABB b : shape.toAabbs()) {
            AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(x + b.minX, y + b.minY, z + b.minZ, x + b.maxX, y + b.maxY, z + b.maxZ);
            if (mask.intersectsWith(bb)) {
                list.add(bb);
            }
        }
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        VoxelShape shape = this.getCollisionShape(this.stateAt(world, x, y, z), world, new BlockPos(x, y, z), CollisionContext.empty());
        if (shape.isEmpty()) {
            return null;
        } else {
            AABB b = shape.bounds();
            return AxisAlignedBB.getBoundingBox(x + b.minX, y + b.minY, z + b.minZ, x + b.maxX, y + b.maxY, z + b.maxZ);
        }
    }

    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        VoxelShape shape = this.shapeAt(world, x, y, z);
        AABB b = shape.isEmpty() ? new AABB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0) : shape.bounds();
        this.setBlockBounds(
            (float)Math.max(0.0, b.minX),
            (float)Math.max(0.0, b.minY),
            (float)Math.max(0.0, b.minZ),
            (float)Math.min(1.0, b.maxX),
            (float)Math.min(1.0, b.maxY),
            (float)Math.min(1.0, b.maxZ)
        );
    }

    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public boolean isOpaqueCube() {
        return this.properties != null && !this.properties.noOcclusion;
    }

    public boolean renderAsNormalBlock() {
        return this.properties != null && !this.properties.noOcclusion && this.renderType == 0;
    }

    public boolean isNormalCube(IBlockAccess world, int x, int y, int z) {
        return this.properties.redstoneConductor != null
            ? this.properties.redstoneConductor.test(this.stateAt(world, x, y, z), world, new BlockPos(x, y, z))
            : super.isNormalCube(world, x, y, z);
    }

    public int getRenderType() {
        return this.renderType;
    }

    public int getRenderBlockPass() {
        return BlockModels.renderPass(this);
    }

    public boolean canRenderInPass(int pass) {
        return pass == this.getRenderBlockPass();
    }

    public void registerBlockIcons(IIconRegister reg) {
        BlockModels.registerIcons(this, reg);
    }

    public IIcon getIcon(int side, int meta) {
        IIcon i = BlockModels.icon(this, side, meta);
        return i != null ? i : this.blockIcon;
    }

    public void setBlockIcon(IIcon icon) {
        this.blockIcon = icon;
    }
}
