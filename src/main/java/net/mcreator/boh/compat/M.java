package net.mcreator.boh.compat;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Attributes;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Builder;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public final class M extends MEvent {
    private M() {
    }

    public static Builder createMobAttributes() {
        return new Builder()
            .add(Attributes.MAX_HEALTH, 20.0)
            .add(Attributes.MOVEMENT_SPEED, 0.7)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.0)
            .add(Attributes.ARMOR, 0.0)
            .add(Attributes.FOLLOW_RANGE, 16.0);
    }

    public static Builder createMonsterAttributes() {
        return createMobAttributes()
            .add(Attributes.FOLLOW_RANGE, 35.0)
            .add(Attributes.MOVEMENT_SPEED, 0.23)
            .add(Attributes.ATTACK_DAMAGE, 2.0)
            .add(Attributes.ARMOR, 0.0);
    }

    public static Builder createLivingAttributes() {
        return new Builder()
            .add(Attributes.MAX_HEALTH, 20.0)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.0)
            .add(Attributes.MOVEMENT_SPEED, 0.7)
            .add(Attributes.ARMOR, 0.0);
    }

    public static boolean checkMobSpawnRules(EntityType<?> type, World world, MobSpawnType reason, BlockPos pos, RandomSource random) {
        Block below = world.getBlock(pos.getX(), pos.getY() - 1, pos.getZ());
        return reason == MobSpawnType.SPAWNER
            || below.canCreatureSpawn(EnumCreatureType.monster, world, pos.getX(), pos.getY() - 1, pos.getZ())
            || below.isOpaqueCube();
    }

    public static boolean checkMonsterSpawnRules(EntityType<?> type, World world, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return world.difficultySetting != EnumDifficulty.PEACEFUL
            && isDarkEnoughToSpawn(world, pos, random)
            && checkMobSpawnRules(type, world, reason, pos, random);
    }

    public static boolean checkAnimalSpawnRules(EntityType<?> type, World world, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return world.getBlock(pos.getX(), pos.getY() - 1, pos.getZ()) == Blocks.grass
            && world.getFullBlockLightValue(pos.getX(), pos.getY(), pos.getZ()) > 8;
    }

    public static boolean isDarkEnoughToSpawn(World world, BlockPos pos, RandomSource random) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (world.getSavedLightValue(EnumSkyBlock.Sky, x, y, z) > random.nextInt(32)) {
            return false;
        } else {
            int light = world.getBlockLightValue(x, y, z);
            if (world.isThundering()) {
                int saved = world.skylightSubtracted;
                world.skylightSubtracted = 10;
                light = world.getBlockLightValue(x, y, z);
                world.skylightSubtracted = saved;
            }

            return light <= random.nextInt(8);
        }
    }

    public static void dropResources(BlockState state, World world, BlockPos pos, Object blockEntity) {
        if (!world.isRemote) {
            state.getBlock().dropBlockAsItem(world, pos.getX(), pos.getY(), pos.getZ(), state.meta(), 0);
        }
    }

    public static void dropResources(BlockState state, World world, BlockPos pos, Object blockEntity, Entity breaker, ItemStack tool) {
        dropResources(state, world, pos, blockEntity);
    }

    public static boolean useShaderTransparency() {
        return false;
    }

    public static boolean hasAltDown() {
        return MClientImpl.altDown();
    }
}
