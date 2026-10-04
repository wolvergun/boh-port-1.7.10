package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.item.BigDaddyDrillItem;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class BigDaddyDrillRightclickedProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity != null) {
            if (M.onGround(entity)) {
                ItemStack _ist = itemstack;
                if (M.hurt(_ist, 2, RandomSource.create(), null)) {
                    M.shrink(_ist, 1);
                    M.setDamageValue(_ist, 0);
                }
                M.putBoolean(M.getOrCreateTag(itemstack), "dash", true);
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:drill_dash")), SoundSource.PLAYERS, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:drill_dash")), SoundSource.PLAYERS, 1.0F, 1.0F, false);
                    }
                }
                BohMod.queueServerWork(20, () -> M.putBoolean(M.getOrCreateTag(itemstack), "dash", false));
                if (M.getItem(itemstack) instanceof BigDaddyDrillItem) {
                    M.putString(M.getOrCreateTag(itemstack), "geckoAnim", "use");
                }
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.DAMAGE_BOOST, 20, 2, false, false));
                }
                M.setDeltaMovement(entity, new Vec3(M.getDeltaMovement(entity).x() + M.getLookAngle(entity).x * 6.0, M.getDeltaMovement(entity).y(), M.getDeltaMovement(entity).z() + M.getLookAngle(entity).z * 6.0));
                if (entity instanceof EntityPlayer _player) {
                    M.addCooldown(M.getCooldowns(_player), M.getItem(itemstack), 60);
                }
            }
        }
    }
}
