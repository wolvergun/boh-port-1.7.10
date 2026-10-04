package net.mcreator.boh.compat.mc.sounds;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.ResourceLocation;

/**
 * 1.20 SoundEvent: a sound id. Mod sounds keep their "boh:" names (registered via the converted sounds.json);
 * vanilla 1.20 ids are mapped to the nearest 1.7.10 sound.
 */
public class SoundEvent {

    private static final Map<String, String> VANILLA = new HashMap<>();

    static {
        String[][] m = {
            { "entity.generic.hurt", "game.hostile.hurt" }, { "entity.generic.death", "game.hostile.die" },
            { "entity.generic.explode", "random.explode" }, { "entity.generic.extinguish_fire", "random.fizz" },
            { "entity.arrow.hit_player", "random.successful_hit" }, { "entity.arrow.shoot", "random.bow" },
            { "block.anvil.land", "random.anvil_land" }, { "block.anvil.destroy", "random.anvil_break" },
            { "block.anvil.break", "random.anvil_break" }, { "block.anvil.use", "random.anvil_use" },
            { "item.axe.strip", "dig.wood" }, { "block.wood.break", "dig.wood" }, { "block.wood.place", "dig.wood" },
            { "entity.evoker.cast_spell", "mob.wither.shoot" }, { "entity.evoker.prepare_summon", "mob.wither.spawn" },
            { "entity.illusioner.cast_spell", "mob.wither.shoot" }, { "entity.blaze.shoot", "mob.ghast.fireball" },
            { "entity.wither.shoot", "mob.wither.shoot" }, { "entity.wolf.howl", "mob.wolf.howl" },
            { "entity.wolf.growl", "mob.wolf.growl" }, { "entity.wolf.hurt", "mob.wolf.hurt" },
            { "entity.wolf.death", "mob.wolf.death" }, { "entity.wolf.step", "mob.wolf.step" },
            { "entity.player.attack.sweep", "random.bow" }, { "entity.player.burp", "random.burp" },
            { "entity.llama.spit", "mob.ghast.fireball" }, { "entity.horse.gallop", "mob.horse.gallop" },
            { "entity.horse.step", "mob.horse.wood" }, { "entity.horse.eat", "eat" },
            { "entity.horse.hurt", "mob.horse.hit" }, { "entity.horse.death", "mob.horse.death" },
            { "entity.horse.ambient", "mob.horse.idle" }, { "entity.zombie_horse.hurt", "mob.horse.zombie.hit" },
            { "entity.zombie_horse.death", "mob.horse.zombie.death" },
            { "entity.zombie_horse.ambient", "mob.horse.zombie.idle" }, { "weather.rain", "ambient.weather.rain" },
            { "item.bucket.fill_lava", "liquid.lavapop" }, { "item.bucket.empty_lava", "liquid.lavapop" },
            { "item.book.page_turn", "random.click" }, { "entity.silverfish.hurt", "mob.silverfish.hit" },
            { "entity.silverfish.death", "mob.silverfish.kill" }, { "entity.silverfish.step", "mob.silverfish.step" },
            { "entity.silverfish.ambient", "mob.silverfish.say" },
            { "entity.parrot.imitate.silverfish", "mob.silverfish.say" }, { "entity.sheep.shear", "mob.sheep.shear" },
            { "block.slime_block.break", "mob.slime.big" }, { "block.bell.resonate", "note.pling" },
            { "item.flintandsteel.use", "fire.ignite" }, { "item.bottle.fill_dragonbreath", "random.drink" },
            { "item.armor.equip_leather", "random.click" }, { "item.armor.equip_generic", "random.click" },
            { "entity.wandering_trader.drink_milk", "random.drink" }, { "entity.iron_golem.damage", "mob.irongolem.hit" },
            { "entity.goat.screaming.ram_impact", "mob.zombie.wood" },
            { "entity.goat.screaming.prepare_ram", "mob.sheep.say" }, { "entity.goat.screaming.hurt", "mob.sheep.say" },
            { "entity.goat.screaming.death", "mob.sheep.say" }, { "entity.goat.screaming.ambient", "mob.sheep.say" },
            { "entity.goat.step", "mob.sheep.step" }, { "entity.firework_rocket.launch", "fireworks.launch" },
            { "entity.firework_rocket.blast", "fireworks.blast" }, { "entity.ender_dragon.ambient", "mob.enderdragon.growl" },
            { "entity.chicken.egg", "mob.chicken.plop" }, { "block.note_block.chime", "note.pling" },
            { "block.amethyst_block.chime", "random.orb" }, { "block.grass.break", "dig.grass" },
            { "block.fire.extinguish", "random.fizz" }, { "block.fire.ambient", "fire.fire" },
            { "block.campfire.crackle", "fire.fire" }, { "block.dripstone_block.place", "dig.stone" },
            { "block.dripstone_block.hit", "step.stone" }, { "block.dripstone_block.fall", "dig.stone" },
            { "block.dripstone_block.break", "dig.stone" }, { "item.shield.break", "random.break" },
            { "item.shield.block", "mob.zombie.wood" }, { "entity.zombie.step", "mob.zombie.step" },
            { "entity.witch.celebrate", "mob.witch.idle" }, { "entity.villager.work_cartographer", "random.click" },
            { "entity.turtle.hurt_baby", "mob.chicken.hurt" }, { "entity.turtle.death_baby", "mob.chicken.hurt" },
            { "entity.splash_potion.break", "game.potion.smash" }, { "entity.spider.step", "mob.spider.step" },
            { "entity.rabbit.jump", "mob.chicken.step" }, { "entity.rabbit.hurt", "mob.chicken.hurt" },
            { "entity.rabbit.death", "mob.chicken.hurt" }, { "entity.rabbit.ambient", "mob.chicken.say" },
            { "entity.fish.swim", "game.hostile.swim" }, { "entity.drowned.step", "mob.zombie.step" },
            { "entity.drowned.hurt_water", "mob.zombie.hurt" }, { "entity.drowned.death_water", "mob.zombie.death" },
            { "entity.drowned.ambient_water", "mob.zombie.say" }, { "entity.cod.hurt", "game.neutral.hurt" },
            { "entity.cod.death", "game.neutral.die" }, { "entity.cod.ambient", "liquid.water" },
            { "entity.bat.loop", "mob.bat.loop" }, { "entity.bat.hurt", "mob.bat.hurt" },
            { "entity.bat.death", "mob.bat.death" }, { "entity.bat.ambient", "mob.bat.idle" },
            { "block.wool.break", "dig.cloth" }, { "entity.player.levelup", "random.levelup" },
            { "entity.experience_orb.pickup", "random.orb" }, { "entity.item.pickup", "random.pop" },
            { "block.stone.break", "dig.stone" }, { "block.glass.break", "dig.glass" },
            { "entity.lightning_bolt.thunder", "ambient.weather.thunder" },
            { "entity.lightning_bolt.impact", "random.explode" }, { "block.portal.travel", "portal.travel" },
            { "block.portal.trigger", "portal.trigger" }, { "block.chest.open", "random.chestopen" },
            { "block.chest.close", "random.chestclosed" }, { "block.wooden_door.open", "random.door_open" },
            { "block.wooden_door.close", "random.door_close" }, { "ui.button.click", "gui.button.press" },
            { "entity.player.hurt", "game.player.hurt" }, { "entity.player.death", "game.player.die" },
            { "entity.zombie.ambient", "mob.zombie.say" }, { "entity.zombie.hurt", "mob.zombie.hurt" },
            { "entity.zombie.death", "mob.zombie.death" }, { "entity.skeleton.ambient", "mob.skeleton.say" },
            { "entity.enderman.teleport", "mob.endermen.portal" }, { "entity.ghast.scream", "mob.ghast.scream" },
            { "intentionally_empty", "" }, };
        for (String[] p : m) VANILLA.put(p[0], p[1]);
    }

    private final ResourceLocation location;
    private final String legacyName;

    public SoundEvent(ResourceLocation location) {
        this.location = location;
        String ns = location.getResourceDomain(), path = location.getResourcePath();
        if (ns.equals("minecraft")) {
            String legacy = VANILLA.get(path);
            legacyName = legacy != null ? legacy : path;
        } else {
            legacyName = ns + ":" + path;
        }
    }

    public static SoundEvent createVariableRangeEvent(ResourceLocation location) {
        return new SoundEvent(location);
    }

    public static SoundEvent createFixedRangeEvent(ResourceLocation location, float range) {
        return new SoundEvent(location);
    }

    public ResourceLocation getLocation() {
        return location;
    }

    /** The sound name the 1.7.10 sound system understands, or "" for a silent event. */
    public String legacyName() {
        return legacyName;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof SoundEvent && ((SoundEvent) o).location.equals(location);
    }

    @Override
    public int hashCode() {
        return location.hashCode();
    }

    @Override
    public String toString() {
        return location.toString();
    }
}
