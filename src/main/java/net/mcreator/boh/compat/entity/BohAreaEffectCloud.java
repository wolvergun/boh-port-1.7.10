package net.mcreator.boh.compat.entity;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.mcreator.boh.compat.command.ParticleNames;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BohAreaEffectCloud extends Entity implements IEntityAdditionalSpawnData {
    private float radius = 3.0F;
    private int duration = 600;
    private int waitTime = 10;
    private float radiusPerTick;
    private String particle = "spell";
    private final List<PotionEffect> effects = new ArrayList<>();
    private final Map<Entity, Integer> victims = new HashMap<>();

    public BohAreaEffectCloud(World w) {
        super(w);
        this.noClip = true;
        this.isImmuneToFire = true;
        this.setSize(this.radius * 2.0F, 0.5F);
    }

    public static BohAreaEffectCloud fromNbt(World w, Vec3 p, NBTTagCompound tag) {
        BohAreaEffectCloud c = new BohAreaEffectCloud(w);
        c.setPosition(p.x, p.y, p.z);
        if (tag != null) {
            c.readFrom(tag);
        }

        return c;
    }

    protected void entityInit() {
    }

    public void onUpdate() {
        super.onUpdate();
        if (this.worldObj.isRemote) {
            int n = (int)(Math.PI * this.radius * this.radius * 0.5);

            for (int i = 0; i < n; i++) {
                double a = this.rand.nextFloat() * Math.PI * 2.0;
                double r = Math.sqrt(this.rand.nextFloat()) * this.radius;
                this.worldObj
                    .spawnParticle(this.particle, this.posX + Math.cos(a) * r, this.posY, this.posZ + Math.sin(a) * r, 0.0, 0.0, 0.0);
            }
        } else if (this.ticksExisted >= this.waitTime + this.duration) {
            this.setDead();
        } else {
            this.radius = this.radius + this.radiusPerTick;
            if (this.radius < 0.5F) {
                this.setDead();
            } else if (this.ticksExisted >= this.waitTime && this.ticksExisted % 5 == 0) {
                this.victims.entrySet().removeIf(en -> this.ticksExisted >= en.getValue());
                AxisAlignedBB box = AxisAlignedBB.getBoundingBox(
                    this.posX - this.radius,
                    this.posY - 0.5,
                    this.posZ - this.radius,
                    this.posX + this.radius,
                    this.posY + 3.0,
                    this.posZ + this.radius
                );

                for (Object o : this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, box)) {
                    EntityLivingBase l = (EntityLivingBase)o;
                    if (!this.victims.containsKey(l)) {
                        double dx = l.posX - this.posX;
                        double dz = l.posZ - this.posZ;
                        if (!(dx * dx + dz * dz > this.radius * this.radius)) {
                            this.victims.put(l, this.ticksExisted + 20);

                            for (PotionEffect e : this.effects) {
                                Potion p = Potion.potionTypes[e.getPotionID()];
                                if (p != null && p.isInstant()) {
                                    p.affectEntity(null, l, e.getAmplifier(), 0.5);
                                } else {
                                    l.addPotionEffect(new PotionEffect(e));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void readFrom(NBTTagCompound t) {
        if (t.hasKey("Radius")) {
            this.radius = t.getFloat("Radius");
        }

        if (t.hasKey("Duration")) {
            this.duration = t.getInteger("Duration");
        }

        if (t.hasKey("WaitTime")) {
            this.waitTime = t.getInteger("WaitTime");
        }

        if (t.hasKey("RadiusPerTick")) {
            this.radiusPerTick = t.getFloat("RadiusPerTick");
        }

        if (t.hasKey("Particle")) {
            this.particle = ParticleNames.legacy(t.getString("Particle"));
        }

        this.effects.clear();
        NBTTagList l = t.getTagList("Effects", 10);

        for (int i = 0; i < l.tagCount(); i++) {
            NBTTagCompound e = l.getCompoundTagAt(i);
            this.effects.add(new PotionEffect(e.getInteger("Id"), e.getInteger("Duration"), e.getInteger("Amplifier"), e.getBoolean("Ambient")));
        }

        this.setSize(this.radius * 2.0F, 0.5F);
    }

    protected void readEntityFromNBT(NBTTagCompound t) {
        this.readFrom(t);
        this.ticksExisted = t.getInteger("Age");
    }

    protected void writeEntityToNBT(NBTTagCompound t) {
        t.setFloat("Radius", this.radius);
        t.setInteger("Duration", this.duration);
        t.setInteger("WaitTime", this.waitTime);
        t.setFloat("RadiusPerTick", this.radiusPerTick);
        t.setString("Particle", this.particle);
        t.setInteger("Age", this.ticksExisted);
        NBTTagList l = new NBTTagList();

        for (PotionEffect e : this.effects) {
            NBTTagCompound c = new NBTTagCompound();
            c.setInteger("Id", e.getPotionID());
            c.setInteger("Duration", e.getDuration());
            c.setInteger("Amplifier", e.getAmplifier());
            c.setBoolean("Ambient", e.getIsAmbient());
            l.appendTag(c);
        }

        t.setTag("Effects", l);
    }

    public void writeSpawnData(ByteBuf b) {
        b.writeFloat(this.radius);
        b.writeInt(this.duration);
        b.writeInt(this.waitTime);
        b.writeFloat(this.radiusPerTick);
        byte[] s = this.particle.getBytes(StandardCharsets.UTF_8);
        b.writeShort(s.length);
        b.writeBytes(s);
    }

    public void readSpawnData(ByteBuf b) {
        this.radius = b.readFloat();
        this.duration = b.readInt();
        this.waitTime = b.readInt();
        this.radiusPerTick = b.readFloat();
        byte[] s = new byte[b.readShort()];
        b.readBytes(s);
        this.particle = new String(s, StandardCharsets.UTF_8);
    }
}
