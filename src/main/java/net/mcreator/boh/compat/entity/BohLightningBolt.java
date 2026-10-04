package net.mcreator.boh.compat.entity;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.world.World;

public class BohLightningBolt extends EntityLightningBolt {
    private boolean visualOnly;
    private int life = 2;
    private int flashes = this.rand.nextInt(3) + 1;

    public BohLightningBolt(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public void setVisualOnly(boolean b) {
        this.visualOnly = b;
    }

    public boolean isVisualOnly() {
        return this.visualOnly;
    }

    public void onUpdate() {
        if (!this.visualOnly) {
            super.onUpdate();
        } else {
            if (this.life == 2) {
                this.worldObj
                    .playSoundEffect(
                        this.posX,
                        this.posY,
                        this.posZ,
                        "ambient.weather.thunder",
                        10000.0F,
                        0.8F + this.rand.nextFloat() * 0.2F
                    );
                this.worldObj
                    .playSoundEffect(
                        this.posX, this.posY, this.posZ, "random.explode", 2.0F, 0.5F + this.rand.nextFloat() * 0.2F
                    );
            }

            this.life--;
            if (this.life < 0) {
                if (this.flashes == 0) {
                    this.setDead();
                } else if (this.life < -this.rand.nextInt(10)) {
                    this.flashes--;
                    this.life = 1;
                    this.boltVertex = this.rand.nextLong();
                }
            }

            if (this.life >= 0 && this.worldObj.isRemote) {
                this.worldObj.lastLightningBolt = 2;
            }
        }
    }
}
