package net.mcreator.boh.compat.mc.world.entity.ai.goal.target;

import java.util.function.Predicate;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.EnumDifficulty;

/** 1.20.1 TargetingConditions / Mob.canAttack rules on 1.7.10 entities. */
public final class Targeting {

    private Targeting() {}

    /** Mob.canAttack + LivingEntity.canBeSeenAsEnemy: alive, not invulnerable or creative, no players on peaceful. */
    public static boolean canAttack(EntityLiving mob, EntityLivingBase target) {
        if (target == null || !target.isEntityAlive() || target.isEntityInvulnerable()) return false;
        if (target instanceof EntityPlayer) {
            if (((EntityPlayer) target).capabilities.disableDamage) return false;
            if (mob.worldObj.difficultySetting == EnumDifficulty.PEACEFUL) return false;
        }
        return !(target instanceof EntityGhast);
    }

    /** LivingEntity.getVisibilityPercent: sneaking, invisibility (less with armour) and a matching mob head. */
    public static double visibility(EntityLivingBase target, EntityLiving looker) {
        double v = 1.0;
        if (target.isSneaking()) v *= 0.8;
        if (target.isInvisible()) {
            int worn = 0;
            for (int i = 1; i <= 4; i++) if (target.getEquipmentInSlot(i) != null) worn++;
            float cover = Math.max(worn / 4.0F, 0.1F);
            v *= 0.7 * cover;
        }
        ItemStack head = target.getEquipmentInSlot(4);
        if (head != null && head.getItem() == Items.skull && looker != null) {
            int t = head.getItemDamage();
            boolean match = looker instanceof EntitySkeleton && t == (((EntitySkeleton) looker).getSkeletonType() == 1 ? 1 : 0)
                || looker instanceof EntityZombie && t == 2 || looker instanceof EntityCreeper && t == 4;
            if (match) v *= 0.5;
        }
        return v;
    }

    /** TargetingConditions.forCombat(). */
    public static final class Conditions {

        private double range = -1;
        private boolean checkLineOfSight = true;
        private boolean testInvisible = true;
        private Predicate<EntityLivingBase> selector;

        public Conditions range(double r) {
            range = r;
            return this;
        }

        public Conditions selector(Predicate<EntityLivingBase> s) {
            selector = s;
            return this;
        }

        public Conditions ignoreLineOfSight() {
            checkLineOfSight = false;
            return this;
        }

        public Conditions ignoreInvisibilityTesting() {
            testInvisible = false;
            return this;
        }

        public boolean test(EntityLiving mob, EntityLivingBase target) {
            if (mob == target || target == null || !target.isEntityAlive()) return false;
            if (selector != null && !selector.test(target)) return false;
            if (!canAttack(mob, target) || mob.isOnSameTeam(target)) return false;
            if (range > 0) {
                double r = Math.max(range * (testInvisible ? visibility(target, mob) : 1.0), 2.0);
                if (mob.getDistanceSqToEntity(target) > r * r) return false;
            }
            return !checkLineOfSight || mob.getEntitySenses().canSee(target);
        }
    }
}
