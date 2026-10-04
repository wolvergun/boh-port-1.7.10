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

    @Override
    public void onUpdate() {
        if (!visualOnly) {
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
}
