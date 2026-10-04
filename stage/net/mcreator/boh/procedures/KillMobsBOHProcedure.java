package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.init.BohModItems;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.tags.TagKey;
import net.mcreator.boh.compat.mc.util.Mth;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.mcreator.boh.compat.mc.world.item.enchantment.EnchantmentHelper;
import net.mcreator.boh.compat.mc.world.item.enchantment.Enchantments;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;

public class KillMobsBOHProcedure {

    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event) {
        if (event != null && M.getEntity(event) != null) {
            execute(event, M.level(M.getEntity(event)), M.getX(M.getEntity(event)), M.getY(M.getEntity(event)), M.getZ(M.getEntity(event)), M.getEntity(event), M.getEntity(M.getSource(event)));
        }
    }

    public static void execute(World world, double x, double y, double z, Entity entity, Entity sourceentity) {
        execute(null, world, x, y, z, entity, sourceentity);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z, Entity entity, Entity sourceentity) {
        if (entity != null && sourceentity != null) {
            if (sourceentity instanceof EntityPlayer) {
                if (Math.random() < 0.7) {
                    if (M.is(M.getType(entity), TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:boh_exotic")))) {
                        for (int index0 = 0; index0 < (int) Mth.nextDouble(RandomSource.create(), 2.0, 6.0); index0++) {
                            if (world instanceof WorldServer _level) {
                                EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(BohModItems.EXOTIC_SOUL.get()));
                                M.setPickUpDelay(entityToSpawn, 10);
                                M.addFreshEntity(_level, entityToSpawn);
                            }
                        }
                    }
                    if (M.is(M.getType(entity), TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:boh_killers")))) {
                        for (int index1 = 0; index1 < (int) Mth.nextDouble(RandomSource.create(), 2.0, 6.0); index1++) {
                            if (world instanceof WorldServer _level) {
                                EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(BohModItems.KILLERS_SOUL.get()));
                                M.setPickUpDelay(entityToSpawn, 10);
                                M.addFreshEntity(_level, entityToSpawn);
                            }
                        }
                    }
                    if (M.is(M.getType(entity), TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:boh_monstrous")))) {
                        for (int index2 = 0; index2 < (int) Mth.nextDouble(RandomSource.create(), 2.0, 6.0); index2++) {
                            if (world instanceof WorldServer _level) {
                                EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(BohModItems.MONSTROUS_SOUL.get()));
                                M.setPickUpDelay(entityToSpawn, 10);
                                M.addFreshEntity(_level, entityToSpawn);
                            }
                        }
                    }
                    if (M.is(M.getType(entity), TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:boh_demons")))) {
                        for (int index3 = 0; index3 < (int) Mth.nextDouble(RandomSource.create(), 2.0, 6.0); index3++) {
                            if (world instanceof WorldServer _level) {
                                EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(BohModItems.DEMONIC_SOUL.get()));
                                M.setPickUpDelay(entityToSpawn, 10);
                                M.addFreshEntity(_level, entityToSpawn);
                            }
                        }
                    }
                } else if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MOB_LOOTING, sourceentity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY) != 0 && Math.random() < 0.85) {
                    if (M.is(M.getType(entity), TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:boh_exotic")))) {
                        for (int index4 = 0; index4 < (int) Mth.nextDouble(RandomSource.create(), 3.0, 6.0); index4++) {
                            if (world instanceof WorldServer _level) {
                                EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(BohModItems.EXOTIC_SOUL.get()));
                                M.setPickUpDelay(entityToSpawn, 10);
                                M.addFreshEntity(_level, entityToSpawn);
                            }
                        }
                    }
                    if (M.is(M.getType(entity), TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:boh_killers")))) {
                        for (int index5 = 0; index5 < (int) Mth.nextDouble(RandomSource.create(), 3.0, 7.0); index5++) {
                            if (world instanceof WorldServer _level) {
                                EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(BohModItems.KILLERS_SOUL.get()));
                                M.setPickUpDelay(entityToSpawn, 10);
                                M.addFreshEntity(_level, entityToSpawn);
                            }
                        }
                    }
                    if (M.is(M.getType(entity), TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:boh_montrous")))) {
                        for (int index6 = 0; index6 < (int) Mth.nextDouble(RandomSource.create(), 3.0, 7.0); index6++) {
                            if (world instanceof WorldServer _level) {
                                EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(BohModItems.MONSTROUS_SOUL.get()));
                                M.setPickUpDelay(entityToSpawn, 10);
                                M.addFreshEntity(_level, entityToSpawn);
                            }
                        }
                    }
                    if (M.is(M.getType(entity), TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:boh_demons")))) {
                        for (int index7 = 0; index7 < (int) Mth.nextDouble(RandomSource.create(), 3.0, 7.0); index7++) {
                            if (world instanceof WorldServer _level) {
                                EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(BohModItems.DEMONIC_SOUL.get()));
                                M.setPickUpDelay(entityToSpawn, 10);
                                M.addFreshEntity(_level, entityToSpawn);
                            }
                        }
                    }
                }
            }
        }
    }
}
