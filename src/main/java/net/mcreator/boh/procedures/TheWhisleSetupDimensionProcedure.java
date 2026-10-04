package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.client.LightTexture;
import net.mcreator.boh.compat.client.Matrix4f;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.forge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.mcreator.boh.compat.mc.client.Camera;
import net.mcreator.boh.compat.mc.client.renderer.DimensionSpecialEffects;
import net.mcreator.boh.compat.mc.client.renderer.SkyType;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.mojang.blaze3d.systems.RenderSystem;
import net.mcreator.boh.compat.mojang.math.Vector3f;
import net.mcreator.boh.compat.world.Dimensions;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class TheWhisleSetupDimensionProcedure {
    private static RegisterDimensionSpecialEffectsEvent provider = null;

    public static void register(String name, DimensionSpecialEffects effects) {
        provider.register(new ResourceLocation("boh", name), effects);
    }

    public static void register(ResourceKey<World> dimension, DimensionSpecialEffects effects) {
        provider.register(M.location(dimension), effects);
    }

    public static DimensionSpecialEffects createOverworldEffects(boolean constantWhiteLight, boolean constantAmbientLight, final boolean fog) {
        return new TheWhisleSetupDimensionProcedure.BohModDimensionSpecialEffects(192.0F, true, SkyType.NORMAL, constantWhiteLight, constantAmbientLight) {
            @Override
            public Vec3 getBrightnessDependentFogColor(Vec3 color, float sunHeight) {
                return M.multiply(color, sunHeight * 0.94F + 0.06F, sunHeight * 0.94F + 0.06F, sunHeight * 0.91F + 0.09F);
            }

            @Override
            public boolean isFoggyAt(int x, int y) {
                return fog;
            }
        };
    }

    public static DimensionSpecialEffects createNetherEffects(boolean constantWhiteLight, boolean constantAmbientLight, final boolean fog) {
        return new TheWhisleSetupDimensionProcedure.BohModDimensionSpecialEffects(Float.NaN, true, SkyType.NONE, constantWhiteLight, constantAmbientLight) {
            @Override
            public Vec3 getBrightnessDependentFogColor(Vec3 color, float sunHeight) {
                return color;
            }

            @Override
            public boolean isFoggyAt(int x, int y) {
                return fog;
            }
        };
    }

    public static DimensionSpecialEffects createEndEffects(boolean constantWhiteLight, boolean constantAmbientLight, final boolean fog) {
        return new TheWhisleSetupDimensionProcedure.BohModDimensionSpecialEffects(Float.NaN, false, SkyType.END, constantWhiteLight, constantAmbientLight) {
            @Override
            public Vec3 getBrightnessDependentFogColor(Vec3 color, float sunHeight) {
                return color.scale(0.15F);
            }

            @Override
            public boolean isFoggyAt(int x, int y) {
                return fog;
            }
        };
    }

    @SubscribeEvent(
        priority = EventPriority.LOWEST
    )
    public void setupDimensions(RegisterDimensionSpecialEffectsEvent event) {
        provider = event;
        execute(event);
    }

    public static void execute() {
        execute(null);
    }

    private static void execute(@Nullable Event event) {
        register(Dimensions.dimensionKey(new ResourceLocation("boh:baseplate_dimension")), createOverworldEffects(false, false, false));
    }

    public abstract static class BohModDimensionSpecialEffects extends DimensionSpecialEffects {
        public static final Set<Predicate<Object[]>> CUSTOM_CLOUDS = new HashSet<>();
        public static final Set<Predicate<Object[]>> CUSTOM_SKY = new HashSet<>();
        public static final Set<Predicate<Object[]>> CUSTOM_WEATHER = new HashSet<>();
        public static final Set<Predicate<Object[]>> CUSTOM_EFFECTS = new HashSet<>();
        public static final Set<Consumer<Object[]>> CUSTOM_LIGHTS = new HashSet<>();

        public BohModDimensionSpecialEffects(float cloudHeight, boolean hasGround, SkyType skyType, boolean forceBrightLightmap, boolean constantAmbientLight) {
            super(cloudHeight, hasGround, skyType, forceBrightLightmap, constantAmbientLight);
        }

        @Override
        public boolean renderClouds(
            WorldClient level, int ticks, float partialTick, PoseStack poseStack, double camX, double camY, double camZ, Matrix4f projectionMatrix
        ) {
            if (CUSTOM_CLOUDS != null && !M.isEmpty(CUSTOM_CLOUDS)) {
                boolean flag = false;
                Object[] objects = new Object[]{level, ticks, partialTick, poseStack, camX, camY, camZ, projectionMatrix};

                for (Predicate<Object[]> predicate : CUSTOM_CLOUDS) {
                    RenderSystem.depthMask(true);
                    RenderSystem.enableDepthTest();
                    RenderSystem.disableCull();
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    flag |= predicate.test(objects);
                }

                RenderSystem.defaultBlendFunc();
                RenderSystem.disableBlend();
                RenderSystem.enableCull();
                RenderSystem.enableDepthTest();
                RenderSystem.depthMask(true);
                return flag;
            } else {
                return true;
            }
        }

        @Override
        public boolean renderSky(
            WorldClient level, int ticks, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog
        ) {
            if (CUSTOM_SKY != null && !M.isEmpty(CUSTOM_SKY)) {
                boolean flag = false;
                Object[] objects = new Object[]{level, ticks, partialTick, poseStack, camera, projectionMatrix, isFoggy, setupFog};

                for (Predicate<Object[]> predicate : CUSTOM_SKY) {
                    RenderSystem.depthMask(false);
                    RenderSystem.enableDepthTest();
                    RenderSystem.enableCull();
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    flag |= predicate.test(objects);
                }

                RenderSystem.defaultBlendFunc();
                RenderSystem.disableBlend();
                RenderSystem.enableCull();
                RenderSystem.enableDepthTest();
                RenderSystem.depthMask(true);
                return flag;
            } else {
                return true;
            }
        }

        @Override
        public boolean renderSnowAndRain(WorldClient level, int ticks, float partialTick, LightTexture lightTexture, double camX, double camY, double camZ) {
            if (CUSTOM_WEATHER != null && !M.isEmpty(CUSTOM_WEATHER)) {
                boolean flag = false;
                Object[] objects = new Object[]{level, ticks, partialTick, lightTexture, camX, camY, camZ};
                M.turnOnLightLayer(lightTexture);

                for (Predicate<Object[]> predicate : CUSTOM_WEATHER) {
                    RenderSystem.depthMask(M.useShaderTransparency());
                    RenderSystem.enableDepthTest();
                    RenderSystem.disableCull();
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    flag |= predicate.test(objects);
                }

                RenderSystem.defaultBlendFunc();
                RenderSystem.disableBlend();
                RenderSystem.enableCull();
                RenderSystem.enableDepthTest();
                RenderSystem.depthMask(M.useShaderTransparency());
                M.turnOffLightLayer(lightTexture);
                return flag;
            } else {
                return true;
            }
        }

        @Override
        public boolean tickRain(WorldClient level, int ticks, Camera camera) {
            if (CUSTOM_EFFECTS != null && !M.isEmpty(CUSTOM_EFFECTS)) {
                boolean flag = false;
                Object[] objects = new Object[]{level, ticks, camera};

                for (Predicate<Object[]> predicate : CUSTOM_EFFECTS) {
                    flag |= predicate.test(objects);
                }

                return flag;
            } else {
                return true;
            }
        }

        @Override
        public void adjustLightmapColors(
            WorldClient level, float partialTick, float skyDarken, float blockLightRedFlicker, float skyLight, int pixelX, int pixelY, Vector3f colors
        ) {
            if (CUSTOM_LIGHTS != null && !M.isEmpty(CUSTOM_LIGHTS)) {
                Object[] objects = new Object[]{level, partialTick, skyDarken, blockLightRedFlicker, skyLight, pixelX, pixelY, colors};

                for (Consumer<Object[]> consumer : CUSTOM_LIGHTS) {
                    consumer.accept(objects);
                }
            }
        }
    }
}
