package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.init.BohModMobEffects;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.potion.Potion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;

public class TheThingSpawnProcedure {

    @SubscribeEvent
    public void onEntitySpawned(EntityJoinWorldEvent event) {
        execute(event, M.getLevel(event), M.getX(M.getEntity(event)), M.getY(M.getEntity(event)), M.getZ(M.getEntity(event)), M.getEntity(event));
    }

    public static void execute(World world, double x, double y, double z, Entity entity) {
        execute(null, world, x, y, z, entity);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (M.coldEnoughToSnow(((BiomeGenBase) M.value(M.getBiome(world, BlockPos.containing(x, y, z)))), BlockPos.containing(x, y, z)) && (entity instanceof EntityVillager || entity instanceof EntityWolf) && Math.random() < 0.001 && entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect((Potion) BohModMobEffects.FROM_OUT_OF_THIS_EARTH.get(), Integer.MAX_VALUE, 0, false, false));
            }
        }
    }
}
