package net.mcreator.boh.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(bus = Bus.MOD)
public class BohModTabs {
   public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "boh");
   public static final RegistryObject<CreativeModeTab> ENTITIES = REGISTRY.register(
      "entities",
      () -> CreativeModeTab.builder()
         .title(Component.translatable("item_group.boh.entities"))
         .icon(() -> new ItemStack((ItemLike)BohModItems.ICON_2.get()))
         .displayItems((parameters, tabData) -> {
            tabData.accept((ItemLike)BohModItems.BEN_DROWNED_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.JEFF_THE_KILLER_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.SIREN_HEAD_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.PYRAMID_HEAD_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.SAW_RUNNER_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.XENOMORPH_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.FACEHUGGER_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.CHESTBURSTER_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.LIFEFORM_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.SLENDER_MAN_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.DEMOGORGON_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.GOLD_LOST_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.RAKE_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.WHITEFACE_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.MICHAEL_DAVIES_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.SMILE_DOG_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.SPRINGTRAP_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.AO_ONI_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.SONIC_EXE_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.SIX_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.RAATMA_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.RAT_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.GASTER_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.JAMES_SUNDERLAND_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.RATAZANA_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.SOUICHI_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.SIMONHENRIKSSON_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.BOOK_SIMON_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.MICHAEL_MYERS_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.EYELESS_JACK_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.WENDIGO_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.NEMESIS_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.JASON_VOORHEES_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.BIG_DADDY_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.LITTLE_SISTER_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.DEER_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.KRAMPUS_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.SADAKO_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.SPECIMEN_9_BOSS_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.ROLLING_GIANT_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.CHUCKY_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.GHOSTFACE_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.REXY_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.SEED_EATER_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.STILTWALKER_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.FREDDY_KRUEGER_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.BOILED_ONE_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.CELEBI_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.GOJI_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.INK_DEMON_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.PREDATOR_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.ANGLER_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.TINKY_WINKY_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.NEWBORN_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.CARTOON_CAT_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.BALDI_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.FIGURE_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.VITA_MIMIC_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.TRIMMING_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.SUBJECT_3_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.PATRICK_BATEMAN_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.LEATHERFACE_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.MX_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.SCISSORMAN_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.HYPNO_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.RUSSIAN_SLEEP_EXPERIMENT_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.PUMPKIN_PLAYER_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.KRASUE_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.KIRIE_HIMURO_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.LAUGHING_JACK_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.JANE_THE_KILLER_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.TAILS_DOLL_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.SUICIDE_MOUSE_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.SQUIDWARD_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.BRUCE_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.NOTHING_THERE_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.FLOWERS_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.NPC_000_SPAWN_EGG.get());
         })
         .withSearchBar()
         .build()
   );
   public static final RegistryObject<CreativeModeTab> BLOCKS = REGISTRY.register(
      "blocks",
      () -> CreativeModeTab.builder()
         .title(Component.translatable("item_group.boh.blocks"))
         .icon(() -> new ItemStack((ItemLike)BohModItems.ICON_1.get()))
         .displayItems((parameters, tabData) -> {
            tabData.accept(((Block)BohModBlocks.XENOMORPH_BLOOD.get()).asItem());
            tabData.accept(((Block)BohModBlocks.OVAMORPH.get()).asItem());
            tabData.accept(((Block)BohModBlocks.DRAWING_SLENDER.get()).asItem());
            tabData.accept(((Block)BohModBlocks.KINDNESS_FLOWER.get()).asItem());
            tabData.accept(((Block)BohModBlocks.LIFEFORM_GROWTH.get()).asItem());
            tabData.accept(((Block)BohModBlocks.VOODOO_DOLL.get()).asItem());
            tabData.accept(((Block)BohModBlocks.SPINEL_ORE_ORE.get()).asItem());
            tabData.accept(((Block)BohModBlocks.GOJIBREATH.get()).asItem());
            tabData.accept(((Block)BohModBlocks.ANALOGTVSADAKOC.get()).asItem());
            tabData.accept(((Block)BohModBlocks.ANALOG_TELEVISION.get()).asItem());
            tabData.accept(((Block)BohModBlocks.BACKROOMS_FLOOR.get()).asItem());
            tabData.accept(((Block)BohModBlocks.BACKROOMS_WALLS.get()).asItem());
            tabData.accept(((Block)BohModBlocks.BACKROOMS_CEILING_TILE.get()).asItem());
            tabData.accept(((Block)BohModBlocks.BACKROOMS_LAMP.get()).asItem());
            tabData.accept(((Block)BohModBlocks.GAMETABLE.get()).asItem());
            tabData.accept(((Block)BohModBlocks.TWIG_TRAP.get()).asItem());
            tabData.accept(((Block)BohModBlocks.BACKROOMS_WALLS_TOP.get()).asItem());
            tabData.accept(((Block)BohModBlocks.BACKROOMS_WALLS_BOTTOM.get()).asItem());
            tabData.accept(((Block)BohModBlocks.BACKROOMS_SOCKET.get()).asItem());
            tabData.accept(((Block)BohModBlocks.PIPE_BLOCK.get()).asItem());
            tabData.accept(((Block)BohModBlocks.PIPE_BURST.get()).asItem());
            tabData.accept(((Block)BohModBlocks.RIFT_STABILIZER.get()).asItem());
            tabData.accept(((Block)BohModBlocks.COMPUTER.get()).asItem());
            tabData.accept(((Block)BohModBlocks.TUBBY_CUSTARD.get()).asItem());
            tabData.accept(((Block)BohModBlocks.SPINEL_BLOCK.get()).asItem());
            tabData.accept(((Block)BohModBlocks.BLOODSTONE_BLOCK.get()).asItem());
            tabData.accept(((Block)BohModBlocks.BLACK_WALNUT_WOOD.get()).asItem());
            tabData.accept(((Block)BohModBlocks.BLACK_WALNUT_LOG.get()).asItem());
            tabData.accept(((Block)BohModBlocks.BLACK_WALNUT_PLANKS.get()).asItem());
            tabData.accept(((Block)BohModBlocks.BLACK_WALNUT_LEAVES.get()).asItem());
            tabData.accept(((Block)BohModBlocks.BLACK_WALNUT_STAIRS.get()).asItem());
            tabData.accept(((Block)BohModBlocks.BLACK_WALNUT_SLAB.get()).asItem());
            tabData.accept(((Block)BohModBlocks.BLACK_WALNUT_FENCE.get()).asItem());
            tabData.accept(((Block)BohModBlocks.BLACK_WALNUT_FENCE_GATE.get()).asItem());
            tabData.accept(((Block)BohModBlocks.BLACK_WALNUT_PRESSURE_PLATE.get()).asItem());
            tabData.accept(((Block)BohModBlocks.BLACK_WALNUT_BUTTON.get()).asItem());
            tabData.accept(((Block)BohModBlocks.DOGWOOD_WOOD.get()).asItem());
            tabData.accept(((Block)BohModBlocks.DOGWOOD_LOG.get()).asItem());
            tabData.accept(((Block)BohModBlocks.DOGWOOD_PLANKS.get()).asItem());
            tabData.accept(((Block)BohModBlocks.DOGWOOD_LEAVES.get()).asItem());
            tabData.accept(((Block)BohModBlocks.DOGWOOD_STAIRS.get()).asItem());
            tabData.accept(((Block)BohModBlocks.DOGWOOD_SLAB.get()).asItem());
            tabData.accept(((Block)BohModBlocks.DOGWOOD_FENCE.get()).asItem());
            tabData.accept(((Block)BohModBlocks.DOGWOOD_FENCE_GATE.get()).asItem());
            tabData.accept(((Block)BohModBlocks.DOGWOOD_PRESSURE_PLATE.get()).asItem());
            tabData.accept(((Block)BohModBlocks.DOGWOOD_BUTTON.get()).asItem());
            tabData.accept(((Block)BohModBlocks.SASSAFRAS_WOOD.get()).asItem());
            tabData.accept(((Block)BohModBlocks.SASSAFRAS_LOG.get()).asItem());
            tabData.accept(((Block)BohModBlocks.SASSAFRAS_PLANKS.get()).asItem());
            tabData.accept(((Block)BohModBlocks.SASSAFRAS_LEAVES.get()).asItem());
            tabData.accept(((Block)BohModBlocks.SASSAFRAS_STAIRS.get()).asItem());
            tabData.accept(((Block)BohModBlocks.SASSAFRAS_SLAB.get()).asItem());
            tabData.accept(((Block)BohModBlocks.SASSAFRAS_FENCE.get()).asItem());
            tabData.accept(((Block)BohModBlocks.SASSAFRAS_FENCE_GATE.get()).asItem());
            tabData.accept(((Block)BohModBlocks.SASSAFRAS_PRESSURE_PLATE.get()).asItem());
            tabData.accept(((Block)BohModBlocks.SASSAFRAS_BUTTON.get()).asItem());
            tabData.accept(((Block)BohModBlocks.JAR_O_WISP.get()).asItem());
            tabData.accept(((Block)BohModBlocks.BLACK_WALNUT_STRIPPED_WOOD.get()).asItem());
            tabData.accept(((Block)BohModBlocks.BLACK_WALNUT_STRIPPED_LOG.get()).asItem());
            tabData.accept(((Block)BohModBlocks.BLACK_WALNUT_DOOR.get()).asItem());
            tabData.accept(((Block)BohModBlocks.BLACK_WALNUT_TRAPDOOR.get()).asItem());
            tabData.accept(((Block)BohModBlocks.DOGWOOD_STRIPPED_WOOD.get()).asItem());
            tabData.accept(((Block)BohModBlocks.DOGWOOD_STRIPPED_LOG.get()).asItem());
            tabData.accept(((Block)BohModBlocks.DOGWOOD_DOOR.get()).asItem());
            tabData.accept(((Block)BohModBlocks.DOGWOOD_TRAPDOOR.get()).asItem());
            tabData.accept(((Block)BohModBlocks.SASSAFRAS_STRIPPED_WOOD.get()).asItem());
            tabData.accept(((Block)BohModBlocks.SASSAFRAS_STRIPPED_LOG.get()).asItem());
            tabData.accept(((Block)BohModBlocks.SASSAFRAS_DOOR.get()).asItem());
            tabData.accept(((Block)BohModBlocks.SASSAFRAS_TRAPDOOR.get()).asItem());
            tabData.accept(((Block)BohModBlocks.BLACK_WALLNUT_SAPPLING.get()).asItem());
            tabData.accept(((Block)BohModBlocks.DOGWOOD_SAPLING.get()).asItem());
            tabData.accept(((Block)BohModBlocks.SASSAFRAS_SAPLING.get()).asItem());
            tabData.accept(((Block)BohModBlocks.VITA_CRAWL.get()).asItem());
            tabData.accept(((Block)BohModBlocks.SINISTREE_WOOD.get()).asItem());
            tabData.accept(((Block)BohModBlocks.SINISTREE_LOG.get()).asItem());
            tabData.accept(((Block)BohModBlocks.SINISTREE_PLANKS.get()).asItem());
            tabData.accept(((Block)BohModBlocks.SINISTREE_LEAVES.get()).asItem());
            tabData.accept(((Block)BohModBlocks.SINISTREE_STAIRS.get()).asItem());
            tabData.accept(((Block)BohModBlocks.SINISTREE_SLAB.get()).asItem());
            tabData.accept(((Block)BohModBlocks.SINISTREE_FENCE.get()).asItem());
            tabData.accept(((Block)BohModBlocks.SINISTREE_FENCE_GATE.get()).asItem());
            tabData.accept(((Block)BohModBlocks.SINISTREE_PRESSURE_PLATE.get()).asItem());
            tabData.accept(((Block)BohModBlocks.SINISTREE_BUTTON.get()).asItem());
            tabData.accept(((Block)BohModBlocks.SINISTREE_STRIPPED_WOOD.get()).asItem());
            tabData.accept(((Block)BohModBlocks.SINISTREE_STRIPPED_LOG.get()).asItem());
            tabData.accept(((Block)BohModBlocks.SINISTREE_DOOR.get()).asItem());
            tabData.accept(((Block)BohModBlocks.SINISTREE_TRAP_DOOR.get()).asItem());
            tabData.accept(((Block)BohModBlocks.SINISTREE_SAPLING.get()).asItem());
            tabData.accept(((Block)BohModBlocks.STUD_PART.get()).asItem());
         })
         .withSearchBar()
         .withTabsBefore(new ResourceLocation[]{ENTITIES.getId()})
         .build()
   );
   public static final RegistryObject<CreativeModeTab> ITEMS = REGISTRY.register(
      "items",
      () -> CreativeModeTab.builder()
         .title(Component.translatable("item_group.boh.items"))
         .icon(() -> new ItemStack((ItemLike)BohModItems.ICON_3.get()))
         .displayItems((parameters, tabData) -> {
            tabData.accept((ItemLike)BohModItems.WHITEFACEHEART.get());
            tabData.accept((ItemLike)BohModItems.POLAROID.get());
            tabData.accept((ItemLike)BohModItems.KILLER_KNIFE.get());
            tabData.accept((ItemLike)BohModItems.THE_GREAT_KNIFE.get());
            tabData.accept((ItemLike)BohModItems.SPINEL.get());
            tabData.accept((ItemLike)BohModItems.RAY_GUN.get());
            tabData.accept((ItemLike)BohModItems.SCHIZOSLEDGE.get());
            tabData.accept((ItemLike)BohModItems.SIMONS_BOOK.get());
            tabData.accept((ItemLike)BohModItems.LIVER.get());
            tabData.accept((ItemLike)BohModItems.THE_SLASHER.get());
            tabData.accept((ItemLike)BohModItems.MASSACRE_AXE.get());
            tabData.accept((ItemLike)BohModItems.CRUCIFIXITEM.get());
            tabData.accept((ItemLike)BohModItems.DEER_MASK_HELMET.get());
            tabData.accept((ItemLike)BohModItems.SIRENPHONE.get());
            tabData.accept((ItemLike)BohModItems.VHS_TAPE.get());
            tabData.accept((ItemLike)BohModItems.GOJI_HEAD_HELMET.get());
            tabData.accept((ItemLike)BohModItems.LIFEFORM_EFFIGY.get());
            tabData.accept((ItemLike)BohModItems.WF_PISTOL.get());
            tabData.accept((ItemLike)BohModItems.VENISON.get());
            tabData.accept((ItemLike)BohModItems.COOKED_VENISON.get());
            tabData.accept((ItemLike)BohModItems.SPECIMEN_9_HEAD.get());
            tabData.accept((ItemLike)BohModItems.EXE_BOOTS_BOOTS.get());
            tabData.accept((ItemLike)BohModItems.DESIRE_EDGE.get());
            tabData.accept((ItemLike)BohModItems.GUN_WITH_ONE_BULLET.get());
            tabData.accept((ItemLike)BohModItems.BOWL_CREEPYPASTA.get());
            tabData.accept((ItemLike)BohModItems.BIG_DADDY_DRILL.get());
            tabData.accept((ItemLike)BohModItems.STOP_SIGN.get());
            tabData.accept((ItemLike)BohModItems.TACTICAL_KNIFE.get());
            tabData.accept((ItemLike)BohModItems.PARTY_POPPER.get());
            tabData.accept((ItemLike)BohModItems.CHAINSAW.get());
            tabData.accept((ItemLike)BohModItems.FREDDY_CLAW.get());
            tabData.accept((ItemLike)BohModItems.EXOTIC_SOUL.get());
            tabData.accept((ItemLike)BohModItems.DEMONIC_SOUL.get());
            tabData.accept((ItemLike)BohModItems.KILLERS_SOUL.get());
            tabData.accept((ItemLike)BohModItems.MONSTROUS_SOUL.get());
            tabData.accept((ItemLike)BohModItems.VAMPIRE_BLOOD.get());
            tabData.accept((ItemLike)BohModItems.WEREWOLF_TEETH.get());
            tabData.accept((ItemLike)BohModItems.ECTOPLASM.get());
            tabData.accept((ItemLike)BohModItems.GILL.get());
            tabData.accept((ItemLike)BohModItems.FRIED_GILL.get());
            tabData.accept((ItemLike)BohModItems.DEMONIC_HORN.get());
            tabData.accept((ItemLike)BohModItems.PAINTED_SWORD.get());
            tabData.accept((ItemLike)BohModItems.MACHETE.get());
            tabData.accept((ItemLike)BohModItems.JASONS_MASK_HELMET.get());
            tabData.accept((ItemLike)BohModItems.HAUNTED_PAPER.get());
            tabData.accept((ItemLike)BohModItems.BLOOD_STONE.get());
            tabData.accept((ItemLike)BohModItems.GIANT_SCISSOR.get());
            tabData.accept(((Block)BohModBlocks.DREAM_CATCHER.get()).asItem());
            tabData.accept((ItemLike)BohModItems.STEPHANO.get());
            tabData.accept((ItemLike)BohModItems.SOUL_STEALER.get());
            tabData.accept((ItemLike)BohModItems.DIAPER_ARMOR_LEGGINGS.get());
            tabData.accept((ItemLike)BohModItems.HILT_OF_A_BLACKSMITH.get());
            tabData.accept((ItemLike)BohModItems.HOLY_WATER_ITEM.get());
            tabData.accept((ItemLike)BohModItems.APPLALYPSE.get());
            tabData.accept((ItemLike)BohModItems.ZACKS_SCYTHE.get());
            tabData.accept((ItemLike)BohModItems.WALNUT.get());
            tabData.accept((ItemLike)BohModItems.SINGED_DREAM_CATCHER.get());
            tabData.accept((ItemLike)BohModItems.BROKEN_CAMERA.get());
            tabData.accept((ItemLike)BohModItems.HATREDS_END.get());
            tabData.accept((ItemLike)BohModItems.BOOM_STICK.get());
            tabData.accept((ItemLike)BohModItems.CAMERA_OBSCURA.get());
            tabData.accept((ItemLike)BohModItems.MUSIC_DISC_OTHERSIDE_ENIGMA.get());
            tabData.accept((ItemLike)BohModItems.MUSIC_DISC_ENDLESS_HUNGER.get());
            tabData.accept((ItemLike)BohModItems.MUSIC_DISC_HEY_ITS_ME.get());
            tabData.accept((ItemLike)BohModItems.MUSIC_DISC_LAVENDER.get());
            tabData.accept((ItemLike)BohModItems.MUSIC_DISC_MAJIN.get());
            tabData.accept((ItemLike)BohModItems.MUSIC_DISC_PROMISE.get());
            tabData.accept((ItemLike)BohModItems.MUSIC_DISC_WHITENOIZ.get());
            tabData.accept((ItemLike)BohModItems.MUSIC_DISC_TURN_THE_LIGHTS_OFF.get());
            tabData.accept((ItemLike)BohModItems.MUSIC_DISC_TOUCHTONE.get());
            tabData.accept((ItemLike)BohModItems.MUSIC_DISC_DAY_THEME.get());
            tabData.accept((ItemLike)BohModItems.MUSIC_DISC_DESTROYED_KINGDOM.get());
            tabData.accept((ItemLike)BohModItems.HEMOTORRENT.get());
            tabData.accept((ItemLike)BohModItems.BALDI_RULER.get());
            tabData.accept((ItemLike)BohModItems.MUSIC_DISC_SMASHING_WINDSHIELDS.get());
            tabData.accept((ItemLike)BohModItems.BIG_TOP_BURGER.get());
            tabData.accept((ItemLike)BohModItems.MUSIC_DISCS_BOYS.get());
            tabData.accept((ItemLike)BohModItems.MUSIC_DISC_NEUTRAL_01.get());
            tabData.accept((ItemLike)BohModItems.MIMICRY.get());
            tabData.accept((ItemLike)BohModItems.MUSIC_DISC_FLOWERS.get());
            tabData.accept((ItemLike)BohModItems.WHISPERING_THORNS_HELMET.get());
            tabData.accept((ItemLike)BohModItems.MUSIC_DISC_HEAVEN_SAYS.get());
         })
         .withSearchBar()
         .withTabsBefore(new ResourceLocation[]{BLOCKS.getId()})
         .build()
   );
   public static final RegistryObject<CreativeModeTab> CRYPTIDS = REGISTRY.register(
      "cryptids",
      () -> CreativeModeTab.builder()
         .title(Component.translatable("item_group.boh.cryptids"))
         .icon(() -> new ItemStack((ItemLike)BohModItems.ICON_4.get()))
         .displayItems((parameters, tabData) -> {
            tabData.accept((ItemLike)BohModItems.MOTHMAN_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.FRESNO_NIGHTCRAWLER_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.WILLOWISP_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.WAR_HORSE_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.FAMINE_HORSE_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.DEATH_HORSE_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.PESTILENCE_HORSE_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.UNICORN_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.JACKALOPE_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.FLATWOODS_MONSTER_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.GRAFTON_MONSTER_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.MOTHLING_SPAWN_EGG.get());
         })
         .withSearchBar()
         .withTabsBefore(new ResourceLocation[]{ITEMS.getId()})
         .build()
   );
   public static final RegistryObject<CreativeModeTab> MONSTERS = REGISTRY.register(
      "monsters",
      () -> CreativeModeTab.builder()
         .title(Component.translatable("item_group.boh.monsters"))
         .icon(() -> new ItemStack((ItemLike)BohModItems.POLAROID.get()))
         .displayItems((parameters, tabData) -> {
            tabData.accept((ItemLike)BohModItems.GRAY_ALIEN_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.SAUCER_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.MARTIAN_DRONE_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.VAMPIRE_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.WEREWOLF_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.GHOST_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.DEMON_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.SWAMP_MONSTER_SPAWN_EGG.get());
            tabData.accept((ItemLike)BohModItems.WEREWOLF_DUMMY_SPAWN_EGG.get());
         })
         .withSearchBar()
         .withTabsBefore(new ResourceLocation[]{CRYPTIDS.getId()})
         .build()
   );
   public static final RegistryObject<CreativeModeTab> DOCUMENTS = REGISTRY.register(
      "documents",
      () -> CreativeModeTab.builder()
         .title(Component.translatable("item_group.boh.documents"))
         .icon(() -> new ItemStack((ItemLike)BohModItems.HAUNTED_PAPER.get()))
         .displayItems((parameters, tabData) -> {
            tabData.accept((ItemLike)BohModItems.DOCUMENT_XENOMORPH.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_SMILE_DOG.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_SPRINGTRAP.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_REXY.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_MOTHMAN.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_RATMAA.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_SUICHI.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_MYERS.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_NEMESIS.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_BIGDADDY.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_ROLLINGGIANT.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_GHOSTFACE.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_GOJI.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_BENDY.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_PREDATOR.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_ANGLER.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_TINKY.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_CHUCKY.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_CARTOONCAT.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_BALDI.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_DEMOGORGON.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_FIGURE.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_JASON.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_BATEMAN.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_VITA.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_SUBJECT_3.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_MX.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_HYPNO.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_SCISSORMAN.get());
            tabData.accept((ItemLike)BohModItems.PUMPKIN_GUY_DOCUMENT.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_KIRIE.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_BEN_DROWNED.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_AO_ONI.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_2011_X.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_WHITE_FACE.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_KRASUE.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_GOLD.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_SUICIDE_MOUSE.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_SQUIDWARD.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_TAILSDOLL.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_LEATHERFACE.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_JANE.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_JEFF.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_BRUCE.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_NOTHING_THERE.get());
            tabData.accept((ItemLike)BohModItems.DOCUMENT_FLOWERS.get());
         })
         .withSearchBar()
         .withTabsBefore(new ResourceLocation[]{MONSTERS.getId()})
         .build()
   );

   @SubscribeEvent
   public static void buildTabContentsVanilla(BuildCreativeModeTabContentsEvent tabData) {
      if (tabData.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
         tabData.accept((ItemLike)BohModItems.PRETZEL.get());
      }
   }
}
