package net.mcreator.boh.compat.mc.world.entity.ai.attributes;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

/**
 * 1.20 Attributes. Ones 1.7.10 lacks are custom {@link RangedAttribute}s whose effect the compat base entities
 * apply themselves (armor reduces damage, attack knockback is added on hit, flying speed drives flight).
 */
public final class Attributes {

    public static final IAttribute MAX_HEALTH = SharedMonsterAttributes.maxHealth;
    public static final IAttribute FOLLOW_RANGE = SharedMonsterAttributes.followRange;
    public static final IAttribute KNOCKBACK_RESISTANCE = SharedMonsterAttributes.knockbackResistance;
    public static final IAttribute MOVEMENT_SPEED = SharedMonsterAttributes.movementSpeed;
    public static final IAttribute ATTACK_DAMAGE = SharedMonsterAttributes.attackDamage;
    public static final IAttribute ARMOR = new RangedAttribute("boh.armor", 0.0, 0.0, 30.0).setShouldWatch(true);
    public static final IAttribute ARMOR_TOUGHNESS = new RangedAttribute("boh.armorToughness", 0.0, 0.0, 20.0);
    public static final IAttribute ATTACK_KNOCKBACK = new RangedAttribute("boh.attackKnockback", 0.0, 0.0, 5.0);
    public static final IAttribute ATTACK_SPEED = new RangedAttribute("boh.attackSpeed", 4.0, 0.0, 1024.0);
    public static final IAttribute FLYING_SPEED = new RangedAttribute("boh.flyingSpeed", 0.4, 0.0, 1024.0);
    public static final IAttribute LUCK = new RangedAttribute("boh.luck", 0.0, -1024.0, 1024.0);
    public static final IAttribute JUMP_STRENGTH = new RangedAttribute("horse.jumpStrength", 0.7, 0.0, 2.0);

    private Attributes() {}
}
