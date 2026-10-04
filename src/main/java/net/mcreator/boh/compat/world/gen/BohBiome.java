package net.mcreator.boh.compat.world.gen;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.mcreator.boh.compat.world.Spawning;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.IMob;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;

public class BohBiome extends BiomeGenBase {
    public final ResourceLocation key;
    public final JsonObject json;
    public final List<ResourceLocation> features = new ArrayList<>();
    public final int skyColor;
    public final int fogColor;
    public final int grassColor;
    public final int foliageColor;
    private static final Map<String, String> VANILLA = new HashMap<>();

    public BohBiome(int id, ResourceLocation key, JsonObject json) {
        super(id, false);
        this.key = key;
        this.json = json;
        JsonObject fx = json.getAsJsonObject("effects");
        this.skyColor = color(fx, "sky_color", 7907327);
        this.fogColor = color(fx, "fog_color", 12638463);
        this.grassColor = color(fx, "grass_color", -1);
        this.foliageColor = color(fx, "foliage_color", -1);
        this.waterColorMultiplier = color(fx, "water_color", 16777215);
        this.setBiomeName(key.getResourcePath());
        this.setColor(this.grassColor < 0 ? 6266944 : this.grassColor);
        float temp = json.has("temperature") ? json.get("temperature").getAsFloat() : 0.8F;
        float rain = json.has("downfall") ? json.get("downfall").getAsFloat() : 0.4F;
        this.setTemperatureRainfall(Math.max(-0.5F, Math.min(2.0F, temp)), rain);
        if (temp < 0.15F) {
            this.setEnableSnow();
        }

        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.flowersPerChunk = 0;
        this.theBiomeDecorator.grassPerChunk = 0;
        this.theBiomeDecorator.reedsPerChunk = 0;
        this.theBiomeDecorator.mushroomsPerChunk = 0;
        this.topBlock = Blocks.grass;
        this.fillerBlock = Blocks.dirt;
        JsonArray steps = json.getAsJsonArray("features");
        if (steps != null) {
            for (JsonElement step : steps) {
                for (JsonElement f : step.getAsJsonArray()) {
                    ResourceLocation rl = new ResourceLocation(f.getAsString());
                    if (!"minecraft".equals(rl.getResourceDomain())) {
                        this.features.add(rl);
                    }
                }
            }
        }
    }

    public void addSpawners() {
        JsonObject sp = this.json.getAsJsonObject("spawners");
        if (sp != null) {
            for (Entry<String, JsonElement> cat : sp.entrySet()) {
                for (JsonElement e : cat.getValue().getAsJsonArray()) {
                    JsonObject s = e.getAsJsonObject();
                    String t = s.get("type").getAsString().replace("minecraft:", "");
                    if (!t.contains(":")) {
                        String legacy = VANILLA.get(t);
                        Class<?> c = legacy == null ? null : (Class)EntityList.stringToClassMapping.get(legacy);
                        if (c != null) {
                            boolean monster = IMob.class.isAssignableFrom(c);
                            (monster ? this.spawnableMonsterList : this.spawnableCreatureList)
                                .add(new SpawnListEntry(c, s.get("weight").getAsInt(), s.get("minCount").getAsInt(), s.get("maxCount").getAsInt()));
                        }
                    } else {
                        try {
                            Spawning.add(s, new BiomeGenBase[]{this});
                        } catch (Exception var12) {
                        }
                    }
                }
            }
        }
    }

    private static int color(JsonObject fx, String k, int def) {
        return fx != null && fx.has(k) ? fx.get(k).getAsInt() & 16777215 : def;
    }

    public int getSkyColorByTemp(float t) {
        return this.skyColor;
    }

    public int getBiomeGrassColor(int x, int y, int z) {
        return this.grassColor < 0 ? super.getBiomeGrassColor(x, y, z) : this.grassColor;
    }

    public int getBiomeFoliageColor(int x, int y, int z) {
        return this.foliageColor < 0 ? super.getBiomeFoliageColor(x, y, z) : this.foliageColor;
    }

    static {
        for (String n : new String[]{
            "Zombie", "Skeleton", "Creeper", "Enderman", "Spider", "Wolf", "Chicken", "Sheep", "Pig", "Cow", "Witch", "Slime", "Squid", "Bat", "Rabbit"
        }) {
            VANILLA.put(n.toLowerCase(), n);
        }
    }
}
