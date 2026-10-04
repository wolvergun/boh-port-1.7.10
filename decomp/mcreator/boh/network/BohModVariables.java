package net.mcreator.boh.network;

import java.util.function.Supplier;
import net.mcreator.boh.BohMod;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.NetworkEvent.Context;

@EventBusSubscriber(bus = Bus.MOD)
public class BohModVariables {
   public static final Capability<BohModVariables.PlayerVariables> PLAYER_VARIABLES_CAPABILITY = CapabilityManager.get(
      new CapabilityToken<BohModVariables.PlayerVariables>() {}
   );

   @SubscribeEvent
   public static void init(FMLCommonSetupEvent event) {
      BohMod.addNetworkMessage(
         BohModVariables.SavedDataSyncMessage.class,
         BohModVariables.SavedDataSyncMessage::buffer,
         BohModVariables.SavedDataSyncMessage::new,
         BohModVariables.SavedDataSyncMessage::handler
      );
      BohMod.addNetworkMessage(
         BohModVariables.PlayerVariablesSyncMessage.class,
         BohModVariables.PlayerVariablesSyncMessage::buffer,
         BohModVariables.PlayerVariablesSyncMessage::new,
         BohModVariables.PlayerVariablesSyncMessage::handler
      );
   }

   @SubscribeEvent
   public static void init(RegisterCapabilitiesEvent event) {
      event.register(BohModVariables.PlayerVariables.class);
   }

   @EventBusSubscriber
   public static class EventBusVariableHandlers {
      @SubscribeEvent
      public static void onPlayerLoggedInSyncPlayerVariables(PlayerLoggedInEvent event) {
         if (!event.getEntity().level().isClientSide()) {
            ((BohModVariables.PlayerVariables)event.getEntity()
                  .getCapability(BohModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                  .orElse(new BohModVariables.PlayerVariables()))
               .syncPlayerVariables(event.getEntity());
         }
      }

      @SubscribeEvent
      public static void onPlayerRespawnedSyncPlayerVariables(PlayerRespawnEvent event) {
         if (!event.getEntity().level().isClientSide()) {
            ((BohModVariables.PlayerVariables)event.getEntity()
                  .getCapability(BohModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                  .orElse(new BohModVariables.PlayerVariables()))
               .syncPlayerVariables(event.getEntity());
         }
      }

      @SubscribeEvent
      public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerChangedDimensionEvent event) {
         if (!event.getEntity().level().isClientSide()) {
            ((BohModVariables.PlayerVariables)event.getEntity()
                  .getCapability(BohModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                  .orElse(new BohModVariables.PlayerVariables()))
               .syncPlayerVariables(event.getEntity());
         }
      }

      @SubscribeEvent
      public static void clonePlayer(Clone event) {
         event.getOriginal().revive();
         BohModVariables.PlayerVariables original = (BohModVariables.PlayerVariables)event.getOriginal()
            .getCapability(BohModVariables.PLAYER_VARIABLES_CAPABILITY, null)
            .orElse(new BohModVariables.PlayerVariables());
         BohModVariables.PlayerVariables clone = (BohModVariables.PlayerVariables)event.getEntity()
            .getCapability(BohModVariables.PLAYER_VARIABLES_CAPABILITY, null)
            .orElse(new BohModVariables.PlayerVariables());
         clone.smile_jpeg = original.smile_jpeg;
         if (!event.isWasDeath()) {
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
      public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
         if (!event.getEntity().level().isClientSide()) {
            SavedData mapdata = BohModVariables.MapVariables.get(event.getEntity().level());
            SavedData worlddata = BohModVariables.WorldVariables.get(event.getEntity().level());
            if (mapdata != null) {
               BohMod.PACKET_HANDLER
                  .send(PacketDistributor.PLAYER.with(() -> (ServerPlayer)event.getEntity()), new BohModVariables.SavedDataSyncMessage(0, mapdata));
            }

            if (worlddata != null) {
               BohMod.PACKET_HANDLER
                  .send(PacketDistributor.PLAYER.with(() -> (ServerPlayer)event.getEntity()), new BohModVariables.SavedDataSyncMessage(1, worlddata));
            }
         }
      }

      @SubscribeEvent
      public static void onPlayerChangedDimension(PlayerChangedDimensionEvent event) {
         if (!event.getEntity().level().isClientSide()) {
            SavedData worlddata = BohModVariables.WorldVariables.get(event.getEntity().level());
            if (worlddata != null) {
               BohMod.PACKET_HANDLER
                  .send(PacketDistributor.PLAYER.with(() -> (ServerPlayer)event.getEntity()), new BohModVariables.SavedDataSyncMessage(1, worlddata));
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

      public static BohModVariables.MapVariables load(CompoundTag tag) {
         BohModVariables.MapVariables data = new BohModVariables.MapVariables();
         data.read(tag);
         return data;
      }

      public void read(CompoundTag nbt) {
         this.spawn_michael = nbt.getDouble("spawn_michael");
         this.spawn_gold = nbt.getDouble("spawn_gold");
         this.spawn_siren = nbt.getDouble("spawn_siren");
         this.Kill_WF = nbt.getDouble("Kill_WF");
         this.chasetheme = nbt.getBoolean("chasetheme");
         this.slender_variable = nbt.getBoolean("slender_variable");
         this.spawn_james = nbt.getDouble("spawn_james");
         this.spawn_simon = nbt.getDouble("spawn_simon");
         this.spawn_gaster = nbt.getDouble("spawn_gaster");
         this.spawn_souichi = nbt.getDouble("spawn_souichi");
         this.spawn_lifeform = nbt.getBoolean("spawn_lifeform");
         this.wf_gun = nbt.getDouble("wf_gun");
         this.spawn_saucer = nbt.getDouble("spawn_saucer");
         this.whisle_occurance_timer = nbt.getDouble("whisle_occurance_timer");
         this.whistle_global_timer = nbt.getDouble("whistle_global_timer");
         this.whistle_logic = nbt.getBoolean("whistle_logic");
      }

      public CompoundTag save(CompoundTag nbt) {
         nbt.putDouble("spawn_michael", this.spawn_michael);
         nbt.putDouble("spawn_gold", this.spawn_gold);
         nbt.putDouble("spawn_siren", this.spawn_siren);
         nbt.putDouble("Kill_WF", this.Kill_WF);
         nbt.putBoolean("chasetheme", this.chasetheme);
         nbt.putBoolean("slender_variable", this.slender_variable);
         nbt.putDouble("spawn_james", this.spawn_james);
         nbt.putDouble("spawn_simon", this.spawn_simon);
         nbt.putDouble("spawn_gaster", this.spawn_gaster);
         nbt.putDouble("spawn_souichi", this.spawn_souichi);
         nbt.putBoolean("spawn_lifeform", this.spawn_lifeform);
         nbt.putDouble("wf_gun", this.wf_gun);
         nbt.putDouble("spawn_saucer", this.spawn_saucer);
         nbt.putDouble("whisle_occurance_timer", this.whisle_occurance_timer);
         nbt.putDouble("whistle_global_timer", this.whistle_global_timer);
         nbt.putBoolean("whistle_logic", this.whistle_logic);
         return nbt;
      }

      public void syncData(LevelAccessor world) {
         this.setDirty();
         if (world instanceof Level && !world.isClientSide()) {
            BohMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new BohModVariables.SavedDataSyncMessage(0, this));
         }
      }

      public static BohModVariables.MapVariables get(LevelAccessor world) {
         return world instanceof ServerLevelAccessor serverLevelAcc
            ? (BohModVariables.MapVariables)serverLevelAcc.getLevel()
               .getServer()
               .getLevel(Level.OVERWORLD)
               .getDataStorage()
               .computeIfAbsent(e -> load(e), BohModVariables.MapVariables::new, "boh_mapvars")
            : clientSide;
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
         if (entity instanceof ServerPlayer serverPlayer) {
            BohMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new BohModVariables.PlayerVariablesSyncMessage(this));
         }
      }

      public Tag writeNBT() {
         CompoundTag nbt = new CompoundTag();
         nbt.putDouble("smile_jpeg", this.smile_jpeg);
         nbt.putBoolean("chase_exe", this.chase_exe);
         nbt.putBoolean("chase_aoni", this.chase_aoni);
         nbt.putBoolean("spawn_slender", this.spawn_slender);
         nbt.putBoolean("chase_myers", this.chase_myers);
         nbt.putBoolean("chase_cartooncat", this.chase_cartooncat);
         nbt.putBoolean("chase_sans", this.chase_sans);
         nbt.putBoolean("chase_mx", this.chase_mx);
         nbt.putBoolean("chase_nothingthere", this.chase_nothingthere);
         return nbt;
      }

      public void readNBT(Tag tag) {
         CompoundTag nbt = (CompoundTag)tag;
         this.smile_jpeg = nbt.getDouble("smile_jpeg");
         this.chase_exe = nbt.getBoolean("chase_exe");
         this.chase_aoni = nbt.getBoolean("chase_aoni");
         this.spawn_slender = nbt.getBoolean("spawn_slender");
         this.chase_myers = nbt.getBoolean("chase_myers");
         this.chase_cartooncat = nbt.getBoolean("chase_cartooncat");
         this.chase_sans = nbt.getBoolean("chase_sans");
         this.chase_mx = nbt.getBoolean("chase_mx");
         this.chase_nothingthere = nbt.getBoolean("chase_nothingthere");
      }
   }

   @EventBusSubscriber
   private static class PlayerVariablesProvider implements ICapabilitySerializable<Tag> {
      private final BohModVariables.PlayerVariables playerVariables = new BohModVariables.PlayerVariables();
      private final LazyOptional<BohModVariables.PlayerVariables> instance = LazyOptional.of(() -> this.playerVariables);

      @SubscribeEvent
      public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
         if (event.getObject() instanceof Player && !(event.getObject() instanceof FakePlayer)) {
            event.addCapability(new ResourceLocation("boh", "player_variables"), new BohModVariables.PlayerVariablesProvider());
         }
      }

      public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
         return cap == BohModVariables.PLAYER_VARIABLES_CAPABILITY ? this.instance.cast() : LazyOptional.empty();
      }

      public Tag serializeNBT() {
         return this.playerVariables.writeNBT();
      }

      public void deserializeNBT(Tag nbt) {
         this.playerVariables.readNBT(nbt);
      }
   }

   public static class PlayerVariablesSyncMessage {
      private final BohModVariables.PlayerVariables data;

      public PlayerVariablesSyncMessage(FriendlyByteBuf buffer) {
         this.data = new BohModVariables.PlayerVariables();
         this.data.readNBT(buffer.readNbt());
      }

      public PlayerVariablesSyncMessage(BohModVariables.PlayerVariables data) {
         this.data = data;
      }

      public static void buffer(BohModVariables.PlayerVariablesSyncMessage message, FriendlyByteBuf buffer) {
         buffer.writeNbt((CompoundTag)message.data.writeNBT());
      }

      public static void handler(BohModVariables.PlayerVariablesSyncMessage message, Supplier<Context> contextSupplier) {
         Context context = contextSupplier.get();
         context.enqueueWork(
            () -> {
               if (!context.getDirection().getReceptionSide().isServer()) {
                  BohModVariables.PlayerVariables variables = (BohModVariables.PlayerVariables)Minecraft.getInstance()
                     .player
                     .getCapability(BohModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                     .orElse(new BohModVariables.PlayerVariables());
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
            }
         );
         context.setPacketHandled(true);
      }
   }

   public static class SavedDataSyncMessage {
      private final int type;
      private SavedData data;

      public SavedDataSyncMessage(FriendlyByteBuf buffer) {
         this.type = buffer.readInt();
         CompoundTag nbt = buffer.readNbt();
         if (nbt != null) {
            this.data = (SavedData)(this.type == 0 ? new BohModVariables.MapVariables() : new BohModVariables.WorldVariables());
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
         buffer.writeInt(message.type);
         if (message.data != null) {
            buffer.writeNbt(message.data.save(new CompoundTag()));
         }
      }

      public static void handler(BohModVariables.SavedDataSyncMessage message, Supplier<Context> contextSupplier) {
         Context context = contextSupplier.get();
         context.enqueueWork(() -> {
            if (!context.getDirection().getReceptionSide().isServer() && message.data != null) {
               if (message.type == 0) {
                  BohModVariables.MapVariables.clientSide = (BohModVariables.MapVariables)message.data;
               } else {
                  BohModVariables.WorldVariables.clientSide = (BohModVariables.WorldVariables)message.data;
               }
            }
         });
         context.setPacketHandled(true);
      }
   }

   public static class WorldVariables extends SavedData {
      public static final String DATA_NAME = "boh_worldvars";
      static BohModVariables.WorldVariables clientSide = new BohModVariables.WorldVariables();

      public static BohModVariables.WorldVariables load(CompoundTag tag) {
         BohModVariables.WorldVariables data = new BohModVariables.WorldVariables();
         data.read(tag);
         return data;
      }

      public void read(CompoundTag nbt) {
      }

      public CompoundTag save(CompoundTag nbt) {
         return nbt;
      }

      public void syncData(LevelAccessor world) {
         this.setDirty();
         if (world instanceof Level level && !level.isClientSide()) {
            BohMod.PACKET_HANDLER.send(PacketDistributor.DIMENSION.with(level::dimension), new BohModVariables.SavedDataSyncMessage(1, this));
         }
      }

      public static BohModVariables.WorldVariables get(LevelAccessor world) {
         return world instanceof ServerLevel level
            ? (BohModVariables.WorldVariables)level.getDataStorage().computeIfAbsent(e -> load(e), BohModVariables.WorldVariables::new, "boh_worldvars")
            : clientSide;
      }
   }
}
