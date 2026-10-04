package net.mcreator.boh.compat.world.gen;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;

/** A biome defined by a 1.20 worldgen/biome JSON (colors, climate, spawners and placed features). */
public class BohBiome extends BiomeGenBase {

    public final ResourceLocation key;
    public final JsonObject json;
    public final List<ResourceLocation> features = new ArrayList<>();
    public final int skyColor, fogColor, grassColor, foliageColor;

    public BohBiome(int id, ResourceLocation key, JsonObject json) {
        super(id, false);
        this.key = key;
        this.json = json;
        JsonObject fx = json.getAsJsonObject("effects");
        skyColor = color(fx, "sky_color", 0x78A7FF);
        fogColor = color(fx, "fog_color", 0xC0D8FF);
        grassColor = color(fx, "grass_color", -1);
        foliageColor = color(fx, "foliage_color", -1);
        waterColorMultiplier = color(fx, "water_color", 0xFFFFFF);
        setBiomeName(key.getResourcePath());
        setColor(grassColor < 0 ? 0x5FA040 : grassColor);
        float temp = json.has("temperature") ? json.get("temperature").getAsFloat() : 0.8F;
        float rain = json.has("downfall") ? json.get("downfall").getAsFloat() : 0.4F;
        setTemperatureRainfall(Math.max(-0.5F, Math.min(2.0F, temp)), rain);
        if (temp < 0.15F) setEnableSnow();
        spawnableMonsterList.clear();
        spawnableCreatureList.clear();
        spawnableWaterCreatureList.clear();
        spawnableCaveCreatureList.clear();
        theBiomeDecorator.treesPerChunk = -999;
        theBiomeDecorator.flowersPerChunk = 0;
        theBiomeDecorator.grassPerChunk = 0;
        theBiomeDecorator.reedsPerChunk = 0;
        theBiomeDecorator.mushroomsPerChunk = 0;
        topBlock = Blocks.grass;
        fillerBlock = Blocks.dirt;
        JsonArray steps = json.getAsJsonArray("features");
        if (steps != null) for (JsonElement step : steps) for (JsonElement f : step.getAsJsonArray()) {
            ResourceLocation rl = new ResourceLocation(f.getAsString());
            if (!"minecraft".equals(rl.getResourceDomain())) features.add(rl);
        }
    }

    /** Adds the JSON spawners (after entity registration). */
    public void addSpawners() {
        JsonObject sp = json.getAsJsonObject("spawners");
        if (sp == null) return;
        for (java.util.Map.Entry<String, JsonElement> cat : sp.entrySet())
            for (JsonElement e : cat.getValue().getAsJsonArray()) {
                JsonObject s = e.getAsJsonObject();
                String t = s.get("type").getAsString().replace("minecraft:", "");
                if (!t.contains(":")) {
                    String legacy = VANILLA.get(t);
                    Class<?> c = legacy == null ? null : (Class<?>) net.minecraft.entity.EntityList.stringToClassMapping.get(legacy);
                    if (c != null) {
                        boolean monster = net.minecraft.entity.monster.IMob.class.isAssignableFrom(c);
                        @SuppressWarnings("unchecked")
                        Class<? extends net.minecraft.entity.EntityLiving> lc = (Class<? extends net.minecraft.entity.EntityLiving>) c;
                        (monster ? spawnableMonsterList : spawnableCreatureList).add(new SpawnListEntry(lc, s.get("weight").getAsInt(),
                            s.get("minCount").getAsInt(), s.get("maxCount").getAsInt()));
                    }
                    continue;
                }
                try {
                    net.mcreator.boh.compat.world.Spawning.add(s, new BiomeGenBase[] { this });
                } catch (Exception ex) {
                    // entity without a 1.7.10 counterpart
                }
            }
    }

    private static final java.util.Map<String, String> VANILLA = new java.util.HashMap<>();

    static {
        for (String n : new String[] { "Zombie", "Skeleton", "Creeper", "Enderman", "Spider", "Wolf", "Chicken", "Sheep", "Pig", "Cow", "Witch",
            "Slime", "Squid", "Bat", "Rabbit" })
            VANILLA.put(n.toLowerCase(), n);
    }

    private static int color(JsonObject fx, String k, int def) {
        return fx != null && fx.has(k) ? fx.get(k).getAsInt() & 0xFFFFFF : def;
    }

    @Override
    public int getSkyColorByTemp(float t) {
        return skyColor;
    }

    @Override
    public int getBiomeGrassColor(int x, int y, int z) {
        return grassColor < 0 ? super.getBiomeGrassColor(x, y, z) : grassColor;
    }

    @Override
    public int getBiomeFoliageColor(int x, int y, int z) {
        return foliageColor < 0 ? super.getBiomeFoliageColor(x, y, z) : foliageColor;
    }
}
