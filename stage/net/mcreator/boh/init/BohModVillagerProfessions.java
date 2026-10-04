package net.mcreator.boh.init;

import com.google.common.collect.ImmutableSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.mc.core.Holder;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.world.entity.ai.village.poi.PoiType;
import net.mcreator.boh.compat.mc.world.entity.ai.village.poi.PoiTypes;
import net.mcreator.boh.compat.mc.world.entity.npc.VillagerProfession;
import net.minecraft.block.Block;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.forge.registries.DeferredRegister;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.forge.registries.RegisterEvent;
import net.mcreator.boh.compat.forge.registries.RegistryObject;
import net.mcreator.boh.compat.forge.registries.Keys;
import net.mcreator.boh.compat.M;

public class BohModVillagerProfessions {

    private static final Map<String, BohModVillagerProfessions.ProfessionPoiType> POI_TYPES = new HashMap<>();

    public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, "boh");

    public static final RegistryObject<VillagerProfession> MERCHANT = registerProfession("merchant", () -> (Block) BohModBlocks.GAMETABLE.get(), () -> (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_cartographer")));

    private static RegistryObject<VillagerProfession> registerProfession(String name, Supplier<Block> block, Supplier<SoundEvent> soundEvent) {
        POI_TYPES.put(name, new BohModVillagerProfessions.ProfessionPoiType(block, null));
        return PROFESSIONS.register(name, () -> {
            Predicate<Holder<PoiType>> poiPredicate = poiTypeHolder -> POI_TYPES.get(name).poiType != null && poiTypeHolder.get() == POI_TYPES.get(name).poiType.get();
            return new VillagerProfession("boh:" + name, poiPredicate, poiPredicate, ImmutableSet.of(), ImmutableSet.of(), soundEvent.get());
        });
    }

    @SubscribeEvent
    public void registerProfessionPointsOfInterest(RegisterEvent event) {
        event.register(Keys.POI_TYPES, registerHelper -> {
            for (Entry<String, BohModVillagerProfessions.ProfessionPoiType> entry : POI_TYPES.entrySet()) {
                Block block = entry.getValue().block.get();
                String name = entry.getKey();
                Optional<Holder<PoiType>> existingCheck = PoiTypes.forState(M.defaultBlockState(block));
                if (existingCheck.isPresent()) {
                    M.error(BohMod.LOGGER, "Skipping villager profession " + name + " that uses POI block " + block + " that is already in use by " + existingCheck);
                } else {
                    PoiType poiType = new PoiType(ImmutableSet.copyOf(M.getPossibleStates(M.getStateDefinition(block))), 1, 1);
                    registerHelper.register(name, poiType);
                    entry.getValue().poiType = (Holder<PoiType>) M.getHolder(ForgeRegistries.POI_TYPES, poiType).get();
                }
            }
        });
    }

    private static class ProfessionPoiType {

        final Supplier<Block> block;

        Holder<PoiType> poiType;

        ProfessionPoiType(Supplier<Block> block, Holder<PoiType> poiType) {
            this.block = block;
            this.poiType = poiType;
        }
    }
}
