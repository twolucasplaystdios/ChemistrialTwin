package net.twolucasplay.chemistrial.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.twolucasplay.chemistrial.blocks.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ChemistrialModBlocksTagProvider extends BlockTagsProvider {
    public ChemistrialModBlocksTagProvider(PackOutput output,
                                           CompletableFuture<HolderLookup.Provider> lookupProvider,
                                           String modId) {
        super(output, lookupProvider, modId);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.ZINC_BLOCK.get())
                .add(ModBlocks.ZINC_ORE.get());

        tag(BlockTags.NEEDS_IRON_TOOL).add()
                .add(ModBlocks.ZINC_BLOCK.get())
                .add(ModBlocks.ZINC_ORE.get());
    }
}
