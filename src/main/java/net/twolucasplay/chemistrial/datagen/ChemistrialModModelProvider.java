package net.twolucasplay.chemistrial.datagen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.twolucasplay.chemistrial.ChemistrialMod;
import net.twolucasplay.chemistrial.blocks.ModBlocks;
import net.twolucasplay.chemistrial.items.ModItems;

public class ChemistrialModModelProvider extends ModelProvider {
    public ChemistrialModModelProvider(PackOutput output) {
        super(output, ChemistrialMod.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        for (Holder<Item> itemHolder : ModItems.ITEMS.getEntries()) {
            Item item = itemHolder.value();

            if (item instanceof BlockItem) {
                continue;
            }
            itemModels.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
        }

        for (DeferredHolder<net.minecraft.world.level.block.Block, ? extends net.minecraft.world.level.block.Block> blockHolder : ModBlocks.BLOCKS.getEntries()) {
            Block block = blockHolder.value();
            blockModels.createTrivialCube(block);
        }
    }
}
