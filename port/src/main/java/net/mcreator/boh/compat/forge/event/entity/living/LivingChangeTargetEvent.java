package net.mcreator.boh.compat.forge.event.entity.living;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingEvent;
import cpw.mods.fml.common.eventhandler.Cancelable;

/** 1.20 LivingChangeTargetEvent, posted by the bridge when a mob picks a new attack target. */
@Cancelable
public class LivingChangeTargetEvent extends LivingEvent {

    public LivingChangeTargetEvent() {
        this(null, null);
    }

    private final EntityLivingBase originalTarget;
    private EntityLivingBase newTarget;

    public LivingChangeTargetEvent(EntityLivingBase entity, EntityLivingBase target) {
        super(entity);
        originalTarget = target;
        newTarget = target;
    }

    public EntityLivingBase getOriginalTarget() {
        return originalTarget;
    }

    public EntityLivingBase getNewTarget() {
        return newTarget;
    }

    public void setNewTarget(EntityLivingBase t) {
        newTarget = t;
    }
}
