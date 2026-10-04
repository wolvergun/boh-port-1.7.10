package net.mcreator.boh.compat.client;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.command.ParticleNames;
import net.mcreator.boh.compat.world.gen.BohBiome;
import net.mcreator.boh.compat.world.gen.BohWorldProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.particle.EntityAuraFX;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

/**
 * The 1.20 biome "effects" that 1.7.10 has no equivalent for, read from the mod biomes' JSON: the looping
 * ambient_sound (fades in/out over 2 s), mood_sound (builds up in the dark), additions_sound (per-tick chance),
 * biome music (replaces the vanilla music ticker's track), the ambient particle, and the fog colour of mod biomes
 * placed in vanilla dimensions (mod dimensions colour their fog in BohWorldProvider). Follows the 1.20.1
 * BiomeAmbientSoundsHandler / MusicManager / ClientLevel.animateTick logic.
 */
public final class BiomeAmbience {

    private static final Map<BiomeGenBase, Effects> EFFECTS = new HashMap<>();
    private final Random random = new Random();
    private final Minecraft mc = Minecraft.getMinecraft();
    private Loop loop;
    private float moodiness;
    private ISound music;
    private int nextSongDelay = 100;
    private Field vanillaMusic, vanillaMusicDelay, tickerField;

    private BiomeAmbience() {}

    public static void register() {
        BiomeAmbience a = new BiomeAmbience();
        FMLCommonHandler.instance().bus().register(a);
        MinecraftForge.EVENT_BUS.register(a);
    }

    /** Parsed effects of one biome; null fields when the biome does not define them. */
    private static final class Effects {

        ResourceLocation ambient, mood, additions, music;
        int moodDelay = 6000, moodExtent = 8, musicMin = 12000, musicMax = 24000;
        double moodOffset = 2.0, additionsChance;
        boolean replaceMusic;
        String particle;
        float particleChance;

        static Effects of(BiomeGenBase b) {
            return EFFECTS.computeIfAbsent(b, k -> k instanceof BohBiome ? parse(((BohBiome) k).json) : new Effects());
        }

        static Effects parse(JsonObject biome) {
            Effects e = new Effects();
            JsonObject fx = biome == null ? null : biome.getAsJsonObject("effects");
            if (fx == null) return e;
            try {
                if (fx.has("ambient_sound")) e.ambient = sound(fx.get("ambient_sound"));
                if (fx.has("mood_sound")) {
                    JsonObject m = fx.getAsJsonObject("mood_sound");
                    e.mood = sound(m.get("sound"));
                    e.moodDelay = m.get("tick_delay").getAsInt();
                    e.moodExtent = m.get("block_search_extent").getAsInt();
                    e.moodOffset = m.get("offset").getAsDouble();
                }
                if (fx.has("additions_sound")) {
                    JsonObject a = fx.getAsJsonObject("additions_sound");
                    e.additions = sound(a.get("sound"));
                    e.additionsChance = a.get("tick_chance").getAsDouble();
                }
                if (fx.has("music")) {
                    JsonObject m = fx.getAsJsonObject("music");
                    e.music = sound(m.get("sound"));
                    e.musicMin = m.get("min_delay").getAsInt();
                    e.musicMax = m.get("max_delay").getAsInt();
                    e.replaceMusic = m.get("replace_current_music").getAsBoolean();
                }
                if (fx.has("particle")) {
                    JsonObject p = fx.getAsJsonObject("particle");
                    e.particle = p.getAsJsonObject("options").get("type").getAsString().replace("minecraft:", "");
                    e.particleChance = p.get("probability").getAsFloat();
                }
            } catch (RuntimeException ex) {
                BohMod.LOGGER.warn("Bad biome effects {}", fx, ex);
            }
            return e;
        }

        private static ResourceLocation sound(JsonElement e) {
            String s = e.isJsonObject() ? e.getAsJsonObject().get("sound_id").getAsString() : e.getAsString();
            return new ResourceLocation(s);
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent e) {
        if (e.phase != TickEvent.Phase.END) return;
        EntityClientPlayerMP player = mc.thePlayer;
        if (player == null || mc.theWorld == null || mc.isGamePaused()) return;
        World world = mc.theWorld;
        BiomeGenBase biome = world.getBiomeGenForCoords(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posZ));
        Effects fx = Effects.of(biome);
        tickLoop(biome, fx);
        tickMood(world, player, fx);
        if (fx.additions != null && random.nextDouble() < fx.additionsChance) mc.getSoundHandler().playSound(PositionedSoundRecord.func_147673_a(fx.additions));
        tickMusic(fx);
        tickParticles(world, player);
    }

    private void tickLoop(BiomeGenBase biome, Effects fx) {
        if (loop != null && (loop.isDonePlaying() || !mc.getSoundHandler().isSoundPlaying(loop)) && loop.fade <= 0) loop = null;
        if (fx.ambient == null || (loop != null && loop.biome == biome)) return;
        if (loop != null) loop.fadeOut();
        loop = new Loop(fx.ambient, biome);
        mc.getSoundHandler().playSound(loop);
    }

    private void tickMood(World world, EntityClientPlayerMP player, Effects fx) {
        if (fx.mood == null) {
            moodiness = 0;
            return;
        }
        int span = fx.moodExtent * 2 + 1;
        int x = MathHelper.floor_double(player.posX) + random.nextInt(span) - fx.moodExtent;
        int y = MathHelper.floor_double(player.posY + player.getEyeHeight()) + random.nextInt(span) - fx.moodExtent;
        int z = MathHelper.floor_double(player.posZ) + random.nextInt(span) - fx.moodExtent;
        int sky = world.getSavedLightValue(EnumSkyBlock.Sky, x, y, z);
        if (sky > 0) moodiness -= sky / 15.0F * 0.001F;
        else moodiness -= (world.getSavedLightValue(EnumSkyBlock.Block, x, y, z) - 1) / (float) fx.moodDelay;
        if (moodiness >= 1.0F) {
            double dx = x + 0.5 - player.posX, dy = y + 0.5 - (player.posY + player.getEyeHeight()), dz = z + 0.5 - player.posZ;
            double dist = Math.max(Math.sqrt(dx * dx + dy * dy + dz * dz), 0.001);
            double out = dist + fx.moodOffset;
            mc.getSoundHandler().playSound(new PositionedSoundRecord(fx.mood, 0.5F, 0.8F + random.nextFloat() * 0.4F,
                (float) (player.posX + dx / dist * out), (float) (player.posY + player.getEyeHeight() + dy / dist * out), (float) (player.posZ + dz / dist * out)));
            moodiness = 0;
        } else {
            moodiness = Math.max(moodiness, 0);
        }
    }

    private void tickMusic(Effects fx) {
        if (music != null && !mc.getSoundHandler().isSoundPlaying(music)) {
            music = null;
            if (fx.music != null) nextSongDelay = Math.min(nextSongDelay, MathHelper.getRandomIntegerInRange(random, fx.musicMin, fx.musicMax));
        }
        if (fx.music == null) {
            if (music == null) return;
        } else {
            nextSongDelay = Math.min(nextSongDelay, fx.musicMax);
            ISound vanilla = vanillaMusic();
            if (vanilla != null && music == null && fx.replaceMusic) {
                mc.getSoundHandler().stopSound(vanilla);
                setVanillaMusic(null);
                nextSongDelay = MathHelper.getRandomIntegerInRange(random, 0, fx.musicMin / 2);
            }
            if (music == null && vanillaMusic() == null && nextSongDelay-- <= 0) {
                music = PositionedSoundRecord.func_147673_a(fx.music);
                mc.getSoundHandler().playSound(music);
            }
        }
        // keep the vanilla ticker from starting a track over the biome music (or before it while in the biome)
        if (music != null || fx.music != null) holdVanillaMusic();
    }

    private void tickParticles(World world, EntityClientPlayerMP player) {
        int setting = mc.gameSettings.particleSetting;
        if (setting >= 2) return;
        int px = MathHelper.floor_double(player.posX), py = MathHelper.floor_double(player.posY), pz = MathHelper.floor_double(player.posZ);
        // ClientLevel.animateTick: 667 random blocks within 16 and 667 within 32 each tick
        for (int i = 0; i < 1334; i++) {
            int r = i < 667 ? 16 : 32;
            int x = px + random.nextInt(r) - random.nextInt(r), y = py + random.nextInt(r) - random.nextInt(r), z = pz + random.nextInt(r) - random.nextInt(r);
            Effects fx = Effects.of(world.getBiomeGenForCoords(x, z));
            if (fx.particle == null || random.nextFloat() > fx.particleChance) continue;
            if (setting == 1 && random.nextBoolean()) continue;
            if (world.getBlock(x, y, z).isNormalCube()) continue;
            spawnParticle(world, fx.particle, x + random.nextDouble(), y + random.nextDouble(), z + random.nextDouble());
        }
    }

    private void spawnParticle(World world, String type, double x, double y, double z) {
        if (type.equals("white_ash") || type.equals("ash")) {
            EntityAuraFX p = new EntityAuraFX(world, x, y, z, 0, 0, 0);
            float c = type.equals("white_ash") ? 0.75F + random.nextFloat() * 0.2F : 0.25F + random.nextFloat() * 0.1F;
            p.setRBGColorF(c, c, c);
            mc.effectRenderer.addEffect(p);
        } else {
            String legacy = ParticleNames.legacy(type);
            if (legacy != null) world.spawnParticle(legacy, x, y, z, 0, 0, 0);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onFogColor(EntityViewRenderEvent.FogColors e) {
        World world = mc.theWorld;
        if (world == null || world.provider instanceof BohWorldProvider) return;
        double x = e.entity.posX, z = e.entity.posZ;
        float sun = MathHelper.clamp_float(MathHelper.cos(world.getCelestialAngle((float) e.renderPartialTicks) * (float) Math.PI * 2.0F) * 2.0F + 0.5F, 0.0F, 1.0F);
        float light = sun * 0.94F + 0.06F;
        // blend over a 5x5 grid of biomes 4 blocks apart (1.20 samples biome fog with a cubic kernel)
        float r = 0, g = 0, b = 0;
        int samples = 0, boh = 0;
        for (int i = -2; i <= 2; i++) for (int j = -2; j <= 2; j++) {
            BiomeGenBase bg = world.getBiomeGenForCoords(MathHelper.floor_double(x) + i * 4, MathHelper.floor_double(z) + j * 4);
            samples++;
            if (bg instanceof BohBiome) {
                int c = ((BohBiome) bg).fogColor;
                r += (c >> 16 & 255) / 255.0F * light;
                g += (c >> 8 & 255) / 255.0F * light;
                b += (c & 255) / 255.0F * light;
                boh++;
            } else {
                r += e.red;
                g += e.green;
                b += e.blue;
            }
        }
        if (boh == 0) return;
        e.red = r / samples;
        e.green = g / samples;
        e.blue = b / samples;
    }

    // ---- vanilla MusicTicker fields (unnamed in 1.7.10 MCP, same names at runtime)

    private MusicTicker musicTicker() throws ReflectiveOperationException {
        if (tickerField == null) {
            try {
                tickerField = Minecraft.class.getDeclaredField("mcMusicTicker");
            } catch (NoSuchFieldException e) {
                tickerField = Minecraft.class.getDeclaredField("field_147126_aw");
            }
            tickerField.setAccessible(true);
        }
        return (MusicTicker) tickerField.get(mc);
    }

    private ISound vanillaMusic() {
        try {
            if (vanillaMusic == null) {
                vanillaMusic = MusicTicker.class.getDeclaredField("field_147678_c");
                vanillaMusic.setAccessible(true);
            }
            return (ISound) vanillaMusic.get(musicTicker());
        } catch (ReflectiveOperationException ex) {
            return null;
        }
    }

    private void setVanillaMusic(ISound s) {
        try {
            vanillaMusic.set(musicTicker(), s);
        } catch (ReflectiveOperationException | RuntimeException ex) {
            // music ticker layout changed: leave vanilla music alone
        }
    }

    private void holdVanillaMusic() {
        try {
            if (vanillaMusicDelay == null) {
                vanillaMusicDelay = MusicTicker.class.getDeclaredField("field_147676_d");
                vanillaMusicDelay.setAccessible(true);
            }
            int d = vanillaMusicDelay.getInt(musicTicker());
            if (d < 200) vanillaMusicDelay.setInt(musicTicker(), 200);
        } catch (ReflectiveOperationException ex) {
            // ignore
        }
    }

    /** 1.20 BiomeAmbientSoundsHandler loop: fades in over 40 ticks, out over 40 ticks after leaving its biome. */
    private final class Loop extends MovingSound {

        final BiomeGenBase biome;
        int fade;
        boolean fadingOut;

        Loop(ResourceLocation sound, BiomeGenBase biome) {
            super(sound);
            this.biome = biome;
            repeat = true;
            field_147665_h = 0;
            field_147666_i = ISound.AttenuationType.NONE;
            volume = 0.01F;
        }

        void fadeOut() {
            fadingOut = true;
        }

        @Override
        public void update() {
            EntityClientPlayerMP p = mc.thePlayer;
            if (p == null || p.isDead) {
                donePlaying = true;
                return;
            }
            if (!fadingOut && p.worldObj.getBiomeGenForCoords(MathHelper.floor_double(p.posX), MathHelper.floor_double(p.posZ)) != biome) fadingOut = true;
            fade = MathHelper.clamp_int(fade + (fadingOut ? -1 : 1), 0, 40);
            volume = Math.max(fade / 40.0F, 0.01F);
            if (fadingOut && fade <= 0) donePlaying = true;
        }
    }
}
