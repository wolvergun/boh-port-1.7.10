package net.mcreator.boh.compat;

import java.util.AbstractList;
import java.util.List;
import java.util.UUID;

import net.mcreator.boh.compat.mc.advancements.PlayerAdvancements;
import net.mcreator.boh.compat.mc.client.Camera;
import net.mcreator.boh.compat.mc.commands.Commands;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.mcreator.boh.compat.mc.server.ServerAdvancementManager;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.level.GameType;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.scores.criteria.RenderType;
import net.mcreator.boh.compat.world.Dimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.mcreator.boh.compat.forge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.gameevent.TickEvent;

/** Event accessors (1.20 getter names over 1.7.10 event fields) and assorted scoreboard/client helpers. */
public class MEvent extends MEntity {

    protected MEvent() {}

    // ------------------------------------------------------------------ generic

    public static void setCanceled(Event e, boolean b) {
        if (e != null && e.isCancelable()) e.setCanceled(b);
    }

    public static boolean isCanceled(Event e) {
        return e != null && e.isCanceled();
    }

    public static boolean isCancelable(Event e) {
        return e != null && e.isCancelable();
    }

    public static void setResult(Event e, Event.Result r) {
        e.setResult(r);
    }

    public static Event.Result getResult(Event e) {
        return e.getResult();
    }

    // ------------------------------------------------------------------ entity events

    public static EntityLivingBase getEntity(LivingEvent e) {
        return e.entityLiving;
    }

    public static EntityPlayer getEntity(PlayerEvent e) {
        return e.entityPlayer;
    }

    public static Entity getEntity(EntityEvent e) {
        return e.entity;
    }

    public static EntityPlayer getEntity(cpw.mods.fml.common.gameevent.PlayerEvent e) {
        return e.player;
    }

    public static EntityPlayer player(TickEvent.PlayerTickEvent e) {
        return e.player;
    }

    public static EntityPlayer getEntity(TickEvent.PlayerTickEvent e) {
        return e.player;
    }

    public static World level(EntityEvent e) {
        return e.entity.worldObj;
    }

    public static World getLevel(PlayerEvent e) {
        return e.entityPlayer.worldObj;
    }

    public static World getLevel(EntityJoinWorldEvent e) {
        return e.world;
    }

    public static DamageSource getSource(LivingAttackEvent e) {
        return e.source;
    }

    public static DamageSource getSource(LivingHurtEvent e) {
        return e.source;
    }

    public static DamageSource getSource(LivingDeathEvent e) {
        return e.source;
    }

    public static float getAmount(LivingAttackEvent e) {
        return e.ammount;
    }

    public static float getAmount(LivingHurtEvent e) {
        return e.ammount;
    }

    public static void setAmount(LivingHurtEvent e, float f) {
        e.ammount = f;
    }

    public static Entity getLightning(EntityStruckByLightningEvent e) {
        return e.lightning;
    }

    public static ResourceKey<World> getDimension(EntityTravelToDimensionEvent e) {
        return e.getDimension();
    }

    public static ResourceKey<World> getTo(cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent e) {
        return Dimensions.key(e.toDim);
    }

    public static ResourceKey<World> getFrom(cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent e) {
        return Dimensions.key(e.fromDim);
    }

    public static EntityPlayer getOriginal(PlayerEvent.Clone e) {
        return e.original;
    }

    public static boolean isWasDeath(PlayerEvent.Clone e) {
        return e.wasDeath;
    }

    public static BlockPos getPos(PlayerSleepInBedEvent e) {
        return new BlockPos(e.x, e.y, e.z);
    }

    public static Entity getTarget(EntityInteractEvent e) {
        return e.target;
    }

    public static BlockPos getPos(EntityInteractEvent e) {
        return BlockPos.containing(e.target.posX, e.target.boundingBox.minY, e.target.posZ);
    }

    public static InteractionHand getHand(EntityInteractEvent e) {
        return InteractionHand.MAIN_HAND;
    }

    public static ItemStack getOriginal(PlayerDestroyItemEvent e) {
        return stack(e.original);
    }

    public static ItemStack getItemStack(ItemTooltipEvent e) {
        return stack(e.itemStack);
    }

    /** The tooltip as a 1.20 component list, writing through to the 1.7.10 string list. */
    public static List<Component> getToolTip(ItemTooltipEvent e) {
        final List<String> lines = e.toolTip;
        return new AbstractList<Component>() {

            @Override
            public Component get(int i) {
                return Component.literal(lines.get(i));
            }

            @Override
            public int size() {
                return lines.size();
            }

            @Override
            public void add(int i, Component c) {
                lines.add(Math.max(0, Math.min(i, lines.size())), c == null ? "" : c.getFormattedText());
            }

            @Override
            public Component set(int i, Component c) {
                return Component.literal(lines.set(i, c.getFormattedText()));
            }

            @Override
            public Component remove(int i) {
                return Component.literal(lines.remove(i));
            }
        };
    }

    // ------------------------------------------------------------------ server, commands, advancements

    public static Commands getCommands(MinecraftServer s) {
        return Commands.INSTANCE;
    }

    public static PlayerAdvancements getAdvancements(EntityPlayerMP p) {
        return new PlayerAdvancements(p);
    }

    public static ServerAdvancementManager getAdvancements(MinecraftServer s) {
        return ServerAdvancementManager.INSTANCE;
    }

    // ------------------------------------------------------------------ scoreboard

    public static ScoreObjective getObjective(Scoreboard sb, String name) {
        return sb.getObjective(name);
    }

    public static ScoreObjective addObjective(Scoreboard sb, String name, IScoreObjectiveCriteria crit, Component display,
        RenderType type) {
        ScoreObjective o = sb.getObjective(name);
        if (o != null) return o;
        o = sb.addScoreObjective(name, crit);
        if (display != null) o.setDisplayName(display.getString());
        return o;
    }

    public static Score getOrCreatePlayerScore(Scoreboard sb, String name, ScoreObjective obj) {
        // a score without objective would crash scoreboard saving; give a detached one instead
        if (obj == null) return new Score(new Scoreboard(), new ScoreObjective(new Scoreboard(), "dummy", net.minecraft.scoreboard.IScoreObjectiveCriteria.field_96641_b), name);
        return sb.func_96529_a(name, obj);
    }

    public static int getScore(Score s) {
        return s.getScorePoints();
    }

    public static void setScore(Score s, int v) {
        s.setScorePoints(v);
    }

    public static boolean addPlayerToTeam(Scoreboard sb, String name, ScorePlayerTeam team) {
        return sb.func_151392_a(name, team.getRegisteredName());
    }

    // ------------------------------------------------------------------ misc world / entity

    public static EntityPlayer player(InventoryPlayer inv) {
        return inv.player;
    }

    public static boolean triggerEvent(TileEntity te, int id, int param) {
        return te.receiveClientEvent(id, param);
    }

    public static boolean canSurvive(BlockState state, World w, BlockPos pos) {
        if (state.getBlock() instanceof net.mcreator.boh.compat.block.BohBlock)
            return ((net.mcreator.boh.compat.block.BohBlock) state.getBlock()).canSurvive(state, w, pos);
        return state.getBlock().canBlockStay(w, pos.getX(), pos.getY(), pos.getZ());
    }

    public static void broadcastBreakEvent(EntityLivingBase e, InteractionHand hand) {
        if (e instanceof EntityPlayer && e.getHeldItem() != null) ((EntityPlayer) e).renderBrokenItemStack(e.getHeldItem());
    }

    public static EntitySmallFireball new_EntitySmallFireball(EntityType<EntitySmallFireball> type, World w) {
        return new EntitySmallFireball(w);
    }

    public static EntityLargeFireball new_EntityLargeFireball(EntityType<EntityLargeFireball> type, World w) {
        return new EntityLargeFireball(w);
    }

    public static void setYBodyRot(Entity e, float v) {
        if (e instanceof EntityLivingBase) ((EntityLivingBase) e).renderYawOffset = v;
    }

    public static double distanceToSqr(Entity a, Entity b) {
        return a.getDistanceSqToEntity(b);
    }

    public static double distanceToSqr(Entity a, net.mcreator.boh.compat.mc.world.phys.Vec3 v) {
        return a.getDistanceSq(v.x, v.y, v.z);
    }

    public static double distanceToSqr(Entity a, double x, double y, double z) {
        return a.getDistanceSq(x, y, z);
    }

    public static void scheduleTick(World w, BlockPos pos, net.mcreator.boh.compat.mc.world.level.material.Fluid f, int delay) {
        w.scheduleBlockUpdate(pos.getX(), pos.getY(), pos.getZ(), w.getBlock(pos.getX(), pos.getY(), pos.getZ()), delay);
    }

    public static World level(TileEntity te) {
        return te.getWorldObj();
    }

    public static BlockPos getBlockPos(TileEntity te) {
        return new BlockPos(te.xCoord, te.yCoord, te.zCoord);
    }

    public static BlockState getBlockState(TileEntity te) {
        return te.getWorldObj() == null ? BlockState.of(te.getBlockType()) : getBlockState(te.getWorldObj(), getBlockPos(te));
    }

    public static net.minecraft.world.WorldServer getLevel(World w) {
        return w instanceof net.minecraft.world.WorldServer ? (net.minecraft.world.WorldServer) w : null;
    }

    public static net.mcreator.boh.compat.mc.world.level.saveddata.DimensionDataStorage getDataStorage(World w) {
        return net.mcreator.boh.compat.mc.world.level.saveddata.DimensionDataStorage.of(w);
    }

    public static void revive(Entity e) {
        e.isDead = false;
    }

    public static BlockState rotate(BlockState s, net.mcreator.boh.compat.mc.world.level.block.Rotation r) {
        return s.getBlock() instanceof net.mcreator.boh.compat.block.BohBlock ? ((net.mcreator.boh.compat.block.BohBlock) s.getBlock()).rotate(s, r) : s;
    }

    public static BlockState mirror(BlockState s, net.mcreator.boh.compat.mc.world.level.block.Mirror m) {
        return s.getBlock() instanceof net.mcreator.boh.compat.block.BohBlock ? ((net.mcreator.boh.compat.block.BohBlock) s.getBlock()).mirror(s, m) : s;
    }

    public static void set(java.util.concurrent.atomic.AtomicInteger a, int v) {
        a.set(v);
    }

    private static final java.util.Map<TileEntity, net.minecraft.nbt.NBTTagCompound> TILE_DATA = new java.util.WeakHashMap<>();

    /** 1.20 BlockEntity.getPersistentData (kept in memory for tiles that do not save it themselves). */
    public static net.minecraft.nbt.NBTTagCompound getPersistentData(TileEntity te) {
        if (te instanceof net.mcreator.boh.compat.mc.world.level.block.entity.RandomizableContainerBlockEntity)
            return ((net.mcreator.boh.compat.mc.world.level.block.entity.RandomizableContainerBlockEntity) te).getPersistentData();
        return TILE_DATA.computeIfAbsent(te, k -> new net.minecraft.nbt.NBTTagCompound());
    }

    public static net.mcreator.boh.compat.mc.client.renderer.DimensionSpecialEffects effects(World w) {
        return net.mcreator.boh.compat.forge.client.DimensionSpecialEffectsManager.getForType(dimension(w).location());
    }

    public static ItemStack split(ItemStack s, int n) {
        ItemStack l = legacy(s);
        return l == null ? EMPTY : stack(l.splitStack(n));
    }

    /** 1.20 Inventory.items as a live list view (empty slots read as EMPTY). */
    public static java.util.List<ItemStack> items(InventoryPlayer inv) {
        return slots(inv.mainInventory);
    }

    public static java.util.List<ItemStack> armor(InventoryPlayer inv) {
        return slots(inv.armorInventory);
    }

    private static java.util.List<ItemStack> slots(ItemStack[] arr) {
        return new java.util.AbstractList<ItemStack>() {

            @Override
            public ItemStack get(int i) {
                return stack(arr[i]);
            }

            @Override
            public ItemStack set(int i, ItemStack s) {
                ItemStack old = stack(arr[i]);
                arr[i] = legacy(s);
                return old;
            }

            @Override
            public int size() {
                return arr.length;
            }
        };
    }

    public static net.mcreator.boh.compat.mc.client.renderer.entity.EntityRenderDispatcher getEntityRenderDispatcher(Minecraft mc) {
        return net.mcreator.boh.compat.mc.client.renderer.entity.EntityRenderDispatcher.INSTANCE;
    }

    public static net.minecraft.util.ResourceLocation getSkinTextureLocation(net.minecraft.client.entity.AbstractClientPlayer p) {
        return MClientImpl.skin(p);
    }

    public static net.minecraft.client.gui.GuiScreen screen(Minecraft mc) {
        return MClientImpl.screen();
    }

    public static net.mcreator.boh.compat.mc.client.BossOverlay gui(Minecraft mc) {
        return net.mcreator.boh.compat.mc.client.BossOverlay.INSTANCE;
    }

    public static int blockStateId(BlockState s) {
        return net.minecraft.block.Block.getIdFromBlock(s.getBlock()) + (s.meta() << 12);
    }

    public static net.mcreator.boh.compat.mc.world.phys.Vec3 getSkyColor(World w, net.mcreator.boh.compat.mc.world.phys.Vec3 pos, float pt) {
        return MClientImpl.skyColor(w, pt);
    }

    public static double getHorizonHeight(net.minecraft.world.storage.WorldInfo info, World w) {
        return w.provider.getHorizon();
    }

    public static ScorePlayerTeam getPlayerTeam(Scoreboard sb, String name) {
        return sb.getTeam(name);
    }

    public static boolean isVillage(net.minecraft.world.WorldServer w, BlockPos pos) {
        return w.villageCollectionObj != null && w.villageCollectionObj.findNearestVillage(pos.getX(), pos.getY(), pos.getZ(), 32) != null;
    }

    public static World level(cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent e) {
        return e.world;
    }

    public static boolean is(net.mcreator.boh.compat.mc.core.Holder<net.minecraft.world.biome.BiomeGenBase> h, net.minecraft.util.ResourceLocation id) {
        net.minecraft.world.biome.BiomeGenBase b = h == null ? null : h.value();
        if (b == null) return false;
        net.minecraft.util.ResourceLocation key = net.mcreator.boh.compat.world.Biomes.keyOf(b);
        return key != null && key.equals(id);
    }

    public static void turnOnLightLayer(net.mcreator.boh.compat.client.LightTexture t) {
        MClientImpl.lightmap(true);
    }

    public static void turnOffLightLayer(net.mcreator.boh.compat.client.LightTexture t) {
        MClientImpl.lightmap(false);
    }

    public static void setByPlayer(net.minecraft.inventory.Slot s, ItemStack stack) {
        s.putStack(legacy(stack));
    }

    public static void setPathfindingMalus(Entity e, Object type, float malus) {
        if (e instanceof net.minecraft.entity.EntityLiving && "WATER".equals(String.valueOf(type)) && malus >= 0)
            ((net.minecraft.entity.EntityLiving) e).getNavigator().setAvoidsWater(false);
    }

    public static boolean isUnobstructed(World w, Entity e) {
        return w.checkNoEntityCollision(e.boundingBox, e);
    }

    public static void calculateEntityAnimation(EntityLivingBase e, boolean flying) {}

    /** Calls the mod's own read(tag) on saved data. */
    public static void read(Object data, net.minecraft.nbt.NBTTagCompound tag) {
        try {
            data.getClass().getMethod("read", net.minecraft.nbt.NBTTagCompound.class).invoke(data, tag);
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static net.mcreator.boh.compat.mc.world.phys.Vec3 getOffset(BlockState s, net.minecraft.world.IBlockAccess w, BlockPos pos) {
        return new net.mcreator.boh.compat.mc.world.phys.Vec3(0, 0, 0);
    }

    public static boolean add(InventoryPlayer inv, ItemStack s) {
        ItemStack l = legacy(s);
        return l == null || inv.addItemStackToInventory(l);
    }

    public static net.minecraft.entity.ai.attributes.AttributeModifier new_AttributeModifier(java.util.UUID id, String name, double amount,
        net.mcreator.boh.compat.mc.world.entity.ai.attributes.Operation op) {
        return new net.minecraft.entity.ai.attributes.AttributeModifier(id, name, amount, op.ordinal());
    }

    // ------------------------------------------------------------------ capabilities

    public static <T> net.mcreator.boh.compat.forge.common.util.LazyOptional<T> getCapability(Entity e,
        net.mcreator.boh.compat.forge.common.capabilities.Capability<T> cap, net.mcreator.boh.compat.mc.core.Direction side) {
        net.mcreator.boh.compat.forge.common.util.LazyOptional<T> o = net.mcreator.boh.compat.forge.common.capabilities.EntityCapabilities.get(e, cap, side);
        if (o.isPresent()) return o;
        if (cap == net.mcreator.boh.compat.forge.common.capabilities.ForgeCapabilities.ITEM_HANDLER) {
            if (e instanceof EntityPlayer) return (net.mcreator.boh.compat.forge.common.util.LazyOptional<T>) net.mcreator.boh.compat.forge.common.util.LazyOptional.ofObject(new net.mcreator.boh.compat.forge.items.wrapper.InvWrapper(((EntityPlayer) e).inventory));
            if (e instanceof net.minecraft.inventory.IInventory) return (net.mcreator.boh.compat.forge.common.util.LazyOptional<T>) net.mcreator.boh.compat.forge.common.util.LazyOptional.ofObject(new net.mcreator.boh.compat.forge.items.wrapper.InvWrapper((net.minecraft.inventory.IInventory) e));
        }
        return net.mcreator.boh.compat.forge.common.util.LazyOptional.empty();
    }

    public static <T> net.mcreator.boh.compat.forge.common.util.LazyOptional<T> getCapability(TileEntity te,
        net.mcreator.boh.compat.forge.common.capabilities.Capability<T> cap, net.mcreator.boh.compat.mc.core.Direction side) {
        if (te instanceof net.mcreator.boh.compat.mc.world.level.block.entity.RandomizableContainerBlockEntity) {
            net.mcreator.boh.compat.forge.common.util.LazyOptional<T> o = ((net.mcreator.boh.compat.mc.world.level.block.entity.RandomizableContainerBlockEntity) te).getCapability(cap, side == null ? net.mcreator.boh.compat.mc.core.Direction.UP : side);
            if (o.isPresent()) return o;
        }
        if (cap == net.mcreator.boh.compat.forge.common.capabilities.ForgeCapabilities.ITEM_HANDLER && te instanceof net.minecraft.inventory.IInventory)
            return (net.mcreator.boh.compat.forge.common.util.LazyOptional<T>) net.mcreator.boh.compat.forge.common.util.LazyOptional.ofObject(new net.mcreator.boh.compat.forge.items.wrapper.InvWrapper((net.minecraft.inventory.IInventory) te));
        return net.mcreator.boh.compat.forge.common.util.LazyOptional.empty();
    }

    public static <T> net.mcreator.boh.compat.forge.common.util.LazyOptional<T> getCapability(ItemStack s,
        net.mcreator.boh.compat.forge.common.capabilities.Capability<T> cap, net.mcreator.boh.compat.mc.core.Direction side) {
        return net.mcreator.boh.compat.forge.common.util.LazyOptional.empty();
    }

    // ------------------------------------------------------------------ slots (1.20 names)

    public static ItemStack getItem(net.minecraft.inventory.Slot s) {
        return stack(s.getStack());
    }

    public static boolean hasItem(net.minecraft.inventory.Slot s) {
        return s.getHasStack();
    }

    public static void set(net.minecraft.inventory.Slot s, ItemStack stack) {
        s.putStack(legacy(stack));
    }

    public static void setChanged(net.minecraft.inventory.Slot s) {
        s.onSlotChanged();
    }

    public static boolean mayPlace(net.minecraft.inventory.Slot s, ItemStack stack) {
        return s.isItemValid(legacy(stack));
    }

    public static boolean mayPickup(net.minecraft.inventory.Slot s, EntityPlayer p) {
        return s.canTakeStack(p);
    }

    public static void onTake(net.minecraft.inventory.Slot s, EntityPlayer p, ItemStack stack) {
        s.onPickupFromSlot(p, legacy(stack));
    }

    public static void onQuickCraft(net.minecraft.inventory.Slot s, ItemStack a, ItemStack b) {
        s.onSlotChange(legacy(a), legacy(b));
    }

    public static int getMaxStackSize(net.minecraft.inventory.Slot s) {
        return s.getSlotStackLimit();
    }

    public static ItemStack remove(net.minecraft.inventory.Slot s, int n) {
        return stack(s.decrStackSize(n));
    }

    public static net.minecraft.client.multiplayer.WorldClient level(Minecraft mc) {
        return MClientImpl.world();
    }

    // ------------------------------------------------------------------ client (bodies delegate to MClientImpl)

    public static net.minecraft.client.entity.AbstractClientPlayer player(Minecraft mc) {
        return MClientImpl.player();
    }

    public static ClientConnection getConnection(Minecraft mc) {
        return ClientConnection.INSTANCE;
    }

    public static ClientGameRenderer gameRenderer(Minecraft mc) {
        return ClientGameRenderer.INSTANCE;
    }

    public static ClientOptions options(Minecraft mc) {
        return ClientOptions.INSTANCE;
    }

    /** 1.20 ClientPacketListener (only player info lookups). */
    public static final class ClientConnection {

        static final ClientConnection INSTANCE = new ClientConnection();

        public PlayerInfo getPlayerInfo(UUID id) {
            GameType g = MClientImpl.gameModeOf(id);
            return g == null ? null : new PlayerInfo(g);
        }
    }

    public static PlayerInfo getPlayerInfo(ClientConnection c, UUID id) {
        return c.getPlayerInfo(id);
    }

    /** 1.20 PlayerInfo. */
    public static final class PlayerInfo {

        private final GameType mode;

        PlayerInfo(GameType mode) {
            this.mode = mode;
        }

        public GameType getGameMode() {
            return mode;
        }
    }

    public static GameType getGameMode(PlayerInfo i) {
        return i.getGameMode();
    }

    /** 1.20 GameRenderer (camera and item activation). */
    public static final class ClientGameRenderer {

        static final ClientGameRenderer INSTANCE = new ClientGameRenderer();

        public Camera getMainCamera() {
            return MClientImpl.camera();
        }

        public void displayItemActivation(ItemStack stack) {
            MClientImpl.displayItemActivation(stack);
        }
    }

    public static Camera getMainCamera(ClientGameRenderer r) {
        return r.getMainCamera();
    }

    public static void displayItemActivation(ClientGameRenderer r, ItemStack s) {
        r.displayItemActivation(s);
    }

    /** 1.20 Options. */
    public static final class ClientOptions {

        static final ClientOptions INSTANCE = new ClientOptions();

        public int getEffectiveRenderDistance() {
            return MClientImpl.renderDistance();
        }
    }

    public static int getEffectiveRenderDistance(ClientOptions o) {
        return o.getEffectiveRenderDistance();
    }
}
