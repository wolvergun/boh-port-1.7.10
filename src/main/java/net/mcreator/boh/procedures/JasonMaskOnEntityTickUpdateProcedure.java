package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class JasonMaskOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null && M.isRaining(M.getLevelData(world)) && M.canSeeSkyFromBelowWater(world, BlockPos.containing(x, y, z)) && Math.random() < 0.001) {
            if (!M.isClientSide(M.level(entity))) {
                M.discard(entity);
            }

            if (world instanceof World) {
                if (!M.isClientSide(world)) {
                    M.playSound(
                        world,
                        null,
                        BlockPos.containing(x, y, z),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:jason_ressurect")),
                        SoundSource.AMBIENT,
                        2.0F,
                        1.0F
                    );
                } else {
                    M.playLocalSound(
                        world,
                        x,
                        y,
                        z,
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:jason_ressurect")),
                        SoundSource.AMBIENT,
                        2.0F,
                        1.0F,
                        false
                    );
                }
            }

            if (world instanceof WorldServer _level) {
                Entity entityToSpawn = M.spawn(BohModEntities.JASON_VOORHEES.get(), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                if (entityToSpawn != null) {
                    M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                }
            }

            if (world instanceof WorldServer _levelx) {
                EntityLightningBolt entityToSpawn = M.create(EntityType.LIGHTNING_BOLT, _levelx);
                M.moveTo(entityToSpawn, Vec3.atBottomCenterOf(BlockPos.containing(x, y, z)));
                M.setVisualOnly(entityToSpawn, true);
                M.addFreshEntity(_levelx, entityToSpawn);
            }
        }
    }
}
