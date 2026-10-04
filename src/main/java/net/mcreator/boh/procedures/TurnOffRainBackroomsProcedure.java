package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.event.entity.EntityTravelToDimensionEvent;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.mcreator.boh.compat.world.Dimensions;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class TurnOffRainBackroomsProcedure {
    @SubscribeEvent
    public void onEntityTravelToDimension(EntityTravelToDimensionEvent event) {
        execute(event, M.level(M.getEntity(event)), M.getDimension(event));
    }

    public static void execute(World world, ResourceKey<World> dimension) {
        execute(null, world, dimension);
    }

    private static void execute(@Nullable Event event, World world, ResourceKey<World> dimension) {
        if (dimension != null && dimension == Dimensions.dimensionKey(new ResourceLocation("boh:level_0"))) {
            M.setRaining(M.getLevelData(world), false);
        }
    }
}
