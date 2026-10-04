package net.mcreator.boh.world.dimension;

import net.mcreator.boh.procedures.Level0PlayerEntersDimensionProcedure;
import net.mcreator.boh.compat.mc.client.renderer.DimensionSpecialEffects;
import net.mcreator.boh.compat.mc.client.renderer.SkyType;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.mcreator.boh.compat.forge.client.event.RegisterDimensionSpecialEffectsEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;

public class Level0Dimension {

    @SubscribeEvent
    public void onPlayerChangedDimensionEvent(PlayerChangedDimensionEvent event) {
        Entity entity = M.getEntity(event);
        World world = M.level(entity);
        double x = M.getX(entity);
        double y = M.getY(entity);
        double z = M.getZ(entity);
        if (M.getTo(event) == net.mcreator.boh.compat.world.Dimensions.dimensionKey( new ResourceLocation("boh:level_0"))) {
            Level0PlayerEntersDimensionProcedure.execute(world, x, z, entity);
        }
    }

    public static class DimensionSpecialEffectsHandler {

        @SubscribeEvent
        @SideOnly(Side.CLIENT)
        public void registerDimensionSpecialEffects(RegisterDimensionSpecialEffectsEvent event) {
            DimensionSpecialEffects customEffect = new DimensionSpecialEffects(Float.NaN, true, SkyType.NONE, false, false) {

                public Vec3 getBrightnessDependentFogColor(Vec3 color, float sunHeight) {
                    return new Vec3(0.9647058824, 0.9607843137, 0.5960784314);
                }

                public boolean isFoggyAt(int x, int y) {
                    return true;
                }
            };
            event.register(new ResourceLocation("boh:level_0"), customEffect);
        }
    }
}
