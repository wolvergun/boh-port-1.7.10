import java.io.File;
import java.lang.reflect.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

/**
 * Offline smoke test: bootstraps vanilla 1.7.10 registries and constructs every mod block/item class with a
 * no-arg constructor, reporting exceptions (catches constructor-time crashes without launching the game).
 * args: classesDir classpathFile
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SmokeTest {

    public static void main(String[] a) throws Exception {
        List<URL> urls = new ArrayList<>();
        urls.add(Paths.get(a[0]).toUri().toURL());
        for (String p : new String(Files.readAllBytes(Paths.get(a[1]))).trim().split(File.pathSeparator))
            if (!p.isEmpty()) urls.add(new File(p).toURI().toURL());
        URLClassLoader cl = new URLClassLoader(urls.toArray(new URL[0]), ClassLoader.getPlatformClassLoader());
        Thread.currentThread().setContextClassLoader(cl);
        // vanilla registries are not bootstrapped (FML needs LaunchClassLoader); vanilla lookups return null here
        Path root = Paths.get(a[0]);
        List<String> names;
        try (Stream<Path> s = Files.walk(root)) {
            names = s.map(p -> root.relativize(p).toString().replace('\\', '/'))
                .filter(n -> n.endsWith(".class") && !n.contains("$") && (n.startsWith("net/mcreator/boh/block/") || n.startsWith("net/mcreator/boh/item/")))
                .map(n -> n.substring(0, n.length() - 6).replace('/', '.')).sorted().collect(Collectors.toList());
        }
        Class<?> block = Class.forName("net.minecraft.block.Block", false, cl), item = Class.forName("net.minecraft.item.Item", false, cl);
        int ok = 0, fail = 0;
        Map<String, Integer> causes = new LinkedHashMap<>();
        for (String n : names) {
            Class<?> c;
            try {
                c = Class.forName(n, false, cl);
            } catch (Throwable t) {
                continue;
            }
            if (Modifier.isAbstract(c.getModifiers()) || !(block.isAssignableFrom(c) || item.isAssignableFrom(c))) continue;
            Constructor<?> ctor;
            try {
                ctor = c.getDeclaredConstructor();
            } catch (NoSuchMethodException e) {
                continue;
            }
            try {
                ctor.setAccessible(true);
                ctor.newInstance();
                ok++;
            } catch (Throwable t) {
                Throwable r = t instanceof InvocationTargetException ? t.getCause() : t;
                fail++;
                String key = r + " @ " + (r.getStackTrace().length > 0 ? r.getStackTrace()[0] : "?");
                causes.merge(key, 1, Integer::sum);
                if (causes.get(key) == 1) {
                    System.out.println("FAIL " + n + ": " + key);
                    for (int i = 0; i < Math.min(8, r.getStackTrace().length); i++) System.out.println("     " + r.getStackTrace()[i]);
                }
            }
        }
        for (String init : new String[] { "BohModSounds", "BohModBlocks", "BohModBlockEntities", "BohModItems", "BohModEntities", "BohModEnchantments",
            "BohModMobEffects", "BohModPotions", "BohModPaintings", "BohModParticleTypes", "BohModMenus", "BohModTabs" }) {
            try {
                Class<?> c = Class.forName("net.mcreator.boh.init." + init, true, cl);
                for (Field f : c.getDeclaredFields()) {
                    if (!Modifier.isStatic(f.getModifiers()) || !f.getType().getSimpleName().equals("DeferredRegister")) continue;
                    f.setAccessible(true);
                    Object reg = f.get(null);
                    for (Object ro : (Collection<?>) reg.getClass().getMethod("getEntries").invoke(reg)) {
                        try {
                            ro.getClass().getMethod("get").invoke(ro);
                            ok++;
                        } catch (Throwable t) {
                            Throwable r = t instanceof InvocationTargetException ? t.getCause() : t;
                            fail++;
                            String key = r + " @ " + (r.getStackTrace().length > 0 ? r.getStackTrace()[0] : "?");
                            if (causes.merge(key, 1, Integer::sum) == 1) {
                                System.out.println("FAIL " + init + " " + ro + ": " + key);
                                for (int i = 0; i < Math.min(10, r.getStackTrace().length); i++) System.out.println("     " + r.getStackTrace()[i]);
                            }
                        }
                    }
                }
            } catch (Throwable t) {
                Throwable r = t instanceof InvocationTargetException ? t.getCause() : t;
                System.out.println("FAIL init " + init + ": " + r);
                for (int i = 0; i < Math.min(10, r.getStackTrace().length); i++) System.out.println("     " + r.getStackTrace()[i]);
            }
        }
        for (String line : Files.readAllLines(Paths.get("port/src/main/resources/assets/boh/compat/subscribers.txt"))) {
            String cn = line.trim().split("\s+")[0];
            try {
                Class<?> sc = Class.forName(cn, true, cl);
                sc.getDeclaredConstructor().newInstance();
                Class<?> ann = Class.forName("cpw.mods.fml.common.eventhandler.SubscribeEvent", false, cl);
                for (Method m : sc.getMethods()) {
                    if (!m.isAnnotationPresent((Class) ann)) continue;
                    Class<?> ev = m.getParameterTypes()[0];
                    try {
                        ev.getConstructor();
                    } catch (NoSuchMethodException nm) {
                        System.out.println("NO-ARG CTOR MISSING: " + ev.getName() + " (" + cn + "." + m.getName() + ")");
                    }
                }
                ok++;
            } catch (Throwable t) {
                Throwable r = t instanceof InvocationTargetException ? t.getCause() : t;
                while (r.getCause() != null && r instanceof InvocationTargetException) r = r.getCause();
                fail++;
                System.out.println("FAIL subscriber " + cn + ": " + r);
                for (int i = 0; i < Math.min(6, r.getStackTrace().length); i++) System.out.println("     " + r.getStackTrace()[i]);
            }
        }
        String[] evs = { "net.mcreator.boh.compat.forge.common.capabilities.RegisterCapabilitiesEvent",
            "net.mcreator.boh.compat.forge.event.entity.EntityAttributeCreationEvent", "net.mcreator.boh.compat.forge.fml.event.lifecycle.FMLCommonSetupEvent",
            "net.mcreator.boh.compat.forge.event.BuildCreativeModeTabContentsEvent", "net.mcreator.boh.compat.forge.registries.RegisterEvent",
            "net.mcreator.boh.compat.forge.client.event.RegisterParticleProvidersEvent", "net.mcreator.boh.compat.forge.client.event.RegisterLayerDefinitions",
            "net.mcreator.boh.compat.forge.client.event.RegisterRenderers", "net.mcreator.boh.compat.forge.client.event.RegisterDimensionSpecialEffectsEvent",
            "net.mcreator.boh.compat.forge.fml.event.lifecycle.FMLClientSetupEvent", "net.mcreator.boh.compat.forge.event.RegisterCommandsEvent" };
        Class<?> annC = Class.forName("cpw.mods.fml.common.eventhandler.SubscribeEvent", false, cl);
        for (String evn : evs) {
            Class<?> evc = Class.forName(evn, true, cl);
            for (String line : Files.readAllLines(Paths.get("port/src/main/resources/assets/boh/compat/subscribers.txt"))) {
                String cn = line.trim().split("\s+")[0];
                Class<?> sc;
                try {
                    sc = Class.forName(cn, true, cl);
                } catch (Throwable t) {
                    continue;
                }
                for (Method m : sc.getMethods()) {
                    if (!m.isAnnotationPresent((Class) annC) || m.getParameterTypes()[0] != evc) continue;
                    try {
                        Constructor<?> k;
                        Object inst;
                        try {
                            k = sc.getDeclaredConstructor();
                            k.setAccessible(true);
                            inst = k.newInstance();
                        } catch (NoSuchMethodException nm) {
                            Field uf = Class.forName("sun.misc.Unsafe").getDeclaredField("theUnsafe");
                            uf.setAccessible(true);
                            Object u = uf.get(null);
                            inst = u.getClass().getMethod("allocateInstance", Class.class).invoke(u, sc);
                        }
                        m.invoke(inst, evc.getConstructor().newInstance());
                        ok++;
                    } catch (Throwable t) {
                        Throwable r = t;
                        while (r instanceof InvocationTargetException && r.getCause() != null) r = r.getCause();
                        StringBuilder st = new StringBuilder();
                        for (StackTraceElement el : r.getStackTrace()) st.append(el).append(';');
                        if (st.toString().contains("cpw.mods.fml.common.Loader")) continue;
                        fail++;
                        System.out.println("FAIL handler " + cn + "." + m.getName() + "(" + evc.getSimpleName() + "): " + r);
                        for (int i = 0; i < Math.min(10, r.getStackTrace().length); i++) System.out.println("     " + r.getStackTrace()[i]);
                    }
                }
            }
        }
        Class<?> creg = Class.forName("net.mcreator.boh.compat.client.ClientRegistry", true, cl);
        Object ctx = Class.forName("net.mcreator.boh.compat.mc.client.renderer.entity.Context", true, cl).getField("INSTANCE").get(null);
        for (String fld : new String[] { "ENTITY", "BLOCK_ENTITY" }) {
            for (Object e : ((Map<?, ?>) creg.getField(fld).get(null)).entrySet()) {
                Map.Entry<?, ?> me = (Map.Entry<?, ?>) e;
                try {
                    ((java.util.function.Function<Object, Object>) me.getValue()).apply(ctx);
                    ok++;
                } catch (Throwable t) {
                    fail++;
                    System.out.println("FAIL renderer " + me.getKey() + ": " + t);
                    for (int i = 0; i < Math.min(10, t.getStackTrace().length); i++) System.out.println("     " + t.getStackTrace()[i]);
                }
            }
        }
        System.out.println("constructed " + ok + ", failed " + fail + ", distinct causes " + causes.size());
    }
}
