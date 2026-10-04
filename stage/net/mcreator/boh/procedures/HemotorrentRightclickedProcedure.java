package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.entity.BloodwaveEntity;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class HemotorrentRightclickedProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity != null) {
            if (entity instanceof EntityPlayer _player) {
                M.addCooldown(M.getCooldowns(_player), M.getItem(itemstack), 100);
            }
            if (entity instanceof EntityLivingBase _entity) {
                M.swing(_entity, InteractionHand.MAIN_HAND, true);
            }
            if (world instanceof World _level) {
                if (!M.isClientSide(_level)) {
                    M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:boiled_one_trumpet")), SoundSource.PLAYERS, 1.0F, 1.0F);
                } else {
                    M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:boiled_one_trumpet")), SoundSource.PLAYERS, 1.0F, 1.0F, false);
                }
            }
            if (world instanceof World _level) {
                if (!M.isClientSide(_level)) {
                    M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:bloodwave")), SoundSource.PLAYERS, 1.0F, 1.0F);
                } else {
                    M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:bloodwave")), SoundSource.PLAYERS, 1.0F, 1.0F, false);
                }
            }
            Entity _ent = entity;
            if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "execute as @p at @s run summon boh:bloodwave ~ ~ ~");
            }
            Entity _ent_r39 = entity;
            if (!M.isClientSide(M.level(_ent_r39)) && M.getServer(_ent_r39) != null) {
                M.performPrefixedCommand(M.getCommands(M.getServer(_ent_r39)), new CommandSourceStack(CommandSource.NULL, M.position(_ent_r39), M.getRotationVector(_ent_r39), M.level(_ent_r39) instanceof WorldServer ? (WorldServer) M.level(_ent_r39) : null, 4, M.getString(M.getName(_ent_r39)), M.getDisplayName(_ent_r39), M.getServer(M.level(_ent_r39)), _ent_r39), "/tp @e[type=boh:bloodwave,limit=1,sort=nearest] @s");
            }
            Vec3 _center = new Vec3(x, y, z);
            for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(2.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                if (entityiterator instanceof BloodwaveEntity && entityiterator instanceof EntityTameable _toTame && entity instanceof EntityPlayer _owner) {
                    M.tame(_toTame, _owner);
                }
            }
        }
    }
}
