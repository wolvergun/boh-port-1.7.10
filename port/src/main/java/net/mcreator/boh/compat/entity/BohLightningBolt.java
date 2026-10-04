package net.mcreator.boh.compat.entity;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.world.World;

/** Lightning that can be visual-only (1.20 LightningBolt.setVisualOnly): no fire, no damage. */
public class BohLightningBolt extends EntityLightningBolt {

    private boolean visualOnly;
    private int life = 2;
    private int flashes;

    public BohLightningBolt(World world, double x, double y, double z) {
        super(world, x, y, z);
        flashes = rand.nextInt(3) + 1;
    }

    public void setVisualOnly(boolean b) {
        visualOnly = b;
    }

    public boolean isVisualOnly() {
        return visualOnly;
    }

    private boolean firstTick = true;

    @Override
    public void onUpdate() {
        if (!visualOnly) {
            // created through EntityType (at 0,0,0, then moved), so vanilla's constructor fire landed nowhere: place it
            // where the bolt actually struck
            if (firstTick && !worldObj.isRemote) igniteAround();
            firstTick = false;
            super.onUpdate();
            return;
        }
        if (life == 2) {
            worldObj.playSoundEffect(posX, posY, posZ, "ambient.weather.thunder", 10000.0F, 0.8F + rand.nextFloat() * 0.2F);
            worldObj.playSoundEffect(posX, posY, posZ, "random.explode", 2.0F, 0.5F + rand.nextFloat() * 0.2F);
        }
        --life;
        if (life < 0) {
            if (flashes == 0) setDead();
            else if (life < -rand.nextInt(10)) {
                --flashes;
                life = 1;
                boltVertex = rand.nextLong();
            }
        }
        if (life >= 0 && worldObj.isRemote) worldObj.lastLightningBolt = 2;
    }

    private void igniteAround() {
        if (!worldObj.getGameRules().getGameRuleBooleanValue("doFireTick")
            || worldObj.difficultySetting != net.minecraft.world.EnumDifficulty.NORMAL && worldObj.difficultySetting != net.minecraft.world.EnumDifficulty.HARD)
            return;
        int x = net.minecraft.util.MathHelper.floor_double(posX), y = net.minecraft.util.MathHelper.floor_double(posY),
            z = net.minecraft.util.MathHelper.floor_double(posZ);
        if (!worldObj.doChunksNearChunkExist(x, y, z, 10)) return;
        for (int i = 0; i < 5; i++) {
            int fx = i == 0 ? x : x + rand.nextInt(3) - 1, fy = i == 0 ? y : y + rand.nextInt(3) - 1, fz = i == 0 ? z : z + rand.nextInt(3) - 1;
            if (worldObj.getBlock(fx, fy, fz).getMaterial() == net.minecraft.block.material.Material.air
                && net.minecraft.init.Blocks.fire.canPlaceBlockAt(worldObj, fx, fy, fz))
                worldObj.setBlock(fx, fy, fz, net.minecraft.init.Blocks.fire);
        }
    }
}
