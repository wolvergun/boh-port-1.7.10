package net.mcreator.boh.compat.mc.world.entity;

import java.util.HashMap;
import java.util.Map;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.tags.TagKey;
import net.mcreator.boh.compat.registry.LegacyIds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityType<T extends Entity> {
    private static final Map<Class<?>, EntityType<?>> BY_CLASS = new HashMap<>();
    private static final Map<String, EntityType<?>> VANILLA = new HashMap<>();
    public static final EntityType<EntityLightningBolt> LIGHTNING_BOLT = vanilla(
        "lightning_bolt", (Class<T>)EntityLightningBolt.class, (t, w) -> (T)(new EntityLightningBolt(w, 0.0, 0.0, 0.0)), 0.0F, 0.0F
    );
    public static final EntityType<EntitySmallFireball> SMALL_FIREBALL = vanilla(
        "small_fireball", (Class<T>)EntitySmallFireball.class, (t, w) -> (T)(new EntitySmallFireball(w)), 0.3125F, 0.3125F
    );
    public static final EntityType<EntityLargeFireball> FIREBALL = vanilla(
        "fireball", (Class<T>)EntityLargeFireball.class, (t, w) -> (T)(new EntityLargeFireball(w)), 1.0F, 1.0F
    );
    public static final EntityType<EntityWolf> WOLF = vanilla("wolf", (Class<T>)EntityWolf.class, (t, w) -> (T)(new EntityWolf(w)), 0.6F, 0.8F);
    public static final EntityType<EntityCow> COW = vanilla("cow", (Class<T>)EntityCow.class, (t, w) -> (T)(new EntityCow(w)), 0.9F, 1.3F);
    public static final EntityType<EntityArrow> ARROW = vanilla("arrow", (Class<T>)EntityArrow.class, (t, w) -> (T)(new EntityArrow(w)), 0.5F, 0.5F);
    final EntityType.EntityFactory<T> factory;
    final Class<T> entityClass;
    final MobCategory category;
    final float width;
    final float height;
    final boolean fireImmune;
    final int trackingRange;
    final int updateInterval;
    final boolean velocityUpdates;
    ResourceLocation id;

    EntityType(
        EntityType.EntityFactory<T> factory,
        Class<T> entityClass,
        MobCategory category,
        float width,
        float height,
        boolean fireImmune,
        int trackingRange,
        int updateInterval,
        boolean velocityUpdates
    ) {
        this.factory = factory;
        this.entityClass = entityClass;
        this.category = category;
        this.width = width;
        this.height = height;
        this.fireImmune = fireImmune;
        this.trackingRange = trackingRange;
        this.updateInterval = updateInterval;
        this.velocityUpdates = velocityUpdates;
        if (entityClass != null) {
            BY_CLASS.put(entityClass, this);
        }
    }

    private static <T extends Entity> EntityType<T> vanilla(String name, Class<T> cls, EntityType.EntityFactory<T> f, float w, float h) {
        EntityType<T> t = new EntityType<>(f, cls, MobCategory.MISC, w, h, false, 64, 3, true);
        t.id = new ResourceLocation("minecraft", name);
        VANILLA.put(name, t);
        return t;
    }

    public static EntityType<?> vanilla(ResourceLocation id) {
        if (!"minecraft".equals(id.getResourceDomain())) {
            return null;
        } else {
            EntityType<?> t = VANILLA.get(id.getResourcePath());
            if (t != null) {
                return t;
            } else {
                String legacyName = LegacyIds.entityName(id.getResourcePath());
                Class<? extends Entity> cls = (Class<? extends Entity>)EntityList.stringToClassMapping.get(legacyName);
                if (cls == null) {
                    return null;
                } else {
                    EntityType nt = new EntityType<>(
                        (type, w) -> EntityList.createEntityByName(legacyName, w),
                        cls,
                        EntityLiving.class.isAssignableFrom(cls) ? MobCategory.CREATURE : MobCategory.MISC,
                        0.6F,
                        1.8F,
                        false,
                        80,
                        3,
                        true
                    );
                    nt.id = id;
                    VANILLA.put(id.getResourcePath(), nt);
                    return nt;
                }
            }
        }
    }

    public static ResourceLocation key(EntityType<?> type) {
        return type == null ? null : type.id;
    }

    public static <T extends Entity> EntityType<T> of(T entity) {
        if (entity == null) {
            return null;
        } else {
            for (Class<?> c = entity.getClass(); c != null && c != Object.class; c = c.getSuperclass()) {
                EntityType<?> t = BY_CLASS.get(c);
                if (t != null && (t.entityClass == entity.getClass() || c == entity.getClass())) {
                    return (EntityType<T>)t;
                }
            }

            String name = EntityList.getEntityString(entity);
            if (entity instanceof EntityPlayer) {
                name = "player";
            }

            if (name == null) {
                name = entity.getClass().getSimpleName();
            }

            EntityType<?> t = vanilla(new ResourceLocation("minecraft", LegacyIds.modernEntityName(name)));
            if (t == null) {
                t = new EntityType<>(null, entity.getClass(), MobCategory.MISC, entity.width, entity.height, false, 64, 3, true);
                t.id = new ResourceLocation("minecraft", name.toLowerCase());
            }

            return (EntityType<T>)t;
        }
    }

    public static EntityType<?> byClass(Class<?> cls) {
        return BY_CLASS.get(cls);
    }

    public T create(World world) {
        return this.factory != null && world != null ? this.factory.create(this, world) : null;
    }

    public T spawn(World world, BlockPos pos, MobSpawnType reason) {
        T e = this.create(world);
        if (e == null) {
            return null;
        } else {
            e.setLocationAndAngles(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, world.rand.nextFloat() * 360.0F, 0.0F);
            if (e instanceof EntityLiving l) {
                l.rotationYawHead = l.rotationYaw;
                l.renderYawOffset = l.rotationYaw;
                l.onSpawnWithEgg(null);
            }

            world.spawnEntityInWorld(e);
            return e;
        }
    }

    public T spawn(World world, Object stack, Object player, BlockPos pos, MobSpawnType reason, boolean b1, boolean b2) {
        return this.spawn(world, pos, reason);
    }

    public Class<T> getEntityClass() {
        return this.entityClass;
    }

    public MobCategory getCategory() {
        return this.category;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public boolean fireImmune() {
        return this.fireImmune;
    }

    public int clientTrackingRange() {
        return this.trackingRange;
    }

    public int updateInterval() {
        return this.updateInterval;
    }

    public boolean trackDeltas() {
        return this.velocityUpdates;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public void setRegistryName(ResourceLocation id) {
        this.id = id;
    }

    public String getDescriptionId() {
        return this.id == null ? "entity.unknown" : "entity." + this.id.getResourceDomain() + "." + this.id.getResourcePath();
    }

    public Component getDescription() {
        return Component.translatable(this.getDescriptionId());
    }

    public boolean is(TagKey<?> tag) {
        return tag.contains(this.id);
    }

    public boolean is(EntityType<?> other) {
        return this == other;
    }

    public EntityDimensions getDimensions() {
        return EntityDimensions.scalable(this.width, this.height);
    }

    @Override
    public String toString() {
        return String.valueOf(this.id);
    }

    @FunctionalInterface
    public interface EntityFactory<T extends Entity> {
        T create(EntityType<T> var1, World var2);
    }
}
