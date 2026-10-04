package net.mcreator.boh.compat.client.particle;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
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
import net.minecraftforge.client.event.TextureStitchEvent.Pre;

public final class ParticleEngine {
    private static final Map<ParticleType<?>, ParticleProvider<Object>> PROVIDERS = new HashMap<>();
    private static final Map<ParticleType<?>, ParticleEngine.Sprites> SPRITES = new HashMap<>();
    public static final ParticleEngine EVENTS = new ParticleEngine();

    private ParticleEngine() {
    }

    public static <T> void registerSpriteSet(ParticleType<?> type, Function<SpriteSet, ? extends ParticleProvider<T>> factory) {
        ParticleEngine.Sprites s = new ParticleEngine.Sprites();
        ResourceLocation id = type.getId();
        if (id != null) {
            try (InputStream in = ParticleEngine.class.getResourceAsStream("/assets/" + id.getResourceDomain() + "/particles/" + id.getResourcePath() + ".json")) {
                if (in != null) {
                    JsonObject o = new JsonParser().parse(new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject();

                    for (JsonElement e : o.getAsJsonArray("textures")) {
                        String n = e.getAsString();
                        String ns = n.contains(":") ? n.substring(0, n.indexOf(58)) : "minecraft";
                        s.names.add(ns + ":particle/" + n.substring(n.indexOf(58) + 1));
                    }
                }
            } catch (Exception var12) {
            }
        }

        SPRITES.put(type, s);
        PROVIDERS.put(type, (ParticleProvider<Object>)factory.apply(s));
    }

    public static <T> void registerSprite(ParticleType<?> type, ParticleProvider<T> provider) {
        PROVIDERS.put(type, provider);
    }

    @SubscribeEvent
    public void onStitch(Pre event) {
        if (event.map.getTextureType() == 1) {
            TextureMap map = event.map;

            for (ParticleEngine.Sprites s : SPRITES.values()) {
                s.icons.clear();

                for (String n : s.names) {
                    s.icons.add(map.registerIcon(n));
                }
            }
        }
    }

    public static void spawn(ParticleType<?> type, double x, double y, double z, double dx, double dy, double dz) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld != null && mc.effectRenderer != null) {
            ParticleProvider<Object> p = PROVIDERS.get(type);
            if (p != null) {
                Particle particle = p.createParticle(type, mc.theWorld, x, y, z, dx, dy, dz);
                if (particle != null) {
                    mc.effectRenderer.addEffect(particle);
                }
            }
        }
    }

    public static final class Sprites implements SpriteSet {
        final List<String> names = new ArrayList<>();
        final List<IIcon> icons = new ArrayList<>();

        @Override
        public IIcon get(int age, int lifetime) {
            return this.icons.isEmpty() ? null : this.icons.get(Math.min(this.icons.size() - 1, age * (this.icons.size() - 1) / Math.max(1, lifetime)));
        }

        @Override
        public IIcon get(Random r) {
            return this.icons.isEmpty() ? null : this.icons.get(r.nextInt(this.icons.size()));
        }
    }
}
