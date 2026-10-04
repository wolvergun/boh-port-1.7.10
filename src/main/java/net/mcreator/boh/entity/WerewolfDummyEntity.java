package net.mcreator.boh.entity;

import java.util.Objects;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.entity.BohMonster;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.MobType;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Attributes;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Builder;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.FloatGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.MeleeAttackGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.PanicGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.RandomLookAroundGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.RandomStrollGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.target.HurtByTargetGoal;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.procedures.WerewolfDummyOnEntityTickUpdateProcedure;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class WerewolfDummyEntity extends BohMonster {
    public WerewolfDummyEntity(World world) {
        this(BohModEntities.WEREWOLF_DUMMY.get(), world);
    }

    public WerewolfDummyEntity(EntityType<WerewolfDummyEntity> type, World world) {
        super(type, world);
        M.setMaxUpStep(this, 0.6F);
        M.set_xpReward(this, 0);
        M.setNoAi(this, false);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false) {
            {
                Objects.requireNonNull(WerewolfDummyEntity.this);
            }

            @Override
            protected double getAttackReachSqr(EntityLivingBase entity) {
                return M.getBbWidth(this.mob) * M.getBbWidth(this.mob) + M.getBbWidth(entity);
            }
        });
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(3, new PanicGoal(this, 1.2));
        this.targetSelector.addGoal(4, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new FloatGoal(this));
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    public double getMyRidingOffset() {
        return -0.35;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
    }

    @Override
    public SoundEvent getDeathSoundEvent() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
    }

    @Override
    public void baseTick() {
        super.baseTick();
        WerewolfDummyOnEntityTickUpdateProcedure.execute(M.level(this), M.getX(this), M.getY(this), M.getZ(this), this);
    }

    public static void init() {
    }

    public static Builder createAttributes() {
        Builder builder = M.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
        builder = builder.add(Attributes.MAX_HEALTH, 20.0);
        builder = builder.add(Attributes.ARMOR, 0.0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 3.0);
        return builder.add(Attributes.FOLLOW_RANGE, 16.0);
    }
}
