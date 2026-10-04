package net.mcreator.boh.compat.forge.common.capabilities;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.mcreator.boh.compat.forge.common.util.LazyOptional;
import net.mcreator.boh.compat.forge.event.AttachCapabilitiesEvent;
import net.mcreator.boh.compat.mc.core.Direction;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;

public final class EntityCapabilities implements IExtendedEntityProperties {
    public static final String KEY = "boh_caps";
    final Map<ResourceLocation, ICapabilityProvider> providers = new LinkedHashMap<>();

    public static EntityCapabilities of(Entity e) {
        return (EntityCapabilities)e.getExtendedProperties("boh_caps");
    }

    public static <T> LazyOptional<T> get(Entity e, Capability<T> cap, Direction side) {
        EntityCapabilities c = e == null ? null : of(e);
        if (c != null) {
            for (ICapabilityProvider p : c.providers.values()) {
                LazyOptional<T> o = p.getCapability(cap, side);
                if (o != null && o.isPresent()) {
                    return o;
                }
            }
        }

        return LazyOptional.empty();
    }

    public void saveNBTData(NBTTagCompound tag) {
        NBTTagCompound out = new NBTTagCompound();

        for (Entry<ResourceLocation, ICapabilityProvider> e : this.providers.entrySet()) {
            if (e.getValue() instanceof ICapabilitySerializable) {
                out.setTag(e.getKey().toString(), ((ICapabilitySerializable)e.getValue()).serializeNBT());
            }
        }

        tag.setTag("ForgeCaps", out);
    }

    public void loadNBTData(NBTTagCompound tag) {
        NBTTagCompound in = tag.getCompoundTag("ForgeCaps");

        for (Entry<ResourceLocation, ICapabilityProvider> e : this.providers.entrySet()) {
            NBTBase b = in.getTag(e.getKey().toString());
            if (b != null && e.getValue() instanceof ICapabilitySerializable) {
                ((ICapabilitySerializable)e.getValue()).deserializeNBT(b);
            }
        }
    }

    public void init(Entity entity, World world) {
    }

    public static void install() {
        MinecraftForge.EVENT_BUS.register(new EntityCapabilities.Hooks());
    }

    public static final class Hooks {
        @SubscribeEvent
        public void construct(EntityConstructing e) {
            AttachCapabilitiesEvent<Entity> ev = new AttachCapabilitiesEvent<>(e.entity);
            MinecraftForge.EVENT_BUS.post(ev);
            if (!ev.getCapabilities().isEmpty()) {
                EntityCapabilities c = new EntityCapabilities();
                c.providers.putAll(ev.getCapabilities());
                e.entity.registerExtendedProperties("boh_caps", c);
            }
        }
    }
}
