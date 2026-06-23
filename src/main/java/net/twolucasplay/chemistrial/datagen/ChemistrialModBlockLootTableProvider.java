package net.twolucasplay.chemistrial.datagen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.common.Mod;
import net.twolucasplay.chemistrial.blocks.ModBlocks;
import net.twolucasplay.chemistrial.items.ModItems;
import org.jspecify.annotations.NonNull;

import java.util.Set;

public class ChemistrialModBlockLootTableProvider extends BlockLootSubProvider {
    public ChemistrialModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.ZINC_BLOCK.value());
        add(ModBlocks.ZINC_ORE.get(), createOreDrop(ModBlocks.ZINC_ORE.get(), ModItems.RAW_ZINC.get()));
        this.add(ModBlocks.SULFUR_FIRE.get(), noDrop());
    }

    @Override
    protected @NonNull Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
