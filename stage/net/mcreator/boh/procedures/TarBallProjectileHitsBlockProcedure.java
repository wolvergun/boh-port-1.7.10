package net.mcreator.boh.procedures;

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

public class TarBallProjectileHitsBlockProcedure {

    public static void execute(World world, double x, double y, double z, Entity immediatesourceentity) {
        if (immediatesourceentity != null) {
            if (world instanceof World _level) {
                if (!M.isClientSide(_level)) {
                    M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.slime_block.break")), SoundSource.BLOCKS, 1.0F, 1.0F);
                } else {
                    M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.slime_block.break")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                }
            }
            Entity _ent = immediatesourceentity;
            if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/particle squid_ink ~ ~ ~ 0.2 0.2 0.2 .1 50");
            }
            Entity _ent_r59 = immediatesourceentity;
            if (!M.isClientSide(M.level(_ent_r59)) && M.getServer(_ent_r59) != null) {
                M.performPrefixedCommand(M.getCommands(M.getServer(_ent_r59)), new CommandSourceStack(CommandSource.NULL, M.position(_ent_r59), M.getRotationVector(_ent_r59), M.level(_ent_r59) instanceof WorldServer ? (WorldServer) M.level(_ent_r59) : null, 4, M.getString(M.getName(_ent_r59)), M.getDisplayName(_ent_r59), M.getServer(M.level(_ent_r59)), _ent_r59), "/summon area_effect_cloud ~ ~ ~ {Particle:squid_ink,Potion:2,Radius:3,Duration:200,Effects:[{Id:2,Duration:20,Amplifier:1,Ambient:1b,ShowParticles:1b,ShowIcon:1}]}");
            }
        }
    }
}
