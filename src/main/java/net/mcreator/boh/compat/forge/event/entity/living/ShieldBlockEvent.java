package net.mcreator.boh.compat.forge.event.entity.living;

import cpw.mods.fml.common.eventhandler.Cancelable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingEvent;

@Cancelable
public class ShieldBlockEvent extends LivingEvent {
    private final DamageSource source;
    private float blocked;

    public ShieldBlockEvent() {
        this(null, null, 0.0F);
    }

    public ShieldBlockEvent(EntityLivingBase entity, DamageSource source, float blocked) {
        super(entity);
        this.source = source;
        this.blocked = blocked;
    }

    public DamageSource getDamageSource() {
        return this.source;
    }

    public float getBlockedDamage() {
        return this.blocked;
    }

    public void setBlockedDamage(float f) {
        this.blocked = f;
    }
}
