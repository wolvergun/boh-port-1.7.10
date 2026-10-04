package net.mcreator.boh.world.dimension;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Objects;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.mcreator.boh.compat.mc.client.renderer.DimensionSpecialEffects;
import net.mcreator.boh.compat.mc.client.renderer.SkyType;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.world.Dimensions;
import net.mcreator.boh.procedures.BoilerRoomDimensionPlayerEntersDimensionProcedure;
import net.mcreator.boh.procedures.BoilerRoomDimensionPlayerLeavesDimensionProcedure;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class BoilerRoomDimensionDimension {
    @SubscribeEvent
    public void onPlayerChangedDimensionEvent(PlayerChangedDimensionEvent event) {
        Entity entity = M.getEntity(event);
        World world = M.level(entity);
        double x = M.getX(entity);
        double y = M.getY(entity);
        double z = M.getZ(entity);
        if (M.getFrom(event) == Dimensions.dimensionKey(new ResourceLocation("boh:boiler_room_dimension"))) {
            BoilerRoomDimensionPlayerLeavesDimensionProcedure.execute(world, entity);
        }

        if (M.getTo(event) == Dimensions.dimensionKey(new ResourceLocation("boh:boiler_room_dimension"))) {
            BoilerRoomDimensionPlayerEntersDimensionProcedure.execute(world, x, y, z, entity);
        }
    }

    public static class BoilerRoomDimensionSpecialEffectsHandler {
        @SubscribeEvent
        @SideOnly(Side.CLIENT)
        public void registerDimensionSpecialEffects(RegisterDimensionSpecialEffectsEvent event) {
            DimensionSpecialEffects customEffect = new DimensionSpecialEffects(Float.NaN, true, SkyType.NONE, false, false) {
                {
                    Objects.requireNonNull(BoilerRoomDimensionSpecialEffectsHandler.this);
                }

                @Override
                public Vec3 getBrightnessDependentFogColor(Vec3 color, float sunHeight) {
                    return color;
                }

                @Override
                public boolean isFoggyAt(int x, int y) {
                    return true;
                }
            };
            event.register(new ResourceLocation("boh:boiler_room_dimension"), customEffect);
        }
    }
}
