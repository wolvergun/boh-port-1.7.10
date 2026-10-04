package net.mcreator.boh.init;

import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.forge.registries.DeferredRegister;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.forge.registries.RegistryObject;

public class BohModSounds {

    public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "boh");

    public static final RegistryObject<SoundEvent> PYRAMIDHEAD_IDLE = REGISTRY.register("pyramidhead_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "pyramidhead_idle")));

    public static final RegistryObject<SoundEvent> PYRAMIDHEAD_HURT = REGISTRY.register("pyramidhead_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "pyramidhead_hurt")));

    public static final RegistryObject<SoundEvent> PYRAMIDHEAD_DEATH = REGISTRY.register("pyramidhead_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "pyramidhead_death")));

    public static final RegistryObject<SoundEvent> SAWRUNNER_HURT = REGISTRY.register("sawrunner_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "sawrunner_hurt")));

    public static final RegistryObject<SoundEvent> SAWRUNNER_ATTACK = REGISTRY.register("sawrunner_attack", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "sawrunner_attack")));

    public static final RegistryObject<SoundEvent> SAWRUNNER_AMBIENT = REGISTRY.register("sawrunner_ambient", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "sawrunner_ambient")));

    public static final RegistryObject<SoundEvent> SAWRUNNER_DEATH = REGISTRY.register("sawrunner_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "sawrunner_death")));

    public static final RegistryObject<SoundEvent> SLENDER_JUMPSCARE = REGISTRY.register("slender_jumpscare", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "slender_jumpscare")));

    public static final RegistryObject<SoundEvent> SLENDER_KILL = REGISTRY.register("slender_kill", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "slender_kill")));

    public static final RegistryObject<SoundEvent> BEN_LAUGHING = REGISTRY.register("ben_laughing", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "ben_laughing")));

    public static final RegistryObject<SoundEvent> BEN_BURNING = REGISTRY.register("ben_burning", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "ben_burning")));

    public static final RegistryObject<SoundEvent> XENOMORPH_DEATH = REGISTRY.register("xenomorph_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "xenomorph_death")));

    public static final RegistryObject<SoundEvent> XENOMORPH_HURT = REGISTRY.register("xenomorph_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "xenomorph_hurt")));

    public static final RegistryObject<SoundEvent> XENOMORPH_IDLE = REGISTRY.register("xenomorph_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "xenomorph_idle")));

    public static final RegistryObject<SoundEvent> XENOMORPH_KILL = REGISTRY.register("xenomorph_kill", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "xenomorph_kill")));

    public static final RegistryObject<SoundEvent> XENOMORPH_STEP = REGISTRY.register("xenomorph_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "xenomorph_step")));

    public static final RegistryObject<SoundEvent> SLENDER_IDLE = REGISTRY.register("slender_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "slender_idle")));

    public static final RegistryObject<SoundEvent> SLENDER_AMBIENT = REGISTRY.register("slender_ambient", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "slender_ambient")));

    public static final RegistryObject<SoundEvent> LIFEFORM_IDLE = REGISTRY.register("lifeform_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "lifeform_idle")));

    public static final RegistryObject<SoundEvent> LIFEFORM_DEATH = REGISTRY.register("lifeform_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "lifeform_death")));

    public static final RegistryObject<SoundEvent> LIFEFORM_HURT = REGISTRY.register("lifeform_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "lifeform_hurt")));

    public static final RegistryObject<SoundEvent> JEFF = REGISTRY.register("jeff", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "jeff")));

    public static final RegistryObject<SoundEvent> SIRENHEAD_STOMP = REGISTRY.register("sirenhead_stomp", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "sirenhead_stomp")));

    public static final RegistryObject<SoundEvent> CHESTBURSTER_KILL = REGISTRY.register("chestburster_kill", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "chestburster_kill")));

    public static final RegistryObject<SoundEvent> FACEHUGGER_ATTACK = REGISTRY.register("facehugger_attack", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "facehugger_attack")));

    public static final RegistryObject<SoundEvent> FACEHUGGER_DEATH = REGISTRY.register("facehugger_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "facehugger_death")));

    public static final RegistryObject<SoundEvent> FACEHUGGER_HURT = REGISTRY.register("facehugger_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "facehugger_hurt")));

    public static final RegistryObject<SoundEvent> FACEHUGGER_IDLE = REGISTRY.register("facehugger_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "facehugger_idle")));

    public static final RegistryObject<SoundEvent> OVAMORPH_OPEN = REGISTRY.register("ovamorph_open", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "ovamorph_open")));

    public static final RegistryObject<SoundEvent> PAGE_PICKUP = REGISTRY.register("page_pickup", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "page_pickup")));

    public static final RegistryObject<SoundEvent> JEFF_SLEEP = REGISTRY.register("jeff_sleep", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "jeff_sleep")));

    public static final RegistryObject<SoundEvent> DEMOGORGON_ATTACK = REGISTRY.register("demogorgon_attack", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "demogorgon_attack")));

    public static final RegistryObject<SoundEvent> DEMOGORGON_DEATH = REGISTRY.register("demogorgon_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "demogorgon_death")));

    public static final RegistryObject<SoundEvent> DEMOGORGON_HURT = REGISTRY.register("demogorgon_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "demogorgon_hurt")));

    public static final RegistryObject<SoundEvent> DEMOGORGON_IDLE = REGISTRY.register("demogorgon_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "demogorgon_idle")));

    public static final RegistryObject<SoundEvent> DEMOGORGON_ROAR = REGISTRY.register("demogorgon_roar", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "demogorgon_roar")));

    public static final RegistryObject<SoundEvent> IM_DEAD = REGISTRY.register("im_dead", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "im_dead")));

    public static final RegistryObject<SoundEvent> NECOARC_SPAWN = REGISTRY.register("necoarc_spawn", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "necoarc_spawn")));

    public static final RegistryObject<SoundEvent> NECOARC_HURT = REGISTRY.register("necoarc_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "necoarc_hurt")));

    public static final RegistryObject<SoundEvent> NECOARC_IDLE = REGISTRY.register("necoarc_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "necoarc_idle")));

    public static final RegistryObject<SoundEvent> WHITEFACE_AMBIENCE = REGISTRY.register("whiteface_ambience", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "whiteface_ambience")));

    public static final RegistryObject<SoundEvent> WHITEFACE_LAUGH = REGISTRY.register("whiteface_laugh", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "whiteface_laugh")));

    public static final RegistryObject<SoundEvent> MICHAEL_SPOTTED = REGISTRY.register("michael_spotted", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "michael_spotted")));

    public static final RegistryObject<SoundEvent> MICHAEL_DEATH = REGISTRY.register("michael_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "michael_death")));

    public static final RegistryObject<SoundEvent> WHITEFACE_SCREAM = REGISTRY.register("whiteface_scream", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "whiteface_scream")));

    public static final RegistryObject<SoundEvent> SPRINGTRAP_IDLE = REGISTRY.register("springtrap_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "springtrap_idle")));

    public static final RegistryObject<SoundEvent> SPRINGTRAP_JUMPSCARE = REGISTRY.register("springtrap_jumpscare", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "springtrap_jumpscare")));

    public static final RegistryObject<SoundEvent> SPRINGTRAP_KILL = REGISTRY.register("springtrap_kill", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "springtrap_kill")));

    public static final RegistryObject<SoundEvent> SPRINGTRAP_SPAWN = REGISTRY.register("springtrap_spawn", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "springtrap_spawn")));

    public static final RegistryObject<SoundEvent> SPRINGTRAP_STEP = REGISTRY.register("springtrap_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "springtrap_step")));

    public static final RegistryObject<SoundEvent> SPRINGTRAP_FIRE = REGISTRY.register("springtrap_fire", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "springtrap_fire")));

    public static final RegistryObject<SoundEvent> SPRINGTRAP_DEATH = REGISTRY.register("springtrap_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "springtrap_death")));

    public static final RegistryObject<SoundEvent> AO_ONI_CHASE = REGISTRY.register("ao_oni_chase", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "ao_oni_chase")));

    public static final RegistryObject<SoundEvent> REXY_STEP = REGISTRY.register("rexy_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "rexy_step")));

    public static final RegistryObject<SoundEvent> REXY_IDLE = REGISTRY.register("rexy_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "rexy_idle")));

    public static final RegistryObject<SoundEvent> REXY_ROAR = REGISTRY.register("rexy_roar", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "rexy_roar")));

    public static final RegistryObject<SoundEvent> REXY_DEATH = REGISTRY.register("rexy_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "rexy_death")));

    public static final RegistryObject<SoundEvent> REXY_HURT = REGISTRY.register("rexy_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "rexy_hurt")));

    public static final RegistryObject<SoundEvent> SIRENHEAD_SIREN = REGISTRY.register("sirenhead_siren", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "sirenhead_siren")));

    public static final RegistryObject<SoundEvent> EXE_SQUELCH = REGISTRY.register("exe_squelch", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "exe_squelch")));

    public static final RegistryObject<SoundEvent> EXE_KILLS = REGISTRY.register("exe_kills", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "exe_kills")));

    public static final RegistryObject<SoundEvent> EXE_TELEPORT = REGISTRY.register("exe_teleport", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "exe_teleport")));

    public static final RegistryObject<SoundEvent> EXE_STATIC = REGISTRY.register("exe_static", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "exe_static")));

    public static final RegistryObject<SoundEvent> EXE_DESPAWN = REGISTRY.register("exe_despawn", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "exe_despawn")));

    public static final RegistryObject<SoundEvent> MONITOR_BREAK = REGISTRY.register("monitor_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "monitor_break")));

    public static final RegistryObject<SoundEvent> SIX_OYE = REGISTRY.register("six_oye", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "six_oye")));

    public static final RegistryObject<SoundEvent> RAKE_SCREAM = REGISTRY.register("rake_scream", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "rake_scream")));

    public static final RegistryObject<SoundEvent> MOTHMAN_IDLE = REGISTRY.register("mothman_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "mothman_idle")));

    public static final RegistryObject<SoundEvent> MOTHMAN_AGGRO = REGISTRY.register("mothman_aggro", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "mothman_aggro")));

    public static final RegistryObject<SoundEvent> RAATMA_IDLE = REGISTRY.register("raatma_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "raatma_idle")));

    public static final RegistryObject<SoundEvent> RAATMA_DEATH = REGISTRY.register("raatma_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "raatma_death")));

    public static final RegistryObject<SoundEvent> RAATMA_ATTACK = REGISTRY.register("raatma_attack", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "raatma_attack")));

    public static final RegistryObject<SoundEvent> RAATMA_HURT = REGISTRY.register("raatma_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "raatma_hurt")));

    public static final RegistryObject<SoundEvent> RAATMA_SCREAM = REGISTRY.register("raatma_scream", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "raatma_scream")));

    public static final RegistryObject<SoundEvent> GASTER_AMBIENCE = REGISTRY.register("gaster_ambience", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "gaster_ambience")));

    public static final RegistryObject<SoundEvent> GASTER_DISAPEAR = REGISTRY.register("gaster_disapear", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "gaster_disapear")));

    public static final RegistryObject<SoundEvent> GASTER_LAUGH = REGISTRY.register("gaster_laugh", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "gaster_laugh")));

    public static final RegistryObject<SoundEvent> MOTHMAN_FLY = REGISTRY.register("mothman_fly", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "mothman_fly")));

    public static final RegistryObject<SoundEvent> MOTHMAN_LAND = REGISTRY.register("mothman_land", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "mothman_land")));

    public static final RegistryObject<SoundEvent> SEEDEATER_IDLE = REGISTRY.register("seedeater_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "seedeater_idle")));

    public static final RegistryObject<SoundEvent> SEEDEATER_HURT = REGISTRY.register("seedeater_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "seedeater_hurt")));

    public static final RegistryObject<SoundEvent> SEEDEATER_DEATH = REGISTRY.register("seedeater_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "seedeater_death")));

    public static final RegistryObject<SoundEvent> SH_SIREN = REGISTRY.register("sh_siren", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "sh_siren")));

    public static final RegistryObject<SoundEvent> RAYGUN = REGISTRY.register("raygun", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "raygun")));

    public static final RegistryObject<SoundEvent> GRAY_IDLE = REGISTRY.register("gray_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "gray_idle")));

    public static final RegistryObject<SoundEvent> GRAY_HURT = REGISTRY.register("gray_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "gray_hurt")));

    public static final RegistryObject<SoundEvent> GRAY_DEATH = REGISTRY.register("gray_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "gray_death")));

    public static final RegistryObject<SoundEvent> MOTHER_SHIP_IDLE = REGISTRY.register("mother_ship_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "mother_ship_idle")));

    public static final RegistryObject<SoundEvent> MOTHER_SHIP_LOOP = REGISTRY.register("mother_ship_loop", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "mother_ship_loop")));

    public static final RegistryObject<SoundEvent> INVASION = REGISTRY.register("invasion", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "invasion")));

    public static final RegistryObject<SoundEvent> SOTIRIS_STINGER = REGISTRY.register("sotiris_stinger", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "sotiris_stinger")));

    public static final RegistryObject<SoundEvent> SH_STATIC = REGISTRY.register("sh_static", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "sh_static")));

    public static final RegistryObject<SoundEvent> KNOCK = REGISTRY.register("knock", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "knock")));

    public static final RegistryObject<SoundEvent> TV_ON = REGISTRY.register("tv_on", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "tv_on")));

    public static final RegistryObject<SoundEvent> TV_STATIC = REGISTRY.register("tv_static", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "tv_static")));

    public static final RegistryObject<SoundEvent> TV_OFF = REGISTRY.register("tv_off", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "tv_off")));

    public static final RegistryObject<SoundEvent> FRESNO_IDLE = REGISTRY.register("fresno_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "fresno_idle")));

    public static final RegistryObject<SoundEvent> FRESNO_HURT = REGISTRY.register("fresno_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "fresno_hurt")));

    public static final RegistryObject<SoundEvent> FRESNO_DEATH = REGISTRY.register("fresno_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "fresno_death")));

    public static final RegistryObject<SoundEvent> SIMON_OST = REGISTRY.register("simon_ost", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "simon_ost")));

    public static final RegistryObject<SoundEvent> MYERS_IDLE = REGISTRY.register("myers_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "myers_idle")));

    public static final RegistryObject<SoundEvent> MYERS_SWING = REGISTRY.register("myers_swing", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "myers_swing")));

    public static final RegistryObject<SoundEvent> MYERS_DEATH = REGISTRY.register("myers_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "myers_death")));

    public static final RegistryObject<SoundEvent> MYERS_CHARGE = REGISTRY.register("myers_charge", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "myers_charge")));

    public static final RegistryObject<SoundEvent> MYERS_STING = REGISTRY.register("myers_sting", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "myers_sting")));

    public static final RegistryObject<SoundEvent> MYERS_CHASE = REGISTRY.register("myers_chase", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "myers_chase")));

    public static final RegistryObject<SoundEvent> WENDIGO_IDLE = REGISTRY.register("wendigo_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "wendigo_idle")));

    public static final RegistryObject<SoundEvent> WENDIGO_DEATH = REGISTRY.register("wendigo_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "wendigo_death")));

    public static final RegistryObject<SoundEvent> WENDIGO_HURT = REGISTRY.register("wendigo_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "wendigo_hurt")));

    public static final RegistryObject<SoundEvent> NAMESIS_ROAR = REGISTRY.register("namesis_roar", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "namesis_roar")));

    public static final RegistryObject<SoundEvent> NEMESIS_IDLE = REGISTRY.register("nemesis_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "nemesis_idle")));

    public static final RegistryObject<SoundEvent> NEMESIS_ATTACK = REGISTRY.register("nemesis_attack", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "nemesis_attack")));

    public static final RegistryObject<SoundEvent> NEMESIS_HURT = REGISTRY.register("nemesis_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "nemesis_hurt")));

    public static final RegistryObject<SoundEvent> NEMESIS_DEATH = REGISTRY.register("nemesis_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "nemesis_death")));

    public static final RegistryObject<SoundEvent> NEMESIS_STARS = REGISTRY.register("nemesis_stars", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "nemesis_stars")));

    public static final RegistryObject<SoundEvent> JASON_KIKIKI = REGISTRY.register("jason_kikiki", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "jason_kikiki")));

    public static final RegistryObject<SoundEvent> BIG_DADDY_IDLE = REGISTRY.register("big_daddy_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "big_daddy_idle")));

    public static final RegistryObject<SoundEvent> BIG_DADDY_MELEE = REGISTRY.register("big_daddy_melee", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "big_daddy_melee")));

    public static final RegistryObject<SoundEvent> BIG_DADDY_HURT = REGISTRY.register("big_daddy_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "big_daddy_hurt")));

    public static final RegistryObject<SoundEvent> BIG_DADDY_DEATH = REGISTRY.register("big_daddy_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "big_daddy_death")));

    public static final RegistryObject<SoundEvent> BIG_DADDY_THREATEN = REGISTRY.register("big_daddy_threaten", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "big_daddy_threaten")));

    public static final RegistryObject<SoundEvent> BIG_DADDY_STEP = REGISTRY.register("big_daddy_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "big_daddy_step")));

    public static final RegistryObject<SoundEvent> GOJI_ROAR = REGISTRY.register("goji_roar", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "goji_roar")));

    public static final RegistryObject<SoundEvent> GOJI_BUILDUP = REGISTRY.register("goji_buildup", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "goji_buildup")));

    public static final RegistryObject<SoundEvent> GOJI_BREATH = REGISTRY.register("goji_breath", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "goji_breath")));

    public static final RegistryObject<SoundEvent> LITTLE_SISTER_IDLE = REGISTRY.register("little_sister_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "little_sister_idle")));

    public static final RegistryObject<SoundEvent> LITTLE_SISTER_ALONE = REGISTRY.register("little_sister_alone", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "little_sister_alone")));

    public static final RegistryObject<SoundEvent> LITTLE_SISTER_HURT = REGISTRY.register("little_sister_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "little_sister_hurt")));

    public static final RegistryObject<SoundEvent> LITTLE_SISTER_HURT_NEAR_BD = REGISTRY.register("little_sister_hurt_near_bd", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "little_sister_hurt_near_bd")));

    public static final RegistryObject<SoundEvent> LITTLE_SISTER_BD_DIES = REGISTRY.register("little_sister_bd_dies", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "little_sister_bd_dies")));

    public static final RegistryObject<SoundEvent> LITTLE_SISTER_SPOT_PLAYER = REGISTRY.register("little_sister_spot_player", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "little_sister_spot_player")));

    public static final RegistryObject<SoundEvent> LITTLE_SISTER_AGGRO = REGISTRY.register("little_sister_aggro", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "little_sister_aggro")));

    public static final RegistryObject<SoundEvent> LITTLE_SISTER_MULTIPLE = REGISTRY.register("little_sister_multiple", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "little_sister_multiple")));

    public static final RegistryObject<SoundEvent> LITTLE_SISTER_CRY = REGISTRY.register("little_sister_cry", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "little_sister_cry")));

    public static final RegistryObject<SoundEvent> LITTLE_SISTER_BD_KILLS = REGISTRY.register("little_sister_bd_kills", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "little_sister_bd_kills")));

    public static final RegistryObject<SoundEvent> LIGHTHEAD_IDLE = REGISTRY.register("lighthead_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "lighthead_idle")));

    public static final RegistryObject<SoundEvent> GRANNY = REGISTRY.register("granny", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "granny")));

    public static final RegistryObject<SoundEvent> DEER_IDLE = REGISTRY.register("deer_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "deer_idle")));

    public static final RegistryObject<SoundEvent> DEER_DEATH = REGISTRY.register("deer_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "deer_death")));

    public static final RegistryObject<SoundEvent> DEER_HURT = REGISTRY.register("deer_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "deer_hurt")));

    public static final RegistryObject<SoundEvent> SIRENPHONE = REGISTRY.register("sirenphone", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "sirenphone")));

    public static final RegistryObject<SoundEvent> SADAKO_24 = REGISTRY.register("sadako_24", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "sadako_24")));

    public static final RegistryObject<SoundEvent> SADAKO_DEATH = REGISTRY.register("sadako_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "sadako_death")));

    public static final RegistryObject<SoundEvent> SADAKO_IDLE = REGISTRY.register("sadako_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "sadako_idle")));

    public static final RegistryObject<SoundEvent> SADAKO_KILL = REGISTRY.register("sadako_kill", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "sadako_kill")));

    public static final RegistryObject<SoundEvent> SADAKO_TV = REGISTRY.register("sadako_tv", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "sadako_tv")));

    public static final RegistryObject<SoundEvent> TV_PUT_TAPE = REGISTRY.register("tv_put_tape", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "tv_put_tape")));

    public static final RegistryObject<SoundEvent> SPECIMEN_9_BOSS = REGISTRY.register("specimen_9_boss", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "specimen_9_boss")));

    public static final RegistryObject<SoundEvent> SCREAMPILLAR = REGISTRY.register("screampillar", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "screampillar")));

    public static final RegistryObject<SoundEvent> DAMAGE_SPOOKY = REGISTRY.register("damage_spooky", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "damage_spooky")));

    public static final RegistryObject<SoundEvent> LEVEL_0_OST = REGISTRY.register("level_0_ost", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "level_0_ost")));

    public static final RegistryObject<SoundEvent> LEVEL_0_SOUNDS = REGISTRY.register("level_0_sounds", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "level_0_sounds")));

    public static final RegistryObject<SoundEvent> LEVEL_0_MOOD = REGISTRY.register("level_0_mood", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "level_0_mood")));

    public static final RegistryObject<SoundEvent> BEN_SPAWN = REGISTRY.register("ben_spawn", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "ben_spawn")));

    public static final RegistryObject<SoundEvent> TV_BILL = REGISTRY.register("tv_bill", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "tv_bill")));

    public static final RegistryObject<SoundEvent> TV_BILLY = REGISTRY.register("tv_billy", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "tv_billy")));

    public static final RegistryObject<SoundEvent> TV_COURAGE = REGISTRY.register("tv_courage", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "tv_courage")));

    public static final RegistryObject<SoundEvent> TV_COVE = REGISTRY.register("tv_cove", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "tv_cove")));

    public static final RegistryObject<SoundEvent> TV_FLESHPIT = REGISTRY.register("tv_fleshpit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "tv_fleshpit")));

    public static final RegistryObject<SoundEvent> TV_GEMINI = REGISTRY.register("tv_gemini", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "tv_gemini")));

    public static final RegistryObject<SoundEvent> TV_LOCAL58 = REGISTRY.register("tv_local58", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "tv_local58")));

    public static final RegistryObject<SoundEvent> TV_MAX = REGISTRY.register("tv_max", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "tv_max")));

    public static final RegistryObject<SoundEvent> TV_NEEDLE = REGISTRY.register("tv_needle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "tv_needle")));

    public static final RegistryObject<SoundEvent> TV_NOMORE = REGISTRY.register("tv_nomore", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "tv_nomore")));

    public static final RegistryObject<SoundEvent> TV_SHAMROCK = REGISTRY.register("tv_shamrock", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "tv_shamrock")));

    public static final RegistryObject<SoundEvent> TV_WYOMING = REGISTRY.register("tv_wyoming", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "tv_wyoming")));

    public static final RegistryObject<SoundEvent> EXE_LAUGH = REGISTRY.register("exe_laugh", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "exe_laugh")));

    public static final RegistryObject<SoundEvent> EXE_CHASE = REGISTRY.register("exe_chase", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "exe_chase")));

    public static final RegistryObject<SoundEvent> SONICEXE_CHASE = REGISTRY.register("sonicexe_chase", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "sonicexe_chase")));

    public static final RegistryObject<SoundEvent> PAINTED_SWORD_HIT = REGISTRY.register("painted_sword_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "painted_sword_hit")));

    public static final RegistryObject<SoundEvent> PAINTED_SWORD_SWEEP = REGISTRY.register("painted_sword_sweep", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "painted_sword_sweep")));

    public static final RegistryObject<SoundEvent> PAINTED_SWORD_KILL = REGISTRY.register("painted_sword_kill", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "painted_sword_kill")));

    public static final RegistryObject<SoundEvent> EFFIGY_SOUND = REGISTRY.register("effigy_sound", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "effigy_sound")));

    public static final RegistryObject<SoundEvent> SONIC_JUMP = REGISTRY.register("sonic_jump", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "sonic_jump")));

    public static final RegistryObject<SoundEvent> LIFEFORM_STEP = REGISTRY.register("lifeform_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "lifeform_step")));

    public static final RegistryObject<SoundEvent> AGWOB_SHOOT = REGISTRY.register("agwob_shoot", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "agwob_shoot")));

    public static final RegistryObject<SoundEvent> AGWOB_EQUIP = REGISTRY.register("agwob_equip", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "agwob_equip")));

    public static final RegistryObject<SoundEvent> ROLLING_GIANT_ROLL = REGISTRY.register("rolling_giant_roll", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "rolling_giant_roll")));

    public static final RegistryObject<SoundEvent> FRESNO_HAPPY = REGISTRY.register("fresno_happy", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "fresno_happy")));

    public static final RegistryObject<SoundEvent> CHUCKY_ATTACK = REGISTRY.register("chucky_attack", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "chucky_attack")));

    public static final RegistryObject<SoundEvent> CHUCKY_CHASE = REGISTRY.register("chucky_chase", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "chucky_chase")));

    public static final RegistryObject<SoundEvent> CHUCKY_DEATH = REGISTRY.register("chucky_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "chucky_death")));

    public static final RegistryObject<SoundEvent> CHUCKY_GRAB = REGISTRY.register("chucky_grab", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "chucky_grab")));

    public static final RegistryObject<SoundEvent> CHUCKY_HURT = REGISTRY.register("chucky_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "chucky_hurt")));

    public static final RegistryObject<SoundEvent> CHUCKY_SPAWN = REGISTRY.register("chucky_spawn", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "chucky_spawn")));

    public static final RegistryObject<SoundEvent> DRILL_DASH = REGISTRY.register("drill_dash", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "drill_dash")));

    public static final RegistryObject<SoundEvent> RABBIDS = REGISTRY.register("rabbids", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "rabbids")));

    public static final RegistryObject<SoundEvent> STOPSIGN_HIT = REGISTRY.register("stopsign_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "stopsign_hit")));

    public static final RegistryObject<SoundEvent> STOPSIGN_EQUIP = REGISTRY.register("stopsign_equip", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "stopsign_equip")));

    public static final RegistryObject<SoundEvent> STOPSIGN_CHARGE = REGISTRY.register("stopsign_charge", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "stopsign_charge")));

    public static final RegistryObject<SoundEvent> STOPSIGN_SWING = REGISTRY.register("stopsign_swing", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "stopsign_swing")));

    public static final RegistryObject<SoundEvent> CHAINSAW_LOOP = REGISTRY.register("chainsaw_loop", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "chainsaw_loop")));

    public static final RegistryObject<SoundEvent> CHAINSAW_REV_1 = REGISTRY.register("chainsaw_rev_1", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "chainsaw_rev_1")));

    public static final RegistryObject<SoundEvent> CHAINSAW_REV_2 = REGISTRY.register("chainsaw_rev_2", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "chainsaw_rev_2")));

    public static final RegistryObject<SoundEvent> CHAINSAW_REV_3 = REGISTRY.register("chainsaw_rev_3", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "chainsaw_rev_3")));

    public static final RegistryObject<SoundEvent> CHAINSAW_REV_4 = REGISTRY.register("chainsaw_rev_4", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "chainsaw_rev_4")));

    public static final RegistryObject<SoundEvent> CHAINSAW_STALL = REGISTRY.register("chainsaw_stall", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "chainsaw_stall")));

    public static final RegistryObject<SoundEvent> FREDDY_LULLABY = REGISTRY.register("freddy_lullaby", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "freddy_lullaby")));

    public static final RegistryObject<SoundEvent> FREDDY_CHASE = REGISTRY.register("freddy_chase", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "freddy_chase")));

    public static final RegistryObject<SoundEvent> FREDDY_DEATH = REGISTRY.register("freddy_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "freddy_death")));

    public static final RegistryObject<SoundEvent> FREDDY_HURT = REGISTRY.register("freddy_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "freddy_hurt")));

    public static final RegistryObject<SoundEvent> FREDDY_LAUGH = REGISTRY.register("freddy_laugh", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "freddy_laugh")));

    public static final RegistryObject<SoundEvent> TV_BOILED = REGISTRY.register("tv_boiled", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "tv_boiled")));

    public static final RegistryObject<SoundEvent> BOILED_ONE_TRUMPET = REGISTRY.register("boiled_one_trumpet", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "boiled_one_trumpet")));

    public static final RegistryObject<SoundEvent> BOILED_ONE_SPEAK = REGISTRY.register("boiled_one_speak", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "boiled_one_speak")));

    public static final RegistryObject<SoundEvent> PHANTOM_BB_SPAWN = REGISTRY.register("phantom_bb_spawn", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "phantom_bb_spawn")));

    public static final RegistryObject<SoundEvent> PHANTOM_BB_LAUGH = REGISTRY.register("phantom_bb_laugh", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "phantom_bb_laugh")));

    public static final RegistryObject<SoundEvent> PHANTOM_MANGLE = REGISTRY.register("phantom_mangle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "phantom_mangle")));

    public static final RegistryObject<SoundEvent> PHANTOM_PUPPET = REGISTRY.register("phantom_puppet", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "phantom_puppet")));

    public static final RegistryObject<SoundEvent> JASON_RESSURECT = REGISTRY.register("jason_ressurect", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "jason_ressurect")));

    public static final RegistryObject<SoundEvent> GHOST_IDLE = REGISTRY.register("ghost_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "ghost_idle")));

    public static final RegistryObject<SoundEvent> GHOST_HURT = REGISTRY.register("ghost_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "ghost_hurt")));

    public static final RegistryObject<SoundEvent> GHOST_DEATH = REGISTRY.register("ghost_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "ghost_death")));

    public static final RegistryObject<SoundEvent> VAMPIRE_IDLE = REGISTRY.register("vampire_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "vampire_idle")));

    public static final RegistryObject<SoundEvent> VAMPIRE_HURT = REGISTRY.register("vampire_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "vampire_hurt")));

    public static final RegistryObject<SoundEvent> VAMPIRE_DEATH = REGISTRY.register("vampire_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "vampire_death")));

    public static final RegistryObject<SoundEvent> DEMON_IDLE = REGISTRY.register("demon_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "demon_idle")));

    public static final RegistryObject<SoundEvent> DEMON_HURT = REGISTRY.register("demon_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "demon_hurt")));

    public static final RegistryObject<SoundEvent> DEMON_DEATH = REGISTRY.register("demon_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "demon_death")));

    public static final RegistryObject<SoundEvent> BENDY_SCREAM = REGISTRY.register("bendy_scream", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "bendy_scream")));

    public static final RegistryObject<SoundEvent> BENDY_STEP = REGISTRY.register("bendy_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "bendy_step")));

    public static final RegistryObject<SoundEvent> BENDY_IDLE = REGISTRY.register("bendy_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "bendy_idle")));

    public static final RegistryObject<SoundEvent> BENDY_HURT = REGISTRY.register("bendy_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "bendy_hurt")));

    public static final RegistryObject<SoundEvent> PREDATOR_DISTRACTION = REGISTRY.register("predator_distraction", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "predator_distraction")));

    public static final RegistryObject<SoundEvent> PREDATOR_CLOAK = REGISTRY.register("predator_cloak", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "predator_cloak")));

    public static final RegistryObject<SoundEvent> PREDATOR_CONSOLE = REGISTRY.register("predator_console", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "predator_console")));

    public static final RegistryObject<SoundEvent> PREDATOR_DEATH = REGISTRY.register("predator_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "predator_death")));

    public static final RegistryObject<SoundEvent> PREDATOR_DECLOAK_WATER = REGISTRY.register("predator_decloak_water", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "predator_decloak_water")));

    public static final RegistryObject<SoundEvent> PREDATOR_HURT = REGISTRY.register("predator_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "predator_hurt")));

    public static final RegistryObject<SoundEvent> PREDATOR_IDLE = REGISTRY.register("predator_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "predator_idle")));

    public static final RegistryObject<SoundEvent> PREDATOR_KILL = REGISTRY.register("predator_kill", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "predator_kill")));

    public static final RegistryObject<SoundEvent> PREDATOR_SCAN = REGISTRY.register("predator_scan", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "predator_scan")));

    public static final RegistryObject<SoundEvent> PREDATOR_STEP = REGISTRY.register("predator_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "predator_step")));

    public static final RegistryObject<SoundEvent> PREDATOR_UNCLOAK = REGISTRY.register("predator_uncloak", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "predator_uncloak")));

    public static final RegistryObject<SoundEvent> PREDATOR_SWING = REGISTRY.register("predator_swing", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "predator_swing")));

    public static final RegistryObject<SoundEvent> PREDATOR_HURT_ALIEN = REGISTRY.register("predator_hurt_alien", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "predator_hurt_alien")));

    public static final RegistryObject<SoundEvent> PREDATOR_HIT = REGISTRY.register("predator_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "predator_hit")));

    public static final RegistryObject<SoundEvent> PREDATOR_CASTER_CONFIRM = REGISTRY.register("predator_caster_confirm", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "predator_caster_confirm")));

    public static final RegistryObject<SoundEvent> PREDATOR_CASTER_EXPLOSION = REGISTRY.register("predator_caster_explosion", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "predator_caster_explosion")));

    public static final RegistryObject<SoundEvent> PREDATOR_CASTER_LOCKON = REGISTRY.register("predator_caster_lockon", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "predator_caster_lockon")));

    public static final RegistryObject<SoundEvent> PREDATOR_CASTER_SHOOT = REGISTRY.register("predator_caster_shoot", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "predator_caster_shoot")));

    public static final RegistryObject<SoundEvent> PREDATOR_CASTER_TRACKING = REGISTRY.register("predator_caster_tracking", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "predator_caster_tracking")));

    public static final RegistryObject<SoundEvent> ANGLER_ATTACK = REGISTRY.register("angler_attack", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "angler_attack")));

    public static final RegistryObject<SoundEvent> ANGLER_KILL = REGISTRY.register("angler_kill", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "angler_kill")));

    public static final RegistryObject<SoundEvent> REXY_ROAR_NOVEL = REGISTRY.register("rexy_roar_novel", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "rexy_roar_novel")));

    public static final RegistryObject<SoundEvent> REXY_ATTACK = REGISTRY.register("rexy_attack", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "rexy_attack")));

    public static final RegistryObject<SoundEvent> REX_ROAR = REGISTRY.register("rex_roar", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "rex_roar")));

    public static final RegistryObject<SoundEvent> COMPUTER_COMPLETE = REGISTRY.register("computer_complete", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "computer_complete")));

    public static final RegistryObject<SoundEvent> COMPUTER_LOAD = REGISTRY.register("computer_load", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "computer_load")));

    public static final RegistryObject<SoundEvent> COMPUTER_LOADFAIL = REGISTRY.register("computer_loadfail", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "computer_loadfail")));

    public static final RegistryObject<SoundEvent> COMPUTER_MAIL = REGISTRY.register("computer_mail", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "computer_mail")));

    public static final RegistryObject<SoundEvent> SIRENHEAD_SONICBOOM = REGISTRY.register("sirenhead_sonicboom", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "sirenhead_sonicboom")));

    public static final RegistryObject<SoundEvent> TINKY_HURT = REGISTRY.register("tinky_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "tinky_hurt")));

    public static final RegistryObject<SoundEvent> TINKY_DEATH = REGISTRY.register("tinky_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "tinky_death")));

    public static final RegistryObject<SoundEvent> TINKYWINKY_JUMPSCARE = REGISTRY.register("tinkywinky_jumpscare", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "tinkywinky_jumpscare")));

    public static final RegistryObject<SoundEvent> TINKYWINKY_CHASE = REGISTRY.register("tinkywinky_chase", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "tinkywinky_chase")));

    public static final RegistryObject<SoundEvent> TINKYWINKY_ATTACK = REGISTRY.register("tinkywinky_attack", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "tinkywinky_attack")));

    public static final RegistryObject<SoundEvent> STEPHANO_KILL = REGISTRY.register("stephano_kill", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "stephano_kill")));

    public static final RegistryObject<SoundEvent> BALDI_RULER = REGISTRY.register("baldi_ruler", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "baldi_ruler")));

    public static final RegistryObject<SoundEvent> JUMPSCARE_BALDI = REGISTRY.register("jumpscare_baldi", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "jumpscare_baldi")));

    public static final RegistryObject<SoundEvent> HOLY_WATER_THROW = REGISTRY.register("holy_water_throw", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "holy_water_throw")));

    public static final RegistryObject<SoundEvent> HOLY_WATER_SPLASH = REGISTRY.register("holy_water_splash", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "holy_water_splash")));

    public static final RegistryObject<SoundEvent> BALDI_IDLE = REGISTRY.register("baldi_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "baldi_idle")));

    public static final RegistryObject<SoundEvent> BALDI_HURT = REGISTRY.register("baldi_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "baldi_hurt")));

    public static final RegistryObject<SoundEvent> BALDI_AGGRO = REGISTRY.register("baldi_aggro", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "baldi_aggro")));

    public static final RegistryObject<SoundEvent> BALDI_DEATH = REGISTRY.register("baldi_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "baldi_death")));

    public static final RegistryObject<SoundEvent> CARTOONCAT_JUMPSCARE = REGISTRY.register("cartooncat_jumpscare", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "cartooncat_jumpscare")));

    public static final RegistryObject<SoundEvent> FIGURE_IDLE = REGISTRY.register("figure_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "figure_idle")));

    public static final RegistryObject<SoundEvent> FIGURE_AGGRO = REGISTRY.register("figure_aggro", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "figure_aggro")));

    public static final RegistryObject<SoundEvent> FIGURE_KILL = REGISTRY.register("figure_kill", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "figure_kill")));

    public static final RegistryObject<SoundEvent> FIGURE_STEP = REGISTRY.register("figure_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "figure_step")));

    public static final RegistryObject<SoundEvent> FIGURE_HURT = REGISTRY.register("figure_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "figure_hurt")));

    public static final RegistryObject<SoundEvent> FIGURE_DEATH = REGISTRY.register("figure_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "figure_death")));

    public static final RegistryObject<SoundEvent> FIGURE_SCREAM = REGISTRY.register("figure_scream", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "figure_scream")));

    public static final RegistryObject<SoundEvent> GASTER_BLASTER_SUMMON = REGISTRY.register("gaster_blaster_summon", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "gaster_blaster_summon")));

    public static final RegistryObject<SoundEvent> GASTER_BLASTER_FIRE = REGISTRY.register("gaster_blaster_fire", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "gaster_blaster_fire")));

    public static final RegistryObject<SoundEvent> HORROR_SANS_ACTION = REGISTRY.register("horror_sans_action", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "horror_sans_action")));

    public static final RegistryObject<SoundEvent> HORROR_SANS_DEATH = REGISTRY.register("horror_sans_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "horror_sans_death")));

    public static final RegistryObject<SoundEvent> HORROR_SANS_TP = REGISTRY.register("horror_sans_tp", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "horror_sans_tp")));

    public static final RegistryObject<SoundEvent> REND_SLASH = REGISTRY.register("rend_slash", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "rend_slash")));

    public static final RegistryObject<SoundEvent> CARTOONCAT_CHASE = REGISTRY.register("cartooncat_chase", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "cartooncat_chase")));

    public static final RegistryObject<SoundEvent> GRAY_OST = REGISTRY.register("gray_ost", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "gray_ost")));

    public static final RegistryObject<SoundEvent> JEFF_LAUGH = REGISTRY.register("jeff_laugh", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "jeff_laugh")));

    public static final RegistryObject<SoundEvent> CARTOON_CAT_DEATH = REGISTRY.register("cartoon_cat_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "cartoon_cat_death")));

    public static final RegistryObject<SoundEvent> CARTOON_CAT_IDLE = REGISTRY.register("cartoon_cat_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "cartoon_cat_idle")));

    public static final RegistryObject<SoundEvent> SANS_OST = REGISTRY.register("sans_ost", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "sans_ost")));

    public static final RegistryObject<SoundEvent> STEPHANO_EQUIP = REGISTRY.register("stephano_equip", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "stephano_equip")));

    public static final RegistryObject<SoundEvent> X1_SPAWN = REGISTRY.register("x1_spawn", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "x1_spawn")));

    public static final RegistryObject<SoundEvent> X1_DEATH = REGISTRY.register("x1_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "x1_death")));

    public static final RegistryObject<SoundEvent> X1_IDLE = REGISTRY.register("x1_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "x1_idle")));

    public static final RegistryObject<SoundEvent> X1_KILL = REGISTRY.register("x1_kill", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "x1_kill")));

    public static final RegistryObject<SoundEvent> X1_SLASH = REGISTRY.register("x1_slash", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "x1_slash")));

    public static final RegistryObject<SoundEvent> VITA_MIMIC_DEATH = REGISTRY.register("vita_mimic_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "vita_mimic_death")));

    public static final RegistryObject<SoundEvent> VITA_MIMIC_HURT = REGISTRY.register("vita_mimic_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "vita_mimic_hurt")));

    public static final RegistryObject<SoundEvent> VITA_MIMIC_IDLE = REGISTRY.register("vita_mimic_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "vita_mimic_idle")));

    public static final RegistryObject<SoundEvent> TRIMMING_IDLE = REGISTRY.register("trimming_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "trimming_idle")));

    public static final RegistryObject<SoundEvent> TRIMMING_HURT = REGISTRY.register("trimming_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "trimming_hurt")));

    public static final RegistryObject<SoundEvent> TRIMMING_DEATH = REGISTRY.register("trimming_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "trimming_death")));

    public static final RegistryObject<SoundEvent> SUBJECT3_CHASE = REGISTRY.register("subject3_chase", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "subject3_chase")));

    public static final RegistryObject<SoundEvent> MX_IDLE = REGISTRY.register("mx_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "mx_idle")));

    public static final RegistryObject<SoundEvent> PASTA_NIGHT = REGISTRY.register("pasta_night", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "pasta_night")));

    public static final RegistryObject<SoundEvent> MYERS_HURT = REGISTRY.register("myers_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "myers_hurt")));

    public static final RegistryObject<SoundEvent> HYPNO_IDLE = REGISTRY.register("hypno_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "hypno_idle")));

    public static final RegistryObject<SoundEvent> HYPNO_DEATH = REGISTRY.register("hypno_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "hypno_death")));

    public static final RegistryObject<SoundEvent> BATEMAN_ATTACK = REGISTRY.register("bateman_attack", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "bateman_attack")));

    public static final RegistryObject<SoundEvent> BATEMAN_KILL = REGISTRY.register("bateman_kill", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "bateman_kill")));

    public static final RegistryObject<SoundEvent> BATEMAN_SPAWN = REGISTRY.register("bateman_spawn", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "bateman_spawn")));

    public static final RegistryObject<SoundEvent> LAGHINGJACK_IDLE = REGISTRY.register("laghingjack_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "laghingjack_idle")));

    public static final RegistryObject<SoundEvent> LAGHINGJACK_AMBIENCE = REGISTRY.register("laghingjack_ambience", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "laghingjack_ambience")));

    public static final RegistryObject<SoundEvent> LAUGHINGJACK_IDLE = REGISTRY.register("laughingjack_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "laughingjack_idle")));

    public static final RegistryObject<SoundEvent> KRASUE_IDLE = REGISTRY.register("krasue_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "krasue_idle")));

    public static final RegistryObject<SoundEvent> KRASUE_SCREAM = REGISTRY.register("krasue_scream", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "krasue_scream")));

    public static final RegistryObject<SoundEvent> MX_JUMP = REGISTRY.register("mx_jump", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "mx_jump")));

    public static final RegistryObject<SoundEvent> TAILSDOLL_JUMPSCARE = REGISTRY.register("tailsdoll_jumpscare", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "tailsdoll_jumpscare")));

    public static final RegistryObject<SoundEvent> SUICIDEMOUSE_JUMPSCARE = REGISTRY.register("suicidemouse_jumpscare", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "suicidemouse_jumpscare")));

    public static final RegistryObject<SoundEvent> SQUIDWARD_REDMIST = REGISTRY.register("squidward_redmist", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "squidward_redmist")));

    public static final RegistryObject<SoundEvent> SHOTGUN_SHOOT = REGISTRY.register("shotgun_shoot", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "shotgun_shoot")));

    public static final RegistryObject<SoundEvent> SHOTGUN_COCKING = REGISTRY.register("shotgun_cocking", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "shotgun_cocking")));

    public static final RegistryObject<SoundEvent> MUSIC_DISC_DAY = REGISTRY.register("music_disc_day", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "music_disc_day")));

    public static final RegistryObject<SoundEvent> MUSIC_DISC_LAVENDER = REGISTRY.register("music_disc_lavender", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "music_disc_lavender")));

    public static final RegistryObject<SoundEvent> MUSIC_DISC_PROMISE = REGISTRY.register("music_disc_promise", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "music_disc_promise")));

    public static final RegistryObject<SoundEvent> MUSIC_DISC_SONIC_CD_BOSS = REGISTRY.register("music_disc_sonic_cd_boss", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "music_disc_sonic_cd_boss")));

    public static final RegistryObject<SoundEvent> MUSIC_DISC_TALLYHALL = REGISTRY.register("music_disc_tallyhall", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "music_disc_tallyhall")));

    public static final RegistryObject<SoundEvent> MUSIC_DISC_TOUCHTONE = REGISTRY.register("music_disc_touchtone", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "music_disc_touchtone")));

    public static final RegistryObject<SoundEvent> MUSIC_DISC_WHITENOIZ = REGISTRY.register("music_disc_whitenoiz", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "music_disc_whitenoiz")));

    public static final RegistryObject<SoundEvent> MUSIC_DISC_MX = REGISTRY.register("music_disc_mx", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "music_disc_mx")));

    public static final RegistryObject<SoundEvent> CHASE_MX = REGISTRY.register("chase_mx", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "chase_mx")));

    public static final RegistryObject<SoundEvent> CAMERA_OBSCURA = REGISTRY.register("camera_obscura", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "camera_obscura")));

    public static final RegistryObject<SoundEvent> WF_PISTOL = REGISTRY.register("wf_pistol", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "wf_pistol")));

    public static final RegistryObject<SoundEvent> BLOODWAVE = REGISTRY.register("bloodwave", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "bloodwave")));

    public static final RegistryObject<SoundEvent> LEATHERFACE_HURT = REGISTRY.register("leatherface_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "leatherface_hurt")));

    public static final RegistryObject<SoundEvent> LEATHERFACE_DEATH = REGISTRY.register("leatherface_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "leatherface_death")));

    public static final RegistryObject<SoundEvent> LEATHERFACE_ATTACK = REGISTRY.register("leatherface_attack", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "leatherface_attack")));

    public static final RegistryObject<SoundEvent> LEATHERFACE_IDLE = REGISTRY.register("leatherface_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "leatherface_idle")));

    public static final RegistryObject<SoundEvent> SQUIDWARD_STEP = REGISTRY.register("squidward_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "squidward_step")));

    public static final RegistryObject<SoundEvent> GHOSTFACE_KNIFE_ACTIVATE = REGISTRY.register("ghostface_knife_activate", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "ghostface_knife_activate")));

    public static final RegistryObject<SoundEvent> JASON_STINGER = REGISTRY.register("jason_stinger", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "jason_stinger")));

    public static final RegistryObject<SoundEvent> MYERS_STINGER = REGISTRY.register("myers_stinger", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "myers_stinger")));

    public static final RegistryObject<SoundEvent> RIFT_OPEN = REGISTRY.register("rift_open", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "rift_open")));

    public static final RegistryObject<SoundEvent> MUSIC_DISC_SMASHING_WINDSHIELDS = REGISTRY.register("music_disc_smashing_windshields", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "music_disc_smashing_windshields")));

    public static final RegistryObject<SoundEvent> MUSIC_DISC_BOYS = REGISTRY.register("music_disc_boys", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "music_disc_boys")));

    public static final RegistryObject<SoundEvent> MIMICRY_HIT = REGISTRY.register("mimicry_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "mimicry_hit")));

    public static final RegistryObject<SoundEvent> MIMICRY_DASH = REGISTRY.register("mimicry_dash", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "mimicry_dash")));

    public static final RegistryObject<SoundEvent> SWEEP = REGISTRY.register("sweep", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "sweep")));

    public static final RegistryObject<SoundEvent> MIMICRY_SWEEP = REGISTRY.register("mimicry_sweep", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "mimicry_sweep")));

    public static final RegistryObject<SoundEvent> NOTHING_THERE_IDLE = REGISTRY.register("nothing_there_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "nothing_there_idle")));

    public static final RegistryObject<SoundEvent> NOTHING_THERE_AGGRO = REGISTRY.register("nothing_there_aggro", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "nothing_there_aggro")));

    public static final RegistryObject<SoundEvent> NOTHING_THERE_ATTACK = REGISTRY.register("nothing_there_attack", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "nothing_there_attack")));

    public static final RegistryObject<SoundEvent> NOTHING_THERE_DEATH = REGISTRY.register("nothing_there_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "nothing_there_death")));

    public static final RegistryObject<SoundEvent> NOTHING_THERE_HIT = REGISTRY.register("nothing_there_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "nothing_there_hit")));

    public static final RegistryObject<SoundEvent> NOTHING_THERE_HURT = REGISTRY.register("nothing_there_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "nothing_there_hurt")));

    public static final RegistryObject<SoundEvent> NOTHING_THERE_STEP = REGISTRY.register("nothing_there_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "nothing_there_step")));

    public static final RegistryObject<SoundEvent> CHASE_NOTHING_THERE = REGISTRY.register("chase_nothing_there", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "chase_nothing_there")));

    public static final RegistryObject<SoundEvent> MUSIC_DISC_NEUTRAL01 = REGISTRY.register("music_disc_neutral01", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "music_disc_neutral01")));

    public static final RegistryObject<SoundEvent> STUD_FOOTSTEP = REGISTRY.register("stud_footstep", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "stud_footstep")));

    public static final RegistryObject<SoundEvent> NPC_000_HURT = REGISTRY.register("npc_000_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "npc_000_hurt")));

    public static final RegistryObject<SoundEvent> NOC_000_DEATH = REGISTRY.register("noc_000_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "noc_000_death")));

    public static final RegistryObject<SoundEvent> NPC_000_DEATH = REGISTRY.register("npc_000_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "npc_000_death")));

    public static final RegistryObject<SoundEvent> BASEPLATE_JUMP = REGISTRY.register("baseplate_jump", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "baseplate_jump")));

    public static final RegistryObject<SoundEvent> NPC_000_ROAR = REGISTRY.register("npc_000_roar", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "npc_000_roar")));

    public static final RegistryObject<SoundEvent> NPC_000_NEAR = REGISTRY.register("npc_000_near", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "npc_000_near")));

    public static final RegistryObject<SoundEvent> NPC_000_FAR = REGISTRY.register("npc_000_far", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "npc_000_far")));

    public static final RegistryObject<SoundEvent> FLOWERS_WHISTLE_DAMAGE = REGISTRY.register("flowers_whistle_damage", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "flowers_whistle_damage")));

    public static final RegistryObject<SoundEvent> FLOWERS_FIGHT_OST = REGISTRY.register("flowers_fight_ost", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "flowers_fight_ost")));

    public static final RegistryObject<SoundEvent> FLOWERS_DISC = REGISTRY.register("flowers_disc", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "flowers_disc")));

    public static final RegistryObject<SoundEvent> MUSIC_DISC_HEAVEN_SAYS = REGISTRY.register("music_disc_heaven_says", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "music_disc_heaven_says")));

    public static final RegistryObject<SoundEvent> MUSIC_DISC_ENIGMA = REGISTRY.register("music_disc_enigma", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "music_disc_enigma")));

    public static final RegistryObject<SoundEvent> MUSIC_DISC_CARTOONCAT = REGISTRY.register("music_disc_cartooncat", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "music_disc_cartooncat")));

    public static final RegistryObject<SoundEvent> MUSIC_DISC_HUNGER = REGISTRY.register("music_disc_hunger", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("boh", "music_disc_hunger")));
}
