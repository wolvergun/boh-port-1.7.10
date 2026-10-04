package net.mcreator.boh.compat.mc.world.entity;

import java.util.HashMap;
import java.util.Map;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.tags.TagKey;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/** 1.20 EntityType: entity class + factory + spawn settings. */
public class EntityType<T extends Entity> {

    @FunctionalInterface
    public interface EntityFactory<T extends Entity> {

        T create(EntityType<T> type, World world);
    }

    private static final Map<Class<?>, EntityType<?>> BY_CLASS = new HashMap<>();
    private static final Map<String, EntityType<?>> VANILLA = new HashMap<>();

    public static final EntityType<EntityLightningBolt> LIGHTNING_BOLT = vanilla("lightning_bolt", EntityLightningBolt.class,
        (t, w) -> new net.mcreator.boh.compat.entity.BohLightningBolt(w, 0, 0, 0), 0f, 0f);
    public static final EntityType<EntitySmallFireball> SMALL_FIREBALL = vanilla("small_fireball", EntitySmallFireball.class,
        (t, w) -> new EntitySmallFireball(w), 0.3125f, 0.3125f);
    public static final EntityType<EntityLargeFireball> FIREBALL = vanilla("fireball", EntityLargeFireball.class,
        (t, w) -> new EntityLargeFireball(w), 1f, 1f);
    public static final EntityType<EntityWolf> WOLF = vanilla("wolf", EntityWolf.class, (t, w) -> new EntityWolf(w), 0.6f, 0.8f);
    public static final EntityType<EntityCow> COW = vanilla("cow", EntityCow.class, (t, w) -> new EntityCow(w), 0.9f, 1.3f);
    public static final EntityType<EntityArrow> ARROW = vanilla("arrow", EntityArrow.class, (t, w) -> new EntityArrow(w), 0.5f, 0.5f);

    final EntityFactory<T> factory;
    final Class<T> entityClass;
    final MobCategory category;
    final float width, height;
    final boolean fireImmune;
    final int trackingRange, updateInterval;
    final boolean velocityUpdates;
    ResourceLocation id;

    EntityType(EntityFactory<T> factory, Class<T> entityClass, MobCategory category, float width, float height,
        boolean fireImmune, int trackingRange, int updateInterval, boolean velocityUpdates) {
        this.factory = factory;
        this.entityClass = entityClass;
        this.category = category;
        this.width = width;
        this.height = height;
        this.fireImmune = fireImmune;
        this.trackingRange = trackingRange;
        this.updateInterval = updateInterval;
        this.velocityUpdates = velocityUpdates;
        if (entityClass != null) BY_CLASS.put(entityClass, this);
    }

    private static <T extends Entity> EntityType<T> vanilla(String name, Class<T> cls, EntityFactory<T> f, float w, float h) {
        EntityType<T> t = new EntityType<>(f, cls, MobCategory.MISC, w, h, false, 64, 3, true);
        t.id = new ResourceLocation("minecraft", name);
        VANILLA.put(name, t);
        return t;
    }

    /** Lookup for ids the mod did not register itself ("minecraft:zombie", ...). */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static EntityType<?> vanilla(ResourceLocation id) {
        if (!"minecraft".equals(id.getResourceDomain())) return null;
        EntityType<?> t = VANILLA.get(id.getResourcePath());
        if (t != null) return t;
        String legacyName = net.mcreator.boh.compat.registry.LegacyIds.entityName(id.getResourcePath());
        Class<? extends Entity> cls = (Class<? extends Entity>) EntityList.stringToClassMapping.get(legacyName);
        if (cls == null) return null;
        Class<? extends Entity> fc = cls;
        EntityType nt = new EntityType(
            (type, w) -> EntityList.createEntityByName(legacyName, (World) w),
            fc,
            EntityLiving.class.isAssignableFrom(cls) ? MobCategory.CREATURE : MobCategory.MISC,
            0.6f,
            1.8f,
            false,
            80,
            3,
            true);
        nt.id = id;
        VANILLA.put(id.getResourcePath(), nt);
        return nt;
    }

    public static ResourceLocation key(EntityType<?> type) {
        return type == null ? null : type.id;
    }

    /** The EntityType of an existing entity (mod types by class, vanilla types by EntityList name). */
    @SuppressWarnings("unchecked")
    public static <T extends Entity> EntityType<T> of(T entity) {
        if (entity == null) return null;
        for (Class<?> c = entity.getClass(); c != null && c != Object.class; c = c.getSuperclass()) {
            EntityType<?> t = BY_CLASS.get(c);
            if (t != null && (t.entityClass == entity.getClass() || c == entity.getClass())) return (EntityType<T>) t;
        }
        String name = EntityList.getEntityString(entity);
        if (entity instanceof net.minecraft.entity.player.EntityPlayer) name = "player";
        if (name == null) name = entity.getClass().getSimpleName();
        EntityType<?> t = vanilla(new ResourceLocation("minecraft", net.mcreator.boh.compat.registry.LegacyIds.modernEntityName(name)));
        if (t == null) {
            t = new EntityType<>(null, (Class<T>) entity.getClass(), MobCategory.MISC, entity.width, entity.height, false, 64, 3, true);
            t.id = new ResourceLocation("minecraft", name.toLowerCase());
        }
        return (EntityType<T>) t;
    }

    public static EntityType<?> byClass(Class<?> cls) {
        return BY_CLASS.get(cls);
    }

    public T create(World world) {
        if (factory == null || world == null) return null;
        return factory.create(this, world);
    }

    public T spawn(World world, BlockPos pos, MobSpawnType reason) {
        T e = create(world);
        if (e == null) return null;
        e.setLocationAndAngles(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, world.rand.nextFloat() * 360f, 0f);
        if (e instanceof EntityLiving) {
            EntityLiving l = (EntityLiving) e;
            l.rotationYawHead = l.rotationYaw;
            l.renderYawOffset = l.rotationYaw;
            l.onSpawnWithEgg(null);
        }
        world.spawnEntityInWorld(e);
        return e;
    }

    public T spawn(World world, Object stack, Object player, BlockPos pos, MobSpawnType reason, boolean b1, boolean b2) {
        return spawn(world, pos, reason);
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public MobCategory getCategory() {
        return category;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public boolean fireImmune() {
        return fireImmune;
    }

    public int clientTrackingRange() {
        return trackingRange;
    }

    public int updateInterval() {
        return updateInterval;
    }

    public boolean trackDeltas() {
        return velocityUpdates;
    }

    public ResourceLocation getId() {
        return id;
    }

    public void setRegistryName(ResourceLocation id) {
        this.id = id;
    }

    public String getDescriptionId() {
        return id == null ? "entity.unknown" : "entity." + id.getResourceDomain() + "." + id.getResourcePath();
    }

    public Component getDescription() {
        return Component.translatable(getDescriptionId());
    }

    public boolean is(TagKey<?> tag) {
        return tag.contains(id);
    }

    public boolean is(EntityType<?> other) {
        return this == other;
    }

    public EntityDimensions getDimensions() {
        return EntityDimensions.scalable(width, height);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
