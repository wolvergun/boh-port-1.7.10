package net.mcreator.boh.network;

import java.util.function.Supplier;
import net.mcreator.boh.BohMod;
import net.minecraft.client.Minecraft;
import net.mcreator.boh.compat.mc.core.Direction;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTBase;
import net.mcreator.boh.compat.mc.network.FriendlyByteBuf;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.level.saveddata.SavedData;
import net.mcreator.boh.compat.forge.common.capabilities.Capability;
import net.mcreator.boh.compat.forge.common.capabilities.CapabilityManager;
import net.mcreator.boh.compat.forge.common.capabilities.CapabilityToken;
import net.mcreator.boh.compat.forge.common.capabilities.ICapabilitySerializable;
import net.mcreator.boh.compat.forge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.FakePlayer;
import net.mcreator.boh.compat.forge.common.util.LazyOptional;
import net.mcreator.boh.compat.forge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.forge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.mcreator.boh.compat.forge.network.PacketDistributor;
import net.mcreator.boh.compat.forge.network.Context;
import net.mcreator.boh.compat.M;

public class BohModVariables {

    public static final Capability<BohModVariables.PlayerVariables> PLAYER_VARIABLES_CAPABILITY = CapabilityManager.get(new CapabilityToken<BohModVariables.PlayerVariables>() {
    });

    @SubscribeEvent
    public void init(FMLCommonSetupEvent event) {
        BohMod.addNetworkMessage(BohModVariables.SavedDataSyncMessage.class, BohModVariables.SavedDataSyncMessage::buffer, BohModVariables.SavedDataSyncMessage::new, BohModVariables.SavedDataSyncMessage::handler);
        BohMod.addNetworkMessage(BohModVariables.PlayerVariablesSyncMessage.class, BohModVariables.PlayerVariablesSyncMessage::buffer, BohModVariables.PlayerVariablesSyncMessage::new, BohModVariables.PlayerVariablesSyncMessage::handler);
    }

    @SubscribeEvent
    public void init(RegisterCapabilitiesEvent event) {
        event.register(BohModVariables.PlayerVariables.class);
    }

    public static class EventBusVariableHandlers {

        @SubscribeEvent
        public void onPlayerLoggedInSyncPlayerVariables(PlayerLoggedInEvent event) {
            if (!M.isClientSide(M.level(M.getEntity(event)))) {
                ((BohModVariables.PlayerVariables) M.getCapability(M.getEntity(event), BohModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new BohModVariables.PlayerVariables())).syncPlayerVariables(M.getEntity(event));
            }
        }

        @SubscribeEvent
        public void onPlayerRespawnedSyncPlayerVariables(PlayerRespawnEvent event) {
            if (!M.isClientSide(M.level(M.getEntity(event)))) {
                ((BohModVariables.PlayerVariables) M.getCapability(M.getEntity(event), BohModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new BohModVariables.PlayerVariables())).syncPlayerVariables(M.getEntity(event));
            }
        }

        @SubscribeEvent
        public void onPlayerChangedDimensionSyncPlayerVariables(PlayerChangedDimensionEvent event) {
            if (!M.isClientSide(M.level(M.getEntity(event)))) {
                ((BohModVariables.PlayerVariables) M.getCapability(M.getEntity(event), BohModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new BohModVariables.PlayerVariables())).syncPlayerVariables(M.getEntity(event));
            }
        }

        @SubscribeEvent
        public void clonePlayer(Clone event) {
            M.revive(M.getOriginal(event));
            BohModVariables.PlayerVariables original = (BohModVariables.PlayerVariables) M.getCapability(M.getOriginal(event), BohModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new BohModVariables.PlayerVariables());
            BohModVariables.PlayerVariables clone = (BohModVariables.PlayerVariables) M.getCapability(M.getEntity(event), BohModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new BohModVariables.PlayerVariables());
            clone.smile_jpeg = original.smile_jpeg;
            if (!M.isWasDeath(event)) {
                clone.chase_exe = original.chase_exe;
                clone.chase_aoni = original.chase_aoni;
                clone.spawn_slender = original.spawn_slender;
                clone.chase_myers = original.chase_myers;
                clone.chase_cartooncat = original.chase_cartooncat;
                clone.chase_sans = original.chase_sans;
                clone.chase_mx = original.chase_mx;
                clone.chase_nothingthere = original.chase_nothingthere;
            }
        }

        @SubscribeEvent
        public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
            if (!M.isClientSide(M.level(M.getEntity(event)))) {
                SavedData mapdata = BohModVariables.MapVariables.get(M.level(M.getEntity(event)));
                SavedData worlddata = BohModVariables.WorldVariables.get(M.level(M.getEntity(event)));
                if (mapdata != null) {
                    BohMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> (EntityPlayerMP) M.getEntity(event)), new BohModVariables.SavedDataSyncMessage(0, mapdata));
                }
                if (worlddata != null) {
                    BohMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> (EntityPlayerMP) M.getEntity(event)), new BohModVariables.SavedDataSyncMessage(1, worlddata));
                }
            }
        }

        @SubscribeEvent
        public void onPlayerChangedDimension(PlayerChangedDimensionEvent event) {
            if (!M.isClientSide(M.level(M.getEntity(event)))) {
                SavedData worlddata = BohModVariables.WorldVariables.get(M.level(M.getEntity(event)));
                if (worlddata != null) {
                    BohMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> (EntityPlayerMP) M.getEntity(event)), new BohModVariables.SavedDataSyncMessage(1, worlddata));
                }
            }
        }
    }

    public static class MapVariables extends SavedData {

        public static final String DATA_NAME = "boh_mapvars";

        public double spawn_michael = 0.0;

        public double spawn_gold = 0.0;

        public double spawn_siren = 0.0;

        public double Kill_WF = 0.0;

        public boolean chasetheme = false;

        public boolean slender_variable = true;

        public double spawn_james = 0.0;

        public double spawn_simon = 0.0;

        public double spawn_gaster = 0.0;

        public double spawn_souichi = 0.0;

        public boolean spawn_lifeform = false;

        public double wf_gun = 0.0;

        public double spawn_saucer = 0.0;

        public double whisle_occurance_timer = 0.0;

        public double whistle_global_timer = 0.0;

        public boolean whistle_logic = false;

        static BohModVariables.MapVariables clientSide = new BohModVariables.MapVariables();

        public static BohModVariables.MapVariables load(NBTTagCompound tag) {
            BohModVariables.MapVariables data = new BohModVariables.MapVariables();
            M.read(data, tag);
            return data;
        }

        public void read(NBTTagCompound nbt) {
            this.spawn_michael = M.getDouble(nbt, "spawn_michael");
            this.spawn_gold = M.getDouble(nbt, "spawn_gold");
            this.spawn_siren = M.getDouble(nbt, "spawn_siren");
            this.Kill_WF = M.getDouble(nbt, "Kill_WF");
            this.chasetheme = M.getBoolean(nbt, "chasetheme");
            this.slender_variable = M.getBoolean(nbt, "slender_variable");
            this.spawn_james = M.getDouble(nbt, "spawn_james");
            this.spawn_simon = M.getDouble(nbt, "spawn_simon");
            this.spawn_gaster = M.getDouble(nbt, "spawn_gaster");
            this.spawn_souichi = M.getDouble(nbt, "spawn_souichi");
            this.spawn_lifeform = M.getBoolean(nbt, "spawn_lifeform");
            this.wf_gun = M.getDouble(nbt, "wf_gun");
            this.spawn_saucer = M.getDouble(nbt, "spawn_saucer");
            this.whisle_occurance_timer = M.getDouble(nbt, "whisle_occurance_timer");
            this.whistle_global_timer = M.getDouble(nbt, "whistle_global_timer");
            this.whistle_logic = M.getBoolean(nbt, "whistle_logic");
        }

        public NBTTagCompound save(NBTTagCompound nbt) {
            M.putDouble(nbt, "spawn_michael", this.spawn_michael);
            M.putDouble(nbt, "spawn_gold", this.spawn_gold);
            M.putDouble(nbt, "spawn_siren", this.spawn_siren);
            M.putDouble(nbt, "Kill_WF", this.Kill_WF);
            M.putBoolean(nbt, "chasetheme", this.chasetheme);
            M.putBoolean(nbt, "slender_variable", this.slender_variable);
            M.putDouble(nbt, "spawn_james", this.spawn_james);
            M.putDouble(nbt, "spawn_simon", this.spawn_simon);
            M.putDouble(nbt, "spawn_gaster", this.spawn_gaster);
            M.putDouble(nbt, "spawn_souichi", this.spawn_souichi);
            M.putBoolean(nbt, "spawn_lifeform", this.spawn_lifeform);
            M.putDouble(nbt, "wf_gun", this.wf_gun);
            M.putDouble(nbt, "spawn_saucer", this.spawn_saucer);
            M.putDouble(nbt, "whisle_occurance_timer", this.whisle_occurance_timer);
            M.putDouble(nbt, "whistle_global_timer", this.whistle_global_timer);
            M.putBoolean(nbt, "whistle_logic", this.whistle_logic);
            return nbt;
        }

        public void syncData(World world) {
            M.setDirty(this);
            if (world instanceof World && !M.isClientSide(world)) {
                BohMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new BohModVariables.SavedDataSyncMessage(0, this));
            }
        }

        public static BohModVariables.MapVariables get(World world) {
            return world instanceof World serverLevelAcc && !M.isClientSide(serverLevelAcc) ? (BohModVariables.MapVariables) M.getDataStorage(M.getLevel(M.getServer(M.getLevel(serverLevelAcc)), M.OVERWORLD)).computeIfAbsent(e -> load(e), BohModVariables.MapVariables::new, "boh_mapvars") : clientSide;
        }
    }

    public static class PlayerVariables {

        public double smile_jpeg = 0.0;

        public boolean chase_exe = false;

        public boolean chase_aoni = false;

        public boolean spawn_slender = false;

        public boolean chase_myers = false;

        public boolean chase_cartooncat = false;

        public boolean chase_sans = false;

        public boolean chase_mx = false;

        public boolean chase_nothingthere = false;

        public void syncPlayerVariables(Entity entity) {
            if (entity instanceof EntityPlayerMP serverPlayer) {
                BohMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new BohModVariables.PlayerVariablesSyncMessage(this));
            }
        }

        public NBTBase writeNBT() {
            NBTTagCompound nbt = M.new_NBTTagCompound();
            M.putDouble(nbt, "smile_jpeg", this.smile_jpeg);
            M.putBoolean(nbt, "chase_exe", this.chase_exe);
            M.putBoolean(nbt, "chase_aoni", this.chase_aoni);
            M.putBoolean(nbt, "spawn_slender", this.spawn_slender);
            M.putBoolean(nbt, "chase_myers", this.chase_myers);
            M.putBoolean(nbt, "chase_cartooncat", this.chase_cartooncat);
            M.putBoolean(nbt, "chase_sans", this.chase_sans);
            M.putBoolean(nbt, "chase_mx", this.chase_mx);
            M.putBoolean(nbt, "chase_nothingthere", this.chase_nothingthere);
            return nbt;
        }

        public void readNBT(NBTBase tag) {
            NBTTagCompound nbt = (NBTTagCompound) tag;
            this.smile_jpeg = M.getDouble(nbt, "smile_jpeg");
            this.chase_exe = M.getBoolean(nbt, "chase_exe");
            this.chase_aoni = M.getBoolean(nbt, "chase_aoni");
            this.spawn_slender = M.getBoolean(nbt, "spawn_slender");
            this.chase_myers = M.getBoolean(nbt, "chase_myers");
            this.chase_cartooncat = M.getBoolean(nbt, "chase_cartooncat");
            this.chase_sans = M.getBoolean(nbt, "chase_sans");
            this.chase_mx = M.getBoolean(nbt, "chase_mx");
            this.chase_nothingthere = M.getBoolean(nbt, "chase_nothingthere");
        }
    }

    static public class PlayerVariablesProvider implements ICapabilitySerializable<NBTBase> {

        private final BohModVariables.PlayerVariables playerVariables = new BohModVariables.PlayerVariables();

        private final LazyOptional<BohModVariables.PlayerVariables> instance = LazyOptional.of(() -> this.playerVariables);

        @SubscribeEvent
        public void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
            if (M.getObject(event) instanceof EntityPlayer && !(M.getObject(event) instanceof FakePlayer)) {
                M.addCapability(event, new ResourceLocation("boh", "player_variables"), new BohModVariables.PlayerVariablesProvider());
            }
        }

        public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
            return cap == BohModVariables.PLAYER_VARIABLES_CAPABILITY ? this.instance.cast() : LazyOptional.empty();
        }

        public NBTBase serializeNBT() {
            return this.playerVariables.writeNBT();
        }

        public void deserializeNBT(NBTBase nbt) {
            this.playerVariables.readNBT(nbt);
        }
    }

    public static class PlayerVariablesSyncMessage {

        private final BohModVariables.PlayerVariables data;

        public PlayerVariablesSyncMessage(FriendlyByteBuf buffer) {
            this.data = new BohModVariables.PlayerVariables();
            this.data.readNBT(M.readNbt(buffer));
        }

        public PlayerVariablesSyncMessage(BohModVariables.PlayerVariables data) {
            this.data = data;
        }

        public static void buffer(BohModVariables.PlayerVariablesSyncMessage message, FriendlyByteBuf buffer) {
            M.writeNbt(buffer, (NBTTagCompound) message.data.writeNBT());
        }

        public static void handler(BohModVariables.PlayerVariablesSyncMessage message, Supplier<Context> contextSupplier) {
            Context context = contextSupplier.get();
            M.enqueueWork(context, () -> {
                if (!M.isServer(M.getReceptionSide(M.getDirection(context)))) {
                    BohModVariables.PlayerVariables variables = (BohModVariables.PlayerVariables) M.getCapability(M.player(Minecraft.getMinecraft()), BohModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new BohModVariables.PlayerVariables());
                    variables.smile_jpeg = message.data.smile_jpeg;
                    variables.chase_exe = message.data.chase_exe;
                    variables.chase_aoni = message.data.chase_aoni;
                    variables.spawn_slender = message.data.spawn_slender;
                    variables.chase_myers = message.data.chase_myers;
                    variables.chase_cartooncat = message.data.chase_cartooncat;
                    variables.chase_sans = message.data.chase_sans;
                    variables.chase_mx = message.data.chase_mx;
                    variables.chase_nothingthere = message.data.chase_nothingthere;
                }
            });
            M.setPacketHandled(context, true);
        }
    }

    public static class SavedDataSyncMessage {

        private final int type;

        private SavedData data;

        public SavedDataSyncMessage(FriendlyByteBuf buffer) {
            this.type = M.readInt(buffer);
            NBTTagCompound nbt = M.readNbt(buffer);
            if (nbt != null) {
                this.data = (SavedData) (this.type == 0 ? new BohModVariables.MapVariables() : new BohModVariables.WorldVariables());
                if (this.data instanceof BohModVariables.MapVariables mapVariables) {
                    mapVariables.read(nbt);
                } else if (this.data instanceof BohModVariables.WorldVariables worldVariables) {
                    worldVariables.read(nbt);
                }
            }
        }

        public SavedDataSyncMessage(int type, SavedData data) {
            this.type = type;
            this.data = data;
        }

        public static void buffer(BohModVariables.SavedDataSyncMessage message, FriendlyByteBuf buffer) {
            M.writeInt(buffer, message.type);
            if (message.data != null) {
                M.writeNbt(buffer, M.save(message.data, M.new_NBTTagCompound()));
            }
        }

        public static void handler(BohModVariables.SavedDataSyncMessage message, Supplier<Context> contextSupplier) {
            Context context = contextSupplier.get();
            M.enqueueWork(context, () -> {
                if (!M.isServer(M.getReceptionSide(M.getDirection(context))) && message.data != null) {
                    if (message.type == 0) {
                        BohModVariables.MapVariables.clientSide = (BohModVariables.MapVariables) message.data;
                    } else {
                        BohModVariables.WorldVariables.clientSide = (BohModVariables.WorldVariables) message.data;
                    }
                }
            });
            M.setPacketHandled(context, true);
        }
    }

    public static class WorldVariables extends SavedData {

        public static final String DATA_NAME = "boh_worldvars";

        static BohModVariables.WorldVariables clientSide = new BohModVariables.WorldVariables();

        public static BohModVariables.WorldVariables load(NBTTagCompound tag) {
            BohModVariables.WorldVariables data = new BohModVariables.WorldVariables();
            M.read(data, tag);
            return data;
        }

        public void read(NBTTagCompound nbt) {
        }

        public NBTTagCompound save(NBTTagCompound nbt) {
            return nbt;
        }

        public void syncData(World world) {
            M.setDirty(this);
            if (world instanceof World level && !M.isClientSide(level)) {
                BohMod.PACKET_HANDLER.send(PacketDistributor.DIMENSION.with(() -> M.dimension(level)), new BohModVariables.SavedDataSyncMessage(1, this));
            }
        }

        public static BohModVariables.WorldVariables get(World world) {
            return world instanceof WorldServer level ? (BohModVariables.WorldVariables) M.getDataStorage(level).computeIfAbsent(e -> load(e), BohModVariables.WorldVariables::new, "boh_worldvars") : clientSide;
        }
    }
}
