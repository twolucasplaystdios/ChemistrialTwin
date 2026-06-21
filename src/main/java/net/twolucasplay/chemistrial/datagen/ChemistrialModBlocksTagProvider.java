package net.twolucasplay.chemistrial.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
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
        tag(BlockTags.SOUL_FIRE_BASE_BLOCKS)
                .add(Blocks.SULFUR.builtInRegistryHolder().getKey());

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.ZINC_BLOCK.getKey())
                .add(ModBlocks.ZINC_ORE.getKey());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.ZINC_BLOCK.getKey())
                .add(ModBlocks.ZINC_ORE.getKey());
    }
}
