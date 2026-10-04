package net.mcreator.boh.init;

import net.mcreator.boh.block.entity.AnalogTVBillBlockEntity;
import net.mcreator.boh.block.entity.AnalogTVBillyBlockEntity;
import net.mcreator.boh.block.entity.AnalogTVBoiledBlockEntity;
import net.mcreator.boh.block.entity.AnalogTVCourageBlockEntity;
import net.mcreator.boh.block.entity.AnalogTVCoveBlockEntity;
import net.mcreator.boh.block.entity.AnalogTVFleshpitBlockEntity;
import net.mcreator.boh.block.entity.AnalogTVGeminiBlockEntity;
import net.mcreator.boh.block.entity.AnalogTVLocal58BlockEntity;
import net.mcreator.boh.block.entity.AnalogTVMaxBlockEntity;
import net.mcreator.boh.block.entity.AnalogTVNeedleBlockEntity;
import net.mcreator.boh.block.entity.AnalogTVNoMoreBlockEntity;
import net.mcreator.boh.block.entity.AnalogTVOperatorBlockEntity;
import net.mcreator.boh.block.entity.AnalogTVSadakoBlockEntity;
import net.mcreator.boh.block.entity.AnalogTVShamrockBlockEntity;
import net.mcreator.boh.block.entity.AnalogTVWyomingBlockEntity;
import net.mcreator.boh.block.entity.AnalogTelevisionBlockEntity;
import net.mcreator.boh.block.entity.BackroomsFloorBlockEntity;
import net.mcreator.boh.block.entity.ComputerBlockEntity;
import net.mcreator.boh.block.entity.GojibreathBlockEntity;
import net.mcreator.boh.block.entity.JarOWispTileEntity;
import net.mcreator.boh.block.entity.OvamorphOpenBlockEntity;
import net.mcreator.boh.block.entity.PipeBurstBlockEntity;
import net.mcreator.boh.block.entity.PokerNightTileEntity;
import net.mcreator.boh.block.entity.TwigTrapBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BohModBlockEntities {
   public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "boh");
   public static final RegistryObject<BlockEntityType<?>> OVAMORPH_OPEN = register("ovamorph_open", BohModBlocks.OVAMORPH_OPEN, OvamorphOpenBlockEntity::new);
   public static final RegistryObject<BlockEntityType<?>> GOJIBREATH = register("gojibreath", BohModBlocks.GOJIBREATH, GojibreathBlockEntity::new);
   public static final RegistryObject<BlockEntityType<?>> ANALOG_TV_SADAKO = register(
      "analog_tv_sadako", BohModBlocks.ANALOG_TV_SADAKO, AnalogTVSadakoBlockEntity::new
   );
   public static final RegistryObject<BlockEntityType<?>> ANALOG_TELEVISION = register(
      "analog_television", BohModBlocks.ANALOG_TELEVISION, AnalogTelevisionBlockEntity::new
   );
   public static final RegistryObject<BlockEntityType<?>> BACKROOMS_FLOOR = register(
      "backrooms_floor", BohModBlocks.BACKROOMS_FLOOR, BackroomsFloorBlockEntity::new
   );
   public static final RegistryObject<BlockEntityType<?>> ANALOG_TV_BILL = register("analog_tv_bill", BohModBlocks.ANALOG_TV_BILL, AnalogTVBillBlockEntity::new);
   public static final RegistryObject<BlockEntityType<?>> ANALOG_TV_BILLY = register(
      "analog_tv_billy", BohModBlocks.ANALOG_TV_BILLY, AnalogTVBillyBlockEntity::new
   );
   public static final RegistryObject<BlockEntityType<?>> ANALOG_TV_COURAGE = register(
      "analog_tv_courage", BohModBlocks.ANALOG_TV_COURAGE, AnalogTVCourageBlockEntity::new
   );
   public static final RegistryObject<BlockEntityType<?>> ANALOG_TV_COVE = register("analog_tv_cove", BohModBlocks.ANALOG_TV_COVE, AnalogTVCoveBlockEntity::new);
   public static final RegistryObject<BlockEntityType<?>> ANALOG_TV_FLESHPIT = register(
      "analog_tv_fleshpit", BohModBlocks.ANALOG_TV_FLESHPIT, AnalogTVFleshpitBlockEntity::new
   );
   public static final RegistryObject<BlockEntityType<?>> ANALOG_TV_GEMINI = register(
      "analog_tv_gemini", BohModBlocks.ANALOG_TV_GEMINI, AnalogTVGeminiBlockEntity::new
   );
   public static final RegistryObject<BlockEntityType<?>> ANALOG_TV_LOCAL_58 = register(
      "analog_tv_local_58", BohModBlocks.ANALOG_TV_LOCAL_58, AnalogTVLocal58BlockEntity::new
   );
   public static final RegistryObject<BlockEntityType<?>> ANALOG_TV_MAX = register("analog_tv_max", BohModBlocks.ANALOG_TV_MAX, AnalogTVMaxBlockEntity::new);
   public static final RegistryObject<BlockEntityType<?>> ANALOG_TV_NEEDLE = register(
      "analog_tv_needle", BohModBlocks.ANALOG_TV_NEEDLE, AnalogTVNeedleBlockEntity::new
   );
   public static final RegistryObject<BlockEntityType<?>> ANALOG_TV_NO_MORE = register(
      "analog_tv_no_more", BohModBlocks.ANALOG_TV_NO_MORE, AnalogTVNoMoreBlockEntity::new
   );
   public static final RegistryObject<BlockEntityType<?>> ANALOG_TV_OPERATOR = register(
      "analog_tv_operator", BohModBlocks.ANALOG_TV_OPERATOR, AnalogTVOperatorBlockEntity::new
   );
   public static final RegistryObject<BlockEntityType<?>> ANALOG_TV_SHAMROCK = register(
      "analog_tv_shamrock", BohModBlocks.ANALOG_TV_SHAMROCK, AnalogTVShamrockBlockEntity::new
   );
   public static final RegistryObject<BlockEntityType<?>> ANALOG_TV_WYOMING = register(
      "analog_tv_wyoming", BohModBlocks.ANALOG_TV_WYOMING, AnalogTVWyomingBlockEntity::new
   );
   public static final RegistryObject<BlockEntityType<?>> TWIG_TRAP = register("twig_trap", BohModBlocks.TWIG_TRAP, TwigTrapBlockEntity::new);
   public static final RegistryObject<BlockEntityType<?>> PIPE_BURST = register("pipe_burst", BohModBlocks.PIPE_BURST, PipeBurstBlockEntity::new);
   public static final RegistryObject<BlockEntityType<?>> ANALOG_TV_BOILED = register(
      "analog_tv_boiled", BohModBlocks.ANALOG_TV_BOILED, AnalogTVBoiledBlockEntity::new
   );
   public static final RegistryObject<BlockEntityType<?>> COMPUTER = register("computer", BohModBlocks.COMPUTER, ComputerBlockEntity::new);
   public static final RegistryObject<BlockEntityType<JarOWispTileEntity>> JAR_O_WISP = REGISTRY.register(
      "jar_o_wisp", () -> Builder.of(JarOWispTileEntity::new, new Block[]{(Block)BohModBlocks.JAR_O_WISP.get()}).build(null)
   );
   public static final RegistryObject<BlockEntityType<PokerNightTileEntity>> POKER_NIGHT = REGISTRY.register(
      "poker_night", () -> Builder.of(PokerNightTileEntity::new, new Block[]{(Block)BohModBlocks.POKER_NIGHT.get()}).build(null)
   );

   private static RegistryObject<BlockEntityType<?>> register(String registryname, RegistryObject<Block> block, BlockEntitySupplier<?> supplier) {
      return REGISTRY.register(registryname, () -> Builder.of(supplier, new Block[]{(Block)block.get()}).build(null));
   }
}
