package net.mcreator.boh.compat.client.particle;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

import net.mcreator.boh.compat.mc.client.particle.Particle;
import net.mcreator.boh.compat.mc.client.particle.ParticleProvider;
import net.mcreator.boh.compat.mc.client.particle.SpriteSet;
import net.mcreator.boh.compat.mc.core.particles.ParticleType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/** Client registry of mod particle providers and their sprite sets (items atlas). */
public final class ParticleEngine {

    private static final Map<ParticleType<?>, ParticleProvider<Object>> PROVIDERS = new HashMap<>();
    private static final Map<ParticleType<?>, Sprites> SPRITES = new HashMap<>();
    public static final ParticleEngine EVENTS = new ParticleEngine();

    private ParticleEngine() {}

    public static final class Sprites implements SpriteSet {

        final List<String> names = new ArrayList<>();
        final List<IIcon> icons = new ArrayList<>();

        @Override
        public IIcon get(int age, int lifetime) {
            if (icons.isEmpty()) return null;
            return icons.get(Math.min(icons.size() - 1, age * (icons.size() - 1) / Math.max(1, lifetime)));
        }

        @Override
        public IIcon get(Random r) {
            return icons.isEmpty() ? null : icons.get(r.nextInt(icons.size()));
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> void registerSpriteSet(ParticleType<?> type, Function<SpriteSet, ? extends ParticleProvider<T>> factory) {
        Sprites s = new Sprites();
        ResourceLocation id = type.getId();
        if (id != null) {
            try (InputStream in = ParticleEngine.class.getResourceAsStream("/assets/" + id.getResourceDomain() + "/particles/"
                + id.getResourcePath() + ".json")) {
                if (in != null) {
                    JsonObject o = new JsonParser().parse(new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject();
                    for (JsonElement e : o.getAsJsonArray("textures")) {
                        String n = e.getAsString();
                        String ns = n.contains(":") ? n.substring(0, n.indexOf(':')) : "minecraft";
                        s.names.add(ns + ":particle/" + n.substring(n.indexOf(':') + 1));
                    }
                }
            } catch (Exception ignored) {}
        }
        SPRITES.put(type, s);
        PROVIDERS.put(type, (ParticleProvider<Object>) factory.apply(s));
    }

    @SuppressWarnings("unchecked")
    public static <T> void registerSprite(ParticleType<?> type, ParticleProvider<T> provider) {
        PROVIDERS.put(type, (ParticleProvider<Object>) provider);
    }

    @SubscribeEvent
    public void onStitch(TextureStitchEvent.Pre event) {
        if (event.map.getTextureType() != 1) return;
        TextureMap map = event.map;
        for (Sprites s : SPRITES.values()) {
            s.icons.clear();
            for (String n : s.names) s.icons.add(map.registerIcon(n));
        }
    }

    public static void spawn(ParticleType<?> type, double x, double y, double z, double dx, double dy, double dz) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld == null || mc.effectRenderer == null) return;
        ParticleProvider<Object> p = PROVIDERS.get(type);
        if (p == null) return;
        Particle particle = p.createParticle(type, mc.theWorld, x, y, z, dx, dy, dz);
        if (particle != null) mc.effectRenderer.addEffect(particle);
    }
}
