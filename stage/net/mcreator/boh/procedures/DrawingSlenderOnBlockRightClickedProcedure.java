package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.SlenderManEntity;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.init.BohModMobEffects;
import net.mcreator.boh.compat.mc.advancements.Advancement;
import net.mcreator.boh.compat.mc.advancements.AdvancementProgress;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.potion.Potion;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.minecraft.entity.EntityLivingBase;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.mc.world.item.Items;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.forge.items.ItemHandlerHelper;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class DrawingSlenderOnBlockRightClickedProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (world instanceof World _level) {
                if (!M.isClientSide(_level)) {
                    M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:page_pickup")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                } else {
                    M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:page_pickup")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                }
            }
            if (entity instanceof EntityPlayerMP _player) {
                Advancement _adv = M.getAdvancement(M.getAdvancements(M.server(_player)), new ResourceLocation("boh:grimm_start"));
                AdvancementProgress _ap = M.getOrStartProgress(M.getAdvancements(_player), _adv);
                if (!M.isDone(_ap)) {
                    for (String criteria : M.getRemainingCriteria(_ap)) {
                        M.award(M.getAdvancements(_player), _adv, criteria);
                    }
                }
            }
            M.destroyBlock(world, BlockPos.containing(x, y, z), false);
            if (entity instanceof EntityPlayer _player) {
                ItemStack _setstack = M.copy(M.new_ItemStack(Items.PAPER));
                M.setCount(_setstack, 1);
                ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
            }
            if (M.isEmpty(M.getEntitiesOfClass(world, SlenderManEntity.class, AABB.ofSize(new Vec3(x, y, z), 600.0, 600.0, 600.0), e -> true))) {
                if (world instanceof WorldServer _level) {
                    Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.SLENDER_MAN.get()), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                    if (entityToSpawn != null) {
                        M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                    }
                }
                BohMod.queueServerWork(10, () -> {
                    Entity _entx = entity;
                    if (!M.isClientSide(M.level(_entx)) && M.getServer(_entx) != null) {
                        M.performPrefixedCommand(M.getCommands(M.getServer(_entx)), new CommandSourceStack(CommandSource.NULL, M.position(_entx), M.getRotationVector(_entx), M.level(_entx) instanceof WorldServer ? (WorldServer) M.level(_entx) : null, 4, M.getString(M.getName(_entx)), M.getDisplayName(_entx), M.getServer(M.level(_entx)), _entx), "/execute at @p rotated ~ 1 run spreadplayers ~ ~ 20 30 false @e[type=boh:slender_man,limit=1]");
                    }
                });
            }
            if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect((Potion) BohModMobEffects.ENGAGED.get(), 999999, (entity instanceof EntityLivingBase _livEnt && M.hasEffect(_livEnt, (Potion) BohModMobEffects.ENGAGED.get()) ? M.getAmplifier(M.getEffect(_livEnt, (Potion) BohModMobEffects.ENGAGED.get())) : 0) + 1, false, false));
            }
            if ((entity instanceof EntityLivingBase _livEnt && M.hasEffect(_livEnt, (Potion) BohModMobEffects.ENGAGED.get()) ? M.getAmplifier(M.getEffect(_livEnt, (Potion) BohModMobEffects.ENGAGED.get())) : 0) > 7) {
                if (entity instanceof EntityLivingBase _entity) {
                    M.removeEffect(_entity, (Potion) BohModMobEffects.ENGAGED.get());
                }
                Vec3 _center = new Vec3(x, y, z);
                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(300.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                    if (entityiterator instanceof SlenderManEntity) {
                        if (entity instanceof EntityPlayerMP _player) {
                            Advancement _adv = M.getAdvancement(M.getAdvancements(M.server(_player)), new ResourceLocation("boh:slender_gift"));
                            AdvancementProgress _ap = M.getOrStartProgress(M.getAdvancements(_player), _adv);
                            if (!M.isDone(_ap)) {
                                for (String criteria : M.getRemainingCriteria(_ap)) {
                                    M.award(M.getAdvancements(_player), _adv, criteria);
                                }
                            }
                        }
                        Entity _ent = entityiterator;
                        if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                            M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/particle minecraft:squid_ink ~ ~ ~ 0.5 3 0.5 0 200");
                        }
                        Entity _ent_r19 = entityiterator;
                        if (!M.isClientSide(M.level(_ent_r19)) && M.getServer(_ent_r19) != null) {
                            M.performPrefixedCommand(M.getCommands(M.getServer(_ent_r19)), new CommandSourceStack(CommandSource.NULL, M.position(_ent_r19), M.getRotationVector(_ent_r19), M.level(_ent_r19) instanceof WorldServer ? (WorldServer) M.level(_ent_r19) : null, 4, M.getString(M.getName(_ent_r19)), M.getDisplayName(_ent_r19), M.getServer(M.level(_ent_r19)), _ent_r19), "/advancement grant @a[distance=0..600] only boh:slender_gift ");
                        }
                        Entity _ent_r20 = entityiterator;
                        if (!M.isClientSide(M.level(_ent_r20)) && M.getServer(_ent_r20) != null) {
                            M.performPrefixedCommand(M.getCommands(M.getServer(_ent_r20)), new CommandSourceStack(CommandSource.NULL, M.position(_ent_r20), M.getRotationVector(_ent_r20), M.level(_ent_r20) instanceof WorldServer ? (WorldServer) M.level(_ent_r20) : null, 4, M.getString(M.getName(_ent_r20)), M.getDisplayName(_ent_r20), M.getServer(M.level(_ent_r20)), _ent_r20), "/playsound boh:slender_jumpscare hostile @a");
                        }
                        if (!M.isClientSide(M.level(entityiterator))) {
                            M.discard(entityiterator);
                        }
                    }
                }
            }
        }
    }
}
