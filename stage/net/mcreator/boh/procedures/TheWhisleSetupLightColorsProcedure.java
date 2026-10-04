package net.mcreator.boh.procedures;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;
import java.lang.reflect.Field;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.mcreator.boh.compat.mc.client.renderer.DimensionSpecialEffects;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.util.Mth;
import net.minecraft.potion.Potion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.forge.client.DimensionSpecialEffectsManager;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.forge.fml.event.lifecycle.FMLClientSetupEvent;
import net.mcreator.boh.compat.mojang.math.Vector3f;
import net.mcreator.boh.compat.M;

public class TheWhisleSetupLightColorsProcedure {

    private static float skyLevel = 0.0F;

    private static float blockLevel = 0.0F;

    private static Vector3f skyColor = null;

    private static Vector3f blockColor = null;

    private static final Consumer<Object[]> CONSUMER = params -> {
        int pixelX = (Integer) params[5];
        int pixelY = (Integer) params[6];
        if (pixelX == 0 && pixelY == 0) {
            Minecraft minecraft = Minecraft.getMinecraft();
            Entity entity = M.getEntity(M.getMainCamera(M.gameRenderer(minecraft)));
            if (entity != null) {
                WorldClient level = M.level(minecraft);
                float partialTick = (Float) params[1];
                Vec3 pos = M.getPosition(entity, partialTick);
                execute(null, entity);
            }
        }
        calculateColor((Vector3f) params[7], pixelX, pixelY);
    };

    private static float calculateBaseLevel(float level) {
        return level * level * (level * -2.0F + 3.0F);
    }

    private static void calculateColor(Vector3f lightColor, int pixelX, int pixelY) {
        if (pixelX != pixelY) {
            if (pixelX > pixelY) {
                if (blockColor == null) {
                    return;
                }
                if (blockLevel == 0.0F) {
                    return;
                }
                float level = Math.abs(calculateBaseLevel(pixelX / 15.0F) - calculateBaseLevel(pixelY / 15.0F)) * blockLevel;
                M.set(lightColor, Mth.clamp(Mth.lerp(level, lightColor.x(), blockColor.x()), 0.0F, 1.0F), Mth.clamp(Mth.lerp(level, lightColor.y(), blockColor.y()), 0.0F, 1.0F), Mth.clamp(Mth.lerp(level, lightColor.z(), blockColor.z()), 0.0F, 1.0F));
            } else {
                if (skyColor == null) {
                    return;
                }
                if (skyLevel == 0.0F) {
                    return;
                }
                float level = Math.abs(pixelX - pixelY) / 15.0F * skyLevel;
                M.set(lightColor, Mth.clamp(Mth.lerp(level, lightColor.x(), skyColor.x()), 0.0F, 1.0F), Mth.clamp(Mth.lerp(level, lightColor.y(), skyColor.y()), 0.0F, 1.0F), Mth.clamp(Mth.lerp(level, lightColor.z(), skyColor.z()), 0.0F, 1.0F));
            }
        }
    }

    public static void setBlockColor(int blockColor) {
        setBlockColor(1.0F, blockColor);
    }

    public static void setBlockColor(float level, int blockColor) {
        blockLevel = Mth.clamp(level, 0.0F, 1.0F);
        TheWhisleSetupLightColorsProcedure.blockColor = new Vector3f((blockColor >> 16 & 0xFF) / 255.0F, (blockColor >> 8 & 0xFF) / 255.0F, (blockColor & 0xFF) / 255.0F);
    }

    public static void setSkyColor(int skyColor) {
        setSkyColor(1.0F, skyColor);
    }

    public static void setSkyColor(float level, int skyColor) {
        skyLevel = Mth.clamp(level, 0.0F, 1.0F);
        TheWhisleSetupLightColorsProcedure.skyColor = new Vector3f((skyColor >> 16 & 0xFF) / 255.0F, (skyColor >> 8 & 0xFF) / 255.0F, (skyColor & 0xFF) / 255.0F);
    }

    @SubscribeEvent
    public void lightColorSetup(FMLClientSetupEvent event) {
        try {
            Field field = DimensionSpecialEffectsManager.class.getDeclaredField("EFFECTS");
            field.setAccessible(true);
            UnmodifiableIterator var2 = ((ImmutableMap) field.get(null)).values().iterator();
            while (var2.hasNext()) {
                DimensionSpecialEffects dimensionSpecialEffects = (DimensionSpecialEffects) var2.next();
                Class<?> effects = dimensionSpecialEffects.getClass();
                ((Set) effects.getField("CUSTOM_LIGHTS").get(null)).add(CONSUMER);
            }
        } catch (Exception var5) {
        }
    }

    public static void execute(Entity entity) {
        execute(null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        if (entity != null) {
            if (M.dimension(M.level(entity)) == net.mcreator.boh.compat.world.Dimensions.dimensionKey( new ResourceLocation("boh:baseplate_dimension"))) {
                if (entity instanceof EntityLivingBase _livEnt3 && M.hasEffect(_livEnt3, (Potion) BohModMobEffects.THE_WHISLE.get())) {
                    setBlockColor(-65536);
                    setSkyColor(-65536);
                    setBlockColor(-65536);
                } else {
                    setBlockColor(-1);
                    setSkyColor(-1);
                    setBlockColor(-1);
                }
            }
        }
    }
}
