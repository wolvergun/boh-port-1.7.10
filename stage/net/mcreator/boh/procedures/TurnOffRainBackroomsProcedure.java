package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.mcreator.boh.compat.forge.event.entity.EntityTravelToDimensionEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;

public class TurnOffRainBackroomsProcedure {

    @SubscribeEvent
    public void onEntityTravelToDimension(EntityTravelToDimensionEvent event) {
        execute(event, M.level(M.getEntity(event)), M.getDimension(event));
    }

    public static void execute(World world, ResourceKey<World> dimension) {
        execute(null, world, dimension);
    }

    private static void execute(@Nullable Event event, World world, ResourceKey<World> dimension) {
        if (dimension != null) {
            if (dimension == net.mcreator.boh.compat.world.Dimensions.dimensionKey( new ResourceLocation("boh:level_0"))) {
                M.setRaining(M.getLevelData(world), false);
            }
        }
    }
}
