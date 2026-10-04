package net.mcreator.boh.init;

import net.mcreator.boh.potion.BlackHoleSunEffectMobEffect;
import net.mcreator.boh.potion.BlessedMobEffect;
import net.mcreator.boh.potion.CognitoHazartMobEffect;
import net.mcreator.boh.potion.CoverYourEarsMobEffect;
import net.mcreator.boh.potion.DrowningMobEffect;
import net.mcreator.boh.potion.EffectChuckyGrabMobEffect;
import net.mcreator.boh.potion.EngagedMobEffect;
import net.mcreator.boh.potion.FaceHuggerEffectMobEffect;
import net.mcreator.boh.potion.FromOutOfThisEarthMobEffect;
import net.mcreator.boh.potion.HideAndSeekMobEffect;
import net.mcreator.boh.potion.HystmEffectMobEffect;
import net.mcreator.boh.potion.IntoTheFogMobEffect;
import net.mcreator.boh.potion.JumpscareAnglerMobEffect;
import net.mcreator.boh.potion.JumpscareBaldiMobEffect;
import net.mcreator.boh.potion.JumpscareCartoonCatMobEffect;
import net.mcreator.boh.potion.JumpscareSuicideMouseMobEffect;
import net.mcreator.boh.potion.JumpscareTailsdollMobEffect;
import net.mcreator.boh.potion.JumpscareTinkyMobEffect;
import net.mcreator.boh.potion.LycanthropyMobEffect;
import net.mcreator.boh.potion.MadMobEffect;
import net.mcreator.boh.potion.NPC000influenceMobEffect;
import net.mcreator.boh.potion.ParasitesSongMobEffect;
import net.mcreator.boh.potion.PhatomPuppetBlindnessMobEffect;
import net.mcreator.boh.potion.RedMistMobEffect;
import net.mcreator.boh.potion.RollingGiantEffectMobEffect;
import net.mcreator.boh.potion.SadakoEffectMobEffect;
import net.mcreator.boh.potion.SafeAndSoundMobEffect;
import net.mcreator.boh.potion.ShapesGazeMobEffect;
import net.mcreator.boh.potion.SightOfThePredatorMobEffect;
import net.mcreator.boh.potion.SlenderInfluenceEffectMobEffect;
import net.mcreator.boh.potion.TheWhisleMobEffect;
import net.mcreator.boh.potion.TimerOverlayMobEffect;
import net.mcreator.boh.potion.VampirismMobEffect;
import net.mcreator.boh.potion.WitnessMobEffect;
import net.minecraft.potion.Potion;
import net.mcreator.boh.compat.forge.registries.DeferredRegister;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.forge.registries.RegistryObject;

public class BohModMobEffects {

    public static final DeferredRegister<Potion> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, "boh");

    public static final RegistryObject<Potion> SLENDER_INFLUENCE_EFFECT = REGISTRY.register("slender_influence_effect", () -> new SlenderInfluenceEffectMobEffect());

    public static final RegistryObject<Potion> FACE_HUGGER_EFFECT = REGISTRY.register("face_hugger_effect", () -> new FaceHuggerEffectMobEffect());

    public static final RegistryObject<Potion> ENGAGED = REGISTRY.register("engaged", () -> new EngagedMobEffect());

    public static final RegistryObject<Potion> WITNESS = REGISTRY.register("witness", () -> new WitnessMobEffect());

    public static final RegistryObject<Potion> HIDE_AND_SEEK = REGISTRY.register("hide_and_seek", () -> new HideAndSeekMobEffect());

    public static final RegistryObject<Potion> BLESSED = REGISTRY.register("blessed", () -> new BlessedMobEffect());

    public static final RegistryObject<Potion> INTO_THE_FOG = REGISTRY.register("into_the_fog", () -> new IntoTheFogMobEffect());

    public static final RegistryObject<Potion> MAD = REGISTRY.register("mad", () -> new MadMobEffect());

    public static final RegistryObject<Potion> SHAPES_GAZE = REGISTRY.register("shapes_gaze", () -> new ShapesGazeMobEffect());

    public static final RegistryObject<Potion> SADAKO_EFFECT = REGISTRY.register("sadako_effect", () -> new SadakoEffectMobEffect());

    public static final RegistryObject<Potion> DROWNING = REGISTRY.register("drowning", () -> new DrowningMobEffect());

    public static final RegistryObject<Potion> ROLLING_GIANT_EFFECT = REGISTRY.register("rolling_giant_effect", () -> new RollingGiantEffectMobEffect());

    public static final RegistryObject<Potion> EFFECT_CHUCKY_GRAB = REGISTRY.register("effect_chucky_grab", () -> new EffectChuckyGrabMobEffect());

    public static final RegistryObject<Potion> COGNITO_HAZART = REGISTRY.register("cognito_hazart", () -> new CognitoHazartMobEffect());

    public static final RegistryObject<Potion> TIMER_OVERLAY = REGISTRY.register("timer_overlay", () -> new TimerOverlayMobEffect());

    public static final RegistryObject<Potion> PHATOM_PUPPET_BLINDNESS = REGISTRY.register("phatom_puppet_blindness", () -> new PhatomPuppetBlindnessMobEffect());

    public static final RegistryObject<Potion> SIGHT_OF_THE_PREDATOR = REGISTRY.register("sight_of_the_predator", () -> new SightOfThePredatorMobEffect());

    public static final RegistryObject<Potion> JUMPSCARE_ANGLER = REGISTRY.register("jumpscare_angler", () -> new JumpscareAnglerMobEffect());

    public static final RegistryObject<Potion> HYSTM_EFFECT = REGISTRY.register("hystm_effect", () -> new HystmEffectMobEffect());

    public static final RegistryObject<Potion> SAFE_AND_SOUND = REGISTRY.register("safe_and_sound", () -> new SafeAndSoundMobEffect());

    public static final RegistryObject<Potion> JUMPSCARE_BALDI = REGISTRY.register("jumpscare_baldi", () -> new JumpscareBaldiMobEffect());

    public static final RegistryObject<Potion> JUMPSCARE_TINKY = REGISTRY.register("jumpscare_tinky", () -> new JumpscareTinkyMobEffect());

    public static final RegistryObject<Potion> BLACK_HOLE_SUN_EFFECT = REGISTRY.register("black_hole_sun_effect", () -> new BlackHoleSunEffectMobEffect());

    public static final RegistryObject<Potion> VAMPIRISM = REGISTRY.register("vampirism", () -> new VampirismMobEffect());

    public static final RegistryObject<Potion> LYCANTHROPY = REGISTRY.register("lycanthropy", () -> new LycanthropyMobEffect());

    public static final RegistryObject<Potion> JUMPSCARE_CARTOON_CAT = REGISTRY.register("jumpscare_cartoon_cat", () -> new JumpscareCartoonCatMobEffect());

    public static final RegistryObject<Potion> JUMPSCARE_TAILSDOLL = REGISTRY.register("jumpscare_tailsdoll", () -> new JumpscareTailsdollMobEffect());

    public static final RegistryObject<Potion> JUMPSCARE_SUICIDE_MOUSE = REGISTRY.register("jumpscare_suicide_mouse", () -> new JumpscareSuicideMouseMobEffect());

    public static final RegistryObject<Potion> RED_MIST = REGISTRY.register("red_mist", () -> new RedMistMobEffect());

    public static final RegistryObject<Potion> FROM_OUT_OF_THIS_EARTH = REGISTRY.register("from_out_of_this_earth", () -> new FromOutOfThisEarthMobEffect());

    public static final RegistryObject<Potion> THE_WHISLE = REGISTRY.register("the_whisle", () -> new TheWhisleMobEffect());

    public static final RegistryObject<Potion> NPC_000INFLUENCE = REGISTRY.register("npc_000influence", () -> new NPC000influenceMobEffect());

    public static final RegistryObject<Potion> COVER_YOUR_EARS = REGISTRY.register("cover_your_ears", () -> new CoverYourEarsMobEffect());

    public static final RegistryObject<Potion> PARASITES_SONG = REGISTRY.register("parasites_song", () -> new ParasitesSongMobEffect());
}
