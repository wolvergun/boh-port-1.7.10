package net.mcreator.boh.compat.forge.event.entity.living;

import cpw.mods.fml.common.eventhandler.Cancelable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingEvent;

@Cancelable
public class LivingChangeTargetEvent extends LivingEvent {
    private final EntityLivingBase originalTarget;
    private EntityLivingBase newTarget;

    public LivingChangeTargetEvent() {
        this(null, null);
    }

    public LivingChangeTargetEvent(EntityLivingBase entity, EntityLivingBase target) {
        super(entity);
        this.originalTarget = target;
        this.newTarget = target;
    }

    public EntityLivingBase getOriginalTarget() {
        return this.originalTarget;
    }

    public EntityLivingBase getNewTarget() {
        return this.newTarget;
    }

    public void setNewTarget(EntityLivingBase t) {
        this.newTarget = t;
    }
}
