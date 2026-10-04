package net.mcreator.boh.compat;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Holder;
import net.mcreator.boh.compat.mc.core.Registry;
import net.mcreator.boh.compat.mc.core.RegistryAccess;
import net.mcreator.boh.compat.mc.core.particles.ParticleOptions;
import net.mcreator.boh.compat.mc.core.particles.ParticleType;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.Difficulty;
import net.mcreator.boh.compat.mc.world.DifficultyInstance;
import net.mcreator.boh.compat.mc.world.damagesource.DamageSources;
import net.mcreator.boh.compat.mc.world.level.ClipContext;
import net.mcreator.boh.compat.mc.world.level.ClipFluid;
import net.mcreator.boh.compat.mc.world.level.ExplosionInteraction;
import net.mcreator.boh.compat.mc.world.level.LightLayer;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.ExtendedStateStore;
import net.mcreator.boh.compat.mc.world.level.block.state.StateDefinition;
import net.mcreator.boh.compat.mc.world.level.material.FluidState;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.BlockHitResult;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.net.CompatNetwork;
import net.mcreator.boh.compat.world.Dimensions;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.storage.WorldInfo;

/** World / block helpers. */
public class MWorld extends MItem {

    public static final int BLOCK_UPDATE_ALL = 3;
    public static final int BLOCK_OUTLINE = 3;
    public static final ResourceKey<World> OVERWORLD = Dimensions.OVERWORLD;
    public static final ResourceKey<World> NETHER = Dimensions.NETHER;
    public static final ResourceKey<World> END = Dimensions.END;

    protected MWorld() {}

    // ------------------------------------------------------------------ world basics

    public static boolean isClientSide(World w) {
        return w == null || w.isRemote;
    }

    public static boolean isClientSide(IBlockAccess w) {
        return !(w instanceof World) || ((World) w).isRemote;
    }

    public static MinecraftServer getServer(World w) {
        return w == null || w.isRemote ? null : MinecraftServer.getServer();
    }

    public static ResourceKey<World> dimension(World w) {
        return Dimensions.key(w.provider.dimensionId);
    }

    public static net.minecraft.world.WorldServer getLevel(MinecraftServer server, ResourceKey<World> key) {
        return server == null ? null : server.worldServerForDimension(Dimensions.id(key));
    }

    public static net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem.StructureTemplateManager getStructureManager(World w) {
        return net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem.StructureTemplateManager.INSTANCE;
    }

    public static java.util.Random random(World w) {
        return w.rand;
    }

    public static WorldServer overworld(MinecraftServer server) {
        return server.worldServerForDimension(0);
    }

    public static Iterable<WorldServer> getAllLevels(MinecraftServer server) {
        return java.util.Arrays.asList(server.worldServers);
    }

    public static long getGameTime(World w) {
        return w.getTotalWorldTime();
    }

    public static long getDayTime(World w) {
        return w.getWorldTime();
    }

    public static void setDayTime(World w, long t) {
        w.setWorldTime(t);
    }

    public static boolean isDay(World w) {
        return w.isDaytime();
    }

    public static boolean isNight(World w) {
        return !w.isDaytime();
    }

    public static WorldInfo getLevelData(World w) {
        return w.getWorldInfo();
    }

    public static boolean isRaining(WorldInfo info) {
        return info.isRaining();
    }

    public static boolean isRaining(World w) {
        return w.isRaining();
    }

    public static boolean isThundering(World w) {
        return w.isThundering();
    }

    public static void setRaining(WorldInfo info, boolean b) {
        info.setRaining(b);
    }

    public static int getXSpawn(WorldInfo info) {
        return info.getSpawnX();
    }

    public static int getYSpawn(WorldInfo info) {
        return info.getSpawnY();
    }

    public static int getZSpawn(WorldInfo info) {
        return info.getSpawnZ();
    }

    public static Difficulty getDifficulty(World w) {
        return Difficulty.of(w.difficultySetting);
    }

    public static DifficultyInstance getCurrentDifficultyAt(World w, BlockPos pos) {
        return new DifficultyInstance(w);
    }

    public static net.mcreator.boh.compat.mc.util.RandomSource getRandom(World w) {
        return net.mcreator.boh.compat.mc.util.RandomSource.wrap(w.rand);
    }

    public static float getTimeOfDay(World w, float partial) {
        return w.getCelestialAngle(partial);
    }

    public static float getSunAngle(World w, float partial) {
        return w.getCelestialAngleRadians(partial);
    }

    public static int getMoonPhase(World w) {
        return w.getMoonPhase();
    }

    public static float getRainLevel(World w, float partial) {
        return w.getRainStrength(partial);
    }

    public static float getStarBrightness(World w, float partial) {
        return MClientImpl.starBrightness(w, partial);
    }

    public static net.minecraft.scoreboard.Scoreboard getScoreboard(World w) {
        return w.getScoreboard();
    }

    public static RegistryAccess registryAccess(World w) {
        return RegistryAccess.INSTANCE;
    }

    public static RegistryAccess registryAccess(MinecraftServer s) {
        return RegistryAccess.INSTANCE;
    }

    public static <T> Registry<T> registryOrThrow(RegistryAccess a, ResourceKey<?> key) {
        return a.registryOrThrow(key);
    }

    @SuppressWarnings("unchecked")
    public static <T> Holder.Reference<T> getHolderOrThrow(Registry<T> reg, ResourceKey<?> key) {
        return reg.getHolderOrThrow((ResourceKey<T>) key);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getOrThrow(Registry<T> reg, ResourceKey<?> key) {
        return reg.getOrThrow((ResourceKey<T>) key);
    }

    // ------------------------------------------------------------------ damage sources

    public static DamageSource new_DamageSource(Holder.Reference<?> type) {
        return DamageSources.create(type.key());
    }

    public static DamageSource new_DamageSource(Holder.Reference<?> type, Entity attacker) {
        return DamageSources.create(type.key(), attacker);
    }

    public static DamageSource new_DamageSource(Holder.Reference<?> type, Entity direct, Entity attacker) {
        return DamageSources.create(type.key(), direct, attacker);
    }

    public static boolean is(DamageSource src, ResourceKey<?> type) {
        return DamageSources.is(src, type);
    }

    public static Entity getEntity(DamageSource src) {
        return src == null ? null : src.getEntity();
    }

    public static Entity getDirectEntity(DamageSource src) {
        return src == null ? null : src.getSourceOfDamage();
    }

    public static String getMsgId(DamageSource src) {
        return src.getDamageType();
    }

    public static Vec3 getSourcePosition(DamageSource src) {
        Entity e = src.getSourceOfDamage();
        return e == null ? null : new Vec3(e.posX, e.posY, e.posZ);
    }

    // ------------------------------------------------------------------ blocks

    public static BlockState getBlockState(IBlockAccess w, BlockPos pos) {
        Block b = w.getBlock(pos.getX(), pos.getY(), pos.getZ());
        int meta = w.getBlockMetadata(pos.getX(), pos.getY(), pos.getZ());
        int ext = 0;
        if (b instanceof BohBlock && ((BohBlock) b).getStateDefinition().needsExtended() && w instanceof World)
            ext = ExtendedStateStore.get((World) w).getExt(pos.getX(), pos.getY(), pos.getZ());
        return BlockState.of(b, meta, ext);
    }

    public static boolean setBlock(World w, BlockPos pos, BlockState state, int flags) {
        Block b = state == null ? Blocks.air : state.getBlock();
        boolean ok = w.setBlock(pos.getX(), pos.getY(), pos.getZ(), b, state == null ? 0 : state.meta(), flags);
        if (b instanceof BohBlock && ((BohBlock) b).getStateDefinition().needsExtended())
            ExtendedStateStore.get(w).setExt(pos.getX(), pos.getY(), pos.getZ(), state.ext());
        return ok;
    }

    public static boolean setBlockAndUpdate(World w, BlockPos pos, BlockState state) {
        return setBlock(w, pos, state, 3);
    }

    public static boolean destroyBlock(World w, BlockPos pos, boolean drop) {
        return w.func_147480_a(pos.getX(), pos.getY(), pos.getZ(), drop);
    }

    public static boolean destroyBlock(World w, BlockPos pos, boolean drop, Entity breaker) {
        return destroyBlock(w, pos, drop);
    }

    public static boolean removeBlock(World w, BlockPos pos, boolean moving) {
        return w.setBlockToAir(pos.getX(), pos.getY(), pos.getZ());
    }

    public static boolean isEmptyBlock(World w, BlockPos pos) {
        return w.isAirBlock(pos.getX(), pos.getY(), pos.getZ());
    }

    public static TileEntity getBlockEntity(IBlockAccess w, BlockPos pos) {
        return w.getTileEntity(pos.getX(), pos.getY(), pos.getZ());
    }

    public static Block getBlock(BlockState s) {
        return s == null ? Blocks.air : s.getBlock();
    }

    public static BlockState defaultBlockState(Block b) {
        return b instanceof BohBlock ? ((BohBlock) b).defaultBlockState() : BlockState.of(b, 0);
    }

    public static StateDefinition getStateDefinition(Block b) {
        return b instanceof BohBlock ? ((BohBlock) b).getStateDefinition() : StateDefinition.empty(b);
    }

    public static boolean is(BlockState s, Block b) {
        return s != null && s.getBlock() == b;
    }

    public static boolean is(BlockState s, net.mcreator.boh.compat.mc.tags.TagKey<?> tag) {
        return s != null && s.is(tag);
    }

    public static boolean isAir(BlockState s) {
        return s == null || s.isAir();
    }

    public static boolean canOcclude(BlockState s) {
        return s.canOcclude();
    }

    public static boolean isSolid(BlockState s) {
        return s.isSolid();
    }

    public static boolean liquid(BlockState s) {
        return s.liquid();
    }

    public static boolean canBeReplaced(BlockState s) {
        return s.canBeReplaced();
    }

    public static int getLightEmission(BlockState s) {
        return s.getLightEmission();
    }

    public static float getDestroySpeed(BlockState s, World w, BlockPos pos) {
        return s.getBlock().getBlockHardness(w, pos.getX(), pos.getY(), pos.getZ());
    }

    public static boolean isRedstoneConductor(BlockState s, IBlockAccess w, BlockPos pos) {
        return s.getBlock().isNormalCube();
    }

    public static int blockStateId(BlockState s) {
        return Block.getIdFromBlock(s.getBlock()) + (s.meta() << 12);
    }

    public static BlockState blockStateById(int id) {
        return BlockState.of(Block.getBlockById(id & 4095), id >> 12);
    }

    public static FluidState getFluidState(BlockState s) {
        return FluidState.of(s.getBlock(), s.meta());
    }

    public static FluidState getFluidState(IBlockAccess w, BlockPos pos) {
        return getFluidState(getBlockState(w, pos));
    }

    public static boolean isEmpty(FluidState f) {
        return f == null || f.isEmpty();
    }

    public static net.mcreator.boh.compat.mc.world.level.material.Fluid getType(FluidState f) {
        return f.getType();
    }

    public static boolean isSource(FluidState f) {
        return f.isSource();
    }

    public static void scheduleTick(World w, BlockPos pos, Block b, int delay) {
        net.mcreator.boh.compat.block.BohBlock.markScheduled(w, pos.getX(), pos.getY(), pos.getZ());
        w.scheduleBlockUpdate(pos.getX(), pos.getY(), pos.getZ(), b, delay);
    }

    public static void updateNeighborsAt(World w, BlockPos pos, Block b) {
        w.notifyBlocksOfNeighborChange(pos.getX(), pos.getY(), pos.getZ(), b);
    }

    public static void updateNeighbourForOutputSignal(World w, BlockPos pos, Block b) {
        w.func_147453_f(pos.getX(), pos.getY(), pos.getZ(), b);
    }

    public static void sendBlockUpdated(World w, BlockPos pos, BlockState oldS, BlockState newS, int flags) {
        w.markBlockForUpdate(pos.getX(), pos.getY(), pos.getZ());
    }

    public static boolean hasChunkAt(World w, BlockPos pos) {
        return w.blockExists(pos.getX(), pos.getY(), pos.getZ());
    }

    public static boolean canSeeSkyFromBelowWater(World w, BlockPos pos) {
        return w.canBlockSeeTheSky(pos.getX(), pos.getY(), pos.getZ());
    }

    public static boolean canSeeSky(World w, BlockPos pos) {
        return w.canBlockSeeTheSky(pos.getX(), pos.getY(), pos.getZ());
    }

    public static int getMaxLocalRawBrightness(World w, BlockPos pos) {
        return w.getBlockLightValue(pos.getX(), pos.getY(), pos.getZ());
    }

    public static int getBrightness(World w, LightLayer layer, BlockPos pos) {
        return w.getSavedLightValue(layer.toVanilla(), pos.getX(), pos.getY(), pos.getZ());
    }

    public static int getHeight(World w, net.mcreator.boh.compat.mc.world.level.levelgen.Types type, int x, int z) {
        return w.getHeightValue(x, z);
    }

    public static double getBlockFloorHeight(World w, BlockPos pos) {
        Block b = w.getBlock(pos.getX(), pos.getY(), pos.getZ());
        b.setBlockBoundsBasedOnState(w, pos.getX(), pos.getY(), pos.getZ());
        return b.getCollisionBoundingBoxFromPool(w, pos.getX(), pos.getY(), pos.getZ()) == null ? 0 : b.getBlockBoundsMaxY();
    }

    public static Holder<BiomeGenBase> getBiome(World w, BlockPos pos) {
        return Holder.direct(w.getBiomeGenForCoords(pos.getX(), pos.getZ()));
    }

    public static boolean coldEnoughToSnow(BiomeGenBase b, BlockPos pos) {
        return b.getFloatTemperature(pos.getX(), pos.getY(), pos.getZ()) < 0.15F;
    }

    public static BlockHitResult clip(World w, ClipContext ctx) {
        MovingObjectPosition mop = w.func_147447_a(ctx.from.toVanilla(), ctx.to.toVanilla(), ctx.fluid != ClipFluid.NONE,
            false, false);
        return BlockHitResult.of(mop, ctx.to);
    }

    // ------------------------------------------------------------------ entities in the world

    public static boolean addFreshEntity(World w, Entity e) {
        if (e == null) return false;
        if (e instanceof net.minecraft.entity.effect.EntityLightningBolt) return w.addWeatherEffect(e);
        return w.spawnEntityInWorld(e);
    }

    public static void addFreshEntityWithPassengers(World w, Entity e) {
        addFreshEntity(w, e);
        if (e.riddenByEntity != null) addFreshEntity(w, e.riddenByEntity);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Entity> List<T> getEntitiesOfClass(World w, Class<T> cls, AABB box, Predicate<? super T> filter) {
        List<T> raw = w.getEntitiesWithinAABB(cls, box.toVanilla());
        if (filter == null) return raw;
        List<T> out = new ArrayList<>(raw.size());
        for (T e : raw) if (filter.test(e)) out.add(e);
        return out;
    }

    public static <T extends Entity> List<T> getEntitiesOfClass(World w, Class<T> cls, AABB box) {
        return getEntitiesOfClass(w, cls, box, null);
    }

    @SuppressWarnings("unchecked")
    public static List<Entity> getEntities(World w, Entity except, AABB box, Predicate<? super Entity> filter) {
        List<Entity> raw = w.getEntitiesWithinAABBExcludingEntity(except, box.toVanilla());
        List<Entity> out = new ArrayList<>(raw.size());
        for (Entity e : raw) if (filter == null || filter.test(e)) out.add(e);
        return out;
    }

    public static List<Entity> getEntities(World w, Entity except, AABB box) {
        return getEntities(w, except, box, null);
    }

    public static Entity getEntity(World w, int id) {
        return w.getEntityByID(id);
    }

    public static EntityPlayer getNearestPlayer(World w, double x, double y, double z, double dist, boolean creativeToo) {
        return w.getClosestPlayer(x, y, z, dist);
    }

    public static EntityPlayer getNearestPlayer(World w, double x, double y, double z, double dist, Predicate<Entity> pred) {
        EntityPlayer best = null;
        double bestD = Double.MAX_VALUE;
        for (Object o : w.playerEntities) {
            EntityPlayer p = (EntityPlayer) o;
            if (pred != null && !pred.test(p)) continue;
            double d = p.getDistanceSq(x, y, z);
            if ((dist < 0 || d < dist * dist) && d < bestD) {
                best = p;
                bestD = d;
            }
        }
        return best;
    }

    public static EntityPlayer getNearestPlayer(World w, Entity e, double dist) {
        return w.getClosestPlayerToEntity(e, dist);
    }

    @SuppressWarnings("unchecked")
    public static List<EntityPlayer> players(World w) {
        return w.playerEntities;
    }

    // ------------------------------------------------------------------ sounds / effects

    public static void playSound(World w, EntityPlayer except, BlockPos pos, SoundEvent sound, SoundSource src, float vol, float pitch) {
        playSound(w, except, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, sound, src, vol, pitch);
    }

    public static void playSound(World w, EntityPlayer except, double x, double y, double z, SoundEvent sound, SoundSource src, float vol,
        float pitch) {
        if (sound == null || sound.legacyName().isEmpty()) return;
        if (w.isRemote) {
            if (except == null) w.playSound(x, y, z, sound.legacyName(), vol, pitch, false);
            return;
        }
        w.playSoundEffect(x, y, z, sound.legacyName(), vol, pitch);
    }

    public static void playSound(World w, EntityPlayer except, Entity at, SoundEvent sound, SoundSource src, float vol, float pitch) {
        playSound(w, except, at.posX, at.posY, at.posZ, sound, src, vol, pitch);
    }

    public static void playLocalSound(World w, double x, double y, double z, SoundEvent sound, SoundSource src, float vol, float pitch,
        boolean delay) {
        if (sound == null || sound.legacyName().isEmpty() || !w.isRemote) return;
        w.playSound(x, y, z, sound.legacyName(), vol, pitch, delay);
    }

    public static void playLocalSound(World w, BlockPos p, SoundEvent sound, SoundSource src, float vol, float pitch, boolean delay) {
        playLocalSound(w, p.getX() + 0.5, p.getY() + 0.5, p.getZ() + 0.5, sound, src, vol, pitch, delay);
    }

    public static void levelEvent(World w, int event, BlockPos pos, int data) {
        w.playAuxSFX(event, pos.getX(), pos.getY(), pos.getZ(), data);
    }

    public static void levelEvent(World w, EntityPlayer player, int event, BlockPos pos, int data) {
        w.playAuxSFXAtEntity(player, event, pos.getX(), pos.getY(), pos.getZ(), data);
    }

    public static net.minecraft.world.Explosion explode(World w, Entity source, double x, double y, double z, float power,
        ExplosionInteraction mode) {
        return explode(w, source, x, y, z, power, false, mode);
    }

    public static net.minecraft.world.Explosion explode(World w, Entity source, double x, double y, double z, float power, boolean fire,
        ExplosionInteraction mode) {
        boolean breakBlocks = mode != ExplosionInteraction.NONE;
        if (mode == ExplosionInteraction.MOB) breakBlocks = w.getGameRules().getGameRuleBooleanValue("mobGriefing");
        return w.newExplosion(source, x, y, z, power, fire, breakBlocks);
    }

    public static void addParticle(World w, ParticleOptions p, double x, double y, double z, double dx, double dy, double dz) {
        ParticleType<?> t = p.getType();
        if (t.legacyName() != null) w.spawnParticle(t.legacyName(), x, y, z, dx, dy, dz);
        else if (w.isRemote) MClientImpl.spawnModParticle(t, x, y, z, dx, dy, dz);
    }

    public static void addAlwaysVisibleParticle(World w, ParticleOptions p, double x, double y, double z, double dx, double dy, double dz) {
        addParticle(w, p, x, y, z, dx, dy, dz);
    }

    /** Server-side particle burst (1.20 ServerLevel.sendParticles). */
    public static int sendParticles(WorldServer w, ParticleOptions p, double x, double y, double z, int count, double dx, double dy,
        double dz, double speed) {
        ParticleType<?> t = p.getType();
        if (t.legacyName() != null) w.func_147487_a(t.legacyName(), x, y, z, count, dx, dy, dz, speed);
        else CompatNetwork.sendParticles(w, t, x, y, z, count, dx, dy, dz, speed);
        return count;
    }

    public static int sendParticles(World w, ParticleOptions p, double x, double y, double z, int count, double dx, double dy, double dz,
        double speed) {
        if (w instanceof WorldServer) return sendParticles((WorldServer) w, p, x, y, z, count, dx, dy, dz, speed);
        return 0;
    }
}
