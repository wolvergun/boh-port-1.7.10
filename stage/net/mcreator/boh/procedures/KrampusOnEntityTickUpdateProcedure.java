package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.KrampusEntity;
import net.mcreator.boh.init.BohModItems;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.util.Mth;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.mcreator.boh.compat.mc.world.item.Items;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class KrampusOnEntityTickUpdateProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (!M.getBoolean(M.getPersistentData(entity), "lines_michael") && !M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 8.0, 8.0, 8.0), e -> true))) {
                M.putBoolean(M.getPersistentData(entity), "lines_michael", true);
            }
            if (!M.getBoolean(M.getPersistentData(entity), "throlgular") && M.getBoolean(M.getPersistentData(entity), "lines_michael")) {
                if (entity instanceof KrampusEntity) {
                    ((KrampusEntity) entity).setAnimation("scare");
                }
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.goat.screaming.prepare_ram")), SoundSource.HOSTILE, 2.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.goat.screaming.prepare_ram")), SoundSource.HOSTILE, 2.0F, 1.0F, false);
                    }
                }
                M.putBoolean(M.getPersistentData(entity), "throlgular", true);
            }
            if (M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 30.0, 30.0, 30.0), e -> true))) {
                M.putBoolean(M.getPersistentData(entity), "lines_michael", false);
                M.putBoolean(M.getPersistentData(entity), "throlgular", false);
            }
            if (!M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 20.0, 20.0, 20.0), e -> true)) && M.getBoolean(M.getPersistentData(entity), "lines_michael")) {
                if (Math.random() < 0.1) {
                    if (world instanceof WorldServer _level) {
                        EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(Items.COAL));
                        M.setPickUpDelay(entityToSpawn, 10);
                        M.addFreshEntity(_level, entityToSpawn);
                    }
                } else if (Math.random() < 0.1) {
                    if (world instanceof WorldServer _level) {
                        EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(Items.CHARCOAL));
                        M.setPickUpDelay(entityToSpawn, 10);
                        M.addFreshEntity(_level, entityToSpawn);
                    }
                } else if (Math.random() < 0.1 && world instanceof WorldServer _level) {
                    EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(Items.BONE));
                    M.setPickUpDelay(entityToSpawn, 10);
                    M.addFreshEntity(_level, entityToSpawn);
                }
                M.putDouble(M.getPersistentData(entity), "run_krampus", M.getDouble(M.getPersistentData(entity), "run_krampus") + 1.0);
            }
            if (M.getDouble(M.getPersistentData(entity), "run_krampus") > 600.0) {
                M.makeStuckInBlock(entity, M.defaultBlockState(Blocks.AIR), new Vec3(0.25, 0.05, 0.25));
                if (entity instanceof KrampusEntity) {
                    ((KrampusEntity) entity).setAnimation("leave");
                }
                BohMod.queueServerWork(92, () -> M.putBoolean(M.getPersistentData(entity), "drop_item", true));
            }
            if (M.getBoolean(M.getPersistentData(entity), "drop_item")) {
                M.putBoolean(M.getPersistentData(entity), "drop_item", false);
                if (!M.isClientSide(M.level(entity))) {
                    M.discard(entity);
                }
                if (Math.random() < 0.7) {
                    for (int index0 = 0; index0 < Mth.nextInt(RandomSource.create(), 1, 4); index0++) {
                        if (world instanceof WorldServer _level) {
                            EntityItem entityToSpawn = M.new_EntityItem(_level, x, y + 10.0, z, M.new_ItemStack(Items.DIAMOND));
                            M.setPickUpDelay(entityToSpawn, 10);
                            M.addFreshEntity(_level, entityToSpawn);
                        }
                    }
                } else if (Math.random() < 0.2) {
                    if (world instanceof WorldServer _level) {
                        EntityItem entityToSpawn = M.new_EntityItem(_level, x, y + 10.0, z, M.new_ItemStack(BohModItems.THE_SLASHER.get()));
                        M.setPickUpDelay(entityToSpawn, 10);
                        M.addFreshEntity(_level, entityToSpawn);
                    }
                } else if (Math.random() < 0.2) {
                    if (world instanceof WorldServer _level) {
                        EntityItem entityToSpawn = M.new_EntityItem(_level, x, y + 10.0, z, M.new_ItemStack(BohModItems.KILLER_KNIFE.get()));
                        M.setPickUpDelay(entityToSpawn, 10);
                        M.addFreshEntity(_level, entityToSpawn);
                    }
                } else if (Math.random() < 0.2) {
                    if (world instanceof WorldServer _level) {
                        EntityItem entityToSpawn = M.new_EntityItem(_level, x, y + 10.0, z, M.new_ItemStack(BohModItems.CRUCIFIX.get()));
                        M.setPickUpDelay(entityToSpawn, 10);
                        M.addFreshEntity(_level, entityToSpawn);
                    }
                } else if (Math.random() < 0.2) {
                    if (world instanceof WorldServer _level) {
                        EntityItem entityToSpawn = M.new_EntityItem(_level, x, y + 10.0, z, M.new_ItemStack(BohModItems.SCHIZOSLEDGE.get()));
                        M.setPickUpDelay(entityToSpawn, 10);
                        M.addFreshEntity(_level, entityToSpawn);
                    }
                } else if (Math.random() < 0.2) {
                    if (world instanceof WorldServer _level) {
                        EntityItem entityToSpawn = M.new_EntityItem(_level, x, y + 10.0, z, M.new_ItemStack(BohModItems.MASSACRE_AXE.get()));
                        M.setPickUpDelay(entityToSpawn, 10);
                        M.addFreshEntity(_level, entityToSpawn);
                    }
                } else {
                    for (int index1 = 0; index1 < Mth.nextInt(RandomSource.create(), 1, 16); index1++) {
                        if (world instanceof WorldServer _level) {
                            EntityItem entityToSpawn = M.new_EntityItem(_level, x, y + 10.0, z, M.new_ItemStack(BohModItems.SPINEL.get()));
                            M.setPickUpDelay(entityToSpawn, 10);
                            M.addFreshEntity(_level, entityToSpawn);
                        }
                    }
                }
                Entity _ent = entity;
                if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/summon firework_rocket ~ ~10 ~ {Life:0,FireworksItem:{id:firework_rocket,tag:{Fireworks:{Explosions:[{Type:2,Flicker:1b,Colors:[I;16383998]}]}},Count:1}}");
                }
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.goat.screaming.ram_impact")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.goat.screaming.ram_impact")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }
            }
        }
    }
}
