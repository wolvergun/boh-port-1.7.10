package net.mcreator.boh.compat.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.netty.buffer.ByteBuf;
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
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

/** 1.20 area_effect_cloud: a lingering disc of particles that applies its effects to entities inside it. */
public class BohAreaEffectCloud extends Entity implements IEntityAdditionalSpawnData {

    private float radius = 3;
    private int duration = 600;
    private int waitTime = 10;
    private float radiusPerTick;
    private String particle = "spell";
    private final List<PotionEffect> effects = new ArrayList<>();
    private final Map<Entity, Integer> victims = new HashMap<>();

    public BohAreaEffectCloud(World w) {
        super(w);
        noClip = true;
        isImmuneToFire = true;
        setSize(radius * 2, 0.5F);
    }

    public static BohAreaEffectCloud fromNbt(World w, Vec3 p, NBTTagCompound tag) {
        BohAreaEffectCloud c = new BohAreaEffectCloud(w);
        c.setPosition(p.x, p.y, p.z);
        if (tag != null) c.readFrom(tag);
        return c;
    }

    @Override
    protected void entityInit() {}

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (worldObj.isRemote) {
            int n = (int) (Math.PI * radius * radius * 0.5F);
            for (int i = 0; i < n; i++) {
                double a = rand.nextFloat() * Math.PI * 2, r = Math.sqrt(rand.nextFloat()) * radius;
                worldObj.spawnParticle(particle, posX + Math.cos(a) * r, posY, posZ + Math.sin(a) * r, 0, 0, 0);
            }
            return;
        }
        if (ticksExisted >= waitTime + duration) {
            setDead();
            return;
        }
        radius += radiusPerTick;
        if (radius < 0.5F) {
            setDead();
            return;
        }
        if (ticksExisted < waitTime || ticksExisted % 5 != 0) return;
        victims.entrySet().removeIf(en -> ticksExisted >= en.getValue());
        AxisAlignedBB box = AxisAlignedBB.getBoundingBox(posX - radius, posY - 0.5, posZ - radius, posX + radius, posY + 3, posZ + radius);
        for (Object o : worldObj.getEntitiesWithinAABB(EntityLivingBase.class, box)) {
            EntityLivingBase l = (EntityLivingBase) o;
            if (victims.containsKey(l)) continue;
            double dx = l.posX - posX, dz = l.posZ - posZ;
            if (dx * dx + dz * dz > radius * radius) continue;
            victims.put(l, ticksExisted + 20);
            for (PotionEffect e : effects) {
                Potion p = Potion.potionTypes[e.getPotionID()];
                if (p != null && p.isInstant()) p.affectEntity(null, l, e.getAmplifier(), 0.5);
                else l.addPotionEffect(new PotionEffect(e));
            }
        }
    }

    private void readFrom(NBTTagCompound t) {
        if (t.hasKey("Radius")) radius = t.getFloat("Radius");
        if (t.hasKey("Duration")) duration = t.getInteger("Duration");
        if (t.hasKey("WaitTime")) waitTime = t.getInteger("WaitTime");
        if (t.hasKey("RadiusPerTick")) radiusPerTick = t.getFloat("RadiusPerTick");
        if (t.hasKey("Particle")) particle = ParticleNames.legacy(t.getString("Particle"));
        effects.clear();
        NBTTagList l = t.getTagList("Effects", 10);
        for (int i = 0; i < l.tagCount(); i++) {
            NBTTagCompound e = l.getCompoundTagAt(i);
            effects.add(new PotionEffect(e.getInteger("Id"), e.getInteger("Duration"), e.getInteger("Amplifier"), e.getBoolean("Ambient")));
        }
        setSize(radius * 2, 0.5F);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound t) {
        readFrom(t);
        ticksExisted = t.getInteger("Age");
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound t) {
        t.setFloat("Radius", radius);
        t.setInteger("Duration", duration);
        t.setInteger("WaitTime", waitTime);
        t.setFloat("RadiusPerTick", radiusPerTick);
        t.setString("Particle", particle);
        t.setInteger("Age", ticksExisted);
        NBTTagList l = new NBTTagList();
        for (PotionEffect e : effects) {
            NBTTagCompound c = new NBTTagCompound();
            c.setInteger("Id", e.getPotionID());
            c.setInteger("Duration", e.getDuration());
            c.setInteger("Amplifier", e.getAmplifier());
            c.setBoolean("Ambient", e.getIsAmbient());
            l.appendTag(c);
        }
        t.setTag("Effects", l);
    }

    @Override
    public void writeSpawnData(ByteBuf b) {
        b.writeFloat(radius);
        b.writeInt(duration);
        b.writeInt(waitTime);
        b.writeFloat(radiusPerTick);
        byte[] s = particle.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        b.writeShort(s.length);
        b.writeBytes(s);
    }

    @Override
    public void readSpawnData(ByteBuf b) {
        radius = b.readFloat();
        duration = b.readInt();
        waitTime = b.readInt();
        radiusPerTick = b.readFloat();
        byte[] s = new byte[b.readShort()];
        b.readBytes(s);
        particle = new String(s, java.nio.charset.StandardCharsets.UTF_8);
    }
}
