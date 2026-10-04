package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.WFPistolProjectileEntity;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.item.WfPistolItem;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class WfPistolRightclickedProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         ItemStack _ist = itemstack;
         if (_ist.hurt(1, RandomSource.create(), null)) {
            _ist.shrink(1);
            _ist.setDamageValue(0);
         }

         if (entity instanceof Player _player) {
            _player.getCooldowns().addCooldown(itemstack.getItem(), 20);
         }

         Entity _shootFrom = entity;
         Level projectileLevel = _shootFrom.level();
         if (!projectileLevel.isClientSide()) {
            Projectile _entityToSpawn = (new Object() {
                  public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
                     AbstractArrow entityToSpawn = new WFPistolProjectileEntity(
                        (EntityType<? extends WFPistolProjectileEntity>)BohModEntities.WF_PISTOL_PROJECTILE.get(), level
                     );
                     entityToSpawn.setOwner(shooter);
                     entityToSpawn.setBaseDamage(damage);
                     entityToSpawn.setKnockback(knockback);
                     entityToSpawn.setSilent(true);
                     return entityToSpawn;
                  }
               })
               .getArrow(projectileLevel, entity, 2.5F, 0);
            _entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
            _entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 3.0F, 0.0F);
            projectileLevel.addFreshEntity(_entityToSpawn);
         }

         if (itemstack.getItem() instanceof WfPistolItem) {
            itemstack.getOrCreateTag().putString("geckoAnim", "shoot");
         }

         if (!world.isClientSide() && world instanceof Level _level) {
            if (!_level.isClientSide()) {
               _level.playSound(
                  null,
                  BlockPos.containing(x, y, z),
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:wf_pistol")),
                  SoundSource.AMBIENT,
                  1.0F,
                  1.0F
               );
            } else {
               _level.playLocalSound(
                  x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:wf_pistol")), SoundSource.AMBIENT, 1.0F, 1.0F, false
               );
            }
         }

         Entity _ent = entity;
         _ent.setYRot(entity.getYRot());
         _ent.setXRot(entity.getXRot() - 5.0F);
         _ent.setYBodyRot(_ent.getYRot());
         _ent.setYHeadRot(_ent.getYRot());
         _ent.yRotO = _ent.getYRot();
         _ent.xRotO = _ent.getXRot();
         if (_ent instanceof LivingEntity _entity) {
            _entity.yBodyRotO = _entity.getYRot();
            _entity.yHeadRotO = _entity.getYRot();
         }
      }
   }
}
