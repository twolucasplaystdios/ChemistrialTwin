package net.twolucasplay.chemistrial;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.twolucasplay.chemistrial.datagen.ChemistrialModBlockLootTableProvider;
import net.twolucasplay.chemistrial.datagen.ChemistrialModBlocksTagProvider;
import net.twolucasplay.chemistrial.datagen.ChemistrialModItemTagProvider;
import net.twolucasplay.chemistrial.datagen.ChemistrialModModelProvider;

import java.util.Collections;
import java.util.List;

@EventBusSubscriber(modid = ChemistrialMod.MODID)
public class ChemistrialModDataGen {
    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        var lookupProvider = event.getLookupProvider();

        generator.addProvider(true, new ChemistrialModModelProvider(packOutput));
        generator.addProvider(true, new ChemistrialModItemTagProvider(packOutput, lookupProvider, ChemistrialMod.MODID));
        generator.addProvider(true, new ChemistrialModBlocksTagProvider(packOutput, lookupProvider, ChemistrialMod.MODID));
        generator.addProvider(true, new LootTableProvider(packOutput, Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(ChemistrialModBlockLootTableProvider::new, LootContextParamSets.BLOCK)), lookupProvider));
    }
}
