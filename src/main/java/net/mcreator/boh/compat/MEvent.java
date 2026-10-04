package net.mcreator.boh.compat;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import java.util.AbstractList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.client.LightTexture;
import net.mcreator.boh.compat.forge.client.DimensionSpecialEffectsManager;
import net.mcreator.boh.compat.forge.common.capabilities.Capability;
import net.mcreator.boh.compat.forge.common.capabilities.EntityCapabilities;
import net.mcreator.boh.compat.forge.common.capabilities.ForgeCapabilities;
import net.mcreator.boh.compat.forge.common.util.LazyOptional;
import net.mcreator.boh.compat.forge.event.entity.EntityTravelToDimensionEvent;
import net.mcreator.boh.compat.forge.items.wrapper.InvWrapper;
import net.mcreator.boh.compat.mc.advancements.PlayerAdvancements;
import net.mcreator.boh.compat.mc.client.BossOverlay;
import net.mcreator.boh.compat.mc.client.Camera;
import net.mcreator.boh.compat.mc.client.renderer.DimensionSpecialEffects;
import net.mcreator.boh.compat.mc.client.renderer.entity.EntityRenderDispatcher;
import net.mcreator.boh.compat.mc.commands.Commands;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.core.Holder;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.mcreator.boh.compat.mc.server.ServerAdvancementManager;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Operation;
import net.mcreator.boh.compat.mc.world.level.GameType;
import net.mcreator.boh.compat.mc.world.level.block.Mirror;
import net.mcreator.boh.compat.mc.world.level.block.Rotation;
import net.mcreator.boh.compat.mc.world.level.block.entity.RandomizableContainerBlockEntity;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.material.Fluid;
import net.mcreator.boh.compat.mc.world.level.saveddata.DimensionDataStorage;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.mc.world.scores.criteria.RenderType;
import net.mcreator.boh.compat.world.Biomes;
import net.mcreator.boh.compat.world.Dimensions;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;

public class MEvent extends MEntity {
    private static final Map<TileEntity, NBTTagCompound> TILE_DATA = new WeakHashMap<>();

    protected MEvent() {
    }

    public static void setCanceled(Event e, boolean b) {
        if (e != null && e.isCancelable()) {
            e.setCanceled(b);
        }
    }

    public static boolean isCanceled(Event e) {
        return e != null && e.isCanceled();
    }

    public static boolean isCancelable(Event e) {
        return e != null && e.isCancelable();
    }

    public static void setResult(Event e, Result r) {
        e.setResult(r);
    }

    public static Result getResult(Event e) {
        return e.getResult();
    }

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

    public static EntityPlayer player(PlayerTickEvent e) {
        return e.player;
    }

    public static EntityPlayer getEntity(PlayerTickEvent e) {
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

    public static ResourceKey<World> getTo(PlayerChangedDimensionEvent e) {
        return Dimensions.key(e.toDim);
    }

    public static ResourceKey<World> getFrom(PlayerChangedDimensionEvent e) {
        return Dimensions.key(e.fromDim);
    }

    public static EntityPlayer getOriginal(Clone e) {
        return e.original;
    }

    public static boolean isWasDeath(Clone e) {
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

    public static List<Component> getToolTip(ItemTooltipEvent e) {
        final List<String> lines = e.toolTip;
        return new AbstractList<Component>() {
            public Component get(int i) {
                return Component.literal(lines.get(i));
            }

            @Override
            public int size() {
                return lines.size();
            }

            public void add(int i, Component c) {
                lines.add(Math.max(0, Math.min(i, lines.size())), c == null ? "" : c.getFormattedText());
            }

            public Component set(int i, Component c) {
                return Component.literal(lines.set(i, c.getFormattedText()));
            }

            public Component remove(int i) {
                return Component.literal(lines.remove(i));
            }
        };
    }

    public static Commands getCommands(MinecraftServer s) {
        return Commands.INSTANCE;
    }

    public static PlayerAdvancements getAdvancements(EntityPlayerMP p) {
        return new PlayerAdvancements(p);
    }

    public static ServerAdvancementManager getAdvancements(MinecraftServer s) {
        return ServerAdvancementManager.INSTANCE;
    }

    public static ScoreObjective getObjective(Scoreboard sb, String name) {
        return sb.getObjective(name);
    }

    public static ScoreObjective addObjective(Scoreboard sb, String name, IScoreObjectiveCriteria crit, Component display, RenderType type) {
        ScoreObjective o = sb.getObjective(name);
        if (o != null) {
            return o;
        } else {
            o = sb.addScoreObjective(name, crit);
            if (display != null) {
                o.setDisplayName(display.getString());
            }

            return o;
        }
    }

    public static Score getOrCreatePlayerScore(Scoreboard sb, String name, ScoreObjective obj) {
        return obj == null
            ? new Score(new Scoreboard(), new ScoreObjective(new Scoreboard(), "dummy", IScoreObjectiveCriteria.field_96641_b), name)
            : sb.func_96529_a(name, obj);
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

    public static EntityPlayer player(InventoryPlayer inv) {
        return inv.player;
    }

    public static boolean triggerEvent(TileEntity te, int id, int param) {
        return te.receiveClientEvent(id, param);
    }

    public static boolean canSurvive(BlockState state, World w, BlockPos pos) {
        return state.getBlock() instanceof BohBlock
            ? ((BohBlock)state.getBlock()).canSurvive(state, w, pos)
            : state.getBlock().canBlockStay(w, pos.getX(), pos.getY(), pos.getZ());
    }

    public static void broadcastBreakEvent(EntityLivingBase e, InteractionHand hand) {
        if (e instanceof EntityPlayer && e.getHeldItem() != null) {
            ((EntityPlayer)e).renderBrokenItemStack(e.getHeldItem());
        }
    }

    public static EntitySmallFireball new_EntitySmallFireball(EntityType<EntitySmallFireball> type, World w) {
        return new EntitySmallFireball(w);
    }

    public static EntityLargeFireball new_EntityLargeFireball(EntityType<EntityLargeFireball> type, World w) {
        return new EntityLargeFireball(w);
    }

    public static void setYBodyRot(Entity e, float v) {
        if (e instanceof EntityLivingBase) {
            ((EntityLivingBase)e).renderYawOffset = v;
        }
    }

    public static double distanceToSqr(Entity a, Entity b) {
        return a.getDistanceSqToEntity(b);
    }

    public static double distanceToSqr(Entity a, Vec3 v) {
        return a.getDistanceSq(v.x, v.y, v.z);
    }

    public static double distanceToSqr(Entity a, double x, double y, double z) {
        return a.getDistanceSq(x, y, z);
    }

    public static void scheduleTick(World w, BlockPos pos, Fluid f, int delay) {
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

    public static WorldServer getLevel(World w) {
        return w instanceof WorldServer ? (WorldServer)w : null;
    }

    public static DimensionDataStorage getDataStorage(World w) {
        return DimensionDataStorage.of(w);
    }

    public static void revive(Entity e) {
        e.isDead = false;
    }

    public static BlockState rotate(BlockState s, Rotation r) {
        return s.getBlock() instanceof BohBlock ? ((BohBlock)s.getBlock()).rotate(s, r) : s;
    }

    public static BlockState mirror(BlockState s, Mirror m) {
        return s.getBlock() instanceof BohBlock ? ((BohBlock)s.getBlock()).mirror(s, m) : s;
    }

    public static void set(AtomicInteger a, int v) {
        a.set(v);
    }

    public static NBTTagCompound getPersistentData(TileEntity te) {
        return te instanceof RandomizableContainerBlockEntity
            ? ((RandomizableContainerBlockEntity)te).getPersistentData()
            : TILE_DATA.computeIfAbsent(te, k -> new NBTTagCompound());
    }

    public static DimensionSpecialEffects effects(World w) {
        return DimensionSpecialEffectsManager.getForType(dimension(w).location());
    }

    public static ItemStack split(ItemStack s, int n) {
        ItemStack l = legacy(s);
        return l == null ? EMPTY : stack(l.splitStack(n));
    }

    public static List<ItemStack> items(InventoryPlayer inv) {
        return slots(inv.mainInventory);
    }

    public static List<ItemStack> armor(InventoryPlayer inv) {
        return slots(inv.armorInventory);
    }

    private static List<ItemStack> slots(final ItemStack[] arr) {
        return new AbstractList<ItemStack>() {
            public ItemStack get(int i) {
                return MItem.stack(arr[i]);
            }

            public ItemStack set(int i, ItemStack s) {
                ItemStack old = MItem.stack(arr[i]);
                arr[i] = MItem.legacy(s);
                return old;
            }

            @Override
            public int size() {
                return arr.length;
            }
        };
    }

    public static EntityRenderDispatcher getEntityRenderDispatcher(Minecraft mc) {
        return EntityRenderDispatcher.INSTANCE;
    }

    public static ResourceLocation getSkinTextureLocation(AbstractClientPlayer p) {
        return MClientImpl.skin(p);
    }

    public static GuiScreen screen(Minecraft mc) {
        return MClientImpl.screen();
    }

    public static BossOverlay gui(Minecraft mc) {
        return BossOverlay.INSTANCE;
    }

    public static int blockStateId(BlockState s) {
        return Block.getIdFromBlock(s.getBlock()) + (s.meta() << 12);
    }

    public static Vec3 getSkyColor(World w, Vec3 pos, float pt) {
        return MClientImpl.skyColor(w, pt);
    }

    public static double getHorizonHeight(WorldInfo info, World w) {
        return w.provider.getHorizon();
    }

    public static ScorePlayerTeam getPlayerTeam(Scoreboard sb, String name) {
        return sb.getTeam(name);
    }

    public static boolean isVillage(WorldServer w, BlockPos pos) {
        return w.villageCollectionObj != null && w.villageCollectionObj.findNearestVillage(pos.getX(), pos.getY(), pos.getZ(), 32) != null;
    }

    public static World level(WorldTickEvent e) {
        return e.world;
    }

    public static boolean is(Holder<BiomeGenBase> h, ResourceLocation id) {
        BiomeGenBase b = h == null ? null : h.value();
        if (b == null) {
            return false;
        } else {
            ResourceLocation key = Biomes.keyOf(b);
            return key != null && key.equals(id);
        }
    }

    public static void turnOnLightLayer(LightTexture t) {
        MClientImpl.lightmap(true);
    }

    public static void turnOffLightLayer(LightTexture t) {
        MClientImpl.lightmap(false);
    }

    public static void setByPlayer(Slot s, ItemStack stack) {
        s.putStack(legacy(stack));
    }

    public static void setPathfindingMalus(Entity e, Object type, float malus) {
        if (e instanceof EntityLiving && "WATER".equals(String.valueOf(type)) && malus >= 0.0F) {
            ((EntityLiving)e).getNavigator().setAvoidsWater(false);
        }
    }

    public static boolean isUnobstructed(World w, Entity e) {
        return w.checkNoEntityCollision(e.boundingBox, e);
    }

    public static void calculateEntityAnimation(EntityLivingBase e, boolean flying) {
    }

    public static void read(Object data, NBTTagCompound tag) {
        try {
            data.getClass().getMethod("read", NBTTagCompound.class).invoke(data, tag);
        } catch (ReflectiveOperationException var3) {
            throw new RuntimeException(var3);
        }
    }

    public static Vec3 getOffset(BlockState s, IBlockAccess w, BlockPos pos) {
        return new Vec3(0.0, 0.0, 0.0);
    }

    public static boolean add(InventoryPlayer inv, ItemStack s) {
        ItemStack l = legacy(s);
        return l == null || inv.addItemStackToInventory(l);
    }

    public static AttributeModifier new_AttributeModifier(UUID id, String name, double amount, Operation op) {
        return new AttributeModifier(id, name, amount, op.ordinal());
    }

    public static <T> LazyOptional<T> getCapability(Entity e, Capability<T> cap, Direction side) {
        LazyOptional<T> o = EntityCapabilities.get(e, cap, side);
        if (o.isPresent()) {
            return o;
        } else {
            if (cap == ForgeCapabilities.ITEM_HANDLER) {
                if (e instanceof EntityPlayer) {
                    return LazyOptional.ofObject((T)(new InvWrapper(((EntityPlayer)e).inventory)));
                }

                if (e instanceof IInventory) {
                    return LazyOptional.ofObject((T)(new InvWrapper((IInventory)e)));
                }
            }

            return LazyOptional.empty();
        }
    }

    public static <T> LazyOptional<T> getCapability(TileEntity te, Capability<T> cap, Direction side) {
        if (te instanceof RandomizableContainerBlockEntity) {
            LazyOptional<T> o = ((RandomizableContainerBlockEntity)te).getCapability(cap, side == null ? Direction.UP : side);
            if (o.isPresent()) {
                return o;
            }
        }

        return cap == ForgeCapabilities.ITEM_HANDLER && te instanceof IInventory
            ? LazyOptional.ofObject((T)(new InvWrapper((IInventory)te)))
            : LazyOptional.empty();
    }

    public static <T> LazyOptional<T> getCapability(ItemStack s, Capability<T> cap, Direction side) {
        return LazyOptional.empty();
    }

    public static ItemStack getItem(Slot s) {
        return stack(s.getStack());
    }

    public static boolean hasItem(Slot s) {
        return s.getHasStack();
    }

    public static void set(Slot s, ItemStack stack) {
        s.putStack(legacy(stack));
    }

    public static void setChanged(Slot s) {
        s.onSlotChanged();
    }

    public static boolean mayPlace(Slot s, ItemStack stack) {
        return s.isItemValid(legacy(stack));
    }

    public static boolean mayPickup(Slot s, EntityPlayer p) {
        return s.canTakeStack(p);
    }

    public static void onTake(Slot s, EntityPlayer p, ItemStack stack) {
        s.onPickupFromSlot(p, legacy(stack));
    }

    public static void onQuickCraft(Slot s, ItemStack a, ItemStack b) {
        s.onSlotChange(legacy(a), legacy(b));
    }

    public static int getMaxStackSize(Slot s) {
        return s.getSlotStackLimit();
    }

    public static ItemStack remove(Slot s, int n) {
        return stack(s.decrStackSize(n));
    }

    public static WorldClient level(Minecraft mc) {
        return MClientImpl.world();
    }

    public static AbstractClientPlayer player(Minecraft mc) {
        return MClientImpl.player();
    }

    public static MEvent.ClientConnection getConnection(Minecraft mc) {
        return MEvent.ClientConnection.INSTANCE;
    }

    public static MEvent.ClientGameRenderer gameRenderer(Minecraft mc) {
        return MEvent.ClientGameRenderer.INSTANCE;
    }

    public static MEvent.ClientOptions options(Minecraft mc) {
        return MEvent.ClientOptions.INSTANCE;
    }

    public static MEvent.PlayerInfo getPlayerInfo(MEvent.ClientConnection c, UUID id) {
        return c.getPlayerInfo(id);
    }

    public static GameType getGameMode(MEvent.PlayerInfo i) {
        return i.getGameMode();
    }

    public static Camera getMainCamera(MEvent.ClientGameRenderer r) {
        return r.getMainCamera();
    }

    public static void displayItemActivation(MEvent.ClientGameRenderer r, ItemStack s) {
        r.displayItemActivation(s);
    }

    public static int getEffectiveRenderDistance(MEvent.ClientOptions o) {
        return o.getEffectiveRenderDistance();
    }

    public static final class ClientConnection {
        static final MEvent.ClientConnection INSTANCE = new MEvent.ClientConnection();

        public MEvent.PlayerInfo getPlayerInfo(UUID id) {
            GameType g = MClientImpl.gameModeOf(id);
            return g == null ? null : new MEvent.PlayerInfo(g);
        }
    }

    public static final class ClientGameRenderer {
        static final MEvent.ClientGameRenderer INSTANCE = new MEvent.ClientGameRenderer();

        public Camera getMainCamera() {
            return MClientImpl.camera();
        }

        public void displayItemActivation(ItemStack stack) {
            MClientImpl.displayItemActivation(stack);
        }
    }

    public static final class ClientOptions {
        static final MEvent.ClientOptions INSTANCE = new MEvent.ClientOptions();

        public int getEffectiveRenderDistance() {
            return MClientImpl.renderDistance();
        }
    }

    public static final class PlayerInfo {
        private final GameType mode;

        PlayerInfo(GameType mode) {
            this.mode = mode;
        }

        public GameType getGameMode() {
            return this.mode;
        }
    }
}
