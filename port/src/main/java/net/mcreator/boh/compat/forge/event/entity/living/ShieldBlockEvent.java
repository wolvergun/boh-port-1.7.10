package net.mcreator.boh.compat.forge.event.entity.living;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingEvent;
import cpw.mods.fml.common.eventhandler.Cancelable;

/** 1.20 ShieldBlockEvent; 1.7.10 has no shields, so it fires when a sword-blocking player is attacked. */
@Cancelable
public class ShieldBlockEvent extends LivingEvent {

    public ShieldBlockEvent() {
        this(null, null, 0);
    }

    private final DamageSource source;
    private float blocked;

    public ShieldBlockEvent(EntityLivingBase entity, DamageSource source, float blocked) {
        super(entity);
        this.source = source;
        this.blocked = blocked;
    }

    public DamageSource getDamageSource() {
        return source;
    }

    public float getBlockedDamage() {
        return blocked;
    }

    public void setBlockedDamage(float f) {
        blocked = f;
    }
}
