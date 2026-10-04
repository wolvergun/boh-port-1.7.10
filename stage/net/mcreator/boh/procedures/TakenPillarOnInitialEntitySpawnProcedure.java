package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.TakenPillarEntity;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class TakenPillarOnInitialEntitySpawnProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (entity instanceof TakenPillarEntity) {
                ((TakenPillarEntity) entity).setAnimation("rise");
            }
            BohMod.queueServerWork(2, () -> {
                Entity _ent = entity;
                if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "spreadplayers ~ ~ 1 4 under 4 true @e[type=boh:taken_pillar,limit=1,distance=0..3]");
                }
            });
            BohMod.queueServerWork(20, () -> M.putBoolean(M.getPersistentData(entity), "damage", true));
            BohMod.queueServerWork(120, () -> {
                if (!M.isClientSide(M.level(entity))) {
                    M.discard(entity);
                }
            });
            if (world instanceof World _level) {
                if (!M.isClientSide(_level)) {
                    M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:screampillar")), SoundSource.HOSTILE, 1.0F, 1.0F);
                } else {
                    M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:screampillar")), SoundSource.HOSTILE, 1.0F, 1.0F, false);
                }
            }
        }
    }
}
