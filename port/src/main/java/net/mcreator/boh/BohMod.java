package net.mcreator.boh;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import net.mcreator.boh.compat.advancement.Advancements;
import net.mcreator.boh.compat.command.LegacyCommands;
import net.mcreator.boh.compat.forge.EventBridge;
import net.mcreator.boh.compat.forge.common.capabilities.EntityCapabilities;
import net.mcreator.boh.compat.forge.common.capabilities.RegisterCapabilitiesEvent;
import net.mcreator.boh.compat.forge.event.RegisterCommandsEvent;
import net.mcreator.boh.compat.forge.event.entity.EntityAttributeCreationEvent;
import net.mcreator.boh.compat.forge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.mcreator.boh.compat.forge.network.Context;
import net.mcreator.boh.compat.forge.network.NetworkRegistry;
import net.mcreator.boh.compat.forge.network.simple.SimpleChannel;
import net.mcreator.boh.compat.mc.network.FriendlyByteBuf;
import net.mcreator.boh.compat.net.CompatNetwork;
import net.mcreator.boh.compat.net.MenuNetwork;
import net.mcreator.boh.init.BohModBlockEntities;
import net.mcreator.boh.init.BohModBlocks;
import net.mcreator.boh.init.BohModEnchantments;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.init.BohModItems;
import net.mcreator.boh.init.BohModMenus;
import net.mcreator.boh.init.BohModMobEffects;
import net.mcreator.boh.init.BohModPaintings;
import net.mcreator.boh.init.BohModParticleTypes;
import net.mcreator.boh.init.BohModPotions;
import net.mcreator.boh.init.BohModSounds;
import net.mcreator.boh.init.BohModTabs;
import net.mcreator.boh.init.BohModVillagerProfessions;
import net.mcreator.boh.world.features.StructureFeature;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;

/**
 * Box of Horrors (1.7.10 port). Mirrors the 1.20 BohMod: deferred registries, the network channel, server work
 * queue; plus the 1.7.10 lifecycle that posts the 1.20 mod-bus events to the translated subscribers.
 */
@Mod(modid = BohMod.MODID, name = "Box of Horrors", version = Tags.VERSION)
public class BohMod {

    public static final Logger LOGGER = LogManager.getLogger(BohMod.class);
    public static final String MODID = "boh";

    @Mod.Instance(MODID)
    public static BohMod instance;

    @SidedProxy(clientSide = "net.mcreator.boh.ClientProxy", serverSide = "net.mcreator.boh.CommonProxy")
    public static CommonProxy proxy;

    /** The 1.20 mod event bus (registration and setup events). */
    public static final EventBus MOD_BUS = new EventBus();

    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation("boh", "boh"), () -> "1",
        "1"::equals, "1"::equals);

    private static int messageID = 0;
    private static final Collection<SimpleEntry<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CompatNetwork.init();
        MenuNetwork.init();
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
        EntityCapabilities.install();
        EventBridge.install();
        net.mcreator.boh.compat.world.SafeScoreboardSave.install();
        net.mcreator.boh.compat.forge.common.brewing.BrewingHooks.install();
        net.mcreator.boh.compat.debug.SelfTest.installIfEnabled();

        BohModSounds.REGISTRY.register(MOD_BUS);
        BohModBlocks.REGISTRY.register(MOD_BUS);
        BohModBlockEntities.REGISTRY.register(MOD_BUS);
        BohModItems.REGISTRY.register(MOD_BUS);
        BohModEntities.REGISTRY.register(MOD_BUS);
        BohModEnchantments.REGISTRY.register(MOD_BUS);
        BohModTabs.REGISTRY.register(MOD_BUS);
        StructureFeature.REGISTRY.register(MOD_BUS);
        BohModMobEffects.REGISTRY.register(MOD_BUS);
        BohModPotions.REGISTRY.register(MOD_BUS);
        BohModPaintings.REGISTRY.register(MOD_BUS);
        BohModParticleTypes.REGISTRY.register(MOD_BUS);
        BohModVillagerProfessions.PROFESSIONS.register(MOD_BUS);
        BohModMenus.REGISTRY.register(MOD_BUS);

        net.mcreator.boh.compat.effect.KuroEffects.init();
        net.mcreator.boh.compat.world.gen.ModWorldgenSetup.preInit(event.getSuggestedConfigurationFile());
        // compat entities created by translated commands/procedures (ids above the mod's own range)
        cpw.mods.fml.common.registry.EntityRegistry.registerModEntity(net.mcreator.boh.compat.entity.BohAreaEffectCloud.class, "area_effect_cloud", 240, this, 64, 10, false);
        cpw.mods.fml.common.registry.EntityRegistry.registerModEntity(net.mcreator.boh.compat.entity.BohArrow.class, "compat_arrow", 241, this, 64, 3, true);
        registerSubscribers(proxy.isClient());
        MOD_BUS.post(new RegisterCapabilitiesEvent());
        MOD_BUS.post(new EntityAttributeCreationEvent());
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MOD_BUS.post(new FMLCommonSetupEvent());
        net.mcreator.boh.compat.forge.event.BuildCreativeModeTabContentsEvent.postAll(MOD_BUS);
        MOD_BUS.post(new net.mcreator.boh.compat.forge.registries.RegisterEvent());
        Advancements.init();
        net.mcreator.boh.compat.registry.Recipes.init();
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        net.mcreator.boh.compat.world.Spawning.init();
        net.mcreator.boh.compat.world.gen.ModWorldgenSetup.postInit();
        proxy.postInit();
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        net.mcreator.boh.compat.mc.commands.Commands.REGISTERED.clear();
        MinecraftForge.EVENT_BUS.post(new RegisterCommandsEvent());
        LegacyCommands.registerAll(event);
    }

    /** Instantiates every translated @EventBusSubscriber class and registers it on its bus. */
    private static void registerSubscribers(boolean client) {
        try (InputStream in = BohMod.class.getResourceAsStream("/assets/boh/compat/subscribers.txt");
            BufferedReader r = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            String line;
            while ((line = r.readLine()) != null) {
                String[] p = line.trim().split("\\s+");
                if (p.length < 3) continue;
                if (p[2].equals("CLIENT") && !client) continue;
                try {
                    Object o = instantiate(Class.forName(p[0]));
                    if (p[1].equals("MOD")) {
                        MOD_BUS.register(o);
                    } else {
                        MinecraftForge.EVENT_BUS.register(o);
                        FMLCommonHandler.instance().bus().register(o);
                    }
                } catch (Throwable t) {
                    LOGGER.error("Could not register event subscriber " + p[0], t);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Could not read subscriber list", e);
        }
    }

    /** Subscriber handlers were static in 1.20 and use no instance state, so any instance will do. */
    private static Object instantiate(Class<?> c) throws Exception {
        try {
            java.lang.reflect.Constructor<?> k = c.getDeclaredConstructor();
            k.setAccessible(true);
            return k.newInstance();
        } catch (NoSuchMethodException e) {
            java.lang.reflect.Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            return ((sun.misc.Unsafe) f.get(null)).allocateInstance(c);
        }
    }

    public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder,
        BiConsumer<T, Supplier<Context>> messageConsumer) {
        PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
        messageID++;
    }

    public static void queueServerWork(int tick, Runnable action) {
        if (FMLCommonHandler.instance().getEffectiveSide().isServer()) workQueue.add(new SimpleEntry<>(action, tick));
    }

    @SubscribeEvent
    public void tick(ServerTickEvent event) {
        if (event.phase == Phase.END) {
            List<SimpleEntry<Runnable, Integer>> actions = new ArrayList<>();
            workQueue.forEach(work -> {
                work.setValue(work.getValue() - 1);
                if (work.getValue() == 0) actions.add(work);
            });
            actions.forEach(e -> {
                try {
                    e.getKey().run();
                } catch (Throwable t) {
                    LOGGER.error("Queued server work failed", t);
                }
            });
            workQueue.removeAll(actions);
        }
    }
}
